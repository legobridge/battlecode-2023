package tacoplayer;

import battlecode.common.*;

import static tacoplayer.RobotPlayer.*;
import static tacoplayer.buildOrderTypes.*;
import static tacoplayer.buildBots.*;

public class HeadquartersStrategy {

    /**
     * Run a single turn for a Headquarters.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */

    // TODO: Make Magic Numbers based on Map Size
    // TODO: Update Magic Numbers
    // TODO: Come up with something better than for(int i = 0; i++ < 5; )

    final static int SMALL_MAP_THRESH = 1000; // threshold deciding if the map is small or not
    final static int MAGIC_NUM_TURNS_TURTLE = 200; // turns to wait before building an anchor in turtle mode
    final static int MAGIC_ANCHOR_NUM_TURNS_TURTLE = 100; // turns to wait before building another anchor in turtle mode
    final static int MAGIC_NUM_TURNS_RUSH = 500; // turns to wait before building an anchor in rush mode
    final static int MAGIC_ANCHOR_NUM_TURNS_RUSH = 200; // turns to wait before building another anchor in rush mode
    final static int MAGIC_AMP_NUM_TURNS_RUSH = 500; // turns to wait before building amps in rush mode
    final static int MAGIC_AMP_NUM_TURNS_TURTLE = 300; // turn to wait before building amps in turtle mode
    final static int MAGIC_AMP_EVERY_NUM_TURNS_RUSH = 200; // turns to wait before building another set of amps in rush mode
    static int MAGIC_AMP_EVERY_NUM_TURNS_TURTLE; // turns to wait before building another set of amps in turtle mode
    final static int AVERAGE_PERIOD = 30; // turns stored to calc moving average of resources
    static int[] adQueue = new int[AVERAGE_PERIOD]; // array to store ad production
    static int[] mnQueue = new int[AVERAGE_PERIOD]; // array to store mana production
    final static double AD_CRITICAL_LOW_THRESH = 0.3;
    static int lastBuiltAnchor = 0; // turn counts passed since building the last anchor
    final static int ACTIONS_PER_TURN = 5; // total 10 cooldowns / 2 per action = 5
    static int carriersBuilt = 0;
    final static int MAX_CARRIERS_BUILT = 50; // max carriers built in rush mode
    static int launchersBuilt = 0;
    final static int MIN_LAUNCHERS_BUILT = 100; // minimum launchers built in rush mode
    static boolean RUSH_MODE = false; // flag for whether to rush or not
    static ResourceType resourceNeeded = ResourceType.MANA; // resource type needed
    static boolean isWithinEnemyHQRange = false; // are we in the action radius of an enemyHQ?
    static MapLocation nearestEnemyHQLoc; // nearest enemy HQ
    static MapLocation myLoc; // my location

    static void runHeadquarters(RobotController rc) throws GameActionException {

        // Commands for only the first HQ to do
        if (Comms.isFirstHQ(rc)) {
            // Reset alive counts
            Comms.resetCounts(rc);
        }

        int ad = rc.getResourceAmount(ResourceType.ADAMANTIUM);
        adQueue[turnCount % AVERAGE_PERIOD] = ad - adQueue[(turnCount - 1) % AVERAGE_PERIOD];
        int mana = rc.getResourceAmount(ResourceType.MANA);
        mnQueue[turnCount % AVERAGE_PERIOD] = mana - mnQueue[(turnCount - 1) % AVERAGE_PERIOD];

        if (rc.getRoundNum() == 1) {
            // TODO: Use closestEnemyHQLoc once it is fixed
            myLoc = rc.getLocation();
            RobotInfo[] enemies = rc.senseNearbyRobots(-1, theirTeam);
            for (int i = 0; i++ < enemies.length; ) {
                if (enemies[i - 1].getType() == RobotType.HEADQUARTERS) {
                    if (myLoc.isWithinDistanceSquared(enemies[i - 1].getLocation(), 2*RobotType.HEADQUARTERS.actionRadiusSquared - 1)) {
                        isWithinEnemyHQRange = true;
                        nearestEnemyHQLoc = enemies[i - 1].getLocation();
                    }
                }
            }
        }

        if (mapSize < SMALL_MAP_THRESH) {
            // RUSH
            if (rc.getRoundNum() == 1) {
                System.out.print("RUSHHHHHHH!");
                RUSH_MODE = true;
            }
            lastBuiltAnchor++;
            if (turnCount == 1) {
                // initially build 4 launchers and 1 carriers
                initialBuildOrder(rc);
            }
            // try make amps
            /** MAGIC NUMBERS USED **/
            else if (turnCount >= MAGIC_AMP_NUM_TURNS_RUSH
                    && (Comms.getPrevRobotCount(rc, RobotType.AMPLIFIER) < hqCount || turnCount % MAGIC_AMP_EVERY_NUM_TURNS_RUSH == 0)) {
                rc.setIndicatorString("Trying to build am amplifier");
                if (tryToBuildRobot(rc, RobotType.AMPLIFIER)) {
                    rc.setIndicatorString("Building an amplifier");
                    // use rest of the actions
                    buildBots(rc, RobotType.LAUNCHER, ACTIONS_PER_TURN - 1);
                }
                else {
                    // couldn't make an amp, lets make something else if we have excess of a resource
                    buildBotsInsteadOfAmp(rc, ad, mana);
                }
            }
            /** MAGIC NUMBERS USED **/
            else if (turnCount > MAGIC_NUM_TURNS_RUSH
                    && lastBuiltAnchor > MAGIC_ANCHOR_NUM_TURNS_RUSH) {
                // wait for resources and build an anchor
                rc.setIndicatorString("Trying to build an anchor");
                if (rc.canBuildAnchor(Anchor.STANDARD)) {
                    rc.setIndicatorString("Building an anchor");
                    rc.buildAnchor(Anchor.STANDARD);
                    lastBuiltAnchor = 0;
                    // use rest of the actions
                    buildBots(rc, RobotType.LAUNCHER, ACTIONS_PER_TURN - 1);
                } else {
                    buildBotsInsteadOfAnchor(rc, ad, mana);
                }
            }
            else {
                // nothing special going on
                buildBots(rc, RobotType.LAUNCHER, ACTIONS_PER_TURN);
            }
        }

        else {
            // TURTLE
            if (rc.getRoundNum() == 1) {
                System.out.print("TURTLE UP");
                if (mapSize <= 2500) {
                    MAGIC_AMP_EVERY_NUM_TURNS_TURTLE = 200;
                } else if (mapSize <= 3000) {
                    MAGIC_AMP_EVERY_NUM_TURNS_TURTLE = 150;
                } else {
                    MAGIC_AMP_EVERY_NUM_TURNS_TURTLE = 100;
                }
            }
            lastBuiltAnchor++;
            if (turnCount == 1) {
                // make 4 launchers and 1 carriers
                initialBuildOrder(rc);
            }
            // try make amps
            /** MAGIC NUMBERS USED **/
            else if (turnCount >= MAGIC_AMP_NUM_TURNS_TURTLE
                    && (Comms.getPrevRobotCount(rc, RobotType.AMPLIFIER) < hqCount || turnCount % MAGIC_AMP_EVERY_NUM_TURNS_TURTLE == 0)) {
                rc.setIndicatorString("Trying to build am amplifier");
                if (tryToBuildRobot(rc, RobotType.AMPLIFIER)) {
                    rc.setIndicatorString("Building an amplifier");
                    // use rest of the actions
                    buildBots(rc, RobotType.LAUNCHER, ACTIONS_PER_TURN - 1);
                }
                else {
                    // couldn't make an amp, lets make something else if we have excess of a resource
                    buildBotsInsteadOfAmp(rc, ad, mana);
                }
            }
            // try and make an anchor
            /** MAGIC NUMBERS USED **/
            else if (turnCount > MAGIC_NUM_TURNS_TURTLE && lastBuiltAnchor > MAGIC_ANCHOR_NUM_TURNS_TURTLE && Comms.getNumNeutralIslands(rc) > 0) {
                // wait for resources and build an anchor
                rc.setIndicatorString("Trying to build an anchor");
                if (rc.canBuildAnchor(Anchor.STANDARD)) {
                    rc.setIndicatorString("Building an anchor");
                    rc.buildAnchor(Anchor.STANDARD);
                    lastBuiltAnchor = 0;
                    // use rest of the actions
                    buildBots(rc, RobotType.LAUNCHER, ACTIONS_PER_TURN - 1);
                } else {
                    // make other bots if we have excess resources
                    buildBotsInsteadOfAnchor(rc, ad, mana);
                }
            } else {
                // build bots
                // we have moving average
                if (turnCount > AVERAGE_PERIOD) {
                    buildBotsWithMovingAverage(rc);
                }
                // we don't have moving average
                else {
                    buildBots(rc, RobotType.CARRIER, ACTIONS_PER_TURN);
                }
            }
        }
    }
}
