// updat

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Obstacle {
    private int x; // X-coordinate of the obstacle
    private int y; // Y-coordinate of the obstacle
    private int width; // Width of the obstacle
    private int height; // Height of the obstacle
    private int speed; // Speed of the obstacle

    public Obstacle(int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    public void move() {
        x -= speed; // Move the obstacle towards the left
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y - height + 10, width, height); // Adjust the position of the obstacle
    }

}
