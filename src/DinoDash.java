//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//
//public class DinoDash extends JFrame {
//    private JLabel scoreLabel;
//    private Dinosaur dinosaur;
//    private Timer gameTimer;
//    private int score;
//    private Obstacle obstacle;
//    private boolean spacePressed;
//
//    public DinoDash() {
//        setTitle("DinoDash");
//        setSize(800, 400);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setResizable(false);
//        setLayout(new BorderLayout());
//
//        scoreLabel = new JLabel("Score: 0", SwingConstants.RIGHT);
//        add(scoreLabel, BorderLayout.NORTH);
//
//        dinosaur = new Dinosaur();
//        add(dinosaur, BorderLayout.CENTER);
//
//        // Initialize the obstacle object
//        int initialX = 800; // Adjust as needed
//        int initialY = 200; // Adjust as needed
//        int obstacleWidth = 20; // Adjust as needed
//        int obstacleHeight = 50; // Adjust as needed
//        int obstacleSpeed = 5; // Adjust as needed
//        obstacle = new Obstacle(initialX, initialY, obstacleWidth, obstacleHeight, obstacleSpeed);
//    }
//
//}
