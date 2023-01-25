package tacoplayer;

import battlecode.common.*;

import static tacoplayer.RobotPlayer.*;
import static tacoplayer.Movement.*;
import static tacoplayer.Sensing.*;

public class LauncherStrategy {

    static int LAUNCHER_BATTALION_SIZE = 3;

    // TODO - move first attack later or attack first and move later?
    static void runLauncher(RobotController rc) throws GameActionException {
        // Update alive counter
        Comms.updateRobotCount(rc);

        // Movement
        move(rc);

        // Attack
        attackEnemies(rc);
    }

    private static void move(RobotController rc) throws GameActionException {
        // Move together
        // Go through the nearby launchers and elect a leader
        int leaderId = Integer.MAX_VALUE;
        RobotInfo leader = null;
        for (int i = -1; ++i < ourLauncherCount; ) {
            if (ourLaunchers[i].getID() < leaderId) {
                leaderId = ourLaunchers[i].getID();
                leader = ourLaunchers[i];
            }
        }
        if (ourLauncherCount < LAUNCHER_BATTALION_SIZE) { // Not enough launchers nearby
            if (closestHqLoc != null) {
                rc.setIndicatorString("Moving towards own HQ");
                moveTowardsLocation(rc, closestHqLoc);
            }
        }
        else if (leaderId > rc.getID()) { // I am the leader!
            rc.setIndicatorString("I am a leader!");
            if (moveTowardsEnemies(rc)) {
                rc.setIndicatorString("moving towards enemy robots");
            } else if (moveTowardsEnemyIslands(rc)) {
                rc.setIndicatorString("moving towards enemy island");
            } else if (moveTowardsEnemyHq(rc)) {
                rc.setIndicatorString("moving towards enemy hq");
            } else if (rc.canMove(rc.getLocation().directionTo(mapCenter))) {
                rc.move(rc.getLocation().directionTo(mapCenter));
                rc.setIndicatorString("moving towards center");
            } else {
                Pathing.moveRandomly(rc);
                rc.setIndicatorString("moving randomly");
            }
        }
        // Follow the leader
        else {
            Direction dir = rc.getLocation().directionTo(leader.getLocation());
            if (rc.canMove(dir)) {
                rc.move(dir);
                rc.setIndicatorString("moving towards leader " + leaderId);
            } else {
                Pathing.moveRandomly(rc);
                rc.setIndicatorString("moving randomly");
            }
        }
    }

    private static void attackEnemies(RobotController rc) throws GameActionException {
        RobotInfo[] enemies = rc.senseNearbyRobots(-1, theirTeam); // TODO - use unified sensing
        // get lowest health launcher, carrier, amp, destabs, and boosters in this pass
        RobotInfo carrierTarget = null;
        int lowestHPCarrier = Integer.MAX_VALUE;
        RobotInfo ampTarget = null;
        int lowestHPAmp = Integer.MAX_VALUE;
        RobotInfo destabTarget = null;
        int lowestHPDestab = Integer.MAX_VALUE;
        RobotInfo boosterTarget = null;
        int lowestHPBooster = Integer.MAX_VALUE;
        RobotInfo launcherTarget = null;
        int lowestHPLauncher = Integer.MAX_VALUE;
        RobotInfo carrierWithAccAnchorTarget = null;
        int lowestHPCarrierWithAccAnchor = Integer.MAX_VALUE;
        RobotInfo carrierWithStdAnchorTarget = null;
        int lowestHPCarrierWithStdAnchor = Integer.MAX_VALUE;
        for (int i = 0; i++ < enemies.length; ) {
            int enemyHealth = enemies[i - 1].getHealth();
            switch (enemies[i - 1].getType()) {
                case LAUNCHER:
                    if (enemyHealth < lowestHPLauncher) {
                        lowestHPLauncher = enemyHealth;
                        launcherTarget = enemies[i - 1];
                    }
                    break;

                case CARRIER:
                    if (enemyHealth < lowestHPCarrierWithAccAnchor) {
                        lowestHPCarrierWithAccAnchor = enemyHealth;
                        carrierWithAccAnchorTarget = enemies[i - 1];
                    }
                    if (enemyHealth < lowestHPCarrierWithStdAnchor) {
                        lowestHPCarrierWithStdAnchor = enemyHealth;
                        carrierWithStdAnchorTarget = enemies[i - 1];
                    }
                    if (enemyHealth < lowestHPCarrier) {
                        lowestHPCarrier = enemyHealth;
                        carrierTarget = enemies[i - 1];
                    }
                    break;

                case AMPLIFIER:
                    if (enemyHealth < lowestHPAmp) {
                        lowestHPAmp = enemyHealth;
                        ampTarget = enemies[i - 1];
                    }
                    break;

                case DESTABILIZER:
                    if (enemyHealth < lowestHPDestab) {
                        lowestHPDestab = enemyHealth;
                        destabTarget = enemies[i - 1];
                    }
                    break;

                case BOOSTER:
                    if (enemyHealth < lowestHPBooster) {
                        lowestHPBooster = enemyHealth;
                        boosterTarget = enemies[i - 1];
                    }
                    break;
            }
            if (carrierWithAccAnchorTarget != null) {
                if (rc.canAttack(carrierWithAccAnchorTarget.getLocation())) {
                    rc.attack(carrierWithAccAnchorTarget.getLocation());
                    rc.setIndicatorString("attacking a carrier with acc anchor!");
                }
            }
            else if (carrierWithStdAnchorTarget != null) {
                if (rc.canAttack(carrierWithStdAnchorTarget.getLocation())) {
                    rc.attack(carrierWithStdAnchorTarget.getLocation());
                    rc.setIndicatorString("attacking a carrier with std anchor!");
                }
            }
            else if (destabTarget != null) {
                if (rc.canAttack(destabTarget.getLocation())) {
                    rc.attack(destabTarget.getLocation());
                    rc.setIndicatorString("attacking a destab!");
                }
            }
            else if (boosterTarget != null) {
                if (rc.canAttack(boosterTarget.getLocation())) {
                    rc.attack(boosterTarget.getLocation());
                    rc.setIndicatorString("attacking a booster!");
                }
            }
            else if (ampTarget != null) {
                if (rc.canAttack(ampTarget.getLocation())) {
                    rc.attack(ampTarget.getLocation());
                    rc.setIndicatorString("attacking an amp!");
                }
            }
            else if (launcherTarget != null) {
                if (rc.canAttack(launcherTarget.getLocation())) {
                    rc.attack(launcherTarget.getLocation());
                    rc.setIndicatorString("attacking a launcher!");
                }
            }
            else if (carrierTarget != null) {
                if (rc.canAttack(carrierTarget.getLocation())) {
                    rc.attack(carrierTarget.getLocation());
                    rc.setIndicatorString("attacking a carrier!");
                }
            }
            else {
                rc.setIndicatorString("nothing to attack");
            }
        }
    }
}