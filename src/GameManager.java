import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
 * Manages the overall game flow, including the game loop, player inputs, 
 * obstacle generation, power-up generation, and collision detection.
 * HAS-A relationships with Dinosaur, ObstacleManager, ScoreManager, PowerUp, and HighScoreManager.
 */
public class GameManager extends JPanel implements ActionListener 
{
    // Constants for game dimensions and mechanics
    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;
    private static final int OBSTACLE_WIDTH = 30;
    private static final int OBSTACLE_HEIGHT = 30;
    private static final int OBSTACLE_SPEED = 5;
    private static final int BIRD_SPEED = 8;
    private static final int OBSTACLE_GAP = 200;
    private static final double POWER_UP_RATIO = 1.0 / 900.0;
    private static int POWER_UP_DURATION = 10;
    private static final int SUPER_JUMP_DURATION = 5000; // 5 seconds duration for super jump
    private static final int CACTUS_Y_POSITION = HEIGHT - 120; // Adjust the y-coordinate for cactus obstacles

    // Game components
    private Dinosaur dinosaur;
    private ObstacleManager obstacleManager;
    private Timer timer; // Use java.util.Timer
    private ScoreManager scoreManager;
    private PowerUp powerUp;
    private Random random;
    private double scoreMultiplier;
    private ImageIcon backgroundGif; // Background GIF
    private HighScoreManager highScoreManager; // High score manager

    /**
     * Constructs a new GameManager object.
     * 
     * @param frame The JFrame object that contains this GameManager.
     */
    public GameManager(JFrame frame)
    {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);
        int dinosaurStartX = 100;

        // Initialize game components
        dinosaur = new Dinosaur(dinosaurStartX);
        obstacleManager = new ObstacleManager();
        timer = new Timer();
        scoreManager = new ScoreManager();
        powerUp = new PowerUp();
        random = new Random();
        scoreMultiplier = 1.0;
        highScoreManager = new HighScoreManager(); // Initialize high score manager

        // Load the background GIF
        backgroundGif = new ImageIcon("resources/bbdino.gif");

        // Make the panel focusable to capture key events
        setFocusable(true);
        addKeyListener(new KeyAdapter() 
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                dinosaur.handleJumpKeyPress(e);
                dinosaur.handleDownKeyPress(e);
            }
        });

        // Schedule the game loop
        timer.scheduleAtFixedRate(new TimerTask() 
        {
            @Override
            public void run() 
            {
                actionPerformed(null);
            }
        }, 0, 20);

        // Stop the timer when the window is closing
        frame.addWindowListener(new java.awt.event.WindowAdapter() 
        {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                timer.cancel();
            }
        });
    }

    /**
     * Returns the game timer.
     * 
     * @return The game timer.
     */
    public Timer getTimer() 
    {
        return timer;
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);

        // Draw the background GIF
        if (backgroundGif != null) 
        {
            Image backgroundImage = backgroundGif.getImage();
            g.drawImage(backgroundImage, 0, HEIGHT - backgroundImage.getHeight(this), WIDTH, backgroundImage.getHeight(this), this);
        }

        // Draw game components
        dinosaur.paintComponent(g);
        obstacleManager.draw(g);
        powerUp.draw(g);

        // Display scores
        g.setColor(Color.BLACK);
        g.drawString("Score: " + scoreManager.getScore(), 10, 20);
        g.drawString("Multiplier: " + scoreMultiplier, 10, 40);
        g.drawString("High Score: " + highScoreManager.getHighScore(), 10, 60);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        // Update game state
        dinosaur.move();
        obstacleManager.update();
        powerUp.update();
        generateObstacle();
        generatePowerUp();
        if (obstacleCollisionDetected()) 
        {
            stopGame();
        }
        handlePowerUpCollisions();
        scoreManager.increaseScore((int) (10 * scoreMultiplier));
        repaint();
    }

    /**
     * Generates a new obstacle if needed.
     */
    private void generateObstacle() 
    {
        int obstacleBottomY = HEIGHT - 50;

        // Generate new obstacles at random intervals
        if (obstacleManager.getObstacles().isEmpty() || obstacleManager.getObstacles().get(obstacleManager.getObstacles().size() - 1).getX() < WIDTH - OBSTACLE_GAP) 
        {
            int randomX = WIDTH + random.nextInt(200);
            int obstacleHeight = 70; // Fixed height for cactus
            int obstacleWidth = 50; // Fixed width for cactus
            String type = random.nextInt(5) == 0 ? "bird" : "obstacle";

            if ("bird".equals(type)) 
            {
                int topPosition = HEIGHT - 75 - random.nextInt(5);
                obstacleManager.addObstacle(new Obstacle(randomX, topPosition, OBSTACLE_WIDTH, 30, BIRD_SPEED, type));
            } 
            else 
            {
                obstacleManager.addObstacle(new Obstacle(randomX, HEIGHT - 120, obstacleWidth, obstacleHeight, OBSTACLE_SPEED, type)); // Align cactus at the bottom
            }
        }
    }

    /**
     * Generates a new power-up if needed.
     */
    private void generatePowerUp() 
    {
        // Generate new power-ups at random intervals
        if (random.nextDouble() < POWER_UP_RATIO) 
        {
            int x = WIDTH + random.nextInt(200);
            int y = HEIGHT - 100 - random.nextInt(200);
            String type = random.nextDouble() < 0.5 ? "super_jump" : "score_multiplier";
            powerUp.addPowerUp(x, y, type);
        }
    }

    /**
     * Checks if there is a collision between the dinosaur and any obstacles.
     * 
     * @return True if a collision is detected, false otherwise.
     */
    private boolean obstacleCollisionDetected() 
    {
        // Check for collisions between the dinosaur and obstacles
        for (Obstacle obstacle : obstacleManager.getObstacles()) 
        {
            if (dinosaur.isColliding(obstacle)) 
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Handles collisions between the dinosaur and power-ups.
     */
    private void handlePowerUpCollisions()
    {
        // Check for collisions between the dinosaur and power-ups
        for (PowerUp.Circle circle : powerUp.getCircles())
        {
            if (dinosaur.isColliding(circle))
            {
                if ("super_jump".equals(circle.getType()))
                {
                    activateSuperJump();
                } 
                else
                {
                    activatePowerUp();
                }
                powerUp.removePowerUp(circle);
                break;
            }
        }
    }

    /**
     * Activates the score multiplier power-up.
     */
    private void activatePowerUp()
    {
        // Activate the score multiplier power-up
        scoreMultiplier = 2.0;
        TimerTask resetMultiplierTask = new TimerTask() 
        {
            @Override
            public void run()
            {
                scoreMultiplier = 1.0;
            }
        };
        timer.schedule(resetMultiplierTask, POWER_UP_DURATION * 1000);
    }

    /**
     * Activates the super jump power-up.
     */
    private void activateSuperJump() 
    {
        // Activate the super jump power-up
        dinosaur.setJumpSpeed(-25);
        TimerTask resetJumpSpeedTask = new TimerTask() 
        {
            @Override
            public void run() 
            {
                dinosaur.setJumpSpeed(-16);
            }
        };
        timer.schedule(resetJumpSpeedTask, SUPER_JUMP_DURATION);
    }

    /**
     * Ends the game and displays the final score.
     */
    private void stopGame() 
    {
        // End the game and display the score
        timer.cancel();
        highScoreManager.checkAndSetHighScore(scoreManager.getScore());
        JOptionPane.showMessageDialog(null, "Game Over! Your Score: " + scoreManager.getScore() + "\nHigh Score: " + highScoreManager.getHighScore());
        System.exit(0);
    }
}