package burritoplayer;

import battlecode.common.MapLocation;

public class ArrayUtil {
    static boolean mapLocationArrayContains(MapLocation[] haystack, MapLocation needle) {
        for (MapLocation hay: haystack) {
            if (hay.equals(needle)) {
                return true;
            }
        }
        return false;
    }
}
