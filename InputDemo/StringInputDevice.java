package byow.InputDemo;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

/**
 * Created by hug.
 */
public class StringInputDevice implements InputSource  {
    private String input;
    private int index;
    private TETile[][] world = null;

    public StringInputDevice(String s) {
        index = 0;
        input = s;
    }

    public StringInputDevice(String s, TETile[][] world) {
        index = 0;
        input = s;
        this.world = world;
    }

    public char getNextKey() {
      /*  int mouseX = (int) StdDraw.mouseX();
        int mouseY = (int) StdDraw.mouseY();

        if (world != null) {
            if (mouseX < world.length && mouseY < world[0].length) {
                {
                    StdDraw.setPenColor(Color.BLACK);
                    StdDraw.filledRectangle(0, world[0].length, 10, 2);
                    StdDraw.setPenColor(Color.WHITE);
                    if (world[mouseX][mouseY].equals(Tileset.WALL)) {
                        StdDraw.text(world.length * .05, world[0].length, "Wall");
                    }
                    else if (world[mouseX][mouseY].equals(Tileset.FLOOR)) {
                        StdDraw.text(world.length * .05, world[0].length, "Floor");
                    } else if (world[mouseX][mouseY].equals(Tileset.NOTHING)) {
                        StdDraw.text(world.length * .05, world[0].length, "Nothing");
                    } else if (world[mouseX][mouseY].equals(Tileset.AVATAR)) {
                        StdDraw.text(world.length * .05, world[0].length, "DaN");
                    }
                    StdDraw.show();
                }
            }
        } */
        char returnChar = input.charAt(index);
        index += 1;
        return returnChar;
    }

    public boolean possibleNextInput() {
        return index < input.length();
    }
}
