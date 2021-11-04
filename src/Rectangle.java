import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Random;

public class Rectangle
{
    PVector position;
    int width;
    int height;
    int padding;
    int step;
    Color color;
    PApplet p;
    Direction currentDirection;
    boolean block;

    Rectangle(Color color) {
        p = Main.processing;

        Random random = new Random();
        this.color = color;
        this.width = 8;
        this.height = 8;
//        this.width = random.nextInt(15) + 10;
//        this.height = random.nextInt(15) + 10;
        this.currentDirection = Direction.fromInt(random.nextInt(3));
        this.padding = 1;
        this.step = 2;
        this.block = false;

        do {
            this.setPositionInsideCircle(Main.radius);
        } while (this.collidesWithAnyOfRectangles(Main.mainArray));
    }

    void setPositionInsideCircle(float r) {
        if (this.position == null) {
            this.position = new PVector();
        }
        float bodyRadius = (float) Math.sqrt((Math.pow(this.width / 2.0f, 2) + Math.pow(this.height / 2.0f, 2)));
        float angle = p.random((float) Math.PI * 2);
        this.position.x = (float) (p.width / 2) + p.random(r - bodyRadius) * (float) Math.cos(angle);
        this.position.y = (float) (p.height / 2) + p.random(r -bodyRadius) * (float) Math.sin(angle);
    }

    void move() {
        if (this.tryMove(this.currentDirection)) {
            return;
        }

        Rectangle collidingRectangle = this.getCollidingRectangle();
        if (collidingRectangle != null) {
            if (!collidingRectangle.block) {
                collidingRectangle.setColor(Color.clone(this.color));
            }
//            if (!this.color.equals(Color.black()) && collidingRectangle.color.equals(Color.black())) {
//                collidingRectangle.setColor(Color.clone(this.color));
//            }
//            if (this.color.equals(Color.black()) && !collidingRectangle.color.equals(Color.black())) {
//                this.color = Color.clone(collidingRectangle.color);
//            }
        }

        ArrayList<Direction> availableDirections = Direction.getList();
        availableDirections.remove(this.currentDirection);
        if (collidingRectangle != null) {
            availableDirections.remove(collidingRectangle.currentDirection);
        }

        Random random = new Random();
        while (availableDirections.size() > 0) {
            int index = random.nextInt(availableDirections.size());
            Direction direction = availableDirections.get(index);
            if (tryMove(direction)) {
                this.currentDirection = direction;
                return;
            }
            availableDirections.remove(index);
        }
    }

    Rectangle getCollidingRectangle() {
        PVector storedPosition = this.position.copy();

        Direction direction = this.currentDirection;
        switch (direction) {
            case right -> this.position.x += this.step;
            case up-> this.position.y -= this.step;
            case left -> this.position.x -= this.step;
            case down -> this.position.y += this.step;
        }

        for (int i = 0; i < Main.mainArray.length; i++) {
            if (Main.mainArray[i] != null && Main.mainArray[i] != this && this.collidesWithRectangle(Main.mainArray[i])) {
                this.position = storedPosition;
                return Main.mainArray[i];
            }
        }

        this.position = storedPosition;
        return null;

    }

    boolean tryMove(Direction direction) {
        PVector storedPosition = this.position.copy();

        switch (direction) {
            case right -> this.position.x += this.step;
            case up-> this.position.y -= this.step;
            case left -> this.position.x -= this.step;
            case down -> this.position.y += this.step;
        }

        if (this.collidesWithAnything()) {
            this.position = storedPosition;
            return false;
        }

        return true;
    }

    boolean collidesWithAnything() {
        return this.collidesWithAnyOfRectangles(Main.mainArray) || this.collidesWithBorder();
    }

    boolean collidesWithBorder() {
        float bodyRadius = (float) Math.sqrt((Math.pow(this.width / 2.0f, 2) + Math.pow(this.height / 2.0f, 2)));
        return PApplet.dist(p.width / 2.0f, p.height / 2.0f, this.position.x, this.position.y) >= Main.radius - bodyRadius;
    }

    boolean collidesWithRectangle(Rectangle rectangle) {
        float distX = PApplet.abs(rectangle.position.x - this.position.x);
        float distY = PApplet.abs(rectangle.position.y - this.position.y);
        float cumulatedWidth = (float) rectangle.width / 2 + (float) this.width / 2;
        float cumulatedHeight = (float) rectangle.height / 2 + (float) this.height / 2;

        return distX < cumulatedWidth && distY < cumulatedHeight;
    }

    boolean collidesWithAnyOfRectangles(Rectangle[] rectangles) {
        for (Rectangle rectangle : rectangles) {
            if (rectangle != null && this.collidesWithRectangle(rectangle) && rectangle != this) {
                return true;
            }
        }
        return false;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    void draw() {
        if(this.block) {
            p.fill(0,255,0);
        } else {
            p.fill(this.color.red, this.color.green, this.color.blue);
        }

        p.rect(
                this.position.x,
                this.position.y,
                this.width - this.padding,
                this.height - this.padding
        );

    }
}