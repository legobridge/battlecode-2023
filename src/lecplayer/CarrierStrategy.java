package lecplayer;

import battlecode.common.*;

public class CarrierStrategy {
    static MapLocation hqLoc;
    static MapLocation wellLoc;
    static MapLocation islandLoc;

    static boolean anchorMode = false;

    /**
     * Run a single turn for a Carrier.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    static void runCarrier(RobotController rc) throws GameActionException {

        if(hqLoc == null) scanHQ(rc);
        if(wellLoc == null) scanWells(rc);
        if(islandLoc == null) scanIslands(rc);

//        if (rc.getAnchor() != null) {
//            // If I have an anchor singularly focus on getting it to the first island I see
//            int[] islands = rc.senseNearbyIslands();
//            Set<MapLocation> islandLocs = new HashSet<>();
//            for (int id : islands) {
//                MapLocation[] thisIslandLocs = rc.senseNearbyIslandLocations(id);
//                islandLocs.addAll(Arrays.asList(thisIslandLocs));
//            }
//            if (islandLocs.size() > 0) {
//                MapLocation islandLocation = islandLocs.iterator().next();
//                rc.setIndicatorString("Moving my anchor towards " + islandLocation);
//                while (!rc.getLocation().equals(islandLocation)) {
//                    Direction dir = rc.getLocation().directionTo(islandLocation);
//                    if (rc.canMove(dir)) {
//                        rc.move(dir);
//                    }
//                }
//                if (rc.canPlaceAnchor()) {
//                    rc.setIndicatorString("Huzzah, placed anchor!");
//                    rc.placeAnchor();
//                }
//            }
//        }
//        // Try to gather from squares around us.
//        MapLocation me = rc.getLocation();
//        for (int dx = -1; dx <= 1; dx++) {
//            for (int dy = -1; dy <= 1; dy++) {
//                MapLocation wellLocation = new MapLocation(me.x + dx, me.y + dy);
//                if (rc.canCollectResource(wellLocation, -1)) {
//                    if (RobotPlayer.rng.nextBoolean()) {
//                        rc.collectResource(wellLocation, -1);
//                        rc.setIndicatorString("Collecting, now have, AD:" +
//                                rc.getResourceAmount(ResourceType.ADAMANTIUM) +
//                                " MN: " + rc.getResourceAmount(ResourceType.MANA) +
//                                " EX: " + rc.getResourceAmount(ResourceType.ELIXIR));
//                    }
//                }
//            }
//        }

        // Collect from well if close and inventory not full
        if(wellLoc != null && rc.canCollectResource(wellLoc, -1)) rc.collectResource(wellLoc, -1);

        //Transfer resource to headquarters
        depositResource(rc, ResourceType.ADAMANTIUM);
        depositResource(rc, ResourceType.MANA);

//        // Occasionally try out the carriers attack
//        if (RobotPlayer.rng.nextInt(20) == 1) {
//            RobotInfo[] enemyRobots = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
//            if (enemyRobots.length > 0) {
//                if (rc.canAttack(enemyRobots[0].location)) {
//                    rc.attack(enemyRobots[0].location);
//                }
//            }
//        }

//        // If we can see a well, move towards it
//        WellInfo[] wells = rc.senseNearbyWells();
//        if (wells.length > 1 && (rc.getResourceAmount(ResourceType.ADAMANTIUM) + rc.getResourceAmount(ResourceType.MANA )) < GameConstants.CARRIER_CAPACITY ) {
//            WellInfo well_one = wells[1];
//            Direction dir = me.directionTo(well_one.getMapLocation());
//            if (rc.canMove(dir))
//                rc.move(dir);
//        }
//        // Also try to move randomly.
//        Direction dir = RobotPlayer.directions[RobotPlayer.rng.nextInt(RobotPlayer.directions.length)];
//        if (rc.canMove(dir)) {
//            rc.move(dir);
//        }

        int total = getTotalResources(rc);

        if(rc.canTakeAnchor(hqLoc, Anchor.STANDARD)) {
            rc.takeAnchor(hqLoc, Anchor.STANDARD);
            anchorMode = true;
        }

        //no resources -> look for well
        if(anchorMode) {
            if(islandLoc == null) RobotPlayer.moveRandom(rc);
            else RobotPlayer.moveTowards(rc, islandLoc);

            if(rc.canPlaceAnchor()) rc.placeAnchor();
        }
        else {
            if (total == 0) {
                if (wellLoc != null) {
                    MapLocation me = rc.getLocation();
                    if (!me.isAdjacentTo(wellLoc)) RobotPlayer.moveTowards(rc, wellLoc);
                } else {
                    RobotPlayer.moveRandom(rc);
                }
            }

            //full resources -> go to HQ
            if (total == GameConstants.CARRIER_CAPACITY) {
                RobotPlayer.moveTowards(rc, hqLoc);
            }
        }
    }

    static void scanHQ(RobotController rc) throws GameActionException {
        RobotInfo[] robots = rc.senseNearbyRobots();
        for(RobotInfo robot : robots) {
            if(robot.getTeam() == rc.getTeam() && robot.getType() == RobotType.HEADQUARTERS) {
                hqLoc = robot.getLocation();
                break;
            }
        }
    }

    static void scanWells(RobotController rc) throws GameActionException {
        WellInfo[] wells = rc.senseNearbyWells();
        if(wells.length > 0) wellLoc = wells[0].getMapLocation();
    }

    static void scanIslands(RobotController rc) throws GameActionException {
        int[] ids = rc.senseNearbyIslands();
        for(int id : ids) {
            if(rc.senseTeamOccupyingIsland(id) == Team.NEUTRAL) {
                MapLocation[] locs = rc.senseNearbyIslandLocations(id);
                if(locs.length > 0) {
                    islandLoc = locs[0];
                    break;
                }
            }
        }
    }

    static void depositResource(RobotController rc, ResourceType type) throws GameActionException {
        int amount = rc.getResourceAmount(type);
        if(amount > 0) {
            if(rc.canTransferResource(hqLoc, type, amount)) rc.transferResource(hqLoc, type, amount);
        }
    }

    static int getTotalResources(RobotController rc) throws GameActionException {
        return rc.getResourceAmount(ResourceType.ADAMANTIUM) + rc.getResourceAmount(ResourceType.MANA);
    }

}
