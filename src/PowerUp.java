import java.awt.*;

public class PowerUp {
    private int x;
    private int y;
    private boolean active;
    private static final Color COLOR = Color.GREEN;
    private static final int SIZE = 20;

    public PowerUp() {
        active = false;
    }

    public void activate() {
        active = true;
    }

    public void deactivate() {
        active = false;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        if (active) {
            g.setColor(COLOR);
            g.fillRect(x, y, SIZE, SIZE);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }

    public boolean isActive() {
        return active;
    }
}
