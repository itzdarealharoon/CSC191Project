import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PowerUp {
    private List<Circle> powerUps;
    private Random random;

    public PowerUp() {
        powerUps = new ArrayList<>();
        random = new Random();
    }

    public void addPowerUp(int x, int y) {
        powerUps.add(new Circle(x, y, 10)); // Example radius of 10
    }

    public List<Circle> getCircles() {
        return powerUps;
    }

    public void removePowerUp(Circle circle) {
        powerUps.remove(circle);
    }

    public void update() {
        for (Circle circle : powerUps) {
            circle.move();
        }
        powerUps.removeIf(circle -> circle.getX() < 0);
    }

    public void draw(Graphics g) {
        for (Circle circle : powerUps) {
            circle.draw(g);
        }
    }

    public class Circle {
        private int x;
        private int y;
        private int radius;
        private int speed;

        public Circle(int x, int y, int radius) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.speed = 5;
        }

        public void move() {
            x -= speed;
        }

        public void draw(Graphics g) {
            g.setColor(Color.RED);
            g.fillOval(x, y, radius * 2, radius * 2);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Rectangle getBounds() {
            return new Rectangle(x, y, radius * 2, radius * 2);
        }
    }
}
