package tacoplayer;

import battlecode.common.*;

public class LauncherStrategy {
    /**
     * Run a single turn for a Launcher.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    static void runLauncher(RobotController rc) throws GameActionException {
        // Attack
        attackEnemies(rc);

        // Movement
        moveTowardsEnemyIslands(rc);
        moveTowardsEnemies(rc);
        Pathing.moveRandomly(rc);
    }

    private static void moveTowardsEnemyIslands(RobotController rc) throws GameActionException {
        if (RobotPlayer.closestEnemyIslandLoc != null) {
            Pathing.moveTowards(rc, RobotPlayer.closestEnemyIslandLoc);
        }
    }

    private static void moveTowardsEnemies(RobotController rc) throws GameActionException {
        RobotInfo[] visibleEnemies = rc.senseNearbyRobots(-1, RobotPlayer.theirTeam);
        if (visibleEnemies.length != 0) {
            MapLocation enemyLocation = visibleEnemies[0].getLocation();
            Pathing.moveTowards(rc, enemyLocation);
        }
    }

    private static void attackEnemies(RobotController rc) throws GameActionException {
        int radius = RobotType.LAUNCHER.actionRadiusSquared;
        RobotInfo[] enemies = rc.senseNearbyRobots(radius, RobotPlayer.theirTeam);
        int lowestHealth = 100;
        int smallestDistance = 100;
        RobotInfo target = null;
        if (enemies.length > 0) {
            for (RobotInfo enemy : enemies) {
                int enemyHealth = enemy.getHealth();
                int enemyDistance = enemy.getLocation().distanceSquaredTo(rc.getLocation());
                if (enemyHealth < lowestHealth) {
                    target = enemy;
                    lowestHealth = enemyHealth;
                    smallestDistance = enemyDistance;
                } else if (enemyHealth == lowestHealth) {
                    if (enemyDistance < smallestDistance) {
                        target = enemy;
                        smallestDistance = enemyDistance;
                    }
                }
            }
            if (target != null) {
                if (rc.canAttack(target.getLocation())) {
                    rc.attack(target.getLocation());
                }
            }
        }
    }
}