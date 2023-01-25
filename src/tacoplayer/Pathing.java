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
        ArrayList<Direction> backupDirectionsToTry = new ArrayList<>();
        boolean moved = false;
        int numDirections = directions.length;
        for (int i = 0; i < numDirections; i++) {
            Direction dir = directions[rng.nextInt(numDirections)];
            MapLocation targetLoc = selfLoc.add(dir);
            if (ArrayUtil.mapLocationArrayContains(lastFewLocs, targetLoc)) { // If we've been here recently, avoid it
                backupDirectionsToTry.add(dir);
            } else if (rc.canMove(dir) && safeFromHQ(rc, closestEnemyHqLoc)) {
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

    private static boolean safeFromHQ(RobotController rc, MapLocation loc) {
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
