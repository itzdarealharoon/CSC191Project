import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class Dinosaur extends JPanel {
    private int xPos;
    private int yPos;
    private boolean isJumping;
    private int jumpSpeed;
    private int gravity;
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;

    public Dinosaur(int x, int y) {
        this.xPos = x;
        this.yPos = y;
        this.isJumping = false;
        this.jumpSpeed = -15;
        this.gravity = 1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(xPos, yPos, WIDTH, HEIGHT);
    }

    public void jump() {
        if (!isJumping) {
            isJumping = true;
            jumpSpeed = -15;
        }
    }

    public void move() {
        if (isJumping) {
            yPos += jumpSpeed;
            jumpSpeed += gravity;
            if (yPos >= 350 - HEIGHT) { // Adjusted based on panel height and dinosaur height
                yPos = 350 - HEIGHT;
                isJumping = false;
            }
        }
        repaint();
    }

    public boolean isColliding(Obstacle obstacle) {
        Rectangle dinosaurRect = new Rectangle(xPos, yPos, WIDTH, HEIGHT);
        Rectangle obstacleRect = new Rectangle(obstacle.getX(), obstacle.getY(), obstacle.getWidth(), obstacle.getHeight());
        return dinosaurRect.intersects(obstacleRect);
    }

    // Method to handle space bar press event
    public void handleSpaceBarPress(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            jump();
        }
    }
}
