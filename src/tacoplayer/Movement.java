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
                return moveAwayFromLocation(rc, closestVisibleEnemyRobotLocation);
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

    static boolean moveAwayFromRobots(RobotController rc, RobotInfo[] robots) throws GameActionException {
        MapLocation target = averageLoc(robots);
        return moveAwayFromLocation(rc, target);
    }

    static boolean moveTowardsLocation(RobotController rc, MapLocation loc) throws GameActionException {
        return Pathing.moveTowards(rc, loc);
    }

    static boolean moveAwayFromLocation(RobotController rc, MapLocation loc) throws GameActionException {
        MapLocation robotLoc = rc.getLocation();
        int x = robotLoc.x + robotLoc.x - loc.x;
        int y = robotLoc.y + robotLoc.y - loc.y;
        MapLocation awayLoc = new MapLocation(x, y);
        return Pathing.moveTowards(rc, awayLoc);
    }

    static boolean moveTowardsEnemyHq(RobotController rc) throws GameActionException {
        rc.setIndicatorString("Moving towards enemy HQ! " + closestEnemyHqLoc);
        return Pathing.moveTowards(rc, closestEnemyHqLoc);
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
