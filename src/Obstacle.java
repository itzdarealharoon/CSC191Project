import java.awt.*;

public class Obstacle {
    private int x;
    private int y;
    private int width;
    private int height;
    private int speed;

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

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y - height, width, height); // Adjust the position of the obstacle
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
}
