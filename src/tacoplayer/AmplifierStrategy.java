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
            rc.setIndicatorString("Retreating to base");
        }
        // If injured move to friendly island
        else if (closestFriendlyIslandLoc != null) {
            Movement.moveTowardsLocation(rc, closestFriendlyIslandLoc);
            rc.setIndicatorString("Moving towards island to heal");
        }
        // Move towards a launcher
        else if (ourLauncherCount > 0) {
            Movement.moveTowardsLocation(rc, ourLaunchers[0].getLocation());
            rc.setIndicatorString("Moving towards launcher");
        }
        // Move randomly
        else {
            Pathing.moveRandomly(rc);
            rc.setIndicatorString("Moving randomly");
        }
    }
}