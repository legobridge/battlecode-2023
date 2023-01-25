package tacoplayer;

import battlecode.common.GameConstants;
import battlecode.common.MapLocation;
import battlecode.common.Team;

import java.util.Objects;

public class IslandInfo {
    public int id;
    public int turnLastSensed;
    public Team team;
    public MapLocation[] locations;
    final static int MAX_ISLAND_ID = GameConstants.MAX_NUMBER_ISLANDS + 1;

    public IslandInfo(int id, int turnLastSensed, Team team, MapLocation[] locations) {
        this.id = id;
        this.turnLastSensed = turnLastSensed;
        this.team = team;
        this.locations = locations;
    }

    public IslandInfo(int islandLocTeam, int islandIdTurnSensed) {
        islandIdTurnSensed--;
        this.id = islandIdTurnSensed % MAX_ISLAND_ID;
        this.turnLastSensed = islandIdTurnSensed / MAX_ISLAND_ID;
        MapLocation mapLocation = MapLocationUtil.unhashMapLocation(Comms.getNumFromBits(islandLocTeam, 1, 12));
        this.locations = new MapLocation[]{mapLocation};
        this.team = Team.values()[Comms.getNumFromBits(islandLocTeam, 13, 14)];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslandInfo that = (IslandInfo) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public static int hashIslandIdAndTurnSensed(int islandId, int turnSensed) {
        return turnSensed * MAX_ISLAND_ID + islandId + 1;
    }
}
