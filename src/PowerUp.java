import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * Lead Author(s): Haroon Usman; 5550080871
 * 
 * Other contributors:
 * <<add additional contributors (mentors, tutors, friends) here, with contact information>>
 * 
 * References:
 * Morelli, R., & Walde, R. (2016). Java, Java, Java: Object-Oriented Problem Solving.
 * Retrieved from https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 * 
 * Version/date: 05/25/2024
 * 
 * Responsibilities of class:
 * Manages power-ups in the game, handles their creation, movement, and rendering.
 * HAS-A relationship with Circle (inner class).
 */
public class PowerUp 
{
    private List<Circle> circles; // List of power-up circles
    private Image superJumpImage; // Image for the super jump power-up
    private Image scoreMultiplierImage; // Image for the score multiplier power-up

    /**
     * Constructor for the PowerUp class.
     * Initializes the list of power-ups and loads the images.
     */
    public PowerUp() 
    {
        circles = new ArrayList<>();
        try 
        {
            superJumpImage = ImageIO.read(new File("resources/pngtree-metal-spring-vector-icon-illustration-design-springy-mechanical-metal-vector-picture-image_10751903.png"));
            scoreMultiplierImage = ImageIO.read(new File("resources/apps.15329.14288115356063326.4d62ce96-2646-47f3-9c63-744d41fe4daf.9c3ea08c-518d-4d7e-9b9b-c8d02fa61765.png"));
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    /**
     * Adds a power-up at the specified coordinates with the given type.
     * @param x The x-coordinate of the power-up
     * @param y The y-coordinate of the power-up
     * @param type The type of the power-up ("super_jump" or "score_multiplier")
     */
    public void addPowerUp(int x, int y, String type) 
    {
        circles.add(new Circle(x, y, type));
    }

    /**
     * Removes a specified power-up from the list.
     * @param circle The power-up to be removed
     */
    public void removePowerUp(Circle circle) 
    {
        circles.remove(circle);
    }

    /**
     * Returns the list of power-ups.
     * @return The list of power-ups
     */
    public List<Circle> getCircles()
    {
        return circles;
    }

    /**
     * Updates the positions of all power-ups, moving them to the left.
     * Removes any power-ups that have moved off-screen.
     */
    public void update() 
    {
        for (Circle circle : circles) 
        {
            circle.move();
        }
        circles.removeIf(circle -> circle.getX() < 0);
    }

    /**
     * Draws all power-ups on the screen.
     * @param g The Graphics object used to draw the power-ups
     */
    public void draw(Graphics g) 
    {
        for (Circle circle : circles) 
        {
            circle.draw(g);
        }
    }

    /**
     * Inner class representing a power-up circle.
     */
    public class Circle 
    {
        private int x;
        private int y;
        private int size;
        private String type;
        private static final int SPEED = 5; // Speed at which the power-ups move to the left
        private static final int SPRING_SIZE = 60; // Size of the spring image
        private static final int MULTIPLIER_SIZE = 60; // Size of the score multiplier image

        /**
         * Constructor for the Circle class.
         * @param x The x-coordinate of the power-up
         * @param y The y-coordinate of the power-up
         * @param type The type of the power-up ("super_jump" or "score_multiplier")
         */
        public Circle(int x, int y, String type)
        {
            this.x = x;
            this.y = y;
            this.size = 20;
            this.type = type;
        }

        /**
         * Moves the power-up to the left by the speed amount.
         */
        public void move() 
        {
            x -= SPEED;
        }

        /**
         * Draws the power-up on the screen.
         * @param g The Graphics object used to draw the power-up
         */
        public void draw(Graphics g) 
        {
            if ("super_jump".equals(type) && superJumpImage != null) 
            {
                g.drawImage(superJumpImage, x, y, SPRING_SIZE, SPRING_SIZE, null);
            } 
            else if ("score_multiplier".equals(type) && scoreMultiplierImage != null) 
            {
                g.drawImage(scoreMultiplierImage, x, y, MULTIPLIER_SIZE, MULTIPLIER_SIZE, null);
            } 
            else if ("score_multiplier".equals(type)) 
            {
                g.setColor(Color.GREEN); // Fallback to green circle for score multiplier
                g.fillOval(x, y, size, size);
            }
        }

        /**
         * Returns the bounding rectangle of the power-up, used for collision detection.
         * @return The bounding rectangle of the power-up
         */
        public Rectangle getBounds() 
        {
            if ("super_jump".equals(type))
            {
                return new Rectangle(x, y, SPRING_SIZE, SPRING_SIZE); // Adjust hitbox for spring image
            } 
            else if ("score_multiplier".equals(type))
            {
                return new Rectangle(x, y, MULTIPLIER_SIZE, MULTIPLIER_SIZE); // Adjust hitbox for score multiplier image
            } 
            else
            {
                return new Rectangle(x, y, size, size);
            }
        }

        /**
         * Returns the x-coordinate of the power-up.
         * @return The x-coordinate of the power-up
         */
        public int getX() 
        {
            return x;
        }

        /**
         * Returns the y-coordinate of the power-up.
         * @return The y-coordinate of the power-up
         */
        public int getY() 
        {
            return y;
        }

        /**
         * Returns the type of the power-up.
         * @return The type of the power-up
         */
        public String getType() 
        {
            return type;
        }
    }
}