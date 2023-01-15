package tacoplayer;

import battlecode.common.*;

import java.awt.*;
import java.util.ArrayList;
import java.lang.Math;

/**
 * Comms is the class used for robot communication
 * with the shared array
 */
public class Comms {
    // Variable to ensure that counts aren't reset multiple times per round
    static int prev_round_counts_reset = 0;


    private static int getNumFromBits(int num, int bit_index1, int bit_index2) {
        int shifted = num >> (bit_index1 - 1);
        int mask = 0;
        for (int i = 0; i < bit_index2-bit_index1+1; i++) {
            mask += (int) Math.pow(2, i);
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
        return hq_locs.toArray(new MapLocation[hq_locs.size()]);
    }

    static boolean updateRobotCount(RobotController rc) throws GameActionException {
        int index = 4;
        switch (rc.getType()) {
            case CARRIER: index = 4;
            case LAUNCHER: index = 5;
            case AMPLIFIER: index = 6;
            case DESTABILIZER: index = 7;
            case BOOSTER: index = 8;
        }
        int num_robots = rc.readSharedArray(index) + 1;

        // Try to write
        if (rc.canWriteSharedArray(index, num_robots)) {
            rc.writeSharedArray(index, num_robots);
            return true;
        }
        else {
            return false;
        }
    }

    static int getRobotCount(RobotController rc, RobotType robot) throws GameActionException {
        int index = 4;
        switch (robot) {
            case CARRIER: index = 4;
            case LAUNCHER: index = 5;
            case AMPLIFIER: index = 6;
            case DESTABILIZER: index = 7;
            case BOOSTER: index = 8;
        }
        int num_robots = rc.readSharedArray(index);
        return num_robots;
    }

    static void resetCounts(RobotController rc) throws GameActionException {
        if (getCurrentRound(rc) != rc.getRoundNum()) {
            System.out.println("\nRound " + String.valueOf(rc.getRoundNum()));
            updateCurrentRound(rc);
            for (int i = 4; i < 9; i++) {
                int save_count = getNumFromBits(
                        rc.readSharedArray(i), 1, 8);
                save_count = save_count << 8;
                rc.writeSharedArray(i, save_count);
            }
        }
    }

    static void updateCurrentRound(RobotController rc) throws GameActionException {
        if (getCurrentRound(rc) != rc.getRoundNum()) {
            rc.writeSharedArray(9, rc.getRoundNum());
        }
    }

    static int getCurrentRound(RobotController rc) throws GameActionException {
        return rc.readSharedArray(9);
    }
}
