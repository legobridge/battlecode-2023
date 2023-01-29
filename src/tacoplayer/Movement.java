package tacoplayer;

import battlecode.common.*;
import static tacoplayer.RobotPlayer.*;
import static tacoplayer.Sensing.*;

public class Movement {
    static boolean moveTowardsVisibleEnemies(RobotController rc) throws GameActionException {
        if (visibleEnemiesCount != 0) {
//            MapLocation enemyLocation = averageLoc(visibleEnemies);
            rc.setIndicatorString("Moving towards enemy robot! " + closestVisibleEnemyRobot.location);
            // If you are outside a certain fraction of the enemy's action radius, move towards it, else move away
            if (closestVisibleEnemyRobotDistSq > (5.0 / 6.0) * rc.getType().actionRadiusSquared) {
                return Pathing.moveTowards(rc, closestVisibleEnemyRobot.location);
            }
            else {
                return moveAwayFromLocation(rc, closestVisibleEnemyRobot.location);
            }
        }
        return false;
    }

    static boolean moveTowardsLocation(RobotController rc, MapLocation loc) throws GameActionException {
        return Pathing.moveTowards(rc, loc);
    }

    static boolean moveDirectlyTowards(RobotController rc, Direction dir) throws GameActionException {
        if (rc.canMove(dir)) {
            rc.move(dir);
            return true;
        }
        else if (rc.canMove(dir.rotateLeft())) {
            rc.move(dir.rotateLeft());
            return true;
        }
        else if (rc.canMove(dir.rotateRight())) {
            rc.move(dir.rotateRight());
            return true;
        }
        return false;
    }

    static boolean moveDirectlyTowards(RobotController rc, MapLocation target) throws GameActionException {
        Direction dir = rc.getLocation().directionTo(target);
        if (rc.canMove(dir)) {
            rc.move(dir);
            return true;
        }
        else if (rc.canMove(dir.rotateLeft())) {
            rc.move(dir.rotateLeft());
            return true;
        }
        else if (rc.canMove(dir.rotateRight())) {
            rc.move(dir.rotateRight());
            return true;
        }
        return false;
    }

    static boolean moveAwayFromLocation(RobotController rc, MapLocation loc) throws GameActionException {
        MapLocation robotLoc = rc.getLocation();
        int x = robotLoc.x + robotLoc.x - loc.x;
        int y = robotLoc.y + robotLoc.y - loc.y;
        MapLocation awayLoc = new MapLocation(x, y);
        rc.setIndicatorString("Moving away from " + awayLoc);
        return moveDirectlyTowards(rc, awayLoc);
    }

    static boolean moveTowardsEnemyWell(RobotController rc) throws GameActionException {
        MapLocation well = Sensing.getClosestWell(rc);
        MapLocation wellSym = MapLocationUtil.calcSymmetricLoc(well, Comms.getSymmetryType());
        rc.setIndicatorString("Moving towards enemy Well! " + wellSym);
        return Pathing.moveTowards(rc, wellSym);
    }
    static boolean moveTowardsEnemyHq(RobotController rc) throws GameActionException {
        rc.setIndicatorString("Moving towards enemy HQ! " + closestEnemyHqLoc);
        if (rc.getLocation().distanceSquaredTo(closestEnemyHqLoc) <= RobotType.HEADQUARTERS.actionRadiusSquared + 3) {
            return moveTowardsLocation(rc, closestHqLoc);
        }
        return Pathing.moveTowards(rc, closestEnemyHqLoc);
    }

    static boolean moveTowardsEnemyIslands(RobotController rc) throws GameActionException {
        if (closestEnemyIslandLoc != null) {
            rc.setIndicatorString("Moving towards enemy island! " + closestEnemyIslandLoc);
            return Pathing.moveTowards(rc, closestEnemyIslandLoc);
        }
        return false;
    }
    static boolean moveToExtract(RobotController rc, MapLocation wellLoc) throws GameActionException {
        MapLocation target = null;
        for (int i = 9; --i >= 0;) {
            MapLocation spot = new MapLocation(wellLoc.x + i % 3 - 1, wellLoc.y + i / 3 - 1);
            if (rc.canSenseLocation(spot)) {
                MapInfo spotInfo = rc.senseMapInfo(spot);
                if (spotInfo.isPassable() && rc.canSenseRobotAtLocation(spot)) {
                    target = spot;
                    break;
                }
            }
        }
        if (target != null) {
            moveTowardsLocation(rc, target);
            return true;
        }
        return false;
    }

    static TranslatePair[] spiralDestinations = new TranslatePair[]{
            new TranslatePair(1, 4),
            new TranslatePair(-3, 4),
            new TranslatePair(-3, -6),
            new TranslatePair(5, -6),
            new TranslatePair(5, 8),
            new TranslatePair(-7, 8),
            new TranslatePair(-7, -10),
            new TranslatePair(9, -10),
            new TranslatePair(9, 12),
            new TranslatePair(-11, 12),
            new TranslatePair(-11, -14),
            new TranslatePair(13, -14),
            new TranslatePair(13, 16),
            new TranslatePair(-15, 16),
            new TranslatePair(-15, -18),
            new TranslatePair(17, -18),
            new TranslatePair(17, 20),
            new TranslatePair(-19, 20),
            new TranslatePair(-19, -22),
            new TranslatePair(21, -22),
            new TranslatePair(21, 24),
            new TranslatePair(-23, 24),
            new TranslatePair(-23, -26),
            new TranslatePair(25, -26),
            new TranslatePair(25, 28),
            new TranslatePair(-27, 28),
            new TranslatePair(-27, -30),
            new TranslatePair(29, -30),
            new TranslatePair(29, 32),
            new TranslatePair(-31, 32),
            new TranslatePair(-31, -34),
            new TranslatePair(33, -34),
            new TranslatePair(33, 36),
            new TranslatePair(-35, 36),
            new TranslatePair(-35, -38),
            new TranslatePair(37, -38),
            new TranslatePair(37, 40),
            new TranslatePair(-39, 40),
            new TranslatePair(-39, -42),
            new TranslatePair(41, -42),
            new TranslatePair(41, 44),
            new TranslatePair(-43, 44),
            new TranslatePair(-43, -46),
            new TranslatePair(45, -46),
            new TranslatePair(45, 48),
            new TranslatePair(-47, 48),
            new TranslatePair(-47, -50),
            new TranslatePair(49, -50),
            new TranslatePair(49, 52),
            new TranslatePair(-51, 52),
            new TranslatePair(-51, -54),
            new TranslatePair(53, -54),
            new TranslatePair(53, 56),
            new TranslatePair(-55, 56),
            new TranslatePair(-55, -58),
            new TranslatePair(57, -58),
            new TranslatePair(57, 60),
            new TranslatePair(-59, 60),
            new TranslatePair(-59, -62),
            new TranslatePair(61, -62)
    };
    static int spiralTurn;
    static int turnsSpentGoingToCurrentWaypoint;
    static MapLocation spiralCenter;
    static boolean moveSpirally(RobotController rc) throws GameActionException {
        if (spiralCenter == null) {
            spiralTurn = 0;
            turnsSpentGoingToCurrentWaypoint = 0;
            spiralCenter = rc.getLocation();
        }
        MapLocation loc;
        do {
            if (spiralTurn == spiralDestinations.length) {
                spiralCenter = null;
                return false;
            }
            loc = spiralCenter.translate(spiralDestinations[spiralTurn].dx, spiralDestinations[spiralTurn].dy);
            spiralTurn++;
        }
        while (!rc.onTheMap(loc) && spiralDestinations[spiralTurn - 1].dx < mapWidth);
        if (!rc.onTheMap(loc)) {
            return false;
        }
        if (rc.getLocation().distanceSquaredTo(loc) > 2 && turnsSpentGoingToCurrentWaypoint < (spiralTurn + 1) * (spiralTurn + 1)) { // Still not there
            turnsSpentGoingToCurrentWaypoint++;
            spiralTurn--;
        }
        else { // We assume we're there
            turnsSpentGoingToCurrentWaypoint = 0;
        }
        return Pathing.moveTowards(rc, loc);
    }
}

