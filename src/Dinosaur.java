import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Lead Author(s): Haroon Usman; 5550080871
 * <<add additional lead authors here, with a full first and last name>>
 * 
 * Other contributors:
 * <<add additional contributors (mentors, tutors, friends) here, with contact information>>
 * 
 * References:
 * Morelli, R., & Walde, R. (2016). Java, Java, Java: Object-Oriented Problem Solving.
 * Retrieved from https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 * 
 * <<add more references here>>
 *  
 * Version/date: 05/25/2024
 * 
 * Responsibilities of class:
 * - Represents the player's dinosaur character.
 * - Handles dinosaur movement and collision detection.
 */
public class Dinosaur extends JPanel 
{
    private int xPos;
    private int yPos;
    private boolean isJumping;
    private boolean isFalling;
    private int jumpSpeed;
    private int gravity;
    private ImageIcon dinosaurGif;

    private static final int GROUND_LEVEL = GameManager.HEIGHT - 50;
    private static final int IMAGE_WIDTH = 120;
    private static final int IMAGE_HEIGHT = 120;
    private static final int HITBOX_WIDTH = 40;
    private static final int HITBOX_HEIGHT = 50;

    /**
     * Constructs a new Dinosaur object.
     * 
     * @param xPos The initial x-position of the dinosaur.
     */
    public Dinosaur(int xPos) 
    {
        this.xPos = xPos;
        this.yPos = GROUND_LEVEL - HITBOX_HEIGHT;
        this.isJumping = false;
        this.isFalling = false;
        this.gravity = 1;
        this.jumpSpeed = -16; // Default jump speed

        // Load the dinosaur GIF
        dinosaurGif = new ImageIcon("resources/ezgif-7-aa79a69fcc.gif");
    }

    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Draw the dinosaur GIF
        if (dinosaurGif != null) 
        {
            Image image = dinosaurGif.getImage();
            int imageYOffset = (HITBOX_HEIGHT - IMAGE_HEIGHT) / 2;
            int imageXOffset = (HITBOX_WIDTH - IMAGE_WIDTH) / 2;
            g.drawImage(image, xPos + imageXOffset, yPos + imageYOffset, IMAGE_WIDTH, IMAGE_HEIGHT, this);
        }
        else 
        {
            g.setColor(Color.GREEN);
            g.fillRect(xPos, yPos, IMAGE_WIDTH, IMAGE_HEIGHT);
        }
    }

    /**
     * Initiates the dinosaur's jump.
     */
    public void jump()
    {
        if (!isJumping && !isFalling)
        {
            isJumping = true;
            jumpSpeed = -16; // Slightly lower jump speed for a lower jump height
        }
    }

    /**
     * Updates the dinosaur's position based on its jumping and falling state.
     */
    public void move() 
    {
        if (isJumping) 
        {
            yPos += jumpSpeed;
            jumpSpeed += gravity;
            if (yPos >= GROUND_LEVEL - HITBOX_HEIGHT) 
            {
                yPos = GROUND_LEVEL - HITBOX_HEIGHT;
                isJumping = false;
                isFalling = false;
            }
        } 
        else if (isFalling) 
        {
            yPos += jumpSpeed;
            jumpSpeed += gravity;
            if (yPos >= GROUND_LEVEL - HITBOX_HEIGHT) 
            {
                yPos = GROUND_LEVEL - HITBOX_HEIGHT;
                isFalling = false;
            }
        }
        repaint();
    }

    @Override
    public Rectangle getBounds() 
    {
        return new Rectangle(xPos, yPos, HITBOX_WIDTH, HITBOX_HEIGHT);
    }

    /**
     * Checks if the dinosaur is colliding with an obstacle.
     * 
     * @param obstacle The obstacle to check collision with.
     * @return True if colliding, false otherwise.
     */
    public boolean isColliding(Obstacle obstacle)
    {
        Rectangle dinosaurRect = getBounds();
        Rectangle obstacleRect = new Rectangle(obstacle.getX(), obstacle.getY(), obstacle.getWidth(), obstacle.getHeight());
        return dinosaurRect.intersects(obstacleRect);
    }

    /**
     * Checks if the dinosaur is colliding with a power-up.
     * 
     * @param powerUp The power-up to check collision with.
     * @return True if colliding, false otherwise.
     */
    public boolean isColliding(PowerUp.Circle powerUp) 
    {
        Rectangle dinosaurRect = getBounds();
        Rectangle powerUpRect = powerUp.getBounds();
        return dinosaurRect.intersects(powerUpRect);
    }

    /**
     * Handles the key press event for jumping.
     * 
     * @param e The key event.
     */
    public void handleJumpKeyPress(KeyEvent e) 
    {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE || key == KeyEvent.VK_UP) 
        {
            jump();
        }
    }

    /**
     * Handles the key press event for canceling a jump and falling faster.
     * 
     * @param e The key event.
     */
    public void handleDownKeyPress(KeyEvent e) 
    {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_DOWN) 
        {
            if (isJumping) 
            {
                jumpSpeed = Math.abs(jumpSpeed);
                isJumping = false;
                isFalling = true;
            }
        }
    }

    /**
     * Sets the jump speed of the dinosaur.
     * 
     * @param jumpSpeed The new jump speed.
     */
    public void setJumpSpeed(int jumpSpeed) 
    {
        this.jumpSpeed = jumpSpeed;
    }
}