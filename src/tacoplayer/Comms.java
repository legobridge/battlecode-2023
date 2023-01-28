package tacoplayer;

import battlecode.common.*;

import static tacoplayer.RobotPlayer.*;
import static tacoplayer.Sensing.*;

/**
 * Comms is the class used for robot communication
 * with the shared array
 */
public class Comms {

    final static int ROBOT_COUNT_START_INDEX = 3;
    final static int NUM_ISLANDS_STORED = 15;
    final static int ISLAND_LOCS_START_INDEX = 9;
    final static int ISLAND_IDS_START_INDEX = ISLAND_LOCS_START_INDEX + NUM_ISLANDS_STORED;
    final static int WELL_LOCS_START_INDEX = 39;
    final static int NUM_WELLS_STORED = 4;
    final static int SYMMETRY_INDEX = 63;
    final static int ALL_SYMMETRIES = 7;

    static int[] islandLocsTeams = new int[NUM_ISLANDS_STORED];
    static int[] islandIDsTurns = new int[NUM_ISLANDS_STORED];

    static int lastNonZeroIndex; // Last index of island recorded in the shared array
    static int[] islandIdToOnlineIndexMap = new int[GameConstants.MAX_NUMBER_ISLANDS + 1]; // Indices are stored with an added 1 to differentiate from zero

    static int locallyKnownSymmetry = 7;
    static boolean needToWriteWells = false;
    static boolean doneWithWells = false;
    static int wellUpdate = 0;
    static int[] sharedWellLocs = new int[NUM_WELLS_STORED];

    static void readAndStoreFromSharedArray(RobotController rc) throws GameActionException {
        // Read only the indices we're using, and update local knowledge with shared array knowledge.
        // Ensure that local knowledge is always a superset of shared array knowledge
        readIslandsFromSharedArray(rc);
        readWellsFromSharedArray(rc);
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
        for (int i = -1; ++i < 4; ) {
            // hashMapLocation adds 1 so the value of hashed_loc can never be 0
            if (rc.readSharedArray(i) == 0) {
                tryToWriteToSharedArray(rc, i, hashed_loc);
                break;
            }
        }
    }

    static void readOurHqLocs(RobotController rc) throws GameActionException {
        // This function is called only once by each Robot
        hqCount = 0;
        while (hqCount < 4) {
            int hqLocFromSharedArray = rc.readSharedArray(hqCount);
            if (hqLocFromSharedArray == 0) {
                break;
            } else {
                ourHqLocs[hqCount++] = MapLocationUtil.unhashMapLocation(hqLocFromSharedArray);
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
        // Find the location of the closest guessed enemy HQ
        MapLocation closestGuessedEnemyHqLoc = MapLocationUtil.getClosestMapLocEuclidean(rc, enemyHqLocs);
        closestEnemyHqLoc = closestGuessedEnemyHqLoc;

        // Compare with the location of the closest sensed enemy HQ
        if (closestGuessedEnemyHqLoc == null || rc.getLocation().distanceSquaredTo(closestGuessedEnemyHqLoc) > closestVisibleEnemyHqDistSq) {
            closestEnemyHqLoc = closestVisibleEnemyHq.location;
        }
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

    private static void readIslandsFromSharedArray(RobotController rc) throws GameActionException {
        lastNonZeroIndex = -1;
        for (int i = -1; ++i < NUM_ISLANDS_STORED; ) {
            // Read island info from shared array to local cache
            islandLocsTeams[i] = rc.readSharedArray(i + ISLAND_LOCS_START_INDEX);
            islandIDsTurns[i] = rc.readSharedArray(i + ISLAND_IDS_START_INDEX);
            if (islandIDsTurns[i] == 0) {
                break;
            }
            else {
                lastNonZeroIndex++;
                // For each island in the shared array, check if we know about it locally
                IslandInfo onlineIslandInfo = new IslandInfo(islandLocsTeams[i], islandIDsTurns[i]);
                int onlineIslandId = onlineIslandInfo.id;
                islandIdToOnlineIndexMap[onlineIslandId] = i + 1;
                if (knownIslands[onlineIslandId] == null) {
                    // This is a new island, add it to local array
                    knownIslandIds[knownIslandCount++] = onlineIslandId;
                    knownIslands[onlineIslandId] = onlineIslandInfo;
                } else if (knownIslands[onlineIslandId].turnLastSensed < onlineIslandInfo.turnLastSensed) {
                    // We know about this island, but the data online is more recent.
                    // Let's reset its values again, in case the team has changed
                    knownIslands[onlineIslandId].team = onlineIslandInfo.team;
                    knownIslands[onlineIslandId].turnLastSensed = onlineIslandInfo.turnLastSensed;
                }
            }
        }
    }

    static boolean tryToUploadWell(RobotController rc, int hashedLoc) throws GameActionException {
        for (int i = NUM_WELLS_STORED; --i >= 0; ) {
            if (sharedWellLocs[i] == hashedLoc) {
                break;
            }
            if (sharedWellLocs[i] == 0) {
                if (!tryToWriteToSharedArray(rc, i + WELL_LOCS_START_INDEX, hashedLoc)) {
                    needToWriteWells = true;
                    wellUpdate = hashedLoc;
                    return false;
                }
                else {
                    sharedWellLocs[i] = hashedLoc;
                    MapLocation loc = MapLocationUtil.unhashMapLocation(hashedLoc);
                    System.out.println("added mn well at " + loc.x + ", " + loc.y);
                    return true;
                }
            }
        }
        return false;
    }

    static void checkWellUpdates(RobotController rc) throws GameActionException {
        if (needToWriteWells) {
            if (tryToUploadWell(rc, wellUpdate)) {
                needToWriteWells = false;
                wellUpdate = 0;
            }
        }
    }

    static void readWellsFromSharedArray(RobotController rc) throws GameActionException {
        for (int i = -1; ++i < NUM_WELLS_STORED; ) {
            sharedWellLocs[i] = rc.readSharedArray(WELL_LOCS_START_INDEX + i);
            doneWithWells = sharedWellLocs[i] != 0;
        }
    }

    static void updateRobotCount(RobotController rc) throws GameActionException {
        int index = ROBOT_COUNT_START_INDEX + rc.getType().ordinal();
        int num_robots = rc.readSharedArray(index);
        tryToWriteToSharedArray(rc, index, num_robots + 1);
    }

    static int getPrevRobotCount(RobotController rc, RobotType robotType) throws GameActionException {
        int index = ROBOT_COUNT_START_INDEX + robotType.ordinal();
        return getNumFromBits(rc.readSharedArray(index), 9, 16);
    }

    // Moves the first 8 bits to the last 8 bits of all the robot counts
    static void resetCounts(RobotController rc) throws GameActionException {
        for (int i = 3; ++i < 9; ) {
            int save_count = getNumFromBits(rc.readSharedArray(i), 1, 8);
            save_count = save_count << 8;
            tryToWriteToSharedArray(rc, i, save_count);
        }
    }

    private static void addIsland(RobotController rc, IslandInfo islandInfo, int index) throws GameActionException {
        System.out.println("ADDING ISLAND: " + islandInfo.id);

        // Get the hashed location of an island square
        int islandLocHashed = MapLocationUtil.hashMapLocation(islandInfo.locations[0]);
        int islandTeamInt = islandInfo.team.ordinal();
        int newIslandLocTeam = bitHack(islandLocHashed, islandTeamInt, 12, 2);

        // Update the island ID to shared array index map
        islandIdToOnlineIndexMap[islandInfo.id] = index + 1;

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

        // Get the existing values from the shared array for this island
        int hashed_loc = getNumFromBits(islandLocsTeams[index], 1, 12);
        int oldTeamInt = getNumFromBits(islandLocsTeams[index], 13, 14);

        // Write the combined location and team to the shared array
        int islandTeamInt = islandInfo.team.ordinal();
        int newLocTeamInt = bitHack(hashed_loc, islandTeamInt, 12, 2);
        islandLocsTeams[index] = newLocTeamInt;
        tryToWriteToSharedArray(rc, index + ISLAND_LOCS_START_INDEX, newLocTeamInt);

        // Write the combined island id and turn sensed
        int islandIdTurn = IslandInfo.hashIslandIdAndTurnSensed(islandInfo.id, islandInfo.turnLastSensed);
        islandIDsTurns[index] = islandIdTurn;
        tryToWriteToSharedArray(rc, index + ISLAND_IDS_START_INDEX, islandIdTurn);

        System.out.println("CHANGED ISLAND TEAM: " + oldTeamInt + " TO " + islandTeamInt);
    }

    static void putIslandsOnline(RobotController rc) throws GameActionException {
        // Check if we are in wi-fi range
        if (!rc.canWriteSharedArray(0, 0)) {
            return;
        }
        // Check if each sensed island ID has not been found
        // Also update the island team
        for (int i = -1; ++i < knownIslandCount; ) {
            IslandInfo knownIslandInfo = knownIslands[knownIslandIds[i]];

            // Read the island info from the shared array
            int onlineIndex = islandIdToOnlineIndexMap[knownIslandInfo.id] - 1;
            if (onlineIndex == -1) { // This island isn't online
                // If the island was not found in the array, add it if there's space
                if (lastNonZeroIndex < NUM_ISLANDS_STORED) {
                    addIsland(rc, knownIslandInfo, ++lastNonZeroIndex);
                }
            }
            else { // This island is already online
                IslandInfo onlineIslandInfo = new IslandInfo(islandLocsTeams[onlineIndex], islandIDsTurns[onlineIndex]);
                if (onlineIslandInfo.turnLastSensed < knownIslandInfo.turnLastSensed) { // We have fresh data, let's upload it
                    if (knownIslandInfo.team != onlineIslandInfo.team) { // The island's team is out of date, let's update it
                        updateIslandTeam(rc, knownIslandInfo, onlineIndex);
                    } // TODO - worth it to update just the turn sensed value?
                }
            }
        }
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
        if (rc.readSharedArray(SYMMETRY_INDEX) > locallyKnownSymmetry) {
            tryToWriteToSharedArray(rc, SYMMETRY_INDEX, locallyKnownSymmetry);
        }
    }
}
