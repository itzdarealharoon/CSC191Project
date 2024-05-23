import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameManager extends JPanel implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 400;
    private static final int OBSTACLE_WIDTH = 30;
    private static final int OBSTACLE_HEIGHT = 30;
    private static final int OBSTACLE_SPEED = 5;
    private static final int OBSTACLE_GAP = 200;
    private Dinosaur dinosaur;
    private ObstacleManager obstacleManager;
    private Timer timer;
    private ScoreManager scoreManager;
    private PowerUp powerUp;
    private Random random;
    private boolean spacePressed;

    public GameManager() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);
        int dinosaurBottomY = HEIGHT - Dinosaur.HEIGHT; // Calculate y-position so bottom aligns with bottom of panel
        int dinosaurStartX = 100; // Adjust the starting x-position here
        int dinosaurStartY = 300; // Adjust the starting y-position here

        dinosaur = new Dinosaur(dinosaurStartX, dinosaurStartY); // Initial position
        obstacleManager = new ObstacleManager();
        timer = new Timer(20, this);
        timer.start();
        scoreManager = new ScoreManager();
        powerUp = new PowerUp();
        random = new Random();
        spacePressed = false;

        // Add KeyListener to listen for space bar press
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                dinosaur.handleSpaceBarPress(e);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dinosaur.paintComponent(g);
        for (Obstacle obstacle : obstacleManager.getObstacles()) {
            obstacle.draw(g);
        }
        g.setColor(Color.BLACK);
        g.drawString("Score: " + scoreManager.getScore(), 10, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dinosaur.move();
        obstacleManager.update();
        generateObstacle();
        if (collisionDetected()) {
            timer.stop();
            JOptionPane.showMessageDialog(null, "Game Over! Your Score: " + scoreManager.getScore());
        }
        scoreManager.increaseScore();
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
                topPosition = HEIGHT - 200 - random.nextInt(50); // Randomize bird's height in the air
            }

            obstacleManager.addObstacle(new Obstacle(randomX, topPosition, OBSTACLE_WIDTH, obstacleHeight, OBSTACLE_SPEED, type));
        }
    }

    private boolean collisionDetected() {
        for (Obstacle obstacle : obstacleManager.getObstacles()) {
            if (dinosaur.isColliding(obstacle)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DinoDash");
        GameManager gameManager = new GameManager();
        frame.add(gameManager);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
