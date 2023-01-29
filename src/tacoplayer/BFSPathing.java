// Inspired by https://github.com/IvanGeffner/battlecode2021/blob/master/thirtyone/BFSMuckraker.java.
package tacoplayer;

import battlecode.common.*;

public class BFSPathing {

    RobotController rc;


    static MapLocation l0; // location representing relative coordinate (-3, -3)
    static int dwalk0; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk0; // best direction to take now if the last move was a walk
    static boolean p0; // is the location passable
    static Direction c0; // direction of the current at the location

    static MapLocation l15; // location representing relative coordinate (-3, -2)
    static int dwalk15; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk15; // best direction to take now if the last move was a walk
    static boolean p15; // is the location passable
    static Direction c15; // direction of the current at the location

    static MapLocation l30; // location representing relative coordinate (-3, -1)
    static int dwalk30; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk30; // best direction to take now if the last move was a walk
    static boolean p30; // is the location passable
    static Direction c30; // direction of the current at the location

    static MapLocation l45; // location representing relative coordinate (-3, 0)
    static int dwalk45; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk45; // best direction to take now if the last move was a walk
    static boolean p45; // is the location passable
    static Direction c45; // direction of the current at the location

    static MapLocation l60; // location representing relative coordinate (-3, 1)
    static int dwalk60; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk60; // best direction to take now if the last move was a walk
    static boolean p60; // is the location passable
    static Direction c60; // direction of the current at the location

    static MapLocation l75; // location representing relative coordinate (-3, 2)
    static int dwalk75; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk75; // best direction to take now if the last move was a walk
    static boolean p75; // is the location passable
    static Direction c75; // direction of the current at the location

    static MapLocation l90; // location representing relative coordinate (-3, 3)
    static int dwalk90; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk90; // best direction to take now if the last move was a walk
    static boolean p90; // is the location passable
    static Direction c90; // direction of the current at the location

    static MapLocation l1; // location representing relative coordinate (-2, -3)
    static int dwalk1; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk1; // best direction to take now if the last move was a walk
    static boolean p1; // is the location passable
    static Direction c1; // direction of the current at the location

    static MapLocation l16; // location representing relative coordinate (-2, -2)
    static int dwalk16; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk16; // best direction to take now if the last move was a walk
    static boolean p16; // is the location passable
    static Direction c16; // direction of the current at the location

    static MapLocation l31; // location representing relative coordinate (-2, -1)
    static int dwalk31; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk31; // best direction to take now if the last move was a walk
    static boolean p31; // is the location passable
    static Direction c31; // direction of the current at the location

    static MapLocation l46; // location representing relative coordinate (-2, 0)
    static int dwalk46; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk46; // best direction to take now if the last move was a walk
    static boolean p46; // is the location passable
    static Direction c46; // direction of the current at the location

    static MapLocation l61; // location representing relative coordinate (-2, 1)
    static int dwalk61; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk61; // best direction to take now if the last move was a walk
    static boolean p61; // is the location passable
    static Direction c61; // direction of the current at the location

    static MapLocation l76; // location representing relative coordinate (-2, 2)
    static int dwalk76; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk76; // best direction to take now if the last move was a walk
    static boolean p76; // is the location passable
    static Direction c76; // direction of the current at the location

    static MapLocation l91; // location representing relative coordinate (-2, 3)
    static int dwalk91; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk91; // best direction to take now if the last move was a walk
    static boolean p91; // is the location passable
    static Direction c91; // direction of the current at the location

    static MapLocation l2; // location representing relative coordinate (-1, -3)
    static int dwalk2; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk2; // best direction to take now if the last move was a walk
    static boolean p2; // is the location passable
    static Direction c2; // direction of the current at the location

    static MapLocation l17; // location representing relative coordinate (-1, -2)
    static int dwalk17; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk17; // best direction to take now if the last move was a walk
    static boolean p17; // is the location passable
    static Direction c17; // direction of the current at the location

    static MapLocation l32; // location representing relative coordinate (-1, -1)
    static int dwalk32; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk32; // best direction to take now if the last move was a walk
    static boolean p32; // is the location passable
    static Direction c32; // direction of the current at the location

    static MapLocation l47; // location representing relative coordinate (-1, 0)
    static int dwalk47; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk47; // best direction to take now if the last move was a walk
    static boolean p47; // is the location passable
    static Direction c47; // direction of the current at the location

    static MapLocation l62; // location representing relative coordinate (-1, 1)
    static int dwalk62; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk62; // best direction to take now if the last move was a walk
    static boolean p62; // is the location passable
    static Direction c62; // direction of the current at the location

    static MapLocation l77; // location representing relative coordinate (-1, 2)
    static int dwalk77; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk77; // best direction to take now if the last move was a walk
    static boolean p77; // is the location passable
    static Direction c77; // direction of the current at the location

    static MapLocation l92; // location representing relative coordinate (-1, 3)
    static int dwalk92; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk92; // best direction to take now if the last move was a walk
    static boolean p92; // is the location passable
    static Direction c92; // direction of the current at the location

    static MapLocation l3; // location representing relative coordinate (0, -3)
    static int dwalk3; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk3; // best direction to take now if the last move was a walk
    static boolean p3; // is the location passable
    static Direction c3; // direction of the current at the location

    static MapLocation l18; // location representing relative coordinate (0, -2)
    static int dwalk18; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk18; // best direction to take now if the last move was a walk
    static boolean p18; // is the location passable
    static Direction c18; // direction of the current at the location

    static MapLocation l33; // location representing relative coordinate (0, -1)
    static int dwalk33; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk33; // best direction to take now if the last move was a walk
    static boolean p33; // is the location passable
    static Direction c33; // direction of the current at the location

    static MapLocation l48; // location representing relative coordinate (0, 0)
    static int dwalk48; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk48; // best direction to take now if the last move was a walk
    static boolean p48; // is the location passable
    static Direction c48; // direction of the current at the location

    static MapLocation l63; // location representing relative coordinate (0, 1)
    static int dwalk63; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk63; // best direction to take now if the last move was a walk
    static boolean p63; // is the location passable
    static Direction c63; // direction of the current at the location

    static MapLocation l78; // location representing relative coordinate (0, 2)
    static int dwalk78; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk78; // best direction to take now if the last move was a walk
    static boolean p78; // is the location passable
    static Direction c78; // direction of the current at the location

    static MapLocation l93; // location representing relative coordinate (0, 3)
    static int dwalk93; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk93; // best direction to take now if the last move was a walk
    static boolean p93; // is the location passable
    static Direction c93; // direction of the current at the location

    static MapLocation l4; // location representing relative coordinate (1, -3)
    static int dwalk4; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk4; // best direction to take now if the last move was a walk
    static boolean p4; // is the location passable
    static Direction c4; // direction of the current at the location

    static MapLocation l19; // location representing relative coordinate (1, -2)
    static int dwalk19; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk19; // best direction to take now if the last move was a walk
    static boolean p19; // is the location passable
    static Direction c19; // direction of the current at the location

    static MapLocation l34; // location representing relative coordinate (1, -1)
    static int dwalk34; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk34; // best direction to take now if the last move was a walk
    static boolean p34; // is the location passable
    static Direction c34; // direction of the current at the location

    static MapLocation l49; // location representing relative coordinate (1, 0)
    static int dwalk49; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk49; // best direction to take now if the last move was a walk
    static boolean p49; // is the location passable
    static Direction c49; // direction of the current at the location

    static MapLocation l64; // location representing relative coordinate (1, 1)
    static int dwalk64; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk64; // best direction to take now if the last move was a walk
    static boolean p64; // is the location passable
    static Direction c64; // direction of the current at the location

    static MapLocation l79; // location representing relative coordinate (1, 2)
    static int dwalk79; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk79; // best direction to take now if the last move was a walk
    static boolean p79; // is the location passable
    static Direction c79; // direction of the current at the location

    static MapLocation l94; // location representing relative coordinate (1, 3)
    static int dwalk94; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk94; // best direction to take now if the last move was a walk
    static boolean p94; // is the location passable
    static Direction c94; // direction of the current at the location

    static MapLocation l5; // location representing relative coordinate (2, -3)
    static int dwalk5; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk5; // best direction to take now if the last move was a walk
    static boolean p5; // is the location passable
    static Direction c5; // direction of the current at the location

    static MapLocation l20; // location representing relative coordinate (2, -2)
    static int dwalk20; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk20; // best direction to take now if the last move was a walk
    static boolean p20; // is the location passable
    static Direction c20; // direction of the current at the location

    static MapLocation l35; // location representing relative coordinate (2, -1)
    static int dwalk35; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk35; // best direction to take now if the last move was a walk
    static boolean p35; // is the location passable
    static Direction c35; // direction of the current at the location

    static MapLocation l50; // location representing relative coordinate (2, 0)
    static int dwalk50; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk50; // best direction to take now if the last move was a walk
    static boolean p50; // is the location passable
    static Direction c50; // direction of the current at the location

    static MapLocation l65; // location representing relative coordinate (2, 1)
    static int dwalk65; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk65; // best direction to take now if the last move was a walk
    static boolean p65; // is the location passable
    static Direction c65; // direction of the current at the location

    static MapLocation l80; // location representing relative coordinate (2, 2)
    static int dwalk80; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk80; // best direction to take now if the last move was a walk
    static boolean p80; // is the location passable
    static Direction c80; // direction of the current at the location

    static MapLocation l95; // location representing relative coordinate (2, 3)
    static int dwalk95; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk95; // best direction to take now if the last move was a walk
    static boolean p95; // is the location passable
    static Direction c95; // direction of the current at the location

    static MapLocation l6; // location representing relative coordinate (3, -3)
    static int dwalk6; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk6; // best direction to take now if the last move was a walk
    static boolean p6; // is the location passable
    static Direction c6; // direction of the current at the location

    static MapLocation l21; // location representing relative coordinate (3, -2)
    static int dwalk21; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk21; // best direction to take now if the last move was a walk
    static boolean p21; // is the location passable
    static Direction c21; // direction of the current at the location

    static MapLocation l36; // location representing relative coordinate (3, -1)
    static int dwalk36; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk36; // best direction to take now if the last move was a walk
    static boolean p36; // is the location passable
    static Direction c36; // direction of the current at the location

    static MapLocation l51; // location representing relative coordinate (3, 0)
    static int dwalk51; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk51; // best direction to take now if the last move was a walk
    static boolean p51; // is the location passable
    static Direction c51; // direction of the current at the location

    static MapLocation l66; // location representing relative coordinate (3, 1)
    static int dwalk66; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk66; // best direction to take now if the last move was a walk
    static boolean p66; // is the location passable
    static Direction c66; // direction of the current at the location

    static MapLocation l81; // location representing relative coordinate (3, 2)
    static int dwalk81; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk81; // best direction to take now if the last move was a walk
    static boolean p81; // is the location passable
    static Direction c81; // direction of the current at the location

    static MapLocation l96; // location representing relative coordinate (3, 3)
    static int dwalk96; // shortest distance to location from current location if the last move was a walk
    static Direction dirwalk96; // best direction to take now if the last move was a walk
    static boolean p96; // is the location passable
    static Direction c96; // direction of the current at the location


    public BFSPathing(RobotController rc) {
        this.rc = rc;
    }

    public Direction getBestDirection(MapLocation target) throws GameActionException {

        l48 = rc.getLocation();
        dwalk48 = 0;
        dirwalk48 = Direction.CENTER;

        if (l48.isAdjacentTo(target)) {
            return l48.directionTo(target);
        }

        MapInfo mi48 = rc.senseMapInfo(l48);
        p48 = true;
        c48 = mi48.getCurrentDirection();

        initRest();


        if (rc.onTheMap(l47)) { // check (-1, 0)
            if (rc.canSenseLocation(l47)) {
                MapInfo mi47 = rc.senseMapInfo(l47);
                p47 = mi47.isPassable();
                c47 = mi47.getCurrentDirection();
            }
            if (p47) {
                if (!rc.isLocationOccupied(l47)) { 
                    // from (0, 0)
                    if (c48 != Direction.CENTER && l48.add(c48).equals(l47)) {
                        // there was a current at the previous location and the current flows into this location
                        if (dwalk48 + 1 < dwalk47) {
                            dwalk47 = dwalk48 + 1;
                            dirwalk47 = Direction.WEST;
                        }
                    }
                    else { // there was no current at the previous location
                        if (dwalk48 + 2 < dwalk47) {
                            dwalk47 = dwalk48 + 2;
                            dirwalk47 = Direction.WEST;
                        }
                    }
                }
            }
        }

        if (rc.onTheMap(l33)) { // check (0, -1)
            if (rc.canSenseLocation(l33)) {
                MapInfo mi33 = rc.senseMapInfo(l33);
                p33 = mi33.isPassable();
                c33 = mi33.getCurrentDirection();
            }
            if (p33) {
                if (!rc.isLocationOccupied(l33)) { 
                    // from (0, 0)
                    if (c48 != Direction.CENTER && l48.add(c48).equals(l33)) {
                        // there was a current at the previous location and the current flows into this location
                        if (dwalk48 + 1 < dwalk33) {
                            dwalk33 = dwalk48 + 1;
                            dirwalk33 = Direction.SOUTH;
                        }
                    }
                    else { // there was no current at the previous location
                        if (dwalk48 + 2 < dwalk33) {
                            dwalk33 = dwalk48 + 2;
                            dirwalk33 = Direction.SOUTH;
                        }
                    }
                    // from (-1, 0)
                    if (c47 != Direction.CENTER && l47.add(c47).equals(l33)) {
                        // there was a current at the previous location and the current flows into this location
                        if (dwalk47 + 1 < dwalk33) {
                            dwalk33 = dwalk47 + 1;
                            dirwalk33 = dirwalk47;
                        }
                    }
                    else { // there was no current at the previous location
                        if (dwalk47 + 2 < dwalk33) {
                            dwalk33 = dwalk47 + 2;
                            dirwalk33 = dirwalk47;
                        }
                    }
                }
            }
        }

        if (rc.onTheMap(l63)) { // check (0, 1)
            if (rc.canSenseLocation(l63)) {
                MapInfo mi63 = rc.senseMapInfo(l63);
                p63 = mi63.isPassable();
                c63 = mi63.getCurrentDirection();
            }
            if (p63) {
                if (!rc.isLocationOccupied(l63)) { 
                    // from (0, 0)
                    if (c48 != Direction.CENTER && l48.add(c48).equals(l63)) {
                        // there was a current at the previous location and the current flows into this location
                        if (dwalk48 + 1 < dwalk63) {
                            dwalk63 = dwalk48 + 1;
                            dirwalk63 = Direction.NORTH;
                        }
                    }
                    else { // there was no current at the previous location
                        if (dwalk48 + 2 < dwalk63) {
                            dwalk63 = dwalk48 + 2;
                            dirwalk63 = Direction.NORTH;
                        }
                    }
                    // from (-1, 0)
                    if (c47 != Direction.CENTER && l47.add(c47).equals(l63)) {
                        // there was a current at the previous location and the current flows into this location
                        if (dwalk47 + 1 < dwalk63) {
                            dwalk63 = dwalk47 + 1;
                            dirwalk63 = dirwalk47;
                        }
                    }
                    else { // there was no current at the previous location
                        if (dwalk47 + 2 < dwalk63) {
                            dwalk63 = dwalk47 + 2;
                            dirwalk63 = dirwalk47;
                        }
                    }
                }
            }
        }

        if (rc.onTheMap(l49)) { // check (1, 0)
            if (rc.canSenseLocation(l49)) {
                MapInfo mi49 = rc.senseMapInfo(l49);
                p49 = mi49.isPassable();
                c49 = mi49.getCurrentDirection();
            }
            if (p49) {
                if (!rc.isLocationOccupied(l49)) { 
                    // from (0, 0)
                    if (c48 != Direction.CENTER && l48.add(c48).equals(l49)) {
                        // there was a current at the previous location and the current flows into this location
                        if (dwalk48 + 1 < dwalk49) {
                            dwalk49 = dwalk48 + 1;
                            dirwalk49 = Direction.EAST;
                        }
                    }
                    else { // there was no current at the previous location
                        if (dwalk48 + 2 < dwalk49) {
                            dwalk49 = dwalk48 + 2;
                            dirwalk49 = Direction.EAST;
                        }
                    }
                    // from (0, -1)
                    if (c33 != Direction.CENTER && l33.add(c33).equals(l49)) {
                        // there was a current at the previous location and the current flows into this location
                        if (dwalk33 + 1 < dwalk49) {
                            dwalk49 = dwalk33 + 1;
                            dirwalk49 = dirwalk33;
                        }
                    }
                    else { // there was no current at the previous location
                        if (dwalk33 + 2 < dwalk49) {
                            dwalk49 = dwalk33 + 2;
                            dirwalk49 = dirwalk33;
                        }
                    }
                    // from (0, 1)
                    if (c63 != Direction.CENTER && l63.add(c63).equals(l49)) {
                        // there was a current at the previous location and the current flows into this location
                        if (dwalk63 + 1 < dwalk49) {
                            dwalk49 = dwalk63 + 1;
                            dirwalk49 = dirwalk63;
                        }
                    }
                    else { // there was no current at the previous location
                        if (dwalk63 + 2 < dwalk49) {
                            dwalk49 = dwalk63 + 2;
                            dirwalk49 = dirwalk63;
                        }
                    }
                }
            }
        }

        if (rc.onTheMap(l32)) { // check (-1, -1)
            if (rc.canSenseLocation(l32)) {
                MapInfo mi32 = rc.senseMapInfo(l32);
                p32 = mi32.isPassable();
                c32 = mi32.getCurrentDirection();
            }
            if (p32) {
                if (!rc.isLocationOccupied(l32)) { 
                    // from (0, 0)
                    if (c48 != Direction.CENTER && l48.add(c48).equals(l32)) {
                        // there was a current at the previous location and the current flows into this location
                        if (dwalk48 + 1 < dwalk32) {
                            dwalk32 = dwalk48 + 1;
                            dirwalk32 = Direction.SOUTHWEST;
                        }
                    }
                    else { // there was no current at the previous location
                        if (dwalk48 + 2 < dwalk32) {
                            dwalk32 = dwalk48 + 2;
                            dirwalk32 = Direction.SOUTHWEST;
                        }
                    }
                    // from (-1, 0)
                    if (c47 != Direction.CENTER && l47.add(c47).equals(l32)) {
                        // there was a current at the previous location and the current flows into this location
                        if (dwalk47 + 1 < dwalk32) {
                            dwalk32 = dwalk47 + 1;
                            dirwalk32 = dirwalk47;
                        }
                    }
                    else { // there was no current at the previous location
                        if (dwalk47 + 2 < dwalk32) {
                            dwalk32 = dwalk47 + 2;
                            dirwalk32 = dirwalk47;
                        }
                    }
                    // from (0, -1)
                    if (c33 != Direction.CENTER && l33.add(c33).equals(l32)) {
                        // there was a current at the previous location and the current flows into this location
                        if (dwalk33 + 1 < dwalk32) {
                            dwalk32 = dwalk33 + 1;
                            dirwalk32 = dirwalk33;
                        }
                    }
                    else { // there was no current at the previous location
                        if (dwalk33 + 2 < dwalk32) {
                            dwalk32 = dwalk33 + 2;
                            dirwalk32 = dirwalk33;
                        }
                    }
                }
            }
        }

        if (rc.onTheMap(l62)) { // check (-1, 1)
            if (rc.canSenseLocation(l62)) {
                MapInfo mi62 = rc.senseMapInfo(l62);
                p62 = mi62.isPassable();
                c62 = mi62.getCurrentDirection();
            }
            if (p62) {
                if (!rc.isLocationOccupied(l62)) { 
                    // from (0, 0)
                    if (c48 != Direction.CENTER && l48.add(c48).equals(l62)) {
                        // there was a current at the previous location and the current flows into this location
                        if (dwalk48 + 1 < dwalk62) {
                            dwalk62 = dwalk48 + 1;
                            dirwalk62 = Direction.NORTHWEST;
                        }
                    }
                    else { // there was no current at the previous location
                        if (dwalk48 + 2 < dwalk62) {
                            dwalk62 = dwalk48 + 2;
                            dirwalk62 = Direction.NORTHWEST;
                        }
                    }
                    // from (-1, 0)
                    if (c47 != Direction.CENTER && l47.add(c47).equals(l62)) {
                        // there was a current at the previous location and the current flows into this location
                        if (dwalk47 + 1 < dwalk62) {
                            dwalk62 = dwalk47 + 1;
                            dirwalk62 = dirwalk47;
                        }
                    }
                    else { // there was no current at the previous location
                        if (dwalk47 + 2 < dwalk62) {
                            dwalk62 = dwalk47 + 2;
                            dirwalk62 = dirwalk47;
                        }
                    }
                    // from (0, 1)
                    if (c63 != Direction.CENTER && l63.add(c63).equals(l62)) {
                        // there was a current at the previous location and the current flows into this location
                        if (dwalk63 + 1 < dwalk62) {
                            dwalk62 = dwalk63 + 1;
                            dirwalk62 = dirwalk63;
                        }
                    }
                    else { // there was no current at the previous location
                        if (dwalk63 + 2 < dwalk62) {
                            dwalk62 = dwalk63 + 2;
                            dirwalk62 = dirwalk63;
                        }
                    }
                }
            }
        }

        if (rc.onTheMap(l34)) { // check (1, -1)
            if (rc.canSenseLocation(l34)) {
                MapInfo mi34 = rc.senseMapInfo(l34);
                p34 = mi34.isPassable();
                c34 = mi34.getCurrentDirection();
            }
            if (p34) {
                if (!rc.isLocationOccupied(l34)) { 
                    // from (0, 0)
                    if (c48 != Direction.CENTER && l48.add(c48).equals(l34)) {
                        // there was a current at the previous location and the current flows into this location
                        if (dwalk48 + 1 < dwalk34) {
                            dwalk34 = dwalk48 + 1;
                            dirwalk34 = Direction.SOUTHEAST;
                        }
                    }
                    else { // there was no current at the previous location
                        if (dwalk48 + 2 < dwalk34) {
                            dwalk34 = dwalk48 + 2;
                            dirwalk34 = Direction.SOUTHEAST;
                        }
                    }
                    // from (0, -1)
                    if (c33 != Direction.CENTER && l33.add(c33).equals(l34)) {
                        // there was a current at the previous location and the current flows into this location
                        if (dwalk33 + 1 < dwalk34) {
                            dwalk34 = dwalk33 + 1;
                            dirwalk34 = dirwalk33;
                        }
                    }
                    else { // there was no current at the previous location
                        if (dwalk33 + 2 < dwalk34) {
                            dwalk34 = dwalk33 + 2;
                            dirwalk34 = dirwalk33;
                        }
                    }
                    // from (1, 0)
                    if (c49 != Direction.CENTER && l49.add(c49).equals(l34)) {
                        // there was a current at the previous location and the current flows into this location
                        if (dwalk49 + 1 < dwalk34) {
                            dwalk34 = dwalk49 + 1;
                            dirwalk34 = dirwalk49;
                        }
                    }
                    else { // there was no current at the previous location
                        if (dwalk49 + 2 < dwalk34) {
                            dwalk34 = dwalk49 + 2;
                            dirwalk34 = dirwalk49;
                        }
                    }
                }
            }
        }

        if (rc.onTheMap(l64)) { // check (1, 1)
            if (rc.canSenseLocation(l64)) {
                MapInfo mi64 = rc.senseMapInfo(l64);
                p64 = mi64.isPassable();
                c64 = mi64.getCurrentDirection();
            }
            if (p64) {
                if (!rc.isLocationOccupied(l64)) { 
                    // from (0, 0)
                    if (c48 != Direction.CENTER && l48.add(c48).equals(l64)) {
                        // there was a current at the previous location and the current flows into this location
                        if (dwalk48 + 1 < dwalk64) {
                            dwalk64 = dwalk48 + 1;
                            dirwalk64 = Direction.NORTHEAST;
                        }
                    }
                    else { // there was no current at the previous location
                        if (dwalk48 + 2 < dwalk64) {
                            dwalk64 = dwalk48 + 2;
                            dirwalk64 = Direction.NORTHEAST;
                        }
                    }
                    // from (0, 1)
                    if (c63 != Direction.CENTER && l63.add(c63).equals(l64)) {
                        // there was a current at the previous location and the current flows into this location
                        if (dwalk63 + 1 < dwalk64) {
                            dwalk64 = dwalk63 + 1;
                            dirwalk64 = dirwalk63;
                        }
                    }
                    else { // there was no current at the previous location
                        if (dwalk63 + 2 < dwalk64) {
                            dwalk64 = dwalk63 + 2;
                            dirwalk64 = dirwalk63;
                        }
                    }
                    // from (1, 0)
                    if (c49 != Direction.CENTER && l49.add(c49).equals(l64)) {
                        // there was a current at the previous location and the current flows into this location
                        if (dwalk49 + 1 < dwalk64) {
                            dwalk64 = dwalk49 + 1;
                            dirwalk64 = dirwalk49;
                        }
                    }
                    else { // there was no current at the previous location
                        if (dwalk49 + 2 < dwalk64) {
                            dwalk64 = dwalk49 + 2;
                            dirwalk64 = dirwalk49;
                        }
                    }
                }
            }
        }

        if (rc.onTheMap(l46)) { // check (-2, 0)
            if (rc.canSenseLocation(l46)) {
                MapInfo mi46 = rc.senseMapInfo(l46);
                p46 = mi46.isPassable();
                c46 = mi46.getCurrentDirection();
            }
            if (p46) {
                // from (-1, 0)
                if (c47 != Direction.CENTER && l47.add(c47).equals(l46)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk47 + 1 < dwalk46) {
                        dwalk46 = dwalk47 + 1;
                        dirwalk46 = dirwalk47;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk47 + 2 < dwalk46) {
                        dwalk46 = dwalk47 + 2;
                        dirwalk46 = dirwalk47;
                    }
                }
                // from (-1, -1)
                if (c32 != Direction.CENTER && l32.add(c32).equals(l46)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk32 + 1 < dwalk46) {
                        dwalk46 = dwalk32 + 1;
                        dirwalk46 = dirwalk32;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk32 + 2 < dwalk46) {
                        dwalk46 = dwalk32 + 2;
                        dirwalk46 = dirwalk32;
                    }
                }
                // from (-1, 1)
                if (c62 != Direction.CENTER && l62.add(c62).equals(l46)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk62 + 1 < dwalk46) {
                        dwalk46 = dwalk62 + 1;
                        dirwalk46 = dirwalk62;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk62 + 2 < dwalk46) {
                        dwalk46 = dwalk62 + 2;
                        dirwalk46 = dirwalk62;
                    }
                }
            }
        }

        if (rc.onTheMap(l18)) { // check (0, -2)
            if (rc.canSenseLocation(l18)) {
                MapInfo mi18 = rc.senseMapInfo(l18);
                p18 = mi18.isPassable();
                c18 = mi18.getCurrentDirection();
            }
            if (p18) {
                // from (0, -1)
                if (c33 != Direction.CENTER && l33.add(c33).equals(l18)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk33 + 1 < dwalk18) {
                        dwalk18 = dwalk33 + 1;
                        dirwalk18 = dirwalk33;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk33 + 2 < dwalk18) {
                        dwalk18 = dwalk33 + 2;
                        dirwalk18 = dirwalk33;
                    }
                }
                // from (-1, -1)
                if (c32 != Direction.CENTER && l32.add(c32).equals(l18)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk32 + 1 < dwalk18) {
                        dwalk18 = dwalk32 + 1;
                        dirwalk18 = dirwalk32;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk32 + 2 < dwalk18) {
                        dwalk18 = dwalk32 + 2;
                        dirwalk18 = dirwalk32;
                    }
                }
                // from (1, -1)
                if (c34 != Direction.CENTER && l34.add(c34).equals(l18)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk34 + 1 < dwalk18) {
                        dwalk18 = dwalk34 + 1;
                        dirwalk18 = dirwalk34;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk34 + 2 < dwalk18) {
                        dwalk18 = dwalk34 + 2;
                        dirwalk18 = dirwalk34;
                    }
                }
            }
        }

        if (rc.onTheMap(l78)) { // check (0, 2)
            if (rc.canSenseLocation(l78)) {
                MapInfo mi78 = rc.senseMapInfo(l78);
                p78 = mi78.isPassable();
                c78 = mi78.getCurrentDirection();
            }
            if (p78) {
                // from (0, 1)
                if (c63 != Direction.CENTER && l63.add(c63).equals(l78)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk63 + 1 < dwalk78) {
                        dwalk78 = dwalk63 + 1;
                        dirwalk78 = dirwalk63;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk63 + 2 < dwalk78) {
                        dwalk78 = dwalk63 + 2;
                        dirwalk78 = dirwalk63;
                    }
                }
                // from (-1, 1)
                if (c62 != Direction.CENTER && l62.add(c62).equals(l78)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk62 + 1 < dwalk78) {
                        dwalk78 = dwalk62 + 1;
                        dirwalk78 = dirwalk62;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk62 + 2 < dwalk78) {
                        dwalk78 = dwalk62 + 2;
                        dirwalk78 = dirwalk62;
                    }
                }
                // from (1, 1)
                if (c64 != Direction.CENTER && l64.add(c64).equals(l78)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk64 + 1 < dwalk78) {
                        dwalk78 = dwalk64 + 1;
                        dirwalk78 = dirwalk64;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk64 + 2 < dwalk78) {
                        dwalk78 = dwalk64 + 2;
                        dirwalk78 = dirwalk64;
                    }
                }
            }
        }

        if (rc.onTheMap(l50)) { // check (2, 0)
            if (rc.canSenseLocation(l50)) {
                MapInfo mi50 = rc.senseMapInfo(l50);
                p50 = mi50.isPassable();
                c50 = mi50.getCurrentDirection();
            }
            if (p50) {
                // from (1, 0)
                if (c49 != Direction.CENTER && l49.add(c49).equals(l50)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk49 + 1 < dwalk50) {
                        dwalk50 = dwalk49 + 1;
                        dirwalk50 = dirwalk49;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk49 + 2 < dwalk50) {
                        dwalk50 = dwalk49 + 2;
                        dirwalk50 = dirwalk49;
                    }
                }
                // from (1, -1)
                if (c34 != Direction.CENTER && l34.add(c34).equals(l50)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk34 + 1 < dwalk50) {
                        dwalk50 = dwalk34 + 1;
                        dirwalk50 = dirwalk34;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk34 + 2 < dwalk50) {
                        dwalk50 = dwalk34 + 2;
                        dirwalk50 = dirwalk34;
                    }
                }
                // from (1, 1)
                if (c64 != Direction.CENTER && l64.add(c64).equals(l50)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk64 + 1 < dwalk50) {
                        dwalk50 = dwalk64 + 1;
                        dirwalk50 = dirwalk64;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk64 + 2 < dwalk50) {
                        dwalk50 = dwalk64 + 2;
                        dirwalk50 = dirwalk64;
                    }
                }
            }
        }

        if (rc.onTheMap(l31)) { // check (-2, -1)
            if (rc.canSenseLocation(l31)) {
                MapInfo mi31 = rc.senseMapInfo(l31);
                p31 = mi31.isPassable();
                c31 = mi31.getCurrentDirection();
            }
            if (p31) {
                // from (-1, 0)
                if (c47 != Direction.CENTER && l47.add(c47).equals(l31)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk47 + 1 < dwalk31) {
                        dwalk31 = dwalk47 + 1;
                        dirwalk31 = dirwalk47;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk47 + 2 < dwalk31) {
                        dwalk31 = dwalk47 + 2;
                        dirwalk31 = dirwalk47;
                    }
                }
                // from (-1, -1)
                if (c32 != Direction.CENTER && l32.add(c32).equals(l31)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk32 + 1 < dwalk31) {
                        dwalk31 = dwalk32 + 1;
                        dirwalk31 = dirwalk32;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk32 + 2 < dwalk31) {
                        dwalk31 = dwalk32 + 2;
                        dirwalk31 = dirwalk32;
                    }
                }
                // from (-2, 0)
                if (c46 != Direction.CENTER && l46.add(c46).equals(l31)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk46 + 1 < dwalk31) {
                        dwalk31 = dwalk46 + 1;
                        dirwalk31 = dirwalk46;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk46 + 2 < dwalk31) {
                        dwalk31 = dwalk46 + 2;
                        dirwalk31 = dirwalk46;
                    }
                }
            }
        }

        if (rc.onTheMap(l61)) { // check (-2, 1)
            if (rc.canSenseLocation(l61)) {
                MapInfo mi61 = rc.senseMapInfo(l61);
                p61 = mi61.isPassable();
                c61 = mi61.getCurrentDirection();
            }
            if (p61) {
                // from (-1, 0)
                if (c47 != Direction.CENTER && l47.add(c47).equals(l61)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk47 + 1 < dwalk61) {
                        dwalk61 = dwalk47 + 1;
                        dirwalk61 = dirwalk47;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk47 + 2 < dwalk61) {
                        dwalk61 = dwalk47 + 2;
                        dirwalk61 = dirwalk47;
                    }
                }
                // from (-1, 1)
                if (c62 != Direction.CENTER && l62.add(c62).equals(l61)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk62 + 1 < dwalk61) {
                        dwalk61 = dwalk62 + 1;
                        dirwalk61 = dirwalk62;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk62 + 2 < dwalk61) {
                        dwalk61 = dwalk62 + 2;
                        dirwalk61 = dirwalk62;
                    }
                }
                // from (-2, 0)
                if (c46 != Direction.CENTER && l46.add(c46).equals(l61)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk46 + 1 < dwalk61) {
                        dwalk61 = dwalk46 + 1;
                        dirwalk61 = dirwalk46;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk46 + 2 < dwalk61) {
                        dwalk61 = dwalk46 + 2;
                        dirwalk61 = dirwalk46;
                    }
                }
            }
        }

        if (rc.onTheMap(l17)) { // check (-1, -2)
            if (rc.canSenseLocation(l17)) {
                MapInfo mi17 = rc.senseMapInfo(l17);
                p17 = mi17.isPassable();
                c17 = mi17.getCurrentDirection();
            }
            if (p17) {
                // from (0, -1)
                if (c33 != Direction.CENTER && l33.add(c33).equals(l17)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk33 + 1 < dwalk17) {
                        dwalk17 = dwalk33 + 1;
                        dirwalk17 = dirwalk33;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk33 + 2 < dwalk17) {
                        dwalk17 = dwalk33 + 2;
                        dirwalk17 = dirwalk33;
                    }
                }
                // from (-1, -1)
                if (c32 != Direction.CENTER && l32.add(c32).equals(l17)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk32 + 1 < dwalk17) {
                        dwalk17 = dwalk32 + 1;
                        dirwalk17 = dirwalk32;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk32 + 2 < dwalk17) {
                        dwalk17 = dwalk32 + 2;
                        dirwalk17 = dirwalk32;
                    }
                }
                // from (0, -2)
                if (c18 != Direction.CENTER && l18.add(c18).equals(l17)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk18 + 1 < dwalk17) {
                        dwalk17 = dwalk18 + 1;
                        dirwalk17 = dirwalk18;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk18 + 2 < dwalk17) {
                        dwalk17 = dwalk18 + 2;
                        dirwalk17 = dirwalk18;
                    }
                }
                // from (-2, -1)
                if (c31 != Direction.CENTER && l31.add(c31).equals(l17)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk31 + 1 < dwalk17) {
                        dwalk17 = dwalk31 + 1;
                        dirwalk17 = dirwalk31;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk31 + 2 < dwalk17) {
                        dwalk17 = dwalk31 + 2;
                        dirwalk17 = dirwalk31;
                    }
                }
            }
        }

        if (rc.onTheMap(l77)) { // check (-1, 2)
            if (rc.canSenseLocation(l77)) {
                MapInfo mi77 = rc.senseMapInfo(l77);
                p77 = mi77.isPassable();
                c77 = mi77.getCurrentDirection();
            }
            if (p77) {
                // from (0, 1)
                if (c63 != Direction.CENTER && l63.add(c63).equals(l77)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk63 + 1 < dwalk77) {
                        dwalk77 = dwalk63 + 1;
                        dirwalk77 = dirwalk63;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk63 + 2 < dwalk77) {
                        dwalk77 = dwalk63 + 2;
                        dirwalk77 = dirwalk63;
                    }
                }
                // from (-1, 1)
                if (c62 != Direction.CENTER && l62.add(c62).equals(l77)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk62 + 1 < dwalk77) {
                        dwalk77 = dwalk62 + 1;
                        dirwalk77 = dirwalk62;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk62 + 2 < dwalk77) {
                        dwalk77 = dwalk62 + 2;
                        dirwalk77 = dirwalk62;
                    }
                }
                // from (0, 2)
                if (c78 != Direction.CENTER && l78.add(c78).equals(l77)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk78 + 1 < dwalk77) {
                        dwalk77 = dwalk78 + 1;
                        dirwalk77 = dirwalk78;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk78 + 2 < dwalk77) {
                        dwalk77 = dwalk78 + 2;
                        dirwalk77 = dirwalk78;
                    }
                }
                // from (-2, 1)
                if (c61 != Direction.CENTER && l61.add(c61).equals(l77)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk61 + 1 < dwalk77) {
                        dwalk77 = dwalk61 + 1;
                        dirwalk77 = dirwalk61;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk61 + 2 < dwalk77) {
                        dwalk77 = dwalk61 + 2;
                        dirwalk77 = dirwalk61;
                    }
                }
            }
        }

        if (rc.onTheMap(l19)) { // check (1, -2)
            if (rc.canSenseLocation(l19)) {
                MapInfo mi19 = rc.senseMapInfo(l19);
                p19 = mi19.isPassable();
                c19 = mi19.getCurrentDirection();
            }
            if (p19) {
                // from (0, -1)
                if (c33 != Direction.CENTER && l33.add(c33).equals(l19)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk33 + 1 < dwalk19) {
                        dwalk19 = dwalk33 + 1;
                        dirwalk19 = dirwalk33;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk33 + 2 < dwalk19) {
                        dwalk19 = dwalk33 + 2;
                        dirwalk19 = dirwalk33;
                    }
                }
                // from (1, -1)
                if (c34 != Direction.CENTER && l34.add(c34).equals(l19)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk34 + 1 < dwalk19) {
                        dwalk19 = dwalk34 + 1;
                        dirwalk19 = dirwalk34;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk34 + 2 < dwalk19) {
                        dwalk19 = dwalk34 + 2;
                        dirwalk19 = dirwalk34;
                    }
                }
                // from (0, -2)
                if (c18 != Direction.CENTER && l18.add(c18).equals(l19)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk18 + 1 < dwalk19) {
                        dwalk19 = dwalk18 + 1;
                        dirwalk19 = dirwalk18;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk18 + 2 < dwalk19) {
                        dwalk19 = dwalk18 + 2;
                        dirwalk19 = dirwalk18;
                    }
                }
            }
        }

        if (rc.onTheMap(l79)) { // check (1, 2)
            if (rc.canSenseLocation(l79)) {
                MapInfo mi79 = rc.senseMapInfo(l79);
                p79 = mi79.isPassable();
                c79 = mi79.getCurrentDirection();
            }
            if (p79) {
                // from (0, 1)
                if (c63 != Direction.CENTER && l63.add(c63).equals(l79)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk63 + 1 < dwalk79) {
                        dwalk79 = dwalk63 + 1;
                        dirwalk79 = dirwalk63;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk63 + 2 < dwalk79) {
                        dwalk79 = dwalk63 + 2;
                        dirwalk79 = dirwalk63;
                    }
                }
                // from (1, 1)
                if (c64 != Direction.CENTER && l64.add(c64).equals(l79)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk64 + 1 < dwalk79) {
                        dwalk79 = dwalk64 + 1;
                        dirwalk79 = dirwalk64;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk64 + 2 < dwalk79) {
                        dwalk79 = dwalk64 + 2;
                        dirwalk79 = dirwalk64;
                    }
                }
                // from (0, 2)
                if (c78 != Direction.CENTER && l78.add(c78).equals(l79)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk78 + 1 < dwalk79) {
                        dwalk79 = dwalk78 + 1;
                        dirwalk79 = dirwalk78;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk78 + 2 < dwalk79) {
                        dwalk79 = dwalk78 + 2;
                        dirwalk79 = dirwalk78;
                    }
                }
            }
        }

        if (rc.onTheMap(l35)) { // check (2, -1)
            if (rc.canSenseLocation(l35)) {
                MapInfo mi35 = rc.senseMapInfo(l35);
                p35 = mi35.isPassable();
                c35 = mi35.getCurrentDirection();
            }
            if (p35) {
                // from (1, 0)
                if (c49 != Direction.CENTER && l49.add(c49).equals(l35)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk49 + 1 < dwalk35) {
                        dwalk35 = dwalk49 + 1;
                        dirwalk35 = dirwalk49;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk49 + 2 < dwalk35) {
                        dwalk35 = dwalk49 + 2;
                        dirwalk35 = dirwalk49;
                    }
                }
                // from (1, -1)
                if (c34 != Direction.CENTER && l34.add(c34).equals(l35)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk34 + 1 < dwalk35) {
                        dwalk35 = dwalk34 + 1;
                        dirwalk35 = dirwalk34;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk34 + 2 < dwalk35) {
                        dwalk35 = dwalk34 + 2;
                        dirwalk35 = dirwalk34;
                    }
                }
                // from (2, 0)
                if (c50 != Direction.CENTER && l50.add(c50).equals(l35)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk50 + 1 < dwalk35) {
                        dwalk35 = dwalk50 + 1;
                        dirwalk35 = dirwalk50;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk50 + 2 < dwalk35) {
                        dwalk35 = dwalk50 + 2;
                        dirwalk35 = dirwalk50;
                    }
                }
                // from (1, -2)
                if (c19 != Direction.CENTER && l19.add(c19).equals(l35)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk19 + 1 < dwalk35) {
                        dwalk35 = dwalk19 + 1;
                        dirwalk35 = dirwalk19;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk19 + 2 < dwalk35) {
                        dwalk35 = dwalk19 + 2;
                        dirwalk35 = dirwalk19;
                    }
                }
            }
        }

        if (rc.onTheMap(l65)) { // check (2, 1)
            if (rc.canSenseLocation(l65)) {
                MapInfo mi65 = rc.senseMapInfo(l65);
                p65 = mi65.isPassable();
                c65 = mi65.getCurrentDirection();
            }
            if (p65) {
                // from (1, 0)
                if (c49 != Direction.CENTER && l49.add(c49).equals(l65)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk49 + 1 < dwalk65) {
                        dwalk65 = dwalk49 + 1;
                        dirwalk65 = dirwalk49;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk49 + 2 < dwalk65) {
                        dwalk65 = dwalk49 + 2;
                        dirwalk65 = dirwalk49;
                    }
                }
                // from (1, 1)
                if (c64 != Direction.CENTER && l64.add(c64).equals(l65)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk64 + 1 < dwalk65) {
                        dwalk65 = dwalk64 + 1;
                        dirwalk65 = dirwalk64;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk64 + 2 < dwalk65) {
                        dwalk65 = dwalk64 + 2;
                        dirwalk65 = dirwalk64;
                    }
                }
                // from (2, 0)
                if (c50 != Direction.CENTER && l50.add(c50).equals(l65)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk50 + 1 < dwalk65) {
                        dwalk65 = dwalk50 + 1;
                        dirwalk65 = dirwalk50;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk50 + 2 < dwalk65) {
                        dwalk65 = dwalk50 + 2;
                        dirwalk65 = dirwalk50;
                    }
                }
                // from (1, 2)
                if (c79 != Direction.CENTER && l79.add(c79).equals(l65)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk79 + 1 < dwalk65) {
                        dwalk65 = dwalk79 + 1;
                        dirwalk65 = dirwalk79;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk79 + 2 < dwalk65) {
                        dwalk65 = dwalk79 + 2;
                        dirwalk65 = dirwalk79;
                    }
                }
            }
        }

        if (rc.onTheMap(l16)) { // check (-2, -2)
            if (rc.canSenseLocation(l16)) {
                MapInfo mi16 = rc.senseMapInfo(l16);
                p16 = mi16.isPassable();
                c16 = mi16.getCurrentDirection();
            }
            if (p16) {
                // from (-1, -1)
                if (c32 != Direction.CENTER && l32.add(c32).equals(l16)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk32 + 1 < dwalk16) {
                        dwalk16 = dwalk32 + 1;
                        dirwalk16 = dirwalk32;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk32 + 2 < dwalk16) {
                        dwalk16 = dwalk32 + 2;
                        dirwalk16 = dirwalk32;
                    }
                }
                // from (-2, -1)
                if (c31 != Direction.CENTER && l31.add(c31).equals(l16)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk31 + 1 < dwalk16) {
                        dwalk16 = dwalk31 + 1;
                        dirwalk16 = dirwalk31;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk31 + 2 < dwalk16) {
                        dwalk16 = dwalk31 + 2;
                        dirwalk16 = dirwalk31;
                    }
                }
                // from (-1, -2)
                if (c17 != Direction.CENTER && l17.add(c17).equals(l16)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk17 + 1 < dwalk16) {
                        dwalk16 = dwalk17 + 1;
                        dirwalk16 = dirwalk17;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk17 + 2 < dwalk16) {
                        dwalk16 = dwalk17 + 2;
                        dirwalk16 = dirwalk17;
                    }
                }
            }
        }

        if (rc.onTheMap(l76)) { // check (-2, 2)
            if (rc.canSenseLocation(l76)) {
                MapInfo mi76 = rc.senseMapInfo(l76);
                p76 = mi76.isPassable();
                c76 = mi76.getCurrentDirection();
            }
            if (p76) {
                // from (-1, 1)
                if (c62 != Direction.CENTER && l62.add(c62).equals(l76)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk62 + 1 < dwalk76) {
                        dwalk76 = dwalk62 + 1;
                        dirwalk76 = dirwalk62;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk62 + 2 < dwalk76) {
                        dwalk76 = dwalk62 + 2;
                        dirwalk76 = dirwalk62;
                    }
                }
                // from (-2, 1)
                if (c61 != Direction.CENTER && l61.add(c61).equals(l76)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk61 + 1 < dwalk76) {
                        dwalk76 = dwalk61 + 1;
                        dirwalk76 = dirwalk61;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk61 + 2 < dwalk76) {
                        dwalk76 = dwalk61 + 2;
                        dirwalk76 = dirwalk61;
                    }
                }
                // from (-1, 2)
                if (c77 != Direction.CENTER && l77.add(c77).equals(l76)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk77 + 1 < dwalk76) {
                        dwalk76 = dwalk77 + 1;
                        dirwalk76 = dirwalk77;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk77 + 2 < dwalk76) {
                        dwalk76 = dwalk77 + 2;
                        dirwalk76 = dirwalk77;
                    }
                }
            }
        }

        if (rc.onTheMap(l20)) { // check (2, -2)
            if (rc.canSenseLocation(l20)) {
                MapInfo mi20 = rc.senseMapInfo(l20);
                p20 = mi20.isPassable();
                c20 = mi20.getCurrentDirection();
            }
            if (p20) {
                // from (1, -1)
                if (c34 != Direction.CENTER && l34.add(c34).equals(l20)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk34 + 1 < dwalk20) {
                        dwalk20 = dwalk34 + 1;
                        dirwalk20 = dirwalk34;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk34 + 2 < dwalk20) {
                        dwalk20 = dwalk34 + 2;
                        dirwalk20 = dirwalk34;
                    }
                }
                // from (1, -2)
                if (c19 != Direction.CENTER && l19.add(c19).equals(l20)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk19 + 1 < dwalk20) {
                        dwalk20 = dwalk19 + 1;
                        dirwalk20 = dirwalk19;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk19 + 2 < dwalk20) {
                        dwalk20 = dwalk19 + 2;
                        dirwalk20 = dirwalk19;
                    }
                }
                // from (2, -1)
                if (c35 != Direction.CENTER && l35.add(c35).equals(l20)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk35 + 1 < dwalk20) {
                        dwalk20 = dwalk35 + 1;
                        dirwalk20 = dirwalk35;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk35 + 2 < dwalk20) {
                        dwalk20 = dwalk35 + 2;
                        dirwalk20 = dirwalk35;
                    }
                }
            }
        }

        if (rc.onTheMap(l80)) { // check (2, 2)
            if (rc.canSenseLocation(l80)) {
                MapInfo mi80 = rc.senseMapInfo(l80);
                p80 = mi80.isPassable();
                c80 = mi80.getCurrentDirection();
            }
            if (p80) {
                // from (1, 1)
                if (c64 != Direction.CENTER && l64.add(c64).equals(l80)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk64 + 1 < dwalk80) {
                        dwalk80 = dwalk64 + 1;
                        dirwalk80 = dirwalk64;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk64 + 2 < dwalk80) {
                        dwalk80 = dwalk64 + 2;
                        dirwalk80 = dirwalk64;
                    }
                }
                // from (1, 2)
                if (c79 != Direction.CENTER && l79.add(c79).equals(l80)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk79 + 1 < dwalk80) {
                        dwalk80 = dwalk79 + 1;
                        dirwalk80 = dirwalk79;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk79 + 2 < dwalk80) {
                        dwalk80 = dwalk79 + 2;
                        dirwalk80 = dirwalk79;
                    }
                }
                // from (2, 1)
                if (c65 != Direction.CENTER && l65.add(c65).equals(l80)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk65 + 1 < dwalk80) {
                        dwalk80 = dwalk65 + 1;
                        dirwalk80 = dirwalk65;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk65 + 2 < dwalk80) {
                        dwalk80 = dwalk65 + 2;
                        dirwalk80 = dirwalk65;
                    }
                }
            }
        }

        if (rc.onTheMap(l45)) { // check (-3, 0)
            if (rc.canSenseLocation(l45)) {
                MapInfo mi45 = rc.senseMapInfo(l45);
                p45 = mi45.isPassable();
                c45 = mi45.getCurrentDirection();
            }
            if (p45) {
                // from (-2, 0)
                if (c46 != Direction.CENTER && l46.add(c46).equals(l45)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk46 + 1 < dwalk45) {
                        dwalk45 = dwalk46 + 1;
                        dirwalk45 = dirwalk46;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk46 + 2 < dwalk45) {
                        dwalk45 = dwalk46 + 2;
                        dirwalk45 = dirwalk46;
                    }
                }
                // from (-2, -1)
                if (c31 != Direction.CENTER && l31.add(c31).equals(l45)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk31 + 1 < dwalk45) {
                        dwalk45 = dwalk31 + 1;
                        dirwalk45 = dirwalk31;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk31 + 2 < dwalk45) {
                        dwalk45 = dwalk31 + 2;
                        dirwalk45 = dirwalk31;
                    }
                }
                // from (-2, 1)
                if (c61 != Direction.CENTER && l61.add(c61).equals(l45)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk61 + 1 < dwalk45) {
                        dwalk45 = dwalk61 + 1;
                        dirwalk45 = dirwalk61;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk61 + 2 < dwalk45) {
                        dwalk45 = dwalk61 + 2;
                        dirwalk45 = dirwalk61;
                    }
                }
            }
        }

        if (rc.onTheMap(l3)) { // check (0, -3)
            if (rc.canSenseLocation(l3)) {
                MapInfo mi3 = rc.senseMapInfo(l3);
                p3 = mi3.isPassable();
                c3 = mi3.getCurrentDirection();
            }
            if (p3) {
                // from (0, -2)
                if (c18 != Direction.CENTER && l18.add(c18).equals(l3)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk18 + 1 < dwalk3) {
                        dwalk3 = dwalk18 + 1;
                        dirwalk3 = dirwalk18;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk18 + 2 < dwalk3) {
                        dwalk3 = dwalk18 + 2;
                        dirwalk3 = dirwalk18;
                    }
                }
                // from (-1, -2)
                if (c17 != Direction.CENTER && l17.add(c17).equals(l3)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk17 + 1 < dwalk3) {
                        dwalk3 = dwalk17 + 1;
                        dirwalk3 = dirwalk17;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk17 + 2 < dwalk3) {
                        dwalk3 = dwalk17 + 2;
                        dirwalk3 = dirwalk17;
                    }
                }
                // from (1, -2)
                if (c19 != Direction.CENTER && l19.add(c19).equals(l3)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk19 + 1 < dwalk3) {
                        dwalk3 = dwalk19 + 1;
                        dirwalk3 = dirwalk19;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk19 + 2 < dwalk3) {
                        dwalk3 = dwalk19 + 2;
                        dirwalk3 = dirwalk19;
                    }
                }
            }
        }

        if (rc.onTheMap(l93)) { // check (0, 3)
            if (rc.canSenseLocation(l93)) {
                MapInfo mi93 = rc.senseMapInfo(l93);
                p93 = mi93.isPassable();
                c93 = mi93.getCurrentDirection();
            }
            if (p93) {
                // from (0, 2)
                if (c78 != Direction.CENTER && l78.add(c78).equals(l93)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk78 + 1 < dwalk93) {
                        dwalk93 = dwalk78 + 1;
                        dirwalk93 = dirwalk78;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk78 + 2 < dwalk93) {
                        dwalk93 = dwalk78 + 2;
                        dirwalk93 = dirwalk78;
                    }
                }
                // from (-1, 2)
                if (c77 != Direction.CENTER && l77.add(c77).equals(l93)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk77 + 1 < dwalk93) {
                        dwalk93 = dwalk77 + 1;
                        dirwalk93 = dirwalk77;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk77 + 2 < dwalk93) {
                        dwalk93 = dwalk77 + 2;
                        dirwalk93 = dirwalk77;
                    }
                }
                // from (1, 2)
                if (c79 != Direction.CENTER && l79.add(c79).equals(l93)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk79 + 1 < dwalk93) {
                        dwalk93 = dwalk79 + 1;
                        dirwalk93 = dirwalk79;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk79 + 2 < dwalk93) {
                        dwalk93 = dwalk79 + 2;
                        dirwalk93 = dirwalk79;
                    }
                }
            }
        }

        if (rc.onTheMap(l51)) { // check (3, 0)
            if (rc.canSenseLocation(l51)) {
                MapInfo mi51 = rc.senseMapInfo(l51);
                p51 = mi51.isPassable();
                c51 = mi51.getCurrentDirection();
            }
            if (p51) {
                // from (2, 0)
                if (c50 != Direction.CENTER && l50.add(c50).equals(l51)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk50 + 1 < dwalk51) {
                        dwalk51 = dwalk50 + 1;
                        dirwalk51 = dirwalk50;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk50 + 2 < dwalk51) {
                        dwalk51 = dwalk50 + 2;
                        dirwalk51 = dirwalk50;
                    }
                }
                // from (2, -1)
                if (c35 != Direction.CENTER && l35.add(c35).equals(l51)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk35 + 1 < dwalk51) {
                        dwalk51 = dwalk35 + 1;
                        dirwalk51 = dirwalk35;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk35 + 2 < dwalk51) {
                        dwalk51 = dwalk35 + 2;
                        dirwalk51 = dirwalk35;
                    }
                }
                // from (2, 1)
                if (c65 != Direction.CENTER && l65.add(c65).equals(l51)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk65 + 1 < dwalk51) {
                        dwalk51 = dwalk65 + 1;
                        dirwalk51 = dirwalk65;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk65 + 2 < dwalk51) {
                        dwalk51 = dwalk65 + 2;
                        dirwalk51 = dirwalk65;
                    }
                }
            }
        }

        if (rc.onTheMap(l30)) { // check (-3, -1)
            if (rc.canSenseLocation(l30)) {
                MapInfo mi30 = rc.senseMapInfo(l30);
                p30 = mi30.isPassable();
                c30 = mi30.getCurrentDirection();
            }
            if (p30) {
                // from (-2, 0)
                if (c46 != Direction.CENTER && l46.add(c46).equals(l30)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk46 + 1 < dwalk30) {
                        dwalk30 = dwalk46 + 1;
                        dirwalk30 = dirwalk46;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk46 + 2 < dwalk30) {
                        dwalk30 = dwalk46 + 2;
                        dirwalk30 = dirwalk46;
                    }
                }
                // from (-2, -1)
                if (c31 != Direction.CENTER && l31.add(c31).equals(l30)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk31 + 1 < dwalk30) {
                        dwalk30 = dwalk31 + 1;
                        dirwalk30 = dirwalk31;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk31 + 2 < dwalk30) {
                        dwalk30 = dwalk31 + 2;
                        dirwalk30 = dirwalk31;
                    }
                }
                // from (-2, -2)
                if (c16 != Direction.CENTER && l16.add(c16).equals(l30)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk16 + 1 < dwalk30) {
                        dwalk30 = dwalk16 + 1;
                        dirwalk30 = dirwalk16;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk16 + 2 < dwalk30) {
                        dwalk30 = dwalk16 + 2;
                        dirwalk30 = dirwalk16;
                    }
                }
                // from (-3, 0)
                if (c45 != Direction.CENTER && l45.add(c45).equals(l30)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk45 + 1 < dwalk30) {
                        dwalk30 = dwalk45 + 1;
                        dirwalk30 = dirwalk45;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk45 + 2 < dwalk30) {
                        dwalk30 = dwalk45 + 2;
                        dirwalk30 = dirwalk45;
                    }
                }
            }
        }

        if (rc.onTheMap(l60)) { // check (-3, 1)
            if (rc.canSenseLocation(l60)) {
                MapInfo mi60 = rc.senseMapInfo(l60);
                p60 = mi60.isPassable();
                c60 = mi60.getCurrentDirection();
            }
            if (p60) {
                // from (-2, 0)
                if (c46 != Direction.CENTER && l46.add(c46).equals(l60)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk46 + 1 < dwalk60) {
                        dwalk60 = dwalk46 + 1;
                        dirwalk60 = dirwalk46;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk46 + 2 < dwalk60) {
                        dwalk60 = dwalk46 + 2;
                        dirwalk60 = dirwalk46;
                    }
                }
                // from (-2, 1)
                if (c61 != Direction.CENTER && l61.add(c61).equals(l60)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk61 + 1 < dwalk60) {
                        dwalk60 = dwalk61 + 1;
                        dirwalk60 = dirwalk61;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk61 + 2 < dwalk60) {
                        dwalk60 = dwalk61 + 2;
                        dirwalk60 = dirwalk61;
                    }
                }
                // from (-2, 2)
                if (c76 != Direction.CENTER && l76.add(c76).equals(l60)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk76 + 1 < dwalk60) {
                        dwalk60 = dwalk76 + 1;
                        dirwalk60 = dirwalk76;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk76 + 2 < dwalk60) {
                        dwalk60 = dwalk76 + 2;
                        dirwalk60 = dirwalk76;
                    }
                }
                // from (-3, 0)
                if (c45 != Direction.CENTER && l45.add(c45).equals(l60)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk45 + 1 < dwalk60) {
                        dwalk60 = dwalk45 + 1;
                        dirwalk60 = dirwalk45;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk45 + 2 < dwalk60) {
                        dwalk60 = dwalk45 + 2;
                        dirwalk60 = dirwalk45;
                    }
                }
            }
        }

        if (rc.onTheMap(l2)) { // check (-1, -3)
            if (rc.canSenseLocation(l2)) {
                MapInfo mi2 = rc.senseMapInfo(l2);
                p2 = mi2.isPassable();
                c2 = mi2.getCurrentDirection();
            }
            if (p2) {
                // from (0, -2)
                if (c18 != Direction.CENTER && l18.add(c18).equals(l2)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk18 + 1 < dwalk2) {
                        dwalk2 = dwalk18 + 1;
                        dirwalk2 = dirwalk18;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk18 + 2 < dwalk2) {
                        dwalk2 = dwalk18 + 2;
                        dirwalk2 = dirwalk18;
                    }
                }
                // from (-1, -2)
                if (c17 != Direction.CENTER && l17.add(c17).equals(l2)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk17 + 1 < dwalk2) {
                        dwalk2 = dwalk17 + 1;
                        dirwalk2 = dirwalk17;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk17 + 2 < dwalk2) {
                        dwalk2 = dwalk17 + 2;
                        dirwalk2 = dirwalk17;
                    }
                }
                // from (-2, -2)
                if (c16 != Direction.CENTER && l16.add(c16).equals(l2)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk16 + 1 < dwalk2) {
                        dwalk2 = dwalk16 + 1;
                        dirwalk2 = dirwalk16;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk16 + 2 < dwalk2) {
                        dwalk2 = dwalk16 + 2;
                        dirwalk2 = dirwalk16;
                    }
                }
                // from (0, -3)
                if (c3 != Direction.CENTER && l3.add(c3).equals(l2)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk3 + 1 < dwalk2) {
                        dwalk2 = dwalk3 + 1;
                        dirwalk2 = dirwalk3;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk3 + 2 < dwalk2) {
                        dwalk2 = dwalk3 + 2;
                        dirwalk2 = dirwalk3;
                    }
                }
            }
        }

        if (rc.onTheMap(l92)) { // check (-1, 3)
            if (rc.canSenseLocation(l92)) {
                MapInfo mi92 = rc.senseMapInfo(l92);
                p92 = mi92.isPassable();
                c92 = mi92.getCurrentDirection();
            }
            if (p92) {
                // from (0, 2)
                if (c78 != Direction.CENTER && l78.add(c78).equals(l92)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk78 + 1 < dwalk92) {
                        dwalk92 = dwalk78 + 1;
                        dirwalk92 = dirwalk78;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk78 + 2 < dwalk92) {
                        dwalk92 = dwalk78 + 2;
                        dirwalk92 = dirwalk78;
                    }
                }
                // from (-1, 2)
                if (c77 != Direction.CENTER && l77.add(c77).equals(l92)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk77 + 1 < dwalk92) {
                        dwalk92 = dwalk77 + 1;
                        dirwalk92 = dirwalk77;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk77 + 2 < dwalk92) {
                        dwalk92 = dwalk77 + 2;
                        dirwalk92 = dirwalk77;
                    }
                }
                // from (-2, 2)
                if (c76 != Direction.CENTER && l76.add(c76).equals(l92)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk76 + 1 < dwalk92) {
                        dwalk92 = dwalk76 + 1;
                        dirwalk92 = dirwalk76;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk76 + 2 < dwalk92) {
                        dwalk92 = dwalk76 + 2;
                        dirwalk92 = dirwalk76;
                    }
                }
                // from (0, 3)
                if (c93 != Direction.CENTER && l93.add(c93).equals(l92)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk93 + 1 < dwalk92) {
                        dwalk92 = dwalk93 + 1;
                        dirwalk92 = dirwalk93;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk93 + 2 < dwalk92) {
                        dwalk92 = dwalk93 + 2;
                        dirwalk92 = dirwalk93;
                    }
                }
            }
        }

        if (rc.onTheMap(l4)) { // check (1, -3)
            if (rc.canSenseLocation(l4)) {
                MapInfo mi4 = rc.senseMapInfo(l4);
                p4 = mi4.isPassable();
                c4 = mi4.getCurrentDirection();
            }
            if (p4) {
                // from (0, -2)
                if (c18 != Direction.CENTER && l18.add(c18).equals(l4)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk18 + 1 < dwalk4) {
                        dwalk4 = dwalk18 + 1;
                        dirwalk4 = dirwalk18;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk18 + 2 < dwalk4) {
                        dwalk4 = dwalk18 + 2;
                        dirwalk4 = dirwalk18;
                    }
                }
                // from (1, -2)
                if (c19 != Direction.CENTER && l19.add(c19).equals(l4)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk19 + 1 < dwalk4) {
                        dwalk4 = dwalk19 + 1;
                        dirwalk4 = dirwalk19;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk19 + 2 < dwalk4) {
                        dwalk4 = dwalk19 + 2;
                        dirwalk4 = dirwalk19;
                    }
                }
                // from (2, -2)
                if (c20 != Direction.CENTER && l20.add(c20).equals(l4)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk20 + 1 < dwalk4) {
                        dwalk4 = dwalk20 + 1;
                        dirwalk4 = dirwalk20;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk20 + 2 < dwalk4) {
                        dwalk4 = dwalk20 + 2;
                        dirwalk4 = dirwalk20;
                    }
                }
                // from (0, -3)
                if (c3 != Direction.CENTER && l3.add(c3).equals(l4)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk3 + 1 < dwalk4) {
                        dwalk4 = dwalk3 + 1;
                        dirwalk4 = dirwalk3;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk3 + 2 < dwalk4) {
                        dwalk4 = dwalk3 + 2;
                        dirwalk4 = dirwalk3;
                    }
                }
            }
        }

        if (rc.onTheMap(l94)) { // check (1, 3)
            if (rc.canSenseLocation(l94)) {
                MapInfo mi94 = rc.senseMapInfo(l94);
                p94 = mi94.isPassable();
                c94 = mi94.getCurrentDirection();
            }
            if (p94) {
                // from (0, 2)
                if (c78 != Direction.CENTER && l78.add(c78).equals(l94)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk78 + 1 < dwalk94) {
                        dwalk94 = dwalk78 + 1;
                        dirwalk94 = dirwalk78;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk78 + 2 < dwalk94) {
                        dwalk94 = dwalk78 + 2;
                        dirwalk94 = dirwalk78;
                    }
                }
                // from (1, 2)
                if (c79 != Direction.CENTER && l79.add(c79).equals(l94)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk79 + 1 < dwalk94) {
                        dwalk94 = dwalk79 + 1;
                        dirwalk94 = dirwalk79;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk79 + 2 < dwalk94) {
                        dwalk94 = dwalk79 + 2;
                        dirwalk94 = dirwalk79;
                    }
                }
                // from (2, 2)
                if (c80 != Direction.CENTER && l80.add(c80).equals(l94)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk80 + 1 < dwalk94) {
                        dwalk94 = dwalk80 + 1;
                        dirwalk94 = dirwalk80;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk80 + 2 < dwalk94) {
                        dwalk94 = dwalk80 + 2;
                        dirwalk94 = dirwalk80;
                    }
                }
                // from (0, 3)
                if (c93 != Direction.CENTER && l93.add(c93).equals(l94)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk93 + 1 < dwalk94) {
                        dwalk94 = dwalk93 + 1;
                        dirwalk94 = dirwalk93;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk93 + 2 < dwalk94) {
                        dwalk94 = dwalk93 + 2;
                        dirwalk94 = dirwalk93;
                    }
                }
            }
        }

        if (rc.onTheMap(l36)) { // check (3, -1)
            if (rc.canSenseLocation(l36)) {
                MapInfo mi36 = rc.senseMapInfo(l36);
                p36 = mi36.isPassable();
                c36 = mi36.getCurrentDirection();
            }
            if (p36) {
                // from (2, 0)
                if (c50 != Direction.CENTER && l50.add(c50).equals(l36)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk50 + 1 < dwalk36) {
                        dwalk36 = dwalk50 + 1;
                        dirwalk36 = dirwalk50;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk50 + 2 < dwalk36) {
                        dwalk36 = dwalk50 + 2;
                        dirwalk36 = dirwalk50;
                    }
                }
                // from (2, -1)
                if (c35 != Direction.CENTER && l35.add(c35).equals(l36)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk35 + 1 < dwalk36) {
                        dwalk36 = dwalk35 + 1;
                        dirwalk36 = dirwalk35;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk35 + 2 < dwalk36) {
                        dwalk36 = dwalk35 + 2;
                        dirwalk36 = dirwalk35;
                    }
                }
                // from (2, -2)
                if (c20 != Direction.CENTER && l20.add(c20).equals(l36)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk20 + 1 < dwalk36) {
                        dwalk36 = dwalk20 + 1;
                        dirwalk36 = dirwalk20;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk20 + 2 < dwalk36) {
                        dwalk36 = dwalk20 + 2;
                        dirwalk36 = dirwalk20;
                    }
                }
                // from (3, 0)
                if (c51 != Direction.CENTER && l51.add(c51).equals(l36)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk51 + 1 < dwalk36) {
                        dwalk36 = dwalk51 + 1;
                        dirwalk36 = dirwalk51;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk51 + 2 < dwalk36) {
                        dwalk36 = dwalk51 + 2;
                        dirwalk36 = dirwalk51;
                    }
                }
            }
        }

        if (rc.onTheMap(l66)) { // check (3, 1)
            if (rc.canSenseLocation(l66)) {
                MapInfo mi66 = rc.senseMapInfo(l66);
                p66 = mi66.isPassable();
                c66 = mi66.getCurrentDirection();
            }
            if (p66) {
                // from (2, 0)
                if (c50 != Direction.CENTER && l50.add(c50).equals(l66)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk50 + 1 < dwalk66) {
                        dwalk66 = dwalk50 + 1;
                        dirwalk66 = dirwalk50;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk50 + 2 < dwalk66) {
                        dwalk66 = dwalk50 + 2;
                        dirwalk66 = dirwalk50;
                    }
                }
                // from (2, 1)
                if (c65 != Direction.CENTER && l65.add(c65).equals(l66)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk65 + 1 < dwalk66) {
                        dwalk66 = dwalk65 + 1;
                        dirwalk66 = dirwalk65;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk65 + 2 < dwalk66) {
                        dwalk66 = dwalk65 + 2;
                        dirwalk66 = dirwalk65;
                    }
                }
                // from (2, 2)
                if (c80 != Direction.CENTER && l80.add(c80).equals(l66)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk80 + 1 < dwalk66) {
                        dwalk66 = dwalk80 + 1;
                        dirwalk66 = dirwalk80;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk80 + 2 < dwalk66) {
                        dwalk66 = dwalk80 + 2;
                        dirwalk66 = dirwalk80;
                    }
                }
                // from (3, 0)
                if (c51 != Direction.CENTER && l51.add(c51).equals(l66)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk51 + 1 < dwalk66) {
                        dwalk66 = dwalk51 + 1;
                        dirwalk66 = dirwalk51;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk51 + 2 < dwalk66) {
                        dwalk66 = dwalk51 + 2;
                        dirwalk66 = dirwalk51;
                    }
                }
            }
        }

        if (rc.onTheMap(l15)) { // check (-3, -2)
            if (rc.canSenseLocation(l15)) {
                MapInfo mi15 = rc.senseMapInfo(l15);
                p15 = mi15.isPassable();
                c15 = mi15.getCurrentDirection();
            }
            if (p15) {
                // from (-2, -1)
                if (c31 != Direction.CENTER && l31.add(c31).equals(l15)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk31 + 1 < dwalk15) {
                        dwalk15 = dwalk31 + 1;
                        dirwalk15 = dirwalk31;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk31 + 2 < dwalk15) {
                        dwalk15 = dwalk31 + 2;
                        dirwalk15 = dirwalk31;
                    }
                }
                // from (-2, -2)
                if (c16 != Direction.CENTER && l16.add(c16).equals(l15)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk16 + 1 < dwalk15) {
                        dwalk15 = dwalk16 + 1;
                        dirwalk15 = dirwalk16;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk16 + 2 < dwalk15) {
                        dwalk15 = dwalk16 + 2;
                        dirwalk15 = dirwalk16;
                    }
                }
                // from (-3, -1)
                if (c30 != Direction.CENTER && l30.add(c30).equals(l15)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk30 + 1 < dwalk15) {
                        dwalk15 = dwalk30 + 1;
                        dirwalk15 = dirwalk30;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk30 + 2 < dwalk15) {
                        dwalk15 = dwalk30 + 2;
                        dirwalk15 = dirwalk30;
                    }
                }
            }
        }

        if (rc.onTheMap(l75)) { // check (-3, 2)
            if (rc.canSenseLocation(l75)) {
                MapInfo mi75 = rc.senseMapInfo(l75);
                p75 = mi75.isPassable();
                c75 = mi75.getCurrentDirection();
            }
            if (p75) {
                // from (-2, 1)
                if (c61 != Direction.CENTER && l61.add(c61).equals(l75)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk61 + 1 < dwalk75) {
                        dwalk75 = dwalk61 + 1;
                        dirwalk75 = dirwalk61;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk61 + 2 < dwalk75) {
                        dwalk75 = dwalk61 + 2;
                        dirwalk75 = dirwalk61;
                    }
                }
                // from (-2, 2)
                if (c76 != Direction.CENTER && l76.add(c76).equals(l75)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk76 + 1 < dwalk75) {
                        dwalk75 = dwalk76 + 1;
                        dirwalk75 = dirwalk76;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk76 + 2 < dwalk75) {
                        dwalk75 = dwalk76 + 2;
                        dirwalk75 = dirwalk76;
                    }
                }
                // from (-3, 1)
                if (c60 != Direction.CENTER && l60.add(c60).equals(l75)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk60 + 1 < dwalk75) {
                        dwalk75 = dwalk60 + 1;
                        dirwalk75 = dirwalk60;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk60 + 2 < dwalk75) {
                        dwalk75 = dwalk60 + 2;
                        dirwalk75 = dirwalk60;
                    }
                }
            }
        }

        if (rc.onTheMap(l1)) { // check (-2, -3)
            if (rc.canSenseLocation(l1)) {
                MapInfo mi1 = rc.senseMapInfo(l1);
                p1 = mi1.isPassable();
                c1 = mi1.getCurrentDirection();
            }
            if (p1) {
                // from (-1, -2)
                if (c17 != Direction.CENTER && l17.add(c17).equals(l1)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk17 + 1 < dwalk1) {
                        dwalk1 = dwalk17 + 1;
                        dirwalk1 = dirwalk17;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk17 + 2 < dwalk1) {
                        dwalk1 = dwalk17 + 2;
                        dirwalk1 = dirwalk17;
                    }
                }
                // from (-2, -2)
                if (c16 != Direction.CENTER && l16.add(c16).equals(l1)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk16 + 1 < dwalk1) {
                        dwalk1 = dwalk16 + 1;
                        dirwalk1 = dirwalk16;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk16 + 2 < dwalk1) {
                        dwalk1 = dwalk16 + 2;
                        dirwalk1 = dirwalk16;
                    }
                }
                // from (-1, -3)
                if (c2 != Direction.CENTER && l2.add(c2).equals(l1)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk2 + 1 < dwalk1) {
                        dwalk1 = dwalk2 + 1;
                        dirwalk1 = dirwalk2;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk2 + 2 < dwalk1) {
                        dwalk1 = dwalk2 + 2;
                        dirwalk1 = dirwalk2;
                    }
                }
                // from (-3, -2)
                if (c15 != Direction.CENTER && l15.add(c15).equals(l1)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk15 + 1 < dwalk1) {
                        dwalk1 = dwalk15 + 1;
                        dirwalk1 = dirwalk15;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk15 + 2 < dwalk1) {
                        dwalk1 = dwalk15 + 2;
                        dirwalk1 = dirwalk15;
                    }
                }
            }
        }

        if (rc.onTheMap(l91)) { // check (-2, 3)
            if (rc.canSenseLocation(l91)) {
                MapInfo mi91 = rc.senseMapInfo(l91);
                p91 = mi91.isPassable();
                c91 = mi91.getCurrentDirection();
            }
            if (p91) {
                // from (-1, 2)
                if (c77 != Direction.CENTER && l77.add(c77).equals(l91)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk77 + 1 < dwalk91) {
                        dwalk91 = dwalk77 + 1;
                        dirwalk91 = dirwalk77;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk77 + 2 < dwalk91) {
                        dwalk91 = dwalk77 + 2;
                        dirwalk91 = dirwalk77;
                    }
                }
                // from (-2, 2)
                if (c76 != Direction.CENTER && l76.add(c76).equals(l91)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk76 + 1 < dwalk91) {
                        dwalk91 = dwalk76 + 1;
                        dirwalk91 = dirwalk76;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk76 + 2 < dwalk91) {
                        dwalk91 = dwalk76 + 2;
                        dirwalk91 = dirwalk76;
                    }
                }
                // from (-1, 3)
                if (c92 != Direction.CENTER && l92.add(c92).equals(l91)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk92 + 1 < dwalk91) {
                        dwalk91 = dwalk92 + 1;
                        dirwalk91 = dirwalk92;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk92 + 2 < dwalk91) {
                        dwalk91 = dwalk92 + 2;
                        dirwalk91 = dirwalk92;
                    }
                }
                // from (-3, 2)
                if (c75 != Direction.CENTER && l75.add(c75).equals(l91)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk75 + 1 < dwalk91) {
                        dwalk91 = dwalk75 + 1;
                        dirwalk91 = dirwalk75;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk75 + 2 < dwalk91) {
                        dwalk91 = dwalk75 + 2;
                        dirwalk91 = dirwalk75;
                    }
                }
            }
        }

        if (rc.onTheMap(l5)) { // check (2, -3)
            if (rc.canSenseLocation(l5)) {
                MapInfo mi5 = rc.senseMapInfo(l5);
                p5 = mi5.isPassable();
                c5 = mi5.getCurrentDirection();
            }
            if (p5) {
                // from (1, -2)
                if (c19 != Direction.CENTER && l19.add(c19).equals(l5)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk19 + 1 < dwalk5) {
                        dwalk5 = dwalk19 + 1;
                        dirwalk5 = dirwalk19;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk19 + 2 < dwalk5) {
                        dwalk5 = dwalk19 + 2;
                        dirwalk5 = dirwalk19;
                    }
                }
                // from (2, -2)
                if (c20 != Direction.CENTER && l20.add(c20).equals(l5)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk20 + 1 < dwalk5) {
                        dwalk5 = dwalk20 + 1;
                        dirwalk5 = dirwalk20;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk20 + 2 < dwalk5) {
                        dwalk5 = dwalk20 + 2;
                        dirwalk5 = dirwalk20;
                    }
                }
                // from (1, -3)
                if (c4 != Direction.CENTER && l4.add(c4).equals(l5)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk4 + 1 < dwalk5) {
                        dwalk5 = dwalk4 + 1;
                        dirwalk5 = dirwalk4;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk4 + 2 < dwalk5) {
                        dwalk5 = dwalk4 + 2;
                        dirwalk5 = dirwalk4;
                    }
                }
            }
        }

        if (rc.onTheMap(l95)) { // check (2, 3)
            if (rc.canSenseLocation(l95)) {
                MapInfo mi95 = rc.senseMapInfo(l95);
                p95 = mi95.isPassable();
                c95 = mi95.getCurrentDirection();
            }
            if (p95) {
                // from (1, 2)
                if (c79 != Direction.CENTER && l79.add(c79).equals(l95)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk79 + 1 < dwalk95) {
                        dwalk95 = dwalk79 + 1;
                        dirwalk95 = dirwalk79;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk79 + 2 < dwalk95) {
                        dwalk95 = dwalk79 + 2;
                        dirwalk95 = dirwalk79;
                    }
                }
                // from (2, 2)
                if (c80 != Direction.CENTER && l80.add(c80).equals(l95)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk80 + 1 < dwalk95) {
                        dwalk95 = dwalk80 + 1;
                        dirwalk95 = dirwalk80;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk80 + 2 < dwalk95) {
                        dwalk95 = dwalk80 + 2;
                        dirwalk95 = dirwalk80;
                    }
                }
                // from (1, 3)
                if (c94 != Direction.CENTER && l94.add(c94).equals(l95)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk94 + 1 < dwalk95) {
                        dwalk95 = dwalk94 + 1;
                        dirwalk95 = dirwalk94;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk94 + 2 < dwalk95) {
                        dwalk95 = dwalk94 + 2;
                        dirwalk95 = dirwalk94;
                    }
                }
            }
        }

        if (rc.onTheMap(l21)) { // check (3, -2)
            if (rc.canSenseLocation(l21)) {
                MapInfo mi21 = rc.senseMapInfo(l21);
                p21 = mi21.isPassable();
                c21 = mi21.getCurrentDirection();
            }
            if (p21) {
                // from (2, -1)
                if (c35 != Direction.CENTER && l35.add(c35).equals(l21)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk35 + 1 < dwalk21) {
                        dwalk21 = dwalk35 + 1;
                        dirwalk21 = dirwalk35;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk35 + 2 < dwalk21) {
                        dwalk21 = dwalk35 + 2;
                        dirwalk21 = dirwalk35;
                    }
                }
                // from (2, -2)
                if (c20 != Direction.CENTER && l20.add(c20).equals(l21)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk20 + 1 < dwalk21) {
                        dwalk21 = dwalk20 + 1;
                        dirwalk21 = dirwalk20;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk20 + 2 < dwalk21) {
                        dwalk21 = dwalk20 + 2;
                        dirwalk21 = dirwalk20;
                    }
                }
                // from (3, -1)
                if (c36 != Direction.CENTER && l36.add(c36).equals(l21)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk36 + 1 < dwalk21) {
                        dwalk21 = dwalk36 + 1;
                        dirwalk21 = dirwalk36;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk36 + 2 < dwalk21) {
                        dwalk21 = dwalk36 + 2;
                        dirwalk21 = dirwalk36;
                    }
                }
                // from (2, -3)
                if (c5 != Direction.CENTER && l5.add(c5).equals(l21)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk5 + 1 < dwalk21) {
                        dwalk21 = dwalk5 + 1;
                        dirwalk21 = dirwalk5;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk5 + 2 < dwalk21) {
                        dwalk21 = dwalk5 + 2;
                        dirwalk21 = dirwalk5;
                    }
                }
            }
        }

        if (rc.onTheMap(l81)) { // check (3, 2)
            if (rc.canSenseLocation(l81)) {
                MapInfo mi81 = rc.senseMapInfo(l81);
                p81 = mi81.isPassable();
                c81 = mi81.getCurrentDirection();
            }
            if (p81) {
                // from (2, 1)
                if (c65 != Direction.CENTER && l65.add(c65).equals(l81)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk65 + 1 < dwalk81) {
                        dwalk81 = dwalk65 + 1;
                        dirwalk81 = dirwalk65;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk65 + 2 < dwalk81) {
                        dwalk81 = dwalk65 + 2;
                        dirwalk81 = dirwalk65;
                    }
                }
                // from (2, 2)
                if (c80 != Direction.CENTER && l80.add(c80).equals(l81)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk80 + 1 < dwalk81) {
                        dwalk81 = dwalk80 + 1;
                        dirwalk81 = dirwalk80;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk80 + 2 < dwalk81) {
                        dwalk81 = dwalk80 + 2;
                        dirwalk81 = dirwalk80;
                    }
                }
                // from (3, 1)
                if (c66 != Direction.CENTER && l66.add(c66).equals(l81)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk66 + 1 < dwalk81) {
                        dwalk81 = dwalk66 + 1;
                        dirwalk81 = dirwalk66;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk66 + 2 < dwalk81) {
                        dwalk81 = dwalk66 + 2;
                        dirwalk81 = dirwalk66;
                    }
                }
                // from (2, 3)
                if (c95 != Direction.CENTER && l95.add(c95).equals(l81)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk95 + 1 < dwalk81) {
                        dwalk81 = dwalk95 + 1;
                        dirwalk81 = dirwalk95;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk95 + 2 < dwalk81) {
                        dwalk81 = dwalk95 + 2;
                        dirwalk81 = dirwalk95;
                    }
                }
            }
        }

        if (rc.onTheMap(l0)) { // check (-3, -3)
            if (rc.canSenseLocation(l0)) {
                MapInfo mi0 = rc.senseMapInfo(l0);
                p0 = mi0.isPassable();
                c0 = mi0.getCurrentDirection();
            }
            if (p0) {
                // from (-2, -2)
                if (c16 != Direction.CENTER && l16.add(c16).equals(l0)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk16 + 1 < dwalk0) {
                        dwalk0 = dwalk16 + 1;
                        dirwalk0 = dirwalk16;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk16 + 2 < dwalk0) {
                        dwalk0 = dwalk16 + 2;
                        dirwalk0 = dirwalk16;
                    }
                }
                // from (-3, -2)
                if (c15 != Direction.CENTER && l15.add(c15).equals(l0)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk15 + 1 < dwalk0) {
                        dwalk0 = dwalk15 + 1;
                        dirwalk0 = dirwalk15;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk15 + 2 < dwalk0) {
                        dwalk0 = dwalk15 + 2;
                        dirwalk0 = dirwalk15;
                    }
                }
                // from (-2, -3)
                if (c1 != Direction.CENTER && l1.add(c1).equals(l0)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk1 + 1 < dwalk0) {
                        dwalk0 = dwalk1 + 1;
                        dirwalk0 = dirwalk1;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk1 + 2 < dwalk0) {
                        dwalk0 = dwalk1 + 2;
                        dirwalk0 = dirwalk1;
                    }
                }
            }
        }

        if (rc.onTheMap(l90)) { // check (-3, 3)
            if (rc.canSenseLocation(l90)) {
                MapInfo mi90 = rc.senseMapInfo(l90);
                p90 = mi90.isPassable();
                c90 = mi90.getCurrentDirection();
            }
            if (p90) {
                // from (-2, 2)
                if (c76 != Direction.CENTER && l76.add(c76).equals(l90)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk76 + 1 < dwalk90) {
                        dwalk90 = dwalk76 + 1;
                        dirwalk90 = dirwalk76;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk76 + 2 < dwalk90) {
                        dwalk90 = dwalk76 + 2;
                        dirwalk90 = dirwalk76;
                    }
                }
                // from (-3, 2)
                if (c75 != Direction.CENTER && l75.add(c75).equals(l90)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk75 + 1 < dwalk90) {
                        dwalk90 = dwalk75 + 1;
                        dirwalk90 = dirwalk75;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk75 + 2 < dwalk90) {
                        dwalk90 = dwalk75 + 2;
                        dirwalk90 = dirwalk75;
                    }
                }
                // from (-2, 3)
                if (c91 != Direction.CENTER && l91.add(c91).equals(l90)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk91 + 1 < dwalk90) {
                        dwalk90 = dwalk91 + 1;
                        dirwalk90 = dirwalk91;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk91 + 2 < dwalk90) {
                        dwalk90 = dwalk91 + 2;
                        dirwalk90 = dirwalk91;
                    }
                }
            }
        }

        if (rc.onTheMap(l6)) { // check (3, -3)
            if (rc.canSenseLocation(l6)) {
                MapInfo mi6 = rc.senseMapInfo(l6);
                p6 = mi6.isPassable();
                c6 = mi6.getCurrentDirection();
            }
            if (p6) {
                // from (2, -2)
                if (c20 != Direction.CENTER && l20.add(c20).equals(l6)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk20 + 1 < dwalk6) {
                        dwalk6 = dwalk20 + 1;
                        dirwalk6 = dirwalk20;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk20 + 2 < dwalk6) {
                        dwalk6 = dwalk20 + 2;
                        dirwalk6 = dirwalk20;
                    }
                }
                // from (2, -3)
                if (c5 != Direction.CENTER && l5.add(c5).equals(l6)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk5 + 1 < dwalk6) {
                        dwalk6 = dwalk5 + 1;
                        dirwalk6 = dirwalk5;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk5 + 2 < dwalk6) {
                        dwalk6 = dwalk5 + 2;
                        dirwalk6 = dirwalk5;
                    }
                }
                // from (3, -2)
                if (c21 != Direction.CENTER && l21.add(c21).equals(l6)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk21 + 1 < dwalk6) {
                        dwalk6 = dwalk21 + 1;
                        dirwalk6 = dirwalk21;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk21 + 2 < dwalk6) {
                        dwalk6 = dwalk21 + 2;
                        dirwalk6 = dirwalk21;
                    }
                }
            }
        }

        if (rc.onTheMap(l96)) { // check (3, 3)
            if (rc.canSenseLocation(l96)) {
                MapInfo mi96 = rc.senseMapInfo(l96);
                p96 = mi96.isPassable();
                c96 = mi96.getCurrentDirection();
            }
            if (p96) {
                // from (2, 2)
                if (c80 != Direction.CENTER && l80.add(c80).equals(l96)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk80 + 1 < dwalk96) {
                        dwalk96 = dwalk80 + 1;
                        dirwalk96 = dirwalk80;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk80 + 2 < dwalk96) {
                        dwalk96 = dwalk80 + 2;
                        dirwalk96 = dirwalk80;
                    }
                }
                // from (2, 3)
                if (c95 != Direction.CENTER && l95.add(c95).equals(l96)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk95 + 1 < dwalk96) {
                        dwalk96 = dwalk95 + 1;
                        dirwalk96 = dirwalk95;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk95 + 2 < dwalk96) {
                        dwalk96 = dwalk95 + 2;
                        dirwalk96 = dirwalk95;
                    }
                }
                // from (3, 2)
                if (c81 != Direction.CENTER && l81.add(c81).equals(l96)) {
                    // there was a current at the previous location and the current flows into this location
                    if (dwalk81 + 1 < dwalk96) {
                        dwalk96 = dwalk81 + 1;
                        dirwalk96 = dirwalk81;
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk81 + 2 < dwalk96) {
                        dwalk96 = dwalk81 + 2;
                        dirwalk96 = dirwalk81;
                    }
                }
            }
        }


        // System.out.println("LOCAL DISTANCES:");
        // System.out.println("\t" + dwalk90 + "\t" + dwalk91 + "\t" + dwalk92 + "\t" + dwalk93 + "\t" + dwalk94 + "\t" + dwalk95 + "\t" + dwalk96);
        // System.out.println("\t" + dwalk75 + "\t" + dwalk76 + "\t" + dwalk77 + "\t" + dwalk78 + "\t" + dwalk79 + "\t" + dwalk80 + "\t" + dwalk81);
        // System.out.println("\t" + dwalk60 + "\t" + dwalk61 + "\t" + dwalk62 + "\t" + dwalk63 + "\t" + dwalk64 + "\t" + dwalk65 + "\t" + dwalk66);
        // System.out.println("\t" + dwalk45 + "\t" + dwalk46 + "\t" + dwalk47 + "\t" + dwalk48 + "\t" + dwalk49 + "\t" + dwalk50 + "\t" + dwalk51);
        // System.out.println("\t" + dwalk30 + "\t" + dwalk31 + "\t" + dwalk32 + "\t" + dwalk33 + "\t" + dwalk34 + "\t" + dwalk35 + "\t" + dwalk36);
        // System.out.println("\t" + dwalk15 + "\t" + dwalk16 + "\t" + dwalk17 + "\t" + dwalk18 + "\t" + dwalk19 + "\t" + dwalk20 + "\t" + dwalk21);
        // System.out.println("\t" + dwalk0 + "\t" + dwalk1 + "\t" + dwalk2 + "\t" + dwalk3 + "\t" + dwalk4 + "\t" + dwalk5 + "\t" + dwalk6);
        // System.out.println("DIRECTIONS:");
        // System.out.println("\t" + dirwalk90 + "\t" + dirwalk91 + "\t" + dirwalk92 + "\t" + dirwalk93 + "\t" + dirwalk94 + "\t" + dirwalk95 + "\t" + dirwalk96);
        // System.out.println("\t" + dirwalk75 + "\t" + dirwalk76 + "\t" + dirwalk77 + "\t" + dirwalk78 + "\t" + dirwalk79 + "\t" + dirwalk80 + "\t" + dirwalk81);
        // System.out.println("\t" + dirwalk60 + "\t" + dirwalk61 + "\t" + dirwalk62 + "\t" + dirwalk63 + "\t" + dirwalk64 + "\t" + dirwalk65 + "\t" + dirwalk66);
        // System.out.println("\t" + dirwalk45 + "\t" + dirwalk46 + "\t" + dirwalk47 + "\t" + dirwalk48 + "\t" + dirwalk49 + "\t" + dirwalk50 + "\t" + dirwalk51);
        // System.out.println("\t" + dirwalk30 + "\t" + dirwalk31 + "\t" + dirwalk32 + "\t" + dirwalk33 + "\t" + dirwalk34 + "\t" + dirwalk35 + "\t" + dirwalk36);
        // System.out.println("\t" + dirwalk15 + "\t" + dirwalk16 + "\t" + dirwalk17 + "\t" + dirwalk18 + "\t" + dirwalk19 + "\t" + dirwalk20 + "\t" + dirwalk21);
        // System.out.println("\t" + dirwalk0 + "\t" + dirwalk1 + "\t" + dirwalk2 + "\t" + dirwalk3 + "\t" + dirwalk4 + "\t" + dirwalk5 + "\t" + dirwalk6);

        int target_dx = target.x - l48.x;
        int target_dy = target.y - l48.y;
        switch (target_dx) {
                case -3:
                    switch (target_dy) {
                        case -3:
                            return dirwalk0; // destination is at relative location (-3, -3)
                        case -2:
                            return dirwalk15; // destination is at relative location (-3, -2)
                        case -1:
                            return dirwalk30; // destination is at relative location (-3, -1)
                        case 0:
                            return dirwalk45; // destination is at relative location (-3, 0)
                        case 1:
                            return dirwalk60; // destination is at relative location (-3, 1)
                        case 2:
                            return dirwalk75; // destination is at relative location (-3, 2)
                        case 3:
                            return dirwalk90; // destination is at relative location (-3, 3)
                    }
                    break;
                case -2:
                    switch (target_dy) {
                        case -3:
                            return dirwalk1; // destination is at relative location (-2, -3)
                        case -2:
                            return dirwalk16; // destination is at relative location (-2, -2)
                        case -1:
                            return dirwalk31; // destination is at relative location (-2, -1)
                        case 0:
                            return dirwalk46; // destination is at relative location (-2, 0)
                        case 1:
                            return dirwalk61; // destination is at relative location (-2, 1)
                        case 2:
                            return dirwalk76; // destination is at relative location (-2, 2)
                        case 3:
                            return dirwalk91; // destination is at relative location (-2, 3)
                    }
                    break;
                case -1:
                    switch (target_dy) {
                        case -3:
                            return dirwalk2; // destination is at relative location (-1, -3)
                        case -2:
                            return dirwalk17; // destination is at relative location (-1, -2)
                        case -1:
                            return dirwalk32; // destination is at relative location (-1, -1)
                        case 0:
                            return dirwalk47; // destination is at relative location (-1, 0)
                        case 1:
                            return dirwalk62; // destination is at relative location (-1, 1)
                        case 2:
                            return dirwalk77; // destination is at relative location (-1, 2)
                        case 3:
                            return dirwalk92; // destination is at relative location (-1, 3)
                    }
                    break;
                case 0:
                    switch (target_dy) {
                        case -3:
                            return dirwalk3; // destination is at relative location (0, -3)
                        case -2:
                            return dirwalk18; // destination is at relative location (0, -2)
                        case -1:
                            return dirwalk33; // destination is at relative location (0, -1)
                        case 0:
                            return dirwalk48; // destination is at relative location (0, 0)
                        case 1:
                            return dirwalk63; // destination is at relative location (0, 1)
                        case 2:
                            return dirwalk78; // destination is at relative location (0, 2)
                        case 3:
                            return dirwalk93; // destination is at relative location (0, 3)
                    }
                    break;
                case 1:
                    switch (target_dy) {
                        case -3:
                            return dirwalk4; // destination is at relative location (1, -3)
                        case -2:
                            return dirwalk19; // destination is at relative location (1, -2)
                        case -1:
                            return dirwalk34; // destination is at relative location (1, -1)
                        case 0:
                            return dirwalk49; // destination is at relative location (1, 0)
                        case 1:
                            return dirwalk64; // destination is at relative location (1, 1)
                        case 2:
                            return dirwalk79; // destination is at relative location (1, 2)
                        case 3:
                            return dirwalk94; // destination is at relative location (1, 3)
                    }
                    break;
                case 2:
                    switch (target_dy) {
                        case -3:
                            return dirwalk5; // destination is at relative location (2, -3)
                        case -2:
                            return dirwalk20; // destination is at relative location (2, -2)
                        case -1:
                            return dirwalk35; // destination is at relative location (2, -1)
                        case 0:
                            return dirwalk50; // destination is at relative location (2, 0)
                        case 1:
                            return dirwalk65; // destination is at relative location (2, 1)
                        case 2:
                            return dirwalk80; // destination is at relative location (2, 2)
                        case 3:
                            return dirwalk95; // destination is at relative location (2, 3)
                    }
                    break;
                case 3:
                    switch (target_dy) {
                        case -3:
                            return dirwalk6; // destination is at relative location (3, -3)
                        case -2:
                            return dirwalk21; // destination is at relative location (3, -2)
                        case -1:
                            return dirwalk36; // destination is at relative location (3, -1)
                        case 0:
                            return dirwalk51; // destination is at relative location (3, 0)
                        case 1:
                            return dirwalk66; // destination is at relative location (3, 1)
                        case 2:
                            return dirwalk81; // destination is at relative location (3, 2)
                        case 3:
                            return dirwalk96; // destination is at relative location (3, 3)
                    }
                    break;
        }

        Direction ans = null;
        double bestScore = 0;
        double currDist = Math.sqrt(l48.distanceSquaredTo(target));
        
        double score0 = (currDist - Math.sqrt(l0.distanceSquaredTo(target))) / dwalk0;
        if (score0 > bestScore) {
            bestScore = score0;
            ans = dirwalk0;
        }

        double score15 = (currDist - Math.sqrt(l15.distanceSquaredTo(target))) / dwalk15;
        if (score15 > bestScore) {
            bestScore = score15;
            ans = dirwalk15;
        }

        double score75 = (currDist - Math.sqrt(l75.distanceSquaredTo(target))) / dwalk75;
        if (score75 > bestScore) {
            bestScore = score75;
            ans = dirwalk75;
        }

        double score90 = (currDist - Math.sqrt(l90.distanceSquaredTo(target))) / dwalk90;
        if (score90 > bestScore) {
            bestScore = score90;
            ans = dirwalk90;
        }

        double score1 = (currDist - Math.sqrt(l1.distanceSquaredTo(target))) / dwalk1;
        if (score1 > bestScore) {
            bestScore = score1;
            ans = dirwalk1;
        }

        double score91 = (currDist - Math.sqrt(l91.distanceSquaredTo(target))) / dwalk91;
        if (score91 > bestScore) {
            bestScore = score91;
            ans = dirwalk91;
        }

        double score5 = (currDist - Math.sqrt(l5.distanceSquaredTo(target))) / dwalk5;
        if (score5 > bestScore) {
            bestScore = score5;
            ans = dirwalk5;
        }

        double score95 = (currDist - Math.sqrt(l95.distanceSquaredTo(target))) / dwalk95;
        if (score95 > bestScore) {
            bestScore = score95;
            ans = dirwalk95;
        }

        double score6 = (currDist - Math.sqrt(l6.distanceSquaredTo(target))) / dwalk6;
        if (score6 > bestScore) {
            bestScore = score6;
            ans = dirwalk6;
        }

        double score21 = (currDist - Math.sqrt(l21.distanceSquaredTo(target))) / dwalk21;
        if (score21 > bestScore) {
            bestScore = score21;
            ans = dirwalk21;
        }

        double score81 = (currDist - Math.sqrt(l81.distanceSquaredTo(target))) / dwalk81;
        if (score81 > bestScore) {
            bestScore = score81;
            ans = dirwalk81;
        }

        double score96 = (currDist - Math.sqrt(l96.distanceSquaredTo(target))) / dwalk96;
        if (score96 > bestScore) {
            bestScore = score96;
            ans = dirwalk96;
        }


        return ans;
    }

    private void initRest() {
    
        l47 = l48.add(Direction.WEST); // (-1, 0) from (0, 0)
        dwalk47 = 99999;
        dirwalk47 = null;
        p47 = true;
        c47 = Direction.CENTER;
    
        l33 = l48.add(Direction.SOUTH); // (0, -1) from (0, 0)
        dwalk33 = 99999;
        dirwalk33 = null;
        p33 = true;
        c33 = Direction.CENTER;
    
        l63 = l48.add(Direction.NORTH); // (0, 1) from (0, 0)
        dwalk63 = 99999;
        dirwalk63 = null;
        p63 = true;
        c63 = Direction.CENTER;
    
        l49 = l48.add(Direction.EAST); // (1, 0) from (0, 0)
        dwalk49 = 99999;
        dirwalk49 = null;
        p49 = true;
        c49 = Direction.CENTER;
    
        l32 = l48.add(Direction.SOUTHWEST); // (-1, -1) from (0, 0)
        dwalk32 = 99999;
        dirwalk32 = null;
        p32 = true;
        c32 = Direction.CENTER;
    
        l62 = l48.add(Direction.NORTHWEST); // (-1, 1) from (0, 0)
        dwalk62 = 99999;
        dirwalk62 = null;
        p62 = true;
        c62 = Direction.CENTER;
    
        l34 = l48.add(Direction.SOUTHEAST); // (1, -1) from (0, 0)
        dwalk34 = 99999;
        dirwalk34 = null;
        p34 = true;
        c34 = Direction.CENTER;
    
        l64 = l48.add(Direction.NORTHEAST); // (1, 1) from (0, 0)
        dwalk64 = 99999;
        dirwalk64 = null;
        p64 = true;
        c64 = Direction.CENTER;
    
        l46 = l47.add(Direction.WEST); // (-2, 0) from (-1, 0)
        dwalk46 = 99999;
        dirwalk46 = null;
        p46 = true;
        c46 = Direction.CENTER;
    
        l18 = l33.add(Direction.SOUTH); // (0, -2) from (0, -1)
        dwalk18 = 99999;
        dirwalk18 = null;
        p18 = true;
        c18 = Direction.CENTER;
    
        l78 = l63.add(Direction.NORTH); // (0, 2) from (0, 1)
        dwalk78 = 99999;
        dirwalk78 = null;
        p78 = true;
        c78 = Direction.CENTER;
    
        l50 = l49.add(Direction.EAST); // (2, 0) from (1, 0)
        dwalk50 = 99999;
        dirwalk50 = null;
        p50 = true;
        c50 = Direction.CENTER;
    
        l31 = l47.add(Direction.SOUTHWEST); // (-2, -1) from (-1, 0)
        dwalk31 = 99999;
        dirwalk31 = null;
        p31 = true;
        c31 = Direction.CENTER;
    
        l61 = l47.add(Direction.NORTHWEST); // (-2, 1) from (-1, 0)
        dwalk61 = 99999;
        dirwalk61 = null;
        p61 = true;
        c61 = Direction.CENTER;
    
        l17 = l33.add(Direction.SOUTHWEST); // (-1, -2) from (0, -1)
        dwalk17 = 99999;
        dirwalk17 = null;
        p17 = true;
        c17 = Direction.CENTER;
    
        l77 = l63.add(Direction.NORTHWEST); // (-1, 2) from (0, 1)
        dwalk77 = 99999;
        dirwalk77 = null;
        p77 = true;
        c77 = Direction.CENTER;
    
        l19 = l33.add(Direction.SOUTHEAST); // (1, -2) from (0, -1)
        dwalk19 = 99999;
        dirwalk19 = null;
        p19 = true;
        c19 = Direction.CENTER;
    
        l79 = l63.add(Direction.NORTHEAST); // (1, 2) from (0, 1)
        dwalk79 = 99999;
        dirwalk79 = null;
        p79 = true;
        c79 = Direction.CENTER;
    
        l35 = l49.add(Direction.SOUTHEAST); // (2, -1) from (1, 0)
        dwalk35 = 99999;
        dirwalk35 = null;
        p35 = true;
        c35 = Direction.CENTER;
    
        l65 = l49.add(Direction.NORTHEAST); // (2, 1) from (1, 0)
        dwalk65 = 99999;
        dirwalk65 = null;
        p65 = true;
        c65 = Direction.CENTER;
    
        l16 = l32.add(Direction.SOUTHWEST); // (-2, -2) from (-1, -1)
        dwalk16 = 99999;
        dirwalk16 = null;
        p16 = true;
        c16 = Direction.CENTER;
    
        l76 = l62.add(Direction.NORTHWEST); // (-2, 2) from (-1, 1)
        dwalk76 = 99999;
        dirwalk76 = null;
        p76 = true;
        c76 = Direction.CENTER;
    
        l20 = l34.add(Direction.SOUTHEAST); // (2, -2) from (1, -1)
        dwalk20 = 99999;
        dirwalk20 = null;
        p20 = true;
        c20 = Direction.CENTER;
    
        l80 = l64.add(Direction.NORTHEAST); // (2, 2) from (1, 1)
        dwalk80 = 99999;
        dirwalk80 = null;
        p80 = true;
        c80 = Direction.CENTER;
    
        l45 = l46.add(Direction.WEST); // (-3, 0) from (-2, 0)
        dwalk45 = 99999;
        dirwalk45 = null;
        p45 = true;
        c45 = Direction.CENTER;
    
        l3 = l18.add(Direction.SOUTH); // (0, -3) from (0, -2)
        dwalk3 = 99999;
        dirwalk3 = null;
        p3 = true;
        c3 = Direction.CENTER;
    
        l93 = l78.add(Direction.NORTH); // (0, 3) from (0, 2)
        dwalk93 = 99999;
        dirwalk93 = null;
        p93 = true;
        c93 = Direction.CENTER;
    
        l51 = l50.add(Direction.EAST); // (3, 0) from (2, 0)
        dwalk51 = 99999;
        dirwalk51 = null;
        p51 = true;
        c51 = Direction.CENTER;
    
        l30 = l46.add(Direction.SOUTHWEST); // (-3, -1) from (-2, 0)
        dwalk30 = 99999;
        dirwalk30 = null;
        p30 = true;
        c30 = Direction.CENTER;
    
        l60 = l46.add(Direction.NORTHWEST); // (-3, 1) from (-2, 0)
        dwalk60 = 99999;
        dirwalk60 = null;
        p60 = true;
        c60 = Direction.CENTER;
    
        l2 = l18.add(Direction.SOUTHWEST); // (-1, -3) from (0, -2)
        dwalk2 = 99999;
        dirwalk2 = null;
        p2 = true;
        c2 = Direction.CENTER;
    
        l92 = l78.add(Direction.NORTHWEST); // (-1, 3) from (0, 2)
        dwalk92 = 99999;
        dirwalk92 = null;
        p92 = true;
        c92 = Direction.CENTER;
    
        l4 = l18.add(Direction.SOUTHEAST); // (1, -3) from (0, -2)
        dwalk4 = 99999;
        dirwalk4 = null;
        p4 = true;
        c4 = Direction.CENTER;
    
        l94 = l78.add(Direction.NORTHEAST); // (1, 3) from (0, 2)
        dwalk94 = 99999;
        dirwalk94 = null;
        p94 = true;
        c94 = Direction.CENTER;
    
        l36 = l50.add(Direction.SOUTHEAST); // (3, -1) from (2, 0)
        dwalk36 = 99999;
        dirwalk36 = null;
        p36 = true;
        c36 = Direction.CENTER;
    
        l66 = l50.add(Direction.NORTHEAST); // (3, 1) from (2, 0)
        dwalk66 = 99999;
        dirwalk66 = null;
        p66 = true;
        c66 = Direction.CENTER;
    
        l15 = l31.add(Direction.SOUTHWEST); // (-3, -2) from (-2, -1)
        dwalk15 = 99999;
        dirwalk15 = null;
        p15 = true;
        c15 = Direction.CENTER;
    
        l75 = l61.add(Direction.NORTHWEST); // (-3, 2) from (-2, 1)
        dwalk75 = 99999;
        dirwalk75 = null;
        p75 = true;
        c75 = Direction.CENTER;
    
        l1 = l17.add(Direction.SOUTHWEST); // (-2, -3) from (-1, -2)
        dwalk1 = 99999;
        dirwalk1 = null;
        p1 = true;
        c1 = Direction.CENTER;
    
        l91 = l77.add(Direction.NORTHWEST); // (-2, 3) from (-1, 2)
        dwalk91 = 99999;
        dirwalk91 = null;
        p91 = true;
        c91 = Direction.CENTER;
    
        l5 = l19.add(Direction.SOUTHEAST); // (2, -3) from (1, -2)
        dwalk5 = 99999;
        dirwalk5 = null;
        p5 = true;
        c5 = Direction.CENTER;
    
        l95 = l79.add(Direction.NORTHEAST); // (2, 3) from (1, 2)
        dwalk95 = 99999;
        dirwalk95 = null;
        p95 = true;
        c95 = Direction.CENTER;
    
        l21 = l35.add(Direction.SOUTHEAST); // (3, -2) from (2, -1)
        dwalk21 = 99999;
        dirwalk21 = null;
        p21 = true;
        c21 = Direction.CENTER;
    
        l81 = l65.add(Direction.NORTHEAST); // (3, 2) from (2, 1)
        dwalk81 = 99999;
        dirwalk81 = null;
        p81 = true;
        c81 = Direction.CENTER;
    
        l0 = l16.add(Direction.SOUTHWEST); // (-3, -3) from (-2, -2)
        dwalk0 = 99999;
        dirwalk0 = null;
        p0 = true;
        c0 = Direction.CENTER;
    
        l90 = l76.add(Direction.NORTHWEST); // (-3, 3) from (-2, 2)
        dwalk90 = 99999;
        dirwalk90 = null;
        p90 = true;
        c90 = Direction.CENTER;
    
        l6 = l20.add(Direction.SOUTHEAST); // (3, -3) from (2, -2)
        dwalk6 = 99999;
        dirwalk6 = null;
        p6 = true;
        c6 = Direction.CENTER;
    
        l96 = l80.add(Direction.NORTHEAST); // (3, 3) from (2, 2)
        dwalk96 = 99999;
        dirwalk96 = null;
        p96 = true;
        c96 = Direction.CENTER;
    
    }
}
