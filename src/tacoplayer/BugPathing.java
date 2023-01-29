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
        return Direction.values()[rng.nextInt(8)];
    }
}
