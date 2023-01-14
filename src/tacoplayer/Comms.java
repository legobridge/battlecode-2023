package tacoplayer;

import battlecode.common.*;
import java.util.ArrayList;

import java.awt.*;

/**
 * Comms is the class used for robot communication
 * with the shared array
 */
public class Comms {
    /**
     * Attempts to read the shared array
     * If error returns 0
     */
    private static int tryRead(RobotController rc, int index) {
        int value = 0;
        try {
            value = rc.readSharedArray(index);
        } catch (GameActionException e) {}
        return value;
    }

    /**
     * Attempts to write to the shared array
     */
    private static void tryWrite(RobotController rc, int index, int value) {
        try{
            rc.writeSharedArray(index, value);
        } catch (GameActionException e) {}
    }

    static void updateHQLocation(RobotController rc) {
        // Hash the 2D coordinate to a 1D coordinate
        MapLocation hq_loc = rc.getLocation();
        int hashed_loc = MapHashUtil.hashMapLocation(hq_loc, rc.getMapWidth());

        // Write it to the shared array if it hasn't been written yet
        for (int i = 0; i < 4; i++) {
            // hashMapLocation adds 1 so the value of hashed_loc can never be 0
            if (tryRead(rc, i) == 0) {
                tryWrite(rc, i, hashed_loc);
                break;
            }
        }
    }

    static MapLocation[] getHQLocations(RobotController rc) {
        // Fill an array list with the locations of the HQs
        ArrayList<MapLocation> hq_locs = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int hashed_loc = tryRead(rc, i);
            if (hashed_loc > 0) {
                hq_locs.add(MapHashUtil.unhashMapLocation(hashed_loc, rc.getMapWidth()));
            }
        }
        // Convert the array list to an array
        return hq_locs.toArray(new MapLocation[hq_locs.size()]);
    }

    static void updateCarrierCount(RobotController rc) {
        int index = 4;
        int num_carriers = tryRead(rc, index) + 1;
        tryWrite(rc, index, num_carriers);
    }

    static void updateLauncherCount (RobotController rc) {
        int index = 5;
        int num_carriers = tryRead(rc, index) + 1;
        tryWrite(rc, index, num_carriers);
    }

    static void updateAmplifierCount(RobotController rc) {
        int index = 6;
        int num_carriers = tryRead(rc, index) + 1;
        tryWrite(rc, index, num_carriers);
    }

    static void updateDestabilizerCount(RobotController rc) {
        int index = 7;
        int num_carriers = tryRead(rc, index) + 1;
        tryWrite(rc, index, num_carriers);
    }

    static void updateBoosterCount(RobotController rc) {
        int index = 8;
        int num_carriers = tryRead(rc, index) + 1;
        tryWrite(rc, index, num_carriers);
    }

    static int getCarrierCount(RobotController rc) {
        int index = 4;
        return tryRead(rc, index);
    }

    static int getLauncherCount(RobotController rc) {
        int index = 5;
        return tryRead(rc, index);
    }

    static int getAmplifierCount(RobotController rc) {
        int index = 6;
        return tryRead(rc, index);
    }

    static int getDestabilizerCount(RobotController rc) {
        int index = 7;
        return tryRead(rc, index);
    }

    static int getBoosterCount(RobotController rc) {
        int index = 8;
        return tryRead(rc, index);
    }
}
