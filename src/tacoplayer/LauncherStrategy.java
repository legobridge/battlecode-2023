package tacoplayer;

import battlecode.common.*;

import static tacoplayer.RobotPlayer.*;
import static tacoplayer.Movement.*;
import static tacoplayer.Sensing.*;

public class LauncherStrategy {

    static int LAUNCHER_BATTALION_SIZE = 3;
    static int prevRoundLeaderID = -1;
    static int prevRoundLeaderHealth = 100;
    static MapLocation prevRoundLeaderLoc;
    static int roundsWithoutPrevLeader = 0;
    static int MAGIC_NUM_HITS_TO_ASSUME_DEAD = 2;
    static int MAGIC_ROUNDS_TO_ELECT_NEW_LEADER = 5;

    // TODO - update batallion size dependent on how far the enemyHQs are?
    static void runLauncher(RobotController rc) throws GameActionException {
        // Update alive counter
        Comms.updateRobotCount(rc);

        // Update health
        updateHealth(rc);

        // Attack
        Combat.attack(rc);
        Combat.retreat(rc);

        // Move
        if (!retreatMode) {
            move(rc);
        }
    }

    private static void move(RobotController rc) throws GameActionException {
        // Move together
        // Go through the nearby launchers and elect a leader
        int leaderId = Integer.MAX_VALUE;
        int leaderHealth = 100;
        MapLocation leaderLoc = null;
        RobotInfo leader = null;
        for (int i = -1; ++i < ourLauncherCount; ) {
            if (ourLaunchers[i].getID() < leaderId) {
                leaderId = ourLaunchers[i].getID();
                leader = ourLaunchers[i];
                leaderHealth = leader.getHealth();
                leaderLoc = leader.getLocation();
            }
        }

        // Check if the leader has changed
        if (leaderId != prevRoundLeaderID && prevRoundLeaderID != -1) {
            // If his health was above two launcher hits, move towards where he was
            if (prevRoundLeaderHealth > MAGIC_NUM_HITS_TO_ASSUME_DEAD * RobotType.LAUNCHER.damage
                    && prevRoundLeaderLoc != null) {
                rc.setIndicatorString("missing :" + String.valueOf(prevRoundLeaderLoc.x) + ", " + String.valueOf(prevRoundLeaderLoc.y));
                Movement.moveTowardsLocation(rc, prevRoundLeaderLoc);
            }
            // If a certain number of rounds have passed without the leader then make a new one
            if (roundsWithoutPrevLeader > MAGIC_ROUNDS_TO_ELECT_NEW_LEADER) {
                prevRoundLeaderID = leaderId;
                prevRoundLeaderHealth = leaderHealth;
                prevRoundLeaderLoc = leaderLoc;
            }
            roundsWithoutPrevLeader++;
        } else {
            prevRoundLeaderID = leaderId;
            prevRoundLeaderHealth = leaderHealth;
            prevRoundLeaderLoc = leaderLoc;
            roundsWithoutPrevLeader = 0;
        }

        if (ourLauncherCount < LAUNCHER_BATTALION_SIZE) { // Not enough launchers nearby
            if (closestHqLoc != null) {
                if(moveTowardsLocation(rc, closestHqLoc)) {
                    rc.setIndicatorString("Moving towards own HQ");
                }
            }
        }
        else if (leaderId > rc.getID()) { // I am the leader!
            if (moveTowardsVisibleEnemies(rc)) {
                rc.setIndicatorString("LEADER: moving towards enemy robots");
            } else if (moveTowardsEnemyIslands(rc)) {
                rc.setIndicatorString("LEADER: moving towards enemy island");
            } else if (moveTowardsEnemyHq(rc)) {
                rc.setIndicatorString("LEADER: moving towards enemy hq");
            } else if (rc.canMove(rc.getLocation().directionTo(mapCenter))) {
                rc.move(rc.getLocation().directionTo(mapCenter));
                rc.setIndicatorString("LEADER: moving towards center");
            } else {
                Pathing.moveRandomly(rc);
                rc.setIndicatorString("LEADER: moving randomly");
            }
        }
        // Follow the leader
        else {
            Direction dir = rc.getLocation().directionTo(leader.getLocation());
            if (rc.canMove(dir)) {
                rc.move(dir);
                rc.setIndicatorString("FOLLOWER: moving towards leader " + leaderId);
            } else {
                Pathing.moveRandomly(rc);
                rc.setIndicatorString("FOLLOWER: moving randomly");
            }
        }
    }
}