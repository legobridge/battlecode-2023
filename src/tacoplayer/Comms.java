package tacoplayer;

import battlecode.common.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Comms is the class used for robot communication
 * with the shared array
 */
public class Comms {

    // Only using keeping track of 10 neutral/enemy islands for bytecode purposes atm
    final static int islandLocsStart = 9;
    final static int islandLocsEnd = 18;
    final static int islandIDsStart = 19;
    final static int islandIDsEnd = 23;
    final static int SYMMETRY_INDEX = 63;
    private static final int ALL_SYMMETRIES = 7;
    // The entire comms array at the start of this robot's turn
    static int[] sharedArrayLocal = new int[GameConstants.SHARED_ARRAY_LENGTH];
    static int[] islandLocsTeams = new int[islandLocsEnd - islandLocsStart + 1];
    static int[] islandIDs = new int[islandIDsEnd - islandIDsStart + 1];

    // Keep track of number of enemy and neutral islands
    static int numEnemyIslands = 0;
    static int numNeutralIslands = 0;
    static int roundUpdated = 0;

    static void readAndStoreSharedArray(RobotController rc) throws GameActionException {
        for (int i = -1; ++i < GameConstants.SHARED_ARRAY_LENGTH; ) {
            sharedArrayLocal[i] = rc.readSharedArray(i);
        }
    }

    private static boolean tryToWriteToSharedArray(RobotController rc, int index, int value) throws GameActionException {
        sharedArrayLocal[index] = value; // TODO - Store this somewhere, write to shared array once in signal range.
                                         //        As of now, it will be overwritten on the next turn.
        if (rc.canWriteSharedArray(index, value)) {
            rc.writeSharedArray(index, value);
            return true;
        }
        return false;
    }

    static int getNumFromBits(int num, int bit_index1, int bit_index2) {
        int shifted = num >> (bit_index1 - 1);
        int mask = (1 << (bit_index2 - bit_index1 + 1)) - 1;
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
        int hashed_loc = MapLocationUtil.hashMapLocation(hq_loc);

        // Write it to the shared array if it hasn't been written yet
        for (int i = 0; i < 4; i++) {
            // hashMapLocation adds 1 so the value of hashed_loc can never be 0
            if (sharedArrayLocal[i] == 0) {
                tryToWriteToSharedArray(rc, i, hashed_loc);
                break;
            }
        }
    }

    static boolean isFirstHQ(RobotController rc) {
        int robot_location = MapLocationUtil.hashMapLocation(rc.getLocation());
        return robot_location == sharedArrayLocal[0];
    }

    static boolean updateRobotCount(RobotController rc) throws GameActionException {
        int index = 3 + rc.getType().ordinal();
        int num_robots = sharedArrayLocal[index];
        if (rc.getRoundNum() % 2 == 1) {
            num_robots += 1;
        } else {
            num_robots += Math.pow(2, 8);
        }

        // Try to write
        return tryToWriteToSharedArray(rc, index, num_robots);
    }

    static int getPrevRobotCount(RobotController rc, RobotType robot) {
        int index = 3 + robot.ordinal();
        return getNumFromBits(
                sharedArrayLocal[index], 9, 16);
    }

    // Moves the first 8 bits to the last 8 bits of all the robot counts
    static void resetCounts(RobotController rc) throws GameActionException {
        for (int i = 4; i < 9; i++) {
            int save_count = getNumFromBits(
                    sharedArrayLocal[i], 1, 8);
            save_count = save_count << 8;
            tryToWriteToSharedArray(rc, i, save_count);
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

    static MapLocation getClosestNeutralIsland(RobotController rc) throws GameActionException {
        updateClassIslandArrays(rc);
        List<Integer> neutralIslands = new ArrayList<>();
        for (int arrayElement : islandLocsTeams) {
            int hashedLoc = getNumFromBits(arrayElement, 1, 12); // Bits 1-12 are for location
            int team = getNumFromBits(arrayElement, 13, 14); // Bits 13-14 are for team
            if (team == 2) {
                neutralIslands.add(hashedLoc);
            }
        }
        MapLocation closest = MapLocationUtil.getClosestLocation(rc.getLocation(), neutralIslands);
        return closest;
    }

    static MapLocation getClosestEnemyIsland(RobotController rc) throws GameActionException {
        updateClassIslandArrays(rc);
        List<Integer> neutralIslands = new ArrayList<>();
        for (int arrayElement : islandLocsTeams) {
            int hashedLoc = getNumFromBits(arrayElement, 1, 12); // Bits 1-12 are for location
            int team = getNumFromBits(arrayElement, 13, 14); // Bits 13-14 are for team
            if (team == 1) {
                neutralIslands.add(hashedLoc);
            }
        }
        return MapLocationUtil.getClosestLocation(rc.getLocation(), neutralIslands);
    }

    private static void countIslands(RobotController rc) {
        roundUpdated = rc.getRoundNum();
        numNeutralIslands = 0;
        numEnemyIslands = 0;
        for (int i = islandLocsStart; i <= islandLocsEnd; i++) {
            int array_element = sharedArrayLocal[i];
            int team = getNumFromBits(array_element, 13, 14);
            if (team == 1) {
                numEnemyIslands++;
            } else if (team == 2) {
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
        updateClassIslandArrays(rc);

        int[] island_ids = rc.senseNearbyIslands();

        // Check if each sensed island ID has not been found
        // Also update the island team
        for (int island_id : island_ids) {
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
                    } else {
                        updateIslandTeam(rc, island_team, idLocIndex);
                    }
                    break;
                }

                // Record the first 0 that occurs
                if (id1 == 0 && first_zero == islandIDsStart) {
                    first_zero = islandLocsStart + 2 * (i - islandIDsStart);
                }
                if (id2 == 0 && first_zero == islandIDsStart) {
                    first_zero = islandLocsStart + 1 + 2 * (i - islandIDsStart);
                }
            }

            // If the island was not found in the array, add it if there's space
            if (!found && first_zero < islandIDsStart) {
                addIsland(rc, island_id, island_team, first_zero);
            }
        }
    }

    private static void updateClassIslandArrays(RobotController rc) {
        if (rc.getRoundNum() != roundUpdated) {
            System.arraycopy(sharedArrayLocal, islandLocsStart, islandLocsTeams, 0, islandLocsEnd - 8);
            System.arraycopy(sharedArrayLocal, islandIDsStart, islandIDs, 0, islandIDsEnd - 18);
            roundUpdated = rc.getRoundNum();
        }
    }

    private static void addIsland(RobotController rc, int island_id, Team island_team, int index) throws GameActionException {
        // Determine if the island is under our control
        boolean our_team = island_team == rc.getTeam();

        // If we don't control the island and we can write to the shared array, write it
        if (!our_team && rc.canWriteSharedArray(index, 0)) {
//            System.out.println("ADDING ISLAND: " + island_id);

            // Get the hashed location of an island square
            MapLocation island_loc = rc.senseNearbyIslandLocations(island_id)[0];
            int island_loc_hashed = MapLocationUtil.hashMapLocation(island_loc);

            // Write location-team value
            tryToWriteToSharedArray(rc, index, island_loc_hashed);
            updateIslandTeam(rc, island_team, index);

            // Write island ID
            updateIslandID(rc, island_id, index);
        }
    }

    private static void updateIslandID(RobotController rc, int island_id, int loc_index) throws GameActionException {
        // Get the island's ID index based on its location index
        int id_index = (loc_index - islandLocsStart) / 2 + islandIDsStart;

        // Determine if the ID is the first 6 bits or the next 6 bits and write it
        if ((loc_index - islandLocsStart) % 2 == 0) {
            // Update first 6 bits of the array element
            int ids = islandIDs[id_index - islandIDsStart];
            int id1 = island_id;
            int id2 = getNumFromBits(ids, 7, 12);
            int new_element = bitHack(id1, id2, 6, 6);
            tryToWriteToSharedArray(rc, id_index, new_element);
        } else {
            // Update bits 7-12 of the array element
            int ids = islandIDs[id_index - islandIDsStart];
            int id1 = getNumFromBits(ids, 1, 6);
            int id2 = island_id;
            int new_element = bitHack(id1, id2, 6, 6);
            tryToWriteToSharedArray(rc, id_index, new_element);
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
        int array_element = sharedArrayLocal[index];
        int hashed_loc = getNumFromBits(array_element, 1, 12);
        int team = getNumFromBits(array_element, 13, 14);

        // If the team has changed, update it
        if (team != island_team_int) {
//            System.out.println("CHANGING ISLAND TEAM: " + Integer.valueOf(team) + " TO " + Integer.valueOf(island_team_int));
            int new_element = bitHack(hashed_loc, island_team_int, 12, 2);
            tryToWriteToSharedArray(rc, index, new_element);
        }
    }

    private static void removeIsland(RobotController rc, int index) throws GameActionException {
        if (rc.canWriteSharedArray(index, 0)) {
//            System.out.println("REMOVING ISLAND");

            // Erase location-team data
            tryToWriteToSharedArray(rc, index, 0);

            // Erase id data
            updateIslandID(rc, 0, index);
        }
    }

    public static void initializeSymmetry(RobotController rc) throws GameActionException {
        tryToWriteToSharedArray(rc, SYMMETRY_INDEX, ALL_SYMMETRIES);
    }

    public static void updateSymmetry(RobotController rc, int mostSymmetryPossible) throws GameActionException {
        tryToWriteToSharedArray(rc, SYMMETRY_INDEX, sharedArrayLocal[SYMMETRY_INDEX] & mostSymmetryPossible);
    }

    public static boolean[] getMapSymmetries() {
        switch (sharedArrayLocal[SYMMETRY_INDEX]) {
            case 1:
                return new boolean[]{false, false, true};
            case 2:
                return new boolean[]{false, true, false};
            case 3:
                return new boolean[]{false, true, true};
            case 4:
                return new boolean[]{true, false, false};
            case 5:
                return new boolean[]{true, false, true};
            case 6:
                return new boolean[]{true, true, false};
            default:
                return new boolean[]{true, true, true};
        }
    }
}
