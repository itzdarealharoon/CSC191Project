import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameManager extends JPanel implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 400;
    private static final int OBSTACLE_WIDTH = 30;
    private static final int OBSTACLE_HEIGHT = 30;
    private static final int OBSTACLE_SPEED = 5;
    private static final int BIRD_SPEED = 8;
    private static final int OBSTACLE_GAP = 200;
    private static final double POWER_UP_RATIO = 1.0 / 1000.0;
    private static int POWER_UP_DURATION = 10;

    private Dinosaur dinosaur;
    private ObstacleManager obstacleManager;
    Timer timer;
    private ScoreManager scoreManager;
    private PowerUp powerUp;
    private Random random;
    private double scoreMultiplier;

    public GameManager() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);
        int dinosaurBottomY = HEIGHT - Dinosaur.HEIGHT; // Calculate y-position so bottom aligns with bottom of panel
        int dinosaurStartX = 100; // Adjust the starting x-position here
        int dinosaurStartY = 300; // Adjust the starting y-position here

        dinosaur = new Dinosaur(dinosaurStartX, dinosaurStartY); // Initial position
        obstacleManager = new ObstacleManager();
        timer = new Timer();
        scoreManager = new ScoreManager();
        powerUp = new PowerUp();
        random = new Random();
        scoreMultiplier = 1.0;

        // Add KeyListener to listen for space bar and arrow key presses
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                dinosaur.handleJumpKeyPress(e);
                dinosaur.handleDownKeyPress(e);
            }
        });
    }

    public Timer getTimer() {
        return timer;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dinosaur.paintComponent(g);
        obstacleManager.draw(g);
        powerUp.draw(g);
        g.setColor(Color.BLACK);
        g.drawString("Score: " + scoreManager.getScore(), 10, 20);
        g.drawString("Multiplier: " + scoreMultiplier, 10, 40);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dinosaur.move();
        obstacleManager.update();
        powerUp.update();
        generateObstacle();
        generatePowerUp();
        if (obstacleCollisionDetected()) {
            stopGame();
        }
        handlePowerUpCollisions();
        scoreManager.increaseScore((int) (10 * scoreMultiplier)); // Increase score with multiplier
        repaint();
    }

    private void generateObstacle() {
        int obstacleBottomY = HEIGHT - 50; // Fixed y-coordinate for the bottom of the obstacle

        // Generate obstacles with random spacing and size
        if (obstacleManager.getObstacles().isEmpty() || obstacleManager.getObstacles().get(obstacleManager.getObstacles().size() - 1).getX() < WIDTH - OBSTACLE_GAP) {
            int randomX = WIDTH + random.nextInt(200); // Randomize the x-coordinate of the obstacle
            int obstacleHeight = 30 + random.nextInt(30); // Randomize the height of the obstacle
            int topPosition = obstacleBottomY - obstacleHeight; // Calculate the top position of the obstacle
            String type = random.nextInt(5) == 0 ? "bird" : "obstacle"; // 1 in 5 chance to create a bird

            if ("bird".equals(type)) {
                topPosition = HEIGHT - 75 - random.nextInt(5); // Randomize bird's height in the air
                obstacleManager.addObstacle(new Obstacle(randomX, topPosition, OBSTACLE_WIDTH, 30, BIRD_SPEED, type)); // Birds are always 30x30
            } else {
                obstacleManager.addObstacle(new Obstacle(randomX, topPosition, OBSTACLE_WIDTH, obstacleHeight, OBSTACLE_SPEED, type));
            }
        }
    }

    private void generatePowerUp() {
        if (random.nextDouble() < POWER_UP_RATIO) {
            int x = WIDTH + random.nextInt(200);
            int y = HEIGHT - 100 - random.nextInt(200); // Randomize power-up's height in the air
            powerUp.addPowerUp(x, y);
        }
    }

    private boolean obstacleCollisionDetected() {
        for (Obstacle obstacle : obstacleManager.getObstacles()) {
            if (dinosaur.isColliding(obstacle)) {
                return true;
            }
        }
        return false;
    }

    private void handlePowerUpCollisions() {
        for (PowerUp.Circle circle : powerUp.getCircles()) {
            if (dinosaur.isColliding(circle)) {
                activatePowerUp();
                powerUp.removePowerUp(circle);
                break; // Remove only one power-up at a time
            }
        }
    }

    private void activatePowerUp() {
        scoreMultiplier = 2.0; // Set multiplier to 2
        TimerTask resetMultiplierTask = new TimerTask() {
            @Override
            public void run() {
                scoreMultiplier = 1.0; // Reset multiplier after duration
            }
        };
        timer.schedule(resetMultiplierTask, POWER_UP_DURATION * 1000); // Schedule reset after duration
    }

    private void stopGame() {
        timer.cancel(); // Stop the game timer
        JOptionPane.showMessageDialog(null, "Game Over! Your Score: " + scoreManager.getScore());
    }
}
