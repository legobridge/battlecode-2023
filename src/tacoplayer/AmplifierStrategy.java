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
            moveAwayFromRobots(rc, enemies);
            moveAwayFromRobots(rc, enemies);
            moveAwayFromRobots(rc, enemies);
        }

        // Alternate wandering and staying grouped with friendlies
        RobotInfo[] friendlies = rc.senseNearbyRobots(-1, ourTeam);
        if (rc.getRoundNum() % 2 == 0 || friendlies.length == 0) {
            Pathing.moveRandomly(rc);
            Pathing.moveRandomly(rc);
            Pathing.moveRandomly(rc);
        }
        else {
            moveTowardsRobots(rc, friendlies);
            moveTowardsRobots(rc, friendlies);
            moveTowardsRobots(rc, friendlies);
        }

        // Sense islands
        Comms.updateIslands(rc);
    }
}