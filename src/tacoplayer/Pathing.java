package tacoplayer;

import battlecode.common.*;

import static tacoplayer.RobotPlayer.*;

public class Pathing {
    static final Direction[] directions = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST,
    };
    // Basic bug nav - Bug 0

    static Direction currentDirection = null;

    final static int MAX_PREV_LOCS_TO_STORE = 5;
    static int lastFewLocsIndex = 0;
    static MapLocation[] lastFewLocs = new MapLocation[MAX_PREV_LOCS_TO_STORE];

    // TODO - Move (and act) as many times as cooldown allows
    // TODO - consider currents to be obstacles (soft)
    // TODO - this is very bytecode inefficient!!!
    static boolean moveTowards(RobotController rc, MapLocation target) throws GameActionException {
//        rc.setIndicatorString("Moving towards target! (" + target + ")");
        if (rc.getLocation().equals(target)) {
            return false;
        }
        if (!rc.isMovementReady()) {
            return false;
        }
        MapLocation selfLoc = rc.getLocation();
        Direction dir = rc.getLocation().directionTo(target);
        MapLocation targetLoc = selfLoc.add(dir);
        boolean hasMoved = false;
        if (!ArrayUtil.mapLocationArrayContains(lastFewLocs, targetLoc)
                && rc.canMove(dir)
                && safeFromHQ(rc, closestEnemyHqLoc)) {
            moveAndUpdateLastFewLocs(rc, dir);
            currentDirection = null;
            hasMoved = true;
        } else {
            if (currentDirection == null) {
                currentDirection = dir;
            }
            // Try to move in a way that keeps the obstacle on our right
            for (int i = 0; i < 8; i++) {
                targetLoc = selfLoc.add(currentDirection);
                if (!ArrayUtil.mapLocationArrayContains(lastFewLocs, targetLoc) && rc.canMove(currentDirection)) {
                    moveAndUpdateLastFewLocs(rc, currentDirection);
                    currentDirection = currentDirection.rotateRight();
                    hasMoved = true;
                    break;
                } else {
                    currentDirection = currentDirection.rotateLeft();
                }
            }
        }
        return hasMoved;
    }

    static void moveRandomly(RobotController rc) throws GameActionException {
//        rc.setIndicatorString("Moving Randomly!");
        if (!rc.isMovementReady()) {
            return;
        }
        MapLocation selfLoc = rc.getLocation();
        Direction[] backupDirectionsToTry = new Direction[MAX_PREV_LOCS_TO_STORE];
        int backupDirectionsToTryIndex = 0;
        boolean moved = false;
        int startingIndex = rng.nextInt(directions.length);
        for (int i = directions.length; --i >= 0; ) {
            Direction dir = directions[(startingIndex + i) % directions.length];
            MapLocation targetLoc = selfLoc.add(dir);
            if (backupDirectionsToTryIndex < MAX_PREV_LOCS_TO_STORE && ArrayUtil.mapLocationArrayContains(lastFewLocs, targetLoc)) { // If we've been here recently, avoid it
                backupDirectionsToTry[backupDirectionsToTryIndex] = dir;
                backupDirectionsToTryIndex++;
            } else if (rc.canMove(dir) && safeFromHQ(rc, closestEnemyHqLoc)) {
                moved = true;
                rc.setIndicatorString("I moved to a brand new place, by moving: " + dir);
                moveAndUpdateLastFewLocs(rc, dir);
            }
        }
        if (!moved) {
            for (int i = -1; ++i < backupDirectionsToTry.length; ) {
                if (rc.canMove(backupDirectionsToTry[i])) {
                    rc.setIndicatorString("I moved in a backup direction: " + backupDirectionsToTry[i]);
                    moveAndUpdateLastFewLocs(rc, backupDirectionsToTry[i]);
                }
            }
        }
    }

    private static void moveAndUpdateLastFewLocs(RobotController rc, Direction dir) throws GameActionException {
        rc.move(dir);
        lastFewLocs[(lastFewLocsIndex++) % MAX_PREV_LOCS_TO_STORE] = rc.getLocation();
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
