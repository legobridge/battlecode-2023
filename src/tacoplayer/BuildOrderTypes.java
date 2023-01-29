package tacoplayer;

import battlecode.common.*;

import static tacoplayer.HeadquartersStrategy.*;
import static tacoplayer.BuildBots.*;

public class BuildOrderTypes {
    static void initialBuildOrder(RobotController rc) throws GameActionException {
        // build 4 launchers and 1 carriers
        for (int i = ACTIONS_PER_TURN - 1; --i >= 0; ) {
            if (tryToBuildRobot(rc, RobotType.LAUNCHER)) {
                rc.setIndicatorString("Building a launcher");
                if (RUSH_MODE) {
                    launchersBuilt++;
                }
            }
        }
        for (int i = 1; --i >= 0; ) {
            if (tryToBuildRobot(rc, RobotType.CARRIER)) {
                rc.setIndicatorString("Building a carrier");
                if (RUSH_MODE) {
                    carriersBuilt++;
                }
            }
        }
    }

    static void buildBotsInsteadOfAmp(RobotController rc, int ad, int mana) throws GameActionException {
        if (RUSH_MODE) {
            for (int i = ACTIONS_PER_TURN; --i >= 0; ) {
                if (mana > RobotType.LAUNCHER.getBuildCost(ResourceType.MANA) + RobotType.AMPLIFIER.getBuildCost(ResourceType.MANA)) {
                    if (tryToBuildRobot(rc, RobotType.LAUNCHER)) {
                        rc.setIndicatorString("Building a launcher");
                        launchersBuilt++;
                    }
                    /** MAGIC NUMBER USED **/
                } else if (ad > RobotType.CARRIER.getBuildCost(ResourceType.ADAMANTIUM) + RobotType.AMPLIFIER.getBuildCost(ResourceType.ADAMANTIUM)
                        && (carriersBuilt < MAX_CARRIERS_BUILT || launchersBuilt > MIN_LAUNCHERS_BUILT)) {
                    if (tryToBuildRobot(rc, RobotType.CARRIER)) {
                        rc.setIndicatorString("Building a carrier");
                        carriersBuilt++;
                    }
                } else {
                    // wait for resources
                    rc.setIndicatorString("Waiting for resources for amp");
                }
            }
        }

        else {
            for (int i = ACTIONS_PER_TURN; --i >= 0; ) {
                if (mana > RobotType.LAUNCHER.getBuildCost(ResourceType.MANA) + RobotType.AMPLIFIER.getBuildCost(ResourceType.MANA)) {
                    if (tryToBuildRobot(rc, RobotType.LAUNCHER)) {
                        rc.setIndicatorString("Building a launcher");
                    }
                    /** MAGIC NUMBER USED **/
                } else if (ad > RobotType.CARRIER.getBuildCost(ResourceType.ADAMANTIUM) + RobotType.AMPLIFIER.getBuildCost(ResourceType.ADAMANTIUM)) {
                    if (tryToBuildRobot(rc, RobotType.CARRIER)) {
                        rc.setIndicatorString("Building a carrier");
                    }
                } else {
                    // wait for resources
                    rc.setIndicatorString("Waiting for resources for amp");
                }
            }
        }
    }

    static void buildBotsInsteadOfAnchor(RobotController rc, int ad, int mana) throws GameActionException {
        if (RUSH_MODE) {
            for (int i = ACTIONS_PER_TURN; --i >= 0; ) {
                if (mana > RobotType.LAUNCHER.getBuildCost(ResourceType.MANA) + Anchor.STANDARD.getBuildCost(ResourceType.MANA)) {
                    if (tryToBuildRobot(rc, RobotType.LAUNCHER)) {
                        rc.setIndicatorString("Building a launcher");
                        launchersBuilt++;
                    }
                    /** MAGIC NUMBER USED **/
                } else if (ad > RobotType.CARRIER.getBuildCost(ResourceType.ADAMANTIUM) + Anchor.STANDARD.getBuildCost(ResourceType.ADAMANTIUM)
                        && (carriersBuilt < MAX_CARRIERS_BUILT || launchersBuilt > MIN_LAUNCHERS_BUILT)) {
                    if (tryToBuildRobot(rc, RobotType.CARRIER)) {
                        rc.setIndicatorString("Building a carrier");
                        carriersBuilt++;
                    }
                } else {
                    // wait for resources
                    rc.setIndicatorString("Waiting for resources for anchor");
                }
            }
        }

        else {
            for (int i = ACTIONS_PER_TURN; --i >= 0; ) {
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
                    rc.setIndicatorString("Waiting for resources");
                }
            }
        }
    }

    static void buildBots(RobotController rc, RobotType botPriority, int actionsAvailable) throws GameActionException {
        if (RUSH_MODE) {
            // RUSHHHHHHH!
            for (int i = actionsAvailable; --i >= 0; ) {
                if (tryToBuildRobot(rc, RobotType.LAUNCHER)) {
                    rc.setIndicatorString("Building a launcher");
                    launchersBuilt++;
                    /** MAGIC NUMBER USED **/
                } else if (carriersBuilt < MAX_CARRIERS_BUILT || launchersBuilt > MIN_LAUNCHERS_BUILT) {
                    if (tryToBuildRobot(rc, RobotType.CARRIER)) {
                        rc.setIndicatorString("Building a carrier");
                        carriersBuilt++;
                    }
                } else {
                    rc.setIndicatorString("Waiting for resources");
                }
            }
        }
        else {
            // TURTLE UP!
            switch (botPriority) {
                case CARRIER:
                    for (int i = ACTIONS_PER_TURN; --i >= 0; ) {
                        if (tryToBuildRobot(rc, RobotType.CARRIER)) {
                            rc.setIndicatorString("Building a carrier");
                        } else if (tryToBuildRobot(rc, RobotType.LAUNCHER)) {
                            rc.setIndicatorString("Building a launcher");
                        } else {
                            rc.setIndicatorString("Waiting for resources");
                        }
                    }
                    break;

                case LAUNCHER:
                default :
                    for (int i = ACTIONS_PER_TURN; --i >= 0; ) {
                        if (tryToBuildRobot(rc, RobotType.LAUNCHER)) {
                            rc.setIndicatorString("Building a launcher");
                        } else if (tryToBuildRobot(rc, RobotType.CARRIER)) {
                            rc.setIndicatorString("Building a carrier");
                        } else {
                            rc.setIndicatorString("Waiting for resources");
                        }
                    }
                    break;
            }
        }
    }

//    static void buildBotsWithMovingAverage(RobotController rc) throws GameActionException {
//        float adMovingAverageDividedCarrierCost = adGetMovingAverage() / RobotType.CARRIER.getBuildCost(ResourceType.ADAMANTIUM);
//        float mnMovingAverageDividedLauncherCost = mnGetMovingAverage() / RobotType.LAUNCHER.getBuildCost(ResourceType.MANA);
//        float ratioAdMn = adMovingAverageDividedCarrierCost / mnMovingAverageDividedLauncherCost;
//        if (ratioAdMn > 1) {
//            ResourceType resourceNeeded = ResourceType.MANA; //todo - resolve this
//            int carriersToBuild = Math.round((float) ACTIONS_PER_TURN / ratioAdMn);
//            int launchersToBuild = ACTIONS_PER_TURN - carriersToBuild;
//            int remainingActions = ACTIONS_PER_TURN;
//            for (int i = launchersToBuild; --i >= 0; ) {
//                if (tryToBuildRobot(rc, RobotType.LAUNCHER)) {
//                    rc.setIndicatorString("Building a launcher");
//                    remainingActions--;
//                }
//                else {
//                    rc.setIndicatorString("Not enough resources to build a launcher");
//                }
//            }
//            for (int i = carriersToBuild; --i >= 0; ) {
//                if (tryToBuildRobot(rc, RobotType.CARRIER)) {
//                    rc.setIndicatorString("Building a carrier");
//                    remainingActions--;
//                }
//                else {
//                    rc.setIndicatorString("Not enough resources to build a carrier");
//                }
//            }
//            buildBots(rc, RobotType.LAUNCHER, remainingActions);
//        }
//        else {
//            if (ratioAdMn < AD_CRITICAL_LOW_THRESH) {
//                resourceNeeded = ResourceType.ADAMANTIUM;
//            }
//            buildBots(rc, RobotType.LAUNCHER, ACTIONS_PER_TURN);
//        }
//    }
//    static float adGetMovingAverage() {
//        int totalAd = 0;
//        for (int i = -1; ++i < adQueue.length; ) {
//            totalAd += adQueue[i];
//        }
//        return (float) totalAd / adQueue.length;
//    }
//
//    static float mnGetMovingAverage() {
//        int totalMn = 0;
//        for (int i = -1; ++i < mnQueue.length; ) {
//            totalMn += mnQueue[i];
//        }
//        return (float) totalMn / mnQueue.length;
//    }
}
