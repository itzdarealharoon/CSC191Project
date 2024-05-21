// update

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DinoDash extends JFrame {
    private JLabel scoreLabel;
    private Dinosaur dinosaur;
    private Timer gameTimer;
    private int score;
    private ObstacleManager obstacleManager;

    public DinoDash() {
        setTitle("DinoDash");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        scoreLabel = new JLabel("Score: 0", SwingConstants.RIGHT);
        add(scoreLabel, BorderLayout.NORTH);

        dinosaur = new Dinosaur();
        add(dinosaur, BorderLayout.CENTER);

        obstacleManager = new ObstacleManager();

        score = 0;
        gameTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dinosaur.move();
                obstacleManager.update();
                generateObstacle();
                if (collisionDetected()) {
                    gameTimer.stop();
                    JOptionPane.showMessageDialog(null, "Game Over! Your Score: " + score);
                }
                score++;
                scoreLabel.setText("Score: " + score);
                repaint(); // Refresh the frame
            }
        });
        gameTimer.start();

        setFocusable(true); // Ensure the JFrame receives keyboard input

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    dinosaur.jump();
                }
            }
        });
    }

    private void generateObstacle() {
        int obstacleY = 250; // Fixed y-coordinate

        // Generate obstacles with random spacing
        if (obstacleManager.getObstacles().isEmpty() || obstacleManager.getObstacles().get(obstacleManager.getObstacles().size() - 1).getX() < 600) {
            int randomX = 800 + (int) (Math.random() * 200); // Randomize the x-coordinate of the obstacle
            obstacleManager.addObstacle(new Obstacle(randomX, obstacleY, 20, 50, 5)); // Create a new obstacle
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

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Obstacle obstacle : obstacleManager.getObstacles()) {
            obstacle.draw(g);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DinoDash().setVisible(true);
            }
        });
    }
}
