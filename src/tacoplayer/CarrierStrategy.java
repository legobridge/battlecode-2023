package tacoplayer;

import battlecode.common.*;

import static tacoplayer.RobotPlayer.*;
import static tacoplayer.Sensing.*;

public class CarrierStrategy {

    static boolean anchorMode = false;
    static MapLocation closestWellLoc;

    /**
     * Run a single turn for a Carrier.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */

    static void runCarrier(RobotController rc) throws GameActionException {

        // Update alive counter
        Comms.updateRobotCount(rc);

        // Update well priority
        if (closestWellLoc == null) {
            if (rc.getRoundNum() % 2 == 0) {
                closestWellLoc = nearestADWell;
            }
            else {
                closestWellLoc = nearestMNWell;
            }
        }

        // Collect from well if close and inventory not full
        if (closestWellLoc != null && rc.canCollectResource(closestWellLoc, -1)) {
            rc.collectResource(closestWellLoc, -1);
        }

        //Transfer resource to headquarters
        // TODO - This is inefficient is robot is adjacent to both hq and resource at once
        depositResource(rc, ResourceType.ADAMANTIUM);
        depositResource(rc, ResourceType.MANA);
        depositResource(rc, ResourceType.ELIXIR);

        // TODO - Last hit
        // Occasionally try out the carriers attack
//        if (rng.nextInt(20) == 1) {
//            RobotInfo[] enemyRobots = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
//            if (enemyRobots.length > 0) {
//                if (rc.canAttack(enemyRobots[0].location)) {
//                    rc.attack(enemyRobots[0].location);
//                }
//            }
//        }

        // Move away from enemies
        if (enemyLauncherCount + enemyDestabCount > 0) {
            Movement.moveTowardsLocation(rc, closestHqLoc);
            Movement.moveTowardsLocation(rc, closestHqLoc);
        }

        int total = getTotalResources(rc);

        if (rc.canTakeAnchor(closestHqLoc, Anchor.STANDARD)) {
            rc.takeAnchor(closestHqLoc, Anchor.STANDARD);
            anchorMode = true;
        }
        if (anchorMode) { // In anchor mode, go plant that flag
            if (closestNeutralIslandLoc == null) {
                Pathing.moveRandomly(rc);
                Pathing.moveRandomly(rc);
            }
            else {
                Movement.moveTowardsLocation(rc, closestNeutralIslandLoc);
                Movement.moveTowardsLocation(rc, closestNeutralIslandLoc);
                if (rc.canPlaceAnchor() && closestNeutralIslandLoc.distanceSquaredTo(rc.getLocation()) == 0) {
                    rc.placeAnchor();
                    anchorMode = false;
                }
            }
        } else {
            if (total != GameConstants.CARRIER_CAPACITY) { // No resources -> look for well
                if (closestWellLoc != null) {
                    MapLocation selfLoc = rc.getLocation();
                    if (!selfLoc.isAdjacentTo(closestWellLoc)) {
                        Movement.moveTowardsLocation(rc, closestWellLoc);
                        Movement.moveTowardsLocation(rc, closestWellLoc);
                    }
                } else {
                    Pathing.moveRandomly(rc);
                    Pathing.moveRandomly(rc);
                }
            }

            // Full resources -> go to HQ
            // TODO - Use the formula
            if (total == GameConstants.CARRIER_CAPACITY) {
                Movement.moveTowardsLocation(rc, closestHqLoc);
                Movement.moveTowardsLocation(rc, closestHqLoc);
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

    static int getTotalResources(RobotController rc) {
        return rc.getResourceAmount(ResourceType.ADAMANTIUM)
                + rc.getResourceAmount(ResourceType.MANA)
                + rc.getResourceAmount(ResourceType.ELIXIR);
    }
}
