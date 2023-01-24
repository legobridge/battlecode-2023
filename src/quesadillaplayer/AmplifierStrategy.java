package quesadillaplayer;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;

import static quesadillaplayer.RobotPlayer.closestEnemyIslandLoc;

public class AmplifierStrategy {
    /**
     * Run a single turn for an Amplifier.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    static void runAmplifier(RobotController rc) throws GameActionException {
        // Update alive counter
        Comms.updateRobotCount(rc);

        RobotPlayer.moveTowardsEnemies(rc, 25);
        Pathing.moveRandomly(rc);
        if (rc.getRoundNum() % 4 == 0) {
            closestEnemyIslandLoc = Comms.getClosestEnemyIsland(rc);
            int b1 = Clock.getBytecodesLeft();
            Comms.updateIslands(rc);
//            System.out.println("bc: " + String.valueOf(b1 - Clock.getBytecodesLeft()));
        }
    }

}