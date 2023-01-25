package tacoplayer;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

import static tacoplayer.RobotPlayer.*;
import static tacoplayer.Sensing.*;


public class AmplifierStrategy {
    /**
     * Run a single turn for an Amplifier.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    static void runAmplifier(RobotController rc) throws GameActionException {
        // Update alive counter
        Comms.updateRobotCount(rc);

        // MUST move away from enemies
        if (enemyLauncherCount + enemyDestabCount > 0) {
            Movement.moveTowardsLocation(rc, closestHqLoc);
        }
        else {
            Pathing.moveRandomly(rc);
        }
    }
}