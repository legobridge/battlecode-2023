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
    final static int MAGIC_NUM_TURNS_RUSH = 150; // turns to wait before building an anchor in rush mode
    final static int MAGIC_ANCHOR_NUM_TURNS_RUSH = 50; // turns to wait before building another anchor in rush mode
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
    static int maxCarriersSeen = 0;
    static int MAGIC_AMP_NUM_TURNS = 150;
    static int MAGIC_NUM_UPDATE_CARRIER_TURNS = 50;
    static boolean needMoreCarriers = true;
    static int lastAnchorRound = 0;
    static boolean needAnchor = true;


    static void runHeadquarters(RobotController rc) throws GameActionException {
        int actions = 0;
        boolean needAmps = Comms.getPrevRobotCount(rc, RobotType.AMPLIFIER) < hqCount
                && rc.getRoundNum() > MAGIC_AMP_NUM_TURNS;

        // Commands for only the first HQ to do
        if (Comms.isFirstHQ(rc)) {
            // Reset alive counts
            Comms.resetCounts(rc);
        }

        // First round
        if (turnCount == 1) {
            initialBuildOrder(rc);
        }

        // Check how many carriers there are
        if (ourCarrierCount > maxCarriersSeen) {
            maxCarriersSeen = ourCarrierCount;
        }

        // Update need more carriers flag
        if (turnCount % MAGIC_NUM_UPDATE_CARRIER_TURNS == 0) {
            if (maxCarriersSeen > 12) {
                needMoreCarriers = false;
            }
            else {
                needMoreCarriers = true;
            }
        }

        // build anchor
        // If past a certain round and haven't built one in a certain number of turns
        if (rc.getRoundNum() >= MAGIC_NUM_TURNS_RUSH
                && rc.getRoundNum() - lastAnchorRound >= MAGIC_ANCHOR_NUM_TURNS_RUSH
                && !needAmps
                && rc.getNumAnchors(Anchor.STANDARD) == 0) {
            needAnchor = true;
            if (closestNeutralIslandLoc != null) {
                if (rc.canBuildAnchor(Anchor.STANDARD)) {
                    rc.setIndicatorString("Building an anchor");
                    rc.buildAnchor(Anchor.STANDARD);
                    lastAnchorRound = rc.getRoundNum();
                    actions++;
                }
            }
        }
        else {
            needAnchor = false;
        }

        // build amplifiers
        // If past a certain round and there are less amps than HQs
        if (actions < ACTIONS_PER_TURN && needAmps && !needAnchor) {
            if (tryToBuildRobot(rc, RobotType.AMPLIFIER)) {
                rc.setIndicatorString("Building an amplifier");
                actions++;
            }
        }

        // build carriers
        if (needMoreCarriers && !needAmps && !needAnchor) {
            for (int i = 0; i < ACTIONS_PER_TURN - actions; i++) {
                if (tryToBuildRobot(rc, RobotType.CARRIER)) {
                    rc.setIndicatorString("Building a carrier");
                    actions++;
                }
            }
        }

        // build launchers
        if (!needAmps && !needAnchor) {
            for (int i = 0; i < ACTIONS_PER_TURN - actions; i++) {
                if (tryToBuildRobot(rc, RobotType.LAUNCHER)) {
                    rc.setIndicatorString("Building a launcher");
                    actions++;
                }
            }
        }
    }
}
