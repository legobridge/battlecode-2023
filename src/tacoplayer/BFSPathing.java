// Inspired by https://github.com/IvanGeffner/battlecode2021/blob/master/thirtyone/BFSMuckraker.java.
package tacoplayer;

import battlecode.common.*;

public class BFSPathing {

    RobotController rc;


    static MapLocation l15; // location representing relative coordinate (-3, -2)
    static int dwalk15; // shortest distance to location from current location if the last move was a walk
    static int dcur15; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk15; // best direction to take now if the last move was a walk
    static Direction dircur15; // best direction to take now if the last move was a current
    static boolean p15; // is the location passable
    static Direction c15; // direction of the current at the location

    static MapLocation l30; // location representing relative coordinate (-3, -1)
    static int dwalk30; // shortest distance to location from current location if the last move was a walk
    static int dcur30; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk30; // best direction to take now if the last move was a walk
    static Direction dircur30; // best direction to take now if the last move was a current
    static boolean p30; // is the location passable
    static Direction c30; // direction of the current at the location

    static MapLocation l45; // location representing relative coordinate (-3, 0)
    static int dwalk45; // shortest distance to location from current location if the last move was a walk
    static int dcur45; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk45; // best direction to take now if the last move was a walk
    static Direction dircur45; // best direction to take now if the last move was a current
    static boolean p45; // is the location passable
    static Direction c45; // direction of the current at the location

    static MapLocation l60; // location representing relative coordinate (-3, 1)
    static int dwalk60; // shortest distance to location from current location if the last move was a walk
    static int dcur60; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk60; // best direction to take now if the last move was a walk
    static Direction dircur60; // best direction to take now if the last move was a current
    static boolean p60; // is the location passable
    static Direction c60; // direction of the current at the location

    static MapLocation l75; // location representing relative coordinate (-3, 2)
    static int dwalk75; // shortest distance to location from current location if the last move was a walk
    static int dcur75; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk75; // best direction to take now if the last move was a walk
    static Direction dircur75; // best direction to take now if the last move was a current
    static boolean p75; // is the location passable
    static Direction c75; // direction of the current at the location

    static MapLocation l1; // location representing relative coordinate (-2, -3)
    static int dwalk1; // shortest distance to location from current location if the last move was a walk
    static int dcur1; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk1; // best direction to take now if the last move was a walk
    static Direction dircur1; // best direction to take now if the last move was a current
    static boolean p1; // is the location passable
    static Direction c1; // direction of the current at the location

    static MapLocation l16; // location representing relative coordinate (-2, -2)
    static int dwalk16; // shortest distance to location from current location if the last move was a walk
    static int dcur16; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk16; // best direction to take now if the last move was a walk
    static Direction dircur16; // best direction to take now if the last move was a current
    static boolean p16; // is the location passable
    static Direction c16; // direction of the current at the location

    static MapLocation l31; // location representing relative coordinate (-2, -1)
    static int dwalk31; // shortest distance to location from current location if the last move was a walk
    static int dcur31; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk31; // best direction to take now if the last move was a walk
    static Direction dircur31; // best direction to take now if the last move was a current
    static boolean p31; // is the location passable
    static Direction c31; // direction of the current at the location

    static MapLocation l46; // location representing relative coordinate (-2, 0)
    static int dwalk46; // shortest distance to location from current location if the last move was a walk
    static int dcur46; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk46; // best direction to take now if the last move was a walk
    static Direction dircur46; // best direction to take now if the last move was a current
    static boolean p46; // is the location passable
    static Direction c46; // direction of the current at the location

    static MapLocation l61; // location representing relative coordinate (-2, 1)
    static int dwalk61; // shortest distance to location from current location if the last move was a walk
    static int dcur61; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk61; // best direction to take now if the last move was a walk
    static Direction dircur61; // best direction to take now if the last move was a current
    static boolean p61; // is the location passable
    static Direction c61; // direction of the current at the location

    static MapLocation l76; // location representing relative coordinate (-2, 2)
    static int dwalk76; // shortest distance to location from current location if the last move was a walk
    static int dcur76; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk76; // best direction to take now if the last move was a walk
    static Direction dircur76; // best direction to take now if the last move was a current
    static boolean p76; // is the location passable
    static Direction c76; // direction of the current at the location

    static MapLocation l91; // location representing relative coordinate (-2, 3)
    static int dwalk91; // shortest distance to location from current location if the last move was a walk
    static int dcur91; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk91; // best direction to take now if the last move was a walk
    static Direction dircur91; // best direction to take now if the last move was a current
    static boolean p91; // is the location passable
    static Direction c91; // direction of the current at the location

    static MapLocation l2; // location representing relative coordinate (-1, -3)
    static int dwalk2; // shortest distance to location from current location if the last move was a walk
    static int dcur2; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk2; // best direction to take now if the last move was a walk
    static Direction dircur2; // best direction to take now if the last move was a current
    static boolean p2; // is the location passable
    static Direction c2; // direction of the current at the location

    static MapLocation l17; // location representing relative coordinate (-1, -2)
    static int dwalk17; // shortest distance to location from current location if the last move was a walk
    static int dcur17; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk17; // best direction to take now if the last move was a walk
    static Direction dircur17; // best direction to take now if the last move was a current
    static boolean p17; // is the location passable
    static Direction c17; // direction of the current at the location

    static MapLocation l32; // location representing relative coordinate (-1, -1)
    static int dwalk32; // shortest distance to location from current location if the last move was a walk
    static int dcur32; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk32; // best direction to take now if the last move was a walk
    static Direction dircur32; // best direction to take now if the last move was a current
    static boolean p32; // is the location passable
    static Direction c32; // direction of the current at the location

    static MapLocation l47; // location representing relative coordinate (-1, 0)
    static int dwalk47; // shortest distance to location from current location if the last move was a walk
    static int dcur47; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk47; // best direction to take now if the last move was a walk
    static Direction dircur47; // best direction to take now if the last move was a current
    static boolean p47; // is the location passable
    static Direction c47; // direction of the current at the location

    static MapLocation l62; // location representing relative coordinate (-1, 1)
    static int dwalk62; // shortest distance to location from current location if the last move was a walk
    static int dcur62; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk62; // best direction to take now if the last move was a walk
    static Direction dircur62; // best direction to take now if the last move was a current
    static boolean p62; // is the location passable
    static Direction c62; // direction of the current at the location

    static MapLocation l77; // location representing relative coordinate (-1, 2)
    static int dwalk77; // shortest distance to location from current location if the last move was a walk
    static int dcur77; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk77; // best direction to take now if the last move was a walk
    static Direction dircur77; // best direction to take now if the last move was a current
    static boolean p77; // is the location passable
    static Direction c77; // direction of the current at the location

    static MapLocation l92; // location representing relative coordinate (-1, 3)
    static int dwalk92; // shortest distance to location from current location if the last move was a walk
    static int dcur92; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk92; // best direction to take now if the last move was a walk
    static Direction dircur92; // best direction to take now if the last move was a current
    static boolean p92; // is the location passable
    static Direction c92; // direction of the current at the location

    static MapLocation l3; // location representing relative coordinate (0, -3)
    static int dwalk3; // shortest distance to location from current location if the last move was a walk
    static int dcur3; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk3; // best direction to take now if the last move was a walk
    static Direction dircur3; // best direction to take now if the last move was a current
    static boolean p3; // is the location passable
    static Direction c3; // direction of the current at the location

    static MapLocation l18; // location representing relative coordinate (0, -2)
    static int dwalk18; // shortest distance to location from current location if the last move was a walk
    static int dcur18; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk18; // best direction to take now if the last move was a walk
    static Direction dircur18; // best direction to take now if the last move was a current
    static boolean p18; // is the location passable
    static Direction c18; // direction of the current at the location

    static MapLocation l33; // location representing relative coordinate (0, -1)
    static int dwalk33; // shortest distance to location from current location if the last move was a walk
    static int dcur33; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk33; // best direction to take now if the last move was a walk
    static Direction dircur33; // best direction to take now if the last move was a current
    static boolean p33; // is the location passable
    static Direction c33; // direction of the current at the location

    static MapLocation l48; // location representing relative coordinate (0, 0)
    static int dwalk48; // shortest distance to location from current location if the last move was a walk
    static int dcur48; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk48; // best direction to take now if the last move was a walk
    static Direction dircur48; // best direction to take now if the last move was a current
    static boolean p48; // is the location passable
    static Direction c48; // direction of the current at the location

    static MapLocation l63; // location representing relative coordinate (0, 1)
    static int dwalk63; // shortest distance to location from current location if the last move was a walk
    static int dcur63; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk63; // best direction to take now if the last move was a walk
    static Direction dircur63; // best direction to take now if the last move was a current
    static boolean p63; // is the location passable
    static Direction c63; // direction of the current at the location

    static MapLocation l78; // location representing relative coordinate (0, 2)
    static int dwalk78; // shortest distance to location from current location if the last move was a walk
    static int dcur78; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk78; // best direction to take now if the last move was a walk
    static Direction dircur78; // best direction to take now if the last move was a current
    static boolean p78; // is the location passable
    static Direction c78; // direction of the current at the location

    static MapLocation l93; // location representing relative coordinate (0, 3)
    static int dwalk93; // shortest distance to location from current location if the last move was a walk
    static int dcur93; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk93; // best direction to take now if the last move was a walk
    static Direction dircur93; // best direction to take now if the last move was a current
    static boolean p93; // is the location passable
    static Direction c93; // direction of the current at the location

    static MapLocation l4; // location representing relative coordinate (1, -3)
    static int dwalk4; // shortest distance to location from current location if the last move was a walk
    static int dcur4; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk4; // best direction to take now if the last move was a walk
    static Direction dircur4; // best direction to take now if the last move was a current
    static boolean p4; // is the location passable
    static Direction c4; // direction of the current at the location

    static MapLocation l19; // location representing relative coordinate (1, -2)
    static int dwalk19; // shortest distance to location from current location if the last move was a walk
    static int dcur19; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk19; // best direction to take now if the last move was a walk
    static Direction dircur19; // best direction to take now if the last move was a current
    static boolean p19; // is the location passable
    static Direction c19; // direction of the current at the location

    static MapLocation l34; // location representing relative coordinate (1, -1)
    static int dwalk34; // shortest distance to location from current location if the last move was a walk
    static int dcur34; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk34; // best direction to take now if the last move was a walk
    static Direction dircur34; // best direction to take now if the last move was a current
    static boolean p34; // is the location passable
    static Direction c34; // direction of the current at the location

    static MapLocation l49; // location representing relative coordinate (1, 0)
    static int dwalk49; // shortest distance to location from current location if the last move was a walk
    static int dcur49; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk49; // best direction to take now if the last move was a walk
    static Direction dircur49; // best direction to take now if the last move was a current
    static boolean p49; // is the location passable
    static Direction c49; // direction of the current at the location

    static MapLocation l64; // location representing relative coordinate (1, 1)
    static int dwalk64; // shortest distance to location from current location if the last move was a walk
    static int dcur64; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk64; // best direction to take now if the last move was a walk
    static Direction dircur64; // best direction to take now if the last move was a current
    static boolean p64; // is the location passable
    static Direction c64; // direction of the current at the location

    static MapLocation l79; // location representing relative coordinate (1, 2)
    static int dwalk79; // shortest distance to location from current location if the last move was a walk
    static int dcur79; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk79; // best direction to take now if the last move was a walk
    static Direction dircur79; // best direction to take now if the last move was a current
    static boolean p79; // is the location passable
    static Direction c79; // direction of the current at the location

    static MapLocation l94; // location representing relative coordinate (1, 3)
    static int dwalk94; // shortest distance to location from current location if the last move was a walk
    static int dcur94; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk94; // best direction to take now if the last move was a walk
    static Direction dircur94; // best direction to take now if the last move was a current
    static boolean p94; // is the location passable
    static Direction c94; // direction of the current at the location

    static MapLocation l5; // location representing relative coordinate (2, -3)
    static int dwalk5; // shortest distance to location from current location if the last move was a walk
    static int dcur5; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk5; // best direction to take now if the last move was a walk
    static Direction dircur5; // best direction to take now if the last move was a current
    static boolean p5; // is the location passable
    static Direction c5; // direction of the current at the location

    static MapLocation l20; // location representing relative coordinate (2, -2)
    static int dwalk20; // shortest distance to location from current location if the last move was a walk
    static int dcur20; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk20; // best direction to take now if the last move was a walk
    static Direction dircur20; // best direction to take now if the last move was a current
    static boolean p20; // is the location passable
    static Direction c20; // direction of the current at the location

    static MapLocation l35; // location representing relative coordinate (2, -1)
    static int dwalk35; // shortest distance to location from current location if the last move was a walk
    static int dcur35; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk35; // best direction to take now if the last move was a walk
    static Direction dircur35; // best direction to take now if the last move was a current
    static boolean p35; // is the location passable
    static Direction c35; // direction of the current at the location

    static MapLocation l50; // location representing relative coordinate (2, 0)
    static int dwalk50; // shortest distance to location from current location if the last move was a walk
    static int dcur50; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk50; // best direction to take now if the last move was a walk
    static Direction dircur50; // best direction to take now if the last move was a current
    static boolean p50; // is the location passable
    static Direction c50; // direction of the current at the location

    static MapLocation l65; // location representing relative coordinate (2, 1)
    static int dwalk65; // shortest distance to location from current location if the last move was a walk
    static int dcur65; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk65; // best direction to take now if the last move was a walk
    static Direction dircur65; // best direction to take now if the last move was a current
    static boolean p65; // is the location passable
    static Direction c65; // direction of the current at the location

    static MapLocation l80; // location representing relative coordinate (2, 2)
    static int dwalk80; // shortest distance to location from current location if the last move was a walk
    static int dcur80; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk80; // best direction to take now if the last move was a walk
    static Direction dircur80; // best direction to take now if the last move was a current
    static boolean p80; // is the location passable
    static Direction c80; // direction of the current at the location

    static MapLocation l95; // location representing relative coordinate (2, 3)
    static int dwalk95; // shortest distance to location from current location if the last move was a walk
    static int dcur95; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk95; // best direction to take now if the last move was a walk
    static Direction dircur95; // best direction to take now if the last move was a current
    static boolean p95; // is the location passable
    static Direction c95; // direction of the current at the location

    static MapLocation l21; // location representing relative coordinate (3, -2)
    static int dwalk21; // shortest distance to location from current location if the last move was a walk
    static int dcur21; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk21; // best direction to take now if the last move was a walk
    static Direction dircur21; // best direction to take now if the last move was a current
    static boolean p21; // is the location passable
    static Direction c21; // direction of the current at the location

    static MapLocation l36; // location representing relative coordinate (3, -1)
    static int dwalk36; // shortest distance to location from current location if the last move was a walk
    static int dcur36; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk36; // best direction to take now if the last move was a walk
    static Direction dircur36; // best direction to take now if the last move was a current
    static boolean p36; // is the location passable
    static Direction c36; // direction of the current at the location

    static MapLocation l51; // location representing relative coordinate (3, 0)
    static int dwalk51; // shortest distance to location from current location if the last move was a walk
    static int dcur51; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk51; // best direction to take now if the last move was a walk
    static Direction dircur51; // best direction to take now if the last move was a current
    static boolean p51; // is the location passable
    static Direction c51; // direction of the current at the location

    static MapLocation l66; // location representing relative coordinate (3, 1)
    static int dwalk66; // shortest distance to location from current location if the last move was a walk
    static int dcur66; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk66; // best direction to take now if the last move was a walk
    static Direction dircur66; // best direction to take now if the last move was a current
    static boolean p66; // is the location passable
    static Direction c66; // direction of the current at the location

    static MapLocation l81; // location representing relative coordinate (3, 2)
    static int dwalk81; // shortest distance to location from current location if the last move was a walk
    static int dcur81; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk81; // best direction to take now if the last move was a walk
    static Direction dircur81; // best direction to take now if the last move was a current
    static boolean p81; // is the location passable
    static Direction c81; // direction of the current at the location


    public BFSPathing(RobotController rc) {
        this.rc = rc;
    }

    public Direction getBestDirection(MapLocation target) throws GameActionException {

        l48 = rc.getLocation();
        dwalk48 = 0;
        dcur48 = 99999;
        dirwalk48 = Direction.CENTER;
        dircur48 = Direction.CENTER;

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
                // from (0, 0)
                if (c48 != Direction.CENTER) { // there was a current at the previous location
                    if (l48.add(c48).equals(l47)) { // the current flows into this location
                        if (dwalk48 < dcur47) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur47 = dwalk48;
                            dircur47 = Direction.WEST;
                        }
                        if (dcur48 + 1 < dcur47) { // I can wait on the current till the end of the turn
                            dcur47 = dcur48 + 1;
                            dircur47 = Direction.WEST;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk48 + 1 < dwalk47) { // I can walk twice in a row since there was no current
                        dwalk47 = dwalk48 + 1;
                        dirwalk47 = Direction.WEST;
                    }
                }
                if (dcur48 + 1 < dwalk47) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk47 = dcur48 + 1;
                    dirwalk47 = Direction.WEST;
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
                // from (0, 0)
                if (c48 != Direction.CENTER) { // there was a current at the previous location
                    if (l48.add(c48).equals(l33)) { // the current flows into this location
                        if (dwalk48 < dcur33) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur33 = dwalk48;
                            dircur33 = Direction.SOUTH;
                        }
                        if (dcur48 + 1 < dcur33) { // I can wait on the current till the end of the turn
                            dcur33 = dcur48 + 1;
                            dircur33 = Direction.SOUTH;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk48 + 1 < dwalk33) { // I can walk twice in a row since there was no current
                        dwalk33 = dwalk48 + 1;
                        dirwalk33 = Direction.SOUTH;
                    }
                }
                if (dcur48 + 1 < dwalk33) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk33 = dcur48 + 1;
                    dirwalk33 = Direction.SOUTH;
                }
                
                // from (-1, 0)
                if (c47 != Direction.CENTER) { // there was a current at the previous location
                    if (l47.add(c47).equals(l33)) { // the current flows into this location
                        if (dwalk47 < dcur33) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur33 = dwalk47;
                            dircur33 = dirwalk47;
                        }
                        if (dcur47 + 1 < dcur33) { // I can wait on the current till the end of the turn
                            dcur33 = dcur47 + 1;
                            dircur33 = dircur47;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk47 + 1 < dwalk33) { // I can walk twice in a row since there was no current
                        dwalk33 = dwalk47 + 1;
                        dirwalk33 = dirwalk47;
                    }
                }
                if (dcur47 + 1 < dwalk33) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk33 = dcur47 + 1;
                    dirwalk33 = dircur47;
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
                // from (0, 0)
                if (c48 != Direction.CENTER) { // there was a current at the previous location
                    if (l48.add(c48).equals(l63)) { // the current flows into this location
                        if (dwalk48 < dcur63) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur63 = dwalk48;
                            dircur63 = Direction.NORTH;
                        }
                        if (dcur48 + 1 < dcur63) { // I can wait on the current till the end of the turn
                            dcur63 = dcur48 + 1;
                            dircur63 = Direction.NORTH;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk48 + 1 < dwalk63) { // I can walk twice in a row since there was no current
                        dwalk63 = dwalk48 + 1;
                        dirwalk63 = Direction.NORTH;
                    }
                }
                if (dcur48 + 1 < dwalk63) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk63 = dcur48 + 1;
                    dirwalk63 = Direction.NORTH;
                }
                
                // from (-1, 0)
                if (c47 != Direction.CENTER) { // there was a current at the previous location
                    if (l47.add(c47).equals(l63)) { // the current flows into this location
                        if (dwalk47 < dcur63) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur63 = dwalk47;
                            dircur63 = dirwalk47;
                        }
                        if (dcur47 + 1 < dcur63) { // I can wait on the current till the end of the turn
                            dcur63 = dcur47 + 1;
                            dircur63 = dircur47;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk47 + 1 < dwalk63) { // I can walk twice in a row since there was no current
                        dwalk63 = dwalk47 + 1;
                        dirwalk63 = dirwalk47;
                    }
                }
                if (dcur47 + 1 < dwalk63) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk63 = dcur47 + 1;
                    dirwalk63 = dircur47;
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
                // from (0, 0)
                if (c48 != Direction.CENTER) { // there was a current at the previous location
                    if (l48.add(c48).equals(l49)) { // the current flows into this location
                        if (dwalk48 < dcur49) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur49 = dwalk48;
                            dircur49 = Direction.EAST;
                        }
                        if (dcur48 + 1 < dcur49) { // I can wait on the current till the end of the turn
                            dcur49 = dcur48 + 1;
                            dircur49 = Direction.EAST;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk48 + 1 < dwalk49) { // I can walk twice in a row since there was no current
                        dwalk49 = dwalk48 + 1;
                        dirwalk49 = Direction.EAST;
                    }
                }
                if (dcur48 + 1 < dwalk49) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk49 = dcur48 + 1;
                    dirwalk49 = Direction.EAST;
                }
                
                // from (0, -1)
                if (c33 != Direction.CENTER) { // there was a current at the previous location
                    if (l33.add(c33).equals(l49)) { // the current flows into this location
                        if (dwalk33 < dcur49) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur49 = dwalk33;
                            dircur49 = dirwalk33;
                        }
                        if (dcur33 + 1 < dcur49) { // I can wait on the current till the end of the turn
                            dcur49 = dcur33 + 1;
                            dircur49 = dircur33;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk33 + 1 < dwalk49) { // I can walk twice in a row since there was no current
                        dwalk49 = dwalk33 + 1;
                        dirwalk49 = dirwalk33;
                    }
                }
                if (dcur33 + 1 < dwalk49) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk49 = dcur33 + 1;
                    dirwalk49 = dircur33;
                }
                
                // from (0, 1)
                if (c63 != Direction.CENTER) { // there was a current at the previous location
                    if (l63.add(c63).equals(l49)) { // the current flows into this location
                        if (dwalk63 < dcur49) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur49 = dwalk63;
                            dircur49 = dirwalk63;
                        }
                        if (dcur63 + 1 < dcur49) { // I can wait on the current till the end of the turn
                            dcur49 = dcur63 + 1;
                            dircur49 = dircur63;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk63 + 1 < dwalk49) { // I can walk twice in a row since there was no current
                        dwalk49 = dwalk63 + 1;
                        dirwalk49 = dirwalk63;
                    }
                }
                if (dcur63 + 1 < dwalk49) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk49 = dcur63 + 1;
                    dirwalk49 = dircur63;
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
                // from (0, 0)
                if (c48 != Direction.CENTER) { // there was a current at the previous location
                    if (l48.add(c48).equals(l32)) { // the current flows into this location
                        if (dwalk48 < dcur32) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur32 = dwalk48;
                            dircur32 = Direction.SOUTHWEST;
                        }
                        if (dcur48 + 1 < dcur32) { // I can wait on the current till the end of the turn
                            dcur32 = dcur48 + 1;
                            dircur32 = Direction.SOUTHWEST;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk48 + 1 < dwalk32) { // I can walk twice in a row since there was no current
                        dwalk32 = dwalk48 + 1;
                        dirwalk32 = Direction.SOUTHWEST;
                    }
                }
                if (dcur48 + 1 < dwalk32) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk32 = dcur48 + 1;
                    dirwalk32 = Direction.SOUTHWEST;
                }
                
                // from (-1, 0)
                if (c47 != Direction.CENTER) { // there was a current at the previous location
                    if (l47.add(c47).equals(l32)) { // the current flows into this location
                        if (dwalk47 < dcur32) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur32 = dwalk47;
                            dircur32 = dirwalk47;
                        }
                        if (dcur47 + 1 < dcur32) { // I can wait on the current till the end of the turn
                            dcur32 = dcur47 + 1;
                            dircur32 = dircur47;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk47 + 1 < dwalk32) { // I can walk twice in a row since there was no current
                        dwalk32 = dwalk47 + 1;
                        dirwalk32 = dirwalk47;
                    }
                }
                if (dcur47 + 1 < dwalk32) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk32 = dcur47 + 1;
                    dirwalk32 = dircur47;
                }
                
                // from (0, -1)
                if (c33 != Direction.CENTER) { // there was a current at the previous location
                    if (l33.add(c33).equals(l32)) { // the current flows into this location
                        if (dwalk33 < dcur32) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur32 = dwalk33;
                            dircur32 = dirwalk33;
                        }
                        if (dcur33 + 1 < dcur32) { // I can wait on the current till the end of the turn
                            dcur32 = dcur33 + 1;
                            dircur32 = dircur33;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk33 + 1 < dwalk32) { // I can walk twice in a row since there was no current
                        dwalk32 = dwalk33 + 1;
                        dirwalk32 = dirwalk33;
                    }
                }
                if (dcur33 + 1 < dwalk32) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk32 = dcur33 + 1;
                    dirwalk32 = dircur33;
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
                // from (0, 0)
                if (c48 != Direction.CENTER) { // there was a current at the previous location
                    if (l48.add(c48).equals(l62)) { // the current flows into this location
                        if (dwalk48 < dcur62) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur62 = dwalk48;
                            dircur62 = Direction.NORTHWEST;
                        }
                        if (dcur48 + 1 < dcur62) { // I can wait on the current till the end of the turn
                            dcur62 = dcur48 + 1;
                            dircur62 = Direction.NORTHWEST;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk48 + 1 < dwalk62) { // I can walk twice in a row since there was no current
                        dwalk62 = dwalk48 + 1;
                        dirwalk62 = Direction.NORTHWEST;
                    }
                }
                if (dcur48 + 1 < dwalk62) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk62 = dcur48 + 1;
                    dirwalk62 = Direction.NORTHWEST;
                }
                
                // from (-1, 0)
                if (c47 != Direction.CENTER) { // there was a current at the previous location
                    if (l47.add(c47).equals(l62)) { // the current flows into this location
                        if (dwalk47 < dcur62) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur62 = dwalk47;
                            dircur62 = dirwalk47;
                        }
                        if (dcur47 + 1 < dcur62) { // I can wait on the current till the end of the turn
                            dcur62 = dcur47 + 1;
                            dircur62 = dircur47;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk47 + 1 < dwalk62) { // I can walk twice in a row since there was no current
                        dwalk62 = dwalk47 + 1;
                        dirwalk62 = dirwalk47;
                    }
                }
                if (dcur47 + 1 < dwalk62) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk62 = dcur47 + 1;
                    dirwalk62 = dircur47;
                }
                
                // from (0, 1)
                if (c63 != Direction.CENTER) { // there was a current at the previous location
                    if (l63.add(c63).equals(l62)) { // the current flows into this location
                        if (dwalk63 < dcur62) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur62 = dwalk63;
                            dircur62 = dirwalk63;
                        }
                        if (dcur63 + 1 < dcur62) { // I can wait on the current till the end of the turn
                            dcur62 = dcur63 + 1;
                            dircur62 = dircur63;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk63 + 1 < dwalk62) { // I can walk twice in a row since there was no current
                        dwalk62 = dwalk63 + 1;
                        dirwalk62 = dirwalk63;
                    }
                }
                if (dcur63 + 1 < dwalk62) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk62 = dcur63 + 1;
                    dirwalk62 = dircur63;
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
                // from (0, 0)
                if (c48 != Direction.CENTER) { // there was a current at the previous location
                    if (l48.add(c48).equals(l34)) { // the current flows into this location
                        if (dwalk48 < dcur34) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur34 = dwalk48;
                            dircur34 = Direction.SOUTHEAST;
                        }
                        if (dcur48 + 1 < dcur34) { // I can wait on the current till the end of the turn
                            dcur34 = dcur48 + 1;
                            dircur34 = Direction.SOUTHEAST;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk48 + 1 < dwalk34) { // I can walk twice in a row since there was no current
                        dwalk34 = dwalk48 + 1;
                        dirwalk34 = Direction.SOUTHEAST;
                    }
                }
                if (dcur48 + 1 < dwalk34) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk34 = dcur48 + 1;
                    dirwalk34 = Direction.SOUTHEAST;
                }
                
                // from (0, -1)
                if (c33 != Direction.CENTER) { // there was a current at the previous location
                    if (l33.add(c33).equals(l34)) { // the current flows into this location
                        if (dwalk33 < dcur34) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur34 = dwalk33;
                            dircur34 = dirwalk33;
                        }
                        if (dcur33 + 1 < dcur34) { // I can wait on the current till the end of the turn
                            dcur34 = dcur33 + 1;
                            dircur34 = dircur33;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk33 + 1 < dwalk34) { // I can walk twice in a row since there was no current
                        dwalk34 = dwalk33 + 1;
                        dirwalk34 = dirwalk33;
                    }
                }
                if (dcur33 + 1 < dwalk34) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk34 = dcur33 + 1;
                    dirwalk34 = dircur33;
                }
                
                // from (1, 0)
                if (c49 != Direction.CENTER) { // there was a current at the previous location
                    if (l49.add(c49).equals(l34)) { // the current flows into this location
                        if (dwalk49 < dcur34) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur34 = dwalk49;
                            dircur34 = dirwalk49;
                        }
                        if (dcur49 + 1 < dcur34) { // I can wait on the current till the end of the turn
                            dcur34 = dcur49 + 1;
                            dircur34 = dircur49;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk49 + 1 < dwalk34) { // I can walk twice in a row since there was no current
                        dwalk34 = dwalk49 + 1;
                        dirwalk34 = dirwalk49;
                    }
                }
                if (dcur49 + 1 < dwalk34) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk34 = dcur49 + 1;
                    dirwalk34 = dircur49;
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
                // from (0, 0)
                if (c48 != Direction.CENTER) { // there was a current at the previous location
                    if (l48.add(c48).equals(l64)) { // the current flows into this location
                        if (dwalk48 < dcur64) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur64 = dwalk48;
                            dircur64 = Direction.NORTHEAST;
                        }
                        if (dcur48 + 1 < dcur64) { // I can wait on the current till the end of the turn
                            dcur64 = dcur48 + 1;
                            dircur64 = Direction.NORTHEAST;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk48 + 1 < dwalk64) { // I can walk twice in a row since there was no current
                        dwalk64 = dwalk48 + 1;
                        dirwalk64 = Direction.NORTHEAST;
                    }
                }
                if (dcur48 + 1 < dwalk64) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk64 = dcur48 + 1;
                    dirwalk64 = Direction.NORTHEAST;
                }
                
                // from (0, 1)
                if (c63 != Direction.CENTER) { // there was a current at the previous location
                    if (l63.add(c63).equals(l64)) { // the current flows into this location
                        if (dwalk63 < dcur64) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur64 = dwalk63;
                            dircur64 = dirwalk63;
                        }
                        if (dcur63 + 1 < dcur64) { // I can wait on the current till the end of the turn
                            dcur64 = dcur63 + 1;
                            dircur64 = dircur63;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk63 + 1 < dwalk64) { // I can walk twice in a row since there was no current
                        dwalk64 = dwalk63 + 1;
                        dirwalk64 = dirwalk63;
                    }
                }
                if (dcur63 + 1 < dwalk64) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk64 = dcur63 + 1;
                    dirwalk64 = dircur63;
                }
                
                // from (1, 0)
                if (c49 != Direction.CENTER) { // there was a current at the previous location
                    if (l49.add(c49).equals(l64)) { // the current flows into this location
                        if (dwalk49 < dcur64) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur64 = dwalk49;
                            dircur64 = dirwalk49;
                        }
                        if (dcur49 + 1 < dcur64) { // I can wait on the current till the end of the turn
                            dcur64 = dcur49 + 1;
                            dircur64 = dircur49;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk49 + 1 < dwalk64) { // I can walk twice in a row since there was no current
                        dwalk64 = dwalk49 + 1;
                        dirwalk64 = dirwalk49;
                    }
                }
                if (dcur49 + 1 < dwalk64) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk64 = dcur49 + 1;
                    dirwalk64 = dircur49;
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
                if (c47 != Direction.CENTER) { // there was a current at the previous location
                    if (l47.add(c47).equals(l46)) { // the current flows into this location
                        if (dwalk47 < dcur46) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur46 = dwalk47;
                            dircur46 = dirwalk47;
                        }
                        if (dcur47 + 1 < dcur46) { // I can wait on the current till the end of the turn
                            dcur46 = dcur47 + 1;
                            dircur46 = dircur47;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk47 + 1 < dwalk46) { // I can walk twice in a row since there was no current
                        dwalk46 = dwalk47 + 1;
                        dirwalk46 = dirwalk47;
                    }
                }
                if (dcur47 + 1 < dwalk46) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk46 = dcur47 + 1;
                    dirwalk46 = dircur47;
                }
                
                // from (-1, -1)
                if (c32 != Direction.CENTER) { // there was a current at the previous location
                    if (l32.add(c32).equals(l46)) { // the current flows into this location
                        if (dwalk32 < dcur46) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur46 = dwalk32;
                            dircur46 = dirwalk32;
                        }
                        if (dcur32 + 1 < dcur46) { // I can wait on the current till the end of the turn
                            dcur46 = dcur32 + 1;
                            dircur46 = dircur32;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk32 + 1 < dwalk46) { // I can walk twice in a row since there was no current
                        dwalk46 = dwalk32 + 1;
                        dirwalk46 = dirwalk32;
                    }
                }
                if (dcur32 + 1 < dwalk46) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk46 = dcur32 + 1;
                    dirwalk46 = dircur32;
                }
                
                // from (-1, 1)
                if (c62 != Direction.CENTER) { // there was a current at the previous location
                    if (l62.add(c62).equals(l46)) { // the current flows into this location
                        if (dwalk62 < dcur46) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur46 = dwalk62;
                            dircur46 = dirwalk62;
                        }
                        if (dcur62 + 1 < dcur46) { // I can wait on the current till the end of the turn
                            dcur46 = dcur62 + 1;
                            dircur46 = dircur62;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk62 + 1 < dwalk46) { // I can walk twice in a row since there was no current
                        dwalk46 = dwalk62 + 1;
                        dirwalk46 = dirwalk62;
                    }
                }
                if (dcur62 + 1 < dwalk46) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk46 = dcur62 + 1;
                    dirwalk46 = dircur62;
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
                if (c33 != Direction.CENTER) { // there was a current at the previous location
                    if (l33.add(c33).equals(l18)) { // the current flows into this location
                        if (dwalk33 < dcur18) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur18 = dwalk33;
                            dircur18 = dirwalk33;
                        }
                        if (dcur33 + 1 < dcur18) { // I can wait on the current till the end of the turn
                            dcur18 = dcur33 + 1;
                            dircur18 = dircur33;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk33 + 1 < dwalk18) { // I can walk twice in a row since there was no current
                        dwalk18 = dwalk33 + 1;
                        dirwalk18 = dirwalk33;
                    }
                }
                if (dcur33 + 1 < dwalk18) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk18 = dcur33 + 1;
                    dirwalk18 = dircur33;
                }
                
                // from (-1, -1)
                if (c32 != Direction.CENTER) { // there was a current at the previous location
                    if (l32.add(c32).equals(l18)) { // the current flows into this location
                        if (dwalk32 < dcur18) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur18 = dwalk32;
                            dircur18 = dirwalk32;
                        }
                        if (dcur32 + 1 < dcur18) { // I can wait on the current till the end of the turn
                            dcur18 = dcur32 + 1;
                            dircur18 = dircur32;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk32 + 1 < dwalk18) { // I can walk twice in a row since there was no current
                        dwalk18 = dwalk32 + 1;
                        dirwalk18 = dirwalk32;
                    }
                }
                if (dcur32 + 1 < dwalk18) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk18 = dcur32 + 1;
                    dirwalk18 = dircur32;
                }
                
                // from (1, -1)
                if (c34 != Direction.CENTER) { // there was a current at the previous location
                    if (l34.add(c34).equals(l18)) { // the current flows into this location
                        if (dwalk34 < dcur18) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur18 = dwalk34;
                            dircur18 = dirwalk34;
                        }
                        if (dcur34 + 1 < dcur18) { // I can wait on the current till the end of the turn
                            dcur18 = dcur34 + 1;
                            dircur18 = dircur34;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk34 + 1 < dwalk18) { // I can walk twice in a row since there was no current
                        dwalk18 = dwalk34 + 1;
                        dirwalk18 = dirwalk34;
                    }
                }
                if (dcur34 + 1 < dwalk18) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk18 = dcur34 + 1;
                    dirwalk18 = dircur34;
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
                if (c63 != Direction.CENTER) { // there was a current at the previous location
                    if (l63.add(c63).equals(l78)) { // the current flows into this location
                        if (dwalk63 < dcur78) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur78 = dwalk63;
                            dircur78 = dirwalk63;
                        }
                        if (dcur63 + 1 < dcur78) { // I can wait on the current till the end of the turn
                            dcur78 = dcur63 + 1;
                            dircur78 = dircur63;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk63 + 1 < dwalk78) { // I can walk twice in a row since there was no current
                        dwalk78 = dwalk63 + 1;
                        dirwalk78 = dirwalk63;
                    }
                }
                if (dcur63 + 1 < dwalk78) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk78 = dcur63 + 1;
                    dirwalk78 = dircur63;
                }
                
                // from (-1, 1)
                if (c62 != Direction.CENTER) { // there was a current at the previous location
                    if (l62.add(c62).equals(l78)) { // the current flows into this location
                        if (dwalk62 < dcur78) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur78 = dwalk62;
                            dircur78 = dirwalk62;
                        }
                        if (dcur62 + 1 < dcur78) { // I can wait on the current till the end of the turn
                            dcur78 = dcur62 + 1;
                            dircur78 = dircur62;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk62 + 1 < dwalk78) { // I can walk twice in a row since there was no current
                        dwalk78 = dwalk62 + 1;
                        dirwalk78 = dirwalk62;
                    }
                }
                if (dcur62 + 1 < dwalk78) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk78 = dcur62 + 1;
                    dirwalk78 = dircur62;
                }
                
                // from (1, 1)
                if (c64 != Direction.CENTER) { // there was a current at the previous location
                    if (l64.add(c64).equals(l78)) { // the current flows into this location
                        if (dwalk64 < dcur78) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur78 = dwalk64;
                            dircur78 = dirwalk64;
                        }
                        if (dcur64 + 1 < dcur78) { // I can wait on the current till the end of the turn
                            dcur78 = dcur64 + 1;
                            dircur78 = dircur64;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk64 + 1 < dwalk78) { // I can walk twice in a row since there was no current
                        dwalk78 = dwalk64 + 1;
                        dirwalk78 = dirwalk64;
                    }
                }
                if (dcur64 + 1 < dwalk78) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk78 = dcur64 + 1;
                    dirwalk78 = dircur64;
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
                if (c49 != Direction.CENTER) { // there was a current at the previous location
                    if (l49.add(c49).equals(l50)) { // the current flows into this location
                        if (dwalk49 < dcur50) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur50 = dwalk49;
                            dircur50 = dirwalk49;
                        }
                        if (dcur49 + 1 < dcur50) { // I can wait on the current till the end of the turn
                            dcur50 = dcur49 + 1;
                            dircur50 = dircur49;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk49 + 1 < dwalk50) { // I can walk twice in a row since there was no current
                        dwalk50 = dwalk49 + 1;
                        dirwalk50 = dirwalk49;
                    }
                }
                if (dcur49 + 1 < dwalk50) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk50 = dcur49 + 1;
                    dirwalk50 = dircur49;
                }
                
                // from (1, -1)
                if (c34 != Direction.CENTER) { // there was a current at the previous location
                    if (l34.add(c34).equals(l50)) { // the current flows into this location
                        if (dwalk34 < dcur50) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur50 = dwalk34;
                            dircur50 = dirwalk34;
                        }
                        if (dcur34 + 1 < dcur50) { // I can wait on the current till the end of the turn
                            dcur50 = dcur34 + 1;
                            dircur50 = dircur34;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk34 + 1 < dwalk50) { // I can walk twice in a row since there was no current
                        dwalk50 = dwalk34 + 1;
                        dirwalk50 = dirwalk34;
                    }
                }
                if (dcur34 + 1 < dwalk50) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk50 = dcur34 + 1;
                    dirwalk50 = dircur34;
                }
                
                // from (1, 1)
                if (c64 != Direction.CENTER) { // there was a current at the previous location
                    if (l64.add(c64).equals(l50)) { // the current flows into this location
                        if (dwalk64 < dcur50) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur50 = dwalk64;
                            dircur50 = dirwalk64;
                        }
                        if (dcur64 + 1 < dcur50) { // I can wait on the current till the end of the turn
                            dcur50 = dcur64 + 1;
                            dircur50 = dircur64;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk64 + 1 < dwalk50) { // I can walk twice in a row since there was no current
                        dwalk50 = dwalk64 + 1;
                        dirwalk50 = dirwalk64;
                    }
                }
                if (dcur64 + 1 < dwalk50) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk50 = dcur64 + 1;
                    dirwalk50 = dircur64;
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
                if (c47 != Direction.CENTER) { // there was a current at the previous location
                    if (l47.add(c47).equals(l31)) { // the current flows into this location
                        if (dwalk47 < dcur31) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur31 = dwalk47;
                            dircur31 = dirwalk47;
                        }
                        if (dcur47 + 1 < dcur31) { // I can wait on the current till the end of the turn
                            dcur31 = dcur47 + 1;
                            dircur31 = dircur47;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk47 + 1 < dwalk31) { // I can walk twice in a row since there was no current
                        dwalk31 = dwalk47 + 1;
                        dirwalk31 = dirwalk47;
                    }
                }
                if (dcur47 + 1 < dwalk31) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk31 = dcur47 + 1;
                    dirwalk31 = dircur47;
                }
                
                // from (-1, -1)
                if (c32 != Direction.CENTER) { // there was a current at the previous location
                    if (l32.add(c32).equals(l31)) { // the current flows into this location
                        if (dwalk32 < dcur31) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur31 = dwalk32;
                            dircur31 = dirwalk32;
                        }
                        if (dcur32 + 1 < dcur31) { // I can wait on the current till the end of the turn
                            dcur31 = dcur32 + 1;
                            dircur31 = dircur32;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk32 + 1 < dwalk31) { // I can walk twice in a row since there was no current
                        dwalk31 = dwalk32 + 1;
                        dirwalk31 = dirwalk32;
                    }
                }
                if (dcur32 + 1 < dwalk31) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk31 = dcur32 + 1;
                    dirwalk31 = dircur32;
                }
                
                // from (-2, 0)
                if (c46 != Direction.CENTER) { // there was a current at the previous location
                    if (l46.add(c46).equals(l31)) { // the current flows into this location
                        if (dwalk46 < dcur31) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur31 = dwalk46;
                            dircur31 = dirwalk46;
                        }
                        if (dcur46 + 1 < dcur31) { // I can wait on the current till the end of the turn
                            dcur31 = dcur46 + 1;
                            dircur31 = dircur46;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk46 + 1 < dwalk31) { // I can walk twice in a row since there was no current
                        dwalk31 = dwalk46 + 1;
                        dirwalk31 = dirwalk46;
                    }
                }
                if (dcur46 + 1 < dwalk31) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk31 = dcur46 + 1;
                    dirwalk31 = dircur46;
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
                if (c47 != Direction.CENTER) { // there was a current at the previous location
                    if (l47.add(c47).equals(l61)) { // the current flows into this location
                        if (dwalk47 < dcur61) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur61 = dwalk47;
                            dircur61 = dirwalk47;
                        }
                        if (dcur47 + 1 < dcur61) { // I can wait on the current till the end of the turn
                            dcur61 = dcur47 + 1;
                            dircur61 = dircur47;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk47 + 1 < dwalk61) { // I can walk twice in a row since there was no current
                        dwalk61 = dwalk47 + 1;
                        dirwalk61 = dirwalk47;
                    }
                }
                if (dcur47 + 1 < dwalk61) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk61 = dcur47 + 1;
                    dirwalk61 = dircur47;
                }
                
                // from (-1, 1)
                if (c62 != Direction.CENTER) { // there was a current at the previous location
                    if (l62.add(c62).equals(l61)) { // the current flows into this location
                        if (dwalk62 < dcur61) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur61 = dwalk62;
                            dircur61 = dirwalk62;
                        }
                        if (dcur62 + 1 < dcur61) { // I can wait on the current till the end of the turn
                            dcur61 = dcur62 + 1;
                            dircur61 = dircur62;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk62 + 1 < dwalk61) { // I can walk twice in a row since there was no current
                        dwalk61 = dwalk62 + 1;
                        dirwalk61 = dirwalk62;
                    }
                }
                if (dcur62 + 1 < dwalk61) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk61 = dcur62 + 1;
                    dirwalk61 = dircur62;
                }
                
                // from (-2, 0)
                if (c46 != Direction.CENTER) { // there was a current at the previous location
                    if (l46.add(c46).equals(l61)) { // the current flows into this location
                        if (dwalk46 < dcur61) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur61 = dwalk46;
                            dircur61 = dirwalk46;
                        }
                        if (dcur46 + 1 < dcur61) { // I can wait on the current till the end of the turn
                            dcur61 = dcur46 + 1;
                            dircur61 = dircur46;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk46 + 1 < dwalk61) { // I can walk twice in a row since there was no current
                        dwalk61 = dwalk46 + 1;
                        dirwalk61 = dirwalk46;
                    }
                }
                if (dcur46 + 1 < dwalk61) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk61 = dcur46 + 1;
                    dirwalk61 = dircur46;
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
                if (c33 != Direction.CENTER) { // there was a current at the previous location
                    if (l33.add(c33).equals(l17)) { // the current flows into this location
                        if (dwalk33 < dcur17) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur17 = dwalk33;
                            dircur17 = dirwalk33;
                        }
                        if (dcur33 + 1 < dcur17) { // I can wait on the current till the end of the turn
                            dcur17 = dcur33 + 1;
                            dircur17 = dircur33;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk33 + 1 < dwalk17) { // I can walk twice in a row since there was no current
                        dwalk17 = dwalk33 + 1;
                        dirwalk17 = dirwalk33;
                    }
                }
                if (dcur33 + 1 < dwalk17) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk17 = dcur33 + 1;
                    dirwalk17 = dircur33;
                }
                
                // from (-1, -1)
                if (c32 != Direction.CENTER) { // there was a current at the previous location
                    if (l32.add(c32).equals(l17)) { // the current flows into this location
                        if (dwalk32 < dcur17) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur17 = dwalk32;
                            dircur17 = dirwalk32;
                        }
                        if (dcur32 + 1 < dcur17) { // I can wait on the current till the end of the turn
                            dcur17 = dcur32 + 1;
                            dircur17 = dircur32;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk32 + 1 < dwalk17) { // I can walk twice in a row since there was no current
                        dwalk17 = dwalk32 + 1;
                        dirwalk17 = dirwalk32;
                    }
                }
                if (dcur32 + 1 < dwalk17) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk17 = dcur32 + 1;
                    dirwalk17 = dircur32;
                }
                
                // from (0, -2)
                if (c18 != Direction.CENTER) { // there was a current at the previous location
                    if (l18.add(c18).equals(l17)) { // the current flows into this location
                        if (dwalk18 < dcur17) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur17 = dwalk18;
                            dircur17 = dirwalk18;
                        }
                        if (dcur18 + 1 < dcur17) { // I can wait on the current till the end of the turn
                            dcur17 = dcur18 + 1;
                            dircur17 = dircur18;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk18 + 1 < dwalk17) { // I can walk twice in a row since there was no current
                        dwalk17 = dwalk18 + 1;
                        dirwalk17 = dirwalk18;
                    }
                }
                if (dcur18 + 1 < dwalk17) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk17 = dcur18 + 1;
                    dirwalk17 = dircur18;
                }
                
                // from (-2, -1)
                if (c31 != Direction.CENTER) { // there was a current at the previous location
                    if (l31.add(c31).equals(l17)) { // the current flows into this location
                        if (dwalk31 < dcur17) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur17 = dwalk31;
                            dircur17 = dirwalk31;
                        }
                        if (dcur31 + 1 < dcur17) { // I can wait on the current till the end of the turn
                            dcur17 = dcur31 + 1;
                            dircur17 = dircur31;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk31 + 1 < dwalk17) { // I can walk twice in a row since there was no current
                        dwalk17 = dwalk31 + 1;
                        dirwalk17 = dirwalk31;
                    }
                }
                if (dcur31 + 1 < dwalk17) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk17 = dcur31 + 1;
                    dirwalk17 = dircur31;
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
                if (c63 != Direction.CENTER) { // there was a current at the previous location
                    if (l63.add(c63).equals(l77)) { // the current flows into this location
                        if (dwalk63 < dcur77) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur77 = dwalk63;
                            dircur77 = dirwalk63;
                        }
                        if (dcur63 + 1 < dcur77) { // I can wait on the current till the end of the turn
                            dcur77 = dcur63 + 1;
                            dircur77 = dircur63;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk63 + 1 < dwalk77) { // I can walk twice in a row since there was no current
                        dwalk77 = dwalk63 + 1;
                        dirwalk77 = dirwalk63;
                    }
                }
                if (dcur63 + 1 < dwalk77) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk77 = dcur63 + 1;
                    dirwalk77 = dircur63;
                }
                
                // from (-1, 1)
                if (c62 != Direction.CENTER) { // there was a current at the previous location
                    if (l62.add(c62).equals(l77)) { // the current flows into this location
                        if (dwalk62 < dcur77) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur77 = dwalk62;
                            dircur77 = dirwalk62;
                        }
                        if (dcur62 + 1 < dcur77) { // I can wait on the current till the end of the turn
                            dcur77 = dcur62 + 1;
                            dircur77 = dircur62;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk62 + 1 < dwalk77) { // I can walk twice in a row since there was no current
                        dwalk77 = dwalk62 + 1;
                        dirwalk77 = dirwalk62;
                    }
                }
                if (dcur62 + 1 < dwalk77) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk77 = dcur62 + 1;
                    dirwalk77 = dircur62;
                }
                
                // from (0, 2)
                if (c78 != Direction.CENTER) { // there was a current at the previous location
                    if (l78.add(c78).equals(l77)) { // the current flows into this location
                        if (dwalk78 < dcur77) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur77 = dwalk78;
                            dircur77 = dirwalk78;
                        }
                        if (dcur78 + 1 < dcur77) { // I can wait on the current till the end of the turn
                            dcur77 = dcur78 + 1;
                            dircur77 = dircur78;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk78 + 1 < dwalk77) { // I can walk twice in a row since there was no current
                        dwalk77 = dwalk78 + 1;
                        dirwalk77 = dirwalk78;
                    }
                }
                if (dcur78 + 1 < dwalk77) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk77 = dcur78 + 1;
                    dirwalk77 = dircur78;
                }
                
                // from (-2, 1)
                if (c61 != Direction.CENTER) { // there was a current at the previous location
                    if (l61.add(c61).equals(l77)) { // the current flows into this location
                        if (dwalk61 < dcur77) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur77 = dwalk61;
                            dircur77 = dirwalk61;
                        }
                        if (dcur61 + 1 < dcur77) { // I can wait on the current till the end of the turn
                            dcur77 = dcur61 + 1;
                            dircur77 = dircur61;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk61 + 1 < dwalk77) { // I can walk twice in a row since there was no current
                        dwalk77 = dwalk61 + 1;
                        dirwalk77 = dirwalk61;
                    }
                }
                if (dcur61 + 1 < dwalk77) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk77 = dcur61 + 1;
                    dirwalk77 = dircur61;
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
                if (c33 != Direction.CENTER) { // there was a current at the previous location
                    if (l33.add(c33).equals(l19)) { // the current flows into this location
                        if (dwalk33 < dcur19) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur19 = dwalk33;
                            dircur19 = dirwalk33;
                        }
                        if (dcur33 + 1 < dcur19) { // I can wait on the current till the end of the turn
                            dcur19 = dcur33 + 1;
                            dircur19 = dircur33;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk33 + 1 < dwalk19) { // I can walk twice in a row since there was no current
                        dwalk19 = dwalk33 + 1;
                        dirwalk19 = dirwalk33;
                    }
                }
                if (dcur33 + 1 < dwalk19) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk19 = dcur33 + 1;
                    dirwalk19 = dircur33;
                }
                
                // from (1, -1)
                if (c34 != Direction.CENTER) { // there was a current at the previous location
                    if (l34.add(c34).equals(l19)) { // the current flows into this location
                        if (dwalk34 < dcur19) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur19 = dwalk34;
                            dircur19 = dirwalk34;
                        }
                        if (dcur34 + 1 < dcur19) { // I can wait on the current till the end of the turn
                            dcur19 = dcur34 + 1;
                            dircur19 = dircur34;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk34 + 1 < dwalk19) { // I can walk twice in a row since there was no current
                        dwalk19 = dwalk34 + 1;
                        dirwalk19 = dirwalk34;
                    }
                }
                if (dcur34 + 1 < dwalk19) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk19 = dcur34 + 1;
                    dirwalk19 = dircur34;
                }
                
                // from (0, -2)
                if (c18 != Direction.CENTER) { // there was a current at the previous location
                    if (l18.add(c18).equals(l19)) { // the current flows into this location
                        if (dwalk18 < dcur19) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur19 = dwalk18;
                            dircur19 = dirwalk18;
                        }
                        if (dcur18 + 1 < dcur19) { // I can wait on the current till the end of the turn
                            dcur19 = dcur18 + 1;
                            dircur19 = dircur18;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk18 + 1 < dwalk19) { // I can walk twice in a row since there was no current
                        dwalk19 = dwalk18 + 1;
                        dirwalk19 = dirwalk18;
                    }
                }
                if (dcur18 + 1 < dwalk19) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk19 = dcur18 + 1;
                    dirwalk19 = dircur18;
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
                if (c63 != Direction.CENTER) { // there was a current at the previous location
                    if (l63.add(c63).equals(l79)) { // the current flows into this location
                        if (dwalk63 < dcur79) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur79 = dwalk63;
                            dircur79 = dirwalk63;
                        }
                        if (dcur63 + 1 < dcur79) { // I can wait on the current till the end of the turn
                            dcur79 = dcur63 + 1;
                            dircur79 = dircur63;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk63 + 1 < dwalk79) { // I can walk twice in a row since there was no current
                        dwalk79 = dwalk63 + 1;
                        dirwalk79 = dirwalk63;
                    }
                }
                if (dcur63 + 1 < dwalk79) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk79 = dcur63 + 1;
                    dirwalk79 = dircur63;
                }
                
                // from (1, 1)
                if (c64 != Direction.CENTER) { // there was a current at the previous location
                    if (l64.add(c64).equals(l79)) { // the current flows into this location
                        if (dwalk64 < dcur79) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur79 = dwalk64;
                            dircur79 = dirwalk64;
                        }
                        if (dcur64 + 1 < dcur79) { // I can wait on the current till the end of the turn
                            dcur79 = dcur64 + 1;
                            dircur79 = dircur64;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk64 + 1 < dwalk79) { // I can walk twice in a row since there was no current
                        dwalk79 = dwalk64 + 1;
                        dirwalk79 = dirwalk64;
                    }
                }
                if (dcur64 + 1 < dwalk79) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk79 = dcur64 + 1;
                    dirwalk79 = dircur64;
                }
                
                // from (0, 2)
                if (c78 != Direction.CENTER) { // there was a current at the previous location
                    if (l78.add(c78).equals(l79)) { // the current flows into this location
                        if (dwalk78 < dcur79) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur79 = dwalk78;
                            dircur79 = dirwalk78;
                        }
                        if (dcur78 + 1 < dcur79) { // I can wait on the current till the end of the turn
                            dcur79 = dcur78 + 1;
                            dircur79 = dircur78;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk78 + 1 < dwalk79) { // I can walk twice in a row since there was no current
                        dwalk79 = dwalk78 + 1;
                        dirwalk79 = dirwalk78;
                    }
                }
                if (dcur78 + 1 < dwalk79) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk79 = dcur78 + 1;
                    dirwalk79 = dircur78;
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
                if (c49 != Direction.CENTER) { // there was a current at the previous location
                    if (l49.add(c49).equals(l35)) { // the current flows into this location
                        if (dwalk49 < dcur35) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur35 = dwalk49;
                            dircur35 = dirwalk49;
                        }
                        if (dcur49 + 1 < dcur35) { // I can wait on the current till the end of the turn
                            dcur35 = dcur49 + 1;
                            dircur35 = dircur49;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk49 + 1 < dwalk35) { // I can walk twice in a row since there was no current
                        dwalk35 = dwalk49 + 1;
                        dirwalk35 = dirwalk49;
                    }
                }
                if (dcur49 + 1 < dwalk35) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk35 = dcur49 + 1;
                    dirwalk35 = dircur49;
                }
                
                // from (1, -1)
                if (c34 != Direction.CENTER) { // there was a current at the previous location
                    if (l34.add(c34).equals(l35)) { // the current flows into this location
                        if (dwalk34 < dcur35) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur35 = dwalk34;
                            dircur35 = dirwalk34;
                        }
                        if (dcur34 + 1 < dcur35) { // I can wait on the current till the end of the turn
                            dcur35 = dcur34 + 1;
                            dircur35 = dircur34;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk34 + 1 < dwalk35) { // I can walk twice in a row since there was no current
                        dwalk35 = dwalk34 + 1;
                        dirwalk35 = dirwalk34;
                    }
                }
                if (dcur34 + 1 < dwalk35) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk35 = dcur34 + 1;
                    dirwalk35 = dircur34;
                }
                
                // from (2, 0)
                if (c50 != Direction.CENTER) { // there was a current at the previous location
                    if (l50.add(c50).equals(l35)) { // the current flows into this location
                        if (dwalk50 < dcur35) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur35 = dwalk50;
                            dircur35 = dirwalk50;
                        }
                        if (dcur50 + 1 < dcur35) { // I can wait on the current till the end of the turn
                            dcur35 = dcur50 + 1;
                            dircur35 = dircur50;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk50 + 1 < dwalk35) { // I can walk twice in a row since there was no current
                        dwalk35 = dwalk50 + 1;
                        dirwalk35 = dirwalk50;
                    }
                }
                if (dcur50 + 1 < dwalk35) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk35 = dcur50 + 1;
                    dirwalk35 = dircur50;
                }
                
                // from (1, -2)
                if (c19 != Direction.CENTER) { // there was a current at the previous location
                    if (l19.add(c19).equals(l35)) { // the current flows into this location
                        if (dwalk19 < dcur35) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur35 = dwalk19;
                            dircur35 = dirwalk19;
                        }
                        if (dcur19 + 1 < dcur35) { // I can wait on the current till the end of the turn
                            dcur35 = dcur19 + 1;
                            dircur35 = dircur19;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk19 + 1 < dwalk35) { // I can walk twice in a row since there was no current
                        dwalk35 = dwalk19 + 1;
                        dirwalk35 = dirwalk19;
                    }
                }
                if (dcur19 + 1 < dwalk35) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk35 = dcur19 + 1;
                    dirwalk35 = dircur19;
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
                if (c49 != Direction.CENTER) { // there was a current at the previous location
                    if (l49.add(c49).equals(l65)) { // the current flows into this location
                        if (dwalk49 < dcur65) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur65 = dwalk49;
                            dircur65 = dirwalk49;
                        }
                        if (dcur49 + 1 < dcur65) { // I can wait on the current till the end of the turn
                            dcur65 = dcur49 + 1;
                            dircur65 = dircur49;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk49 + 1 < dwalk65) { // I can walk twice in a row since there was no current
                        dwalk65 = dwalk49 + 1;
                        dirwalk65 = dirwalk49;
                    }
                }
                if (dcur49 + 1 < dwalk65) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk65 = dcur49 + 1;
                    dirwalk65 = dircur49;
                }
                
                // from (1, 1)
                if (c64 != Direction.CENTER) { // there was a current at the previous location
                    if (l64.add(c64).equals(l65)) { // the current flows into this location
                        if (dwalk64 < dcur65) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur65 = dwalk64;
                            dircur65 = dirwalk64;
                        }
                        if (dcur64 + 1 < dcur65) { // I can wait on the current till the end of the turn
                            dcur65 = dcur64 + 1;
                            dircur65 = dircur64;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk64 + 1 < dwalk65) { // I can walk twice in a row since there was no current
                        dwalk65 = dwalk64 + 1;
                        dirwalk65 = dirwalk64;
                    }
                }
                if (dcur64 + 1 < dwalk65) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk65 = dcur64 + 1;
                    dirwalk65 = dircur64;
                }
                
                // from (2, 0)
                if (c50 != Direction.CENTER) { // there was a current at the previous location
                    if (l50.add(c50).equals(l65)) { // the current flows into this location
                        if (dwalk50 < dcur65) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur65 = dwalk50;
                            dircur65 = dirwalk50;
                        }
                        if (dcur50 + 1 < dcur65) { // I can wait on the current till the end of the turn
                            dcur65 = dcur50 + 1;
                            dircur65 = dircur50;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk50 + 1 < dwalk65) { // I can walk twice in a row since there was no current
                        dwalk65 = dwalk50 + 1;
                        dirwalk65 = dirwalk50;
                    }
                }
                if (dcur50 + 1 < dwalk65) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk65 = dcur50 + 1;
                    dirwalk65 = dircur50;
                }
                
                // from (1, 2)
                if (c79 != Direction.CENTER) { // there was a current at the previous location
                    if (l79.add(c79).equals(l65)) { // the current flows into this location
                        if (dwalk79 < dcur65) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur65 = dwalk79;
                            dircur65 = dirwalk79;
                        }
                        if (dcur79 + 1 < dcur65) { // I can wait on the current till the end of the turn
                            dcur65 = dcur79 + 1;
                            dircur65 = dircur79;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk79 + 1 < dwalk65) { // I can walk twice in a row since there was no current
                        dwalk65 = dwalk79 + 1;
                        dirwalk65 = dirwalk79;
                    }
                }
                if (dcur79 + 1 < dwalk65) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk65 = dcur79 + 1;
                    dirwalk65 = dircur79;
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
                if (c32 != Direction.CENTER) { // there was a current at the previous location
                    if (l32.add(c32).equals(l16)) { // the current flows into this location
                        if (dwalk32 < dcur16) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur16 = dwalk32;
                            dircur16 = dirwalk32;
                        }
                        if (dcur32 + 1 < dcur16) { // I can wait on the current till the end of the turn
                            dcur16 = dcur32 + 1;
                            dircur16 = dircur32;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk32 + 1 < dwalk16) { // I can walk twice in a row since there was no current
                        dwalk16 = dwalk32 + 1;
                        dirwalk16 = dirwalk32;
                    }
                }
                if (dcur32 + 1 < dwalk16) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk16 = dcur32 + 1;
                    dirwalk16 = dircur32;
                }
                
                // from (-2, -1)
                if (c31 != Direction.CENTER) { // there was a current at the previous location
                    if (l31.add(c31).equals(l16)) { // the current flows into this location
                        if (dwalk31 < dcur16) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur16 = dwalk31;
                            dircur16 = dirwalk31;
                        }
                        if (dcur31 + 1 < dcur16) { // I can wait on the current till the end of the turn
                            dcur16 = dcur31 + 1;
                            dircur16 = dircur31;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk31 + 1 < dwalk16) { // I can walk twice in a row since there was no current
                        dwalk16 = dwalk31 + 1;
                        dirwalk16 = dirwalk31;
                    }
                }
                if (dcur31 + 1 < dwalk16) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk16 = dcur31 + 1;
                    dirwalk16 = dircur31;
                }
                
                // from (-1, -2)
                if (c17 != Direction.CENTER) { // there was a current at the previous location
                    if (l17.add(c17).equals(l16)) { // the current flows into this location
                        if (dwalk17 < dcur16) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur16 = dwalk17;
                            dircur16 = dirwalk17;
                        }
                        if (dcur17 + 1 < dcur16) { // I can wait on the current till the end of the turn
                            dcur16 = dcur17 + 1;
                            dircur16 = dircur17;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk17 + 1 < dwalk16) { // I can walk twice in a row since there was no current
                        dwalk16 = dwalk17 + 1;
                        dirwalk16 = dirwalk17;
                    }
                }
                if (dcur17 + 1 < dwalk16) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk16 = dcur17 + 1;
                    dirwalk16 = dircur17;
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
                if (c62 != Direction.CENTER) { // there was a current at the previous location
                    if (l62.add(c62).equals(l76)) { // the current flows into this location
                        if (dwalk62 < dcur76) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur76 = dwalk62;
                            dircur76 = dirwalk62;
                        }
                        if (dcur62 + 1 < dcur76) { // I can wait on the current till the end of the turn
                            dcur76 = dcur62 + 1;
                            dircur76 = dircur62;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk62 + 1 < dwalk76) { // I can walk twice in a row since there was no current
                        dwalk76 = dwalk62 + 1;
                        dirwalk76 = dirwalk62;
                    }
                }
                if (dcur62 + 1 < dwalk76) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk76 = dcur62 + 1;
                    dirwalk76 = dircur62;
                }
                
                // from (-2, 1)
                if (c61 != Direction.CENTER) { // there was a current at the previous location
                    if (l61.add(c61).equals(l76)) { // the current flows into this location
                        if (dwalk61 < dcur76) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur76 = dwalk61;
                            dircur76 = dirwalk61;
                        }
                        if (dcur61 + 1 < dcur76) { // I can wait on the current till the end of the turn
                            dcur76 = dcur61 + 1;
                            dircur76 = dircur61;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk61 + 1 < dwalk76) { // I can walk twice in a row since there was no current
                        dwalk76 = dwalk61 + 1;
                        dirwalk76 = dirwalk61;
                    }
                }
                if (dcur61 + 1 < dwalk76) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk76 = dcur61 + 1;
                    dirwalk76 = dircur61;
                }
                
                // from (-1, 2)
                if (c77 != Direction.CENTER) { // there was a current at the previous location
                    if (l77.add(c77).equals(l76)) { // the current flows into this location
                        if (dwalk77 < dcur76) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur76 = dwalk77;
                            dircur76 = dirwalk77;
                        }
                        if (dcur77 + 1 < dcur76) { // I can wait on the current till the end of the turn
                            dcur76 = dcur77 + 1;
                            dircur76 = dircur77;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk77 + 1 < dwalk76) { // I can walk twice in a row since there was no current
                        dwalk76 = dwalk77 + 1;
                        dirwalk76 = dirwalk77;
                    }
                }
                if (dcur77 + 1 < dwalk76) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk76 = dcur77 + 1;
                    dirwalk76 = dircur77;
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
                if (c34 != Direction.CENTER) { // there was a current at the previous location
                    if (l34.add(c34).equals(l20)) { // the current flows into this location
                        if (dwalk34 < dcur20) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur20 = dwalk34;
                            dircur20 = dirwalk34;
                        }
                        if (dcur34 + 1 < dcur20) { // I can wait on the current till the end of the turn
                            dcur20 = dcur34 + 1;
                            dircur20 = dircur34;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk34 + 1 < dwalk20) { // I can walk twice in a row since there was no current
                        dwalk20 = dwalk34 + 1;
                        dirwalk20 = dirwalk34;
                    }
                }
                if (dcur34 + 1 < dwalk20) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk20 = dcur34 + 1;
                    dirwalk20 = dircur34;
                }
                
                // from (1, -2)
                if (c19 != Direction.CENTER) { // there was a current at the previous location
                    if (l19.add(c19).equals(l20)) { // the current flows into this location
                        if (dwalk19 < dcur20) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur20 = dwalk19;
                            dircur20 = dirwalk19;
                        }
                        if (dcur19 + 1 < dcur20) { // I can wait on the current till the end of the turn
                            dcur20 = dcur19 + 1;
                            dircur20 = dircur19;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk19 + 1 < dwalk20) { // I can walk twice in a row since there was no current
                        dwalk20 = dwalk19 + 1;
                        dirwalk20 = dirwalk19;
                    }
                }
                if (dcur19 + 1 < dwalk20) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk20 = dcur19 + 1;
                    dirwalk20 = dircur19;
                }
                
                // from (2, -1)
                if (c35 != Direction.CENTER) { // there was a current at the previous location
                    if (l35.add(c35).equals(l20)) { // the current flows into this location
                        if (dwalk35 < dcur20) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur20 = dwalk35;
                            dircur20 = dirwalk35;
                        }
                        if (dcur35 + 1 < dcur20) { // I can wait on the current till the end of the turn
                            dcur20 = dcur35 + 1;
                            dircur20 = dircur35;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk35 + 1 < dwalk20) { // I can walk twice in a row since there was no current
                        dwalk20 = dwalk35 + 1;
                        dirwalk20 = dirwalk35;
                    }
                }
                if (dcur35 + 1 < dwalk20) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk20 = dcur35 + 1;
                    dirwalk20 = dircur35;
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
                if (c64 != Direction.CENTER) { // there was a current at the previous location
                    if (l64.add(c64).equals(l80)) { // the current flows into this location
                        if (dwalk64 < dcur80) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur80 = dwalk64;
                            dircur80 = dirwalk64;
                        }
                        if (dcur64 + 1 < dcur80) { // I can wait on the current till the end of the turn
                            dcur80 = dcur64 + 1;
                            dircur80 = dircur64;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk64 + 1 < dwalk80) { // I can walk twice in a row since there was no current
                        dwalk80 = dwalk64 + 1;
                        dirwalk80 = dirwalk64;
                    }
                }
                if (dcur64 + 1 < dwalk80) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk80 = dcur64 + 1;
                    dirwalk80 = dircur64;
                }
                
                // from (1, 2)
                if (c79 != Direction.CENTER) { // there was a current at the previous location
                    if (l79.add(c79).equals(l80)) { // the current flows into this location
                        if (dwalk79 < dcur80) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur80 = dwalk79;
                            dircur80 = dirwalk79;
                        }
                        if (dcur79 + 1 < dcur80) { // I can wait on the current till the end of the turn
                            dcur80 = dcur79 + 1;
                            dircur80 = dircur79;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk79 + 1 < dwalk80) { // I can walk twice in a row since there was no current
                        dwalk80 = dwalk79 + 1;
                        dirwalk80 = dirwalk79;
                    }
                }
                if (dcur79 + 1 < dwalk80) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk80 = dcur79 + 1;
                    dirwalk80 = dircur79;
                }
                
                // from (2, 1)
                if (c65 != Direction.CENTER) { // there was a current at the previous location
                    if (l65.add(c65).equals(l80)) { // the current flows into this location
                        if (dwalk65 < dcur80) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur80 = dwalk65;
                            dircur80 = dirwalk65;
                        }
                        if (dcur65 + 1 < dcur80) { // I can wait on the current till the end of the turn
                            dcur80 = dcur65 + 1;
                            dircur80 = dircur65;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk65 + 1 < dwalk80) { // I can walk twice in a row since there was no current
                        dwalk80 = dwalk65 + 1;
                        dirwalk80 = dirwalk65;
                    }
                }
                if (dcur65 + 1 < dwalk80) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk80 = dcur65 + 1;
                    dirwalk80 = dircur65;
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
                if (c46 != Direction.CENTER) { // there was a current at the previous location
                    if (l46.add(c46).equals(l45)) { // the current flows into this location
                        if (dwalk46 < dcur45) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur45 = dwalk46;
                            dircur45 = dirwalk46;
                        }
                        if (dcur46 + 1 < dcur45) { // I can wait on the current till the end of the turn
                            dcur45 = dcur46 + 1;
                            dircur45 = dircur46;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk46 + 1 < dwalk45) { // I can walk twice in a row since there was no current
                        dwalk45 = dwalk46 + 1;
                        dirwalk45 = dirwalk46;
                    }
                }
                if (dcur46 + 1 < dwalk45) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk45 = dcur46 + 1;
                    dirwalk45 = dircur46;
                }
                
                // from (-2, -1)
                if (c31 != Direction.CENTER) { // there was a current at the previous location
                    if (l31.add(c31).equals(l45)) { // the current flows into this location
                        if (dwalk31 < dcur45) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur45 = dwalk31;
                            dircur45 = dirwalk31;
                        }
                        if (dcur31 + 1 < dcur45) { // I can wait on the current till the end of the turn
                            dcur45 = dcur31 + 1;
                            dircur45 = dircur31;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk31 + 1 < dwalk45) { // I can walk twice in a row since there was no current
                        dwalk45 = dwalk31 + 1;
                        dirwalk45 = dirwalk31;
                    }
                }
                if (dcur31 + 1 < dwalk45) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk45 = dcur31 + 1;
                    dirwalk45 = dircur31;
                }
                
                // from (-2, 1)
                if (c61 != Direction.CENTER) { // there was a current at the previous location
                    if (l61.add(c61).equals(l45)) { // the current flows into this location
                        if (dwalk61 < dcur45) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur45 = dwalk61;
                            dircur45 = dirwalk61;
                        }
                        if (dcur61 + 1 < dcur45) { // I can wait on the current till the end of the turn
                            dcur45 = dcur61 + 1;
                            dircur45 = dircur61;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk61 + 1 < dwalk45) { // I can walk twice in a row since there was no current
                        dwalk45 = dwalk61 + 1;
                        dirwalk45 = dirwalk61;
                    }
                }
                if (dcur61 + 1 < dwalk45) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk45 = dcur61 + 1;
                    dirwalk45 = dircur61;
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
                if (c18 != Direction.CENTER) { // there was a current at the previous location
                    if (l18.add(c18).equals(l3)) { // the current flows into this location
                        if (dwalk18 < dcur3) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur3 = dwalk18;
                            dircur3 = dirwalk18;
                        }
                        if (dcur18 + 1 < dcur3) { // I can wait on the current till the end of the turn
                            dcur3 = dcur18 + 1;
                            dircur3 = dircur18;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk18 + 1 < dwalk3) { // I can walk twice in a row since there was no current
                        dwalk3 = dwalk18 + 1;
                        dirwalk3 = dirwalk18;
                    }
                }
                if (dcur18 + 1 < dwalk3) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk3 = dcur18 + 1;
                    dirwalk3 = dircur18;
                }
                
                // from (-1, -2)
                if (c17 != Direction.CENTER) { // there was a current at the previous location
                    if (l17.add(c17).equals(l3)) { // the current flows into this location
                        if (dwalk17 < dcur3) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur3 = dwalk17;
                            dircur3 = dirwalk17;
                        }
                        if (dcur17 + 1 < dcur3) { // I can wait on the current till the end of the turn
                            dcur3 = dcur17 + 1;
                            dircur3 = dircur17;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk17 + 1 < dwalk3) { // I can walk twice in a row since there was no current
                        dwalk3 = dwalk17 + 1;
                        dirwalk3 = dirwalk17;
                    }
                }
                if (dcur17 + 1 < dwalk3) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk3 = dcur17 + 1;
                    dirwalk3 = dircur17;
                }
                
                // from (1, -2)
                if (c19 != Direction.CENTER) { // there was a current at the previous location
                    if (l19.add(c19).equals(l3)) { // the current flows into this location
                        if (dwalk19 < dcur3) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur3 = dwalk19;
                            dircur3 = dirwalk19;
                        }
                        if (dcur19 + 1 < dcur3) { // I can wait on the current till the end of the turn
                            dcur3 = dcur19 + 1;
                            dircur3 = dircur19;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk19 + 1 < dwalk3) { // I can walk twice in a row since there was no current
                        dwalk3 = dwalk19 + 1;
                        dirwalk3 = dirwalk19;
                    }
                }
                if (dcur19 + 1 < dwalk3) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk3 = dcur19 + 1;
                    dirwalk3 = dircur19;
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
                if (c78 != Direction.CENTER) { // there was a current at the previous location
                    if (l78.add(c78).equals(l93)) { // the current flows into this location
                        if (dwalk78 < dcur93) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur93 = dwalk78;
                            dircur93 = dirwalk78;
                        }
                        if (dcur78 + 1 < dcur93) { // I can wait on the current till the end of the turn
                            dcur93 = dcur78 + 1;
                            dircur93 = dircur78;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk78 + 1 < dwalk93) { // I can walk twice in a row since there was no current
                        dwalk93 = dwalk78 + 1;
                        dirwalk93 = dirwalk78;
                    }
                }
                if (dcur78 + 1 < dwalk93) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk93 = dcur78 + 1;
                    dirwalk93 = dircur78;
                }
                
                // from (-1, 2)
                if (c77 != Direction.CENTER) { // there was a current at the previous location
                    if (l77.add(c77).equals(l93)) { // the current flows into this location
                        if (dwalk77 < dcur93) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur93 = dwalk77;
                            dircur93 = dirwalk77;
                        }
                        if (dcur77 + 1 < dcur93) { // I can wait on the current till the end of the turn
                            dcur93 = dcur77 + 1;
                            dircur93 = dircur77;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk77 + 1 < dwalk93) { // I can walk twice in a row since there was no current
                        dwalk93 = dwalk77 + 1;
                        dirwalk93 = dirwalk77;
                    }
                }
                if (dcur77 + 1 < dwalk93) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk93 = dcur77 + 1;
                    dirwalk93 = dircur77;
                }
                
                // from (1, 2)
                if (c79 != Direction.CENTER) { // there was a current at the previous location
                    if (l79.add(c79).equals(l93)) { // the current flows into this location
                        if (dwalk79 < dcur93) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur93 = dwalk79;
                            dircur93 = dirwalk79;
                        }
                        if (dcur79 + 1 < dcur93) { // I can wait on the current till the end of the turn
                            dcur93 = dcur79 + 1;
                            dircur93 = dircur79;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk79 + 1 < dwalk93) { // I can walk twice in a row since there was no current
                        dwalk93 = dwalk79 + 1;
                        dirwalk93 = dirwalk79;
                    }
                }
                if (dcur79 + 1 < dwalk93) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk93 = dcur79 + 1;
                    dirwalk93 = dircur79;
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
                if (c50 != Direction.CENTER) { // there was a current at the previous location
                    if (l50.add(c50).equals(l51)) { // the current flows into this location
                        if (dwalk50 < dcur51) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur51 = dwalk50;
                            dircur51 = dirwalk50;
                        }
                        if (dcur50 + 1 < dcur51) { // I can wait on the current till the end of the turn
                            dcur51 = dcur50 + 1;
                            dircur51 = dircur50;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk50 + 1 < dwalk51) { // I can walk twice in a row since there was no current
                        dwalk51 = dwalk50 + 1;
                        dirwalk51 = dirwalk50;
                    }
                }
                if (dcur50 + 1 < dwalk51) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk51 = dcur50 + 1;
                    dirwalk51 = dircur50;
                }
                
                // from (2, -1)
                if (c35 != Direction.CENTER) { // there was a current at the previous location
                    if (l35.add(c35).equals(l51)) { // the current flows into this location
                        if (dwalk35 < dcur51) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur51 = dwalk35;
                            dircur51 = dirwalk35;
                        }
                        if (dcur35 + 1 < dcur51) { // I can wait on the current till the end of the turn
                            dcur51 = dcur35 + 1;
                            dircur51 = dircur35;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk35 + 1 < dwalk51) { // I can walk twice in a row since there was no current
                        dwalk51 = dwalk35 + 1;
                        dirwalk51 = dirwalk35;
                    }
                }
                if (dcur35 + 1 < dwalk51) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk51 = dcur35 + 1;
                    dirwalk51 = dircur35;
                }
                
                // from (2, 1)
                if (c65 != Direction.CENTER) { // there was a current at the previous location
                    if (l65.add(c65).equals(l51)) { // the current flows into this location
                        if (dwalk65 < dcur51) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur51 = dwalk65;
                            dircur51 = dirwalk65;
                        }
                        if (dcur65 + 1 < dcur51) { // I can wait on the current till the end of the turn
                            dcur51 = dcur65 + 1;
                            dircur51 = dircur65;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk65 + 1 < dwalk51) { // I can walk twice in a row since there was no current
                        dwalk51 = dwalk65 + 1;
                        dirwalk51 = dirwalk65;
                    }
                }
                if (dcur65 + 1 < dwalk51) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk51 = dcur65 + 1;
                    dirwalk51 = dircur65;
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
                if (c46 != Direction.CENTER) { // there was a current at the previous location
                    if (l46.add(c46).equals(l30)) { // the current flows into this location
                        if (dwalk46 < dcur30) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur30 = dwalk46;
                            dircur30 = dirwalk46;
                        }
                        if (dcur46 + 1 < dcur30) { // I can wait on the current till the end of the turn
                            dcur30 = dcur46 + 1;
                            dircur30 = dircur46;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk46 + 1 < dwalk30) { // I can walk twice in a row since there was no current
                        dwalk30 = dwalk46 + 1;
                        dirwalk30 = dirwalk46;
                    }
                }
                if (dcur46 + 1 < dwalk30) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk30 = dcur46 + 1;
                    dirwalk30 = dircur46;
                }
                
                // from (-2, -1)
                if (c31 != Direction.CENTER) { // there was a current at the previous location
                    if (l31.add(c31).equals(l30)) { // the current flows into this location
                        if (dwalk31 < dcur30) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur30 = dwalk31;
                            dircur30 = dirwalk31;
                        }
                        if (dcur31 + 1 < dcur30) { // I can wait on the current till the end of the turn
                            dcur30 = dcur31 + 1;
                            dircur30 = dircur31;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk31 + 1 < dwalk30) { // I can walk twice in a row since there was no current
                        dwalk30 = dwalk31 + 1;
                        dirwalk30 = dirwalk31;
                    }
                }
                if (dcur31 + 1 < dwalk30) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk30 = dcur31 + 1;
                    dirwalk30 = dircur31;
                }
                
                // from (-2, -2)
                if (c16 != Direction.CENTER) { // there was a current at the previous location
                    if (l16.add(c16).equals(l30)) { // the current flows into this location
                        if (dwalk16 < dcur30) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur30 = dwalk16;
                            dircur30 = dirwalk16;
                        }
                        if (dcur16 + 1 < dcur30) { // I can wait on the current till the end of the turn
                            dcur30 = dcur16 + 1;
                            dircur30 = dircur16;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk16 + 1 < dwalk30) { // I can walk twice in a row since there was no current
                        dwalk30 = dwalk16 + 1;
                        dirwalk30 = dirwalk16;
                    }
                }
                if (dcur16 + 1 < dwalk30) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk30 = dcur16 + 1;
                    dirwalk30 = dircur16;
                }
                
                // from (-3, 0)
                if (c45 != Direction.CENTER) { // there was a current at the previous location
                    if (l45.add(c45).equals(l30)) { // the current flows into this location
                        if (dwalk45 < dcur30) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur30 = dwalk45;
                            dircur30 = dirwalk45;
                        }
                        if (dcur45 + 1 < dcur30) { // I can wait on the current till the end of the turn
                            dcur30 = dcur45 + 1;
                            dircur30 = dircur45;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk45 + 1 < dwalk30) { // I can walk twice in a row since there was no current
                        dwalk30 = dwalk45 + 1;
                        dirwalk30 = dirwalk45;
                    }
                }
                if (dcur45 + 1 < dwalk30) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk30 = dcur45 + 1;
                    dirwalk30 = dircur45;
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
                if (c46 != Direction.CENTER) { // there was a current at the previous location
                    if (l46.add(c46).equals(l60)) { // the current flows into this location
                        if (dwalk46 < dcur60) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur60 = dwalk46;
                            dircur60 = dirwalk46;
                        }
                        if (dcur46 + 1 < dcur60) { // I can wait on the current till the end of the turn
                            dcur60 = dcur46 + 1;
                            dircur60 = dircur46;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk46 + 1 < dwalk60) { // I can walk twice in a row since there was no current
                        dwalk60 = dwalk46 + 1;
                        dirwalk60 = dirwalk46;
                    }
                }
                if (dcur46 + 1 < dwalk60) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk60 = dcur46 + 1;
                    dirwalk60 = dircur46;
                }
                
                // from (-2, 1)
                if (c61 != Direction.CENTER) { // there was a current at the previous location
                    if (l61.add(c61).equals(l60)) { // the current flows into this location
                        if (dwalk61 < dcur60) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur60 = dwalk61;
                            dircur60 = dirwalk61;
                        }
                        if (dcur61 + 1 < dcur60) { // I can wait on the current till the end of the turn
                            dcur60 = dcur61 + 1;
                            dircur60 = dircur61;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk61 + 1 < dwalk60) { // I can walk twice in a row since there was no current
                        dwalk60 = dwalk61 + 1;
                        dirwalk60 = dirwalk61;
                    }
                }
                if (dcur61 + 1 < dwalk60) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk60 = dcur61 + 1;
                    dirwalk60 = dircur61;
                }
                
                // from (-2, 2)
                if (c76 != Direction.CENTER) { // there was a current at the previous location
                    if (l76.add(c76).equals(l60)) { // the current flows into this location
                        if (dwalk76 < dcur60) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur60 = dwalk76;
                            dircur60 = dirwalk76;
                        }
                        if (dcur76 + 1 < dcur60) { // I can wait on the current till the end of the turn
                            dcur60 = dcur76 + 1;
                            dircur60 = dircur76;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk76 + 1 < dwalk60) { // I can walk twice in a row since there was no current
                        dwalk60 = dwalk76 + 1;
                        dirwalk60 = dirwalk76;
                    }
                }
                if (dcur76 + 1 < dwalk60) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk60 = dcur76 + 1;
                    dirwalk60 = dircur76;
                }
                
                // from (-3, 0)
                if (c45 != Direction.CENTER) { // there was a current at the previous location
                    if (l45.add(c45).equals(l60)) { // the current flows into this location
                        if (dwalk45 < dcur60) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur60 = dwalk45;
                            dircur60 = dirwalk45;
                        }
                        if (dcur45 + 1 < dcur60) { // I can wait on the current till the end of the turn
                            dcur60 = dcur45 + 1;
                            dircur60 = dircur45;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk45 + 1 < dwalk60) { // I can walk twice in a row since there was no current
                        dwalk60 = dwalk45 + 1;
                        dirwalk60 = dirwalk45;
                    }
                }
                if (dcur45 + 1 < dwalk60) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk60 = dcur45 + 1;
                    dirwalk60 = dircur45;
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
                if (c18 != Direction.CENTER) { // there was a current at the previous location
                    if (l18.add(c18).equals(l2)) { // the current flows into this location
                        if (dwalk18 < dcur2) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur2 = dwalk18;
                            dircur2 = dirwalk18;
                        }
                        if (dcur18 + 1 < dcur2) { // I can wait on the current till the end of the turn
                            dcur2 = dcur18 + 1;
                            dircur2 = dircur18;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk18 + 1 < dwalk2) { // I can walk twice in a row since there was no current
                        dwalk2 = dwalk18 + 1;
                        dirwalk2 = dirwalk18;
                    }
                }
                if (dcur18 + 1 < dwalk2) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk2 = dcur18 + 1;
                    dirwalk2 = dircur18;
                }
                
                // from (-1, -2)
                if (c17 != Direction.CENTER) { // there was a current at the previous location
                    if (l17.add(c17).equals(l2)) { // the current flows into this location
                        if (dwalk17 < dcur2) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur2 = dwalk17;
                            dircur2 = dirwalk17;
                        }
                        if (dcur17 + 1 < dcur2) { // I can wait on the current till the end of the turn
                            dcur2 = dcur17 + 1;
                            dircur2 = dircur17;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk17 + 1 < dwalk2) { // I can walk twice in a row since there was no current
                        dwalk2 = dwalk17 + 1;
                        dirwalk2 = dirwalk17;
                    }
                }
                if (dcur17 + 1 < dwalk2) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk2 = dcur17 + 1;
                    dirwalk2 = dircur17;
                }
                
                // from (-2, -2)
                if (c16 != Direction.CENTER) { // there was a current at the previous location
                    if (l16.add(c16).equals(l2)) { // the current flows into this location
                        if (dwalk16 < dcur2) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur2 = dwalk16;
                            dircur2 = dirwalk16;
                        }
                        if (dcur16 + 1 < dcur2) { // I can wait on the current till the end of the turn
                            dcur2 = dcur16 + 1;
                            dircur2 = dircur16;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk16 + 1 < dwalk2) { // I can walk twice in a row since there was no current
                        dwalk2 = dwalk16 + 1;
                        dirwalk2 = dirwalk16;
                    }
                }
                if (dcur16 + 1 < dwalk2) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk2 = dcur16 + 1;
                    dirwalk2 = dircur16;
                }
                
                // from (0, -3)
                if (c3 != Direction.CENTER) { // there was a current at the previous location
                    if (l3.add(c3).equals(l2)) { // the current flows into this location
                        if (dwalk3 < dcur2) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur2 = dwalk3;
                            dircur2 = dirwalk3;
                        }
                        if (dcur3 + 1 < dcur2) { // I can wait on the current till the end of the turn
                            dcur2 = dcur3 + 1;
                            dircur2 = dircur3;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk3 + 1 < dwalk2) { // I can walk twice in a row since there was no current
                        dwalk2 = dwalk3 + 1;
                        dirwalk2 = dirwalk3;
                    }
                }
                if (dcur3 + 1 < dwalk2) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk2 = dcur3 + 1;
                    dirwalk2 = dircur3;
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
                if (c78 != Direction.CENTER) { // there was a current at the previous location
                    if (l78.add(c78).equals(l92)) { // the current flows into this location
                        if (dwalk78 < dcur92) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur92 = dwalk78;
                            dircur92 = dirwalk78;
                        }
                        if (dcur78 + 1 < dcur92) { // I can wait on the current till the end of the turn
                            dcur92 = dcur78 + 1;
                            dircur92 = dircur78;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk78 + 1 < dwalk92) { // I can walk twice in a row since there was no current
                        dwalk92 = dwalk78 + 1;
                        dirwalk92 = dirwalk78;
                    }
                }
                if (dcur78 + 1 < dwalk92) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk92 = dcur78 + 1;
                    dirwalk92 = dircur78;
                }
                
                // from (-1, 2)
                if (c77 != Direction.CENTER) { // there was a current at the previous location
                    if (l77.add(c77).equals(l92)) { // the current flows into this location
                        if (dwalk77 < dcur92) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur92 = dwalk77;
                            dircur92 = dirwalk77;
                        }
                        if (dcur77 + 1 < dcur92) { // I can wait on the current till the end of the turn
                            dcur92 = dcur77 + 1;
                            dircur92 = dircur77;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk77 + 1 < dwalk92) { // I can walk twice in a row since there was no current
                        dwalk92 = dwalk77 + 1;
                        dirwalk92 = dirwalk77;
                    }
                }
                if (dcur77 + 1 < dwalk92) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk92 = dcur77 + 1;
                    dirwalk92 = dircur77;
                }
                
                // from (-2, 2)
                if (c76 != Direction.CENTER) { // there was a current at the previous location
                    if (l76.add(c76).equals(l92)) { // the current flows into this location
                        if (dwalk76 < dcur92) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur92 = dwalk76;
                            dircur92 = dirwalk76;
                        }
                        if (dcur76 + 1 < dcur92) { // I can wait on the current till the end of the turn
                            dcur92 = dcur76 + 1;
                            dircur92 = dircur76;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk76 + 1 < dwalk92) { // I can walk twice in a row since there was no current
                        dwalk92 = dwalk76 + 1;
                        dirwalk92 = dirwalk76;
                    }
                }
                if (dcur76 + 1 < dwalk92) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk92 = dcur76 + 1;
                    dirwalk92 = dircur76;
                }
                
                // from (0, 3)
                if (c93 != Direction.CENTER) { // there was a current at the previous location
                    if (l93.add(c93).equals(l92)) { // the current flows into this location
                        if (dwalk93 < dcur92) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur92 = dwalk93;
                            dircur92 = dirwalk93;
                        }
                        if (dcur93 + 1 < dcur92) { // I can wait on the current till the end of the turn
                            dcur92 = dcur93 + 1;
                            dircur92 = dircur93;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk93 + 1 < dwalk92) { // I can walk twice in a row since there was no current
                        dwalk92 = dwalk93 + 1;
                        dirwalk92 = dirwalk93;
                    }
                }
                if (dcur93 + 1 < dwalk92) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk92 = dcur93 + 1;
                    dirwalk92 = dircur93;
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
                if (c18 != Direction.CENTER) { // there was a current at the previous location
                    if (l18.add(c18).equals(l4)) { // the current flows into this location
                        if (dwalk18 < dcur4) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur4 = dwalk18;
                            dircur4 = dirwalk18;
                        }
                        if (dcur18 + 1 < dcur4) { // I can wait on the current till the end of the turn
                            dcur4 = dcur18 + 1;
                            dircur4 = dircur18;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk18 + 1 < dwalk4) { // I can walk twice in a row since there was no current
                        dwalk4 = dwalk18 + 1;
                        dirwalk4 = dirwalk18;
                    }
                }
                if (dcur18 + 1 < dwalk4) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk4 = dcur18 + 1;
                    dirwalk4 = dircur18;
                }
                
                // from (1, -2)
                if (c19 != Direction.CENTER) { // there was a current at the previous location
                    if (l19.add(c19).equals(l4)) { // the current flows into this location
                        if (dwalk19 < dcur4) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur4 = dwalk19;
                            dircur4 = dirwalk19;
                        }
                        if (dcur19 + 1 < dcur4) { // I can wait on the current till the end of the turn
                            dcur4 = dcur19 + 1;
                            dircur4 = dircur19;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk19 + 1 < dwalk4) { // I can walk twice in a row since there was no current
                        dwalk4 = dwalk19 + 1;
                        dirwalk4 = dirwalk19;
                    }
                }
                if (dcur19 + 1 < dwalk4) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk4 = dcur19 + 1;
                    dirwalk4 = dircur19;
                }
                
                // from (2, -2)
                if (c20 != Direction.CENTER) { // there was a current at the previous location
                    if (l20.add(c20).equals(l4)) { // the current flows into this location
                        if (dwalk20 < dcur4) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur4 = dwalk20;
                            dircur4 = dirwalk20;
                        }
                        if (dcur20 + 1 < dcur4) { // I can wait on the current till the end of the turn
                            dcur4 = dcur20 + 1;
                            dircur4 = dircur20;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk20 + 1 < dwalk4) { // I can walk twice in a row since there was no current
                        dwalk4 = dwalk20 + 1;
                        dirwalk4 = dirwalk20;
                    }
                }
                if (dcur20 + 1 < dwalk4) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk4 = dcur20 + 1;
                    dirwalk4 = dircur20;
                }
                
                // from (0, -3)
                if (c3 != Direction.CENTER) { // there was a current at the previous location
                    if (l3.add(c3).equals(l4)) { // the current flows into this location
                        if (dwalk3 < dcur4) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur4 = dwalk3;
                            dircur4 = dirwalk3;
                        }
                        if (dcur3 + 1 < dcur4) { // I can wait on the current till the end of the turn
                            dcur4 = dcur3 + 1;
                            dircur4 = dircur3;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk3 + 1 < dwalk4) { // I can walk twice in a row since there was no current
                        dwalk4 = dwalk3 + 1;
                        dirwalk4 = dirwalk3;
                    }
                }
                if (dcur3 + 1 < dwalk4) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk4 = dcur3 + 1;
                    dirwalk4 = dircur3;
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
                if (c78 != Direction.CENTER) { // there was a current at the previous location
                    if (l78.add(c78).equals(l94)) { // the current flows into this location
                        if (dwalk78 < dcur94) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur94 = dwalk78;
                            dircur94 = dirwalk78;
                        }
                        if (dcur78 + 1 < dcur94) { // I can wait on the current till the end of the turn
                            dcur94 = dcur78 + 1;
                            dircur94 = dircur78;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk78 + 1 < dwalk94) { // I can walk twice in a row since there was no current
                        dwalk94 = dwalk78 + 1;
                        dirwalk94 = dirwalk78;
                    }
                }
                if (dcur78 + 1 < dwalk94) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk94 = dcur78 + 1;
                    dirwalk94 = dircur78;
                }
                
                // from (1, 2)
                if (c79 != Direction.CENTER) { // there was a current at the previous location
                    if (l79.add(c79).equals(l94)) { // the current flows into this location
                        if (dwalk79 < dcur94) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur94 = dwalk79;
                            dircur94 = dirwalk79;
                        }
                        if (dcur79 + 1 < dcur94) { // I can wait on the current till the end of the turn
                            dcur94 = dcur79 + 1;
                            dircur94 = dircur79;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk79 + 1 < dwalk94) { // I can walk twice in a row since there was no current
                        dwalk94 = dwalk79 + 1;
                        dirwalk94 = dirwalk79;
                    }
                }
                if (dcur79 + 1 < dwalk94) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk94 = dcur79 + 1;
                    dirwalk94 = dircur79;
                }
                
                // from (2, 2)
                if (c80 != Direction.CENTER) { // there was a current at the previous location
                    if (l80.add(c80).equals(l94)) { // the current flows into this location
                        if (dwalk80 < dcur94) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur94 = dwalk80;
                            dircur94 = dirwalk80;
                        }
                        if (dcur80 + 1 < dcur94) { // I can wait on the current till the end of the turn
                            dcur94 = dcur80 + 1;
                            dircur94 = dircur80;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk80 + 1 < dwalk94) { // I can walk twice in a row since there was no current
                        dwalk94 = dwalk80 + 1;
                        dirwalk94 = dirwalk80;
                    }
                }
                if (dcur80 + 1 < dwalk94) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk94 = dcur80 + 1;
                    dirwalk94 = dircur80;
                }
                
                // from (0, 3)
                if (c93 != Direction.CENTER) { // there was a current at the previous location
                    if (l93.add(c93).equals(l94)) { // the current flows into this location
                        if (dwalk93 < dcur94) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur94 = dwalk93;
                            dircur94 = dirwalk93;
                        }
                        if (dcur93 + 1 < dcur94) { // I can wait on the current till the end of the turn
                            dcur94 = dcur93 + 1;
                            dircur94 = dircur93;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk93 + 1 < dwalk94) { // I can walk twice in a row since there was no current
                        dwalk94 = dwalk93 + 1;
                        dirwalk94 = dirwalk93;
                    }
                }
                if (dcur93 + 1 < dwalk94) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk94 = dcur93 + 1;
                    dirwalk94 = dircur93;
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
                if (c50 != Direction.CENTER) { // there was a current at the previous location
                    if (l50.add(c50).equals(l36)) { // the current flows into this location
                        if (dwalk50 < dcur36) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur36 = dwalk50;
                            dircur36 = dirwalk50;
                        }
                        if (dcur50 + 1 < dcur36) { // I can wait on the current till the end of the turn
                            dcur36 = dcur50 + 1;
                            dircur36 = dircur50;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk50 + 1 < dwalk36) { // I can walk twice in a row since there was no current
                        dwalk36 = dwalk50 + 1;
                        dirwalk36 = dirwalk50;
                    }
                }
                if (dcur50 + 1 < dwalk36) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk36 = dcur50 + 1;
                    dirwalk36 = dircur50;
                }
                
                // from (2, -1)
                if (c35 != Direction.CENTER) { // there was a current at the previous location
                    if (l35.add(c35).equals(l36)) { // the current flows into this location
                        if (dwalk35 < dcur36) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur36 = dwalk35;
                            dircur36 = dirwalk35;
                        }
                        if (dcur35 + 1 < dcur36) { // I can wait on the current till the end of the turn
                            dcur36 = dcur35 + 1;
                            dircur36 = dircur35;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk35 + 1 < dwalk36) { // I can walk twice in a row since there was no current
                        dwalk36 = dwalk35 + 1;
                        dirwalk36 = dirwalk35;
                    }
                }
                if (dcur35 + 1 < dwalk36) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk36 = dcur35 + 1;
                    dirwalk36 = dircur35;
                }
                
                // from (2, -2)
                if (c20 != Direction.CENTER) { // there was a current at the previous location
                    if (l20.add(c20).equals(l36)) { // the current flows into this location
                        if (dwalk20 < dcur36) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur36 = dwalk20;
                            dircur36 = dirwalk20;
                        }
                        if (dcur20 + 1 < dcur36) { // I can wait on the current till the end of the turn
                            dcur36 = dcur20 + 1;
                            dircur36 = dircur20;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk20 + 1 < dwalk36) { // I can walk twice in a row since there was no current
                        dwalk36 = dwalk20 + 1;
                        dirwalk36 = dirwalk20;
                    }
                }
                if (dcur20 + 1 < dwalk36) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk36 = dcur20 + 1;
                    dirwalk36 = dircur20;
                }
                
                // from (3, 0)
                if (c51 != Direction.CENTER) { // there was a current at the previous location
                    if (l51.add(c51).equals(l36)) { // the current flows into this location
                        if (dwalk51 < dcur36) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur36 = dwalk51;
                            dircur36 = dirwalk51;
                        }
                        if (dcur51 + 1 < dcur36) { // I can wait on the current till the end of the turn
                            dcur36 = dcur51 + 1;
                            dircur36 = dircur51;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk51 + 1 < dwalk36) { // I can walk twice in a row since there was no current
                        dwalk36 = dwalk51 + 1;
                        dirwalk36 = dirwalk51;
                    }
                }
                if (dcur51 + 1 < dwalk36) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk36 = dcur51 + 1;
                    dirwalk36 = dircur51;
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
                if (c50 != Direction.CENTER) { // there was a current at the previous location
                    if (l50.add(c50).equals(l66)) { // the current flows into this location
                        if (dwalk50 < dcur66) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur66 = dwalk50;
                            dircur66 = dirwalk50;
                        }
                        if (dcur50 + 1 < dcur66) { // I can wait on the current till the end of the turn
                            dcur66 = dcur50 + 1;
                            dircur66 = dircur50;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk50 + 1 < dwalk66) { // I can walk twice in a row since there was no current
                        dwalk66 = dwalk50 + 1;
                        dirwalk66 = dirwalk50;
                    }
                }
                if (dcur50 + 1 < dwalk66) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk66 = dcur50 + 1;
                    dirwalk66 = dircur50;
                }
                
                // from (2, 1)
                if (c65 != Direction.CENTER) { // there was a current at the previous location
                    if (l65.add(c65).equals(l66)) { // the current flows into this location
                        if (dwalk65 < dcur66) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur66 = dwalk65;
                            dircur66 = dirwalk65;
                        }
                        if (dcur65 + 1 < dcur66) { // I can wait on the current till the end of the turn
                            dcur66 = dcur65 + 1;
                            dircur66 = dircur65;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk65 + 1 < dwalk66) { // I can walk twice in a row since there was no current
                        dwalk66 = dwalk65 + 1;
                        dirwalk66 = dirwalk65;
                    }
                }
                if (dcur65 + 1 < dwalk66) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk66 = dcur65 + 1;
                    dirwalk66 = dircur65;
                }
                
                // from (2, 2)
                if (c80 != Direction.CENTER) { // there was a current at the previous location
                    if (l80.add(c80).equals(l66)) { // the current flows into this location
                        if (dwalk80 < dcur66) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur66 = dwalk80;
                            dircur66 = dirwalk80;
                        }
                        if (dcur80 + 1 < dcur66) { // I can wait on the current till the end of the turn
                            dcur66 = dcur80 + 1;
                            dircur66 = dircur80;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk80 + 1 < dwalk66) { // I can walk twice in a row since there was no current
                        dwalk66 = dwalk80 + 1;
                        dirwalk66 = dirwalk80;
                    }
                }
                if (dcur80 + 1 < dwalk66) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk66 = dcur80 + 1;
                    dirwalk66 = dircur80;
                }
                
                // from (3, 0)
                if (c51 != Direction.CENTER) { // there was a current at the previous location
                    if (l51.add(c51).equals(l66)) { // the current flows into this location
                        if (dwalk51 < dcur66) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur66 = dwalk51;
                            dircur66 = dirwalk51;
                        }
                        if (dcur51 + 1 < dcur66) { // I can wait on the current till the end of the turn
                            dcur66 = dcur51 + 1;
                            dircur66 = dircur51;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk51 + 1 < dwalk66) { // I can walk twice in a row since there was no current
                        dwalk66 = dwalk51 + 1;
                        dirwalk66 = dirwalk51;
                    }
                }
                if (dcur51 + 1 < dwalk66) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk66 = dcur51 + 1;
                    dirwalk66 = dircur51;
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
                if (c31 != Direction.CENTER) { // there was a current at the previous location
                    if (l31.add(c31).equals(l15)) { // the current flows into this location
                        if (dwalk31 < dcur15) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur15 = dwalk31;
                            dircur15 = dirwalk31;
                        }
                        if (dcur31 + 1 < dcur15) { // I can wait on the current till the end of the turn
                            dcur15 = dcur31 + 1;
                            dircur15 = dircur31;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk31 + 1 < dwalk15) { // I can walk twice in a row since there was no current
                        dwalk15 = dwalk31 + 1;
                        dirwalk15 = dirwalk31;
                    }
                }
                if (dcur31 + 1 < dwalk15) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk15 = dcur31 + 1;
                    dirwalk15 = dircur31;
                }
                
                // from (-2, -2)
                if (c16 != Direction.CENTER) { // there was a current at the previous location
                    if (l16.add(c16).equals(l15)) { // the current flows into this location
                        if (dwalk16 < dcur15) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur15 = dwalk16;
                            dircur15 = dirwalk16;
                        }
                        if (dcur16 + 1 < dcur15) { // I can wait on the current till the end of the turn
                            dcur15 = dcur16 + 1;
                            dircur15 = dircur16;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk16 + 1 < dwalk15) { // I can walk twice in a row since there was no current
                        dwalk15 = dwalk16 + 1;
                        dirwalk15 = dirwalk16;
                    }
                }
                if (dcur16 + 1 < dwalk15) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk15 = dcur16 + 1;
                    dirwalk15 = dircur16;
                }
                
                // from (-3, -1)
                if (c30 != Direction.CENTER) { // there was a current at the previous location
                    if (l30.add(c30).equals(l15)) { // the current flows into this location
                        if (dwalk30 < dcur15) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur15 = dwalk30;
                            dircur15 = dirwalk30;
                        }
                        if (dcur30 + 1 < dcur15) { // I can wait on the current till the end of the turn
                            dcur15 = dcur30 + 1;
                            dircur15 = dircur30;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk30 + 1 < dwalk15) { // I can walk twice in a row since there was no current
                        dwalk15 = dwalk30 + 1;
                        dirwalk15 = dirwalk30;
                    }
                }
                if (dcur30 + 1 < dwalk15) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk15 = dcur30 + 1;
                    dirwalk15 = dircur30;
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
                if (c61 != Direction.CENTER) { // there was a current at the previous location
                    if (l61.add(c61).equals(l75)) { // the current flows into this location
                        if (dwalk61 < dcur75) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur75 = dwalk61;
                            dircur75 = dirwalk61;
                        }
                        if (dcur61 + 1 < dcur75) { // I can wait on the current till the end of the turn
                            dcur75 = dcur61 + 1;
                            dircur75 = dircur61;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk61 + 1 < dwalk75) { // I can walk twice in a row since there was no current
                        dwalk75 = dwalk61 + 1;
                        dirwalk75 = dirwalk61;
                    }
                }
                if (dcur61 + 1 < dwalk75) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk75 = dcur61 + 1;
                    dirwalk75 = dircur61;
                }
                
                // from (-2, 2)
                if (c76 != Direction.CENTER) { // there was a current at the previous location
                    if (l76.add(c76).equals(l75)) { // the current flows into this location
                        if (dwalk76 < dcur75) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur75 = dwalk76;
                            dircur75 = dirwalk76;
                        }
                        if (dcur76 + 1 < dcur75) { // I can wait on the current till the end of the turn
                            dcur75 = dcur76 + 1;
                            dircur75 = dircur76;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk76 + 1 < dwalk75) { // I can walk twice in a row since there was no current
                        dwalk75 = dwalk76 + 1;
                        dirwalk75 = dirwalk76;
                    }
                }
                if (dcur76 + 1 < dwalk75) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk75 = dcur76 + 1;
                    dirwalk75 = dircur76;
                }
                
                // from (-3, 1)
                if (c60 != Direction.CENTER) { // there was a current at the previous location
                    if (l60.add(c60).equals(l75)) { // the current flows into this location
                        if (dwalk60 < dcur75) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur75 = dwalk60;
                            dircur75 = dirwalk60;
                        }
                        if (dcur60 + 1 < dcur75) { // I can wait on the current till the end of the turn
                            dcur75 = dcur60 + 1;
                            dircur75 = dircur60;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk60 + 1 < dwalk75) { // I can walk twice in a row since there was no current
                        dwalk75 = dwalk60 + 1;
                        dirwalk75 = dirwalk60;
                    }
                }
                if (dcur60 + 1 < dwalk75) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk75 = dcur60 + 1;
                    dirwalk75 = dircur60;
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
                if (c17 != Direction.CENTER) { // there was a current at the previous location
                    if (l17.add(c17).equals(l1)) { // the current flows into this location
                        if (dwalk17 < dcur1) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur1 = dwalk17;
                            dircur1 = dirwalk17;
                        }
                        if (dcur17 + 1 < dcur1) { // I can wait on the current till the end of the turn
                            dcur1 = dcur17 + 1;
                            dircur1 = dircur17;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk17 + 1 < dwalk1) { // I can walk twice in a row since there was no current
                        dwalk1 = dwalk17 + 1;
                        dirwalk1 = dirwalk17;
                    }
                }
                if (dcur17 + 1 < dwalk1) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk1 = dcur17 + 1;
                    dirwalk1 = dircur17;
                }
                
                // from (-2, -2)
                if (c16 != Direction.CENTER) { // there was a current at the previous location
                    if (l16.add(c16).equals(l1)) { // the current flows into this location
                        if (dwalk16 < dcur1) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur1 = dwalk16;
                            dircur1 = dirwalk16;
                        }
                        if (dcur16 + 1 < dcur1) { // I can wait on the current till the end of the turn
                            dcur1 = dcur16 + 1;
                            dircur1 = dircur16;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk16 + 1 < dwalk1) { // I can walk twice in a row since there was no current
                        dwalk1 = dwalk16 + 1;
                        dirwalk1 = dirwalk16;
                    }
                }
                if (dcur16 + 1 < dwalk1) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk1 = dcur16 + 1;
                    dirwalk1 = dircur16;
                }
                
                // from (-1, -3)
                if (c2 != Direction.CENTER) { // there was a current at the previous location
                    if (l2.add(c2).equals(l1)) { // the current flows into this location
                        if (dwalk2 < dcur1) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur1 = dwalk2;
                            dircur1 = dirwalk2;
                        }
                        if (dcur2 + 1 < dcur1) { // I can wait on the current till the end of the turn
                            dcur1 = dcur2 + 1;
                            dircur1 = dircur2;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk2 + 1 < dwalk1) { // I can walk twice in a row since there was no current
                        dwalk1 = dwalk2 + 1;
                        dirwalk1 = dirwalk2;
                    }
                }
                if (dcur2 + 1 < dwalk1) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk1 = dcur2 + 1;
                    dirwalk1 = dircur2;
                }
                
                // from (-3, -2)
                if (c15 != Direction.CENTER) { // there was a current at the previous location
                    if (l15.add(c15).equals(l1)) { // the current flows into this location
                        if (dwalk15 < dcur1) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur1 = dwalk15;
                            dircur1 = dirwalk15;
                        }
                        if (dcur15 + 1 < dcur1) { // I can wait on the current till the end of the turn
                            dcur1 = dcur15 + 1;
                            dircur1 = dircur15;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk15 + 1 < dwalk1) { // I can walk twice in a row since there was no current
                        dwalk1 = dwalk15 + 1;
                        dirwalk1 = dirwalk15;
                    }
                }
                if (dcur15 + 1 < dwalk1) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk1 = dcur15 + 1;
                    dirwalk1 = dircur15;
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
                if (c77 != Direction.CENTER) { // there was a current at the previous location
                    if (l77.add(c77).equals(l91)) { // the current flows into this location
                        if (dwalk77 < dcur91) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur91 = dwalk77;
                            dircur91 = dirwalk77;
                        }
                        if (dcur77 + 1 < dcur91) { // I can wait on the current till the end of the turn
                            dcur91 = dcur77 + 1;
                            dircur91 = dircur77;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk77 + 1 < dwalk91) { // I can walk twice in a row since there was no current
                        dwalk91 = dwalk77 + 1;
                        dirwalk91 = dirwalk77;
                    }
                }
                if (dcur77 + 1 < dwalk91) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk91 = dcur77 + 1;
                    dirwalk91 = dircur77;
                }
                
                // from (-2, 2)
                if (c76 != Direction.CENTER) { // there was a current at the previous location
                    if (l76.add(c76).equals(l91)) { // the current flows into this location
                        if (dwalk76 < dcur91) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur91 = dwalk76;
                            dircur91 = dirwalk76;
                        }
                        if (dcur76 + 1 < dcur91) { // I can wait on the current till the end of the turn
                            dcur91 = dcur76 + 1;
                            dircur91 = dircur76;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk76 + 1 < dwalk91) { // I can walk twice in a row since there was no current
                        dwalk91 = dwalk76 + 1;
                        dirwalk91 = dirwalk76;
                    }
                }
                if (dcur76 + 1 < dwalk91) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk91 = dcur76 + 1;
                    dirwalk91 = dircur76;
                }
                
                // from (-1, 3)
                if (c92 != Direction.CENTER) { // there was a current at the previous location
                    if (l92.add(c92).equals(l91)) { // the current flows into this location
                        if (dwalk92 < dcur91) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur91 = dwalk92;
                            dircur91 = dirwalk92;
                        }
                        if (dcur92 + 1 < dcur91) { // I can wait on the current till the end of the turn
                            dcur91 = dcur92 + 1;
                            dircur91 = dircur92;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk92 + 1 < dwalk91) { // I can walk twice in a row since there was no current
                        dwalk91 = dwalk92 + 1;
                        dirwalk91 = dirwalk92;
                    }
                }
                if (dcur92 + 1 < dwalk91) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk91 = dcur92 + 1;
                    dirwalk91 = dircur92;
                }
                
                // from (-3, 2)
                if (c75 != Direction.CENTER) { // there was a current at the previous location
                    if (l75.add(c75).equals(l91)) { // the current flows into this location
                        if (dwalk75 < dcur91) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur91 = dwalk75;
                            dircur91 = dirwalk75;
                        }
                        if (dcur75 + 1 < dcur91) { // I can wait on the current till the end of the turn
                            dcur91 = dcur75 + 1;
                            dircur91 = dircur75;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk75 + 1 < dwalk91) { // I can walk twice in a row since there was no current
                        dwalk91 = dwalk75 + 1;
                        dirwalk91 = dirwalk75;
                    }
                }
                if (dcur75 + 1 < dwalk91) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk91 = dcur75 + 1;
                    dirwalk91 = dircur75;
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
                if (c19 != Direction.CENTER) { // there was a current at the previous location
                    if (l19.add(c19).equals(l5)) { // the current flows into this location
                        if (dwalk19 < dcur5) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur5 = dwalk19;
                            dircur5 = dirwalk19;
                        }
                        if (dcur19 + 1 < dcur5) { // I can wait on the current till the end of the turn
                            dcur5 = dcur19 + 1;
                            dircur5 = dircur19;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk19 + 1 < dwalk5) { // I can walk twice in a row since there was no current
                        dwalk5 = dwalk19 + 1;
                        dirwalk5 = dirwalk19;
                    }
                }
                if (dcur19 + 1 < dwalk5) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk5 = dcur19 + 1;
                    dirwalk5 = dircur19;
                }
                
                // from (2, -2)
                if (c20 != Direction.CENTER) { // there was a current at the previous location
                    if (l20.add(c20).equals(l5)) { // the current flows into this location
                        if (dwalk20 < dcur5) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur5 = dwalk20;
                            dircur5 = dirwalk20;
                        }
                        if (dcur20 + 1 < dcur5) { // I can wait on the current till the end of the turn
                            dcur5 = dcur20 + 1;
                            dircur5 = dircur20;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk20 + 1 < dwalk5) { // I can walk twice in a row since there was no current
                        dwalk5 = dwalk20 + 1;
                        dirwalk5 = dirwalk20;
                    }
                }
                if (dcur20 + 1 < dwalk5) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk5 = dcur20 + 1;
                    dirwalk5 = dircur20;
                }
                
                // from (1, -3)
                if (c4 != Direction.CENTER) { // there was a current at the previous location
                    if (l4.add(c4).equals(l5)) { // the current flows into this location
                        if (dwalk4 < dcur5) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur5 = dwalk4;
                            dircur5 = dirwalk4;
                        }
                        if (dcur4 + 1 < dcur5) { // I can wait on the current till the end of the turn
                            dcur5 = dcur4 + 1;
                            dircur5 = dircur4;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk4 + 1 < dwalk5) { // I can walk twice in a row since there was no current
                        dwalk5 = dwalk4 + 1;
                        dirwalk5 = dirwalk4;
                    }
                }
                if (dcur4 + 1 < dwalk5) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk5 = dcur4 + 1;
                    dirwalk5 = dircur4;
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
                if (c79 != Direction.CENTER) { // there was a current at the previous location
                    if (l79.add(c79).equals(l95)) { // the current flows into this location
                        if (dwalk79 < dcur95) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur95 = dwalk79;
                            dircur95 = dirwalk79;
                        }
                        if (dcur79 + 1 < dcur95) { // I can wait on the current till the end of the turn
                            dcur95 = dcur79 + 1;
                            dircur95 = dircur79;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk79 + 1 < dwalk95) { // I can walk twice in a row since there was no current
                        dwalk95 = dwalk79 + 1;
                        dirwalk95 = dirwalk79;
                    }
                }
                if (dcur79 + 1 < dwalk95) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk95 = dcur79 + 1;
                    dirwalk95 = dircur79;
                }
                
                // from (2, 2)
                if (c80 != Direction.CENTER) { // there was a current at the previous location
                    if (l80.add(c80).equals(l95)) { // the current flows into this location
                        if (dwalk80 < dcur95) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur95 = dwalk80;
                            dircur95 = dirwalk80;
                        }
                        if (dcur80 + 1 < dcur95) { // I can wait on the current till the end of the turn
                            dcur95 = dcur80 + 1;
                            dircur95 = dircur80;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk80 + 1 < dwalk95) { // I can walk twice in a row since there was no current
                        dwalk95 = dwalk80 + 1;
                        dirwalk95 = dirwalk80;
                    }
                }
                if (dcur80 + 1 < dwalk95) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk95 = dcur80 + 1;
                    dirwalk95 = dircur80;
                }
                
                // from (1, 3)
                if (c94 != Direction.CENTER) { // there was a current at the previous location
                    if (l94.add(c94).equals(l95)) { // the current flows into this location
                        if (dwalk94 < dcur95) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur95 = dwalk94;
                            dircur95 = dirwalk94;
                        }
                        if (dcur94 + 1 < dcur95) { // I can wait on the current till the end of the turn
                            dcur95 = dcur94 + 1;
                            dircur95 = dircur94;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk94 + 1 < dwalk95) { // I can walk twice in a row since there was no current
                        dwalk95 = dwalk94 + 1;
                        dirwalk95 = dirwalk94;
                    }
                }
                if (dcur94 + 1 < dwalk95) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk95 = dcur94 + 1;
                    dirwalk95 = dircur94;
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
                if (c35 != Direction.CENTER) { // there was a current at the previous location
                    if (l35.add(c35).equals(l21)) { // the current flows into this location
                        if (dwalk35 < dcur21) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur21 = dwalk35;
                            dircur21 = dirwalk35;
                        }
                        if (dcur35 + 1 < dcur21) { // I can wait on the current till the end of the turn
                            dcur21 = dcur35 + 1;
                            dircur21 = dircur35;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk35 + 1 < dwalk21) { // I can walk twice in a row since there was no current
                        dwalk21 = dwalk35 + 1;
                        dirwalk21 = dirwalk35;
                    }
                }
                if (dcur35 + 1 < dwalk21) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk21 = dcur35 + 1;
                    dirwalk21 = dircur35;
                }
                
                // from (2, -2)
                if (c20 != Direction.CENTER) { // there was a current at the previous location
                    if (l20.add(c20).equals(l21)) { // the current flows into this location
                        if (dwalk20 < dcur21) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur21 = dwalk20;
                            dircur21 = dirwalk20;
                        }
                        if (dcur20 + 1 < dcur21) { // I can wait on the current till the end of the turn
                            dcur21 = dcur20 + 1;
                            dircur21 = dircur20;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk20 + 1 < dwalk21) { // I can walk twice in a row since there was no current
                        dwalk21 = dwalk20 + 1;
                        dirwalk21 = dirwalk20;
                    }
                }
                if (dcur20 + 1 < dwalk21) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk21 = dcur20 + 1;
                    dirwalk21 = dircur20;
                }
                
                // from (3, -1)
                if (c36 != Direction.CENTER) { // there was a current at the previous location
                    if (l36.add(c36).equals(l21)) { // the current flows into this location
                        if (dwalk36 < dcur21) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur21 = dwalk36;
                            dircur21 = dirwalk36;
                        }
                        if (dcur36 + 1 < dcur21) { // I can wait on the current till the end of the turn
                            dcur21 = dcur36 + 1;
                            dircur21 = dircur36;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk36 + 1 < dwalk21) { // I can walk twice in a row since there was no current
                        dwalk21 = dwalk36 + 1;
                        dirwalk21 = dirwalk36;
                    }
                }
                if (dcur36 + 1 < dwalk21) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk21 = dcur36 + 1;
                    dirwalk21 = dircur36;
                }
                
                // from (2, -3)
                if (c5 != Direction.CENTER) { // there was a current at the previous location
                    if (l5.add(c5).equals(l21)) { // the current flows into this location
                        if (dwalk5 < dcur21) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur21 = dwalk5;
                            dircur21 = dirwalk5;
                        }
                        if (dcur5 + 1 < dcur21) { // I can wait on the current till the end of the turn
                            dcur21 = dcur5 + 1;
                            dircur21 = dircur5;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk5 + 1 < dwalk21) { // I can walk twice in a row since there was no current
                        dwalk21 = dwalk5 + 1;
                        dirwalk21 = dirwalk5;
                    }
                }
                if (dcur5 + 1 < dwalk21) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk21 = dcur5 + 1;
                    dirwalk21 = dircur5;
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
                if (c65 != Direction.CENTER) { // there was a current at the previous location
                    if (l65.add(c65).equals(l81)) { // the current flows into this location
                        if (dwalk65 < dcur81) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur81 = dwalk65;
                            dircur81 = dirwalk65;
                        }
                        if (dcur65 + 1 < dcur81) { // I can wait on the current till the end of the turn
                            dcur81 = dcur65 + 1;
                            dircur81 = dircur65;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk65 + 1 < dwalk81) { // I can walk twice in a row since there was no current
                        dwalk81 = dwalk65 + 1;
                        dirwalk81 = dirwalk65;
                    }
                }
                if (dcur65 + 1 < dwalk81) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk81 = dcur65 + 1;
                    dirwalk81 = dircur65;
                }
                
                // from (2, 2)
                if (c80 != Direction.CENTER) { // there was a current at the previous location
                    if (l80.add(c80).equals(l81)) { // the current flows into this location
                        if (dwalk80 < dcur81) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur81 = dwalk80;
                            dircur81 = dirwalk80;
                        }
                        if (dcur80 + 1 < dcur81) { // I can wait on the current till the end of the turn
                            dcur81 = dcur80 + 1;
                            dircur81 = dircur80;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk80 + 1 < dwalk81) { // I can walk twice in a row since there was no current
                        dwalk81 = dwalk80 + 1;
                        dirwalk81 = dirwalk80;
                    }
                }
                if (dcur80 + 1 < dwalk81) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk81 = dcur80 + 1;
                    dirwalk81 = dircur80;
                }
                
                // from (3, 1)
                if (c66 != Direction.CENTER) { // there was a current at the previous location
                    if (l66.add(c66).equals(l81)) { // the current flows into this location
                        if (dwalk66 < dcur81) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur81 = dwalk66;
                            dircur81 = dirwalk66;
                        }
                        if (dcur66 + 1 < dcur81) { // I can wait on the current till the end of the turn
                            dcur81 = dcur66 + 1;
                            dircur81 = dircur66;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk66 + 1 < dwalk81) { // I can walk twice in a row since there was no current
                        dwalk81 = dwalk66 + 1;
                        dirwalk81 = dirwalk66;
                    }
                }
                if (dcur66 + 1 < dwalk81) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk81 = dcur66 + 1;
                    dirwalk81 = dircur66;
                }
                
                // from (2, 3)
                if (c95 != Direction.CENTER) { // there was a current at the previous location
                    if (l95.add(c95).equals(l81)) { // the current flows into this location
                        if (dwalk95 < dcur81) { // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur81 = dwalk95;
                            dircur81 = dirwalk95;
                        }
                        if (dcur95 + 1 < dcur81) { // I can wait on the current till the end of the turn
                            dcur81 = dcur95 + 1;
                            dircur81 = dircur95;
                        }
                    }
                }
                else { // there was no current at the previous location
                    if (dwalk95 + 1 < dwalk81) { // I can walk twice in a row since there was no current
                        dwalk81 = dwalk95 + 1;
                        dirwalk81 = dirwalk95;
                    }
                }
                if (dcur95 + 1 < dwalk81) { // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk81 = dcur95 + 1;
                    dirwalk81 = dircur95;
                }
                
              }
        }


        // System.out.println("LOCAL DISTANCES:");
        // System.out.println("\t" + "\t" + dwalk91 + "\t" + dwalk92 + "\t" + dwalk93 + "\t" + dwalk94 + "\t" + dwalk95 + "\t");
        // System.out.println("\t" + dwalk75 + "\t" + dwalk76 + "\t" + dwalk77 + "\t" + dwalk78 + "\t" + dwalk79 + "\t" + dwalk80 + "\t" + dwalk81);
        // System.out.println("\t" + dwalk60 + "\t" + dwalk61 + "\t" + dwalk62 + "\t" + dwalk63 + "\t" + dwalk64 + "\t" + dwalk65 + "\t" + dwalk66);
        // System.out.println("\t" + dwalk45 + "\t" + dwalk46 + "\t" + dwalk47 + "\t" + dwalk48 + "\t" + dwalk49 + "\t" + dwalk50 + "\t" + dwalk51);
        // System.out.println("\t" + dwalk30 + "\t" + dwalk31 + "\t" + dwalk32 + "\t" + dwalk33 + "\t" + dwalk34 + "\t" + dwalk35 + "\t" + dwalk36);
        // System.out.println("\t" + dwalk15 + "\t" + dwalk16 + "\t" + dwalk17 + "\t" + dwalk18 + "\t" + dwalk19 + "\t" + dwalk20 + "\t" + dwalk21);
        // System.out.println("\t" + "\t" + dwalk1 + "\t" + dwalk2 + "\t" + dwalk3 + "\t" + dwalk4 + "\t" + dwalk5 + "\t");
        // System.out.println("DIRECTIONS:");
        // System.out.println("\t" + "\t" + dirwalk91 + "\t" + dirwalk92 + "\t" + dirwalk93 + "\t" + dirwalk94 + "\t" + dirwalk95 + "\t");
        // System.out.println("\t" + dirwalk75 + "\t" + dirwalk76 + "\t" + dirwalk77 + "\t" + dirwalk78 + "\t" + dirwalk79 + "\t" + dirwalk80 + "\t" + dirwalk81);
        // System.out.println("\t" + dirwalk60 + "\t" + dirwalk61 + "\t" + dirwalk62 + "\t" + dirwalk63 + "\t" + dirwalk64 + "\t" + dirwalk65 + "\t" + dirwalk66);
        // System.out.println("\t" + dirwalk45 + "\t" + dirwalk46 + "\t" + dirwalk47 + "\t" + dirwalk48 + "\t" + dirwalk49 + "\t" + dirwalk50 + "\t" + dirwalk51);
        // System.out.println("\t" + dirwalk30 + "\t" + dirwalk31 + "\t" + dirwalk32 + "\t" + dirwalk33 + "\t" + dirwalk34 + "\t" + dirwalk35 + "\t" + dirwalk36);
        // System.out.println("\t" + dirwalk15 + "\t" + dirwalk16 + "\t" + dirwalk17 + "\t" + dirwalk18 + "\t" + dirwalk19 + "\t" + dirwalk20 + "\t" + dirwalk21);
        // System.out.println("\t" + "\t" + dirwalk1 + "\t" + dirwalk2 + "\t" + dirwalk3 + "\t" + dirwalk4 + "\t" + dirwalk5 + "\t");

        int target_dx = target.x - l48.x;
        int target_dy = target.y - l48.y;
        switch (target_dx) {
                case -3:
                    switch (target_dy) {
                        case -2:
                            return (dwalk15 < dcur15) ? dirwalk15 : dircur15; // destination is at relative location (-3, -2)
                        case -1:
                            return (dwalk30 < dcur30) ? dirwalk30 : dircur30; // destination is at relative location (-3, -1)
                        case 0:
                            return (dwalk45 < dcur45) ? dirwalk45 : dircur45; // destination is at relative location (-3, 0)
                        case 1:
                            return (dwalk60 < dcur60) ? dirwalk60 : dircur60; // destination is at relative location (-3, 1)
                        case 2:
                            return (dwalk75 < dcur75) ? dirwalk75 : dircur75; // destination is at relative location (-3, 2)
                    }
                    break;
                case -2:
                    switch (target_dy) {
                        case -3:
                            return (dwalk1 < dcur1) ? dirwalk1 : dircur1; // destination is at relative location (-2, -3)
                        case -2:
                            return (dwalk16 < dcur16) ? dirwalk16 : dircur16; // destination is at relative location (-2, -2)
                        case -1:
                            return (dwalk31 < dcur31) ? dirwalk31 : dircur31; // destination is at relative location (-2, -1)
                        case 0:
                            return (dwalk46 < dcur46) ? dirwalk46 : dircur46; // destination is at relative location (-2, 0)
                        case 1:
                            return (dwalk61 < dcur61) ? dirwalk61 : dircur61; // destination is at relative location (-2, 1)
                        case 2:
                            return (dwalk76 < dcur76) ? dirwalk76 : dircur76; // destination is at relative location (-2, 2)
                        case 3:
                            return (dwalk91 < dcur91) ? dirwalk91 : dircur91; // destination is at relative location (-2, 3)
                    }
                    break;
                case -1:
                    switch (target_dy) {
                        case -3:
                            return (dwalk2 < dcur2) ? dirwalk2 : dircur2; // destination is at relative location (-1, -3)
                        case -2:
                            return (dwalk17 < dcur17) ? dirwalk17 : dircur17; // destination is at relative location (-1, -2)
                        case -1:
                            return (dwalk32 < dcur32) ? dirwalk32 : dircur32; // destination is at relative location (-1, -1)
                        case 0:
                            return (dwalk47 < dcur47) ? dirwalk47 : dircur47; // destination is at relative location (-1, 0)
                        case 1:
                            return (dwalk62 < dcur62) ? dirwalk62 : dircur62; // destination is at relative location (-1, 1)
                        case 2:
                            return (dwalk77 < dcur77) ? dirwalk77 : dircur77; // destination is at relative location (-1, 2)
                        case 3:
                            return (dwalk92 < dcur92) ? dirwalk92 : dircur92; // destination is at relative location (-1, 3)
                    }
                    break;
                case 0:
                    switch (target_dy) {
                        case -3:
                            return (dwalk3 < dcur3) ? dirwalk3 : dircur3; // destination is at relative location (0, -3)
                        case -2:
                            return (dwalk18 < dcur18) ? dirwalk18 : dircur18; // destination is at relative location (0, -2)
                        case -1:
                            return (dwalk33 < dcur33) ? dirwalk33 : dircur33; // destination is at relative location (0, -1)
                        case 0:
                            return (dwalk48 < dcur48) ? dirwalk48 : dircur48; // destination is at relative location (0, 0)
                        case 1:
                            return (dwalk63 < dcur63) ? dirwalk63 : dircur63; // destination is at relative location (0, 1)
                        case 2:
                            return (dwalk78 < dcur78) ? dirwalk78 : dircur78; // destination is at relative location (0, 2)
                        case 3:
                            return (dwalk93 < dcur93) ? dirwalk93 : dircur93; // destination is at relative location (0, 3)
                    }
                    break;
                case 1:
                    switch (target_dy) {
                        case -3:
                            return (dwalk4 < dcur4) ? dirwalk4 : dircur4; // destination is at relative location (1, -3)
                        case -2:
                            return (dwalk19 < dcur19) ? dirwalk19 : dircur19; // destination is at relative location (1, -2)
                        case -1:
                            return (dwalk34 < dcur34) ? dirwalk34 : dircur34; // destination is at relative location (1, -1)
                        case 0:
                            return (dwalk49 < dcur49) ? dirwalk49 : dircur49; // destination is at relative location (1, 0)
                        case 1:
                            return (dwalk64 < dcur64) ? dirwalk64 : dircur64; // destination is at relative location (1, 1)
                        case 2:
                            return (dwalk79 < dcur79) ? dirwalk79 : dircur79; // destination is at relative location (1, 2)
                        case 3:
                            return (dwalk94 < dcur94) ? dirwalk94 : dircur94; // destination is at relative location (1, 3)
                    }
                    break;
                case 2:
                    switch (target_dy) {
                        case -3:
                            return (dwalk5 < dcur5) ? dirwalk5 : dircur5; // destination is at relative location (2, -3)
                        case -2:
                            return (dwalk20 < dcur20) ? dirwalk20 : dircur20; // destination is at relative location (2, -2)
                        case -1:
                            return (dwalk35 < dcur35) ? dirwalk35 : dircur35; // destination is at relative location (2, -1)
                        case 0:
                            return (dwalk50 < dcur50) ? dirwalk50 : dircur50; // destination is at relative location (2, 0)
                        case 1:
                            return (dwalk65 < dcur65) ? dirwalk65 : dircur65; // destination is at relative location (2, 1)
                        case 2:
                            return (dwalk80 < dcur80) ? dirwalk80 : dircur80; // destination is at relative location (2, 2)
                        case 3:
                            return (dwalk95 < dcur95) ? dirwalk95 : dircur95; // destination is at relative location (2, 3)
                    }
                    break;
                case 3:
                    switch (target_dy) {
                        case -2:
                            return (dwalk21 < dcur21) ? dirwalk21 : dircur21; // destination is at relative location (3, -2)
                        case -1:
                            return (dwalk36 < dcur36) ? dirwalk36 : dircur36; // destination is at relative location (3, -1)
                        case 0:
                            return (dwalk51 < dcur51) ? dirwalk51 : dircur51; // destination is at relative location (3, 0)
                        case 1:
                            return (dwalk66 < dcur66) ? dirwalk66 : dircur66; // destination is at relative location (3, 1)
                        case 2:
                            return (dwalk81 < dcur81) ? dirwalk81 : dircur81; // destination is at relative location (3, 2)
                    }
                    break;
        }

        Direction ans = null;
        double bestScore = 0;
        double currDist = Math.sqrt(l48.distanceSquaredTo(target));
        
        if (dwalk15 < dcur15) {
            double score15 = (currDist - Math.sqrt(l15.distanceSquaredTo(target))) / dwalk15;
            if (score15 > bestScore) {
                bestScore = score15;
                ans = dirwalk15;
            }
        }
        else {
            double score15 = (currDist - Math.sqrt(l15.distanceSquaredTo(target))) / dcur15;
            if (score15 > bestScore) {
                bestScore = score15;
                ans = dircur15;
            }
        }

        if (dwalk30 < dcur30) {
            double score30 = (currDist - Math.sqrt(l30.distanceSquaredTo(target))) / dwalk30;
            if (score30 > bestScore) {
                bestScore = score30;
                ans = dirwalk30;
            }
        }
        else {
            double score30 = (currDist - Math.sqrt(l30.distanceSquaredTo(target))) / dcur30;
            if (score30 > bestScore) {
                bestScore = score30;
                ans = dircur30;
            }
        }

        if (dwalk45 < dcur45) {
            double score45 = (currDist - Math.sqrt(l45.distanceSquaredTo(target))) / dwalk45;
            if (score45 > bestScore) {
                bestScore = score45;
                ans = dirwalk45;
            }
        }
        else {
            double score45 = (currDist - Math.sqrt(l45.distanceSquaredTo(target))) / dcur45;
            if (score45 > bestScore) {
                bestScore = score45;
                ans = dircur45;
            }
        }

        if (dwalk60 < dcur60) {
            double score60 = (currDist - Math.sqrt(l60.distanceSquaredTo(target))) / dwalk60;
            if (score60 > bestScore) {
                bestScore = score60;
                ans = dirwalk60;
            }
        }
        else {
            double score60 = (currDist - Math.sqrt(l60.distanceSquaredTo(target))) / dcur60;
            if (score60 > bestScore) {
                bestScore = score60;
                ans = dircur60;
            }
        }

        if (dwalk75 < dcur75) {
            double score75 = (currDist - Math.sqrt(l75.distanceSquaredTo(target))) / dwalk75;
            if (score75 > bestScore) {
                bestScore = score75;
                ans = dirwalk75;
            }
        }
        else {
            double score75 = (currDist - Math.sqrt(l75.distanceSquaredTo(target))) / dcur75;
            if (score75 > bestScore) {
                bestScore = score75;
                ans = dircur75;
            }
        }

        if (dwalk1 < dcur1) {
            double score1 = (currDist - Math.sqrt(l1.distanceSquaredTo(target))) / dwalk1;
            if (score1 > bestScore) {
                bestScore = score1;
                ans = dirwalk1;
            }
        }
        else {
            double score1 = (currDist - Math.sqrt(l1.distanceSquaredTo(target))) / dcur1;
            if (score1 > bestScore) {
                bestScore = score1;
                ans = dircur1;
            }
        }

        if (dwalk16 < dcur16) {
            double score16 = (currDist - Math.sqrt(l16.distanceSquaredTo(target))) / dwalk16;
            if (score16 > bestScore) {
                bestScore = score16;
                ans = dirwalk16;
            }
        }
        else {
            double score16 = (currDist - Math.sqrt(l16.distanceSquaredTo(target))) / dcur16;
            if (score16 > bestScore) {
                bestScore = score16;
                ans = dircur16;
            }
        }

        if (dwalk31 < dcur31) {
            double score31 = (currDist - Math.sqrt(l31.distanceSquaredTo(target))) / dwalk31;
            if (score31 > bestScore) {
                bestScore = score31;
                ans = dirwalk31;
            }
        }
        else {
            double score31 = (currDist - Math.sqrt(l31.distanceSquaredTo(target))) / dcur31;
            if (score31 > bestScore) {
                bestScore = score31;
                ans = dircur31;
            }
        }

        if (dwalk61 < dcur61) {
            double score61 = (currDist - Math.sqrt(l61.distanceSquaredTo(target))) / dwalk61;
            if (score61 > bestScore) {
                bestScore = score61;
                ans = dirwalk61;
            }
        }
        else {
            double score61 = (currDist - Math.sqrt(l61.distanceSquaredTo(target))) / dcur61;
            if (score61 > bestScore) {
                bestScore = score61;
                ans = dircur61;
            }
        }

        if (dwalk76 < dcur76) {
            double score76 = (currDist - Math.sqrt(l76.distanceSquaredTo(target))) / dwalk76;
            if (score76 > bestScore) {
                bestScore = score76;
                ans = dirwalk76;
            }
        }
        else {
            double score76 = (currDist - Math.sqrt(l76.distanceSquaredTo(target))) / dcur76;
            if (score76 > bestScore) {
                bestScore = score76;
                ans = dircur76;
            }
        }

        if (dwalk91 < dcur91) {
            double score91 = (currDist - Math.sqrt(l91.distanceSquaredTo(target))) / dwalk91;
            if (score91 > bestScore) {
                bestScore = score91;
                ans = dirwalk91;
            }
        }
        else {
            double score91 = (currDist - Math.sqrt(l91.distanceSquaredTo(target))) / dcur91;
            if (score91 > bestScore) {
                bestScore = score91;
                ans = dircur91;
            }
        }

        if (dwalk2 < dcur2) {
            double score2 = (currDist - Math.sqrt(l2.distanceSquaredTo(target))) / dwalk2;
            if (score2 > bestScore) {
                bestScore = score2;
                ans = dirwalk2;
            }
        }
        else {
            double score2 = (currDist - Math.sqrt(l2.distanceSquaredTo(target))) / dcur2;
            if (score2 > bestScore) {
                bestScore = score2;
                ans = dircur2;
            }
        }

        if (dwalk17 < dcur17) {
            double score17 = (currDist - Math.sqrt(l17.distanceSquaredTo(target))) / dwalk17;
            if (score17 > bestScore) {
                bestScore = score17;
                ans = dirwalk17;
            }
        }
        else {
            double score17 = (currDist - Math.sqrt(l17.distanceSquaredTo(target))) / dcur17;
            if (score17 > bestScore) {
                bestScore = score17;
                ans = dircur17;
            }
        }

        if (dwalk77 < dcur77) {
            double score77 = (currDist - Math.sqrt(l77.distanceSquaredTo(target))) / dwalk77;
            if (score77 > bestScore) {
                bestScore = score77;
                ans = dirwalk77;
            }
        }
        else {
            double score77 = (currDist - Math.sqrt(l77.distanceSquaredTo(target))) / dcur77;
            if (score77 > bestScore) {
                bestScore = score77;
                ans = dircur77;
            }
        }

        if (dwalk92 < dcur92) {
            double score92 = (currDist - Math.sqrt(l92.distanceSquaredTo(target))) / dwalk92;
            if (score92 > bestScore) {
                bestScore = score92;
                ans = dirwalk92;
            }
        }
        else {
            double score92 = (currDist - Math.sqrt(l92.distanceSquaredTo(target))) / dcur92;
            if (score92 > bestScore) {
                bestScore = score92;
                ans = dircur92;
            }
        }

        if (dwalk3 < dcur3) {
            double score3 = (currDist - Math.sqrt(l3.distanceSquaredTo(target))) / dwalk3;
            if (score3 > bestScore) {
                bestScore = score3;
                ans = dirwalk3;
            }
        }
        else {
            double score3 = (currDist - Math.sqrt(l3.distanceSquaredTo(target))) / dcur3;
            if (score3 > bestScore) {
                bestScore = score3;
                ans = dircur3;
            }
        }

        if (dwalk93 < dcur93) {
            double score93 = (currDist - Math.sqrt(l93.distanceSquaredTo(target))) / dwalk93;
            if (score93 > bestScore) {
                bestScore = score93;
                ans = dirwalk93;
            }
        }
        else {
            double score93 = (currDist - Math.sqrt(l93.distanceSquaredTo(target))) / dcur93;
            if (score93 > bestScore) {
                bestScore = score93;
                ans = dircur93;
            }
        }

        if (dwalk4 < dcur4) {
            double score4 = (currDist - Math.sqrt(l4.distanceSquaredTo(target))) / dwalk4;
            if (score4 > bestScore) {
                bestScore = score4;
                ans = dirwalk4;
            }
        }
        else {
            double score4 = (currDist - Math.sqrt(l4.distanceSquaredTo(target))) / dcur4;
            if (score4 > bestScore) {
                bestScore = score4;
                ans = dircur4;
            }
        }

        if (dwalk19 < dcur19) {
            double score19 = (currDist - Math.sqrt(l19.distanceSquaredTo(target))) / dwalk19;
            if (score19 > bestScore) {
                bestScore = score19;
                ans = dirwalk19;
            }
        }
        else {
            double score19 = (currDist - Math.sqrt(l19.distanceSquaredTo(target))) / dcur19;
            if (score19 > bestScore) {
                bestScore = score19;
                ans = dircur19;
            }
        }

        if (dwalk79 < dcur79) {
            double score79 = (currDist - Math.sqrt(l79.distanceSquaredTo(target))) / dwalk79;
            if (score79 > bestScore) {
                bestScore = score79;
                ans = dirwalk79;
            }
        }
        else {
            double score79 = (currDist - Math.sqrt(l79.distanceSquaredTo(target))) / dcur79;
            if (score79 > bestScore) {
                bestScore = score79;
                ans = dircur79;
            }
        }

        if (dwalk94 < dcur94) {
            double score94 = (currDist - Math.sqrt(l94.distanceSquaredTo(target))) / dwalk94;
            if (score94 > bestScore) {
                bestScore = score94;
                ans = dirwalk94;
            }
        }
        else {
            double score94 = (currDist - Math.sqrt(l94.distanceSquaredTo(target))) / dcur94;
            if (score94 > bestScore) {
                bestScore = score94;
                ans = dircur94;
            }
        }

        if (dwalk5 < dcur5) {
            double score5 = (currDist - Math.sqrt(l5.distanceSquaredTo(target))) / dwalk5;
            if (score5 > bestScore) {
                bestScore = score5;
                ans = dirwalk5;
            }
        }
        else {
            double score5 = (currDist - Math.sqrt(l5.distanceSquaredTo(target))) / dcur5;
            if (score5 > bestScore) {
                bestScore = score5;
                ans = dircur5;
            }
        }

        if (dwalk20 < dcur20) {
            double score20 = (currDist - Math.sqrt(l20.distanceSquaredTo(target))) / dwalk20;
            if (score20 > bestScore) {
                bestScore = score20;
                ans = dirwalk20;
            }
        }
        else {
            double score20 = (currDist - Math.sqrt(l20.distanceSquaredTo(target))) / dcur20;
            if (score20 > bestScore) {
                bestScore = score20;
                ans = dircur20;
            }
        }

        if (dwalk35 < dcur35) {
            double score35 = (currDist - Math.sqrt(l35.distanceSquaredTo(target))) / dwalk35;
            if (score35 > bestScore) {
                bestScore = score35;
                ans = dirwalk35;
            }
        }
        else {
            double score35 = (currDist - Math.sqrt(l35.distanceSquaredTo(target))) / dcur35;
            if (score35 > bestScore) {
                bestScore = score35;
                ans = dircur35;
            }
        }

        if (dwalk65 < dcur65) {
            double score65 = (currDist - Math.sqrt(l65.distanceSquaredTo(target))) / dwalk65;
            if (score65 > bestScore) {
                bestScore = score65;
                ans = dirwalk65;
            }
        }
        else {
            double score65 = (currDist - Math.sqrt(l65.distanceSquaredTo(target))) / dcur65;
            if (score65 > bestScore) {
                bestScore = score65;
                ans = dircur65;
            }
        }

        if (dwalk80 < dcur80) {
            double score80 = (currDist - Math.sqrt(l80.distanceSquaredTo(target))) / dwalk80;
            if (score80 > bestScore) {
                bestScore = score80;
                ans = dirwalk80;
            }
        }
        else {
            double score80 = (currDist - Math.sqrt(l80.distanceSquaredTo(target))) / dcur80;
            if (score80 > bestScore) {
                bestScore = score80;
                ans = dircur80;
            }
        }

        if (dwalk95 < dcur95) {
            double score95 = (currDist - Math.sqrt(l95.distanceSquaredTo(target))) / dwalk95;
            if (score95 > bestScore) {
                bestScore = score95;
                ans = dirwalk95;
            }
        }
        else {
            double score95 = (currDist - Math.sqrt(l95.distanceSquaredTo(target))) / dcur95;
            if (score95 > bestScore) {
                bestScore = score95;
                ans = dircur95;
            }
        }

        if (dwalk21 < dcur21) {
            double score21 = (currDist - Math.sqrt(l21.distanceSquaredTo(target))) / dwalk21;
            if (score21 > bestScore) {
                bestScore = score21;
                ans = dirwalk21;
            }
        }
        else {
            double score21 = (currDist - Math.sqrt(l21.distanceSquaredTo(target))) / dcur21;
            if (score21 > bestScore) {
                bestScore = score21;
                ans = dircur21;
            }
        }

        if (dwalk36 < dcur36) {
            double score36 = (currDist - Math.sqrt(l36.distanceSquaredTo(target))) / dwalk36;
            if (score36 > bestScore) {
                bestScore = score36;
                ans = dirwalk36;
            }
        }
        else {
            double score36 = (currDist - Math.sqrt(l36.distanceSquaredTo(target))) / dcur36;
            if (score36 > bestScore) {
                bestScore = score36;
                ans = dircur36;
            }
        }

        if (dwalk51 < dcur51) {
            double score51 = (currDist - Math.sqrt(l51.distanceSquaredTo(target))) / dwalk51;
            if (score51 > bestScore) {
                bestScore = score51;
                ans = dirwalk51;
            }
        }
        else {
            double score51 = (currDist - Math.sqrt(l51.distanceSquaredTo(target))) / dcur51;
            if (score51 > bestScore) {
                bestScore = score51;
                ans = dircur51;
            }
        }

        if (dwalk66 < dcur66) {
            double score66 = (currDist - Math.sqrt(l66.distanceSquaredTo(target))) / dwalk66;
            if (score66 > bestScore) {
                bestScore = score66;
                ans = dirwalk66;
            }
        }
        else {
            double score66 = (currDist - Math.sqrt(l66.distanceSquaredTo(target))) / dcur66;
            if (score66 > bestScore) {
                bestScore = score66;
                ans = dircur66;
            }
        }

        if (dwalk81 < dcur81) {
            double score81 = (currDist - Math.sqrt(l81.distanceSquaredTo(target))) / dwalk81;
            if (score81 > bestScore) {
                bestScore = score81;
                ans = dirwalk81;
            }
        }
        else {
            double score81 = (currDist - Math.sqrt(l81.distanceSquaredTo(target))) / dcur81;
            if (score81 > bestScore) {
                bestScore = score81;
                ans = dircur81;
            }
        }


        return ans;
    }

    private void initRest() {
    
        l47 = l48.add(Direction.WEST); // (-1, 0) from (0, 0)
        dwalk47 = 99999;
        dcur47 = 99999;
        dirwalk47 = null;
        dircur47 = null;
        p47 = true;
        c47 = Direction.CENTER;
    
        l33 = l48.add(Direction.SOUTH); // (0, -1) from (0, 0)
        dwalk33 = 99999;
        dcur33 = 99999;
        dirwalk33 = null;
        dircur33 = null;
        p33 = true;
        c33 = Direction.CENTER;
    
        l63 = l48.add(Direction.NORTH); // (0, 1) from (0, 0)
        dwalk63 = 99999;
        dcur63 = 99999;
        dirwalk63 = null;
        dircur63 = null;
        p63 = true;
        c63 = Direction.CENTER;
    
        l49 = l48.add(Direction.EAST); // (1, 0) from (0, 0)
        dwalk49 = 99999;
        dcur49 = 99999;
        dirwalk49 = null;
        dircur49 = null;
        p49 = true;
        c49 = Direction.CENTER;
    
        l32 = l48.add(Direction.SOUTHWEST); // (-1, -1) from (0, 0)
        dwalk32 = 99999;
        dcur32 = 99999;
        dirwalk32 = null;
        dircur32 = null;
        p32 = true;
        c32 = Direction.CENTER;
    
        l62 = l48.add(Direction.NORTHWEST); // (-1, 1) from (0, 0)
        dwalk62 = 99999;
        dcur62 = 99999;
        dirwalk62 = null;
        dircur62 = null;
        p62 = true;
        c62 = Direction.CENTER;
    
        l34 = l48.add(Direction.SOUTHEAST); // (1, -1) from (0, 0)
        dwalk34 = 99999;
        dcur34 = 99999;
        dirwalk34 = null;
        dircur34 = null;
        p34 = true;
        c34 = Direction.CENTER;
    
        l64 = l48.add(Direction.NORTHEAST); // (1, 1) from (0, 0)
        dwalk64 = 99999;
        dcur64 = 99999;
        dirwalk64 = null;
        dircur64 = null;
        p64 = true;
        c64 = Direction.CENTER;
    
        l46 = l47.add(Direction.WEST); // (-2, 0) from (-1, 0)
        dwalk46 = 99999;
        dcur46 = 99999;
        dirwalk46 = null;
        dircur46 = null;
        p46 = true;
        c46 = Direction.CENTER;
    
        l18 = l33.add(Direction.SOUTH); // (0, -2) from (0, -1)
        dwalk18 = 99999;
        dcur18 = 99999;
        dirwalk18 = null;
        dircur18 = null;
        p18 = true;
        c18 = Direction.CENTER;
    
        l78 = l63.add(Direction.NORTH); // (0, 2) from (0, 1)
        dwalk78 = 99999;
        dcur78 = 99999;
        dirwalk78 = null;
        dircur78 = null;
        p78 = true;
        c78 = Direction.CENTER;
    
        l50 = l49.add(Direction.EAST); // (2, 0) from (1, 0)
        dwalk50 = 99999;
        dcur50 = 99999;
        dirwalk50 = null;
        dircur50 = null;
        p50 = true;
        c50 = Direction.CENTER;
    
        l31 = l47.add(Direction.SOUTHWEST); // (-2, -1) from (-1, 0)
        dwalk31 = 99999;
        dcur31 = 99999;
        dirwalk31 = null;
        dircur31 = null;
        p31 = true;
        c31 = Direction.CENTER;
    
        l61 = l47.add(Direction.NORTHWEST); // (-2, 1) from (-1, 0)
        dwalk61 = 99999;
        dcur61 = 99999;
        dirwalk61 = null;
        dircur61 = null;
        p61 = true;
        c61 = Direction.CENTER;
    
        l17 = l33.add(Direction.SOUTHWEST); // (-1, -2) from (0, -1)
        dwalk17 = 99999;
        dcur17 = 99999;
        dirwalk17 = null;
        dircur17 = null;
        p17 = true;
        c17 = Direction.CENTER;
    
        l77 = l63.add(Direction.NORTHWEST); // (-1, 2) from (0, 1)
        dwalk77 = 99999;
        dcur77 = 99999;
        dirwalk77 = null;
        dircur77 = null;
        p77 = true;
        c77 = Direction.CENTER;
    
        l19 = l33.add(Direction.SOUTHEAST); // (1, -2) from (0, -1)
        dwalk19 = 99999;
        dcur19 = 99999;
        dirwalk19 = null;
        dircur19 = null;
        p19 = true;
        c19 = Direction.CENTER;
    
        l79 = l63.add(Direction.NORTHEAST); // (1, 2) from (0, 1)
        dwalk79 = 99999;
        dcur79 = 99999;
        dirwalk79 = null;
        dircur79 = null;
        p79 = true;
        c79 = Direction.CENTER;
    
        l35 = l49.add(Direction.SOUTHEAST); // (2, -1) from (1, 0)
        dwalk35 = 99999;
        dcur35 = 99999;
        dirwalk35 = null;
        dircur35 = null;
        p35 = true;
        c35 = Direction.CENTER;
    
        l65 = l49.add(Direction.NORTHEAST); // (2, 1) from (1, 0)
        dwalk65 = 99999;
        dcur65 = 99999;
        dirwalk65 = null;
        dircur65 = null;
        p65 = true;
        c65 = Direction.CENTER;
    
        l16 = l32.add(Direction.SOUTHWEST); // (-2, -2) from (-1, -1)
        dwalk16 = 99999;
        dcur16 = 99999;
        dirwalk16 = null;
        dircur16 = null;
        p16 = true;
        c16 = Direction.CENTER;
    
        l76 = l62.add(Direction.NORTHWEST); // (-2, 2) from (-1, 1)
        dwalk76 = 99999;
        dcur76 = 99999;
        dirwalk76 = null;
        dircur76 = null;
        p76 = true;
        c76 = Direction.CENTER;
    
        l20 = l34.add(Direction.SOUTHEAST); // (2, -2) from (1, -1)
        dwalk20 = 99999;
        dcur20 = 99999;
        dirwalk20 = null;
        dircur20 = null;
        p20 = true;
        c20 = Direction.CENTER;
    
        l80 = l64.add(Direction.NORTHEAST); // (2, 2) from (1, 1)
        dwalk80 = 99999;
        dcur80 = 99999;
        dirwalk80 = null;
        dircur80 = null;
        p80 = true;
        c80 = Direction.CENTER;
    
        l45 = l46.add(Direction.WEST); // (-3, 0) from (-2, 0)
        dwalk45 = 99999;
        dcur45 = 99999;
        dirwalk45 = null;
        dircur45 = null;
        p45 = true;
        c45 = Direction.CENTER;
    
        l3 = l18.add(Direction.SOUTH); // (0, -3) from (0, -2)
        dwalk3 = 99999;
        dcur3 = 99999;
        dirwalk3 = null;
        dircur3 = null;
        p3 = true;
        c3 = Direction.CENTER;
    
        l93 = l78.add(Direction.NORTH); // (0, 3) from (0, 2)
        dwalk93 = 99999;
        dcur93 = 99999;
        dirwalk93 = null;
        dircur93 = null;
        p93 = true;
        c93 = Direction.CENTER;
    
        l51 = l50.add(Direction.EAST); // (3, 0) from (2, 0)
        dwalk51 = 99999;
        dcur51 = 99999;
        dirwalk51 = null;
        dircur51 = null;
        p51 = true;
        c51 = Direction.CENTER;
    
        l30 = l46.add(Direction.SOUTHWEST); // (-3, -1) from (-2, 0)
        dwalk30 = 99999;
        dcur30 = 99999;
        dirwalk30 = null;
        dircur30 = null;
        p30 = true;
        c30 = Direction.CENTER;
    
        l60 = l46.add(Direction.NORTHWEST); // (-3, 1) from (-2, 0)
        dwalk60 = 99999;
        dcur60 = 99999;
        dirwalk60 = null;
        dircur60 = null;
        p60 = true;
        c60 = Direction.CENTER;
    
        l2 = l18.add(Direction.SOUTHWEST); // (-1, -3) from (0, -2)
        dwalk2 = 99999;
        dcur2 = 99999;
        dirwalk2 = null;
        dircur2 = null;
        p2 = true;
        c2 = Direction.CENTER;
    
        l92 = l78.add(Direction.NORTHWEST); // (-1, 3) from (0, 2)
        dwalk92 = 99999;
        dcur92 = 99999;
        dirwalk92 = null;
        dircur92 = null;
        p92 = true;
        c92 = Direction.CENTER;
    
        l4 = l18.add(Direction.SOUTHEAST); // (1, -3) from (0, -2)
        dwalk4 = 99999;
        dcur4 = 99999;
        dirwalk4 = null;
        dircur4 = null;
        p4 = true;
        c4 = Direction.CENTER;
    
        l94 = l78.add(Direction.NORTHEAST); // (1, 3) from (0, 2)
        dwalk94 = 99999;
        dcur94 = 99999;
        dirwalk94 = null;
        dircur94 = null;
        p94 = true;
        c94 = Direction.CENTER;
    
        l36 = l50.add(Direction.SOUTHEAST); // (3, -1) from (2, 0)
        dwalk36 = 99999;
        dcur36 = 99999;
        dirwalk36 = null;
        dircur36 = null;
        p36 = true;
        c36 = Direction.CENTER;
    
        l66 = l50.add(Direction.NORTHEAST); // (3, 1) from (2, 0)
        dwalk66 = 99999;
        dcur66 = 99999;
        dirwalk66 = null;
        dircur66 = null;
        p66 = true;
        c66 = Direction.CENTER;
    
        l15 = l31.add(Direction.SOUTHWEST); // (-3, -2) from (-2, -1)
        dwalk15 = 99999;
        dcur15 = 99999;
        dirwalk15 = null;
        dircur15 = null;
        p15 = true;
        c15 = Direction.CENTER;
    
        l75 = l61.add(Direction.NORTHWEST); // (-3, 2) from (-2, 1)
        dwalk75 = 99999;
        dcur75 = 99999;
        dirwalk75 = null;
        dircur75 = null;
        p75 = true;
        c75 = Direction.CENTER;
    
        l1 = l17.add(Direction.SOUTHWEST); // (-2, -3) from (-1, -2)
        dwalk1 = 99999;
        dcur1 = 99999;
        dirwalk1 = null;
        dircur1 = null;
        p1 = true;
        c1 = Direction.CENTER;
    
        l91 = l77.add(Direction.NORTHWEST); // (-2, 3) from (-1, 2)
        dwalk91 = 99999;
        dcur91 = 99999;
        dirwalk91 = null;
        dircur91 = null;
        p91 = true;
        c91 = Direction.CENTER;
    
        l5 = l19.add(Direction.SOUTHEAST); // (2, -3) from (1, -2)
        dwalk5 = 99999;
        dcur5 = 99999;
        dirwalk5 = null;
        dircur5 = null;
        p5 = true;
        c5 = Direction.CENTER;
    
        l95 = l79.add(Direction.NORTHEAST); // (2, 3) from (1, 2)
        dwalk95 = 99999;
        dcur95 = 99999;
        dirwalk95 = null;
        dircur95 = null;
        p95 = true;
        c95 = Direction.CENTER;
    
        l21 = l35.add(Direction.SOUTHEAST); // (3, -2) from (2, -1)
        dwalk21 = 99999;
        dcur21 = 99999;
        dirwalk21 = null;
        dircur21 = null;
        p21 = true;
        c21 = Direction.CENTER;
    
        l81 = l65.add(Direction.NORTHEAST); // (3, 2) from (2, 1)
        dwalk81 = 99999;
        dcur81 = 99999;
        dirwalk81 = null;
        dircur81 = null;
        p81 = true;
        c81 = Direction.CENTER;
    
    }
}
