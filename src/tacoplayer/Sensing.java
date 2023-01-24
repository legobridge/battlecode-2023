package tacoplayer;

import battlecode.common.*;

import static tacoplayer.RobotPlayer.*;

public class Sensing {
    static void scanObstacles(RobotController rc) {
        // TODO - scan for clouds and currents and add to local memory
    }

    static void scanRobots(RobotController rc) throws GameActionException {
        RobotInfo[] robots = rc.senseNearbyRobots();

        for (int j = -1; ++j < robots.length; ) {
            switch (robots[j].getType()) {
                case HEADQUARTERS:
                    if (robots[j].team == theirTeam) {
                        // If enemy headquarters is spotted, try to figure out which sort of symmetry the map has
                        // TODO - stop doing this once fairly confident of symmetry
                        int mostSymmetryPossible = 0;
                        MapLocation enemyHqLoc = robots[j].getLocation();
                        for (int i = -1; ++i < hqCount; ) {
                            mostSymmetryPossible |= MapLocationUtil.getSymmetriesBetween(ourHqLocs[i], enemyHqLoc);
                        }
                        Comms.updateSymmetry(mostSymmetryPossible);
                    }
                    break;
                case CARRIER:
                    break;
                case LAUNCHER:
                    break;
                default: // TODO - sense other robots
                    break;
            }
        }
    }

    static void scanWells(RobotController rc) throws GameActionException {
        WellInfo[] wells = rc.senseNearbyWells();
        MapLocation selfLoc = rc.getLocation();
        int closestWellDistSq = MAX_MAP_DIST_SQ;

        for (WellInfo well : wells) {
            MapLocation wellLoc = well.getMapLocation();
            if (!knownWellLocs.contains(wellLoc)) {
                knownWellLocs.add(wellLoc);
            }
            int wellDistSq = selfLoc.distanceSquaredTo(wellLoc);
            if (closestWellLoc == null || wellDistSq < closestWellDistSq) {
                closestWellLoc = wellLoc;
                closestWellDistSq = wellDistSq;
            }
        }
    }

    public static void scanIslands(RobotController rc) throws GameActionException {
        int[] islandIds = rc.senseNearbyIslands();
        for (int i = -1; ++i < islandIds.length; ) {
            int islandId = islandIds[i];
            Team islandTeam = rc.senseTeamOccupyingIsland(islandId);
            for (int j = -1; ++j < knownIslands.length; ) {
                if (knownIslands[j] == null) { // I haven't seen this islan
                    knownIslands[j] = new IslandInfo(islandId, islandTeam, rc.senseNearbyIslandLocations(islandId));
                    break;
                } else if (islandId == knownIslands[j].id) { // I've seen this island before
                    knownIslands[j].team = islandTeam; // Set the team again, in case it has changed
                    break;
                }
            }
        }
    }
}
