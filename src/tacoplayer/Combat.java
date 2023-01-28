package tacoplayer;

import battlecode.common.*;
import static tacoplayer.RobotPlayer.*;
import static tacoplayer.Sensing.*;

public class Combat {
    static int MAGIC_DIVE_HEALTH = 40;
    static int MAGIC_RETREAT_HEALTH = 20;

    /** attack mode */
    static void attack(RobotController rc) throws GameActionException {
        RobotInfo[] enemies = rc.senseNearbyRobots(-1, theirTeam); // TODO - use universal sensing
        if (visibleEnemiesCount == 0) {
            attackMode = false;
            return;
        }

        if (enemyLauncherCount > ourLauncherCount) {
            runawayMode = true;
        } else {
            runawayMode = false;
        }

        // If retreat mode, attack nearest enemy if in range and keep haulin ass
        // else if enemy is low on health and within movement range, dive and attack it
        // else kite
        if (retreatMode || runawayMode) {
            // Prioritize attack launchers
            rc.setIndicatorString("RETREAT");
            if (lowestHealthEnemyLauncherLoc != null) {
                tryAttack(rc, lowestHealthEnemyLauncherLoc);
            }
            else if (lowestHealthEnemyInRangeLoc != null) {
                tryAttack(rc, lowestHealthEnemyInRangeLoc);
            }
            else {
                tryAttack(rc, closestEnemyLoc);
            }
        }
        if ((lowestHealthEnemyHealth <= MAGIC_DIVE_HEALTH || enemyLauncherCount == 0) && Pathing.safeFromHQ(rc, closestEnemyHqLoc)) {
            // Prioritize attack launchers
            rc.setIndicatorString("DIVE");
            attackDive(rc, lowestHealthEnemyLoc);
        }
        else {
            // Prioritize attack launchers
            rc.setIndicatorString("KITE");
            if (lowestHealthEnemyLauncherLoc != null) {
                attackKite(rc, RobotType.LAUNCHER, lowestHealthEnemyLauncherLoc, closestEnemyDistSq);
            }
            else {
                attackKite(rc, lowestHealthEnemy.getType(), lowestHealthEnemyLoc, closestEnemyDistSq);
            }
        }

        // Try attacking closest enemy if no attack was done
        tryAttack(rc, closestEnemyLoc);
    }

    static void carrierAttack(RobotController rc) throws GameActionException {
        int damage = 5 * rc.getResourceAmount(ResourceType.ADAMANTIUM) / 4;
        if (lowestHealthEnemyInRangeHealth <= damage) {
            tryAttack(rc, lowestHealthEnemyInRangeLoc);
        }
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
        if (!isHealing()) {
//            if (islandLoc == null) {
//                //TODO - add move randomly to movement class
//            }
//            else {
//                Movement.moveTowardsLocation(rc, islandLoc);
//            }
            Movement.moveTowardsLocation(rc, closestFriendlyIslandLoc);
        }
    }

    /** We won't win this one. Regroup and re-arm */
    static void runaway(RobotController rc) throws GameActionException {
        if (!runawayMode) {
            return;
        }
        Direction enemyDir = rc.getLocation().directionTo(enemyLaunchers[0].getLocation()).opposite();
        if (Movement.moveDirectlyTowards(rc, enemyDir)) {
            rc.setIndicatorString("Avoiding a fight");
        }
        else if (closestFriendlyIslandLoc != null) {
            Movement.moveTowardsLocation(rc, closestFriendlyIslandLoc);
        }
        else {
            Movement.moveTowardsLocation(rc, closestHqLoc);
        }
        runawayMode = false;
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
            attackMode = true;
            return true;
        }
        return false;
    }

    static boolean isMoveOutOfRange(RobotController rc, MapLocation enemyLoc, MapLocation newLoc) {
        if (newLoc.distanceSquaredTo(enemyLoc) > rc.getType().actionRadiusSquared) {
            return true;
        }
        return false;
    }
}
