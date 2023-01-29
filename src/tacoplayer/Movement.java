package tacoplayer;

import battlecode.common.*;
import static tacoplayer.RobotPlayer.*;
import static tacoplayer.Sensing.*;

public class Movement {
    static boolean moveTowardsVisibleEnemies(RobotController rc) throws GameActionException {
        if (visibleEnemiesCount != 0) {
//            MapLocation enemyLocation = averageLoc(visibleEnemies);
            rc.setIndicatorString("Moving towards enemy robot! " + closestVisibleEnemyRobot.location);
            // If you are outside a certain fraction of the enemy's action radius, move towards it, else move away
            if (closestVisibleEnemyRobotDistSq > (5.0 / 6.0) * rc.getType().actionRadiusSquared) {
                return Pathing.moveTowards(rc, closestVisibleEnemyRobot.location);
            }
            else {
                return moveAwayFromLocation(rc, closestVisibleEnemyRobot.location);
            }
        }
        return false;
    }

    static boolean moveTowardsLocation(RobotController rc, MapLocation loc) throws GameActionException {
        return Pathing.moveTowards(rc, loc);
    }

    static boolean moveDirectlyTowards(RobotController rc, Direction dir) throws GameActionException {
        if (rc.canMove(dir)) {
            rc.move(dir);
            return true;
        }
        else if (rc.canMove(dir.rotateLeft())) {
            rc.move(dir.rotateLeft());
            return true;
        }
        else if (rc.canMove(dir.rotateRight())) {
            rc.move(dir.rotateRight());
            return true;
        }
        return false;
    }

    static boolean moveDirectlyTowards(RobotController rc, MapLocation target) throws GameActionException {
        Direction dir = rc.getLocation().directionTo(target);
        if (rc.canMove(dir)) {
            rc.move(dir);
            return true;
        }
        else if (rc.canMove(dir.rotateLeft())) {
            rc.move(dir.rotateLeft());
            return true;
        }
        else if (rc.canMove(dir.rotateRight())) {
            rc.move(dir.rotateRight());
            return true;
        }
        return false;
    }

    static boolean moveAwayFromLocation(RobotController rc, MapLocation loc) throws GameActionException {
        MapLocation robotLoc = rc.getLocation();
        int x = robotLoc.x + robotLoc.x - loc.x;
        int y = robotLoc.y + robotLoc.y - loc.y;
        MapLocation awayLoc = new MapLocation(x, y);
        rc.setIndicatorString("Moving away from " + awayLoc);
        return moveDirectlyTowards(rc, awayLoc);
    }

    static boolean moveTowardsEnemyWell(RobotController rc) throws GameActionException {
        MapLocation well = Sensing.getClosestWell(rc);
        MapLocation wellSym = MapLocationUtil.calcSymmetricLoc(well, Comms.getSymmetryType());
        rc.setIndicatorString("Moving towards enemy Well! " + wellSym);
        return Pathing.moveTowards(rc, wellSym);
    }
    static boolean moveTowardsEnemyHq(RobotController rc) throws GameActionException {
        rc.setIndicatorString("Moving towards enemy HQ! " + closestEnemyHqLoc);
        if (rc.getLocation().distanceSquaredTo(closestEnemyHqLoc) <= RobotType.HEADQUARTERS.actionRadiusSquared + 3) {
            return moveTowardsLocation(rc, closestHqLoc);
        }
        return Pathing.moveTowards(rc, closestEnemyHqLoc);
    }

    static boolean moveTowardsEnemyIslands(RobotController rc) throws GameActionException {
        if (closestEnemyIslandLoc != null) {
            rc.setIndicatorString("Moving towards enemy island! " + closestEnemyIslandLoc);
            return Pathing.moveTowards(rc, closestEnemyIslandLoc);
        }
        return false;
    }
    static boolean moveToExtract(RobotController rc, MapLocation wellLoc) throws GameActionException {
        MapLocation target = null;
        for (int i = 9; --i >= 0;) {
            MapLocation spot = new MapLocation(wellLoc.x + i % 3 - 1, wellLoc.y + i / 3 - 1);
            if (rc.canSenseLocation(spot)) {
                MapInfo spotInfo = rc.senseMapInfo(spot);
                if (spotInfo.isPassable() && rc.canSenseRobotAtLocation(spot)) {
                    target = spot;
                    break;
                }
            }
        }
        if (target != null) {
            moveTowardsLocation(rc, target);
            return true;
        }
        return false;
    }
}

