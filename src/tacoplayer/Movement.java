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
}
