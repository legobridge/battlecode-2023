package tacoplayer;

import battlecode.common.MapLocation;
import battlecode.common.Team;

import java.util.Objects;

public class IslandInfo {
    public int id;
    public Team team;
    public MapLocation[] locations;

    public IslandInfo(int id, Team team, MapLocation[] locations) {
        this.id = id;
        this.team = team;
        this.locations = locations;
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
}
