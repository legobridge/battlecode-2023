package tacoplayer;

import battlecode.common.*;
import static tacoplayer.RobotPlayer.*;
import static tacoplayer.Sensing.*;

public class Movement {
    static boolean moveTowardsVisibleEnemies(RobotController rc) throws GameActionException {
        if (visibleEnemiesCount != 0) {
//            MapLocation enemyLocation = averageLoc(visibleEnemies);
            rc.setIndicatorString("Moving towards enemy robot! " + closestVisibleEnemyRobotLocation);
            // If you are outside a certain fraction of the enemy's action radius, move towards it, else move away
            if (closestVisibleEnemyRobotDistSq > (5.0 / 6.0) * rc.getType().actionRadiusSquared) {
                return Pathing.moveTowards(rc, closestVisibleEnemyRobotLocation);
            }
            else {
                MapLocation ourLoc = rc.getLocation();
                MapLocation runAwayLocation = new MapLocation(
                        ourLoc.x - closestVisibleEnemyRobotLocation.x,
                        ourLoc.y - closestVisibleEnemyRobotLocation.y);
                return Pathing.moveTowards(rc, runAwayLocation);
            }
        }
        return false;
    }

    static MapLocation averageLoc(RobotInfo[] robots) {
        int sumX = 0;
        int sumY = 0;
        for (int i = 0; i < robots.length; i++) {
            MapLocation loc = robots[i].getLocation();
            sumX += loc.x;
            sumY += loc.y;
        }
        return new MapLocation((int) ((float)sumX / robots.length), (int) ((float) sumY / robots.length));

    }

    static void moveTowardsRobots(RobotController rc, RobotInfo[] robots) throws GameActionException {
        MapLocation target = averageLoc(robots);
        Pathing.moveTowards(rc, target);
    }

    static void moveAwayFromRobots(RobotController rc, RobotInfo[] robots) throws GameActionException {
        MapLocation target = averageLoc(robots);
        moveAwayFromLocation(rc, target);
    }

    static void moveTowardsLocation(RobotController rc, MapLocation loc) throws GameActionException {
        Pathing.moveTowards(rc, loc);
    }

    static void moveAwayFromLocation(RobotController rc, MapLocation loc) throws GameActionException {
        MapLocation robotLoc = rc.getLocation();
        int dx = robotLoc.x - loc.x;
        int dy = robotLoc.y - loc.y;
        MapLocation awayLoc = new MapLocation(robotLoc.x + dx, robotLoc.y + dy);
        Pathing.moveTowards(rc, awayLoc);
    }

    static boolean moveTowardsEnemyHq(RobotController rc) throws GameActionException {
        if (closestEnemyHqLoc != null) {
            rc.setIndicatorString("Moving towards enemy HQ! " + closestEnemyHqLoc);
            // Prevent bot from running into range of HQ
            int distSqToHQ = closestEnemyHqLoc.distanceSquaredTo(rc.getLocation());
            if (distSqToHQ < RobotType.HEADQUARTERS.actionRadiusSquared + 3) {
                return false;
            }
            return Pathing.moveTowards(rc, closestEnemyHqLoc);
        }
        return false;
    }

    static boolean moveTowardsEnemyIslands(RobotController rc) throws GameActionException {
        if (closestEnemyIslandLoc != null) {
            rc.setIndicatorString("Moving towards enemy island! " + closestEnemyIslandLoc);
            Pathing.moveTowards(rc, closestEnemyIslandLoc);
            return true;
        }
        return false;
    }

    static boolean moveTowardsCenter(RobotController rc) throws GameActionException {
        int width = rc.getMapWidth();
        int height = rc.getMapHeight();
        MapLocation center = new MapLocation(width/2, height/2);
        return Pathing.moveTowards(rc, center);
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

    static boolean moveSortaDirectlyTowards(RobotController rc, Direction dir) throws GameActionException {
        if (rc.canMove(dir)) {
            rc.setIndicatorString("Moving to extract");
            rc.move(dir);
            return true;
        }
        else if (rc.canMove(dir.rotateRight())) {
            rc.setIndicatorString("Moving to extract");
            rc.move(dir.rotateRight());
            return true;
        }
        else if (rc.canMove(dir.rotateLeft())) {
            rc.setIndicatorString("Moving to extract");
            rc.move(dir.rotateLeft());
            return true;
        }
        else if (rc.canMove(dir.rotateRight().rotateRight())) {
            rc.setIndicatorString("Moving to extract");
            rc.move(dir.rotateRight().rotateRight());
            return true;
        }
        else if (rc.canMove(dir.rotateLeft().rotateLeft())) {
            rc.setIndicatorString("Moving to extract");
            rc.move(dir.rotateLeft().rotateLeft());
            return true;
        }
        return false;
    }

    static boolean moveToExtract(RobotController rc, MapLocation wellLoc) throws GameActionException {
        MapLocation target = null;
        for (int i = 9; --i >= 0;) {
            MapLocation spot = new MapLocation(wellLoc.x + i % 3 - 1, wellLoc.y + i / 3 - 1);
            MapInfo spotInfo = rc.senseMapInfo(spot);
            if (spotInfo.isPassable() && rc.canSenseRobotAtLocation(spot)) {
                target = spot;
                break;
            }
        }
        if (target != null) {
            moveSortaDirectlyTowards(rc, rc.getLocation().directionTo(target));
            return true;
        }
        return false;
    }
}
