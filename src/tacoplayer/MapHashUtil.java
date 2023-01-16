package tacoplayer;

import battlecode.common.MapLocation;

public class MapHashUtil {
    /**
     * Encodes a MapLocation object as a single integer.
     * @param mapLocation a MapLocation object
     * @return a single integer representing a unique MapLocation
     */
    public static int hashMapLocation(MapLocation mapLocation) {
        int x = mapLocation.x;
        int y = mapLocation.y;
        return y * RobotPlayer.mapWidth + x + 1;
    }

    /**
     * Undoes {@link MapHashUtil#hashMapLocation(MapLocation) hashMapLocation}
     * and returns a single MapLocation object.
     * @param hashedMapLocation an integer encoded using the hashMapLocation() function
     * @return a MapLocation
     */
    public static MapLocation unhashMapLocation(int hashedMapLocation) {
        hashedMapLocation--;
        int x = hashedMapLocation % RobotPlayer.mapWidth;
        int y = hashedMapLocation / RobotPlayer.mapWidth;
        return new MapLocation(x, y);
    }
}
