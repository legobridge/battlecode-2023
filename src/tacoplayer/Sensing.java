package tacoplayer;

import battlecode.common.*;

import static battlecode.common.Team.NEUTRAL;
import static tacoplayer.RobotPlayer.*;

public class Sensing {

    final static int MAX_SENSED_ROBOTS = 120;

    static int ourCarrierCount;
    static int ourLauncherCount;
    static int ourDestabCount;
    static RobotInfo[] ourCarriers = new RobotInfo[MAX_SENSED_ROBOTS];
    static RobotInfo[] ourLaunchers = new RobotInfo[MAX_SENSED_ROBOTS];
    static RobotInfo[] ourDestab = new RobotInfo[MAX_SENSED_ROBOTS];

    static int visibleEnemiesCount;
    static int closestVisibleEnemyRobotDistSq;
    static MapLocation closestVisibleEnemyRobotLocation;
    static int enemyHqCount;
    static int enemyCarrierCount;
    static int enemyLauncherCount;
    static int enemyDestabCount;
    static RobotInfo[] enemyHqs = new RobotInfo[4];
    static RobotInfo[] enemyCarriers = new RobotInfo[MAX_SENSED_ROBOTS];
    static RobotInfo[] enemyLaunchers = new RobotInfo[MAX_SENSED_ROBOTS];
    static RobotInfo[] enemyDestab = new RobotInfo[MAX_SENSED_ROBOTS];

    static void scanObstacles(RobotController rc) {
        // TODO - scan for clouds and currents and add to local memory
    }

    static void scanRobots(RobotController rc) throws GameActionException {
        ourCarrierCount = 0;
        ourLauncherCount = 0;
        ourDestabCount = 0;

        visibleEnemiesCount = 0;
        closestVisibleEnemyRobotDistSq = Integer.MAX_VALUE;
        closestVisibleEnemyRobotLocation = null;
        enemyHqCount = 0;
        enemyCarrierCount = 0;
        enemyLauncherCount = 0;
        enemyDestabCount = 0;
        RobotInfo[] robots = rc.senseNearbyRobots();
        for (int j = -1; ++j < robots.length; ) {
            if (robots[j].team == ourTeam) {
                switch (robots[j].getType()) {
                    case HEADQUARTERS:
                        break;
                    case CARRIER:
                        ourCarriers[ourCarrierCount++] = robots[j];
                        break;
                    case LAUNCHER:
                        ourLaunchers[ourLauncherCount++] = robots[j];
                        break;
                    case AMPLIFIER: // TODO - sense these robots
                        break;
                    case BOOSTER:
                        break;
                    case DESTABILIZER:
                        break;
                }
            } else {
                visibleEnemiesCount++;
                MapLocation enemyRobotLocation = robots[j].getLocation();
                int enemyRobotDistSq = rc.getLocation().distanceSquaredTo(enemyRobotLocation);
                if (closestVisibleEnemyRobotLocation == null || enemyRobotDistSq < closestVisibleEnemyRobotDistSq) {
                    closestVisibleEnemyRobotDistSq = enemyRobotDistSq;
                    closestVisibleEnemyRobotLocation = enemyRobotLocation;
                }
                switch (robots[j].getType()) {
                    case HEADQUARTERS:
                        // Don't count headquarters as a visible enemy
                        visibleEnemiesCount--;
                        enemyHqs[enemyHqCount++] = robots[j];
                        // If enemy headquarters is spotted, try to figure out which sort of symmetry the map has
                        // TODO - stop doing this once fairly confident of symmetry
                        int mostSymmetryPossible = 0;
                        for (int i = -1; ++i < hqCount; ) {
                            mostSymmetryPossible |= MapLocationUtil.getSymmetriesBetween(ourHqLocs[i], enemyRobotLocation);
                        }
                        Comms.updateSymmetry(mostSymmetryPossible);
                        break;
                    case CARRIER:
                        enemyCarriers[enemyCarrierCount++] = robots[j];
                        break;
                    case LAUNCHER:
                        enemyLaunchers[enemyLauncherCount++] = robots[j];
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
            for (int j = -1; ++j < knownIslands.length; ) {
                if (knownIslands[j] == null) { // I haven't seen this island
                    knownIslands[j] = new IslandInfo(islandId, rc.getRoundNum(), islandTeam, rc.senseNearbyIslandLocations(islandId));
                    break;
                } else if (islandId == knownIslands[j].id) { // I've seen this island before
                    knownIslands[j].team = islandTeam; // Set the team again, in case it has changed
                    knownIslands[j].turnLastSensed = rc.getRoundNum(); // Update the last sensed turn
                    break;
                }
            }
        }
        closestFriendlyIslandLoc = MapLocationUtil.getClosestIslandMapLocEuclidean(rc, knownIslands, ourTeam);
        closestNeutralIslandLoc = MapLocationUtil.getClosestIslandMapLocEuclidean(rc, knownIslands, NEUTRAL);
        closestEnemyIslandLoc = MapLocationUtil.getClosestIslandMapLocEuclidean(rc, knownIslands, theirTeam);
    }
}
