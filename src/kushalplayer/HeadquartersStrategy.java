package kushalplayer;

import battlecode.common.*;

public class HeadquartersStrategy {
    /**
     * Run a single turn for a Headquarters.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    static void runHeadquarters(RobotController rc) throws GameActionException {
        if (rc.getRoundNum() % 100 >= 10) {
            // Once every 100 rounds save up for 90 rounds to build an anchor
            buildAnchor(rc);
        }
        else {
            buildRobot(rc);
        }
    }

    private static void buildAnchor(RobotController rc) throws GameActionException {
        rc.setIndicatorString("Trying to build an anchor!");
        if (rc.canBuildAnchor(Anchor.STANDARD)) {
            // If we can build an anchor do it!
            rc.setIndicatorString("Building anchor!");
            rc.buildAnchor(Anchor.STANDARD);
        }
    }

    private static void buildRobot(RobotController rc) throws GameActionException {
        // Pick a type of robot to build
        RobotType robotTypeToBuild;
        if (RobotPlayer.rng.nextBoolean()) {
            robotTypeToBuild = RobotType.CARRIER;
        } else {
            robotTypeToBuild = RobotType.LAUNCHER;
        }

        // Try to build the robot, if we have enough resources and space around the HQ
        tryToBuildRobot(rc, robotTypeToBuild);
    }

    static void tryToBuildRobot(RobotController rc, RobotType robotTypeToBuild) throws GameActionException {
        rc.setIndicatorString("Trying to build a " + robotTypeToBuild);
        for (Direction dir: RobotPlayer.directions) {
            MapLocation candidateBuildLoc = rc.getLocation().add(dir);
            if (rc.canBuildRobot(robotTypeToBuild, candidateBuildLoc)) {
                rc.buildRobot(robotTypeToBuild, candidateBuildLoc);
                return;
            }
        }
    }
}
