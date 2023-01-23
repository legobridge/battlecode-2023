package tacoplayer;

import battlecode.common.*;

import static tacoplayer.RobotPlayer.directions;
import static tacoplayer.RobotPlayer.turnCount;

public class HeadquartersStrategy {

    /**
     * Run a single turn for a Headquarters.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    static int firstAnchorRound = 150;
    static int roundsBetweenAnchor = 10;
    static int lastRoundAnchor = firstAnchorRound - roundsBetweenAnchor;
    static void runHeadquarters(RobotController rc) throws GameActionException {

        // Commands for only the first HQ to do
        if (Comms.isFirstHQ(rc)) {
            // Reset alive counts
            Comms.resetCounts(rc);
        }

        if (turnCount % 2 == 0) {
            buildAnchor(rc);
        }
        buildRobot(rc);
    }
    private static void buildAnchor(RobotController rc) throws GameActionException {
        rc.setIndicatorString("Trying to build an anchor!");
        if (rc.canBuildAnchor(Anchor.STANDARD) && Comms.getNumNeutralIslands(rc) > 0 &&
                rc.getRoundNum() - lastRoundAnchor >= roundsBetweenAnchor &&
                rc.getRoundNum() >= firstAnchorRound) {
            // If we can build an anchor do it!
            System.out.println("building anchor");
            rc.setIndicatorString("Building anchor!");
            rc.buildAnchor(Anchor.STANDARD);
            lastRoundAnchor = rc.getRoundNum();
        }
    }

    private static void buildRobot(RobotController rc) throws GameActionException {
        // Pick a type of robot to build
        RobotType robotTypeToBuild;
//        System.out.println("amps: " + String.valueOf(Comms.getPrevRobotCount(rc, RobotType.AMPLIFIER) < 1));
//        if (turnCount >= 150 && Comms.getPrevRobotCount(rc, RobotType.AMPLIFIER) < 1) {
//            robotTypeToBuild = RobotType.AMPLIFIER;
//        }
        if (turnCount % 5 == 1 && turnCount < 150) {
            robotTypeToBuild = RobotType.CARRIER;
        } else {
            robotTypeToBuild = RobotType.LAUNCHER;
        }

        // Try to build the robot, if we have enough resources and space around the HQ
        tryToBuildRobot(rc, robotTypeToBuild);
    }

    static void tryToBuildRobot(RobotController rc, RobotType robotTypeToBuild) throws GameActionException {
        rc.setIndicatorString("Trying to build a " + robotTypeToBuild);
        for (Direction dir: directions) {
            MapLocation candidateBuildLoc = rc.getLocation().add(dir);
            if (rc.canBuildRobot(robotTypeToBuild, candidateBuildLoc)) {
                rc.buildRobot(robotTypeToBuild, candidateBuildLoc);
                return;
            }
        }
    }

//    static RobotType[] buildOrder = {
//            RobotType.CARRIER,
//            RobotType.CARRIER,
//            RobotType.LAUNCHER,
//            RobotType.LAUNCHER,
//            RobotType.AMPLIFIER,
//            RobotType.CARRIER};
//
//    static int currentBuildOrderIndex = 0;
//
//    static boolean anchorBuildingMode = false;
//    static void runHeadquarters(RobotController rc) throws GameActionException {
//        anchorBuildingMode = turnCount % 50 == 0;
//        if (anchorBuildingMode) {
//            if (tryToBuildAnchor(rc)) {
//                anchorBuildingMode = false;
//            }
//        }
//        else {
//            // Pick a type of robot to build
//            RobotType robotTypeToBuild = buildOrder[currentBuildOrderIndex];
//
//            // Try to build the robot, if we have enough resources and space around the HQ
//            if (tryToBuildRobot(rc, robotTypeToBuild)) {
//                currentBuildOrderIndex = (currentBuildOrderIndex + 1) % buildOrder.length;
//            }
//        }
//    }
//
//    private static boolean tryToBuildAnchor(RobotController rc) throws GameActionException {
//        rc.setIndicatorString("Trying to build an anchor!");
//        if (rc.canBuildAnchor(Anchor.STANDARD)) {
//            // If we can build an anchor do it!
//            rc.buildAnchor(Anchor.STANDARD);
//            return true;
//        }
//        return false;
//    }
//
//    static boolean tryToBuildRobot(RobotController rc, RobotType robotTypeToBuild) throws GameActionException {
//        rc.setIndicatorString("Trying to build a " + robotTypeToBuild);
//        for (Direction dir : directions) {
//            MapLocation candidateBuildLoc = rc.getLocation().add(dir);
//            if (rc.canBuildRobot(robotTypeToBuild, candidateBuildLoc)) {
//                rc.buildRobot(robotTypeToBuild, candidateBuildLoc);
//                return true;
//            }
//        }
//        return false;
//    }
}
