package tacoplayer;

import battlecode.common.Direction;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

import static tacoplayer.RobotPlayer.*;

public class BugPathing {

    RobotController rc;

    public BugPathing(RobotController rc) {
        this.rc = rc;
    }

    public Direction getBestDirection(MapLocation target) {
        Direction dirAnti = rc.getLocation().directionTo(target);
        Direction dirClock = rc.getLocation().directionTo(target);
        for (int i = 3; --i >= 0;) {
            if (rc.canMove(dirAnti)) {
                return dirAnti;
            }
            else {
                dirAnti = dirAnti.rotateLeft();
            }
            if (rc.canMove(dirClock)) {
                return dirClock;
            }
            else {
                dirClock = dirClock.rotateRight();
            }
        }
        return null;
    }
}
