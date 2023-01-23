package tacoplayer;

import battlecode.common.*;

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

    final static int SMALL_MAP_THRESH = 1000;
    final static int MAGIC_NUM_TURNS = 200;
    final static int MAGIC_ANCHOR_NUM_TURNS = 100;
    // Number of turns for moving average
    final static int AVERAGE_PERIOD = 30;
    static int[] adQueue = new int[AVERAGE_PERIOD];
    static int[] mnQueue = new int[AVERAGE_PERIOD];
    static int lastBuiltAnchor = 0;
    static int mapSize = 0;
    static MapLocation mapCenter = null;

    static void runHeadquarters(RobotController rc) throws GameActionException {
        if (rc.getRoundNum() == 1) {
            Comms.updateHQLocation(rc);
            mapSize = rc.getMapWidth() * rc.getMapHeight();
            mapCenter = new MapLocation(rc.getMapWidth()/2, rc.getMapHeight()/2);
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
        } else {
            // TURTLE
            if (rc.getRoundNum() == 1) {
                System.out.print("TURTLE UP");
            }
            lastBuiltAnchor++;
            if (RobotPlayer.turnCount == 1) {
                for (int i = 0; i++ < 5; ) {
                    if (tryToBuildRobot(rc, RobotType.LAUNCHER)) {
                        rc.setIndicatorString("Building a launcher");
                    }
                }
                if (tryToBuildRobot(rc, RobotType.CARRIER)) {
                    rc.setIndicatorString("Building a carrier");
                }
            }
            // if it has been 200 turns, and we see a neutral island make an anchor
            /** MAGIC NUMBERS USED **/
            else if (RobotPlayer.turnCount > MAGIC_NUM_TURNS && lastBuiltAnchor > MAGIC_ANCHOR_NUM_TURNS) {
                // wait for resources and build an anchor
                rc.setIndicatorString("Trying to build an anchor");
                if (rc.canBuildAnchor(Anchor.STANDARD)) {
                    rc.setIndicatorString("Building an anchor");
                    rc.buildAnchor(Anchor.STANDARD);
                    lastBuiltAnchor = 0;
                } else {
                    for (int i = 0; i++ < 5; ) {
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
            } else {
                // build bots
                // we have moving average
                if (RobotPlayer.turnCount > AVERAGE_PERIOD) {
                    if (adGetMovingAverage() > mnGetMovingAverage()) {
                        // more ad being gathered per turn
                        for (int i = 0; i++ < 5; ) {
                            if (tryToBuildRobot(rc, RobotType.CARRIER)) {
                                rc.setIndicatorString("Building a carrier");
                            } else if (tryToBuildRobot(rc, RobotType.LAUNCHER)) {
                                rc.setIndicatorString("Building a launcher");
                            } else {
                                rc.setIndicatorString("Waiting for resources");
                            }
                        }
                    } else {
                        // more mana being gathered per turn
                        for (int i = 0; i++ < 5; ) {
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
                    for (int i = 0; i++ < 5; ) {
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

    static boolean tryToBuildRobot(RobotController rc, RobotType robotTypeToBuild) throws GameActionException {
        rc.setIndicatorString("Trying to build a " + robotTypeToBuild);
        MapLocation myLoc = rc.getLocation();
        switch (robotTypeToBuild) {
            case LAUNCHER:
                Direction centerDir = myLoc.directionTo(mapCenter);
                MapLocation farthestCandidateBuildLoc = getFarthestBuildLoc(myLoc, centerDir);
                if (rc.canBuildRobot(robotTypeToBuild, farthestCandidateBuildLoc)) {
                    rc.buildRobot(robotTypeToBuild, farthestCandidateBuildLoc);
                    return true;
                }
                MapLocation[] firstDepthCandidateBuildLocs = getAroundSurroundLocs(farthestCandidateBuildLoc, centerDir);
                for (int i = 0; i++ < firstDepthCandidateBuildLocs.length; ) {
                    if (rc.canBuildRobot(robotTypeToBuild, firstDepthCandidateBuildLocs[i - 1])) {
                        rc.buildRobot(robotTypeToBuild, firstDepthCandidateBuildLocs[i - 1]);
                        return true;
                    }
                }
                for (int i = 0; i++ < firstDepthCandidateBuildLocs.length; ) {
                    MapLocation[] secondDepthCandidateBuildLocs = getAroundSurroundLocs(firstDepthCandidateBuildLocs[i-1], centerDir);
                    for (int j = 0; j++ < secondDepthCandidateBuildLocs.length; ) {
                        if (rc.canBuildRobot(robotTypeToBuild, secondDepthCandidateBuildLocs[j - 1])) {
                            rc.buildRobot(robotTypeToBuild, secondDepthCandidateBuildLocs[j - 1]);
                            return true;
                        }
                    }
                }
                break;

            default:
                for (int i = 0; i++ < RobotPlayer.directions.length;) {
                    MapLocation candidateBuildLoc = rc.getLocation().add(RobotPlayer.directions[i-1]);
                    while (rc.getLocation().isWithinDistanceSquared(candidateBuildLoc.add(RobotPlayer.directions[i-1]), RobotType.HEADQUARTERS.actionRadiusSquared)) {
                        candidateBuildLoc = candidateBuildLoc.add(RobotPlayer.directions[i-1]);
                    }
                    if (rc.canBuildRobot(robotTypeToBuild, candidateBuildLoc)) {
                        rc.buildRobot(robotTypeToBuild, candidateBuildLoc);
                        return true;
                    }
                }
                break;
        }

        return false;
    }

    static MapLocation getFarthestBuildLoc(MapLocation myLoc, Direction dir) {
        MapLocation buildLoc = myLoc;
        switch (dir) {
            case NORTH:
                buildLoc = buildLoc.translate(0, 3);
                break;

            case NORTHEAST:
                buildLoc = buildLoc.translate(2, 2);
                break;

            case EAST:
                buildLoc = buildLoc.translate(3, 0);
                break;

            case SOUTHEAST:
                buildLoc = buildLoc.translate(2, -2);
                break;

            case SOUTH:
                buildLoc = buildLoc.translate(0, -3);
                break;

            case SOUTHWEST:
                buildLoc = buildLoc.translate(-2, -2);
                break;

            case WEST:
                buildLoc = buildLoc.translate(-3, 0);
                break;

            case NORTHWEST:
                buildLoc = buildLoc.translate(-2, 2);
                break;
        }
        return buildLoc;
    }

    static MapLocation[] getAroundSurroundLocs(MapLocation loc, Direction dir) {
        MapLocation[] buildLocs = new MapLocation[3];
        switch (dir) {
            case NORTH:
                buildLocs[0] = loc.translate(0, -1);
                buildLocs[1] = loc.translate(-1, -1);
                buildLocs[2] = loc.translate(1, -1);
                break;

            case NORTHEAST:
                buildLocs[0] = loc.translate(-1, 0);
                buildLocs[1] = loc.translate(-1, -1);
                buildLocs[2] = loc.translate(0, -1);
                break;

            case EAST:
                buildLocs[0] = loc.translate(-1, 1);
                buildLocs[1] = loc.translate(-1, 0);
                buildLocs[2] = loc.translate(-1, -1);
                break;

            case SOUTHEAST:
                buildLocs[0] = loc.translate(0, 1);
                buildLocs[1] = loc.translate(-1, 1);
                buildLocs[2] = loc.translate(-1, 0);
                break;

            case SOUTH:
                buildLocs[0] = loc.translate(0, 1);
                buildLocs[1] = loc.translate(-1, 1);
                buildLocs[2] = loc.translate(1, 1);
                break;

            case SOUTHWEST:
                buildLocs[0] = loc.translate(0, 1);
                buildLocs[1] = loc.translate(1, 1);
                buildLocs[2] = loc.translate(1, 0);
                break;

            case WEST:
                buildLocs[0] = loc.translate(1, 1);
                buildLocs[1] = loc.translate(1, 0);
                buildLocs[2] = loc.translate(1, -1);
                break;

            case NORTHWEST:
                buildLocs[0] = loc.translate(1, 0);
                buildLocs[1] = loc.translate(1, -1);
                buildLocs[2] = loc.translate(0, -1);
                break;
        }
        return buildLocs;
    }

    static float adGetMovingAverage() {
        int totalAd = 0;
        for (int i = 0; i++ < adQueue.length; ) {
            totalAd += adQueue[i - 1];
        }
        return totalAd / adQueue.length;
    }

    static float mnGetMovingAverage() {
        int totalMn = 0;
        for (int i = 0; i++ < mnQueue.length; ) {
            totalMn += mnQueue[i - 1];
        }
        return totalMn / mnQueue.length;
    }
}
