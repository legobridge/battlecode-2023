package rohinplayer;

import battlecode.common.*;

public class LocationAndDistance implements Comparable<LocationAndDistance> {
    MapLocation mapLocation;
    int distFromCurrentUnit;

    public LocationAndDistance(MapLocation mapLocation, int distFromCurrentUnit) {
        this.mapLocation = mapLocation;
        this.distFromCurrentUnit = distFromCurrentUnit;
    }


    @Override
    public int compareTo(LocationAndDistance other) {
        return this.distFromCurrentUnit - other.distFromCurrentUnit;
    }
}
