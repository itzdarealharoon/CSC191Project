import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Obstacle {
    private int x, y, width, height, speed;
    private String type;

    public Obstacle(int x, int y, int width, int height, int speed, String type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.type = type;
    }

    public void move() {
        x -= speed;
    }

    public void draw(Graphics g) {
        if ("bird".equals(type)) {
            g.setColor(Color.RED); // Bird color
        } else {
            g.setColor(Color.BLUE); // Regular obstacle color
        }
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
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

    public String getType() {
        return type;
    }
}
