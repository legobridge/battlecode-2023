package tacoplayer;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

import static tacoplayer.RobotPlayer.*;


public class AmplifierStrategy {
    /**
     * Run a single turn for an Amplifier.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    static void runAmplifier(RobotController rc) throws GameActionException {
        // Update alive counter
        Comms.updateRobotCount(rc);
        if (rc.canMove(rc.getLocation().directionTo(mapCenter))) {
            rc.move(rc.getLocation().directionTo(mapCenter));
        }
        else {
            Pathing.moveRandomly(rc);
        }
    }

}