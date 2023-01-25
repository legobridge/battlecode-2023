package quesadillaplayer;

import battlecode.common.*;

import static quesadillaplayer.RobotPlayer.*;

public class CarrierStrategy {

    static boolean anchorMode = false;
    // TODO - Run away from enemy launchers!

    /**
     * Run a single turn for a Carrier.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    static void runCarrier(RobotController rc) throws GameActionException {
        // Update alive counter
        Comms.updateRobotCount(rc);

        // Collect from well if close and inventory not full
        if (closestWellLoc != null && rc.canCollectResource(closestWellLoc, -1)) {
            rc.collectResource(closestWellLoc, -1);
        }

        //Transfer resource to headquarters
        depositResource(rc, ResourceType.ADAMANTIUM);
        depositResource(rc, ResourceType.MANA);
        depositResource(rc, ResourceType.ELIXIR);

        // Occasionally try out the carriers attack
        if (rng.nextInt(20) == 1) {
            RobotInfo[] enemyRobots = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
            if (enemyRobots.length > 0) {
                if (rc.canAttack(enemyRobots[0].location)) {
                    rc.attack(enemyRobots[0].location);
                }
            }
        }

        int total = getTotalResources(rc);

        if (rc.canTakeAnchor(closestHqLoc, Anchor.STANDARD)) {
            rc.takeAnchor(closestHqLoc, Anchor.STANDARD);
            anchorMode = true;
        }

        closestNeutralIslandLoc = Comms.getClosestNeutralIsland(rc);
        if (anchorMode) { // In anchor mode, go plant that flag
            if (closestNeutralIslandLoc == null) {
                Pathing.moveRandomly(rc);
                Pathing.moveRandomly(rc);
            }
            else {
                Pathing.moveTowards(rc, closestNeutralIslandLoc);
                Pathing.moveTowards(rc, closestNeutralIslandLoc);
            }
            if (rc.canPlaceAnchor() && closestNeutralIslandLoc.distanceSquaredTo(rc.getLocation()) == 0) {
                rc.placeAnchor();
                anchorMode = false;
            }
        } else {
            if (total == 0) { // No resources -> look for well
                if (closestWellLoc != null) {
                    MapLocation selfLoc = rc.getLocation();
                    if (!selfLoc.isAdjacentTo(closestWellLoc)) {
                        Pathing.moveTowards(rc, closestWellLoc);
                        Pathing.moveTowards(rc, closestWellLoc);
                    }
                } else {
                    Pathing.moveRandomly(rc);
                    Pathing.moveRandomly(rc);
                }
            }

            // Full resources -> go to HQ
            if (total == GameConstants.CARRIER_CAPACITY) {
                Pathing.moveTowards(rc, closestHqLoc);
                Pathing.moveTowards(rc, closestHqLoc);
            }
        }
    }

    static void depositResource(RobotController rc, ResourceType type) throws GameActionException {
        int amount = rc.getResourceAmount(type);
        if (amount > 0) {
            if (rc.canTransferResource(closestHqLoc, type, amount)) {
                rc.transferResource(closestHqLoc, type, amount);
            }
        }
    }

    static int getTotalResources(RobotController rc) throws GameActionException {
        return rc.getResourceAmount(ResourceType.ADAMANTIUM)
                + rc.getResourceAmount(ResourceType.MANA)
                + rc.getResourceAmount(ResourceType.ELIXIR);
    }
}