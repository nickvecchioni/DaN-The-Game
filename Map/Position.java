package byow.Map;

public class Position {

    private int x;
    private int y;
    private int orientation = 0;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(int x, int y, int orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getOrientation() {
        return orientation;
    }
}
