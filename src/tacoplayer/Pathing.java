package tacoplayer;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

import java.util.ArrayList;
import java.util.LinkedList;

public class Pathing {
    // Basic bug nav - Bug 0

    static Direction currentDirection = null;

    static int MAX_PREV_LOCS_TO_STORE = 5;
    static LinkedList<MapLocation> lastFewLocs = new LinkedList<>();

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
        MapLocation selfLoc = rc.getLocation();
        Direction dir = rc.getLocation().directionTo(target);
        MapLocation targetLoc = selfLoc.add(dir);
        if (!lastFewLocs.contains(targetLoc) && rc.canMove(dir)) {
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
                if (!lastFewLocs.contains(targetLoc) && rc.canMove(currentDirection)) {
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
        rc.setIndicatorString("Moving Randomly!");
        if (!rc.isMovementReady()) {
            return;
        }
        MapLocation selfLoc = rc.getLocation();
        ArrayList<Direction> backupDirectionsToTry = new ArrayList<>();
        boolean moved = false;
        int numDirections = RobotPlayer.directions.length;
        for (int i = 0; i < numDirections; i++) {
            Direction dir = RobotPlayer.directions[RobotPlayer.rng.nextInt(numDirections)];
            MapLocation targetLoc = selfLoc.add(dir);
            if (lastFewLocs.contains(targetLoc)) { // If we've been here recently, avoid it
                backupDirectionsToTry.add(dir);
            } else if (rc.canMove(dir)) {
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
        lastFewLocs.addLast(rc.getLocation());
        if (lastFewLocs.size() > MAX_PREV_LOCS_TO_STORE) {
            lastFewLocs.removeFirst();
        }
    }
}
