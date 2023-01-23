package tacoplayer;

import battlecode.common.*;

import static tacoplayer.RobotPlayer.closestEnemyIslandLoc;
import static tacoplayer.RobotPlayer.theirTeam;

public class LauncherStrategy {
    /**
     * Run a single turn for a Launcher.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    static void runLauncher(RobotController rc) throws GameActionException {
        // Update alive counter
        Comms.updateRobotCount(rc);

        // Attack
        attackEnemies(rc);

        // Movement
        moveTowardsEnemyIslands(rc);
        RobotPlayer.moveTowardsEnemies(rc);
        Pathing.moveRandomly(rc);

        // Update islands - only every 4 rounds, its expensive and not necessary every round
        if (rc.getRoundNum() % 4 == 0) {
            closestEnemyIslandLoc = Comms.getClosestEnemyIsland(rc);
            int b1 = Clock.getBytecodesLeft();
            Comms.updateIslands(rc);
//            System.out.println("bc: " + String.valueOf(b1 - Clock.getBytecodesLeft()));
        }
    }

    private static void moveTowardsEnemyIslands(RobotController rc) throws GameActionException {
        if (closestEnemyIslandLoc != null) {
            rc.setIndicatorString("Moving towards enemy island! " + closestEnemyIslandLoc);
            Pathing.moveTowards(rc, closestEnemyIslandLoc);
        }
    }

    private static void attackEnemies(RobotController rc) throws GameActionException {
        int radius = RobotType.LAUNCHER.actionRadiusSquared;
        RobotInfo[] enemies = rc.senseNearbyRobots(radius, theirTeam);
        int lowestHealth = 1000;
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