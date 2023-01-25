package tacoplayer;

import battlecode.common.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public strictfp class RobotPlayer {

    static final Random rng = new Random(6147);

    static final Direction[] directions = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST,
    };

    // Calculated as (60 * sqrt(2)) ^ 2
    static final int MAX_MAP_DIST_SQ = 7200;
    static int turnCount = 0;
    static Team ourTeam;
    static Team theirTeam;

    static int hqCount;
    static int islandCount;

    static int mapHeight;
    static int mapWidth;
    static int mapSize;
    static MapLocation mapCenter;

    static int[][] map;

    // TODO - Convert all ArrayLists to arrays later for bytecode optimization
    // TODO - "Closest" is just straight line distance, improve upon that
    // TODO - Stop getting confused by other robots
    static MapLocation closestHqLoc;
    static MapLocation closestEnemyHqLoc;
    static MapLocation[] ourHqLocs = new MapLocation[4];
    static MapLocation[] enemyHqLocs = new MapLocation[12];

    static ArrayList<MapLocation> knownWellLocs = new ArrayList<>();
    static IslandInfo[] knownIslands = new IslandInfo[GameConstants.MAX_NUMBER_ISLANDS];
    static MapLocation closestFriendlyIslandLoc;
    static MapLocation closestNeutralIslandLoc;
    static MapLocation closestEnemyIslandLoc;

    static MapLocation nearestADWell;
    static MapLocation nearestMNWell;
    static MapLocation nearestEXWell;
    static MapLocation secondNearestADWell;
    static MapLocation secondNearestMNWell;
    static MapLocation secondNearestEXWell;
    static int nearestADWellDistSq = Integer.MAX_VALUE;
    static int nearestMNWellDistSq = Integer.MAX_VALUE;
    static int nearestEXWellDistSq = Integer.MAX_VALUE;
    static int secondNearestADWellDistSq = Integer.MAX_VALUE;
    static int secondNearestMNWellDistSq= Integer.MAX_VALUE;
    static int secondNearestEXWellDistSq= Integer.MAX_VALUE;
    static int lastRoundHealth;
    static int thisRoundHealth;
    static boolean retreatMode = false;

    // Lists to hold values that couldn't be written to shared array
    // but should be once the bot is in range to write
    static List<Integer> write_indexes = new ArrayList<>();
    static List<Integer> fwrite_values = new ArrayList<>();

    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {

        takeFirstTurn(rc);
        takeSecondTurn(rc);
        //noinspection InfiniteLoopStatement
        while (true) {
            takeATurn(rc);
        }
    }

    private static void takeFirstTurn(RobotController rc) throws GameActionException {
        // Set game constants
        setGameConstants(rc);

        // Upload own location if HQ
        if (rc.getType() == RobotType.HEADQUARTERS) {
            Comms.putHqLocationOnline(rc);
        }
        else {
            Comms.readOurHqLocs(rc);
        }

        // Initialize symmetry with 111 (all symmetries)
        if (Comms.isFirstHQ(rc)) {
            Comms.initializeSymmetry(rc);
        }
        takeATurn(rc);
    }

    private static void takeSecondTurn(RobotController rc) throws GameActionException {
        if (rc.getType() == RobotType.HEADQUARTERS) {
            Comms.readOurHqLocs(rc);
        }
        takeATurn(rc);
    }

    private static void takeATurn(RobotController rc) {
        int startRoundNum = rc.getRoundNum(); // The round number at the start of the robot's turn
        turnCount += 1;  // We have now been alive for one more turn!
        try {

            // Read from comms array
            Comms.readAndStoreFromSharedArray(rc);

            // Scan surroundings
            Sensing.scanObstacles(rc); // TODO - clouds, currents, etc.
            Sensing.scanRobots(rc);
            Sensing.scanIslands(rc);
            Sensing.scanWells(rc); // TODO - pick well based on what we need. Also push to shared array.

            Comms.updateEnemyHqLocs(rc);

            if (rc.getType() != RobotType.HEADQUARTERS) {
                closestHqLoc = MapLocationUtil.getClosestMapLocEuclidean(rc, ourHqLocs);
            }

            // The same run() function is called for every robot on your team, even if they are
            // different types. Here, we separate the control depending on the RobotType, so we can
            // use different strategies on different robots. If you wish, you are free to rewrite
            // this into a different control structure!
            switch (rc.getType()) {
                case HEADQUARTERS:
                    HeadquartersStrategy.runHeadquarters(rc);
                    break;
                case CARRIER:
                    CarrierStrategy.runCarrier(rc);
                    break;
                case LAUNCHER:
                    LauncherStrategy.runLauncher(rc);
                    break;
                case AMPLIFIER:
                    AmplifierStrategy.runAmplifier(rc);
                    break;
                case BOOSTER:
                    // TODO
                    break;
                case DESTABILIZER:
                    // TODO
                    break;
            }

            // Put information in shared array at the end of each round
            Comms.putSymmetryOnline(rc);
            Comms.putIslandsOnline(rc);

        } catch (GameActionException e) {
            System.out.println(rc.getType() + " GameActionException");
            e.printStackTrace();

        } catch (Exception e) {
            System.out.println(rc.getType() + " Exception");
            e.printStackTrace();
        } finally {
            // Check if a turn was skipped by comparing with startRoundNum
            if (rc.getRoundNum() > startRoundNum) {
                System.out.println("Skipped a turn!");
            }
            Clock.yield();
        }
    }

    private static void setGameConstants(RobotController rc) {
        if (rc.getTeam() == Team.A) {
            ourTeam = Team.A;
            theirTeam = Team.B;
        } else {
            ourTeam = Team.B;
            theirTeam = Team.A;
        }
        islandCount = rc.getIslandCount();
        mapWidth = rc.getMapWidth();
        mapHeight = rc.getMapHeight();
        mapSize = rc.getMapWidth() * rc.getMapHeight();
        mapCenter = new MapLocation(rc.getMapWidth() / 2, rc.getMapHeight() / 2);
        map = new int[mapWidth][mapHeight];
    }

    private static void scanObstacles(RobotController rc) {
        // TODO - scan for clouds and currents and add to local memory
    }

    static void scanRobots(RobotController rc) throws GameActionException {
        RobotInfo[] robots = rc.senseNearbyRobots();

        for (int j = -1; ++j < robots.length;) {
            switch (robots[j].getType()) {
                case HEADQUARTERS:
                    if (robots[j].team == theirTeam) {
                        // If enemy headquarters is spotted, try to figure out which sort of symmetry the map has
                        // TODO - stop doing this once fairly confident of symmetry
                        int mostSymmetryPossible = 0;
                        MapLocation enemyHqLoc = robots[j].getLocation();
                        for (int i = -1; ++i < hqCount; ) {
                            mostSymmetryPossible |= MapLocationUtil.getSymmetriesBetween(ourHqLocs[i], enemyHqLoc);
                        }
                        Comms.updateSymmetry(mostSymmetryPossible);
                    }
                    break;
                default: // TODO - sense other robots
                    break;
            }
        }
    }

    static void scanWells(RobotController rc) throws GameActionException {
        WellInfo[] wells = rc.senseNearbyWells();
        MapLocation selfLoc = rc.getLocation();

        for (WellInfo well : wells) {
            MapLocation wellLoc = well.getMapLocation();
            ResourceType wellType = well.getResourceType();
            if (!knownWellLocs.contains(wellLoc)) {
                knownWellLocs.add(wellLoc);
            }
            int wellDistSq = selfLoc.distanceSquaredTo(wellLoc);

            MapLocation closestWellLoc = nearestADWell;
            MapLocation secondClosestWellLoc = secondNearestADWell;
            int closestWellDistSq = nearestADWellDistSq;
            int secondClosestWellDistSq = secondNearestADWellDistSq;

            switch (wellType) {
                case ADAMANTIUM:
                    closestWellLoc = nearestADWell;
                    closestWellDistSq = nearestADWellDistSq;
                    secondClosestWellLoc = secondNearestADWell;
                    secondClosestWellDistSq = secondNearestADWellDistSq;
                    break;
                case MANA:
                    closestWellLoc = nearestMNWell;
                    closestWellDistSq = nearestMNWellDistSq;
                    secondClosestWellLoc = secondNearestMNWell;
                    secondClosestWellDistSq = secondNearestMNWellDistSq;
                    break;
                case ELIXIR:
                    closestWellLoc = nearestEXWell;
                    closestWellDistSq = nearestEXWellDistSq;
                    secondClosestWellLoc = secondNearestEXWell;
                    secondClosestWellDistSq = secondNearestEXWellDistSq;
                    break;
            }

            if (closestWellLoc == null || wellDistSq < closestWellDistSq) {
                updateNearestWell(wellLoc, wellDistSq, wellType);
            }
            else if (secondClosestWellLoc == null ||
                    (wellDistSq >= closestWellDistSq && wellDistSq < secondClosestWellDistSq)) {
                updateSecondNearestWell(wellLoc, wellDistSq, wellType);
            }
        }
    }

    static void updateNearestWell (MapLocation loc, int distSq, ResourceType res) {
        switch (res) {
            case ADAMANTIUM:
                nearestADWell = loc;
                nearestADWellDistSq = distSq;
                break;
            case MANA:
                nearestMNWell = loc;
                nearestMNWellDistSq = distSq;
                break;
            case ELIXIR:
                nearestEXWell = loc;
                nearestEXWellDistSq = distSq;
                break;
        }
    }

    static void updateSecondNearestWell (MapLocation loc, int distSq, ResourceType res) {
        switch (res) {
            case ADAMANTIUM:
                secondNearestADWell = loc;
                secondNearestADWellDistSq = distSq;
                break;
            case MANA:
                secondNearestMNWell = loc;
                secondNearestMNWellDistSq = distSq;
                break;
            case ELIXIR:
                secondNearestEXWell = loc;
                secondNearestEXWellDistSq = distSq;
                break;
        }
    }
    static void updateHealth(RobotController rc) {
        lastRoundHealth = thisRoundHealth;
        thisRoundHealth = rc.getHealth();
    }

    static boolean isHealing(RobotController rc) {
        if (thisRoundHealth > lastRoundHealth) {
            return true;
        }
        return false;
    }
}
