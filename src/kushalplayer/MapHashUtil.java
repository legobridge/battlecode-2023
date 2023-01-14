package kushalplayer;

import battlecode.common.MapLocation;

public class MapHashUtil {
    /**
     * Encodes a MapLocation object as a single integer.
     * @param mapLocation a MapLocation object
     * @param mapWidth the width of the game map
     * @return a single integer representing a unique MapLocation
     */
    public static int hashMapLocation(MapLocation mapLocation, int mapWidth) {
        int x = mapLocation.x;
        int y = mapLocation.y;
        return y * mapWidth + x + 1;
    }

    /**
     * Undoes {@link MapHashUtil#hashMapLocation(MapLocation, int) hashMapLocation}
     * and returns a single MapLocation object.
     * @param hashedMapLocation an integer encoded using the hashMapLocation() function
     * @param mapWidth the width of the game map
     * @return a MapLocation
     */
    public static MapLocation unhashMapLocation(int hashedMapLocation, int mapWidth) {
        hashedMapLocation--;
        int x = hashedMapLocation % mapWidth;
        int y = hashedMapLocation / mapWidth;
        return new MapLocation(x, y);
    }
}
