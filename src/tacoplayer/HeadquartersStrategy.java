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
    // TODO: Make Magic Numbers based on Map Size
    // TODO: Update Magic Numbers
    // TODO: Come up with something better than for(int i = 0; i++ < 5; )

    final static int SMALL_MAP_THRESH = 1000; // threshold deciding if the map is small or not
    final static int MAGIC_NUM_TURNS_TURTLE = 200; // turns to wait before building an anchor in turtle mode
    final static int MAGIC_ANCHOR_NUM_TURNS_TURTLE = 100; // turns to wait before building another anchor in turtle mode
    final static int MAGIC_NUM_TURNS_RUSH = 100; // turns to wait before building an anchor in rush mode
    final static int MAGIC_ANCHOR_NUM_TURNS_RUSH = 50; // turns to wait before building another anchor in rush mode
    final static int MAGIC_AMP_NUM_TURNS = 200; // turns to wait before building amps
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
    static int lastAnchorRound = 0;
    static boolean needMoreCarriers = true;
    static int maxCarriersSeen = 0;
    static int MAGIC_NUM_UPDATE_CARRIER_TURNS = 50;

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
            // initially build 3 launchers and 2 carriers
            initialBuildOrder(rc);
        }

        // Check how many carriers there are
        if (Sensing.ourCarrierCount > maxCarriersSeen) {
            maxCarriersSeen = Sensing.ourCarrierCount;
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
                && !needAmps) {
            if (Comms.getNumNeutralIslands(rc) > 0) {
                if (rc.canBuildAnchor(Anchor.STANDARD)) {
                    rc.setIndicatorString("Building an anchor");
                    rc.buildAnchor(Anchor.STANDARD);
                    lastAnchorRound = rc.getRoundNum();
                    actions++;
                }
            }
        }

        // build amplifiers
        // If past a certain round and there are less amps than HQs
        if (actions < ACTIONS_PER_TURN && needAmps) {
            if (tryToBuildRobot(rc, RobotType.AMPLIFIER)) {
                rc.setIndicatorString("Building an amplifier");
                actions++;
            }
        }

        // build carriers
        if (needMoreCarriers && !needAmps) {
            for (int i = 0; i < ACTIONS_PER_TURN - actions; i++) {
                if (tryToBuildRobot(rc, RobotType.CARRIER)) {
                    rc.setIndicatorString("Building a carrier");
                    actions++;
                }
            }
        }

        // build launchers
        if (!needAmps) {
            for (int i = 0; i < ACTIONS_PER_TURN - actions; i++) {
                if (tryToBuildRobot(rc, RobotType.LAUNCHER)) {
                    rc.setIndicatorString("Building a launcher");
                    actions++;
                }
            }
        }
    }
}