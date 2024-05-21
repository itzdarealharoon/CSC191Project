// updat

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class Dinosaur extends JPanel {
    private int yPos;
    private boolean isJumping;
    private int jumpSpeed;
    private int gravity;

    public Dinosaur() {
        yPos = 250; // Lower the initial position
        isJumping = false;
        jumpSpeed = -15; // Initial jump speed
        gravity = 1; // Gravity effect
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(100, yPos, 50, 50); // Adjust the size and position of the dinosaur rectangle
    }

    public void jump() {
        if (!isJumping) {
            isJumping = true;
            jumpSpeed = -15; // Set jump speed
        }
    }

    public void move() {
        // Apply gravity
        if (isJumping) {
            yPos += jumpSpeed;
            jumpSpeed += gravity;
            // Check if dinosaur has reached the ground
            if (yPos >= 250) { // Adjust the ground level
                yPos = 250; // Reset position
                isJumping = false; // End jump
            }
        }
        repaint(); // Refresh JPanel
    }

    public boolean isColliding(Obstacle obstacle) {
        Rectangle dinosaurRect = new Rectangle(100, yPos, 50, 60); // Dinosaur rectangle
        Rectangle obstacleRect = new Rectangle(obstacle.getX(), obstacle.getY(), obstacle.getWidth(), obstacle.getHeight()); // Obstacle rectangle
        return dinosaurRect.intersects(obstacleRect);
    }
}
