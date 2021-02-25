package byow.InputDemo;

/**
 * Created by hug.
 */

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import java.util.Date;

import java.awt.*;

public class KeyboardInputSource implements InputSource {
    private static final boolean PRINT_TYPED_KEYS = false;
    private TETile[][] world;

    public KeyboardInputSource(TETile[][] world) {
        this.world = world;
    }

    public KeyboardInputSource() {
        this.world = null;
    }

    public char getNextKey() {
        char c;
        while (true) {
            int mouseX = (int) StdDraw.mouseX();
            int mouseY = (int) StdDraw.mouseY();

            if (world != null) {
                if (mouseX < world.length && mouseY < world[0].length) {
                    {

                        StdDraw.setPenColor(Color.BLACK);
                        StdDraw.filledRectangle(0, world[0].length, 10, 2);
                        StdDraw.setPenColor(Color.WHITE);
                        if (world[mouseX][mouseY].equals(Tileset.WALL)) {
                            StdDraw.text(world.length * .05, world[0].length, "Wall");
                        } else if (world[mouseX][mouseY].equals(Tileset.FLOOR)) {
                            StdDraw.text(world.length * .05, world[0].length, "Floor");
                        } else if (world[mouseX][mouseY].equals(Tileset.NOTHING)) {
                            StdDraw.text(world.length * .05, world[0].length, "The Void");
                        } else if (world[mouseX][mouseY].equals(Tileset.AVATAR)) {
                            StdDraw.text(world.length * .05, world[0].length, "DaN");
                        } else if (world[mouseX][mouseY].equals(Tileset.GRASS)) {
                            StdDraw.text(world.length * .05, world[0].length, "come gimme a hug");
                        } else if (world[mouseX][mouseY].equals(Tileset.WATER)) {
                            StdDraw.text(world.length * .05, world[0].length, "splash");
                        }

                        Date date = new Date();
                        String dateString = date.toString();
                        StdDraw.text(world.length / 1.15, world[0].length, dateString);
                        StdDraw.show();
                        StdDraw.setPenColor(Color.black);
                        StdDraw.filledRectangle(world.length / 1.1, world[0].length, 12, 1);
                    }
                }


            }
            if (StdDraw.hasNextKeyTyped()) {
                c = Character.toUpperCase(StdDraw.nextKeyTyped());
                if (PRINT_TYPED_KEYS) {
                    System.out.print(c);
                }
                return c;
            }
        }
    }

    public boolean possibleNextInput() {
        return true;
    }
}
