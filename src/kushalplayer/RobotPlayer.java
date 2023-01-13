package kushalplayer;

import battlecode.common.*;

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

    static Team ourTeam;
    static Team theirTeam;

    static int mapHeight;
    static int mapWidth;

    static MapLocation lastSeenHeadquarters;

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
        if (rc.getTeam() == Team.A) {
            ourTeam = Team.A;
            theirTeam = Team.B;
        }
        else {
            ourTeam = Team.B;
            theirTeam = Team.A;
        }
        mapHeight = rc.getMapHeight();
        mapWidth = rc.getMapWidth();

        if (lastSeenHeadquarters == null) {
            if (rc.getType() == RobotType.HEADQUARTERS) {
                lastSeenHeadquarters = rc.getLocation();
            }
            RobotInfo[] nearbyRobots = rc.senseNearbyRobots(2, RobotPlayer.ourTeam);
            for (RobotInfo nearbyRobot: nearbyRobots) {
                if (nearbyRobot.type == RobotType.HEADQUARTERS) {
                    lastSeenHeadquarters = nearbyRobot.location;
                }
            }
            if (lastSeenHeadquarters == null) {
                throw new GameActionException(GameActionExceptionType.NO_ROBOT_THERE, "Headquarters not found, wtf");
            }
        }

        //noinspection InfiniteLoopStatement
        while (true) {

            turnCount += 1;  // We have now been alive for one more turn!
            int startRoundNum = rc.getRoundNum(); // The current round number

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode.
            try {
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

}
