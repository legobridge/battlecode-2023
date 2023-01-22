package tacoplayer;

import battlecode.common.*;

public class AmplifierStrategy {
    /**
     * Run a single turn for an Amplifier.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    static void runAmplifier(RobotController rc) throws GameActionException {
        Pathing.moveRandomly(rc);
    }

}