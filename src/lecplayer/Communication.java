package lecplayer;

import battlecode.common.*;
import java.util.ArrayList;

class Message {
    public int idx;
    public int value;
    public int turnAdded;

    Message (int idx, int value, int turnAdded) {
        this.idx = idx;
        this.value = value;
        this.turnAdded = turnAdded;
    }
}

public class Communication {

    private static final int OUTDATED_TURNS_AMOUNT = 30;
    private static final int AREA_RADIUS = RobotType.CARRIER.visionRadiusSquared;

    // Maybe you want to change this based on exact amounts which you can get on turn 1
    static final int STARTING_ISLAND_IDX = 4;
    private static final int STARTING_ENEMY_IDX = GameConstants.MAX_NUMBER_ISLANDS + GameConstants.MAX_STARTING_HEADQUARTERS;

    private static final int TOTAL_BITS = 16;
    private static final int MAPLOC_BITS = 12;
    private static final int TEAM_BITS = 1;
    private static final int HEALTH_BITS = 3;
    private static final int HEALTH_SIZE = (int) Math.ceil(Anchor.ACCELERATING.totalHealth / 8.0);

    private static ArrayList<Message> messagesQueue = new ArrayList<>();
    private static MapLocation[] headquarterLocs = new MapLocation[GameConstants.MAX_STARTING_HEADQUARTERS];

    static void addHeadquarter (RobotController rc) throws GameActionException {
        MapLocation me = rc.getLocation();
        for (int i = 0; i < GameConstants.MAX_STARTING_HEADQUARTERS; i++) {
            if (rc.readSharedArray(i) == 0) {
                rc.writeSharedArray(i, locationToInt(rc, me));
                break;
            }
        }
    }

    static void updateHeadquarterInfo (RobotController rc) throws GameActionException {
        if (RobotPlayer.turnCount == 2) {
            for (int i = 0; i < GameConstants.MAX_STARTING_HEADQUARTERS; i++) {
                headquarterLocs[i] = intToLocation(rc, rc.readSharedArray(i));
                if (rc.readSharedArray(i) == 0) {
                    break;
                }
            }
        }
    }

    static void tryWriteMessages (RobotController rc) throws GameActionException {
        messagesQueue.removeIf(msg -> msg.turnAdded + OUTDATED_TURNS_AMOUNT < RobotPlayer.turnCount);
        // Can always write (0, 0), so just checks are we in range to write
        if (rc.canWriteSharedArray(0, 0)) {
            while (messagesQueue.size() > 0) {
                Message msg = messagesQueue.remove(0); // Take from front or back?
                if (rc.canWriteSharedArray(msg.idx, msg.value)) {
                    rc.writeSharedArray(msg.idx, msg.value);
                }
            }
        }
    }

    static void updateIslandInfo (RobotController rc, int id) throws GameActionException {
        MapLocation[] islandLocations = rc.senseNearbyIslandLocations(null, -1, id);
        if (islandLocations.length > 0) {
            int idx_to_write = id + STARTING_ISLAND_IDX - 1;
            int value = locationToInt(rc, islandLocations[0]);
            if (value != rc.readSharedArray(idx_to_write)) {
                rc.canWriteSharedArray(id, locationToInt(rc, islandLocations[0]));
            }
        }
    }

    static void clearObsoleteEnemies (RobotController rc) throws GameActionException {
        for (int i = STARTING_ENEMY_IDX; i < GameConstants.SHARED_ARRAY_LENGTH; i++) {
            MapLocation enemy  = intToLocation(rc, rc.readSharedArray(i));
        }
    }

    private static int locationToInt (RobotController rc, MapLocation m) {
        if (m == null) {
            return 0;
        }
        return 1 + m.x + m.y * rc.getMapWidth();
    }

    private static MapLocation intToLocation (RobotController rc, int m) {
        if (m == 0) {
            return null;
        }
        m--;
        return new MapLocation(m % rc.getMapWidth(), m / rc.getMapWidth());
    }
}
