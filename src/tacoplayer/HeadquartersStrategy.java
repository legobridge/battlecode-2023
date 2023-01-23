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
    final static int MAGIC_NUM_TURNS = 200;
    final static int MAGIC_ANCHOR_NUM_TURNS = 100;
    // Number of turns for moving average
    final static int AVERAGE_PERIOD = 30;
    static int[] adQueue = new int[AVERAGE_PERIOD];
    static int[] mnQueue = new int[AVERAGE_PERIOD];
    static int lastBuiltAnchor = 0;
    static int mapSize = 0;
    static void runHeadquarters(RobotController rc) throws GameActionException {
        if (rc.getRoundNum() == 1) {
            Comms.updateHQLocation(rc);
            mapSize = rc.getMapWidth() * rc.getMapHeight();
        }

        // Commands for only the first HQ to do
        if (Comms.isFirstHQ(rc)) {
            // Reset alive counts
            Comms.resetCounts(rc);
        }

        int ad = rc.getResourceAmount(ResourceType.ADAMANTIUM);
        adQueue[RobotPlayer.turnCount % AVERAGE_PERIOD] = ad - adQueue[(RobotPlayer.turnCount - 1) % AVERAGE_PERIOD];
        int mana = rc.getResourceAmount(ResourceType.MANA);
        mnQueue[RobotPlayer.turnCount % AVERAGE_PERIOD] = mana - mnQueue[(RobotPlayer.turnCount - 1) % AVERAGE_PERIOD];

        if (mapSize < SMALL_MAP_THRESH) {
            // RUSH
            if (rc.getRoundNum() == 1) {
                System.out.print("RUSHHHHHHH!");
            }
        }
        else {
            // TURTLE
            if (rc.getRoundNum() == 1) {
                System.out.print("TURTLE UP");
            }
            // if it has been 200 turns, and we see a neutral island make an anchor
            /** MAGIC NUMBERS USED **/
            lastBuiltAnchor++;
            // WHY AREN'T WE GOING IN HERE!?
            if (RobotPlayer.turnCount > MAGIC_NUM_TURNS && lastBuiltAnchor > MAGIC_ANCHOR_NUM_TURNS) {
                // wait for resources and build an anchor
                System.out.print("Hello!");
                rc.setIndicatorString("Trying to build an anchor");
                if (rc.canBuildAnchor(Anchor.STANDARD)) {
                    rc.setIndicatorString("Building an anchor");
                    System.out.print("Building an anchor");
                    rc.buildAnchor(Anchor.STANDARD);
                    lastBuiltAnchor = 0;
                }
                else {
                    for (int i = 0; i++ < 5;) {
                        if (mana > RobotType.LAUNCHER.getBuildCost(ResourceType.MANA) + Anchor.STANDARD.getBuildCost(ResourceType.MANA)) {
                            if (tryToBuildRobot(rc, RobotType.LAUNCHER)) {
                                rc.setIndicatorString("Building a launcher");
                            }
                        } else if (ad > RobotType.CARRIER.getBuildCost(ResourceType.ADAMANTIUM) + Anchor.STANDARD.getBuildCost(ResourceType.ADAMANTIUM)) {
                            if (tryToBuildRobot(rc, RobotType.CARRIER)) {
                                rc.setIndicatorString("Building a carrier");
                            }
                        } else {
                            // wait for resources
                            rc.setIndicatorString("Waiting for resources to build an anchor");
                            System.out.print("Waiting for resources");
                        }
                    }
                }
            }
            else {
                // build bots
                // we have moving average
                if (RobotPlayer.turnCount > AVERAGE_PERIOD) {
                    if (adGetMovingAverage() > mnGetMovingAverage()) {
                        for (int i = 0; i++ < 5;) {
                            if (tryToBuildRobot(rc, RobotType.CARRIER)) {
                                rc.setIndicatorString("Building a carrier");
                            } else if (tryToBuildRobot(rc, RobotType.LAUNCHER)) {
                                rc.setIndicatorString("Building a launcher");
                            } else {
                                rc.setIndicatorString("Waiting for resources");
                            }
                        }
                    }
                    else {
                        for (int i = 0; i++ < 5;) {
                            if (tryToBuildRobot(rc, RobotType.LAUNCHER)) {
                                rc.setIndicatorString("Building a launcher");
                            } else if (tryToBuildRobot(rc, RobotType.CARRIER)) {
                                rc.setIndicatorString("Building a carrier");
                            } else {
                                rc.setIndicatorString("Waiting for resources");
                            }
                        }
                    }
                }
                // we don't have moving average
                else {
                    for (int i = 0; i++ < 5;) {
                        if (tryToBuildRobot(rc, RobotType.CARRIER)) {
                            rc.setIndicatorString("Building a carrier");
                        } else if (tryToBuildRobot(rc, RobotType.LAUNCHER)) {
                            rc.setIndicatorString("Building a launcher");
                        } else {
                            rc.setIndicatorString("Waiting for resources");
                        }
                    }
                }
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

    static float adGetMovingAverage() {
        int totalAd = 0;
        for (int i = 0; i++ < adQueue.length;) {
            totalAd += adQueue[i-1];
        }
        return totalAd / adQueue.length;
    }

    static float mnGetMovingAverage() {
        int totalMn = 0;
        for (int i = 0; i++ < mnQueue.length;) {
            totalMn += mnQueue[i-1];
        }
        return totalMn / mnQueue.length;
    }

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
