package tacoplayer;

import battlecode.common.*;

import java.util.ArrayList;

import static tacoplayer.RobotPlayer.*;

public class Pathing {
    // Basic bug nav - Bug 0

    static Direction currentDirection = null;

    final static int MAX_PREV_LOCS_TO_STORE = 5;
    static int lastFewLocsIndex = 0;
    static MapLocation[] lastFewLocs = new MapLocation[MAX_PREV_LOCS_TO_STORE];

    // TODO - Move (and act) as many times as cooldown allows
    // TODO - consider currents to be obstacles (soft)
    // TODO - this is very bytecode inefficient!!!
    static void moveTowards(RobotController rc, MapLocation target) throws GameActionException {
        rc.setIndicatorString("Moving towards target! (" + target + ")");
        if (rc.getLocation().equals(target)) {
            return;
        }
        if (!rc.isMovementReady()) {
            return;
        }
        MapLocation nearestEnemyHQLoc = getNearestEnemyHQLoc(rc);
        MapLocation selfLoc = rc.getLocation();
        Direction dir = rc.getLocation().directionTo(target);
        MapLocation targetLoc = selfLoc.add(dir);
        if (!ArrayUtil.mapLocationArrayContains(lastFewLocs, targetLoc)
                && rc.canMove(dir)
                && safeFromHQ(rc, nearestEnemyHQLoc)) {
            moveAndUpdateLastFewLocs(rc, dir);
            currentDirection = null;
        } else {
            if (currentDirection == null) {
                currentDirection = dir;
            }
            // Try to move in a way that keeps the obstacle on our right
            boolean moved = false;
            for (int i = 0; i < 8; i++) {
                targetLoc = selfLoc.add(currentDirection);
                if (!ArrayUtil.mapLocationArrayContains(lastFewLocs, targetLoc) && rc.canMove(currentDirection)) {
                    moveAndUpdateLastFewLocs(rc, currentDirection);
                    currentDirection = currentDirection.rotateRight();
                    moved = true;
                    break;
                } else {
                    currentDirection = currentDirection.rotateLeft();
                }
            }
            if (!moved) {
                moveRandomly(rc);
            }
        }
    }

    static void moveRandomly(RobotController rc) throws GameActionException {
//        rc.setIndicatorString("Moving Randomly!");
        if (!rc.isMovementReady()) {
            return;
        }
        MapLocation nearestEnemyHQLoc = getNearestEnemyHQLoc(rc);
        MapLocation selfLoc = rc.getLocation();
        ArrayList<Direction> backupDirectionsToTry = new ArrayList<>();
        boolean moved = false;
        int numDirections = directions.length;
        for (int i = 0; i < numDirections; i++) {
            Direction dir = directions[rng.nextInt(numDirections)];
            MapLocation targetLoc = selfLoc.add(dir);
            if (ArrayUtil.mapLocationArrayContains(lastFewLocs, targetLoc)) { // If we've been here recently, avoid it
                backupDirectionsToTry.add(dir);
            } else if (rc.canMove(dir) && safeFromHQ(rc, nearestEnemyHQLoc)) {
                moved = true;
                rc.setIndicatorString("I moved to a brand new place, by moving: " + dir);
                moveAndUpdateLastFewLocs(rc, dir);
            }
        }
        if (!moved) {
            for (Direction backupDirection : backupDirectionsToTry) {
                if (rc.canMove(backupDirection)) {
                    rc.setIndicatorString("I moved in a backup direction: " + backupDirection);
                    moveAndUpdateLastFewLocs(rc, backupDirection);
                }
            }
        }
    }

    private static void moveAndUpdateLastFewLocs(RobotController rc, Direction dir) throws GameActionException {
        rc.move(dir);
        lastFewLocs[(lastFewLocsIndex++) % MAX_PREV_LOCS_TO_STORE] = rc.getLocation();
    }

    static MapLocation getNearestEnemyHQLoc(RobotController rc) throws GameActionException {
        RobotInfo[] enemies = rc.senseNearbyRobots(-1, theirTeam);
        MapLocation nearestHQ = null;
        MapLocation ourLoc = rc.getLocation();
        int closestHQDistSq = Integer.MAX_VALUE;
        for (int i = 0; i < enemies.length; i++) {
            RobotInfo enemy = enemies[i];
            if (enemy.getType() == RobotType.HEADQUARTERS) {
                int distSq = ourLoc.distanceSquaredTo(enemy.getLocation());
                if (distSq < closestHQDistSq) {
                    nearestHQ = enemy.getLocation();
                    closestHQDistSq = distSq;
                }
            }
        }
        return nearestHQ;
    }

    static boolean safeFromHQ(RobotController rc, MapLocation loc) {
        if (loc == null) {
            return true;
        }
        else if (loc.distanceSquaredTo(rc.getLocation()) < RobotType.HEADQUARTERS.actionRadiusSquared+3) {
            return false;
        }
        else {
            return true;
        }
    }
}
