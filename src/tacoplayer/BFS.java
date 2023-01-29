//package tacoplayer;
//
//import battlecode.common.*;
//import tacoplayer.MapLocationUtil;
//
//import static tacoplayer.RobotPlayer.map;
//
//public class BFS {
//
//    static final Direction[] directions = {
//            Direction.NORTH,
//            Direction.NORTHEAST,
//            Direction.EAST,
//            Direction.SOUTHEAST,
//            Direction.SOUTH,
//            Direction.SOUTHWEST,
//            Direction.WEST,
//            Direction.NORTHWEST,
//    };
//
//    static final int QUEUE_SIZE = 70;
//    static final int CLOUD_SCORE_PENALTY = 1;
//    static char[] minDistFromStart;
//    static char[] minDistFromStartCurrentReached;
//    static char[] prevDir;
//    static char[] prevDirCurrentReached;
//    static char[] bestDir;
//    static char[] visited;
//    static MapLocation[] queue = new MapLocation[QUEUE_SIZE];
//    static int queueFront;
//    static int queueBack;
//
//    static void resetPathing() {
//        minDistFromStart = RobotPlayer.MAP_SIZE_STRING.toCharArray();
//        minDistFromStartCurrentReached = RobotPlayer.MAP_SIZE_STRING.toCharArray();
//        prevDir = RobotPlayer.MAP_SIZE_STRING.toCharArray();
//        prevDirCurrentReached = RobotPlayer.MAP_SIZE_STRING.toCharArray();
//        bestDir = RobotPlayer.MAP_SIZE_STRING.toCharArray();
//        visited = RobotPlayer.MAP_SIZE_STRING.toCharArray();
//        queueFront = 0;
//        queueBack = 0;
//    }
//
//    public static Direction getBestDirection(RobotController rc, MapLocation target) throws GameActionException {
//        MapLocation selfLoc = rc.getLocation();
//        if (selfLoc.isAdjacentTo(target)) {
//            return selfLoc.directionTo(target);
//        } else {
//            resetPathing();
//            queue[queueBack++] = selfLoc;
//            int visionRadiusSq = rc.senseCloud(selfLoc) ? 13 : 4;
//            while (queueFront != queueBack) {
//                MapLocation loc = queue[queueFront++];
//                int hashedLoc = MapLocationUtil.hashMapLocation(loc);
//                if (loc.equals(target)) {
//                    return getDirectionFromChar(bestDir[hashedLoc]);
//                }
//                visited[hashedLoc] = 't';
//                for (Direction direction : directions) { // TODO - unroll loop
//                    MapLocation candidateLoc = loc.add(direction);
//                    if (!rc.onTheMap(candidateLoc) || selfLoc.distanceSquaredTo(candidateLoc) > visionRadiusSq) {
//                        continue;
//                    }
//                    int hashedCandidateLoc = MapLocationUtil.hashMapLocation(candidateLoc);
//                    if (visited[hashedCandidateLoc] == 't') {
//                        continue;
//                    }
//                    queue[queueBack++] = candidateLoc;
//
//                    if (map[hashedCandidateLoc] == '\0') { // First time sensing this location
//                        if (!rc.canSenseLocation(candidateLoc)) { // There must be a cloud at this location
//                            map[hashedCandidateLoc] = 'C';
//                            computeForLocation(hashedCandidateLoc, null);
//                        } else {
//                            MapInfo mapInfo = rc.senseMapInfo(candidateLoc);
//                            if (!mapInfo.isPassable()) {
//                                map[hashedCandidateLoc] = 'X';
//                            } else {
//                                switch (mapInfo.getCurrentDirection()) {
//                                    case NORTH:
//                                        map[hashedCandidateLoc] = '0';
//                                        computeForLocation(hashedCandidateLoc, Direction.NORTH);
//                                        break;
//                                    case NORTHEAST:
//                                        map[hashedCandidateLoc] = '1';
//                                        computeForLocation(hashedCandidateLoc, Direction.NORTHEAST);
//                                        break;
//                                    case EAST:
//                                        map[hashedCandidateLoc] = '2';
//                                        computeForLocation(hashedCandidateLoc, Direction.EAST);
//                                        break;
//                                    case SOUTHEAST:
//                                        map[hashedCandidateLoc] = '3';
//                                        computeForLocation(hashedCandidateLoc, Direction.SOUTHEAST);
//                                        break;
//                                    case SOUTH:
//                                        map[hashedCandidateLoc] = '4';
//                                        computeForLocation(hashedCandidateLoc, Direction.SOUTH);
//                                        break;
//                                    case SOUTHWEST:
//                                        map[hashedCandidateLoc] = '5';
//                                        computeForLocation(hashedCandidateLoc, Direction.SOUTHWEST);
//                                        break;
//                                    case WEST:
//                                        map[hashedCandidateLoc] = '6';
//                                        computeForLocation(hashedCandidateLoc, Direction.WEST);
//                                        break;
//                                    case NORTHWEST:
//                                        map[hashedCandidateLoc] = '7';
//                                        computeForLocation(hashedCandidateLoc, Direction.NORTHWEST);
//                                        break;
//                                    case CENTER:
//                                        map[hashedCandidateLoc] = 'P';
//                                        computeForLocation(hashedCandidateLoc, null);
//                                        break;
//                                }
//                            }
//                        }
//                    } else {
//                        switch (map[hashedCandidateLoc]) {
//                            case 'X':
//                                // Do nothing
//                                break;
//                            case '\0':
//                            case 'P':
//                            case 'C':
//                                computeForLocation(hashedCandidateLoc, null);
//                                break;
//                            default:
//                                computeForLocation(hashedCandidateLoc, getDirectionFromChar(map[hashedCandidateLoc]));
//                        }
//                    }
//                }
//            }
//            return getBestDirectionFromEdgeTiles(selfLoc, target);
//        }
//    }
//
//    static void computeForLocation(int hashedMapLoc, Direction currentDirection) {
//
//    }
//
//    private static Direction getBestDirectionFromEdgeTiles(MapLocation selfLoc, MapLocation target) {
//        double currDist = Math.sqrt(selfLoc.distanceSquaredTo(target));
//
//        double bestScore = 0;
//        char bestDirChar = '\0';
//
//        MapLocation edgeLoc1 = selfLoc.translate(-4, -2);
//        int hashedEdgeLoc1 = MapLocationUtil.hashMapLocation(edgeLoc1);
//        int minDist1 = Math.min(minDistFromStart[hashedEdgeLoc1], minDistFromStartCurrentReached[hashedEdgeLoc1]);
//        double score1 = (currDist - Math.sqrt(edgeLoc1.distanceSquaredTo(target))) / minDist1;
//        if (score1 > bestScore) {
//            bestScore = score1;
//            bestDirChar = bestDir[hashedEdgeLoc1];
//        }
//
//        MapLocation edgeLoc2 = selfLoc.translate(-4, -1);
//        int hashedEdgeLoc2 = MapLocationUtil.hashMapLocation(edgeLoc2);
//        int minDist2 = Math.min(minDistFromStart[hashedEdgeLoc2], minDistFromStartCurrentReached[hashedEdgeLoc2]);
//        double score2 = (currDist - Math.sqrt(edgeLoc2.distanceSquaredTo(target))) / minDist2;
//        if (score2 > bestScore) {
//            bestScore = score2;
//            bestDirChar = bestDir[hashedEdgeLoc2];
//        }
//
//        MapLocation edgeLoc3 = selfLoc.translate(-4, 0);
//        int hashedEdgeLoc3 = MapLocationUtil.hashMapLocation(edgeLoc3);
//        int minDist3 = Math.min(minDistFromStart[hashedEdgeLoc3], minDistFromStartCurrentReached[hashedEdgeLoc3]);
//        double score3 = (currDist - Math.sqrt(edgeLoc3.distanceSquaredTo(target))) / minDist3;
//        if (score3 > bestScore) {
//            bestScore = score3;
//            bestDirChar = bestDir[hashedEdgeLoc3];
//        }
//
//        MapLocation edgeLoc4 = selfLoc.translate(-4, 1);
//        int hashedEdgeLoc4 = MapLocationUtil.hashMapLocation(edgeLoc4);
//        int minDist4 = Math.min(minDistFromStart[hashedEdgeLoc4], minDistFromStartCurrentReached[hashedEdgeLoc4]);
//        double score4 = (currDist - Math.sqrt(edgeLoc4.distanceSquaredTo(target))) / minDist4;
//        if (score4 > bestScore) {
//            bestScore = score4;
//            bestDirChar = bestDir[hashedEdgeLoc4];
//        }
//
//        MapLocation edgeLoc5 = selfLoc.translate(-4, 2);
//        int hashedEdgeLoc5 = MapLocationUtil.hashMapLocation(edgeLoc5);
//        int minDist5 = Math.min(minDistFromStart[hashedEdgeLoc5], minDistFromStartCurrentReached[hashedEdgeLoc5]);
//        double score5 = (currDist - Math.sqrt(edgeLoc5.distanceSquaredTo(target))) / minDist5;
//        if (score5 > bestScore) {
//            bestScore = score5;
//            bestDirChar = bestDir[hashedEdgeLoc5];
//        }
//
//        MapLocation edgeLoc6 = selfLoc.translate(-3, -3);
//        int hashedEdgeLoc6 = MapLocationUtil.hashMapLocation(edgeLoc6);
//        int minDist6 = Math.min(minDistFromStart[hashedEdgeLoc6], minDistFromStartCurrentReached[hashedEdgeLoc6]);
//        double score6 = (currDist - Math.sqrt(edgeLoc6.distanceSquaredTo(target))) / minDist6;
//        if (score6 > bestScore) {
//            bestScore = score6;
//            bestDirChar = bestDir[hashedEdgeLoc6];
//        }
//
//        MapLocation edgeLoc7 = selfLoc.translate(-3, -2);
//        int hashedEdgeLoc7 = MapLocationUtil.hashMapLocation(edgeLoc7);
//        int minDist7 = Math.min(minDistFromStart[hashedEdgeLoc7], minDistFromStartCurrentReached[hashedEdgeLoc7]);
//        double score7 = (currDist - Math.sqrt(edgeLoc7.distanceSquaredTo(target))) / minDist7;
//        if (score7 > bestScore) {
//            bestScore = score7;
//            bestDirChar = bestDir[hashedEdgeLoc7];
//        }
//
//        MapLocation edgeLoc8 = selfLoc.translate(-3, -1);
//        int hashedEdgeLoc8 = MapLocationUtil.hashMapLocation(edgeLoc8);
//        int minDist8 = Math.min(minDistFromStart[hashedEdgeLoc8], minDistFromStartCurrentReached[hashedEdgeLoc8]);
//        double score8 = (currDist - Math.sqrt(edgeLoc8.distanceSquaredTo(target))) / minDist8;
//        if (score8 > bestScore) {
//            bestScore = score8;
//            bestDirChar = bestDir[hashedEdgeLoc8];
//        }
//
//        MapLocation edgeLoc9 = selfLoc.translate(-3, 1);
//        int hashedEdgeLoc9 = MapLocationUtil.hashMapLocation(edgeLoc9);
//        int minDist9 = Math.min(minDistFromStart[hashedEdgeLoc9], minDistFromStartCurrentReached[hashedEdgeLoc9]);
//        double score9 = (currDist - Math.sqrt(edgeLoc9.distanceSquaredTo(target))) / minDist9;
//        if (score9 > bestScore) {
//            bestScore = score9;
//            bestDirChar = bestDir[hashedEdgeLoc9];
//        }
//
//        MapLocation edgeLoc10 = selfLoc.translate(-3, 2);
//        int hashedEdgeLoc10 = MapLocationUtil.hashMapLocation(edgeLoc10);
//        int minDist10 = Math.min(minDistFromStart[hashedEdgeLoc10], minDistFromStartCurrentReached[hashedEdgeLoc10]);
//        double score10 = (currDist - Math.sqrt(edgeLoc10.distanceSquaredTo(target))) / minDist10;
//        if (score10 > bestScore) {
//            bestScore = score10;
//            bestDirChar = bestDir[hashedEdgeLoc10];
//        }
//
//        MapLocation edgeLoc11 = selfLoc.translate(-3, 3);
//        int hashedEdgeLoc11 = MapLocationUtil.hashMapLocation(edgeLoc11);
//        int minDist11 = Math.min(minDistFromStart[hashedEdgeLoc11], minDistFromStartCurrentReached[hashedEdgeLoc11]);
//        double score11 = (currDist - Math.sqrt(edgeLoc11.distanceSquaredTo(target))) / minDist11;
//        if (score11 > bestScore) {
//            bestScore = score11;
//            bestDirChar = bestDir[hashedEdgeLoc11];
//        }
//
//        MapLocation edgeLoc12 = selfLoc.translate(-2, -4);
//        int hashedEdgeLoc12 = MapLocationUtil.hashMapLocation(edgeLoc12);
//        int minDist12 = Math.min(minDistFromStart[hashedEdgeLoc12], minDistFromStartCurrentReached[hashedEdgeLoc12]);
//        double score12 = (currDist - Math.sqrt(edgeLoc12.distanceSquaredTo(target))) / minDist12;
//        if (score12 > bestScore) {
//            bestScore = score12;
//            bestDirChar = bestDir[hashedEdgeLoc12];
//        }
//
//        MapLocation edgeLoc13 = selfLoc.translate(-2, -3);
//        int hashedEdgeLoc13 = MapLocationUtil.hashMapLocation(edgeLoc13);
//        int minDist13 = Math.min(minDistFromStart[hashedEdgeLoc13], minDistFromStartCurrentReached[hashedEdgeLoc13]);
//        double score13 = (currDist - Math.sqrt(edgeLoc13.distanceSquaredTo(target))) / minDist13;
//        if (score13 > bestScore) {
//            bestScore = score13;
//            bestDirChar = bestDir[hashedEdgeLoc13];
//        }
//
//        MapLocation edgeLoc14 = selfLoc.translate(-2, 3);
//        int hashedEdgeLoc14 = MapLocationUtil.hashMapLocation(edgeLoc14);
//        int minDist14 = Math.min(minDistFromStart[hashedEdgeLoc14], minDistFromStartCurrentReached[hashedEdgeLoc14]);
//        double score14 = (currDist - Math.sqrt(edgeLoc14.distanceSquaredTo(target))) / minDist14;
//        if (score14 > bestScore) {
//            bestScore = score14;
//            bestDirChar = bestDir[hashedEdgeLoc14];
//        }
//
//        MapLocation edgeLoc15 = selfLoc.translate(-2, 4);
//        int hashedEdgeLoc15 = MapLocationUtil.hashMapLocation(edgeLoc15);
//        int minDist15 = Math.min(minDistFromStart[hashedEdgeLoc15], minDistFromStartCurrentReached[hashedEdgeLoc15]);
//        double score15 = (currDist - Math.sqrt(edgeLoc15.distanceSquaredTo(target))) / minDist15;
//        if (score15 > bestScore) {
//            bestScore = score15;
//            bestDirChar = bestDir[hashedEdgeLoc15];
//        }
//
//        MapLocation edgeLoc16 = selfLoc.translate(-1, -4);
//        int hashedEdgeLoc16 = MapLocationUtil.hashMapLocation(edgeLoc16);
//        int minDist16 = Math.min(minDistFromStart[hashedEdgeLoc16], minDistFromStartCurrentReached[hashedEdgeLoc16]);
//        double score16 = (currDist - Math.sqrt(edgeLoc16.distanceSquaredTo(target))) / minDist16;
//        if (score16 > bestScore) {
//            bestScore = score16;
//            bestDirChar = bestDir[hashedEdgeLoc16];
//        }
//
//        MapLocation edgeLoc17 = selfLoc.translate(-1, -3);
//        int hashedEdgeLoc17 = MapLocationUtil.hashMapLocation(edgeLoc17);
//        int minDist17 = Math.min(minDistFromStart[hashedEdgeLoc17], minDistFromStartCurrentReached[hashedEdgeLoc17]);
//        double score17 = (currDist - Math.sqrt(edgeLoc17.distanceSquaredTo(target))) / minDist17;
//        if (score17 > bestScore) {
//            bestScore = score17;
//            bestDirChar = bestDir[hashedEdgeLoc17];
//        }
//
//        MapLocation edgeLoc18 = selfLoc.translate(-1, 3);
//        int hashedEdgeLoc18 = MapLocationUtil.hashMapLocation(edgeLoc18);
//        int minDist18 = Math.min(minDistFromStart[hashedEdgeLoc18], minDistFromStartCurrentReached[hashedEdgeLoc18]);
//        double score18 = (currDist - Math.sqrt(edgeLoc18.distanceSquaredTo(target))) / minDist18;
//        if (score18 > bestScore) {
//            bestScore = score18;
//            bestDirChar = bestDir[hashedEdgeLoc18];
//        }
//
//        MapLocation edgeLoc19 = selfLoc.translate(-1, 4);
//        int hashedEdgeLoc19 = MapLocationUtil.hashMapLocation(edgeLoc19);
//        int minDist19 = Math.min(minDistFromStart[hashedEdgeLoc19], minDistFromStartCurrentReached[hashedEdgeLoc19]);
//        double score19 = (currDist - Math.sqrt(edgeLoc19.distanceSquaredTo(target))) / minDist19;
//        if (score19 > bestScore) {
//            bestScore = score19;
//            bestDirChar = bestDir[hashedEdgeLoc19];
//        }
//
//        MapLocation edgeLoc20 = selfLoc.translate(0, -4);
//        int hashedEdgeLoc20 = MapLocationUtil.hashMapLocation(edgeLoc20);
//        int minDist20 = Math.min(minDistFromStart[hashedEdgeLoc20], minDistFromStartCurrentReached[hashedEdgeLoc20]);
//        double score20 = (currDist - Math.sqrt(edgeLoc20.distanceSquaredTo(target))) / minDist20;
//        if (score20 > bestScore) {
//            bestScore = score20;
//            bestDirChar = bestDir[hashedEdgeLoc20];
//        }
//
//        MapLocation edgeLoc21 = selfLoc.translate(0, 4);
//        int hashedEdgeLoc21 = MapLocationUtil.hashMapLocation(edgeLoc21);
//        int minDist21 = Math.min(minDistFromStart[hashedEdgeLoc21], minDistFromStartCurrentReached[hashedEdgeLoc21]);
//        double score21 = (currDist - Math.sqrt(edgeLoc21.distanceSquaredTo(target))) / minDist21;
//        if (score21 > bestScore) {
//            bestScore = score21;
//            bestDirChar = bestDir[hashedEdgeLoc21];
//        }
//
//        MapLocation edgeLoc22 = selfLoc.translate(1, -4);
//        int hashedEdgeLoc22 = MapLocationUtil.hashMapLocation(edgeLoc22);
//        int minDist22 = Math.min(minDistFromStart[hashedEdgeLoc22], minDistFromStartCurrentReached[hashedEdgeLoc22]);
//        double score22 = (currDist - Math.sqrt(edgeLoc22.distanceSquaredTo(target))) / minDist22;
//        if (score22 > bestScore) {
//            bestScore = score22;
//            bestDirChar = bestDir[hashedEdgeLoc22];
//        }
//
//        MapLocation edgeLoc23 = selfLoc.translate(1, -3);
//        int hashedEdgeLoc23 = MapLocationUtil.hashMapLocation(edgeLoc23);
//        int minDist23 = Math.min(minDistFromStart[hashedEdgeLoc23], minDistFromStartCurrentReached[hashedEdgeLoc23]);
//        double score23 = (currDist - Math.sqrt(edgeLoc23.distanceSquaredTo(target))) / minDist23;
//        if (score23 > bestScore) {
//            bestScore = score23;
//            bestDirChar = bestDir[hashedEdgeLoc23];
//        }
//
//        MapLocation edgeLoc24 = selfLoc.translate(1, 3);
//        int hashedEdgeLoc24 = MapLocationUtil.hashMapLocation(edgeLoc24);
//        int minDist24 = Math.min(minDistFromStart[hashedEdgeLoc24], minDistFromStartCurrentReached[hashedEdgeLoc24]);
//        double score24 = (currDist - Math.sqrt(edgeLoc24.distanceSquaredTo(target))) / minDist24;
//        if (score24 > bestScore) {
//            bestScore = score24;
//            bestDirChar = bestDir[hashedEdgeLoc24];
//        }
//
//        MapLocation edgeLoc25 = selfLoc.translate(1, 4);
//        int hashedEdgeLoc25 = MapLocationUtil.hashMapLocation(edgeLoc25);
//        int minDist25 = Math.min(minDistFromStart[hashedEdgeLoc25], minDistFromStartCurrentReached[hashedEdgeLoc25]);
//        double score25 = (currDist - Math.sqrt(edgeLoc25.distanceSquaredTo(target))) / minDist25;
//        if (score25 > bestScore) {
//            bestScore = score25;
//            bestDirChar = bestDir[hashedEdgeLoc25];
//        }
//
//        MapLocation edgeLoc26 = selfLoc.translate(2, -4);
//        int hashedEdgeLoc26 = MapLocationUtil.hashMapLocation(edgeLoc26);
//        int minDist26 = Math.min(minDistFromStart[hashedEdgeLoc26], minDistFromStartCurrentReached[hashedEdgeLoc26]);
//        double score26 = (currDist - Math.sqrt(edgeLoc26.distanceSquaredTo(target))) / minDist26;
//        if (score26 > bestScore) {
//            bestScore = score26;
//            bestDirChar = bestDir[hashedEdgeLoc26];
//        }
//
//        MapLocation edgeLoc27 = selfLoc.translate(2, -3);
//        int hashedEdgeLoc27 = MapLocationUtil.hashMapLocation(edgeLoc27);
//        int minDist27 = Math.min(minDistFromStart[hashedEdgeLoc27], minDistFromStartCurrentReached[hashedEdgeLoc27]);
//        double score27 = (currDist - Math.sqrt(edgeLoc27.distanceSquaredTo(target))) / minDist27;
//        if (score27 > bestScore) {
//            bestScore = score27;
//            bestDirChar = bestDir[hashedEdgeLoc27];
//        }
//
//        MapLocation edgeLoc28 = selfLoc.translate(2, 3);
//        int hashedEdgeLoc28 = MapLocationUtil.hashMapLocation(edgeLoc28);
//        int minDist28 = Math.min(minDistFromStart[hashedEdgeLoc28], minDistFromStartCurrentReached[hashedEdgeLoc28]);
//        double score28 = (currDist - Math.sqrt(edgeLoc28.distanceSquaredTo(target))) / minDist28;
//        if (score28 > bestScore) {
//            bestScore = score28;
//            bestDirChar = bestDir[hashedEdgeLoc28];
//        }
//
//        MapLocation edgeLoc29 = selfLoc.translate(2, 4);
//        int hashedEdgeLoc29 = MapLocationUtil.hashMapLocation(edgeLoc29);
//        int minDist29 = Math.min(minDistFromStart[hashedEdgeLoc29], minDistFromStartCurrentReached[hashedEdgeLoc29]);
//        double score29 = (currDist - Math.sqrt(edgeLoc29.distanceSquaredTo(target))) / minDist29;
//        if (score29 > bestScore) {
//            bestScore = score29;
//            bestDirChar = bestDir[hashedEdgeLoc29];
//        }
//
//        MapLocation edgeLoc30 = selfLoc.translate(3, -3);
//        int hashedEdgeLoc30 = MapLocationUtil.hashMapLocation(edgeLoc30);
//        int minDist30 = Math.min(minDistFromStart[hashedEdgeLoc30], minDistFromStartCurrentReached[hashedEdgeLoc30]);
//        double score30 = (currDist - Math.sqrt(edgeLoc30.distanceSquaredTo(target))) / minDist30;
//        if (score30 > bestScore) {
//            bestScore = score30;
//            bestDirChar = bestDir[hashedEdgeLoc30];
//        }
//
//        MapLocation edgeLoc31 = selfLoc.translate(3, -2);
//        int hashedEdgeLoc31 = MapLocationUtil.hashMapLocation(edgeLoc31);
//        int minDist31 = Math.min(minDistFromStart[hashedEdgeLoc31], minDistFromStartCurrentReached[hashedEdgeLoc31]);
//        double score31 = (currDist - Math.sqrt(edgeLoc31.distanceSquaredTo(target))) / minDist31;
//        if (score31 > bestScore) {
//            bestScore = score31;
//            bestDirChar = bestDir[hashedEdgeLoc31];
//        }
//
//        MapLocation edgeLoc32 = selfLoc.translate(3, -1);
//        int hashedEdgeLoc32 = MapLocationUtil.hashMapLocation(edgeLoc32);
//        int minDist32 = Math.min(minDistFromStart[hashedEdgeLoc32], minDistFromStartCurrentReached[hashedEdgeLoc32]);
//        double score32 = (currDist - Math.sqrt(edgeLoc32.distanceSquaredTo(target))) / minDist32;
//        if (score32 > bestScore) {
//            bestScore = score32;
//            bestDirChar = bestDir[hashedEdgeLoc32];
//        }
//
//        MapLocation edgeLoc33 = selfLoc.translate(3, 1);
//        int hashedEdgeLoc33 = MapLocationUtil.hashMapLocation(edgeLoc33);
//        int minDist33 = Math.min(minDistFromStart[hashedEdgeLoc33], minDistFromStartCurrentReached[hashedEdgeLoc33]);
//        double score33 = (currDist - Math.sqrt(edgeLoc33.distanceSquaredTo(target))) / minDist33;
//        if (score33 > bestScore) {
//            bestScore = score33;
//            bestDirChar = bestDir[hashedEdgeLoc33];
//        }
//
//        MapLocation edgeLoc34 = selfLoc.translate(3, 2);
//        int hashedEdgeLoc34 = MapLocationUtil.hashMapLocation(edgeLoc34);
//        int minDist34 = Math.min(minDistFromStart[hashedEdgeLoc34], minDistFromStartCurrentReached[hashedEdgeLoc34]);
//        double score34 = (currDist - Math.sqrt(edgeLoc34.distanceSquaredTo(target))) / minDist34;
//        if (score34 > bestScore) {
//            bestScore = score34;
//            bestDirChar = bestDir[hashedEdgeLoc34];
//        }
//
//        MapLocation edgeLoc35 = selfLoc.translate(3, 3);
//        int hashedEdgeLoc35 = MapLocationUtil.hashMapLocation(edgeLoc35);
//        int minDist35 = Math.min(minDistFromStart[hashedEdgeLoc35], minDistFromStartCurrentReached[hashedEdgeLoc35]);
//        double score35 = (currDist - Math.sqrt(edgeLoc35.distanceSquaredTo(target))) / minDist35;
//        if (score35 > bestScore) {
//            bestScore = score35;
//            bestDirChar = bestDir[hashedEdgeLoc35];
//        }
//
//        MapLocation edgeLoc36 = selfLoc.translate(4, -2);
//        int hashedEdgeLoc36 = MapLocationUtil.hashMapLocation(edgeLoc36);
//        int minDist36 = Math.min(minDistFromStart[hashedEdgeLoc36], minDistFromStartCurrentReached[hashedEdgeLoc36]);
//        double score36 = (currDist - Math.sqrt(edgeLoc36.distanceSquaredTo(target))) / minDist36;
//        if (score36 > bestScore) {
//            bestScore = score36;
//            bestDirChar = bestDir[hashedEdgeLoc36];
//        }
//
//        MapLocation edgeLoc37 = selfLoc.translate(4, -1);
//        int hashedEdgeLoc37 = MapLocationUtil.hashMapLocation(edgeLoc37);
//        int minDist37 = Math.min(minDistFromStart[hashedEdgeLoc37], minDistFromStartCurrentReached[hashedEdgeLoc37]);
//        double score37 = (currDist - Math.sqrt(edgeLoc37.distanceSquaredTo(target))) / minDist37;
//        if (score37 > bestScore) {
//            bestScore = score37;
//            bestDirChar = bestDir[hashedEdgeLoc37];
//        }
//
//        MapLocation edgeLoc38 = selfLoc.translate(4, 0);
//        int hashedEdgeLoc38 = MapLocationUtil.hashMapLocation(edgeLoc38);
//        int minDist38 = Math.min(minDistFromStart[hashedEdgeLoc38], minDistFromStartCurrentReached[hashedEdgeLoc38]);
//        double score38 = (currDist - Math.sqrt(edgeLoc38.distanceSquaredTo(target))) / minDist38;
//        if (score38 > bestScore) {
//            bestScore = score38;
//            bestDirChar = bestDir[hashedEdgeLoc38];
//        }
//
//        MapLocation edgeLoc39 = selfLoc.translate(4, 1);
//        int hashedEdgeLoc39 = MapLocationUtil.hashMapLocation(edgeLoc39);
//        int minDist39 = Math.min(minDistFromStart[hashedEdgeLoc39], minDistFromStartCurrentReached[hashedEdgeLoc39]);
//        double score39 = (currDist - Math.sqrt(edgeLoc39.distanceSquaredTo(target))) / minDist39;
//        if (score39 > bestScore) {
//            bestScore = score39;
//            bestDirChar = bestDir[hashedEdgeLoc39];
//        }
//
//        MapLocation edgeLoc40 = selfLoc.translate(4, 2);
//        int hashedEdgeLoc40 = MapLocationUtil.hashMapLocation(edgeLoc40);
//        int minDist40 = Math.min(minDistFromStart[hashedEdgeLoc40], minDistFromStartCurrentReached[hashedEdgeLoc40]);
//        double score40 = (currDist - Math.sqrt(edgeLoc40.distanceSquaredTo(target))) / minDist40;
//        if (score40 > bestScore) {
//            bestScore = score40;
//            bestDirChar = bestDir[hashedEdgeLoc40];
//        }
//
//        if (bestScore > 0) {
//            return getDirectionFromChar(bestDirChar);
//        }
//        return null;
//    }
//
//    private static Direction getDirectionFromChar(char c) {
//        switch (c) {
//            case '0':
//                return Direction.NORTH;
//            case '1':
//                return Direction.NORTHEAST;
//            case '2':
//                return Direction.EAST;
//            case '3':
//                return Direction.SOUTHEAST;
//            case '4':
//                return Direction.SOUTH;
//            case '5':
//                return Direction.SOUTHWEST;
//            case '6':
//                return Direction.WEST;
//            case '7':
//                return Direction.NORTHWEST;
//            default:
//                return Direction.CENTER;
//        }
//    }
//}
