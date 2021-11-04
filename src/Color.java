public class Color
{
    public int red;
    public int green;
    public int blue;

    Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public boolean equals(Color color) {
        return this.red == color.red && this.green == color.green && this.blue == color.blue;
    }

    public static Color black() {
        return new Color(0,0,0);
    }

    public static Color red() {
        return new Color(255,0,0);
    }

    public static Color green() {
        return new Color(0,255,0);
    }

    public static Color blue() {
        return new Color(0,0,255);
    }

    public static Color clone(Color color) {
        return new Color(color.red, color.green, color.blue);
    }
}
