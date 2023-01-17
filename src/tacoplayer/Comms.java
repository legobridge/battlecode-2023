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
    // Only using keeping track of 10 neutral/enemy islands for bytecode purposes atm
    static int islandLocsStart = 9;
    static int islandLocsEnd = 18;
    static int islandIDsStart = 19;
    static int islandIDsEnd = 23;
    static int[] islandLocsTeams = new int[islandLocsEnd-islandLocsStart+1];
    static int[] islandIDs = new int[islandIDsEnd-islandIDsStart+1];

    // Keep track of number of enemy and neutral islands
    static int numEnemyIslands = 0;
    static int numNeutralIslands = 0;
    static int roundUpdated = 0;

    static int getNumFromBits(int num, int bit_index1, int bit_index2) {
        int shifted = num >> (bit_index1 - 1);
        int mask = 0;
        for (int i = 0; i < bit_index2-bit_index1+1; i++) {
            mask += 1 << i;
        }
        return shifted & mask;
    }

    static int bitHack(int nBit, int mBit, int n, int m) {
        int mask = (1 << n) - 1; // mask with n leading 1s and (16 - n) trailing 0s
        int nBitInPlace = nBit & mask; // write the n-bit number to the first n bits

        int mBitShifted = mBit << n; // left-shift the m-bit number by n bits
        int result = nBitInPlace | mBitShifted; // use bitwise OR to write the m-bit number to the next m bits

        return result;
    }

    static void updateHQLocation(RobotController rc) throws GameActionException {
        // Hash the 2D coordinate to a 1D coordinate
        MapLocation hq_loc = rc.getLocation();
        int hashed_loc = MapHashUtil.hashMapLocation(hq_loc);

        // Write it to the shared array if it hasn't been written yet
        for (int i = 0; i < 4; i++) {
            // hashMapLocation adds 1 so the value of hashed_loc can never be 0
            if (rc.readSharedArray(i) == 0) {
                rc.writeSharedArray(i, hashed_loc);
                break;
            }
        }
    }

    static boolean isFirstHQ(RobotController rc) throws GameActionException {
        int robot_location = MapHashUtil.hashMapLocation(rc.getLocation());
        if (robot_location == rc.readSharedArray(0)) {
            return true;
        }
        else {
            return false;
        }
    }

    static MapLocation[] getHQLocations(RobotController rc) throws GameActionException{
        // Fill an array list with the locations of the HQs
        ArrayList<MapLocation> hq_locs = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int hashed_loc = rc.readSharedArray(i);
            if (hashed_loc > 0) {
                hq_locs.add(MapHashUtil.unhashMapLocation(hashed_loc));
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
            save_count = save_count << 8;
            rc.writeSharedArray(i, save_count);
        }
    }

    static int getNumNeutralIslands(RobotController rc) throws GameActionException {
        // If the array has already been searched by an HQ, this number has been recorded
        if (roundUpdated == rc.getRoundNum()) {
            return numNeutralIslands;
        }

        // Else count neutral and enemy islands
        countIslands(rc);

        return numNeutralIslands;
    }

    static int getNumEnemyIslands(RobotController rc) throws GameActionException {
        // If the array has already been searched by an HQ, this number has been recorded
        if (roundUpdated == rc.getRoundNum()) {
            return numEnemyIslands;
        }

        // Else count neutral and enemy islands
        countIslands(rc);

        return numEnemyIslands;
    }

    private static void countIslands(RobotController rc) throws GameActionException {
        roundUpdated = rc.getRoundNum();
        numNeutralIslands = 0;
        numEnemyIslands = 0;
        for (int i = islandLocsStart; i <= islandLocsEnd; i++) {
            int array_element = rc.readSharedArray(i);
            int team = getNumFromBits(array_element, 13, 14);
            if (team == 1) {
                numEnemyIslands++;
            }
            else if (team == 2) {
                numNeutralIslands++;
            }
        }
//        System.out.println("ROUND: " + String.valueOf(roundUpdated));
//        System.out.println("NEUTRAL: " + String.valueOf(numNeutralIslands));
//        System.out.println("ENEMY: " + String.valueOf(numEnemyIslands));
    }

    static void updateIslands(RobotController rc) throws GameActionException {
        // Record Island data once so byte code doesn't have to be
        // wasted by reading the shared array many times
        for (int i = islandLocsStart; i <= islandLocsEnd; i++) {
            islandLocsTeams[i-islandLocsStart] = rc.readSharedArray(i);
        }
        for (int i = islandIDsStart; i <= islandIDsEnd; i++) {
            islandIDs[i-islandIDsStart] = rc.readSharedArray(i);
        }

        int[] island_ids = rc.senseNearbyIslands();

        // Check if each sensed island ID has not been found
        // Also update the island team
        for (int island_id: island_ids) {
            boolean found = false; // Flag for whether or not the island_id is in the shared array
            int first_zero = islandIDsStart; // First open space in island data for new data to be recorded
            Team island_team = rc.senseTeamOccupyingIsland(island_id); // Team that occupies the island

            // Iterate through all island IDs
            for (int i = islandIDsStart; i <= islandIDsEnd; i++) {
                // Read the ids from the shared array
                int ids = islandIDs[i - islandIDsStart];

                // Each index holds 2 6-bit IDs
                int id1 = getNumFromBits(ids, 1, 6);
                int id2 = getNumFromBits(ids, 7, 12);

                // See if island_ids matches either of the ids at this index
                if (island_id == id1 || island_id == id2) {
                    // Mark that we found it
                    found = true;

                    // Get the index for the island's location data
                    int idLocIndex = islandLocsStart + 2 * (i - islandIDsStart);
                    if (island_id == id2) {
                        idLocIndex++;
                    }

                    // If we own the island, remove it
                    // Otherwise update its team
                    if (island_team == rc.getTeam()) {
                        removeIsland(rc, idLocIndex);
                    }
                    else {
                        updateIslandTeam(rc, island_team, idLocIndex);
                    }
                    break;
                }

                // Record the first 0 that occurs
                if (id1 == 0 && first_zero == islandIDsStart) {
                    first_zero = islandLocsStart + 2 * (i - islandIDsStart);
                }
                if (id2 == 0 && first_zero == islandIDsStart) {
                    first_zero = islandLocsStart+1 + 2 * (i - islandIDsStart);
                }
            }

            // If the island was not found in the array, add it if there's space
            if (!found && first_zero < islandIDsStart) {
                addIsland(rc, island_id, island_team, first_zero);
            }
        }
    }

    private static void addIsland(RobotController rc, int island_id, Team island_team, int index) throws GameActionException {
        // Determine if the island is under our control
        boolean our_team = island_team == rc.getTeam();

        // If we don't control the island and we can write to the shared array, write it
        if (!our_team && rc.canWriteSharedArray(index, 0)){
            System.out.println("ADDING ISLAND: " + String.valueOf(island_id));

            // Get the hashed location of an island square
            MapLocation island_loc = rc.senseNearbyIslandLocations(island_id)[0];
            int island_loc_hashed = MapHashUtil.hashMapLocation(island_loc);

            // Write location-team value
            rc.writeSharedArray(index, island_loc_hashed);
            updateIslandTeam(rc, island_team, index);

            // Write island ID
            updateIslandID(rc, island_id, index);
        }
    }

    private static void updateIslandID(RobotController rc, int island_id, int loc_index) throws GameActionException {
        // Get the island's ID index based on its location index
        int id_index = (loc_index - islandLocsStart) / 2 + islandIDsStart;

        // Determine if the ID is the first 6 bits or the next 6 bits and write it
        if ( (loc_index - islandLocsStart) % 2 == 0) {
            // Update first 6 bits of the array element
            int ids = islandIDs[id_index-islandIDsStart];
            int id1 = island_id;
            int id2 = getNumFromBits(ids, 7, 12);
            int new_element = bitHack(id1, id2, 6, 6);
            rc.writeSharedArray(id_index, new_element);
        }
        else {
            // Update bits 7-12 of the array element
            int ids = islandIDs[id_index-islandIDsStart];
            int id1 = getNumFromBits(ids, 1, 6);
            int id2 = island_id;
            int new_element = bitHack(id1, id2, 6, 6);
            rc.writeSharedArray(id_index, new_element);
        }
    }

    private static void updateIslandTeam(RobotController rc, Team island_team, int index) throws GameActionException {
        int island_team_int = 0; // 0 - Ours, 1 - Enemy, 2 - Neutral, 3 - Unknown

        // If the island is ours, discard it for now
        // Else convert the team to a 2 bit number to be stored
        if (island_team == rc.getTeam()) {
            removeIsland(rc, index);
            return;
        } else {
            if (island_team == Team.NEUTRAL) {
                island_team_int = 2;
            } else {
                island_team_int = 1;
            }
        }

        // Get the values from the shared array for this island
        int array_element = rc.readSharedArray(index);
        int hashed_loc = getNumFromBits(array_element, 1, 12);
        int team = getNumFromBits(array_element, 13, 14);

        // If the team has changed, update it
        if (team != island_team_int) {
            System.out.println("CHANGING ISLAND TEAM: " + Integer.valueOf(team) + " TO " + Integer.valueOf(island_team_int));
            int new_element = bitHack(hashed_loc, island_team_int, 12, 2);
            if (rc.canWriteSharedArray(index, new_element)) {
                rc.writeSharedArray(index, new_element);
            }
        }
    }

    private static void removeIsland(RobotController rc, int index) throws GameActionException {
        if (rc.canWriteSharedArray(index, 0)) {
            System.out.println("REMOVING ISLAND");

            // Erase location-team data
            rc.writeSharedArray(index, 0);

            // Erase id data
            updateIslandID(rc, 0, index);
        }
    }
}
