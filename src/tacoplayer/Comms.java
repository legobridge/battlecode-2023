package tacoplayer;

import battlecode.common.*;
import java.util.ArrayList;
import java.lang.Math;

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

    /**
     * Gets information from an integer based on bits
     * num is the number that will be bit hacked
     * bit_index1 is the first bit index (starting at 1)
     * bit_index2 is the second bit index
     *
     * For example:
     *  if num = 181, bit_index1 = 3, and bit_index2 = 6
     *  181 = 1 0 1 1 0 1 0 1
     *  We are looking the segment 1 1 0 1
     *  Which is 13
     */
    private static int getNumFromBits(int num, int bit_index1, int bit_index2) {
        int shifted = num >> (bit_index1 - 1);
        int mask = 0;
        for (int i = 0; i < bit_index2-bit_index1+1; i++) {
            mask += (int) Math.pow(2, i);
        }
        return shifted & mask;
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
        int num_carriers= tryRead(rc, index) + 1;
        tryWrite(rc, index, num_carriers);
    }

    static void updateLauncherCount (RobotController rc) {
        int index = 5;
        int num_launchers = tryRead(rc, index) + 1;
        tryWrite(rc, index, num_launchers);
    }

    static void updateAmplifierCount(RobotController rc) {
        int index = 6;
        int num_amplifiers = tryRead(rc, index) + 1;
        tryWrite(rc, index, num_amplifiers);
    }

    static void updateDestabilizerCount(RobotController rc) {
        int index = 7;
        int num_destabilizers = tryRead(rc, index) + 1;
        tryWrite(rc, index, num_destabilizers);
    }

    static void updateBoosterCount(RobotController rc) {
        int index = 8;
        int num_boosters = tryRead(rc, index) + 1;
        tryWrite(rc, index, num_boosters);
    }

    static int[] getCarrierCounts(RobotController rc) {
        int index = 4;
        int shared_array_val = tryRead(rc, index);
        int this_turn_count = getNumFromBits(shared_array_val, 1, 8);
        int prev_turn_count = getNumFromBits(shared_array_val, 9, 16);
        int[] counts = new int[2];
        counts[0] = this_turn_count;
        counts[1] = prev_turn_count;
        return counts;
    }

    static int[] getLauncherCounts(RobotController rc) {
        int index = 5;
        int shared_array_val = tryRead(rc, index);
        int this_turn_count = getNumFromBits(shared_array_val, 1, 8);
        int prev_turn_count = getNumFromBits(shared_array_val, 9, 16);
        int[] counts = new int[2];
        counts[0] = this_turn_count;
        counts[1] = prev_turn_count;
        return counts;
    }

    static int[] getAmplifierCounts(RobotController rc) {
        int index = 6;
        int shared_array_val = tryRead(rc, index);
        int this_turn_count = getNumFromBits(shared_array_val, 1, 8);
        int prev_turn_count = getNumFromBits(shared_array_val, 9, 16);
        int[] counts = new int[2];
        counts[0] = this_turn_count;
        counts[1] = prev_turn_count;
        return counts;
    }

    static int[] getDestabilizerCounts(RobotController rc) {
        int index = 7;
        int shared_array_val = tryRead(rc, index);
        int this_turn_count = getNumFromBits(shared_array_val, 1, 8);
        int prev_turn_count = getNumFromBits(shared_array_val, 9, 16);
        int[] counts = new int[2];
        counts[0] = this_turn_count;
        counts[1] = prev_turn_count;
        return counts;
    }

    static int[] getBoosterCounts(RobotController rc) {
        int index = 8;
        int shared_array_val = tryRead(rc, index);
        int this_turn_count = getNumFromBits(shared_array_val, 1, 8);
        int prev_turn_count = getNumFromBits(shared_array_val, 9, 16);
        int[] counts = new int[2];
        counts[0] = this_turn_count;
        counts[1] = prev_turn_count;
        return counts;
    }

    static void resetCounts(RobotController rc) {
        for (int i = 4; i < 9; i++) {
            int save_count = getNumFromBits(i, 1, 8);
            save_count = save_count << 8;
            tryWrite(rc, i, save_count);
        }
    }
}
