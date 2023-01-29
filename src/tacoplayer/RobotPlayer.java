package tacoplayer;

import battlecode.common.*;

import java.util.ArrayList;
import java.util.Random;

public strictfp class RobotPlayer {

    static final Random rng = new Random(6147);

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

    static MapLocation closestHqLoc;
    static MapLocation closestEnemyHqLoc;
    static MapLocation[] ourHqLocs = new MapLocation[4];
    static MapLocation[] enemyHqLocs = new MapLocation[12];

    static ArrayList<MapLocation> knownWellLocs = new ArrayList<>();

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
    static boolean runawayMode = false;
    static boolean attackMode = false;


    static double sumReadBc = 0;
    static double sumSensingBc = 0;
    static double sumTurnBc = 0;
    static double sumWriteBc = 0;

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
        Pathing.bfsPathing = new BFSPathing(rc);

        // Upload own location if HQ
        if (rc.getType() == RobotType.HEADQUARTERS) {
            Comms.putHqLocationOnline(rc);
        }
        Comms.readOurHqLocs(rc);

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
            int bc1 = Clock.getBytecodesLeft();
            // Read from comms array
            Comms.readAndStoreFromSharedArray(rc);

            int bc2 = Clock.getBytecodesLeft();
            // Scan surroundings
            Sensing.scanRobots(rc);
            Sensing.scanIslands(rc);
            Sensing.scanWells(rc);
            Comms.updateEnemyHqLocs(rc);

            if (rc.getType() != RobotType.HEADQUARTERS) {
                closestHqLoc = MapLocationUtil.getClosestMapLocEuclidean(rc, ourHqLocs);
            }

            int bc3 = Clock.getBytecodesLeft();
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
            int bc4 = Clock.getBytecodesLeft();
            // Put information in shared array at the end of each round
            // TODO - put behind bytecode check
            if (Sensing.ourAmplifierCount == 0) {
                Comms.putSymmetryOnline(rc);
                Comms.putIslandsOnline(rc);
            }
            int bc5 = Clock.getBytecodesLeft();
//            sumReadBc += bc1 - bc2;
//            sumSensingBc += bc2 - bc3;
//            sumTurnBc += bc3 - bc4;
//            sumWriteBc += bc4 - bc5;
//            if (turnCount > 100 && turnCount % 50 == 0) {
//                System.out.println("Read: " + sumReadBc / turnCount);
//                System.out.println("Sensing: " + sumSensingBc / turnCount);
//                System.out.println("Turn: " + sumTurnBc / turnCount);
//                System.out.println("Write: " + sumWriteBc / turnCount);
//            }
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
    }
    static void updateHealth(RobotController rc) {
        lastRoundHealth = thisRoundHealth;
        thisRoundHealth = rc.getHealth();
    }

    static boolean isHealing() {
        return thisRoundHealth > lastRoundHealth;
    }
}
