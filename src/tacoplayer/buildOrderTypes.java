package tacoplayer;

import battlecode.common.*;

import static tacoplayer.HeadquartersStrategy.*;
import static tacoplayer.buildBots.*;

public class buildOrderTypes {
    static void initialBuildOrder(RobotController rc) throws GameActionException {
        // build 3 launchers and 2 carriers
        for (int i = 0; i++ < 3; ) {
            if (tryToBuildRobot(rc, RobotType.LAUNCHER)) {
                rc.setIndicatorString("Building a launcher");
                if (RUSH_MODE) {
                    launchersBuilt++;
                }
            }
        }
        for (int i = 0; i++ < 2; ) {
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
            for (int i = 0; i++ < ACTIONS_PER_TURN; ) {
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
            for (int i = 0; i++ < ACTIONS_PER_TURN; ) {
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
            for (int i = 0; i++ < ACTIONS_PER_TURN; ) {
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
            for (int i = 0; i++ < ACTIONS_PER_TURN; ) {
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
            for (int i = 0; i++ < actionsAvailable; ) {
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
                    for (int i = 0; i++ < ACTIONS_PER_TURN; ) {
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
                    for (int i = 0; i++ < ACTIONS_PER_TURN; ) {
                        if (tryToBuildRobot(rc, RobotType.LAUNCHER)) {
                            rc.setIndicatorString("Building a launcher");
                        } else if (tryToBuildRobot(rc, RobotType.CARRIER)) {
                            rc.setIndicatorString("Building a carrier");
                        } else {
                            rc.setIndicatorString("Waiting for resources");
                        }
                    }
                    break;

                default:
                    for (int i = 0; i++ < ACTIONS_PER_TURN; ) {
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
}
