package byow.Map;

public class SpecialPosition extends Position {
    private int orientation;

    public SpecialPosition(int x, int y, int orientation) {
        super(x, y);
        this.orientation = orientation;
    }

    public int getOrientation() {
        return orientation;
    }
}
