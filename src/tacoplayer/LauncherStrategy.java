package tacoplayer;

import battlecode.common.*;

import static tacoplayer.RobotPlayer.*;

public class LauncherStrategy {
    /**
     * Run a single turn for a Launcher.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    static void runLauncher(RobotController rc) throws GameActionException {
        // Update alive counter
        Comms.updateRobotCount(rc);

        closestEnemyIslandLoc = MapLocationUtil.getClosestIslandMapLocEuclidean(rc, knownIslands, theirTeam);

        // move together
        // sense nearby robots
        RobotInfo[] alliedRobots = rc.senseNearbyRobots(-1, ourTeam);
        int launchersNearby = 0;
        // go through the nearby robots (launchers) and assign yourself as leader or follow a leader
        int leader_id = Integer.MAX_VALUE;
        RobotInfo leader = null;
        for (int i = 0; i++ < alliedRobots.length; ) {
            if (alliedRobots[i - 1].getType() == RobotType.LAUNCHER) {
                if (alliedRobots[i - 1].getID() < leader_id) {
                    leader_id = alliedRobots[i -1].getID();
                    leader = alliedRobots[i - 1];
                    launchersNearby++;
                }
            }
        }
        // not enough launchers nearby
        if (launchersNearby < 3) {
            Pathing.moveRandomly(rc);
            rc.setIndicatorString("moving randomly");
//            // move towards our own closest HQ
//            int leastDistance = Integer.MAX_VALUE;
//            MapLocation targetHqLoc = null;
//            for (int i = 0; i++ < ourHqLocs.length; ) {
//                if (ourHqLocs[i -1] != null) {
//                    if (rc.getLocation().distanceSquaredTo(ourHqLocs[i - 1]) < leastDistance) {
//                        leastDistance = rc.getLocation().distanceSquaredTo(ourHqLocs[i - 1]);
//                        targetHqLoc = ourHqLocs[i - 1];
//                    }
//                }
//            }
//            if (targetHqLoc != null) {
//                Direction dir = rc.getLocation().directionTo(targetHqLoc);
//                if (rc.canMove(dir)) {
//                    rc.move(dir);
//                    rc.setIndicatorString("moving towards closest home hq");
//                }
//                else {
//                    Pathing.moveRandomly(rc);
//                    rc.setIndicatorString("moving randomly");
//                }
//            }
//            else {
//                Pathing.moveRandomly(rc);
//                rc.setIndicatorString("moving randomly");
//            }
        }
        // I am the leader!
        else if (leader_id > rc.getID()) {
            rc.setIndicatorString("I am a leader!");
            if (moveTowardsEnemies(rc)) {
//                System.out.print("enemy Bot");
                rc.setIndicatorString("moving towards enemy robots");
            }
            else {
//                if (moveTowardsEnemyHq(rc)) {
//                    System.out.print("enemy HQ");
//                    rc.setIndicatorString("moving towards enemy hq");
//                }
//                else {
//                    if (rc.canMove(rc.getLocation().directionTo(mapCenter))) {
//                        rc.move(rc.getLocation().directionTo(mapCenter));
//                        rc.setIndicatorString("moving towards center");
//                    }
//                    else {
//                        Pathing.moveRandomly(rc);
//                        rc.setIndicatorString("moving randomly");
//                    }
//                }
                if (moveTowardsEnemyIslands(rc)) {
                    rc.setIndicatorString("moving towards enemy island");
                }
                else if (rc.canMove(rc.getLocation().directionTo(mapCenter))) {
//                    System.out.print("Center");
                    rc.move(rc.getLocation().directionTo(mapCenter));
                    rc.setIndicatorString("moving towards center");
                }
                else {
//                    System.out.print("Random");
                    Pathing.moveRandomly(rc);
                    rc.setIndicatorString("moving randomly");
                }
            }
        }
        // follow the leader
        else {
            Direction dir = rc.getLocation().directionTo(leader.getLocation());
            if (rc.canMove(dir)) {
                rc.move(dir);
//                System.out.print("Heil!");
                rc.setIndicatorString("moving towards leader " + leader_id);
            } else {
                Pathing.moveRandomly(rc);
                rc.setIndicatorString("moving randomly");
            }
        }

        // Attack
        attackEnemies(rc);

        // Movement
        // moveTowardsEnemyIslands(rc);
        // moveTowardsEnemies(rc);
        // moveTowardsEnemyHq(rc);
        // Pathing.moveRandomly(rc);
    }

    private static boolean moveTowardsEnemyIslands(RobotController rc) throws GameActionException {
        if (closestEnemyIslandLoc != null) {
            rc.setIndicatorString("Moving towards enemy island! " + closestEnemyIslandLoc);
            Pathing.moveTowards(rc, closestEnemyIslandLoc);
            return true;
        }
        return false;
    }

    private static void attackEnemies(RobotController rc) throws GameActionException {
        int radius = RobotType.LAUNCHER.actionRadiusSquared;
        RobotInfo[] enemies = rc.senseNearbyRobots(radius, theirTeam);
        int lowestHealth = Integer.MAX_VALUE;
        int smallestDistance = Integer.MAX_VALUE;
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

    private static boolean moveTowardsEnemyHq(RobotController rc) throws GameActionException {
        if (closestEnemyHqLoc != null) {
            rc.setIndicatorString("Moving towards enemy HQ! " + closestEnemyHqLoc);
            Pathing.moveTowards(rc, closestEnemyHqLoc);
            return true;
        }
        return false;
    }

}