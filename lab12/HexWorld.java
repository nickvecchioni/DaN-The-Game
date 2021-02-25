package byow.lab12;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static void fillInLevel(TETile[][] world, int x, int y, int s, TETile t) {
        for (int i = 0; i < s; i++) {
            world[x + i][y] = t;
        }
    }

    // Position p specifies the lower left corner of the hexagon.
    public static void addHexagon(TETile[][] world, int x, int y, int s, TETile t) {
        HexWorld hex = new HexWorld();
        hex.addLowerandUpperHalf(world, x, y, s, t);
    }

    public static void drawTessalationOfHexagons(TETile[][] world, int x, int y, int s, TETile t) {
        drawLeftMostTessalation(world, x, y, s, t);
        drawSecondLeftMostTessalation(world, x, y, s, Tileset.WATER);
        drawMiddleTessalation(world, x, y, s, Tileset.FLOWER);
        drawSecondRightMostTessalation(world, x, y, s, Tileset.SAND);
        drawRightMostTessalation(world, x, y, s, Tileset.MOUNTAIN);
    }

    /* Below code isn't good. Far too repetitive. */

    // move left 2 * s - 1
    // move up s
    private static void drawLeftMostTessalation(TETile[][] world, int x, int y, int s, TETile t) {
        x -= (3 * s + 1);
        y += 2 * s;

        for(int i = 1; i <= 3; i++) {
            addHexagon(world, x, y, s, t);
            y += 2 * s;
        }
    }

    private static void drawSecondLeftMostTessalation(TETile[][] world, int x, int y, int s, TETile t) {
        x -= (2 * s - 1);
        y +=  s;

        for(int i = 1; i <= 4; i++) {
            addHexagon(world, x, y, s, t);
            y += 2 * s;
        }
    }

    private static void drawMiddleTessalation(TETile[][] world, int x, int y, int s, TETile t) {
        for(int i = 1; i <= 5; i++) {
            addHexagon(world, x, y, s, t);
            y += 2 * s;
        }
    }

    private static void drawSecondRightMostTessalation(TETile[][] world, int x, int y, int s, TETile t) {
        x += (2 * s - 1);
        y +=  s;

        for(int i = 1; i <= 4; i++) {
            addHexagon(world, x, y, s, t);
            y += 2 * s;
        }
    }

    private static void drawRightMostTessalation(TETile[][] world, int x, int y, int s, TETile t) {
        x += (3 * s + 1);
        y += 2 * s;

        for(int i = 1; i <= 3; i++) {
            addHexagon(world, x, y, s, t);
            y += 2 * s;
        }
    }


    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        drawTessalationOfHexagons(world, 20, 5, 2, Tileset.GRASS);

        ter.renderFrame(world);
    }

    private void addLowerandUpperHalf(TETile[][] world, int x, int y, int s, TETile t) {
        int width = s;
        for (int i = 0; i < s - 1; i++) {
            fillInLevel(world, x, y, width, t);
            x -= 1;
            y += 1;
            width += 2;
        }
        fillInLevel(world, x, y, width, t);
        y += 1;

        for(int i = 0; i < s; i++) {
            fillInLevel(world, x, y, width, t);
            x += 1;
            y += 1;
            width -= 2;
        }
    }

}
