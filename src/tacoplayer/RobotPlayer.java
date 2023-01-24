package tacoplayer;

import battlecode.common.*;

import java.util.ArrayList;
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
    public static MapLocation closestNeutralIslandLoc;
    public static MapLocation closestEnemyIslandLoc;
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
    static MapLocation closestWellLoc;
    static ArrayList<MapLocation> knownWellLocs = new ArrayList<>();
    static IslandInfo[] knownIslands = new IslandInfo[GameConstants.MAX_NUMBER_ISLANDS];

    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {

        // Set game constants
        setGameConstants(rc);

        // Initialize Allied HQ Locations
        Comms.initializeAlliedHqLocs(rc);

        // Initialize symmetry with 111 (all symmetries)
        if (Comms.isFirstHQ(rc)) {
            Comms.initializeSymmetry(rc);
        }

        //noinspection InfiniteLoopStatement
        while (true) {

            turnCount += 1;  // We have now been alive for one more turn!

            int startRoundNum = rc.getRoundNum(); // The current round number

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode.
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

                // Signify we've done everything we want to do, thereby ending our turn.
                Clock.yield();
            }
            // End of loop: go back to the top. Clock.yield() has ended, so it's time for another turn!
        }
        // Your code should never reach here (unless it's intentional)! Self-destruction imminent...
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

    static boolean moveTowardsEnemies(RobotController rc) throws GameActionException {
        RobotInfo[] visibleEnemies = rc.senseNearbyRobots(-1, theirTeam);
        if (visibleEnemies.length != 0) {
            MapLocation enemyLocation = averageLoc(visibleEnemies);
            rc.setIndicatorString("Moving towards enemy robot! " + enemyLocation);
            // If you are outside 3/4 the enemy's action radius, move towards it, else move away
            if (visibleEnemies[0].getLocation().distanceSquaredTo(rc.getLocation()) > rc.getType().actionRadiusSquared * 5 / 6) {
                Pathing.moveTowards(rc, enemyLocation);
                return true;
            } else {
                MapLocation ourLoc = rc.getLocation();
                MapLocation runAwayLocation = new MapLocation(ourLoc.x - enemyLocation.x, ourLoc.y - enemyLocation.y);
                Pathing.moveTowards(rc, runAwayLocation);
                return true;
            }
        }
        return false;
    }

    static void moveTowardsEnemies(RobotController rc, int radius) throws GameActionException {
        RobotInfo[] visibleEnemies = rc.senseNearbyRobots(-1, theirTeam);
        if (visibleEnemies.length != 0) {
            MapLocation enemyLocation = averageLoc(visibleEnemies);
            rc.setIndicatorString("Moving towards enemy robot! " + enemyLocation);
            // If you are outside 3/4 the enemy's action radius, move towards it, else move away
            if (visibleEnemies[0].getLocation().distanceSquaredTo(rc.getLocation()) > radius) {
                Pathing.moveTowards(rc, enemyLocation);
            } else {
                MapLocation ourLoc = rc.getLocation();
                MapLocation runAwayLocation = new MapLocation(ourLoc.x - enemyLocation.x, ourLoc.y - enemyLocation.y);
                Pathing.moveTowards(rc, runAwayLocation);
            }
        }
    }

    static MapLocation averageLoc(RobotInfo[] robots) {
        int sumX = 0;
        int sumY = 0;
        for (int i = 0; i < robots.length; i++) {
            MapLocation loc = robots[i].getLocation();
            sumX += loc.x;
            sumY += loc.y;
        }
        return new MapLocation((int) ((float) sumX / robots.length), (int) ((float) sumY / robots.length));

    }

    static void moveTowardsRobots(RobotController rc, RobotInfo[] robots) throws GameActionException {
        MapLocation target = averageLoc(robots);
        Pathing.moveTowards(rc, target);
    }

    static void moveAwayFromRobots(RobotController rc, RobotInfo[] robots) throws GameActionException {
        MapLocation target = averageLoc(robots);
        moveAwayFromLocation(rc, target);
    }

    static void moveAwayFromLocation(RobotController rc, MapLocation loc) throws GameActionException {
        MapLocation robotLoc = rc.getLocation();
        int dx = robotLoc.x - loc.x;
        int dy = robotLoc.y - loc.y;
        MapLocation awayLoc = new MapLocation(robotLoc.x + dx, robotLoc.y + dy);
        Pathing.moveTowards(rc, awayLoc);
    }
}
