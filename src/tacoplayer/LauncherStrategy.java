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

        // Update health
        updateHealth(rc);

        // Attack
        Combat.attack(rc);
        Combat.retreat(rc);

        if (!retreatMode) {
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
                        leader_id = alliedRobots[i - 1].getID();
                        leader = alliedRobots[i - 1];
                    }
                    launchersNearby++;
                }
            }
            // not enough launchers nearby
            if (launchersNearby < 3) {
                if (closestHqLoc != null) {
                    if (rc.canMove(rc.getLocation().directionTo(closestHqLoc))) {
                        rc.move(rc.getLocation().directionTo(closestHqLoc));
                        rc.setIndicatorString("moving towards enemy HQ");
                    }
                } else {
                    Pathing.moveRandomly(rc);
                    rc.setIndicatorString("moving randomly");
                }
            }
            // I am the leader!
            else if (leader_id > rc.getID()) {
                rc.setIndicatorString("I am a leader!");
                if (Movement.moveTowardsEnemies(rc)) {
                    rc.setIndicatorString("moving towards enemy robots");
                } else if (Movement.moveTowardsEnemyIslands(rc)) {
                    rc.setIndicatorString("moving towards enemy island");
                } else if (Movement.moveTowardsEnemyHq(rc)) {
                    rc.setIndicatorString("moving towards enemy hq");
                } else if (rc.canMove(rc.getLocation().directionTo(mapCenter))) {
                    rc.move(rc.getLocation().directionTo(mapCenter));
                    rc.setIndicatorString("moving towards center");
                } else {
                    Pathing.moveRandomly(rc);
                    rc.setIndicatorString("moving randomly");
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

            // Update islands - only every 4 rounds, it's expensive and not necessary every round
            if (rc.getRoundNum() % 4 == 0) {
                Sensing.scanIslands(rc);
            }
        }
    }
}