package byow.Map;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

import java.util.Random;

public class MapGeneratorTest {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;

    private static long SEED;
    private static Random RANDOM;

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        Random r = new Random(SEED);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];

        // fill in nothing.
        MapGenerator.fillInNothingness(world, WIDTH, HEIGHT);

        MapGenerator.buildPotentiallyRandomRoom(world, null, 0, r);
        // draws the world to the screen
        ter.renderFrame(world);
    }
}
