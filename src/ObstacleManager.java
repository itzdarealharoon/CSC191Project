import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

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
 * Manages the collection of obstacles in the game, handles their creation, movement, and removal.
 * HAS-A relationship with Obstacle.
 */
public class ObstacleManager 
{
    private List<Obstacle> obstacles;

    /**
     * Constructs a new ObstacleManager and initializes the obstacle list.
     */
    public ObstacleManager() 
    {
        obstacles = new ArrayList<>();
    }

    /**
     * Adds a new obstacle to the manager.
     * 
     * @param obstacle The obstacle to add.
     */
    public void addObstacle(Obstacle obstacle) 
    {
        obstacles.add(obstacle);
    }

    /**
     * Gets the list of obstacles.
     * 
     * @return A list of Obstacle objects.
     */
    public List<Obstacle> getObstacles()
    {
        return obstacles;
    }

    /**
     * Updates the position of the obstacles, moving them to the left.
     */
    public void update() 
    {
        for (Obstacle obstacle : obstacles) 
        {
            obstacle.move();
        }
        obstacles.removeIf(obstacle -> obstacle.getX() < 0);
    }

    /**
     * Draws all the obstacles on the screen.
     * 
     * @param g The Graphics object used for drawing.
     */
    public void draw(Graphics g) 
    {
        for (Obstacle obstacle : obstacles) 
        {
            obstacle.draw(g);
        }
    }
}