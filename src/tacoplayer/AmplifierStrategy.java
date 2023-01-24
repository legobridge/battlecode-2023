package tacoplayer;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;

import static tacoplayer.RobotPlayer.*;


public class AmplifierStrategy {
    /**
     * Run a single turn for an Amplifier.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    static void runAmplifier(RobotController rc) throws GameActionException {
        // Update alive counter
        Comms.updateRobotCount(rc);

        // MUST move away from enemies
        RobotInfo[] enemies = rc.senseNearbyRobots(-1, theirTeam);
        if (enemies.length > 0) {
            Movement.moveTowardsLocation(rc, closestHqLoc);
            Movement.moveTowardsLocation(rc, closestHqLoc);
            Movement.moveTowardsLocation(rc, closestHqLoc);
        }
        else {
            Pathing.moveRandomly(rc);
            Pathing.moveRandomly(rc);
            Pathing.moveRandomly(rc);
        }

        // Sense islands
        Comms.updateIslands(rc);
    }
}