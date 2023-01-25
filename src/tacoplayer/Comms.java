package tacoplayer;

import battlecode.common.*;

import static battlecode.common.Team.NEUTRAL;
import static tacoplayer.RobotPlayer.*;

/**
 * Comms is the class used for robot communication
 * with the shared array
 */
public class Comms {

    // Only using keeping track of 10 neutral/enemy islands for bytecode purposes atm
    final static int NUM_ISLANDS_STORED = 10;
    final static int ISLAND_LOCS_START_INDEX = 9;
    final static int ISLAND_IDS_START_INDEX = 19;
    final static int SYMMETRY_INDEX = 63;
    final static int ALL_SYMMETRIES = 7;

    // The entire comms array at the start of this robot's turn
//    static int[] sharedArrayLocal = new int[GameConstants.SHARED_ARRAY_LENGTH];
    static int[] islandLocsTeams = new int[NUM_ISLANDS_STORED];
    static int[] islandIDsTurns = new int[NUM_ISLANDS_STORED];

    // Keep track of number of enemy and neutral islands
    static int numEnemyIslands = 0;
    static int numNeutralIslands = 0;
    static int roundUpdated = 0;
    static int locallyKnownSymmetry = 7;

    static void readAndStoreFromSharedArray(RobotController rc) throws GameActionException {
        // Read only the indices we're using, and update local knowledge with shared array knowledge.
        // Ensure that local knowledge is always a superset of shared array knowledge
        updateClassIslandArrays(rc);
        locallyKnownSymmetry &= rc.readSharedArray(SYMMETRY_INDEX);
    }

    private static boolean tryToWriteToSharedArray(RobotController rc, int index, int value) throws GameActionException {
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

        return nBitInPlace | mBitShifted;
    }

    static void putHqLocationOnline(RobotController rc) throws GameActionException {
        // This function is called only once by each HQ
        // Hash the 2D coordinates to 1D coordinates
        int hashed_loc = MapLocationUtil.hashMapLocation(rc.getLocation());

        // Write it to the shared array if it hasn't been written yet
        for (int i = 0; i < 4; i++) {
            // hashMapLocation adds 1 so the value of hashed_loc can never be 0
            if (rc.readSharedArray(i) == 0) {
                tryToWriteToSharedArray(rc, i, hashed_loc);
                break;
            }
        }
    }

    static void initializeAlliedHqLocs(RobotController rc) throws GameActionException {
        // This function is called only once by each Robot
        switch (rc.getType()) {
            case HEADQUARTERS:
                putHqLocationOnline(rc);
                break;
            default: // Note that this means HQs don't know about other allied HQs
                while (hqCount < 4) {
                    int hqLocFromSharedArray = rc.readSharedArray(hqCount);
                    if (hqLocFromSharedArray == 0) {
                        break;
                    } else {
                        ourHqLocs[hqCount++] = MapLocationUtil.unhashMapLocation(hqLocFromSharedArray);
                    }
                }
        }
    }

    static boolean isFirstHQ(RobotController rc) throws GameActionException {
        int robot_location = MapLocationUtil.hashMapLocation(rc.getLocation());
        return robot_location == rc.readSharedArray(0);
    }

    static void updateEnemyHqLocs(RobotController rc) throws GameActionException {
        for (int i = -1; ++i < hqCount; ) { // If we are in range of an enemy HQ, check if symmetry assumptions are valid
            validateSymmetry(rc, i, SymmetryType.HORIZONTAL.ordinal(), 3);
            validateSymmetry(rc, i, SymmetryType.VERTICAL.ordinal(), 5);
            validateSymmetry(rc, i, SymmetryType.ROTATIONAL.ordinal(), 6);
        }
        boolean[] symmetries = getMapSymmetries();
        for (int i = -1; ++i < hqCount; ) { // Based on current symmetry assumptions, populate guesses for enemy HQ locs
            for (int j = -1; ++j < symmetries.length; ) {
                if (!symmetries[j]) {
                    enemyHqLocs[j * 4 + i] = null;
                } else {
                    enemyHqLocs[j * 4 + i] = MapLocationUtil.calcSymmetricLoc(ourHqLocs[i], SymmetryType.values()[j]);
                }
            }
        }
        // Update closest Enemy HQ Location
        closestEnemyHqLoc = MapLocationUtil.getClosestMapLocEuclidean(rc, enemyHqLocs);
    }

    private static void validateSymmetry(RobotController rc, int hqNum, int symmetryType, int mostSymmetryPossible) throws GameActionException {
        MapLocation enemyHqLoc = enemyHqLocs[symmetryType * 4 + hqNum];
        if (enemyHqLoc == null) {
            return;
        }
        if (rc.canSenseLocation(enemyHqLoc)) {
            RobotInfo robotInfo = rc.senseRobotAtLocation(enemyHqLoc);
            if (robotInfo == null || robotInfo.type != RobotType.HEADQUARTERS || robotInfo.team != theirTeam) {
                // This is not a valid symmetry!
                updateSymmetry(mostSymmetryPossible);
            }
        }
    }

    private static void updateClassIslandArrays(RobotController rc) throws GameActionException {
        // TODO - replace data struct with custom hashmap
        for (int i = -1; ++i < NUM_ISLANDS_STORED; ) {
            // For each island in the shared array, check if we know about it locally
            islandLocsTeams[i] = rc.readSharedArray(i + ISLAND_LOCS_START_INDEX);
            islandIDsTurns[i] = rc.readSharedArray(i + ISLAND_IDS_START_INDEX);
            if (islandLocsTeams[i] == 0 || islandIDsTurns[i] == 0) {
                continue;
            }

            IslandInfo islandIdTurnObj = IslandInfo.unhashIslandIdAndTurnSensed(islandIDsTurns[i]);
            int islandId = islandIdTurnObj.id;
            int turnLastSensed = islandIdTurnObj.turnLastSensed;

            for (int j = -1; ++j < knownIslands.length; ) {
                if (knownIslands[j] == null) {
                    // This is a new island, add it to local array
                    int hashedLoc = getNumFromBits(islandLocsTeams[i], 1, 12); // Bits 1-12 are for location
                    Team islandTeam = convertSharedArrayTeamToTeam(islandLocsTeams[i]);
                    MapLocation islandLocation = MapLocationUtil.unhashMapLocation(hashedLoc);

                    knownIslands[j] = new IslandInfo(islandId, islandTeam, new MapLocation[]{islandLocation}, turnLastSensed);
                    break;
                } else if (knownIslands[j].id == islandId) {
                    if (knownIslands[j].turnLastSensed < turnLastSensed) {
                        // We know about this island, but not recently.
                        // Let's reset its values again, in case the team has changed
                        knownIslands[j].team = convertSharedArrayTeamToTeam(islandLocsTeams[i]);
                        knownIslands[j].turnLastSensed = turnLastSensed;
                    }
                    break;
                }
            }
        }
    }

    private static Team convertSharedArrayTeamToTeam(int sharedArrayTeam) {
        int islandTeamEncoded = getNumFromBits(sharedArrayTeam, 13, 14); // Bits 13-14 are for team
        Team islandTeam;
        switch (islandTeamEncoded) {
            case 1:
                islandTeam = theirTeam;
                break;
            case 2:
                islandTeam = NEUTRAL;
                break;
            default:
                islandTeam = ourTeam; // THIS SHOULD NEVER HAPPEN
        }
        return islandTeam;
    }

    private static int convertTeamToSharedArrayTeam(Team team) {
        // 0 - Ours, 1 - Enemy, 2 - Neutral, 3 - Unknown

        // If the island is ours, discard it for now
        // Else convert the team to a 2 bit number to be stored
        if (team == ourTeam) {
            return 0;
        }
        if (team == theirTeam) {
            return 1;
        }
        if (team == NEUTRAL) {
            return 2;
        }
        return 3;
    }


    static boolean updateRobotCount(RobotController rc) throws GameActionException {
        // TODO - maybe don't do all of this
        int index = 3 + rc.getType().ordinal();
        int num_robots = rc.readSharedArray(index);
        if (rc.getRoundNum() % 2 == 1) {
            num_robots += 1;
        } else {
            num_robots += Math.pow(2, 8);
        }

        // Try to write
        return tryToWriteToSharedArray(rc, index, num_robots);
    }

    static int getPrevRobotCount(RobotController rc, RobotType robot) throws GameActionException {
        int index = 3 + robot.ordinal();
        return getNumFromBits(rc.readSharedArray(index), 9, 16);
    }

    // Moves the first 8 bits to the last 8 bits of all the robot counts

    static void resetCounts(RobotController rc) throws GameActionException {
        for (int i = 4; i < 9; i++) {
            int save_count = getNumFromBits(rc.readSharedArray(i), 1, 8);
            save_count = save_count << 8;
            tryToWriteToSharedArray(rc, i, save_count);
        }
    }

    static int getNumNeutralIslands(RobotController rc) {
        // Count neutral and enemy islands
        countIslands(rc);
        return numNeutralIslands;
    }

    static int getNumEnemyIslands(RobotController rc) {
        // Count neutral and enemy islands
        countIslands(rc);
        return numEnemyIslands;
    }

    private static void countIslands(RobotController rc) {
        if (roundUpdated == rc.getRoundNum()) {
            return;
        }
        roundUpdated = rc.getRoundNum();
        numNeutralIslands = 0;
        numEnemyIslands = 0;
        for (int i = -1; ++i < knownIslands.length; ) {
            if (knownIslands[i] == null) {
                break;
            }
            if (knownIslands[i].team == theirTeam) {
                numEnemyIslands++;
            } else if (knownIslands[i].team == NEUTRAL) {
                numNeutralIslands++;
            }
        }
    }

    static void putIslandsOnline(RobotController rc) throws GameActionException {
        // Check if we are in wi-fi range
        if (!rc.canWriteSharedArray(0, 0)) {
            return;
        }
        // Check if each sensed island ID has not been found
        // Also update the island team
        for (int i = -1; ++i < knownIslands.length; ) {
            IslandInfo islandInfo = knownIslands[i];
            if (islandInfo == null) {
                break;
            }

            boolean found = false; // Flag for whether the island_id is in the shared array
            int firstZeroIndex = -1; // First open space in island data for new data to be recorded

            // Iterate through all island IDs
            for (int j = -1; ++j < NUM_ISLANDS_STORED; ) {

                // Record the first 0 that occurs
                if (islandIDsTurns[j] == 0) {
                    if (firstZeroIndex == -1) {
                        firstZeroIndex = j;
                    }
                    continue;
                }

                Team islandTeam = convertSharedArrayTeamToTeam(islandLocsTeams[j]);

                // Read the id and turn last sensed from the shared array
                IslandInfo islandIdTurnFromSharedArray = IslandInfo.unhashIslandIdAndTurnSensed(islandIDsTurns[j]);

                // See if island_ids matches either of the ids at this index
                if (islandInfo.id == islandIdTurnFromSharedArray.id) {
                    // Mark that we found it
                    found = true;
                    if (islandIdTurnFromSharedArray.turnLastSensed < islandInfo.turnLastSensed) { // We have fresh data, let's upload it
                        if (islandInfo.team == ourTeam) { // If we own the island now, remove it
                            removeIsland(rc, j);
                        } else if (islandInfo.team != islandTeam){ // Otherwise update its team
                            updateIslandTeam(rc, islandInfo, j);
                        }
                    }
                    break;
                }
            }
            // If the island was not found in the array, add it if there's space
            if (!found && firstZeroIndex != -1) {
                addIsland(rc, islandInfo, firstZeroIndex);
            }
        }
    }

    private static void addIsland(RobotController rc, IslandInfo islandInfo, int index) throws GameActionException {
        System.out.println("ADDING ISLAND: " + islandInfo.id);

        // Get the hashed location of an island square
        int islandLocHashed = MapLocationUtil.hashMapLocation(islandInfo.locations[0]);
        int islandTeamInt = convertTeamToSharedArrayTeam(islandInfo.team);
        int newIslandLocTeam = bitHack(islandLocHashed, islandTeamInt, 12, 2);

        // Write location-team value
        islandLocsTeams[index] = newIslandLocTeam;
        tryToWriteToSharedArray(rc, index + ISLAND_LOCS_START_INDEX, newIslandLocTeam);

        // Get the combined island id and turn sensed
        int islandIdTurn = IslandInfo.hashIslandIdAndTurnSensed(islandInfo.id, islandInfo.turnLastSensed);

        // Write island ID
        islandIDsTurns[index] = islandIdTurn;
        tryToWriteToSharedArray(rc, index + ISLAND_IDS_START_INDEX, islandIdTurn);
    }

    private static void updateIslandTeam(RobotController rc, IslandInfo islandInfo, int index) throws GameActionException {
        int islandTeamInt = convertTeamToSharedArrayTeam(islandInfo.team);

        // Get the values from the shared array for this island
        int array_element = islandLocsTeams[index];
        int hashed_loc = getNumFromBits(array_element, 1, 12);
        int oldTeamInt = getNumFromBits(array_element, 13, 14);

        System.out.println("CHANGING ISLAND TEAM: " + oldTeamInt + " TO " + islandTeamInt);
        int new_element = bitHack(hashed_loc, islandTeamInt, 12, 2);

        islandLocsTeams[index] = new_element;
        tryToWriteToSharedArray(rc, index + ISLAND_LOCS_START_INDEX, new_element);

        // Get the combined island id and turn sensed
        int islandIdTurn = IslandInfo.hashIslandIdAndTurnSensed(islandInfo.id, islandInfo.turnLastSensed);

        // Write island ID
        islandIDsTurns[index] = islandIdTurn;
        tryToWriteToSharedArray(rc, index + ISLAND_IDS_START_INDEX, islandIdTurn);
    }

    private static void removeIsland(RobotController rc, int index) throws GameActionException {
        System.out.println("REMOVING ISLAND");

        // Erase location-team data
        islandLocsTeams[index] = 0;
        tryToWriteToSharedArray(rc, index + ISLAND_LOCS_START_INDEX, 0);

        // Erase id data
        islandIDsTurns[index] = 0;
        tryToWriteToSharedArray(rc, index + ISLAND_IDS_START_INDEX, 0);

    }

    public static void initializeSymmetry(RobotController rc) throws GameActionException {
        tryToWriteToSharedArray(rc, SYMMETRY_INDEX, ALL_SYMMETRIES);
    }

    public static void updateSymmetry(int mostSymmetryPossible) {
        locallyKnownSymmetry = locallyKnownSymmetry & mostSymmetryPossible;
    }

    public static boolean[] getMapSymmetries() {
        switch (locallyKnownSymmetry) {
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

    public static void putSymmetryOnline(RobotController rc) throws GameActionException {
        // Check if we are in wi-fi range
        if (!rc.canWriteSharedArray(0, 0)) {
            return;
        }
        tryToWriteToSharedArray(rc, SYMMETRY_INDEX, locallyKnownSymmetry);
    }
}
