import java.util.ArrayList;

public enum Direction {
    right, up, left, down;

    public static ArrayList<Direction> getList(){
        ArrayList<Direction> result = new ArrayList<Direction>();
        result.add(right);
        result.add(up);
        result.add(left);
        result.add(down);

        return result;
    }

    public static Direction fromInt(int integer) {
        return switch (integer) {
            default -> Direction.right;
            case 1 -> Direction.up;
            case 2 -> Direction.left;
            case 3 -> Direction.down;
        };
    }

    public static int toInt(Direction direction) {
        return switch (direction) {
            default -> 0;
            case up -> 1;
            case left -> 2;
            case down -> 3;
        };
    }

    @Override
    public String toString() {
        return this.name();
    }

}
