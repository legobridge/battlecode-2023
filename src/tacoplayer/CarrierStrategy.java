package tacoplayer;

import battlecode.common.*;

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
        if (RobotPlayer.closestWellLoc != null && rc.canCollectResource(RobotPlayer.closestWellLoc, -1)) {
            rc.collectResource(RobotPlayer.closestWellLoc, -1);
        }

        //Transfer resource to headquarters
        depositResource(rc, ResourceType.ADAMANTIUM);
        depositResource(rc, ResourceType.MANA);
        depositResource(rc, ResourceType.ELIXIR);

        // Occasionally try out the carriers attack
        if (RobotPlayer.rng.nextInt(20) == 1) {
            RobotInfo[] enemyRobots = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
            if (enemyRobots.length > 0) {
                if (rc.canAttack(enemyRobots[0].location)) {
                    rc.attack(enemyRobots[0].location);
                }
            }
        }

        int total = getTotalResources(rc);

        if (rc.canTakeAnchor(RobotPlayer.closestHqLoc, Anchor.STANDARD)) {
            rc.takeAnchor(RobotPlayer.closestHqLoc, Anchor.STANDARD);
            anchorMode = true;
        }

        if (anchorMode) { // In anchor mode, go plant that flag
            if (RobotPlayer.closestNeutralIslandLoc == null) {
                Pathing.moveRandomly(rc);
                Pathing.moveRandomly(rc);
            }
            else {
                Pathing.moveTowards(rc, RobotPlayer.closestNeutralIslandLoc);
                Pathing.moveTowards(rc, RobotPlayer.closestNeutralIslandLoc);
            }
            if (rc.canPlaceAnchor()) {
                rc.placeAnchor();
                anchorMode = false;
            }
        } else {
            if (total == 0) { // No resources -> look for well
                if (RobotPlayer.closestWellLoc != null) {
                    MapLocation selfLoc = rc.getLocation();
                    if (!selfLoc.isAdjacentTo(RobotPlayer.closestWellLoc)) {
                        Pathing.moveTowards(rc, RobotPlayer.closestWellLoc);
                        Pathing.moveTowards(rc, RobotPlayer.closestWellLoc);
                    }
                } else {
                    Pathing.moveRandomly(rc);
                    Pathing.moveRandomly(rc);
                }
            }

            // Full resources -> go to HQ
            if (total == GameConstants.CARRIER_CAPACITY) {
                Pathing.moveTowards(rc, RobotPlayer.closestHqLoc);
                Pathing.moveTowards(rc, RobotPlayer.closestHqLoc);
            }
        }
    }

    static void depositResource(RobotController rc, ResourceType type) throws GameActionException {
        int amount = rc.getResourceAmount(type);
        if (amount > 0) {
            if (rc.canTransferResource(RobotPlayer.closestHqLoc, type, amount)) {
                rc.transferResource(RobotPlayer.closestHqLoc, type, amount);
            }
        }
    }

    static int getTotalResources(RobotController rc) throws GameActionException {
        return rc.getResourceAmount(ResourceType.ADAMANTIUM)
                + rc.getResourceAmount(ResourceType.MANA)
                + rc.getResourceAmount(ResourceType.ELIXIR);
    }
}
