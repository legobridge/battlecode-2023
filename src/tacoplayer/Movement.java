package tacoplayer;

import battlecode.common.*;
import static tacoplayer.RobotPlayer.*;

public class Movement {
    static boolean moveTowardsEnemies(RobotController rc) throws GameActionException {
        RobotInfo[] visibleEnemies = rc.senseNearbyRobots(-1, theirTeam);
        if (visibleEnemies.length != 0) {
            MapLocation enemyLocation = averageLoc(visibleEnemies);
            rc.setIndicatorString("Moving towards enemy robot! " + enemyLocation);
            // If you are outside 3/4 the enemy's action radius, move towards it, else move away
            if (visibleEnemies[0].getLocation().distanceSquaredTo(rc.getLocation()) > rc.getType().actionRadiusSquared * 5/6) {
                Pathing.moveTowards(rc, enemyLocation);
                return true;
            }
            else {
                MapLocation ourLoc = rc.getLocation();
                MapLocation runAwayLocation = new MapLocation(ourLoc.x-enemyLocation.x, ourLoc.y-enemyLocation.y);
                Pathing.moveTowards(rc, runAwayLocation);
                return true;
            }
        }
        return false;
    }

    static void moveTowardsEnemies(RobotController rc, int radius) throws GameActionException {
        RobotInfo[] visibleEnemies = rc.senseNearbyRobots(-1, theirTeam);
        if (visibleEnemies.length != 0) {
            MapLocation enemyLocation = averageLoc(visibleEnemies);
            rc.setIndicatorString("Moving towards enemy robot! " + enemyLocation);
            // If you are outside 3/4 the enemy's action radius, move towards it, else move away
            if (visibleEnemies[0].getLocation().distanceSquaredTo(rc.getLocation()) > radius) {
                Pathing.moveTowards(rc, enemyLocation);
            }
            else {
                MapLocation ourLoc = rc.getLocation();
                MapLocation runAwayLocation = new MapLocation(ourLoc.x-enemyLocation.x, ourLoc.y-enemyLocation.y);
                Pathing.moveTowards(rc, runAwayLocation);
            }
        }
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
            Pathing.moveTowards(rc, closestEnemyHqLoc);
            return true;
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
