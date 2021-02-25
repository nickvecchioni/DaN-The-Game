package byow.Map;
top
import byow.Core.RandomUtils;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class MapGenerator {

    public static void fillInNothingness(TETile[][] world, int width, int height) {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    private static void buildRoomWalls(TETile[][] world, Room room) {
        int upperLeftCornerX = room.getUpperLeftCorner().getX();
        int upperLeftCornerY = room.getUpperLeftCorner().getY();
        int lowerRightCornerX = room.getLowerRightCorner().getX();
        int lowerRightCornerY = room.getLowerRightCorner().getY();

        if (upperLeftCornerX > 0 && lowerRightCornerX < world.length
                && lowerRightCornerY > 0 && upperLeftCornerY < world[0].length) {
            for (int x = upperLeftCornerX; x <= lowerRightCornerX; x++) {
                world[x][upperLeftCornerY] = Tileset.WALL;
                world[x][lowerRightCornerY] = Tileset.WALL;
            }

            for (int y = lowerRightCornerY; y <= upperLeftCornerY; y++) {
                world[upperLeftCornerX][y] = Tileset.WALL;
                world[lowerRightCornerX][y] = Tileset.WALL;
            }
        }
    }

    private static boolean buildRoomFloor(TETile[][] world, Room room) {
        int upperLeftCornerX = room.getUpperLeftCorner().getX();
        int upperLeftCornerY = room.getUpperLeftCorner().getY();
        int lowerRightCornerX = room.getLowerRightCorner().getX();
        int lowerRightCornerY = room.getLowerRightCorner().getY();
        boolean isBuilt = false;

        if (upperLeftCornerX > 0 && lowerRightCornerX < world.length
                && lowerRightCornerY > 0 && upperLeftCornerY < world[0].length) {
            isBuilt = true;
            for (int x = upperLeftCornerX; x <= lowerRightCornerX; x++) {
                for (int y = lowerRightCornerY; y <= upperLeftCornerY; y++) {
                    world[x][y] = Tileset.FLOOR;
                }
            }
        }
        return isBuilt;
    }

    public static boolean buildRoom(TETile[][] world, Room room, Random r) {
        boolean isBuilt = false;
        if (!room.overLap()) {
            isBuilt = buildRoomFloor(world, room);
            buildRoomWalls(world, room);
            buildHallway(world, room, r);
        }
        return isBuilt;
    }

    private static void cleanRoomToHallway(TETile[][] world, Room hallway, int orientation) {
        int xUL = hallway.getUpperLeftCorner().getX();
        int yUL = hallway.getUpperLeftCorner().getY();
        int xLR = hallway.getLowerRightCorner().getX();
        int yLR = hallway.getLowerRightCorner().getY();

        if (xUL >= 0 && xLR <= world.length - 1
                && yLR >= 0 && yUL <= world[0].length - 1) {
            if (orientation == 1) {
                world[xLR - 1][yLR] = Tileset.FLOOR;
                world[xLR - 1][yLR - 1] = Tileset.FLOOR;
            } else if (orientation == 2) {
                world[xUL + 1][yUL] = Tileset.FLOOR;
                world[xUL + 1][yUL + 1] = Tileset.FLOOR;
            } else if (orientation == 3) {
                world[xLR][yLR + 1] = Tileset.FLOOR;
                world[xLR + 1][yLR + 1] = Tileset.FLOOR;
            } else {
                world[xUL][yUL - 1] = Tileset.FLOOR;
                world[xUL - 1][yUL - 1] = Tileset.FLOOR;
            }
        }
    }

    /*public static void buildHallwayFromHallway(TETile[][] world,
    Room hallway, Random r, int orientation) {

        int lengthOfHallway = r.nextInt(5) + 5;
        Room adjacentHallway;
        int newOrientation;
        Position newUL, newLR;
        int xUL = hallway.getUpperLeftCorner().getX();
        int yUL = hallway.getUpperLeftCorner().getY();
        int xLR = hallway.getLowerRightCorner().getX();
        int yLR = hallway.getLowerRightCorner().getY();

        if (orientation == 1) {
            newUL = hallway.getUpperLeftCorner();
            newLR = new Position(xUL + lengthOfHallway, yUL - 2);
            newOrientation = 4;
        }  else if (orientation == 2) {
            newUL = new Position(xLR - 2, yLR + 2);
            newLR = new Position(xLR + lengthOfHallway, yLR);
            newOrientation = 4;
        } else if (orientation == 3) {
            newUL = hallway.getUpperLeftCorner();
            newLR = new Position(xUL + 2, yUL - lengthOfHallway);
            newOrientation = 2;
        } else {
            newUL = new Position(xLR - 2, yLR + 2);
            newLR = new Position(xLR, yLR - lengthOfHallway);
            newOrientation = 2;
        }

        adjacentHallway = new Room(newUL, newLR);
        buildRoom(world, adjacentHallway, r);
        buildPotentiallyRandomRoom(world, adjacentHallway, newOrientation, r);
    } */

    public static void buildHallway(TETile[][] world, Room room, Random r) {
        int numOfHalls = 20;

        for (int i = 1; i <= numOfHalls; i++) {
            SpecialPosition randomPosOnRoom = room.getRandomEdgePosition(r);
            int randomPosX = randomPosOnRoom.getX();
            int randomPosY = randomPosOnRoom.getY();
            int orientation = randomPosOnRoom.getOrientation();
            int lengthOfHallway = r.nextInt(5) + 2;
            Position ul, lr;

            if (orientation == 1) {
                ul = new Position(randomPosX, randomPosY + lengthOfHallway);
                lr = new Position(randomPosX + 2, randomPosY + 1);
            } else if (orientation == 2) {
                ul = new Position(randomPosX, randomPosY - 1);
                lr = new Position(randomPosX + 2, randomPosY - lengthOfHallway);
            } else if (orientation == 3) {
                ul = new Position(randomPosX - lengthOfHallway, randomPosY + 2);
                lr = new Position(randomPosX - 1, randomPosY);
            } else {
                ul = new Position(randomPosX + 1, randomPosY + 2);
                lr = new Position(randomPosX + lengthOfHallway, randomPosY);
            }

            Room hallway = new Room(ul, lr);

            if (!hallway.overLap()) {
                buildRoomFloor(world, hallway);
                buildRoomWalls(world, hallway);
                if (hallway.getUpperLeftCorner().getX() > 14
                        && hallway.getLowerRightCorner().getX() < world.length - 14
                        && hallway.getLowerRightCorner().getY() > 15
                        && hallway.getUpperLeftCorner().getY() < world[0].length - 14) {
                        buildPotentiallyRandomRoom(world, hallway, orientation, r);


                }
                cleanRoomToHallway(world, hallway, orientation);
            }
        }
    }

    public static boolean buildPotentiallyRandomRoom(TETile[][] world,
                                                  Room hallway, int orientation, Random r) {
        int width, height, xCoord1, xCoord2, yCoord1, yCoord2;
        boolean isBuilt;
        width = RandomUtils.uniform(r, 4) + 4;
        height = RandomUtils.uniform(r, 4) + 4;

        // Upper Left = (xCoord2, yCoord1)
        // Lower Right = (xCoord1, yCoord2)
        if (hallway == null) {
            xCoord1 = world.length / 2;
            yCoord1 = world[0].length / 2 + 5;
            xCoord2 = xCoord1 - width;
            yCoord2 = yCoord1 - height;
        } else {
            int xULHallway = hallway.getUpperLeftCorner().getX();
            int yULHallway = hallway.getUpperLeftCorner().getY();
            int xLRHallway = hallway.getLowerRightCorner().getX();
            int yLRHallway = hallway.getLowerRightCorner().getY();

            if (orientation == 1) {
                xCoord1 = xULHallway + 2;
                yCoord2 = yULHallway + 1;
                xCoord2 = xCoord1 - width;
                yCoord1 = yCoord2 + height;
            } else if (orientation == 2) {
                xCoord2 = xULHallway - 2;
                yCoord1 = yLRHallway - 1;
                xCoord1 = xCoord2 + width;
                yCoord2 = yCoord1 - height;
            } else if (orientation == 3) {
                xCoord1 = xULHallway - 1;
                yCoord2 = yLRHallway - 2;
                xCoord2 = xCoord1 - width;
                yCoord1 = yCoord2 + height;
            } else {
                xCoord2 = xLRHallway + 1;
                yCoord1 = yULHallway + 2;
                xCoord1 = xCoord2 + width;
                yCoord2 = yCoord1 - height;
            }
        }

        Position ul = new Position(xCoord2, yCoord1);
        Position lr = new Position(xCoord1, yCoord2);

        Room room = new Room(ul, lr);
        isBuilt = buildRoom(world, room, r);
        if (isBuilt) {
            if (hallway != null) {
                if (orientation == 1) {
                    cleanRoomToHallway(world, hallway, 2);
                } else if (orientation == 2) {
                    cleanRoomToHallway(world, hallway, 1);
                } else if (orientation == 3) {
                    cleanRoomToHallway(world, hallway, 4);
                } else {
                    cleanRoomToHallway(world, hallway, 3);
                }
            }
        }
        return isBuilt;
    }
}
