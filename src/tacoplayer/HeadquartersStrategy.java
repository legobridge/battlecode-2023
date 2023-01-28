package tacoplayer;

import battlecode.common.*;

import static tacoplayer.RobotPlayer.*;
import static tacoplayer.BuildOrderTypes.*;
import static tacoplayer.BuildBots.*;
import static tacoplayer.Sensing.*;

public class HeadquartersStrategy {

    /**
     * Run a single turn for a Headquarters.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */

    // TODO: Make Magic Numbers based on Map Size
    // TODO: Update Magic Numbers
    // TODO: Come up with something better than for(int i = 0; i++ < 5; )
    // TODO: ACTIONS_PER_TURN should account for destab and booster effects
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
    static boolean underSiege = false; // flag for whether under siege or not

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
            if (rc.getLocation().isWithinDistanceSquared(closestEnemyHqLoc, 2 * RobotType.HEADQUARTERS.actionRadiusSquared - 1)) {
                isWithinEnemyHQRange = true;
            }
            initialBuildOrder(rc);
        }
        else {
            /** MAGIC NUMBERS USED **/
            if (rc.getLocation().distanceSquaredTo(RobotPlayer.closestEnemyHqLoc) < 2000) {
                rc.setIndicatorString("RUSH");
                RUSH_MODE = true;
            } else {
                rc.setIndicatorString("TURTLE");
                /** MAGIC NUMBERS USED **/
                if (mapSize <= 2500) {
                    MAGIC_AMP_EVERY_NUM_TURNS_TURTLE = 200;
                } else if (mapSize <= 3000) {
                    MAGIC_AMP_EVERY_NUM_TURNS_TURTLE = 150;
                } else {
                    MAGIC_AMP_EVERY_NUM_TURNS_TURTLE = 100;
                }
                RUSH_MODE = false;
            }
            if (RUSH_MODE) {
                lastBuiltAnchor++;
                /** MAGIC NUMBERS USED **/
                if (enemyLauncherCount - 1.5 * ourLauncherCount > 0) {
                    // we are under siege!
                    underSiege = true;
                    rc.setIndicatorString("Under siege!");
                    if (mana >= ACTIONS_PER_TURN * RobotType.LAUNCHER.getBuildCost(ResourceType.MANA)) {
                        int launchersBuiltThisTurn = 0;
                        for (int i = ACTIONS_PER_TURN; --i >= 0; ) {
                            if (tryToBuildRobot(rc, RobotType.LAUNCHER)) {
                                launchersBuiltThisTurn++;
                            }
                        }
                        if (launchersBuiltThisTurn == ACTIONS_PER_TURN) {
                            rc.setIndicatorString("built 5 launchers");
                        } else {
                            // should never ever happen
                            System.out.println("failed under siege");
                        }
                    } else {
                        rc.setIndicatorString("waiting for resources under siege");
                    }
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
                    } else {
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
                } else {
                    // nothing special going on
                    buildBots(rc, RobotType.LAUNCHER, ACTIONS_PER_TURN);
                }
            } else {
                // TURTLE
                lastBuiltAnchor++;
                /** MAGIC NUMBERS USED **/
                if (enemyLauncherCount - 1.5 * ourLauncherCount > 0) {
                    // we are under siege!
                    underSiege = true;
                    rc.setIndicatorString("Under siege!");
                    if (mana >= ACTIONS_PER_TURN * RobotType.LAUNCHER.getBuildCost(ResourceType.MANA)) {
                        int launchersBuiltThisTurn = 0;
                        for (int i = ACTIONS_PER_TURN; --i >= 0; ) {
                            if (tryToBuildRobot(rc, RobotType.LAUNCHER)) {
                                launchersBuiltThisTurn++;
                            }
                        }
                        if (launchersBuiltThisTurn == ACTIONS_PER_TURN) {
                            rc.setIndicatorString("built 5 launchers");
                        } else {
                            // should never ever happen
                            System.out.println("failed under siege");
                        }
                    } else {
                        rc.setIndicatorString("waiting for resources under siege");
                    }
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
                    } else {
                        // couldn't make an amp, lets make something else if we have excess of a resource
                        buildBotsInsteadOfAmp(rc, ad, mana);
                    }
                }
                // try and make an anchor
                /** MAGIC NUMBERS USED **/
                else if (turnCount > MAGIC_NUM_TURNS_TURTLE && lastBuiltAnchor > MAGIC_ANCHOR_NUM_TURNS_TURTLE) {
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
}
