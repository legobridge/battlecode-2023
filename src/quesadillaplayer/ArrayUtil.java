package quesadillaplayer;

import battlecode.common.MapLocation;

public class ArrayUtil {
    static boolean mapLocationArrayContains(MapLocation[] haystack, MapLocation needle) {
        for (int i = -1; ++i < haystack.length;) {
            if (needle.equals(haystack[i])) {
                return true;
            }
        }
        return false;
    }
}
