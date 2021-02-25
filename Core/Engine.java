package byow.Core;

import byow.InputDemo.InputSource;
import byow.InputDemo.KeyboardInputSource;
import byow.InputDemo.StringInputDevice;
import byow.Map.MapGenerator;
import byow.Map.Room;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Font;
import java.awt.Color;


public class Engine {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 45;
    private static boolean isReplay = false;
    private String keyStrokeHistory = "";
    TERenderer ter = new TERenderer();


    private static void displayMainScreen() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 60);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(Color.WHITE);

        StdDraw.text(WIDTH / 2.0, HEIGHT / 1.4, "DaN the Game");
        font = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0, "New Game (N)");
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.5, "Load Game (L)");
        StdDraw.text(WIDTH / 2.0, HEIGHT / 8.0, "Quit (Q)");
        StdDraw.text(WIDTH / 2.0, HEIGHT / 4.7, "Replay (R)");
        StdDraw.text(WIDTH / 2.0, HEIGHT / 3.3, "The Lore (P)");

        StdDraw.show();
    }

    private void displayLore() {
        StdDraw.clear(Color.black);
        Font font = new Font("Comic Sans MS", Font.BOLD, 20);
        StdDraw.setFont(font);
       /* StdDraw.picture(WIDTH / 2.0, HEIGHT / 2.0, "./joshhug.jpg", WIDTH, HEIGHT);
        StdDraw.text(WIDTH/2.0, HEIGHT / 1.7, "Hug Me"); */



        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(WIDTH / 2.0, HEIGHT - HEIGHT / 13.0, "A twisted labyrinth of rooms and hallways,\n");
        StdDraw.text(WIDTH / 2.0, HEIGHT - 2 * HEIGHT / 13.0, "hellish torment of lost confusion.");
        StdDraw.text(WIDTH / 2.0, HEIGHT - 3 * HEIGHT / 13.0, "Dare tread within the void and wall maze,");
        StdDraw.text(WIDTH / 2.0, HEIGHT - 4 * HEIGHT / 13.0, "but fear those whom despise your intrusion.");
        StdDraw.text(WIDTH / 2.0, HEIGHT - 5 * HEIGHT / 13.0, "Leave behind your cowardice and cretin-ship");
        StdDraw.text(WIDTH / 2.0, HEIGHT - 6 * HEIGHT / 13.0, "And perhaps you’ll discover a new self worth.");
        StdDraw.text(WIDTH / 2.0, HEIGHT - 7 * HEIGHT / 13.0, "Continue moving to progress bit by bit,");
        StdDraw.text(WIDTH / 2.0, HEIGHT - 8 * HEIGHT / 13.0, "yet be weary of the traps that thwart.");
        StdDraw.text(WIDTH / 2.0, HEIGHT - 9 * HEIGHT / 13.0, "No mere mortal can survive this game,");
        StdDraw.text(WIDTH / 2.0, HEIGHT - 10 * HEIGHT / 13.0, "Only those of true greater fame.");
        StdDraw.text(WIDTH / 2.0, HEIGHT - 11 * HEIGHT / 13.0, "A hero amongst us prevails, glory to the man.");
        StdDraw.text(WIDTH / 2.0, HEIGHT - 12 * HEIGHT / 13.0, "Beware as you enter but welcome, to the Game of DaN.");

        font = new Font("Monaco", Font.BOLD, 10);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH/10.0, HEIGHT / 18.0, "Press 'M' to return to main menu");

        StdDraw.show();

        InputSource keyboardInput = new KeyboardInputSource();
        char nextChar = keyboardInput.getNextKey();

        while (nextChar != 'M') {
            nextChar = keyboardInput.getNextKey();
            nextChar = Character.toUpperCase(nextChar);
        }
        interactWithKeyboard();
    }

    private static TETile[][] drawInitialWorld(String seed) {
        TETile[][] world = null;
        Random r = new Random(Long.parseLong(seed));
        TERenderer ter = new TERenderer();
         ter.initialize(WIDTH, HEIGHT + 2);
        //initialize tiles
        world = new TETile[WIDTH][HEIGHT];

        // fill in nothing.
        MapGenerator.fillInNothingness(world, WIDTH, HEIGHT);

        MapGenerator.buildPotentiallyRandomRoom(world, null, 0, r);
        // draws the world to the screen

        ter.renderFrame(world);

        return world;
    }

    private static void getInitialAvatarWorld(TETile[][] world) {
        for (int i = 0; i < world.length; i += 1) {
            for (int j = 0; j < world[0].length; j += 1) {
                if (world[i][j] == Tileset.FLOOR) {
                    world[i][j] = Tileset.AVATAR;
                    return;
                }
            }

        }
    }

    private static void addClosedDoor(TETile[][] world) {
        for (int i = world.length - 1; i >= 0; i -= 1) {
            for (int j = 0; j < world[0].length; j += 1) {
                if (world[i][j] == Tileset.FLOOR) {
                    world[i][j] = Tileset.UNLOCKED_DOOR;
                    return;
                }
            }

        }
    }

    private void youLose() {
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(new Font("Comic Sans MS", Font.BOLD, 60));
        StdDraw.text(WIDTH / 2.0, HEIGHT / 1.9, "You Suck!");
        StdDraw.setFont(new Font("Comic Sans MS", Font.BOLD, 10));
        StdDraw.text(WIDTH/10.0, HEIGHT / 18.0, "Press 'M' to return to main menu");

        StdDraw.show();

        InputSource keyboardInput = new KeyboardInputSource();
        char nextChar2 = keyboardInput.getNextKey();

        while (nextChar2 != 'M') {
            nextChar2 = keyboardInput.getNextKey();
            nextChar2 = Character.toUpperCase(nextChar2);
        }
        interactWithKeyboard();
    }

    private void easterEgg(){
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(new Font("Comic Sans MS", Font.BOLD, 60));
        StdDraw.picture(WIDTH / 2.0, HEIGHT / 2.0, "./joshhug.jpg", WIDTH, HEIGHT);
        StdDraw.text(WIDTH/2.0, HEIGHT / 1.7, "Hug Me");
        StdDraw.setFont(new Font("Comic Sans MS", Font.BOLD, 10));
        StdDraw.text(WIDTH/10.0, HEIGHT / 18.0, "Press 'M' to return to main menu");

        StdDraw.show();

        InputSource keyboardInput = new KeyboardInputSource();
        char nextChar2 = keyboardInput.getNextKey();

        while (nextChar2 != 'M') {
            nextChar2 = keyboardInput.getNextKey();
            nextChar2 = Character.toUpperCase(nextChar2);
        }


        interactWithKeyboard();
    }

    private static Point locationOfAvatar(TETile[][] world) {
        for (int i = 0; i < world.length; i += 1) {
            for (int j = 0; j < world[0].length; j += 1) {
                if (world[i][j] == Tileset.AVATAR) {
                    return new Point(i, j);
                }
            }

        }
        return null;
    }

    private static void addWaterToWorld(TETile[][] world) {
        for(int i = 0; i < world.length; i += 1) {
            for(int j = 0; j < world[0].length; j += 1) {
                Random r = new Random();
                double rand = r.nextDouble();

                if (world[i][j] == Tileset.FLOOR && rand > 0.975) {
                    world[i][j] = Tileset.WATER;
                }
            }
        }
    }

    private static void addFlowerToWorld(TETile[][] world) {
        for(int i = 0; i < world.length; i += 1) {
            for(int j = 0; j < world[0].length; j += 1) {
                Random r = new Random();
                double rand = r.nextDouble();

                if (world[i][j] == Tileset.FLOOR && rand > 0.975) {
                    world[i][j] = Tileset.FLOWER;
                }
            }
        }
    }


    private static void addGrassToWorld(TETile[][] world) { // Doug is allergic to grass.
        for(int i = 0; i < world.length; i += 1) {
            for(int j = 0; j < world[0].length; j += 1) {
                Random r = new Random();
                double rand = r.nextDouble();

                if (world[i][j] == Tileset.FLOOR && rand > 0.96) {
                    world[i][j] = Tileset.GRASS;
                }
            }
        }
    }

    private String takeInSeedInput(InputSource input) {
        String seed = "";
          StdDraw.clear(Color.black);
          StdDraw.text(WIDTH / 2.0, HEIGHT / 1.6, "Enter a Seed. Press S to Begin.");
         StdDraw.show();
        char nextLetter = input.getNextKey();

        while (nextLetter != 'S' ) {
            if (Character.isDigit(nextLetter)) {
                keyStrokeHistory += nextLetter;
                seed += nextLetter;
                StdDraw.clear(Color.black);
                StdDraw.text(WIDTH / 2.0, HEIGHT / 1.6, "Enter a Seed. Press S to Begin.");
                StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0, seed);
                StdDraw.show();
            }
            nextLetter = input.getNextKey();
        }
        keyStrokeHistory += nextLetter;
        return seed;
    }

    private void playRestOfGame(TETile[][] world, InputSource input) {
        TERenderer ter = new TERenderer();
        char nextChar = 'b';
        char prevChar;
        int avatarX, avatarY;
        Point locationOfAvatar;
        getInitialAvatarWorld(world);
        addClosedDoor(world);
        addWaterToWorld(world);
        addGrassToWorld(world);
        addFlowerToWorld(world);
         ter.renderFrame(world);
        locationOfAvatar = locationOfAvatar(world);
        int grassCount = 0;
        int bubbles = 5;
        int flowerCount = 0;

        avatarX = (int) locationOfAvatar.getX();
        avatarY = (int) locationOfAvatar.getY();


        StdDraw.setPenColor(Color.white);

        while (true) {
            prevChar = nextChar;
            StdDraw.setPenColor(Color.green);
            StdDraw.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
            StdDraw.text(WIDTH / 25.0, HEIGHT / 1.1, "grass count: " + grassCount);
            StdDraw.setPenColor(Color.blue);
            StdDraw.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
            StdDraw.text(WIDTH / 25.0, HEIGHT / 1.2, "lighter count: " + bubbles);
            StdDraw.setPenColor(Color.pink);
            StdDraw.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
            StdDraw.text(WIDTH / 25.0, HEIGHT / 1.3, "flower count: " + flowerCount);
            StdDraw.setPenColor(Color.CYAN);
            StdDraw.setFont(new Font("Monaco", Font.BOLD, 12));
            StdDraw.text(WIDTH / 10.0, HEIGHT / 6, "press 'M' to return to main menu");

            StdDraw.show();
            if (grassCount * bubbles > 30 && flowerCount < 3) {
                for(int i = 0; i < world.length; i += 1) {
                    for(int j = 0; j < world[0].length; j += 1) {
                        Random r = new Random();
                        double rand = r.nextDouble();

                        if (world[i][j] == Tileset.FLOOR && rand > 0.99) {
                            world[i][j] = Tileset.MOUNTAIN;
                        }
                    }
                }

            }
            if (input.possibleNextInput()) {
                nextChar = input.getNextKey();
                keyStrokeHistory += nextChar;
            } else {
                input = new KeyboardInputSource(world);
            }

            if (world[avatarX + 1][avatarY] == Tileset.MOUNTAIN
                && world[avatarX][avatarY + 1] == Tileset.MOUNTAIN
                && world[avatarX - 1][avatarY] == Tileset.MOUNTAIN
                && world[avatarX][avatarY - 1] == Tileset.MOUNTAIN) {
                youLose();
            }
            if (nextChar == 'W') {
                if (world[avatarX][avatarY + 1] == Tileset.FLOOR
                        || world[avatarX][avatarY + 1] == Tileset.GRASS
                        || world[avatarX][avatarY + 1] == Tileset.WATER
                        || world[avatarX][avatarY + 2] == Tileset.FLOWER) {
                    if (world[avatarX][avatarY + 2] == Tileset.FLOWER) {
                        world[avatarX][avatarY] = Tileset.FLOOR;
                        flowerCount += 1;
                        avatarY += 1;
                    }
                    if (world[avatarX][avatarY + 1] == Tileset.GRASS) {
                        grassCount += 1;
                        if (grassCount > 10) {
                            easterEgg();
                            break;
                        }
                    }
                    if (world[avatarX][avatarY + 1] == Tileset.WATER) {
                        bubbles -= 1;
                        if (bubbles == 0) {
                            youLose();
                            break;
                        }
                    }
                    world[avatarX][avatarY + 1] = Tileset.AVATAR;
                    world[avatarX][avatarY] = Tileset.FLOOR;
                    avatarY += 1;
                } else if (world[avatarX][avatarY + 1] == Tileset.WALL) {
                    youLose();
                    break;
                }
            } else if (nextChar == 'A') {
                if (world[avatarX - 1][avatarY] == Tileset.FLOOR
                        || world[avatarX - 1][avatarY] == Tileset.GRASS
                        || world[avatarX - 1][avatarY] == Tileset.WATER
                        || world[avatarX - 2][avatarY] == Tileset.FLOWER) {
                        if (world[avatarX - 2][avatarY] == Tileset.FLOWER) {
                            world[avatarX][avatarY] = Tileset.FLOOR;
                            flowerCount += 1;
                            avatarX -= 1;
                        }
                    if (world[avatarX - 1][avatarY] == Tileset.GRASS) {
                        grassCount += 1;
                        if (grassCount > 10) {
                            easterEgg();
                            break;
                        }
                        if (world[avatarX - 1][avatarY] == Tileset.WATER) {
                            bubbles -= 1;
                            if (bubbles == 0) {
                                youLose();
                                break;
                            }
                        }
                    }
                    world[avatarX - 1][avatarY] = Tileset.AVATAR;
                    world[avatarX][avatarY] = Tileset.FLOOR;
                    avatarX -= 1;

                } else if (world[avatarX - 1][avatarY] == Tileset.WALL) {
                    youLose();
                    break;
                }
            } else if (nextChar == 'S') {
                if (world[avatarX][avatarY - 1] == Tileset.FLOOR
                        || world[avatarX][avatarY - 1] == Tileset.GRASS
                        || world[avatarX][avatarY - 1] == Tileset.WATER
                        || world[avatarX][avatarY - 2] == Tileset.FLOWER) {
                    if (world[avatarX][avatarY - 2] == Tileset.FLOWER) {
                        world[avatarX][avatarY] = Tileset.FLOOR;
                        flowerCount += 1;
                        avatarY -= 1;
                    }
                    if (world[avatarX][avatarY - 1] == Tileset.GRASS) {
                        grassCount += 1;
                        if (grassCount > 10) {
                            easterEgg();
                            break;
                        }
                    }
                    if (world[avatarX][avatarY - 1] == Tileset.WATER) {
                        bubbles -= 1;
                        if (bubbles == 0) {
                            youLose();
                            break;
                        }
                    }
                    world[avatarX][avatarY - 1] = Tileset.AVATAR;
                    world[avatarX][avatarY] = Tileset.FLOOR;
                    avatarY -= 1;
                } else if (world[avatarX][avatarY - 1] == Tileset.WALL) {
                    youLose();
                    break;
                }
            } else if (nextChar == 'D') {
                if (world[avatarX + 1][avatarY] != Tileset.WALL
                        && world[avatarX + 1][avatarY] == Tileset.FLOOR
                        || world[avatarX + 1][avatarY] == Tileset.GRASS
                        || world[avatarX + 1][avatarY] == Tileset.WATER
                        || world[avatarX + 2][avatarY] == Tileset.FLOWER) {
                    if (world[avatarX + 2][avatarY] == Tileset.FLOWER) {
                        world[avatarX][avatarY] = Tileset.FLOOR;
                        flowerCount += 1;
                        avatarX += 1;
                    }
                    if (world[avatarX + 1][avatarY] == Tileset.GRASS) {
                        grassCount += 1;
                        if (grassCount > 10) {
                            easterEgg();
                            break;
                        }
                        if (world[avatarX + 1][avatarY] == Tileset.WATER) {
                            bubbles -= 1;
                            if (bubbles == 0) {
                                youLose();
                                break;
                            }
                        }
                    }
                    world[avatarX + 1][avatarY] = Tileset.AVATAR;
                    world[avatarX][avatarY] = Tileset.FLOOR;
                    avatarX += 1;
                } else if (world[avatarX + 1][avatarY] == Tileset.WALL) {
                    youLose();
                    break;
                }
            else if (world[avatarX + 1][avatarY] == Tileset.UNLOCKED_DOOR) {
                    StdDraw.clear(Color.black);
                    StdDraw.setPenColor(Color.white);
                    StdDraw.setFont(new Font("Comic Sans MS", Font.BOLD, 60));
                    StdDraw.text(WIDTH / 2.0, HEIGHT / 1.9, "You Win!");
                    StdDraw.setFont(new Font("Comic Sans MS", Font.BOLD, 10));
                    StdDraw.text(WIDTH/10.0, HEIGHT / 18.0, "Press 'M' to return to main menu");

                    StdDraw.show();

                    InputSource keyboardInput = new KeyboardInputSource();
                    char nextChar2 = keyboardInput.getNextKey();

                    while (nextChar2 != 'M') {
                        nextChar2 = keyboardInput.getNextKey();
                        nextChar2 = Character.toUpperCase(nextChar2);
                    }
                    interactWithKeyboard();
                    break;
                }
            } else if (prevChar == ':' && nextChar == 'Q') {
                keyStrokeHistory = keyStrokeHistory.substring(0, keyStrokeHistory.length() - 2);

                try (PrintStream out = new PrintStream(new FileOutputStream("previousLoad.txt"))) {
                    out.print(keyStrokeHistory);
                } catch (FileNotFoundException e) {
                    System.out.println("oops!");
                }

                 System.exit(0);
            } else if (nextChar == 'M') {
                interactWithKeyboard();
            }

            if (isReplay) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    System.out.println("o");
                }
            }
             ter.renderFrame(world);

        }
    }

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        displayMainScreen();
        String seed;
        TETile[][] world;
        InputSource keyboardInput = new KeyboardInputSource();

        char nextLetter = keyboardInput.getNextKey();
        while (nextLetter != 'N' && nextLetter != 'L' && nextLetter != 'Q'
                && nextLetter != 'R' && nextLetter != 'P') {
            nextLetter = keyboardInput.getNextKey();
        }
        if (nextLetter != 'L') {
            keyStrokeHistory += nextLetter;
        }
        if (nextLetter == 'N') {
            Room.clearRooms();
            seed = takeInSeedInput(keyboardInput);
            world = drawInitialWorld(seed);
            keyboardInput = new KeyboardInputSource(world);
            playRestOfGame(world, keyboardInput);


        } else if (nextLetter == 'L') {
            String file = "./previousLoad.txt";
            try {
                Scanner scanner = new Scanner(new File(file));
                if (scanner.hasNext()) {
                    String previousLoad = scanner.next();
                    if (!previousLoad.contains("N")) {
                        previousLoad = 'N' + previousLoad;
                    }
                    System.out.println(previousLoad);
                    interactWithInputString(previousLoad);
                } else {
                     System.exit(0);
                    System.out.println("WHY");
                }
            } catch (FileNotFoundException e) {
                System.out.println("oops");
            }
        } else if (nextLetter == 'R') {
            System.out.println(keyStrokeHistory);
            String file = "./previousLoad.txt";
            isReplay = true;
            try {
                Scanner scanner = new Scanner(new File(file));
                if (scanner.hasNext()) {
                    String previousLoad = scanner.next();
                    if (!previousLoad.contains("N")) {
                        previousLoad = "N" + previousLoad.substring(1);
                    }
                    interactWithInputString(previousLoad);
                } else {
                    System.out.println("WHY");
                     System.exit(0);
                }
            } catch (FileNotFoundException e) {
                System.out.println("oops");
            }
        } else if (nextLetter == 'P') {
            displayLore();

        } else {
            System.out.println("WHY");
             System.exit(0);
        }


    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, both of these calls:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        String upperCaseInput = input.toUpperCase();
        InputSource inputString = new StringInputDevice(upperCaseInput);
        char firstChar = inputString.getNextKey();
        String seed;
        TETile[][] world = new TETile[2][2];

        if (firstChar == 'N') {
            Room.clearRooms();
            seed = takeInSeedInput(inputString);
            world = drawInitialWorld(seed);
            playRestOfGame(world, inputString);

        } else if (firstChar == 'L') {
            String file = "./previousLoad.txt";
            try {
                Scanner scanner = new Scanner(new File(file));
                if (scanner.hasNext()) {
                    String previousLoad = scanner.next();
                    world = interactWithInputString('N'
                            + previousLoad + upperCaseInput.substring(1));
                } else {
                    System.exit(0);
                }
            } catch (FileNotFoundException e) {
                System.out.println("oops");
            }
        } else {
            System.out.println("WHY");
             System.exit(0);
        }

        return world;
    }
}
