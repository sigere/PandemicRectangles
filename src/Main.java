import processing.core.PApplet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Main extends PApplet
{
    public static PApplet processing;
    public static float radius;
    public static Rectangle[] mainArray;
    private int x = 0;

    public static void main(String[] args) {
        PApplet.main("Main", args);
    }

    @Override
    public void settings() {
        size(1000,800);
        Main.radius = ((float) Math.min(this.width, this.height) / 2) - 10.0f;
    }

    @Override
    public void setup() {
        Main.processing = this;
        frameRate(60);
        rectMode(CENTER);
        Main.mainArray = new Rectangle[1500];

        for (int i=0; i<Main.mainArray.length; i++) {
            mainArray[i] = new Rectangle(Color.black());
        }

        mainArray[mainArray.length-1].setColor(Color.red());
        mainArray[mainArray.length-1].block = true;
    }

    @Override
    public void draw() {
        background(1,0,59);
        noFill();
        for (Rectangle rectangle : Main.mainArray) {
            rectangle.draw();
            rectangle.move();
        }

        if(x++ > 50)
        {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            PApplet.print("Black: " + Main.countRectanglesWithColor(Color.black()) + "\n");
            PApplet.print("Red: " + Main.countRectanglesWithColor(Color.red()) + "\n");
            PApplet.print("\n---------------------\n");
            x = 0;
        }
    }

    private static int countRectanglesWithColor(Color color) {
        int counter = 0;
        for (Rectangle rectangle : Main.mainArray) {
            if (rectangle.color.equals(color)) {
                counter++;
            }
        }
        return counter;
    }
}
