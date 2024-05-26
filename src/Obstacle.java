import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

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
 * - Represents obstacles in the game, such as cacti and birds.
 * - Manages the movement and rendering of obstacles.
 */
public class Obstacle 
{
    private int x, y, width, height, speed;
    private String type;
    private Image cactusImage;
    private Image birdImage;

    /**
     * Constructs a new Obstacle object.
     * 
     * @param x The x-position of the obstacle.
     * @param y The y-position of the obstacle.
     * @param width The width of the obstacle.
     * @param height The height of the obstacle.
     * @param speed The speed of the obstacle's movement.
     * @param type The type of the obstacle ("obstacle" or "bird").
     */
    public Obstacle(int x, int y, int width, int height, int speed, String type) 
    {
        this.x = x;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.type = type;

        try 
        {
            if ("obstacle".equals(type)) 
            {
                cactusImage = ImageIO.read(new File("resources/4-original.png"));
                cactusImage = cactusImage.getScaledInstance(50, 70, Image.SCALE_SMOOTH); // Adjusted dimensions
                this.y = GameManager.HEIGHT - height - 50; // Align bottom of cactus with the ground level
            } 
            else if ("bird".equals(type)) 
            {
                birdImage = ImageIO.read(new File("resources/Chrome_Pterodactyl.png"));
                birdImage = birdImage.getScaledInstance(50, 30, Image.SCALE_SMOOTH); // Adjusted dimensions
                this.y = y; // Set bird y-position as passed
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    /**
     * Moves the obstacle to the left based on its speed.
     */
    public void move() 
    {
        x -= speed;
    }

    /**
     * Draws the obstacle on the screen.
     * 
     * @param g The Graphics object used for drawing.
     */
    public void draw(Graphics g) 
    {
        if ("obstacle".equals(type) && cactusImage != null) 
        {
            g.drawImage(cactusImage, x, y, null);
        } 
        else if ("bird".equals(type) && birdImage != null) 
        {
            g.drawImage(birdImage, x, y, null);
        } 
        else 
        {
            g.setColor(Color.BLACK);
            g.fillRect(x, y, width, height);
        }
    }

    /**
     * Gets the bounds of the obstacle for collision detection.
     * 
     * @return A Rectangle representing the bounds of the obstacle.
     */
    public Rectangle getBounds() 
    {
        return new Rectangle(x, y, width, height);
    }

    // Getters for the obstacle's properties

    /**
     * Gets the x-position of the obstacle.
     * 
     * @return The x-position of the obstacle.
     */
    public int getX() 
    {
        return x;
    }

    /**
     * Gets the y-position of the obstacle.
     * 
     * @return The y-position of the obstacle.
     */
    public int getY()
    {
        return y;
    }

    /**
     * Gets the width of the obstacle.
     * 
     * @return The width of the obstacle.
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Gets the height of the obstacle.
     * 
     * @return The height of the obstacle.
     */
    public int getHeight() 
    {
        return height;
    }
}