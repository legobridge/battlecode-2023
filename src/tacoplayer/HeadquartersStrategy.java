package tacoplayer;

import battlecode.common.*;

public class HeadquartersStrategy {

    /**
     * Run a single turn for a Headquarters.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */

    // TODO: Need Neutral Islands Numbers from Comms
    // TODO: Implement Rush
    // TODO: Make Magic Numbers based on Map Size
    // TODO: Update Magic Numbers
    // TODO: Incorporate Moving Averages

    final static int SMALL_MAP_THRESH = 1000;
    final static int MAGIC_NUM_TURNS = 50;
    final static int MAGIC_NUM_CARRIERS = 20;
    final static int MAGIC_NUM_AMPLIFIERS = 10;
    final static int MAGIC_NUM_LAUNCHERS = 20;
    final static int MAGIC_TURNS_BUFFER = 50;
//    final static int AVERAGE_PERIOD = 20;
    static RobotType[] buildOrderTurtle = {
            RobotType.CARRIER,
            RobotType.CARRIER,
            RobotType.LAUNCHER,
            RobotType.LAUNCHER,
            RobotType.AMPLIFIER };

    static int currentBuildOrderIndexTurtle = 0;
//    static int[] adQueue = new int[AVERAGE_PERIOD];
//    static int[] mnQueue = new int[AVERAGE_PERIOD];
    static int lastBuiltAnchor = 0;
    static int ampsBuilt = 0;
    static void runHeadquarters(RobotController rc) throws GameActionException {
        if (rc.getRoundNum() == 1) {
            Comms.updateHQLocation(rc);
        }

        // Commands for only the first HQ to do
        if (Comms.isFirstHQ(rc)) {
            // Reset alive counts
            Comms.resetCounts(rc);
        }

        int mapSize = rc.getMapWidth() * rc.getMapHeight();

        int numCarriers = Comms.getPrevRobotCount(rc, RobotType.CARRIER);
        int numLaunchers = Comms.getPrevRobotCount(rc, RobotType.LAUNCHER);
        int numAmplifiers = Comms.getPrevRobotCount(rc, RobotType.AMPLIFIER);
        int numHQs = Comms.getHQLocations(rc).length;

        if (mapSize < SMALL_MAP_THRESH) {
            // RUSH
            if (rc.getRoundNum() == 1) {
                System.out.print("RUSHHHHHHH!");
            }
            if (numCarriers/numHQs < 4) /**MAGIC NUMBER ALERT**/ {
                tryToBuildRobot(rc, RobotType.CARRIER);
            } else if (numAmplifiers/numHQs < 1) /**MAGIC NUMBER ALERT**/ {
                tryToBuildRobot(rc, RobotType.AMPLIFIER);
            } else if (RobotPlayer.turnCount >= MAGIC_NUM_TURNS && numLaunchers > MAGIC_NUM_LAUNCHERS && RobotPlayer.turnCount - lastBuiltAnchor > MAGIC_TURNS_BUFFER) {
                if (tryToBuildAnchor(rc)) {
                    lastBuiltAnchor = RobotPlayer.turnCount;
                }
            } else {
                tryToBuildRobot(rc, RobotType.LAUNCHER);
            }
        }
        else {
            // TURTLE
            if (rc.getRoundNum() == 1) {
                System.out.print("TURTLE UP");
            }
            // go twice through the loop
            if (currentBuildOrderIndexTurtle / buildOrderTurtle.length < 1) /**MAGIC NUM ALERT!**/ {
                // Pick a type of robot to build
                RobotType robotTypeToBuild = buildOrderTurtle[currentBuildOrderIndexTurtle % buildOrderTurtle.length];

                // Try to build the robot, if we have enough resources and space around the HQ
                if (tryToBuildRobot(rc, robotTypeToBuild)) {
                    currentBuildOrderIndexTurtle = currentBuildOrderIndexTurtle + 1;
                }
            }
            else {
//                if (RobotPlayer.turnCount >= MAGIC_NUM_TURNS) {
//                    // uncomment once we have Comms
//                    // int numNeutralIslands = Comms.getNeutralIslandCount;
//                    // if (numNeutralIslands && numLauncher > 2 * numNeutralIslands && RobotPlayer.turnCount - lastBuilt > MAGIC_TURNS_BUFFER) {
//                    //  if (tryToBuildAnchor(rc)) {
//                    //      static int lastBuilt = RobotPlayer.turnCount;
//                    //  }
//                    // }
//                    // burn below once we have Comms
//                    if (numLaunchers > MAGIC_NUM_LAUNCHERS && RobotPlayer.turnCount - lastBuiltAnchor > MAGIC_TURNS_BUFFER) {
//                        if (tryToBuildAnchor(rc)) {
//                            lastBuiltAnchor = RobotPlayer.turnCount;
//                            anchorMade = true;
//                        }
//                    }
//                }
                if (numLaunchers > MAGIC_NUM_LAUNCHERS && RobotPlayer.turnCount - lastBuiltAnchor > MAGIC_TURNS_BUFFER) {
                    if (tryToBuildAnchor(rc)) {
                        lastBuiltAnchor = RobotPlayer.turnCount;
                    }
                }
                else if (ampsBuilt < MAGIC_NUM_AMPLIFIERS) {
                    if (tryToBuildRobot(rc, RobotType.AMPLIFIER)) {
                        ampsBuilt++;
                    }
                    else if(!tryToBuildRobot(rc, RobotType.LAUNCHER)) {
                            tryToBuildRobot(rc, RobotType.CARRIER);
                    }
                }
                else {
                    if (!tryToBuildRobot(rc, RobotType.LAUNCHER)) {
                        tryToBuildRobot(rc, RobotType.CARRIER);
                    }
                }
            }

            /**
            if (numCarriers > MAGIC_NUM_CARRIERS) {
                buildOrderTurtle[0] = null;
                buildOrderTurtle[1] = null;
            }
            else {
                if (buildOrderTurtle[0] == null) buildOrderTurtle[0] = RobotType.CARRIER;
                if (buildOrderTurtle[1] == null) buildOrderTurtle[1] = RobotType.CARRIER;
            }

            if (numAmplifiers > MAGIC_NUM_AMPLIFIERS) {
                buildOrderTurtle[4] = null;
            }
            else {
                if (buildOrderTurtle[4] == null) buildOrderTurtle[4] = RobotType.AMPLIFIER;
            }

            // Pick a type of robot to build
            RobotType robotTypeToBuild = buildOrderTurtle[currentBuildOrderIndexTurtle];

            // Try to build the robot, if we have enough resources and space around the HQ
            if (tryToBuildRobot(rc, robotTypeToBuild)) {
                currentBuildOrderIndexTurtle = (currentBuildOrderIndexTurtle + 1) % buildOrderTurtle.length;
                while (buildOrderTurtle[currentBuildOrderIndexTurtle] == null) {
                    currentBuildOrderIndexTurtle = (currentBuildOrderIndexTurtle + 1) % buildOrderTurtle.length;
                }
            }
             **/

        }
//        adQueuePush(rc.getResourceAmount(ResourceType.ADAMANTIUM) - adQueue[0]);
//        mnQueuePush(rc.getResourceAmount(ResourceType.MANA) - mnQueue[0]);
//        if (RobotPlayer.turnCount > 20) {
//            float adMovingAverageDividedCarrierCost = adGetMovingAverage() / RobotType.CARRIER.getBuildCost(ResourceType.ADAMANTIUM);
//            float mnMovingAverageDividedLauncherCost = mnGetMovingAverage() / RobotType.LAUNCHER.getBuildCost(ResourceType.MANA);
//            float ratioAdMn = adMovingAverageDividedCarrierCost / mnMovingAverageDividedLauncherCost;
//            if (floor(ratioAdMn) > 1) {
//              // we are generating enough to create more than 1 carrier for every 1 launcher we create -> adjust build order accordingly
//              // look for more mana
//              // meanwhile create more carriers and flag all carriers to attack when possible?
//            }
//        }
    }

    private static boolean tryToBuildAnchor(RobotController rc) throws GameActionException {
        rc.setIndicatorString("Trying to build an anchor!");
        if (rc.canBuildAnchor(Anchor.STANDARD)) {
            // If we can build an anchor do it!
            rc.setIndicatorString("Building anchor!");
            rc.buildAnchor(Anchor.STANDARD);
            return true;
        }
        return false;
    }

    static boolean tryToBuildRobot(RobotController rc, RobotType robotTypeToBuild) throws GameActionException {
        rc.setIndicatorString("Trying to build a " + robotTypeToBuild);
        for (Direction dir: RobotPlayer.directions) {
            MapLocation candidateBuildLoc = rc.getLocation().add(dir);
            if (rc.canBuildRobot(robotTypeToBuild, candidateBuildLoc)) {
                rc.buildRobot(robotTypeToBuild, candidateBuildLoc);
                return true;
            }
        }
        return false;
    }

//    static void adQueuePush (int ad) {
//        for (int i = 0; i < adQueue.length - 1; i++) {
//            adQueue[i+1] = adQueue[i];
//        }
//        adQueue[0] = ad;
//    }
//
//    static float adGetMovingAverage() {
//        int totalAd = 0;
//        for (int ad : adQueue) {
//            totalAd += ad;
//        }
//        return totalAd / adQueue.length;
//    }
//
//    static void mnQueuePush (int mn) {
//        for (int i = 0; i < mnQueue.length - 1; i++) {
//            mnQueue[i+1] = mnQueue[i];
//        }
//        mnQueue[0] = mn;
//    }
//
//    static float mnGetMovingAverage() {
//        int totalMn = 0;
//        for (int mn : mnQueue) {
//            totalMn += mn;
//        }
//        return totalMn / mnQueue.length;
//    }

/** ---------------------------- OLD CODE ------------------------------------ **/


//    static RobotType[] buildOrder = {
//            RobotType.CARRIER,
//            RobotType.CARRIER,
//            RobotType.LAUNCHER,
//            RobotType.LAUNCHER,
//            RobotType.AMPLIFIER,
//            RobotType.CARRIER};
//
//    static int currentBuildOrderIndex = 0;
//
//    static boolean anchorBuildingMode = false;
//    static void runHeadquarters(RobotController rc) throws GameActionException {
//        anchorBuildingMode = RobotPlayer.turnCount % 50 == 0;
//        if (anchorBuildingMode) {
//            if (tryToBuildAnchor(rc)) {
//                anchorBuildingMode = false;
//            }
//        }
//        else {
//            // Pick a type of robot to build
//            RobotType robotTypeToBuild = buildOrder[currentBuildOrderIndex];
//
//            // Try to build the robot, if we have enough resources and space around the HQ
//            if (tryToBuildRobot(rc, robotTypeToBuild)) {
//                currentBuildOrderIndex = (currentBuildOrderIndex + 1) % buildOrder.length;
//            }
//        }
//    }
//
//    private static boolean tryToBuildAnchor(RobotController rc) throws GameActionException {
//        rc.setIndicatorString("Trying to build an anchor!");
//        if (rc.canBuildAnchor(Anchor.STANDARD)) {
//            // If we can build an anchor do it!
//            rc.buildAnchor(Anchor.STANDARD);
//            return true;
//        }
//        return false;
//    }
//
//    static boolean tryToBuildRobot(RobotController rc, RobotType robotTypeToBuild) throws GameActionException {
//        rc.setIndicatorString("Trying to build a " + robotTypeToBuild);
//        for (Direction dir : RobotPlayer.directions) {
//            MapLocation candidateBuildLoc = rc.getLocation().add(dir);
//            if (rc.canBuildRobot(robotTypeToBuild, candidateBuildLoc)) {
//                rc.buildRobot(robotTypeToBuild, candidateBuildLoc);
//                return true;
//            }
//        }
//        return false;
//    }
}
