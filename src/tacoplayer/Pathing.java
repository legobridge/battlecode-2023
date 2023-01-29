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

    static BFSPathing bfsPathing;
    static BugPathing bugPathing;
    static final int BFS_BYTECODE_THRESHOLD = 5000;
    final static int MAX_PREV_LOCS_TO_STORE = 7;
    static int lastFewLocsIndex = 0;
    static MapLocation[] lastFewLocs = new MapLocation[MAX_PREV_LOCS_TO_STORE];

    static boolean moveTowards(RobotController rc, MapLocation target) throws GameActionException {
        MapLocation loc = rc.getLocation();
        if (loc.equals(target)) {
            return false;
        }
        if (!rc.isMovementReady()) {
            return false;
        }
        Direction directionToTarget = null;
        int bytecodesLeft = Clock.getBytecodesLeft();
        if (bytecodesLeft > BFS_BYTECODE_THRESHOLD) {
            directionToTarget = bfsPathing.getBestDirection(target);
        }
        if (directionToTarget == null) {
            directionToTarget = bugPathing.getBestDirection(target);
        }
        if (rc.canMove(directionToTarget)) {
            rc.move(directionToTarget);
            return true;
        }
        return false;
    }

    static void moveRandomly(RobotController rc) throws GameActionException {
        if (!rc.isMovementReady()) {
            return;
        }
        MapLocation selfLoc = rc.getLocation();
        Direction[] backupDirectionsToTry = new Direction[MAX_PREV_LOCS_TO_STORE];
        int backupDirectionsToTryIndex = MAX_PREV_LOCS_TO_STORE - 1;
        boolean moved = false;
        int startingIndex = rng.nextInt(directions.length);
        for (int i = directions.length; --i >= 0; ) {
            Direction dir = directions[(startingIndex + i) % directions.length];
            MapLocation targetLoc = selfLoc.add(dir);
            if (backupDirectionsToTryIndex >= 0 && ArrayUtil.mapLocationArrayContains(lastFewLocs, targetLoc)) { // If we've been here recently, avoid it
                backupDirectionsToTry[backupDirectionsToTryIndex] = dir;
                backupDirectionsToTryIndex--;
            } else if (rc.canMove(dir) && safeFromHQ(rc, closestEnemyHqLoc)) {
                moved = true;
//                rc.setIndicatorString("I moved to a brand new place, by moving: " + dir);
                moveAndUpdateLastFewLocs(rc, dir);
            }
        }
        if (!moved) {
            for (int i = backupDirectionsToTry.length; --i >= backupDirectionsToTryIndex + 1; ) {
                if (rc.canMove(backupDirectionsToTry[i])) {
//                    rc.setIndicatorString("I moved in a backup direction: " + backupDirectionsToTry[i]);
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
        else return loc.distanceSquaredTo(rc.getLocation()) >= RobotType.HEADQUARTERS.actionRadiusSquared + 3;
    }
}
