package tacoplayer;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

import static tacoplayer.RobotPlayer.*;
import static tacoplayer.Sensing.*;

public class AmplifierStrategy {

    static MapLocation targetLoc;
    static int movingTogetherTurns;
    static int movingAwayTurns;
    static MapLocation lastSeenAmpLoc;
    /**
     * Run a single turn for an Amplifier.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    static void runAmplifier(RobotController rc) throws GameActionException {
        // Update alive counter
        Comms.updateRobotCount(rc);
        // MUST move away from enemies
        if (enemyLauncherCount + enemyDestabCount > ourLauncherCount) {
            Movement.moveTowardsLocation(rc, closestHqLoc);
            rc.setIndicatorString("moving towards our closest HQ");
        }
        // if we see another amp move away from it
        else if (movingTogetherTurns < 10) {
            if (ourAmplifierCount > 0) {
                Movement.moveAwayFromLocation(rc, ourAmplifiers[0].getLocation());
                lastSeenAmpLoc = ourAmplifiers[0].getLocation();
                movingTogetherTurns++;
                rc.setIndicatorString("moving away from friendly amp");
            }
            else {
                // default move to the targetLoc (default is farthest location)
                Movement.moveTowardsLocation(rc, targetLoc);
                rc.setIndicatorString("moving towards [" + targetLoc.x + ", " + targetLoc.y + "]");
            }
        }
        else if (movingAwayTurns < 10) {
            // move away from friendly amp for 10 turns
            Movement.moveAwayFromLocation(rc, lastSeenAmpLoc);
            movingTogetherTurns = 0;
            movingAwayTurns++;
            rc.setIndicatorString("still moving away from friendly amp");
        }
        else {
            // get new target location
            assignFarthestLocation(rc);
            Movement.moveTowardsLocation(rc, targetLoc);
            rc.setIndicatorString("newly moving towards [" + targetLoc.x + ", " + targetLoc.y + "]");
            movingAwayTurns = 0;
        }
    }
    static public void assignFarthestLocation(RobotController rc) {
        if (rc.getLocation().x > mapCenter.x) {
            if (rc.getLocation().y > mapCenter.y) {
                AmplifierStrategy.targetLoc = new MapLocation(4, 4);
            } else {
                AmplifierStrategy.targetLoc = new MapLocation(4, mapHeight - 1 - 4);
            }
        } else {
            if (rc.getLocation().y > mapCenter.y) {
                AmplifierStrategy.targetLoc = new MapLocation(mapWidth - 1 - 4, 4);
            } else {
                AmplifierStrategy.targetLoc = new MapLocation(mapWidth - 1 - 4, mapHeight - 1 - 4);
            }
        }
    }
}