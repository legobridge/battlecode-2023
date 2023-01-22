package tacoplayer;

import battlecode.common.MapLocation;
import static tacoplayer.RobotPlayer.*;

public class MapHashUtil {
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
     * Undoes {@link MapHashUtil#hashMapLocation(MapLocation) hashMapLocation}
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
}
