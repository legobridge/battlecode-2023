package tacoplayer;

import battlecode.common.*;

import static tacoplayer.RobotPlayer.*;
import static tacoplayer.Sensing.*;

public class CarrierStrategy {

    static boolean anchorMode = false;
    static MapLocation wellAssignment;
    static ResourceType firstResourceGoal;
    static ResourceType secondResourceGoal;
    static ResourceType currentResourceAssignment;
    static int resourcesLastRound;
    static int resourcesThisRound;
    static boolean atCarrierCapacity = false;
    static boolean extracting = false;
    static boolean depositing = false;

    /**
     * Run a single turn for a Carrier.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */

    static void runCarrier(RobotController rc) throws GameActionException {

        // Update alive counter
        Comms.updateRobotCount(rc);

        // Update resources
        int adAmount = rc.getResourceAmount(ResourceType.ADAMANTIUM);
        int mnAmount = rc.getResourceAmount(ResourceType.MANA);
        int exAmount = rc.getResourceAmount(ResourceType.ELIXIR);
        resourcesLastRound = resourcesThisRound;
        resourcesThisRound = adAmount + mnAmount + exAmount;

        // If we are holding as much as possible, we are at carrier capacity
        if (resourcesThisRound == GameConstants.CARRIER_CAPACITY) atCarrierCapacity = true;
        else atCarrierCapacity = false;

        // If we gained resources last round, we are extracting, we stop extracting when we are full
        if (resourcesThisRound > resourcesLastRound) extracting = true;
        else if (resourcesThisRound == GameConstants.CARRIER_CAPACITY) extracting = false;

        // If we lost resources last round, we are depositing, we stop depositing when we are empty
        if (resourcesThisRound < resourcesLastRound) depositing = true;
        else if(resourcesThisRound == 0) depositing = false;

        // Update well priority
        if (firstResourceGoal == null) {
            int round = rc.getRoundNum();
            if (rc.getRoundNum() % 2 == 0 && (round <= 2 || round > 30)) {
                wellAssignment = nearestADWell;
                firstResourceGoal = ResourceType.ADAMANTIUM;
                secondResourceGoal = ResourceType.MANA;
            }
            else {
                wellAssignment = nearestMNWell;
                firstResourceGoal = ResourceType.MANA;
                secondResourceGoal = ResourceType.ADAMANTIUM;
            }
            currentResourceAssignment = firstResourceGoal;
        }

        // Update well assignment if it is still null
        if (wellAssignment == null) {
            if (firstResourceGoal == ResourceType.ADAMANTIUM) {
                wellAssignment = nearestADWell;
            }
            else {
                wellAssignment = nearestMNWell;
            }
        }

        // If you are at the well and there are more than 6 robots,
        // Switch which well you go to
//        if (ourCarrierCount > 12 && !extracting && wellAssignment != null
        if (Movement.moveToExtract(rc, wellAssignment)
                && rc.getLocation().distanceSquaredTo(wellAssignment) <= 9) {
            // If we are currently trying to collect the first resource goal from the nearest well,
            // Try collecting the secondary resource goal from its nearest well
            if (currentResourceAssignment == firstResourceGoal
                    && (wellAssignment == nearestADWell || wellAssignment == nearestMNWell)) {
                currentResourceAssignment = secondResourceGoal;
                if (secondResourceGoal == ResourceType.ADAMANTIUM) wellAssignment = nearestADWell;
                else wellAssignment = nearestMNWell;
            }

            // If we are currently collecting the secondary resource goal from its nearest well,
            // Try collecting the first resource goal from its second nearest location
            if (currentResourceAssignment == secondResourceGoal
                    && (wellAssignment == nearestADWell || wellAssignment == nearestMNWell)) {
                currentResourceAssignment = firstResourceGoal;
                if (firstResourceGoal == ResourceType.ADAMANTIUM) wellAssignment = secondNearestADWell;
                else wellAssignment = secondNearestMNWell;
            }

            // If we are currently collecting the first resource goal from its 2nd nearest well,
            // Try collecting the secondary resource goal from its 2nd nearest well
            if (currentResourceAssignment == firstResourceGoal
                    && (wellAssignment == secondNearestADWell || wellAssignment == secondNearestMNWell)) {
                currentResourceAssignment = secondResourceGoal;
                if (secondResourceGoal == ResourceType.ADAMANTIUM) wellAssignment = secondNearestADWell;
                else wellAssignment = secondNearestMNWell;
            }
        }
//        else {
//            // Raise the alarm
//            Comms.updateWellAttackInfo(rc, closestWellLoc);
//        }

        // Collect from well if close and inventory not full
        if (wellAssignment != null && rc.canCollectResource(wellAssignment, -1)) {
            rc.collectResource(wellAssignment, -1);
        }

        // If extracting and there are many carriers, upgrade the well
//        if (wellAssignment != null && rc.canSenseLocation(wellAssignment)) {
//            WellInfo well = rc.senseWell(wellAssignment);
//            if (ourCarrierCount > 6 && extracting && !well.isUpgraded() && rc.getResourceAmount(ResourceType.MANA) > 0
//                    && well.getResourceType() == ResourceType.MANA) {
//                if (rc.canTransferResource(wellAssignment, ResourceType.MANA, rc.getResourceAmount(ResourceType.MANA))) {
//                    rc.transferResource(wellAssignment, ResourceType.MANA, rc.getResourceAmount(ResourceType.MANA));
//                    System.out.println("UPGRADING WELL");
//                }
//            }
//        }

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

        // Pick up anchor if possible
        if (rc.canTakeAnchor(closestHqLoc, Anchor.STANDARD)) {
            rc.takeAnchor(closestHqLoc, Anchor.STANDARD);
            anchorMode = true;
        }

        // If you have an anchor, go plant that flag
        if (anchorMode) {
            if (closestNeutralIslandLoc == null) {
                //TODO - return anchor
                Pathing.moveRandomly(rc);
                Pathing.moveRandomly(rc);
            }
            else {
                Pathing.moveTowards(rc, closestNeutralIslandLoc);
                Pathing.moveTowards(rc, closestNeutralIslandLoc);
                if (rc.canPlaceAnchor() && closestNeutralIslandLoc.distanceSquaredTo(rc.getLocation()) == 0) {
                    rc.placeAnchor();
                    anchorMode = false;
                }
            }
        } else {
            if (resourcesThisRound != GameConstants.CARRIER_CAPACITY) { // No resources -> look for well
                if (wellAssignment != null) {
                    MapLocation selfLoc = rc.getLocation();
                    if (!selfLoc.isAdjacentTo(wellAssignment)) {
                        Pathing.moveTowards(rc, wellAssignment);
                        Pathing.moveTowards(rc, wellAssignment);
                    }
//                } else if (rc.getRoundNum() < 15) {
//                    Movement.moveTowardsCenter(rc);
                }else {
//                    Movement.moveTowardsCenter(rc);
                    Pathing.moveRandomly(rc);
                    Pathing.moveRandomly(rc);
                }
            }

            // Full resources -> go to HQ
            // TODO - Use the formula
            if (resourcesThisRound == GameConstants.CARRIER_CAPACITY) {
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
}
