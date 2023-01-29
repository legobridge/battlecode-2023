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
    static boolean attackEnemyWell = false;
    static boolean attackEnemyHQ = false;
    static boolean attackEnemyIsland = false;
    static int MAGIC_ROUNDS_PER_OBJECTIVE = 50;
    static int roundsWithoutEnemies = 1000;
    static Direction dirToEnemy;
    static SymmetryType usingSym;
    static MapLocation enemyWellLoc;

    // TODO - update batallion size dependent on how far the enemyHQs are?
    static void runLauncher(RobotController rc) throws GameActionException {
        // Update alive counter
        Comms.updateRobotCount(rc);

        // Update objectives
        if (closestEnemyIslandLoc != null) {
            attackEnemyIsland = true;
        }
        else {
            attackEnemyIsland = false;
        }
        if (rc.getRoundNum() % MAGIC_ROUNDS_PER_OBJECTIVE == 7
                || (!attackEnemyHQ && !attackEnemyWell)) {
            if (ourLauncherCount % 2 == 0 && closestEnemyHqLoc != null) {
                attackEnemyHQ = true;
                attackEnemyWell = false;
            }
            else if (Comms.sharedWellLocs[3] != 0) {
                attackEnemyWell = true;
                attackEnemyHQ = false;
            }
        }

        // Update health
        updateHealth(rc);

        // Attack
        Combat.attack(rc);
        Combat.retreat(rc);
        Combat.runaway(rc);

        // Update symmetry
        updateSymmetry(rc);

        // Move
        if (!retreatMode) {
            if (rc.getRoundNum() < 200) {
                earlyMove(rc);
            }
            move(rc);
        }
    }

    private static void earlyMove(RobotController rc) throws GameActionException {
        // Move together
        // Go through the nearby launchers and elect a leader
        boolean inEnemyHQRange = rc.getLocation().distanceSquaredTo(closestEnemyHqLoc) <= RobotType.HEADQUARTERS.actionRadiusSquared;
        int leaderId = Integer.MAX_VALUE;
        int lowestHealth = Integer.MAX_VALUE;
        int lowestHealthId = Integer.MAX_VALUE;
        RobotInfo lowestHealthLauncher = null;
        for (int i = ourLauncherCount; --i >= 0; ) {
            if (ourLaunchers[i].getID() < leaderId) {
                leaderId = ourLaunchers[i].getID();
            }
            if (ourLaunchers[i].getHealth() < lowestHealth) {
                lowestHealth = ourLaunchers[i].getHealth();
                lowestHealthLauncher = ourLaunchers[i];
                lowestHealthId = ourLaunchers[i].getID();
            }
        }
        // Record an enemy position
        if (enemyLauncherCount > 0) {
            dirToEnemy = rc.getLocation().directionTo(enemyLaunchers[0].getLocation());
            roundsWithoutEnemies = 0;
        }
        else {
            roundsWithoutEnemies++;
        }

        // Get out of hq range
        if (inEnemyHQRange) {
            rc.setIndicatorString("Getting away from enemy HQ");
            moveTowardsLocation(rc, closestHqLoc);
        }
        else if (roundsWithoutEnemies <= 5) {
            Movement.moveDirectlyTowards(rc, dirToEnemy);
            rc.setIndicatorString("Chasing enemies");
        }
        // If the lowest health guy is getting hurt, move in to help
        else if (lowestHealth < prevLowestHealth && lowestHealthId == prevLowestHealthId
                && lowestHealthLauncher != null) {
            rc.setIndicatorString("Moving in as backup");
            moveDirectlyTowards(rc, lowestHealthLauncher.getLocation());
        }
        // Move to wherever is closest, enemy well or enemy HQ
        else if (enemyWellLoc != null
                && rc.getLocation().distanceSquaredTo(closestEnemyHqLoc) >=
                rc.getLocation().distanceSquaredTo(enemyWellLoc)) {
            Movement.moveTowardsLocation(rc, enemyWellLoc);
            rc.setIndicatorString("Moving to enemy well");
        }
        else {
            Movement.moveTowardsLocation(rc, closestEnemyHqLoc);
            rc.setIndicatorString("Moving to enemy HQ");
        }
    }

    private static void move(RobotController rc) throws GameActionException {
        // Move together
        // Go through the nearby launchers and elect a leader
        boolean inEnemyHQRange = rc.getLocation().distanceSquaredTo(closestEnemyHqLoc) <= RobotType.HEADQUARTERS.actionRadiusSquared;
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
        // Record an enemy position
        if (enemyLauncherCount > 0) {
            dirToEnemy = rc.getLocation().directionTo(enemyLaunchers[0].getLocation());
            roundsWithoutEnemies = 0;
        } else {
            roundsWithoutEnemies++;
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

        // Objectives
        if (attackEnemyIsland) {
            moveTowardsEnemyIslands(rc);
            rc.setIndicatorString("LEADER: moving towards enemy island");
        } else if (attackEnemyWell) {
            moveTowardsEnemyWell(rc);
            rc.setIndicatorString("LEADER: raiding mana well");
        } else if (attackEnemyHQ) {
            moveTowardsEnemyHq(rc);
            rc.setIndicatorString("LEADER: moving towards enemy hq");
        }
        // Get out of hq range
        if (inEnemyHQRange) {
            rc.setIndicatorString("Getting away from enemy HQ");
            moveTowardsLocation(rc, closestHqLoc);
        }
        // Chase enemies
        else if (roundsWithoutEnemies <= 5) {
            Movement.moveDirectlyTowards(rc, dirToEnemy);
            rc.setIndicatorString("Chasing enemies");
        }
        // If you're hurt and not attacking, go heal
        else if (rc.getHealth() < rc.getType().getMaxHealth()
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
        } else { // Follow the leader
            if (moveTowardsLocation(rc, leaderLoc)) {
                rc.setIndicatorString("FOLLOWER: moving towards leader " + leaderId);
            }
        }

        // If all else fails
        if (moveTowardsLocation(rc, mapCenter)) {
            rc.setIndicatorString("LEADER: moving towards map center");
        } else {
            Pathing.moveRandomly(rc);
            rc.setIndicatorString("FOLLOWER: moving randomly");

            prevLowestHealth = lowestHealth;
            prevLowestHealthId = lowestHealthId;
        }
    }
    static void updateSymmetry(RobotController rc) throws GameActionException {
        // If we are going to an HQ and its not there, remove that symmetry
        if (rc.canSenseLocation(closestEnemyHqLoc)) {
            RobotInfo rinf = rc.senseRobotAtLocation(closestEnemyHqLoc);
            if (rinf == null || rinf.getType() != RobotType.HEADQUARTERS) {
                Comms.removeSymmetry(usingSym);
            }
        }
        usingSym = Comms.getSymmetryType();
        enemyWellLoc = Comms.getNearestEnemyWellLoc(rc, usingSym);
        if (enemyWellLoc != null && rc.canSenseLocation(enemyWellLoc)) {
            WellInfo rinf = rc.senseWell(enemyWellLoc);
            if (rinf == null) {
                Comms.removeSymmetry(usingSym);
            }
        }
    }
}