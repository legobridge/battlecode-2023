package tacoplayer;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;


public class AmplifierStrategy {
    /**
     * Run a single turn for an Amplifier.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    static void runAmplifier(RobotController rc) throws GameActionException {
        // Update alive counter
        Comms.updateRobotCount(rc);

        Pathing.moveRandomly(rc);
    }

}