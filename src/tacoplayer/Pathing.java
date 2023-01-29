package tacoplayer;

import battlecode.common.*;

public class Pathing {

    static BFSPathing bfsPathing;
    static boolean moveTowards(RobotController rc, MapLocation target) throws GameActionException {
        MapLocation loc = rc.getLocation();
        if (loc.equals(target)) {
            return false;
        }
        if (!rc.isMovementReady()) {
            return false;
        }
        int bc1= Clock.getBytecodesLeft();
        Direction directionToTarget = bfsPathing.getBestDirection(target);
        int bc2= Clock.getBytecodesLeft();
        System.out.println(bc1 - bc2);
        if (directionToTarget != null && rc.canMove(directionToTarget)) {
            rc.move(directionToTarget);
            return true;
        }
        return false;
    }

    static void moveRandomly(RobotController rc) throws GameActionException {
        if (!rc.isMovementReady()) {
            return;
        }

    }

    static boolean safeFromHQ(RobotController rc, MapLocation loc) {
        if (loc == null) {
            return true;
        }
        else return loc.distanceSquaredTo(rc.getLocation()) >= RobotType.HEADQUARTERS.actionRadiusSquared + 3;
    }
}
