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

    // TODO: "Maximize Robots Made in a turn"
    // TODO: Implement Rush
    // TODO: Make Magic Numbers based on Map Size
    // TODO: Update Magic Numbers
    // TODO: Incorporate Moving Averages
    // TODO: Come up with something better than for(int i = 0; i++ < 5; )

    final static int SMALL_MAP_THRESH = 1000; // threshold deciding if the map is small or not
    final static int MAGIC_NUM_TURNS_TURTLE = 200; // turns to wait before building an anchor in turtle mode
    final static int MAGIC_ANCHOR_NUM_TURNS_TURTLE = 100; // turns to wait before building another anchor in turtle mode
    final static int MAGIC_NUM_TURNS_RUSH = 500; // turns to wait before building an anchor in rush mode
    final static int MAGIC_ANCHOR_NUM_TURNS_RUSH = 200; // turns to wait before building another anchor in rush mode
    final static int MAGIC_AMP_NUM_TURNS = 500; // turns to wait before building amps
    final static int MAGIC_AMP_EVERY_NUM_TURNS = 100; // turns to wait before building another set of amps
    final static int AVERAGE_PERIOD = 30; // turns stored to calc moving average of resources
    static int[] adQueue = new int[AVERAGE_PERIOD]; // array to store ad production
    static int[] mnQueue = new int[AVERAGE_PERIOD]; // array to store mana production
    static int lastBuiltAnchor = 0; // turn counts passed since building the last anchor
    final static int ACTIONS_PER_TURN = 5; // total 10 cooldowns / 2 per action = 5
    static int carriersBuilt = 0;
    final static int MAX_CARRIERS_BUILT = 50; // max carriers built in rush mode
    static int launchersBuilt = 0;
    final static int MIN_LAUNCHERS_BUILT = 100; // minimum launchers built in rush mode
    static boolean RUSH_MODE = false; // flag for whether to rush or not

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

        if (mapSize < SMALL_MAP_THRESH) {
            // RUSH
            if (rc.getRoundNum() == 1) {
                System.out.print("RUSHHHHHHH!");
                RUSH_MODE = true;
            }
            lastBuiltAnchor++;
            if (turnCount == 1) {
                // initially build 3 launchers and 2 carriers
                initialBuildOrder(rc);
            }
            // try make amps
            /** MAGIC NUMBERS USED **/
            else if (turnCount >= MAGIC_AMP_NUM_TURNS
                    && Comms.getPrevRobotCount(rc, RobotType.AMPLIFIER) < hqCount) {
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
            }
            lastBuiltAnchor++;
            if (turnCount == 1) {
                // make 3 launchers and 2 carriers
                initialBuildOrder(rc);
            }
            // try make amps
            /** MAGIC NUMBERS USED **/
            else if (turnCount >= MAGIC_AMP_NUM_TURNS
                    && turnCount % MAGIC_AMP_EVERY_NUM_TURNS == 0) {
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

    static void buildBotsWithMovingAverage(RobotController rc) throws GameActionException {
        float adMovingAverageDividedCarrierCost = adGetMovingAverage() / RobotType.CARRIER.getBuildCost(ResourceType.ADAMANTIUM);
        float mnMovingAverageDividedLauncherCost = mnGetMovingAverage() / RobotType.LAUNCHER.getBuildCost(ResourceType.MANA);
        float ratioAdMn = adMovingAverageDividedCarrierCost / mnMovingAverageDividedLauncherCost;
        if (ratioAdMn > 1) {
            int carriersToBuild = Math.round((float) ACTIONS_PER_TURN / ratioAdMn);
            int launchersToBuild = ACTIONS_PER_TURN - carriersToBuild;
            int remainingActions = ACTIONS_PER_TURN;
            for (int i = 0; i++ < launchersToBuild; ) {
                if (tryToBuildRobot(rc, RobotType.LAUNCHER)) {
                    rc.setIndicatorString("Building a launcher");
                    remainingActions--;
                }
                else {
                    rc.setIndicatorString("Not enough resources to build a launcher");
                }
            }
            for (int i = 0; i++ < carriersToBuild; ) {
                if (tryToBuildRobot(rc, RobotType.CARRIER)) {
                    rc.setIndicatorString("Building a carrier");
                    remainingActions--;
                }
                else {
                    rc.setIndicatorString("Not enough resources to build a carrier");
                }
            }
            buildBots(rc, RobotType.LAUNCHER, remainingActions);
        }
        else {
            buildBots(rc, RobotType.LAUNCHER, ACTIONS_PER_TURN);
        }
    }

    static float adGetMovingAverage() {
        int totalAd = 0;
        for (int i = 0; i++ < adQueue.length; ) {
            totalAd += adQueue[i - 1];
        }
        return (float) totalAd / adQueue.length;
    }

    static float mnGetMovingAverage() {
        int totalMn = 0;
        for (int i = 0; i++ < mnQueue.length; ) {
            totalMn += mnQueue[i - 1];
        }
        return (float) totalMn / mnQueue.length;
    }
}
