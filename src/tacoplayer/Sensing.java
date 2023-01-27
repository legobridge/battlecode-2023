package tacoplayer;

import battlecode.common.*;

import static tacoplayer.RobotPlayer.*;

public class Sensing {

    final static int MAX_SENSED_ROBOTS = 120;
    final static int MAX_RADIUS_TO_SENSE_OBSTACLES = 20;
    final static int SMALLER_RADIUS_TO_SENSE_OBSTACLES = 13;

    static int ourCarrierCount;
    static int ourLauncherCount;
    static int ourDestabCount;
    static RobotInfo[] ourCarriers = new RobotInfo[MAX_SENSED_ROBOTS];
    static RobotInfo[] ourLaunchers = new RobotInfo[MAX_SENSED_ROBOTS];
    static RobotInfo[] ourDestab = new RobotInfo[MAX_SENSED_ROBOTS];

    static int enemyHqCount;
    static int enemyCarrierCount;
    static int enemyLauncherCount;
    static int enemyDestabCount;
    static RobotInfo[] enemyHqs = new RobotInfo[4];
    static RobotInfo[] enemyCarriers = new RobotInfo[MAX_SENSED_ROBOTS];
    static RobotInfo[] enemyLaunchers = new RobotInfo[MAX_SENSED_ROBOTS];
    static RobotInfo[] enemyDestab = new RobotInfo[MAX_SENSED_ROBOTS];

    static int visibleEnemiesCount;
    static int closestVisibleEnemyHqDistSq;
    static RobotInfo closestVisibleEnemyHq;
    static int closestVisibleEnemyRobotDistSq;
    static RobotInfo closestVisibleEnemyRobot;

    static int closestFriendlyIslandDistSq;
    static int closestNeutralIslandDistSq;
    static int closestEnemyIslandDistSq;
    static MapLocation closestFriendlyIslandLoc;
    static MapLocation closestNeutralIslandLoc;
    static MapLocation closestEnemyIslandLoc;

    static int knownIslandCount = 0;
    static int[] knownIslandIds = new int[GameConstants.MAX_NUMBER_ISLANDS];
    static IslandInfo[] knownIslands = new IslandInfo[GameConstants.MAX_NUMBER_ISLANDS + 1]; // Island with ID i is stored at i-th index

    static int bcsum = 0;
    static void scanObstacles(RobotController rc) throws GameActionException {
//        int bc1 = Clock.getBytecodesLeft();
        int radiusSquaredToSense = MAX_RADIUS_TO_SENSE_OBSTACLES;
        if (turnCount == 1) { // On the robot's first turn, don't scan everything
            radiusSquaredToSense = SMALLER_RADIUS_TO_SENSE_OBSTACLES;
        }
        MapInfo[] mapInfos = rc.senseNearbyMapInfos(radiusSquaredToSense);
        for (int i = mapInfos.length; --i >= 0;) {
            MapLocation loc = mapInfos[i].getMapLocation();
            if (map[loc.x][loc.y] == 0) {
                if (!mapInfos[i].isPassable()) {
                    map[loc.x][loc.y] = 1;
                }
                else if (mapInfos[i].hasCloud()) {
                    map[loc.x][loc.y] = 2;
                } else {
                    map[loc.x][loc.y] = 3 + mapInfos[i].getCurrentDirection().ordinal();
                }
            }
        }
//        bcsum += (bc1 - Clock.getBytecodesLeft());
//        if (rc.getType() == RobotType.LAUNCHER && rc.getID() % 17 == 0) {
//            System.out.println(bcsum / turnCount);
//        }
    }

    static void scanRobots(RobotController rc) throws GameActionException {
        ourCarrierCount = 0;
        ourLauncherCount = 0;
        ourDestabCount = 0;

        visibleEnemiesCount = 0;
        closestVisibleEnemyHqDistSq = MAX_MAP_DIST_SQ;
        closestVisibleEnemyRobotDistSq = MAX_MAP_DIST_SQ;
        closestVisibleEnemyRobot = null;
        enemyHqCount = 0;
        enemyCarrierCount = 0;
        enemyLauncherCount = 0;
        enemyDestabCount = 0;
        RobotInfo[] robots = rc.senseNearbyRobots();
        for (int j = -1; ++j < robots.length; ) {
            RobotInfo robot = robots[j];
            if (robot.team == ourTeam) {
                switch (robot.getType()) {
                    case HEADQUARTERS:
                        break;
                    case CARRIER:
                        ourCarriers[ourCarrierCount++] = robot;
                        break;
                    case LAUNCHER:
                        ourLaunchers[ourLauncherCount++] = robot;
                        break;
                    case AMPLIFIER: // TODO - sense these robots
                        break;
                    case BOOSTER:
                        break;
                    case DESTABILIZER:
                        break;
                }
            } else {
                MapLocation enemyRobotLocation = robot.getLocation();
                switch (robot.getType()) {
                    case HEADQUARTERS:
                        int enemyHqDistSq = rc.getLocation().distanceSquaredTo(enemyRobotLocation);
                        if (enemyHqDistSq < closestVisibleEnemyHqDistSq) {
                            closestVisibleEnemyHqDistSq = enemyHqDistSq;
                            closestVisibleEnemyHq = robot;
                        }
                        break;
                    default:
                        visibleEnemiesCount++;
                        int enemyRobotDistSq = rc.getLocation().distanceSquaredTo(enemyRobotLocation);
                        if (enemyRobotDistSq < closestVisibleEnemyRobotDistSq) {
                            closestVisibleEnemyRobotDistSq = enemyRobotDistSq;
                            closestVisibleEnemyRobot = robot;
                        }
                }
                switch (robot.getType()) {
                    case HEADQUARTERS:
                        enemyHqs[enemyHqCount++] = robot;
                        // If enemy headquarters is spotted, try to figure out which sort of symmetry the map has
                        boolean isOnlyOneSymmetryLeft = (Comms.locallyKnownSymmetry == 1
                                || Comms.locallyKnownSymmetry == 2
                                || Comms.locallyKnownSymmetry == 4);
                        if (!isOnlyOneSymmetryLeft) {
                            int mostSymmetryPossible = 0;
                            for (int i = -1; ++i < hqCount; ) {
                                mostSymmetryPossible |= MapLocationUtil.getSymmetriesBetween(ourHqLocs[i], enemyRobotLocation);
                            }
                            Comms.updateSymmetry(mostSymmetryPossible);
                        }
                        break;
                    case CARRIER:
                        enemyCarriers[enemyCarrierCount++] = robot;
                        break;
                    case LAUNCHER:
                        enemyLaunchers[enemyLauncherCount++] = robot;
                        break;
                    case AMPLIFIER: // TODO - sense these robots
                        break;
                    case BOOSTER:
                        break;
                    case DESTABILIZER:
                        break;
                }
            }
        }
    }

    static void scanWells(RobotController rc) {
        WellInfo[] wells = rc.senseNearbyWells();
        MapLocation selfLoc = rc.getLocation();

        for (WellInfo well : wells) {
            MapLocation wellLoc = well.getMapLocation();
            ResourceType wellType = well.getResourceType();
            if (!knownWellLocs.contains(wellLoc)) {
                knownWellLocs.add(wellLoc);
            }
            int wellDistSq = selfLoc.distanceSquaredTo(wellLoc);

            MapLocation closestWellLoc = nearestADWell;
            MapLocation secondClosestWellLoc = secondNearestADWell;
            int closestWellDistSq = nearestADWellDistSq;
            int secondClosestWellDistSq = secondNearestADWellDistSq;

            switch (wellType) {
                case ADAMANTIUM:
                    closestWellLoc = nearestADWell;
                    closestWellDistSq = nearestADWellDistSq;
                    secondClosestWellLoc = secondNearestADWell;
                    secondClosestWellDistSq = secondNearestADWellDistSq;
                    break;
                case MANA:
                    closestWellLoc = nearestMNWell;
                    closestWellDistSq = nearestMNWellDistSq;
                    secondClosestWellLoc = secondNearestMNWell;
                    secondClosestWellDistSq = secondNearestMNWellDistSq;
                    break;
                case ELIXIR:
                    closestWellLoc = nearestEXWell;
                    closestWellDistSq = nearestEXWellDistSq;
                    secondClosestWellLoc = secondNearestEXWell;
                    secondClosestWellDistSq = secondNearestEXWellDistSq;
                    break;
            }

            if (closestWellLoc == null || wellDistSq < closestWellDistSq) {
                updateNearestWell(wellLoc, wellDistSq, wellType);
            } else if (secondClosestWellLoc == null || wellDistSq < secondClosestWellDistSq) {
                updateSecondNearestWell(wellLoc, wellDistSq, wellType);
            }
        }
    }

    static void updateNearestWell(MapLocation loc, int distSq, ResourceType res) {
        switch (res) {
            case ADAMANTIUM:
                nearestADWell = loc;
                nearestADWellDistSq = distSq;
                break;
            case MANA:
                nearestMNWell = loc;
                nearestMNWellDistSq = distSq;
                break;
            case ELIXIR:
                nearestEXWell = loc;
                nearestEXWellDistSq = distSq;
                break;
        }
    }

    static void updateSecondNearestWell(MapLocation loc, int distSq, ResourceType res) {
        switch (res) {
            case ADAMANTIUM:
                secondNearestADWell = loc;
                secondNearestADWellDistSq = distSq;
                break;
            case MANA:
                secondNearestMNWell = loc;
                secondNearestMNWellDistSq = distSq;
                break;
            case ELIXIR:
                secondNearestEXWell = loc;
                secondNearestEXWellDistSq = distSq;
                break;
        }
    }

    public static void scanIslands(RobotController rc) throws GameActionException {
        int[] islandIds = rc.senseNearbyIslands();
        for (int i = -1; ++i < islandIds.length; ) {
            int islandId = islandIds[i];
            Team islandTeam = rc.senseTeamOccupyingIsland(islandId);
            if (knownIslands[islandId] == null) { // I haven't seen this island
                knownIslandIds[knownIslandCount++] = islandId;
                knownIslands[islandId] = new IslandInfo(islandId, rc.getRoundNum(),
                        islandTeam, rc.senseNearbyIslandLocations(islandId));
            } else { // I've seen this island before
                knownIslands[islandId].team = islandTeam; // Set the team again, in case it has changed
                knownIslands[islandId].turnLastSensed = rc.getRoundNum(); // Update the last sensed turn
            }
        }
        computeClosestIslands(rc);
    }

    private static void computeClosestIslands(RobotController rc) {
        closestFriendlyIslandDistSq = MAX_MAP_DIST_SQ;
        closestNeutralIslandDistSq = MAX_MAP_DIST_SQ;
        closestEnemyIslandDistSq = MAX_MAP_DIST_SQ;

        for (int i = -1; ++i < knownIslandCount;) {
            IslandInfo knownIslandInfo = knownIslands[knownIslandIds[i]];
            MapLocation knownIslandLoc = knownIslandInfo.locations[0];
            int thisDistSq = rc.getLocation().distanceSquaredTo(knownIslandLoc);
            if (knownIslandInfo.team == ourTeam) {
                if (thisDistSq < closestFriendlyIslandDistSq) {
                    closestFriendlyIslandLoc = knownIslandLoc;
                    closestFriendlyIslandDistSq = thisDistSq;
                }
            }
            else if (knownIslandInfo.team == Team.NEUTRAL) {
                if (thisDistSq < closestNeutralIslandDistSq) {
                    closestNeutralIslandLoc = knownIslandLoc;
                    closestNeutralIslandDistSq = thisDistSq;
                }
            }
            else {
                if (thisDistSq < closestEnemyIslandDistSq) {
                    closestEnemyIslandLoc = knownIslandLoc;
                    closestEnemyIslandDistSq = thisDistSq;
                }
            }
        }
    }
}
