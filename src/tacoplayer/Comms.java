package tacoplayer;

import battlecode.common.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

/**
 * Comms is the class used for robot communication
 * with the shared array
 */
public class Comms {
    static int getNumFromBits(int num, int bit_index1, int bit_index2) {
        int shifted = num >> (bit_index1 - 1);
        int mask = 0;
        for (int i = 0; i < bit_index2-bit_index1+1; i++) {
            mask += 1 << i;
        }
        return shifted & mask;
    }

    static void updateHQLocation(RobotController rc) throws GameActionException {
        // Hash the 2D coordinate to a 1D coordinate
        MapLocation hq_loc = rc.getLocation();
        int hashed_loc = MapHashUtil.hashMapLocation(hq_loc, rc.getMapWidth());

        // Write it to the shared array if it hasn't been written yet
        for (int i = 0; i < 4; i++) {
            // hashMapLocation adds 1 so the value of hashed_loc can never be 0
            if (rc.readSharedArray(i) == 0) {
                rc.writeSharedArray(i, hashed_loc);
                break;
            }
        }
    }

    static MapLocation[] getHQLocations(RobotController rc) throws GameActionException{
        // Fill an array list with the locations of the HQs
        ArrayList<MapLocation> hq_locs = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int hashed_loc = rc.readSharedArray(i);
            if (hashed_loc > 0) {
                hq_locs.add(MapHashUtil.unhashMapLocation(hashed_loc, rc.getMapWidth()));
            }
        }
        // Convert the array list to an array
        return hq_locs.toArray(new MapLocation[0]);
    }

    static boolean updateRobotCount(RobotController rc) throws GameActionException {
        int index = 3 + rc.getType().ordinal();
        int num_robots = rc.readSharedArray(index);
        if (rc.getRoundNum() % 2 == 1) {
            num_robots += 1;
        }
        else {
            num_robots += Math.pow(2, 8);
        }

        // Try to write
        if (rc.canWriteSharedArray(index, num_robots)) {
            rc.writeSharedArray(index, num_robots);
            return true;
        }
        else {
            return false;
        }
    }

    static int getPrevRobotCount(RobotController rc, RobotType robot) throws GameActionException {
        int index = 3 + robot.ordinal();
        return getNumFromBits(
                rc.readSharedArray(index), 9, 16);
    }

    // Moves the first 8 bits to the last 8 bits of all the robot counts
    static void resetCounts(RobotController rc) throws GameActionException {
        for (int i = 4; i < 9; i++) {
            int save_count = getNumFromBits(
                    rc.readSharedArray(i), 1, 8);
            if (save_count != 0) {
                save_count = save_count << 8;
                rc.writeSharedArray(i, save_count);
            }
        }
    }
}
