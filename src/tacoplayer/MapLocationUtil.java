package tacoplayer;

import battlecode.common.MapLocation;
import battlecode.common.RobotController;

import java.util.List;

import static tacoplayer.RobotPlayer.*;

public class MapLocationUtil {
    /**
     * Encodes a MapLocation object as a single integer.
     * @param mapLocation a MapLocation object
     * @return a single integer representing a unique MapLocation
     */
    public static int hashMapLocation(MapLocation mapLocation) {
        int x = mapLocation.x;
        int y = mapLocation.y;
        return y * mapWidth + x + 1;
    }

    /**
     * Undoes {@link MapLocationUtil#hashMapLocation(MapLocation) hashMapLocation}
     * and returns a single MapLocation object.
     * @param hashedMapLocation an integer encoded using the hashMapLocation() function
     * @return a MapLocation
     */
    public static MapLocation unhashMapLocation(int hashedMapLocation) {
        hashedMapLocation--;
        int x = hashedMapLocation % mapWidth;
        int y = hashedMapLocation / mapWidth;
        return new MapLocation(x, y);
    }

    public static MapLocation calcSymmetricLoc(MapLocation loc, SymmetryType symmetryType) {
        switch (symmetryType) {
            case HORIZONTAL:
                return new MapLocation(mapWidth - 1 - loc.x, loc.y);
            case VERTICAL:
                return new MapLocation(loc.x, mapHeight - 1 - loc.y);
            default:
                return new MapLocation(mapWidth - 1 - loc.x, mapHeight - 1 - loc.y);
        }
    }

    public static int getSymmetriesBetween(MapLocation loc1, MapLocation loc2) {
        int symmetries = 0;
        boolean horizontalReflection = mapWidth - 1 - loc1.x == loc2.x;
        boolean verticalReflection = mapHeight - 1 - loc1.y == loc2.y;
        if (horizontalReflection && loc1.y == loc2.y) {
            symmetries += 4; // HORIZONTAL
        }
        if (verticalReflection && loc1.x == loc2.x) {
            symmetries += 2; // VERTICAL
        }
        if (horizontalReflection && verticalReflection) {
            symmetries += 1; // ROTATIONAL
        }
        return symmetries;
    }

    static MapLocation getClosestMapLocEuclidean(RobotController rc, MapLocation[] mapLocations) {
        MapLocation selfLoc = rc.getLocation();
        MapLocation closestLoc = null;
        int closestLocDistSq = MAX_MAP_DIST_SQ;
        for (int i = -1; ++i < mapLocations.length;) {
            if (mapLocations[i] == null) {
                continue;
            }
            int thisDistSq = selfLoc.distanceSquaredTo(mapLocations[i]);
            if (closestLoc == null || thisDistSq < closestLocDistSq) {
                closestLoc = mapLocations[i];
                closestLocDistSq = thisDistSq;
            }
        }
        return closestLoc;
    }

    static MapLocation getClosestLocation(MapLocation startLoc, List<Integer> hashedLocs) {
        MapLocation closestLoc = null;
        int closest_distance = 10000;
        for (int hashedLoc : hashedLocs) {
            MapLocation loc = unhashMapLocation(hashedLoc);
            int distance = Math.abs(startLoc.x - loc.x) + Math.abs(startLoc.y - loc.y);
            if (closestLoc == null) {
                closestLoc = loc;
                closest_distance = distance;
            } else if (distance < closest_distance) {
                closestLoc = loc;
                closest_distance = distance;
            }
        }

        return closestLoc;
    }
}
