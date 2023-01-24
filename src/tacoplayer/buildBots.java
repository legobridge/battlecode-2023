package tacoplayer;

import battlecode.common.*;

import static tacoplayer.RobotPlayer.*;

public class buildBots {
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
                for (int i = 0; i++ < directions.length;) {
                    MapLocation candidateBuildLoc = rc.getLocation().add(directions[i-1]);
                    while (rc.getLocation().isWithinDistanceSquared(candidateBuildLoc.add(directions[i-1]), RobotType.HEADQUARTERS.actionRadiusSquared)) {
                        candidateBuildLoc = candidateBuildLoc.add(directions[i-1]);
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
}
