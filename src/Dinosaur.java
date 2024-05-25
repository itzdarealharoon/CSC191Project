import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Dinosaur extends JPanel {
    private int xPos;
    private int yPos;
    private boolean isJumping;
    private boolean isFalling;
    private int jumpSpeed;
    private int gravity;
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    private BufferedImage dinosaurImage;
    
    private static final int GROUND_LEVEL = 350; // Adjust this based on your panel height

    public Dinosaur(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.isJumping = false;
        this.isFalling = false;
        this.gravity = 1; // Adjust gravity as needed

        try {
            BufferedImage originalImage = ImageIO.read(new File("resources/Runningdustgif.gif"));
            dinosaurImage = resizeImage(originalImage, WIDTH, HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Ensure proper rendering behavior
        if (dinosaurImage != null) {
            g.drawImage(dinosaurImage, xPos, yPos, WIDTH, HEIGHT, null);
        } else {
            g.setColor(Color.GREEN);
            g.fillRect(xPos, yPos, WIDTH, HEIGHT);
        }
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, width, height, null);
        g2d.dispose();
        return resizedImage;
    }

    public void jump() {
        if (!isJumping && !isFalling) {
            isJumping = true;
            jumpSpeed = -15; // Set jump speed when initiating jump
        }
    }

    public void move() {
        if (isJumping) {
            yPos += jumpSpeed;
            jumpSpeed += gravity;
            if (yPos >= GROUND_LEVEL - HEIGHT) { // Adjusted based on panel height and dinosaur height
                yPos = GROUND_LEVEL - HEIGHT;
                isJumping = false;
                isFalling = false;
            }
        } else if (isFalling) {
            yPos += jumpSpeed;
            jumpSpeed += gravity;
            if (yPos >= GROUND_LEVEL - HEIGHT) { // Adjusted based on panel height and dinosaur height
                yPos = GROUND_LEVEL - HEIGHT;
                isFalling = false;
            }
        }
        repaint();
    }

    public Rectangle getBounds() {
        // Adjust the hitbox dimensions independently
        int hitboxX = xPos + 5; // Example offset
        int hitboxY = yPos + 10; // Example offset
        int hitboxWidth = WIDTH - 10; // Example adjustment
        int hitboxHeight = HEIGHT - 10; // Example adjustment
        return new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight);
    }

    public boolean isColliding(Obstacle obstacle) {
        Rectangle dinosaurRect = getBounds(); // Dinosaur rectangle
        Rectangle obstacleRect = new Rectangle(obstacle.getX(), obstacle.getY(), obstacle.getWidth(), obstacle.getHeight()); // Obstacle rectangle
        return dinosaurRect.intersects(obstacleRect);
    }

    public boolean isColliding(PowerUp.Circle powerUp) {
        Rectangle dinosaurRect = getBounds(); // Dinosaur rectangle
        Rectangle powerUpRect = powerUp.getBounds(); // Power-up rectangle
        return dinosaurRect.intersects(powerUpRect);
    }

    // Method to handle space bar press event
    public void handleJumpKeyPress(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE || key == KeyEvent.VK_UP) {
            jump();
        }
    }

    public void handleDownKeyPress(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_DOWN) {
            if (isJumping) {
                jumpSpeed = Math.abs(jumpSpeed); // Make the dinosaur fall down faster
                isJumping = false;
                isFalling = true;
            }
        }
    }
}
