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
        moveTowardsEnemies(rc);
        moveAwayFromFarthestAlly(rc);
//        moveTowardsFarthestAlly(rc);
        moveTowardsClosestWell(rc);
        RobotPlayer.moveRandom(rc);
    }

    private static void moveTowardsEnemies(RobotController rc) throws GameActionException {
        RobotInfo[] visibleEnemies = rc.senseNearbyRobots(-1, RobotPlayer.theirTeam);
        for (RobotInfo enemy : visibleEnemies) {
            MapLocation enemyLocation = enemy.getLocation();
            MapLocation robotLocation = rc.getLocation();
            Direction moveDir = robotLocation.directionTo(enemyLocation);
            if (rc.canMove(moveDir)) {
                rc.move(moveDir);
                break;
            }
        }
    }

    private static void moveAwayFromFarthestAlly(RobotController rc) throws GameActionException {
        MapLocation targetLoc = null;
        int targetDistSq = 0;
        MapLocation selfLoc = rc.getLocation();

        RobotInfo[] visibleAllies = rc.senseNearbyRobots(-1, RobotPlayer.ourTeam);
        for (RobotInfo ally : visibleAllies) {
            MapLocation allyLoc = ally.getLocation();
            int allyDistSq = selfLoc.distanceSquaredTo(allyLoc);
            if (allyDistSq > targetDistSq) {
                targetLoc = allyLoc;
                targetDistSq = allyDistSq;
            }
        }
        if (targetLoc != null) {
            Direction moveDir = selfLoc.directionTo(targetLoc).opposite();
            if (rc.canMove(moveDir)) {
                rc.move(moveDir);
            }
        }
    }

    private static void moveTowardsFarthestAlly(RobotController rc) throws GameActionException {
        MapLocation targetLoc = null;
        int targetDistSq = 0;
        MapLocation selfLoc = rc.getLocation();

        RobotInfo[] visibleAllies = rc.senseNearbyRobots(-1, RobotPlayer.ourTeam);
        for (RobotInfo ally : visibleAllies) {
            MapLocation allyLoc = ally.getLocation();
            int allyDistSq = selfLoc.distanceSquaredTo(allyLoc);
            if (allyDistSq > targetDistSq) {
                targetLoc = allyLoc;
                targetDistSq = allyDistSq;
            }
        }
        if (targetLoc != null) {
            Direction moveDir = selfLoc.directionTo(targetLoc);
            if (rc.canMove(moveDir)) {
                rc.move(moveDir);
            }
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

    private static void moveTowardsClosestWell(RobotController rc) throws GameActionException {
        if (RobotPlayer.closestWellLoc != null) {
            Direction dir = rc.getLocation().directionTo(RobotPlayer.closestWellLoc);
            if (rc.canMove(dir)) {
                rc.move(dir);
            }
        }
    }
}