package tacoplayer;

import battlecode.common.*;

import static tacoplayer.RobotPlayer.*;
import static tacoplayer.Movement.*;
import static tacoplayer.Sensing.*;

public class LauncherStrategy {

    static int LAUNCHER_BATTALION_SIZE = 3;
    static int prevLowestHealth = 0;
    static int prevLowestId;

    // TODO - move first attack later or attack first and move later?
    static void runLauncher(RobotController rc) throws GameActionException {
        // Update alive counter
        Comms.updateRobotCount(rc);

        // Update health
        updateHealth(rc);

        // Attack
        Combat.attack(rc);
        Combat.retreat(rc);
        Combat.runaway(rc);
//
//        // Defend well
//        if (!attackMode && ourCarrierCount == 0) {
//            MapLocation wellAttackLoc = Comms.getNearestAttackWell(rc);
//            if (wellAttackLoc != null) {
//                System.out.println("DEFEND WELL " + String.valueOf(wellAttackLoc.x) + ", " + String.valueOf(wellAttackLoc.y));
//                moveTowardsLocation(rc, wellAttackLoc);
//            }
//        }

        // Move
        if (!retreatMode) {
            move(rc);
        }
    }

    private static void move(RobotController rc) throws GameActionException {
        // Move together
        // Go through the nearby launchers and elect a leader
        int leaderId = Integer.MAX_VALUE;
        int lowestHealth = Integer.MAX_VALUE;
        int lowestId = Integer.MAX_VALUE;
        RobotInfo leader = null;
        RobotInfo lowestHealthLauncher = null;
        for (int i = -1; ++i < ourLauncherCount; ) {
            if (ourLaunchers[i].getID() < leaderId) {
                leaderId = ourLaunchers[i].getID();
                leader = ourLaunchers[i];
            }
            if (ourLaunchers[i].getHealth() < lowestHealth) {
                lowestHealth = ourLaunchers[i].getHealth();
                lowestHealthLauncher = ourLaunchers[i];
                lowestId = ourLaunchers[i].getID();
            }
        }
        if (rc.getHealth() < (int) ((double) rc.getType().getMaxHealth() * MAGIC_NUM_HEAL_PERCENT)
                && !attackMode
                && closestFriendlyIslandLoc != null) {
            // Go heal if below a certain percentage of health
            rc.setIndicatorString("Moving towards island to heal");
            moveTowardsLocation(rc, closestFriendlyIslandLoc);
        }
        else if (lowestHealth < prevLowestHealth && lowestId == prevLowestId) {
            rc.setIndicatorString("Moving in as backup");
            moveDirectlyTowards(rc, lowestHealthLauncher.getLocation());
        }
        else if (ourLauncherCount < LAUNCHER_BATTALION_SIZE) { // Not enough launchers nearby
            if (closestHqLoc != null) {
                rc.setIndicatorString("Moving towards own HQ");
                moveTowardsLocation(rc, closestHqLoc);
            }
        }
//        else if (leaderId > rc.getID()) { // I am the leader!
        else if (true) {
            rc.setIndicatorString("I am a leader!");
            if (moveTowardsVisibleEnemies(rc)) {
                rc.setIndicatorString("moving towards enemy robots");
            } else if (moveTowardsEnemyIslands(rc)) {
                rc.setIndicatorString("moving towards enemy island");
            } else if (moveTowardsEnemyHq(rc)) {
                rc.setIndicatorString("moving towards enemy hq");
            } else if (rc.getRoundNum() % 3 != 0) {
                moveTowardsCenter(rc);
                rc.setIndicatorString("moving towards center");
            } else {
                Pathing.moveRandomly(rc);
                rc.setIndicatorString("moving randomly");
            }
        }
        // Follow the leader
        else {
            if (moveDirectlyTowards(rc, leader.getLocation())) {
                rc.setIndicatorString("moving towards leader " + leaderId);
            }else if (rc.getRoundNum() % 3 != 0){
                moveTowardsCenter(rc);
                rc.setIndicatorString("moving towards center");
            } else {
                Pathing.moveRandomly(rc);
                rc.setIndicatorString("moving randomly");
            }
        }
        prevLowestHealth = lowestHealth;
        prevLowestId = lowestId;
    }
}