package tim;

import battlecode.common.*;
import battlecode.world.Well;

import java.util.*;

/**
 * RobotPlayer is the class that describes your main robot strategy.
 * The run() method inside this class is like your main function: this is what we'll call once your robot
 * is created!
 */
public strictfp class RobotPlayer {

    /**
     * We will use this variable to count the number of turns this robot has been alive.
     * You can use static variables like this to save any information you want. Keep in mind that even though
     * these variables are static, in Battlecode they aren't actually shared between your robots.
     */
    static int turnCount = 0;

    /**
     * A random number generator.
     * We will use this RNG to make some random moves. The Random class is provided by the java.util.Random
     * import at the top of this file. Here, we *seed* the RNG with a constant number (6147); this makes sure
     * we get the same sequence of numbers every time this code is run. This is very useful for debugging!
     */
    static final Random rng = new Random(6147);

    /** Array containing all the possible movement directions. */
    static final Direction[] directions = {
        Direction.NORTH,
        Direction.NORTHEAST,
        Direction.EAST,
        Direction.SOUTHEAST,
        Direction.SOUTH,
        Direction.SOUTHWEST,
        Direction.WEST,
        Direction.NORTHWEST,
    };

    /**
     * run() is the method that is called when a robot is instantiated in the Battlecode world.
     * It is like the main function for your robot. If this method returns, the robot dies!
     *
     * @param rc  The RobotController object. You use it to perform actions from this robot, and to get
     *            information on its current status. Essentially your portal to interacting with the world.
     **/
    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {
        while (true) {
            // This code runs during the entire lifespan of the robot, which is why it is in an infinite
            // loop. If we ever leave this loop and return from run(), the robot dies! At the end of the
            // loop, we call Clock.yield(), signifying that we've done everything we want to do.

            turnCount += 1;  // We have now been alive for one more turn!

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode.
            try {
                // The same run() function is called for every robot on your team, even if they are
                // different types. Here, we separate the control depending on the RobotType, so we can
                // use different strategies on different robots. If you wish, you are free to rewrite
                // this into a different control structure!
                switch (rc.getType()) {
                    case HEADQUARTERS:     runHeadquarters(rc);  break;
                    case CARRIER:          runCarrier(rc);   break;
                    case LAUNCHER:         runLauncher(rc); break;
                    case BOOSTER:          // Examplefuncsplayer doesn't use any of these robot types below.
                    case DESTABILIZER:     // You might want to give them a try!
                    case AMPLIFIER:        runAmplifier(rc); break;
                }

            } catch (GameActionException e) {
                // Oh no! It looks like we did something illegal in the Battlecode world. You should
                // handle GameActionExceptions judiciously, in case unexpected events occur in the game
                // world. Remember, uncaught exceptions cause your robot to explode!
                System.out.println(rc.getType() + " Exception");
                e.printStackTrace();

            } catch (Exception e) {
                // Oh no! It looks like our code tried to do something bad. This isn't a
                // GameActionException, so it's more likely to be a bug in our code.
                System.out.println(rc.getType() + " Exception");
                e.printStackTrace();

            } finally {
                // Signify we've done everything we want to do, thereby ending our turn.
                // This will make our code wait until the next turn, and then perform this loop again.
                Clock.yield();
            }
            // End of loop: go back to the top. Clock.yield() has ended, so it's time for another turn!
        }

        // Your code should never reach here (unless it's intentional)! Self-destruction imminent...
    }

    /**
     * Run a single turn for a Headquarters.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    static void runHeadquarters(RobotController rc) throws GameActionException {
        // If turn 0, initialize the shared array
        if (rc.getRoundNum() == 1) {
            // Write all values to max value (2^16 - 1 = 65535)
            for(int i = 0; i < GameConstants.SHARED_ARRAY_LENGTH; i++) {
                rc.writeSharedArray(i, 65535);
            }

            // Record position of headquarters
            MapLocation hq_loc = rc.getLocation();
            rc.writeSharedArray(0, hq_loc.x);
            rc.writeSharedArray(1, hq_loc.y);

            // Record position of any known wells in the shared array
            WellInfo[] wells = rc.senseNearbyWells();
            for (WellInfo w : wells) {
                MapLocation loc = w.getMapLocation();
                ResourceType well_type = w.getResourceType();
                int well_type_num = 0;
                switch (well_type) {
                    case ADAMANTIUM: well_type_num = 0; break;
                    case MANA: well_type_num = 1; break;
                    case ELIXIR: well_type_num = 2; break;
                }
                rc.writeSharedArray(2+well_type_num*2, loc.x);
                rc.writeSharedArray(2+well_type_num*2+1, loc.y);
            }
            rc.writeSharedArray(63, 0);
            // Build an amplifier to scout
            for (Direction dir: directions) {
                MapLocation build_loc = rc.getLocation().add(dir);
                if (rc.canBuildRobot(RobotType.AMPLIFIER, build_loc)) {
                    rc.buildRobot(RobotType.AMPLIFIER, build_loc);
                }
            }
        }

        // Pick a direction to build in.
        Direction dir = directions[rng.nextInt(directions.length)];
        MapLocation newLoc = rc.getLocation().add(dir);
        if (rc.canBuildAnchor(Anchor.STANDARD) && 1==0) {
            // If we can build an anchor do it!
            rc.buildAnchor(Anchor.STANDARD);
            rc.setIndicatorString("Building anchor! " + rc.getAnchor());
        }

        if (rng.nextBoolean()) {
            // Let's try to build a carrier.
            rc.setIndicatorString("Trying to build a carrier");
            if (rc.canBuildRobot(RobotType.CARRIER, newLoc) && rc.readSharedArray(63) < 4) {
                rc.buildRobot(RobotType.CARRIER, newLoc);
                rc.writeSharedArray(63, rc.readSharedArray(63)+1);
            }
        } else {
            // Let's try to build a launcher.
            rc.setIndicatorString("Trying to build a launcher");
            if (rc.canBuildRobot(RobotType.LAUNCHER, newLoc)) {
                rc.buildRobot(RobotType.LAUNCHER, newLoc);
            }
        }

        MapLocation me = rc.getLocation();
        int hq_x = rc.readSharedArray(0);
        int hq_y = rc.readSharedArray(1);
        int mana_well_x = rc.readSharedArray(4);
        int mana_well_y = rc.readSharedArray(5);

        int hq_to_well = mana_well_x - hq_x + mana_well_y - hq_y;
        int me_to_well = mana_well_x - me.x + mana_well_y - me.y;
        if (me_to_well < hq_to_well) {
            rc.writeSharedArray(0, me.x);
            rc.writeSharedArray(1, me.y);
        }
    }

    /**
     * Run a single turn for a Carrier.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    static void runCarrier(RobotController rc) throws GameActionException {
        if (rc.getAnchor() != null) {
            // If I have an anchor singularly focus on getting it to the first island I see
            int[] islands = rc.senseNearbyIslands();
            Set<MapLocation> islandLocs = new HashSet<>();
            for (int id : islands) {
                MapLocation[] thisIslandLocs = rc.senseNearbyIslandLocations(id);
                islandLocs.addAll(Arrays.asList(thisIslandLocs));
            }
            if (islandLocs.size() > 0) {
                MapLocation islandLocation = islandLocs.iterator().next();
                rc.setIndicatorString("Moving my anchor towards " + islandLocation);
                while (!rc.getLocation().equals(islandLocation)) {
                    Direction dir = rc.getLocation().directionTo(islandLocation);
                    if (rc.canMove(dir)) {
                        rc.move(dir);
                    }
                }
                if (rc.canPlaceAnchor()) {
                    rc.setIndicatorString("Huzzah, placed anchor!");
                    rc.placeAnchor();
                }
            }
        }
        // Try to gather from squares around us.
        MapLocation me = rc.getLocation();
        boolean collecting_resource = false;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                MapLocation wellLocation = new MapLocation(me.x + dx, me.y + dy);
                if (rc.canCollectResource(wellLocation, -1)) {
                    collecting_resource = true;
                    rc.collectResource(wellLocation, -1);
                    rc.setIndicatorString("Collecting, now have, AD:" +
                            rc.getResourceAmount(ResourceType.ADAMANTIUM) +
                            " MN: " + rc.getResourceAmount(ResourceType.MANA) + 
                            " EX: " + rc.getResourceAmount(ResourceType.ELIXIR));
                }
                if (rc.canTransferResource(wellLocation, ResourceType.MANA, 1)) {
                    rc.transferResource(wellLocation, ResourceType.MANA, 1);
                }
            }
        }
        // Occasionally try out the carriers attack
        if (rng.nextInt(20) == 1) {
            RobotInfo[] enemyRobots = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
            if (enemyRobots.length > 0) {
                if (rc.canAttack(enemyRobots[0].location)) {
                    rc.attack(enemyRobots[0].location);
                }
            }
        }

        // If we can see a well, move towards it
        WellInfo[] wells = rc.senseNearbyWells();
        if (wells.length > 1 && rng.nextInt(3) == 1) {
            WellInfo well_one = wells[1];
            Direction dir = me.directionTo(well_one.getMapLocation());
            if (rc.canMove(dir)) 
                rc.move(dir);
        }

        // Move towards mana well if cargo isn't full
        if (rc.getResourceAmount(ResourceType.MANA) == 0) {
            System.out.println("moving towards a");
            int well_x = 0;
            int well_y = 0;
            try {
                well_x = rc.readSharedArray(4);
                well_y = rc.readSharedArray(5);
            } catch (battlecode.common.GameActionException e) {}
            MapLocation well_location = new MapLocation(well_x, well_y);
            Direction dir = me.directionTo(well_location);
            if (rc.canMove(dir)) {
                rc.move(dir);
            }
            else {
                dir = directions[rng.nextInt(directions.length)];
                if (rc.canMove(dir)) {
                    rc.move(dir);
                }
            }
        }

        // If full move home
        if (rc.getResourceAmount(ResourceType.MANA) == 40) {
            System.out.println("I'm full, im going home");
            int hq_x = 0;
            int hq_y = 0;
            try {
                hq_x = rc.readSharedArray(0);
                hq_y = rc.readSharedArray(1);
            } catch (battlecode.common.GameActionException e) {}
            MapLocation hq_location = new MapLocation(hq_x, hq_y);
            Direction dir = me.directionTo(hq_location);
            if (rc.canMove(dir)) {
                rc.move(dir);
            }
            else {
                dir = directions[rng.nextInt(directions.length)];
                if (rc.canMove(dir)) {
                    rc.move(dir);
                }
            }
        }
    }

    /**
     * Run a single turn for a Launcher.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    static void runLauncher(RobotController rc) throws GameActionException {
        // Try to attack someone
        int radius = rc.getType().actionRadiusSquared;
        Team opponent = rc.getTeam().opponent();
        RobotInfo[] enemies = rc.senseNearbyRobots(radius, opponent);
        if (enemies.length >= 0) {
            // MapLocation toAttack = enemies[0].location;
            MapLocation toAttack = rc.getLocation().add(Direction.EAST);

            if (rc.canAttack(toAttack)) {
                rc.setIndicatorString("Attacking");        
                rc.attack(toAttack);
            }
        }

        // Also try to move randomly.
        Direction dir = directions[rng.nextInt(directions.length)];
        if (rc.canMove(dir)) {
            rc.move(dir);
        }
    }

    /**
     * Run a single turn for an Amplifier.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    static void runAmplifier(RobotController rc) throws GameActionException {
        // Check for nearby wells
        WellInfo[] wells = rc.senseNearbyWells();
        for (WellInfo well: wells) {
            updateSharedArrayWell(rc, well);
        }

        Direction dir = directions[rng.nextInt(directions.length)];
        if (rc.canMove(dir)) {
            rc.move(dir);
        }
    }

    static void updateSharedArrayWell(RobotController rc, WellInfo well) {
        int well_type = getWellTypeNum(well);
        int well_index = 2 + 2 * well_type;
        boolean need_to_write = false;
        int write_x = 0;
        int write_y = 0;
        // If there is no well recorded for this type of well, then record it
        try {
            if (rc.readSharedArray(well_index) == 65535 && rc.canWriteSharedArray(well_index, 0)) {
                MapLocation well_location = well.getMapLocation();
                write_x = well_location.x;
                write_y = well_location.y;
                need_to_write = true;
            }
        } catch (battlecode.common.GameActionException e) {

        }
        // If there is a well in the shared array, see if this one is closer

        // Write to the shared array if needed
        if (need_to_write) {
            System.out.println("FOUND A WELL" + String.valueOf(well_type));
            try {
                rc.writeSharedArray(well_index, write_x);
                rc.writeSharedArray(well_index + 1, write_y);
            } catch (battlecode.common.GameActionException e) {

            }
        }

    }

    /**
     Returns
     0 if the well is for Adamantium
     1 if the well is for Mana
     2 if the well is for Elixer
     */
    static int getWellTypeNum(WellInfo well) {
        ResourceType well_type = well.getResourceType();
        int well_type_num = 0;
        switch (well_type) {
            case ADAMANTIUM:
                well_type_num = 0;
                break;
            case MANA:
                well_type_num = 1;
                break;
            case ELIXIR:
                well_type_num = 2;
                break;
        }
        return well_type_num;
    }
}
