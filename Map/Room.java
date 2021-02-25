package byow.Map;

import byow.Core.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Room {

    private static List<Room> rooms = new ArrayList<>();
    private Position upperLeftCorner;
    private Position lowerRightCorner;

    public Room(Position upperLeftCorner, Position lowerRightCorner) {
        this.upperLeftCorner = upperLeftCorner;
        this.lowerRightCorner = lowerRightCorner;
        rooms.add(this);
    }

    public static List<Room> getRooms() {
        return rooms;
    }

    public static void clearRooms() {
        rooms = new ArrayList<>();
    }

    public Position getUpperLeftCorner() {
        return upperLeftCorner;
    }

    public Position getLowerRightCorner() {
        return lowerRightCorner;
    }

    // Returns whether the created room overlaps any pre-existing rooms.
    public boolean overLap() {
        for (Room room : rooms) {
            if (room == this) {
                continue;
            }
            if (getUpperLeftCorner().getX()
                    > room.getLowerRightCorner().getX()// R1 is right to R2
                    || getLowerRightCorner().getX()
                    < room.getUpperLeftCorner().getX() // R1 is left to R2
                    || getUpperLeftCorner().getY()
                    < room.getLowerRightCorner().getY() // R1 is above R2
                    || getLowerRightCorner().getY()
                    > room.getUpperLeftCorner().getY()) { // R1 is below R1
                int x = 1;
            } else {
                rooms.remove(this);
                return true;
            }
        }
        return false;
    }

    public SpecialPosition getRandomEdgePosition(Random r) {
        Position upperLeftOfRoom = getUpperLeftCorner();
        Position lowerRightOfRoom = getLowerRightCorner();
        int xUL = upperLeftOfRoom.getX();
        int yUL = upperLeftOfRoom.getY();
        int xLR = lowerRightOfRoom.getX();
        int yLR = lowerRightOfRoom.getY();

        int randomX, randomY;

        if (xUL != xLR && yUL != yLR) {
            if (xUL < xLR) {
                randomX = RandomUtils.uniform(r, xUL, xLR - 1);
            } else {
                randomX = RandomUtils.uniform(r, xLR, xUL - 1);
            }

            if (yUL < yLR) {
                randomY = RandomUtils.uniform(r, yUL, yLR - 1);
            } else {
                randomY = RandomUtils.uniform(r, yLR, yUL - 1);
            }

            double halfAndHalf = RandomUtils.uniform(r);

            // Return upper edge position.
            if (halfAndHalf < 0.25) {
                return new SpecialPosition(randomX, yUL, 1);
            } else if (halfAndHalf < 0.5) { // Return lower edge position.
                return new SpecialPosition(randomX, yLR, 2);
            } else if (halfAndHalf < 0.75) { // Return left edge position.
                return new SpecialPosition(xUL, randomY, 3);
            } else { // Return right edge position.
                return new SpecialPosition(xLR, randomY, 4);
            }
        } else {
            return null;
        }
    }
}
