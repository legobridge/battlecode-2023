package tacoplayer;

import battlecode.common.*;


import static tacoplayer.HeadquartersStrategy.*;
import static tacoplayer.RobotPlayer.*;
import static tacoplayer.Sensing.*;

public class BuildBots {

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
        Direction centerDir = rc.getLocation().directionTo(mapCenter);
        switch (robotTypeToBuild) {
            case LAUNCHER:
                if (rc.getResourceAmount(ResourceType.MANA) < robotTypeToBuild.getBuildCost(ResourceType.MANA)) {
                    return false;
                }
                if (closestVisibleEnemyRobot != null) {
                    Direction closestEnemyDir = rc.getLocation().directionTo(closestVisibleEnemyRobot.location);
                    if (buildFarthest(rc, robotTypeToBuild, closestEnemyDir)) {
                        return true;
                    }
                }
                else if (closestEnemyIslandLoc != null) {
                    Direction closestEnemyIslandDir = rc.getLocation().directionTo(closestEnemyIslandLoc);
                    if (buildFarthest(rc, robotTypeToBuild, closestEnemyIslandDir)) {
                        return true;
                    }
                }
                else if (closestEnemyHqLoc != null) {
                    Direction closestEnemyHQDir = rc.getLocation().directionTo(closestEnemyHqLoc);
                    if (buildFarthest(rc, robotTypeToBuild, closestEnemyHQDir)) {
                        return true;
                    }
                }
                else if (buildFarthest(rc, robotTypeToBuild, centerDir)) {
                    return true;
                }
                break;

            case AMPLIFIER:
                if (rc.getResourceAmount(ResourceType.ADAMANTIUM) < robotTypeToBuild.getBuildCost(ResourceType.ADAMANTIUM)
                        || rc.getResourceAmount(ResourceType.MANA) < robotTypeToBuild.getBuildCost(ResourceType.MANA)) {
                    return false;
                }
                if (buildFarthest(rc, robotTypeToBuild, centerDir)) {
                    return true;
                }
                break;

            case CARRIER:
                if (rc.getResourceAmount(ResourceType.ADAMANTIUM) < robotTypeToBuild.getBuildCost(ResourceType.ADAMANTIUM)) {
                    return false;
                }
                if (rc.getNumAnchors(Anchor.STANDARD) > 0 || rc.getNumAnchors(Anchor.ACCELERATING) > 0) {
                    // hq has an anchor
                    Direction nearestNeutralIslandDir = rc.getLocation().directionTo(closestNeutralIslandLoc);
                    if (nearestNeutralIslandDir != null) {
                        if (buildFarthest(rc, robotTypeToBuild, nearestNeutralIslandDir)) {
                            return true;
                        }
                    }
                }
                Direction nearestWellDir;
                Direction secondNearestWellDir;
                switch (ResourceType.MANA) {
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
                    if (buildFarthest(rc, robotTypeToBuild, nearestWellDir)) {
                        return true;
                    }
                    else if (secondNearestWellDir != null) {
                        if (buildFarthest(rc, robotTypeToBuild, secondNearestWellDir)) {
                            return true;
                        }
                    }
                }
                if (secondNearestWellDir != null) {
                    if (buildFarthest(rc, robotTypeToBuild, secondNearestWellDir)) {
                        return true;
                    }
                }
                if (buildFarthest(rc, robotTypeToBuild, centerDir)) {
                    return true;
                }
                break;

            default:
                // build towards center
                if (buildFarthest(rc, robotTypeToBuild, centerDir)) {
                    return true;
                }
                break;
        }
        return false;
    }

    static boolean buildFarthest(RobotController rc, RobotType robotTypeToBuild, Direction dirToBuild) throws GameActionException {
        boolean isWithinEnemyHQRange = false; //todo - resolve this
        switch (dirToBuild) {
            case NORTH:
                for (int i = -1; ++i < farthestNorth.length; ) {
                    MapLocation buildLoc = rc.getLocation().translate(farthestNorth[i].dx, farthestNorth[i].dy);
                    if (!(isWithinEnemyHQRange && buildLoc.isWithinDistanceSquared(closestEnemyHqLoc, RobotType.HEADQUARTERS.actionRadiusSquared))
                            && rc.canBuildRobot(robotTypeToBuild, buildLoc)) {
                        rc.buildRobot(robotTypeToBuild, buildLoc);
                        return true;
                    }
                }
                break;

            case NORTHEAST:
                for (int i = -1; ++i < farthestNorthEast.length; ) {
                    MapLocation buildLoc = rc.getLocation().translate(farthestNorthEast[i].dx, farthestNorthEast[i].dy);
                    if (!(isWithinEnemyHQRange && buildLoc.isWithinDistanceSquared(closestEnemyHqLoc, RobotType.HEADQUARTERS.actionRadiusSquared))
                            && rc.canBuildRobot(robotTypeToBuild, buildLoc)) {
                        rc.buildRobot(robotTypeToBuild, buildLoc);
                        return true;
                    }
                }
                break;

            case EAST:
                for (int i = -1; ++i < farthestNorth.length; ) {
                    TranslatePair translatePair = rotate90Clockwise(farthestNorth[i]);
                    MapLocation buildLoc = rc.getLocation().translate(translatePair.dx, translatePair.dy);
                    if (!(isWithinEnemyHQRange && buildLoc.isWithinDistanceSquared(closestEnemyHqLoc, RobotType.HEADQUARTERS.actionRadiusSquared))
                            && rc.canBuildRobot(robotTypeToBuild, buildLoc)) {
                        rc.buildRobot(robotTypeToBuild, buildLoc);
                        return true;
                    }
                }
                break;

            case SOUTHEAST:
                for (int i = -1; ++i < farthestNorthEast.length; ) {
                    TranslatePair translatePair = rotate90Clockwise(farthestNorthEast[i]);
                    MapLocation buildLoc = rc.getLocation().translate(translatePair.dx, translatePair.dy);
                    if (!(isWithinEnemyHQRange && buildLoc.isWithinDistanceSquared(closestEnemyHqLoc, RobotType.HEADQUARTERS.actionRadiusSquared))
                            && rc.canBuildRobot(robotTypeToBuild, buildLoc)) {
                        rc.buildRobot(robotTypeToBuild, buildLoc);
                        return true;
                    }
                }
                break;

            case SOUTH:
                for (int i = -1; ++i < farthestNorth.length; ) {
                    TranslatePair translatePair = rotate180(farthestNorth[i]);
                    MapLocation buildLoc = rc.getLocation().translate(translatePair.dx, translatePair.dy);
                    if (!(isWithinEnemyHQRange && buildLoc.isWithinDistanceSquared(closestEnemyHqLoc, RobotType.HEADQUARTERS.actionRadiusSquared))
                            && rc.canBuildRobot(robotTypeToBuild, buildLoc)) {
                        rc.buildRobot(robotTypeToBuild, buildLoc);
                        return true;
                    }
                }
                break;

            case SOUTHWEST:
                for (int i = -1; ++i < farthestNorthEast.length; ) {
                    TranslatePair translatePair = rotate180(farthestNorthEast[i]);
                    MapLocation buildLoc = rc.getLocation().translate(translatePair.dx, translatePair.dy);
                    if (!(isWithinEnemyHQRange && buildLoc.isWithinDistanceSquared(closestEnemyHqLoc, RobotType.HEADQUARTERS.actionRadiusSquared))
                            && rc.canBuildRobot(robotTypeToBuild, buildLoc)) {
                        rc.buildRobot(robotTypeToBuild, buildLoc);
                        return true;
                    }
                }
                break;

            case WEST:
                for (int i = -1; ++i < farthestNorth.length; ) {
                    TranslatePair translatePair = rotate90AntiClockwise(farthestNorth[i]);
                    MapLocation buildLoc = rc.getLocation().translate(translatePair.dx, translatePair.dy);
                    if (!(isWithinEnemyHQRange && buildLoc.isWithinDistanceSquared(closestEnemyHqLoc, RobotType.HEADQUARTERS.actionRadiusSquared))
                            && rc.canBuildRobot(robotTypeToBuild, buildLoc)) {
                        rc.buildRobot(robotTypeToBuild, buildLoc);
                        return true;
                    }
                }
                break;

            case NORTHWEST:
                for (int i = -1; ++i < farthestNorthEast.length; ) {
                    TranslatePair translatePair = rotate90AntiClockwise(farthestNorthEast[i]);
                    MapLocation buildLoc = rc.getLocation().translate(translatePair.dx, translatePair.dy);
                    if (!(isWithinEnemyHQRange && buildLoc.isWithinDistanceSquared(closestEnemyHqLoc, RobotType.HEADQUARTERS.actionRadiusSquared))
                            && rc.canBuildRobot(robotTypeToBuild, buildLoc)) {
                        rc.buildRobot(robotTypeToBuild, buildLoc);
                        return true;
                    }
                }
                break;

        }
        return false;
    }

    static TranslatePair rotate90AntiClockwise(TranslatePair translatePair) {
        return new TranslatePair(-1 * translatePair.dy, translatePair.dx);
    }

    static TranslatePair rotate90Clockwise(TranslatePair translatePair) {
        return new TranslatePair(translatePair.dy, -1 * translatePair.dx);
    }

    static TranslatePair rotate180(TranslatePair translatePair) {
        return new TranslatePair(-1 * translatePair.dx, -1 * translatePair.dy);
    }
}
