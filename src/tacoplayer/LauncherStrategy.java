package tacoplayer;

import battlecode.common.*;

import java.util.Map;

import static tacoplayer.RobotPlayer.*;
import static tacoplayer.Movement.*;
import static tacoplayer.Sensing.*;

public class LauncherStrategy {

    static int LAUNCHER_BATTALION_SIZE = 3;
    static int prevRoundLeaderID = -1;
    static int prevRoundLeaderHealth = 100;
    static MapLocation prevRoundLeaderLoc;
    static int roundsWithoutPrevLeader = 0;
    static int prevLowestHealth = 100;
    static int prevLowestHealthId = 0;
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
        Combat.runaway(rc);

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
        int lowestHealthId = Integer.MAX_VALUE;
        int leaderHealth = 100;
        MapLocation leaderLoc = null;
        RobotInfo leader = null;
        RobotInfo lowestHealthLauncher = null;
        for (int i = ourLauncherCount; --i >= 0; ) {
            if (ourLaunchers[i].getID() < leaderId) {
                leaderId = ourLaunchers[i].getID();
                leader = ourLaunchers[i];
                leaderHealth = leader.getHealth();
                leaderLoc = leader.getLocation();
            }
            if (ourLaunchers[i].getHealth() < lowestHealth) {
                lowestHealth = ourLaunchers[i].getHealth();
                lowestHealthLauncher = ourLaunchers[i];
                lowestHealthId = ourLaunchers[i].getID();
            }
        }

        // Check if the leader has changed
        if (leaderId != prevRoundLeaderID && prevRoundLeaderID != -1) {
            // If his health was above two launcher hits, move towards where he was
            if (prevRoundLeaderHealth > MAGIC_NUM_HITS_TO_ASSUME_DEAD * RobotType.LAUNCHER.damage
                    && prevRoundLeaderLoc != null) {
                rc.setIndicatorString("missing :" + prevRoundLeaderLoc.x + ", " + prevRoundLeaderLoc.y);
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
        if (rc.getRoundNum() < 7) {
            MapLocation center = new MapLocation(rc.getMapWidth() / 2, rc.getMapHeight() / 2);
            Movement.moveTowardsLocation(rc, center);
        }
        // If you're hurt and not attacking, go heal
        if (rc.getHealth() <  rc.getType().getMaxHealth()
                && !attackMode
                && closestFriendlyIslandLoc != null) {
            // Go heal if below a certain percentage of health
            rc.setIndicatorString("Moving towards island to heal");
            moveTowardsLocation(rc, closestFriendlyIslandLoc);
        }
        // If the lowest health guy is getting hurt, move in to help
        else if (lowestHealth < prevLowestHealth && lowestHealthId == prevLowestHealthId
                && lowestHealthLauncher != null) {
            rc.setIndicatorString("Moving in as backup");
            moveDirectlyTowards(rc, lowestHealthLauncher.getLocation());
        }
        else if (ourLauncherCount < LAUNCHER_BATTALION_SIZE) { // Not enough launchers nearby
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
            } else if (moveTowardsEnemyWell(rc)) {
                rc.setIndicatorString("LEADER: raiding mana well");
            } else if (moveTowardsEnemyHq(rc)) {
                rc.setIndicatorString("LEADER: moving towards enemy hq");
            } else if (moveTowardsLocation(rc, mapCenter)) {
                rc.setIndicatorString("LEADER: moving towards map center");
            } else {
                Pathing.moveRandomly(rc);
                rc.setIndicatorString("LEADER: moving randomly");
            }
        }
        else { // Follow the leader
            if (moveTowardsLocation(rc, leaderLoc)) {
                rc.setIndicatorString("FOLLOWER: moving towards leader " + leaderId);
            } if (moveTowardsLocation(rc, mapCenter)) {
                rc.setIndicatorString("LEADER: moving towards map center");
            } else {
                Pathing.moveRandomly(rc);
                rc.setIndicatorString("FOLLOWER: moving randomly");
            }
        }
        prevLowestHealth = lowestHealth;
        prevLowestHealthId = lowestHealthId;
    }
}