package tacoplayer;

import battlecode.common.*;
import static tacoplayer.RobotPlayer.*;

import java.awt.*;

public class Combat {
    static int MAGIC_DIVE_HEALTH = 40;
    static int MAGIC_RETREAT_HEALTH = 20;

    /** attack mode */
    static void attack(RobotController rc) throws GameActionException {
        RobotInfo[] enemies = rc.senseNearbyRobots(-1, theirTeam);
        if (enemies.length == 0) {
            return;
        }

        MapLocation ourLoc = rc.getLocation();
        MapLocation closestEnemyLoc = null;
        MapLocation lowestLauncherLoc = null;
        MapLocation lowestHealthLoc = null;
        RobotType closestEnemyType = null;
        int closestEnemyDistSq = Integer.MAX_VALUE;
        int lowestLauncherDistSq = Integer.MAX_VALUE;
        int lowestHealth = Integer.MAX_VALUE;
        int numLaunchers = 0;
        int numEnemies = 0;

        // Go through nearby enemies
        for (int i = 0; i < enemies.length; i++) {
            // Get info from enemy
            RobotInfo enemy = enemies[i];
            MapLocation enemyLoc = enemy.getLocation();
            RobotType enemyType = enemy.getType();
            int distSqToEnemy = ourLoc.distanceSquaredTo(enemyLoc);
            int enemyHealth = enemy.getHealth();
            boolean isHQ = enemyType == RobotType.HEADQUARTERS;

            // Update enemy count
            if (!isHQ) {
                numEnemies++;
            }

            // Update the launcher count
            if (enemyType == RobotType.LAUNCHER) {
                numLaunchers++;
            }

            // Update closest enemy
            if (distSqToEnemy < closestEnemyDistSq && !isHQ) {
                closestEnemyLoc = enemyLoc;
                closestEnemyType = enemyType;
                closestEnemyDistSq = distSqToEnemy;
            }

            // Update closest enemy launcher
            if (enemyType == RobotType.LAUNCHER && distSqToEnemy < lowestLauncherDistSq && !isHQ) {
                lowestLauncherLoc = enemyLoc;
                lowestLauncherDistSq = distSqToEnemy;
            }

            // Update lowest health enemy
            if (enemyHealth < lowestHealth && !isHQ) {
                lowestHealthLoc = enemyLoc;
                lowestHealth = enemyHealth;
            }


        }
        // If only HQs were found, exit
        if (numEnemies == 0) {
            return;
        }

        MapLocation closestEnemyHQ = Pathing.getNearestEnemyHQLoc(rc);

        // If retreat mode, attack nearest enemy if in range and keep haulin ass
        // else if enemy is low on health and within movement range, dive and attack it
        // else kite
        if (retreatMode) {
            // Prioritize attack launchers
            rc.setIndicatorString("RETREAT");
            if (lowestLauncherLoc != null) {
                tryAttack(rc, lowestLauncherLoc);
            }
            else {
                tryAttack(rc, closestEnemyLoc);
            }
        }
        if ((lowestHealth <= MAGIC_DIVE_HEALTH || numLaunchers == 0) && Pathing.safeFromHQ(rc, closestEnemyHQ)) {
            // Prioritize attack launchers
            rc.setIndicatorString("DIVE");
            attackDive(rc, lowestHealthLoc);
        }
        else {
            // Prioritize attack launchers
            rc.setIndicatorString("KITE");
            if (lowestLauncherLoc != null) {
                attackKite(rc, closestEnemyType, lowestLauncherLoc, closestEnemyDistSq);
            }
            else {
                attackKite(rc, closestEnemyType, lowestHealthLoc, closestEnemyDistSq);
            }
        }

        // Try attacking closest enemy if no attack was done
        tryAttack(rc, closestEnemyLoc);
    }

    /** Mission failed, we'll get em next time */
    static void retreat(RobotController rc) throws GameActionException {
        // If low health begin retreat
        if (rc.getHealth() <= MAGIC_RETREAT_HEALTH && closestFriendlyIslandLoc != null) {
            retreatMode = true;
        }
        // If not in retreat or fully healed, end retreat
        if (!retreatMode || rc.getHealth() == rc.getType().getMaxHealth()) {
            retreatMode = false;
            return;
        }

        // If you're at an island, stay there until healed
        // else move to nearest friendly HQ if you haven't reached one since retreat mode was activated
        // If you have reached one, wander until you find a friendly island
        if (!isHealing(rc)) {
//            MapLocation islandLoc = Comms.getClosestFriendlyIsland(rc);
//            if (islandLoc == null) {
//                //TODO - add move randomly to movement class
//            }
//            else {
//                Movement.moveTowardsLocation(rc, islandLoc);
//            }
            Movement.moveTowardsLocation(rc, closestFriendlyIslandLoc);
            System.out.println("RETREAT");
        }
    }

    /** strategically move in and out of enemy range to prevent taking damage */
    static boolean attackKite(RobotController rc, RobotType enemyType,
                           MapLocation closestEnemyLoc, int distSqToEnemy)
            throws GameActionException {
        boolean attackSuccessful = false;
        // If you are within radius of an enemy and can attack, do so
        attackSuccessful = tryAttack(rc, closestEnemyLoc);

        // If you are inside enemy range, try to get out
        // If you are outside enemy range, move in range and attack
        if (distSqToEnemy <= enemyType.actionRadiusSquared) {
            Movement.moveAwayFromLocation(rc, closestEnemyLoc);
        }
        else {
            Movement.moveTowardsLocation(rc, closestEnemyLoc);
            attackSuccessful = tryAttack(rc, closestEnemyLoc);
        }
        return attackSuccessful;

    }

    /** Kill that bitch at all costs */
    static boolean attackDive(RobotController rc, MapLocation attackLoc) throws GameActionException {
        // WHY ARE YOU RUNNING?
        Movement.moveTowardsLocation(rc, attackLoc);

        // GET HIS ASS
        return tryAttack(rc, attackLoc);
    }

    /** Attack nearest enemy if possible */
    static boolean tryAttack(RobotController rc, MapLocation attackLoc) throws GameActionException {
        if (rc.canAttack(attackLoc)) {
            rc.attack(attackLoc);
            return true;
        }
        return false;
    }
}
