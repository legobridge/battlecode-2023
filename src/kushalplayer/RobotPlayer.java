package kushalplayer;

import battlecode.common.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * RobotPlayer is the class that describes your main robot strategy.
 * The run() method inside this class is like your main function: this is what we'll call once your robot
 * is created!
 */
public strictfp class RobotPlayer {

    /**
     * We will use this variable to count the number of turns this robot has been alive.
     * You can use static variables like this to save any information you want. Keep in mind that even though
     * these variables are static, in Battlecode they aren't actually shared between your robots.
     */
    static int turnCount = 0;

    /**
     * A random number generator.
     * We will use this RNG to make some random moves. The Random class is provided by the java.util.Random
     * import at the top of this file. Here, we *seed* the RNG with a constant number (6147); this makes sure
     * we get the same sequence of numbers every time this code is run. This is very useful for debugging!
     */
    static final Random rng = new Random(6147);

    /**
     * Array containing all the possible movement directions. (excludes CENTER)
     */
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

    static Team ourTeam;
    static Team theirTeam;

    static int islandCount;

    static int mapHeight;
    static int mapWidth;

    static int[][] map;

    // TODO - Convert all ArrayLists to arrays later for bytecode optimization
    static MapLocation closestHqLoc;
    static ArrayList<MapLocation> knownHqLocs = new ArrayList<>();

    static MapLocation closestWellLoc;
    static ArrayList<MapLocation> knownWellLocs = new ArrayList<>();

    public static MapLocation closestIslandLoc;
    static ArrayList<MapLocation> knownIslandLocs = new ArrayList<>();

    /**
     * run() is the method that is called when a robot is instantiated in the Battlecode world.
     * It is like the main function for your robot. If this method returns, the robot dies!
     *
     * @param rc The RobotController object. You use it to perform actions from this robot, and to get
     *           information on its current status. Essentially your portal to interacting with the world.
     **/
    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {

        // Hello world! Standard output is very useful for debugging.
        // Everything you say here will be directly viewable in your terminal when you run a match!
        System.out.println("I'm a " + rc.getType() + " and I just got created! I have health " + rc.getHealth());

        // You can also use indicators to save debug notes in replays.
        rc.setIndicatorString("Hello world!");

        // Set game constants
        setGameConstants(rc);

        //noinspection InfiniteLoopStatement
        while (true) {

            turnCount += 1;  // We have now been alive for one more turn!

            int startRoundNum = rc.getRoundNum(); // The current round number

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode.
            try {

                // Scan surroundings
                scanHQ(rc);
                scanWells(rc);
                scanIslands(rc);

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
                    case BOOSTER:
                        // TODO
                        break;
                    case DESTABILIZER:
                        // TODO
                        break;
                    case AMPLIFIER:
                        // TODO
                        break;
                }

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
        map = new int[mapWidth][mapHeight];
    }

    static void moveRandom(RobotController rc) throws GameActionException {
        Direction dir = directions[rng.nextInt(directions.length)];
        if (rc.canMove(dir)) rc.move(dir);
    }

    static void moveTowards(RobotController rc, MapLocation loc) throws GameActionException {
        Direction dir = rc.getLocation().directionTo(loc);
        if (rc.canMove(dir)) {
            rc.move(dir);
        }
        else {
            moveRandom(rc);
        }
    }

    static void scanHQ(RobotController rc) throws GameActionException {
        RobotInfo[] robots = rc.senseNearbyRobots();
        MapLocation selfLoc = rc.getLocation();
        int closestHqDistSq = MAX_MAP_DIST_SQ;

        for (RobotInfo robot : robots) {
            if (robot.getTeam() == ourTeam && robot.getType() == RobotType.HEADQUARTERS) {
                MapLocation hqLoc = robot.getLocation();
                if (!knownHqLocs.contains(hqLoc)) {
                    knownHqLocs.add(hqLoc);
                }
                int hqDistSq = selfLoc.distanceSquaredTo(hqLoc);
                if (closestHqLoc == null || hqDistSq < closestHqDistSq) {
                    closestHqLoc = hqLoc;
                    closestHqDistSq = hqDistSq;
                }
            }
        }
    }

    static void scanWells(RobotController rc) throws GameActionException {
        WellInfo[] wells = rc.senseNearbyWells();
        MapLocation selfLoc = rc.getLocation();
        int closestWellDistSq = MAX_MAP_DIST_SQ;

        for (WellInfo well : wells) {
            MapLocation wellLoc = well.getMapLocation();
            if (!knownWellLocs.contains(wellLoc)) {
                knownWellLocs.add(wellLoc);
            }
            int wellDistSq = selfLoc.distanceSquaredTo(wellLoc);
            if (closestWellLoc == null || wellDistSq < closestWellDistSq) {
                closestWellLoc = wellLoc;
                closestWellDistSq = wellDistSq;
            }
        }
    }

    static void scanIslands(RobotController rc) throws GameActionException {
        int[] ids = rc.senseNearbyIslands();
        MapLocation selfLoc = rc.getLocation();
        int closestIslandDistSq = MAX_MAP_DIST_SQ;

        for (int id : ids) {
            if (rc.senseTeamOccupyingIsland(id) == Team.NEUTRAL) {
                MapLocation[] islandLocs = rc.senseNearbyIslandLocations(id);
                for (MapLocation islandLoc : islandLocs) {
                    if (!knownIslandLocs.contains(islandLoc)) {
                        knownIslandLocs.add(islandLoc);
                    }
                    int islandDistSq = selfLoc.distanceSquaredTo(islandLoc);
                    if (closestIslandLoc == null || islandDistSq < closestIslandDistSq) {
                        closestIslandLoc = islandLoc;
                        closestIslandDistSq = islandDistSq;
                    }
                }
            }
        }
    }
}
