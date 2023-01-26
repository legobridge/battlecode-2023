package tacoplayer;

import battlecode.common.*;


import static tacoplayer.HeadquartersStrategy.*;
import static tacoplayer.RobotPlayer.*;

public class buildBots {

    static TranslatePair[] farthestNorth = new TranslatePair[] {
            new TranslatePair(0, 3),
            new TranslatePair(-1, 2),
            new TranslatePair(0, 2),
            new TranslatePair(1, 2),
            new TranslatePair(-2, 2),
            new TranslatePair(-2, -1),
            new TranslatePair(-1, 1),
            new TranslatePair(0, 1),
            new TranslatePair(1, 1),
            new TranslatePair(2, 2),
            new TranslatePair(-3, 0),
            new TranslatePair(-2, 0),
            new TranslatePair(-1, 0),
            new TranslatePair(1, 0),
            new TranslatePair(2, 0),
            new TranslatePair(3, 0),
            new TranslatePair(-2, -1),
            new TranslatePair(-1, -1),
            new TranslatePair(0, -1),
            new TranslatePair(1, -1),
            new TranslatePair(1, -1),
            new TranslatePair(2, -1),
            new TranslatePair(-2, -2),
            new TranslatePair(-1, -2),
            new TranslatePair(0, -2),
            new TranslatePair(1, -2),
            new TranslatePair(2, -2),
            new TranslatePair(0, -3)
    };

    static TranslatePair[] farthestNorthEast = new TranslatePair[] {
            new TranslatePair(2, 2),
            new TranslatePair(1, 2),
            new TranslatePair(1, 1),
            new TranslatePair(2, 1),
            new TranslatePair(0, 3),
            new TranslatePair(0, 2),
            new TranslatePair(0, 1),
            new TranslatePair(1, 0),
            new TranslatePair(2, 0),
            new TranslatePair(3, 0),
            new TranslatePair(-1, 2),
            new TranslatePair(-1, 1),
            new TranslatePair(-1, 0),
            new TranslatePair(-1, -1),
            new TranslatePair(0, -1),
            new TranslatePair(1, -1),
            new TranslatePair(2, -1),
            new TranslatePair(-2, 2),
            new TranslatePair(-2, 1),
            new TranslatePair(-2, 0),
            new TranslatePair(-2, -1),
            new TranslatePair(-2, -2),
            new TranslatePair(-1, -2),
            new TranslatePair(0, -2),
            new TranslatePair(1, -2),
            new TranslatePair(2, -2),
            new TranslatePair(-3, 0),
            new TranslatePair(0, -3)
    };

    static boolean tryToBuildRobot(RobotController rc, RobotType robotTypeToBuild) throws GameActionException {
        rc.setIndicatorString("Trying to build a " + robotTypeToBuild);
        MapLocation myLoc = rc.getLocation();
        Direction centerDir = myLoc.directionTo(mapCenter);
        switch (robotTypeToBuild) {
            case LAUNCHER:
                if (rc.getResourceAmount(ResourceType.MANA) < robotTypeToBuild.getBuildCost(ResourceType.MANA)) {
                    return false;
                }
                if (buildFarthest(rc, robotTypeToBuild, myLoc, centerDir)) {
                    return true;
                }
                break;

            case AMPLIFIER:
                if (rc.getResourceAmount(ResourceType.ADAMANTIUM) < robotTypeToBuild.getBuildCost(ResourceType.ADAMANTIUM)
                        || rc.getResourceAmount(ResourceType.MANA) < robotTypeToBuild.getBuildCost(ResourceType.MANA)) {
                    return false;
                }
                if (buildFarthest(rc, robotTypeToBuild, myLoc, centerDir)) {
                    return true;
                }
                break;

            case CARRIER:
                if (rc.getResourceAmount(ResourceType.ADAMANTIUM) < robotTypeToBuild.getBuildCost(ResourceType.ADAMANTIUM)) {
                    return false;
                }
                Direction nearestWellDir;
                Direction secondNearestWellDir;
                switch (resourceNeeded) {
                    case ADAMANTIUM:
                         nearestWellDir = rc.getLocation().directionTo(nearestADWell);
                         secondNearestWellDir = rc.getLocation().directionTo(secondNearestADWell);
                        break;

                    case ELIXIR:
                         nearestWellDir = rc.getLocation().directionTo(nearestEXWell);
                         secondNearestWellDir = rc.getLocation().directionTo(secondNearestEXWell);
                        break;

                    case MANA:
                    default:
                         nearestWellDir = rc.getLocation().directionTo(nearestMNWell);
                         secondNearestWellDir = rc.getLocation().directionTo(secondNearestMNWell);
                        break;
                }
                if (nearestWellDir != null) {
                    if (buildFarthest(rc, robotTypeToBuild, myLoc, nearestWellDir)) {
                        return true;
                    }
                    else if (secondNearestWellDir != null) {
                        if (buildFarthest(rc, robotTypeToBuild, myLoc, secondNearestWellDir)) {
                            return true;
                        }
                    }
                }
                if (secondNearestWellDir != null) {
                    if (buildFarthest(rc, robotTypeToBuild, myLoc, secondNearestWellDir)) {
                        return true;
                    }
                }
                if (buildFarthest(rc, robotTypeToBuild, myLoc, centerDir)) {
                    return true;
                }
                break;

            default:
                // build towards center
                if (buildFarthest(rc, robotTypeToBuild, myLoc, centerDir)) {
                    return true;
                }
                break;
        }
        return false;
    }

    static boolean buildFarthest(RobotController rc, RobotType robotTypeToBuild, MapLocation myLoc, Direction dirToBuild) throws GameActionException {
        switch (dirToBuild) {
            case NORTH:
                for (int i = 0; i++ < farthestNorth.length; ) {
                    MapLocation buildLoc = myLoc.translate(farthestNorth[i - 1].getDx(), farthestNorth[i - 1].getDy());
                    if (rc.canBuildRobot(robotTypeToBuild, buildLoc)) {
                        rc.buildRobot(robotTypeToBuild, buildLoc);
                        return true;
                    }
                }
                break;

            case NORTHEAST:
                for (int i = 0; i++ < farthestNorthEast.length; ) {
                    MapLocation buildLoc = myLoc.translate(farthestNorthEast[i - 1].getDx(), farthestNorthEast[i - 1].getDy());
                    if (rc.canBuildRobot(robotTypeToBuild, buildLoc)) {
                        rc.buildRobot(robotTypeToBuild, buildLoc);
                        return true;
                    }
                }
                break;

            case EAST:
                for (int i = 0; i++ < farthestNorth.length; ) {
                    TranslatePair translatePair = rotate90Clockwise(farthestNorth[i - 1]);
                    MapLocation buildLoc = myLoc.translate(translatePair.getDx(), translatePair.getDy());
                    if (rc.canBuildRobot(robotTypeToBuild, buildLoc)) {
                        rc.buildRobot(robotTypeToBuild, buildLoc);
                        return true;
                    }
                }
                break;

            case SOUTHEAST:
                for (int i = 0; i++ < farthestNorthEast.length; ) {
                    TranslatePair translatePair = rotate90Clockwise(farthestNorthEast[i - 1]);
                    MapLocation buildLoc = myLoc.translate(translatePair.getDx(), translatePair.getDy());
                    if (rc.canBuildRobot(robotTypeToBuild, buildLoc)) {
                        rc.buildRobot(robotTypeToBuild, buildLoc);
                        return true;
                    }
                }
                break;

            case SOUTH:
                for (int i = 0; i++ < farthestNorth.length; ) {
                    TranslatePair translatePair = rotate180(farthestNorth[i - 1]);
                    MapLocation buildLoc = myLoc.translate(translatePair.getDx(), translatePair.getDy());
                    if (rc.canBuildRobot(robotTypeToBuild, buildLoc)) {
                        rc.buildRobot(robotTypeToBuild, buildLoc);
                        return true;
                    }
                }
                break;

            case SOUTHWEST:
                for (int i = 0; i++ < farthestNorthEast.length; ) {
                    TranslatePair translatePair = rotate180(farthestNorthEast[i - 1]);
                    MapLocation buildLoc = myLoc.translate(translatePair.getDx(), translatePair.getDy());
                    if (rc.canBuildRobot(robotTypeToBuild, buildLoc)) {
                        rc.buildRobot(robotTypeToBuild, buildLoc);
                        return true;
                    }
                }
                break;

            case WEST:
                for (int i = 0; i++ < farthestNorth.length; ) {
                    TranslatePair translatePair = rotate90AntiClockwise(farthestNorth[i - 1]);
                    MapLocation buildLoc = myLoc.translate(translatePair.getDx(), translatePair.getDy());
                    if (rc.canBuildRobot(robotTypeToBuild, buildLoc)) {
                        rc.buildRobot(robotTypeToBuild, buildLoc);
                        return true;
                    }
                }
                break;

            case NORTHWEST:
                for (int i = 0; i++ < farthestNorthEast.length; ) {
                    TranslatePair translatePair = rotate90AntiClockwise(farthestNorthEast[i - 1]);
                    MapLocation buildLoc = myLoc.translate(translatePair.getDx(), translatePair.getDy());
                    if (rc.canBuildRobot(robotTypeToBuild, buildLoc)) {
                        rc.buildRobot(robotTypeToBuild, buildLoc);
                        return true;
                    }
                }
                break;

        }
        return false;
    }

    static TranslatePair rotate90AntiClockwise(TranslatePair translatePair) {
        return new TranslatePair(-1 * translatePair.getDy(), translatePair.getDx());
    }

    static TranslatePair rotate90Clockwise(TranslatePair translatePair) {
        return new TranslatePair(translatePair.getDy(), -1 * translatePair.getDx());
    }

    static TranslatePair rotate180(TranslatePair translatePair) {
        return new TranslatePair(-1 * translatePair.getDx(), -1 * translatePair.getDy());
    }
}
