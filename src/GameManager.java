// updat

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
    private static final int OBSTACLE_HEIGHT = 50;
    private static final int OBSTACLE_SPEED = 5;
    private static final int OBSTACLE_GAP = 200;
    private Dinosaur dinosaur;
    private ObstacleManager obstacleManager;
    private Timer timer;
    private int score;
    private Random random;
    
    public GameManager() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);
        dinosaur = new Dinosaur();
        obstacleManager = new ObstacleManager();
        timer = new Timer(20, this);
        random = new Random();
        score = 0;
        timer.start();

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    dinosaur.jump();
                }
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
        g.drawString("Score: " + score, 10, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dinosaur.move();
        obstacleManager.update();
        generateObstacle();
        if (collisionDetected()) {
            timer.stop();
            JOptionPane.showMessageDialog(null, "Game Over! Your Score: " + score);
        }
        score++;
        repaint();
    }

    private void generateObstacle() {
        int obstacleY = HEIGHT - OBSTACLE_HEIGHT - 20; // Fixed y-coordinate

        // Generate obstacles with random spacing
        if (obstacleManager.getObstacles().isEmpty() || obstacleManager.getObstacles().get(obstacleManager.getObstacles().size() - 1).getX() < WIDTH - OBSTACLE_GAP) {
            int randomX = WIDTH + random.nextInt(200); // Randomize the x-coordinate of the obstacle
            obstacleManager.addObstacle(new Obstacle(randomX, obstacleY, OBSTACLE_WIDTH, OBSTACLE_HEIGHT, OBSTACLE_SPEED)); // Increase height of the obstacle
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
