import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

public class MainMenu extends JPanel {
    public MainMenu() {
        setPreferredSize(new Dimension(800, 400)); // Set the size of the main menu
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        JButton instructionsButton = new JButton("Instructions");
        instructionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInstructions();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));
        buttonPanel.add(startGameButton);
        buttonPanel.add(instructionsButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void startGame() {
        GameManager gameManager = new GameManager();
        JFrame frame = new JFrame("DinoDash");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(gameManager);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        gameManager.getTimer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                gameManager.actionPerformed(null);
            }
        }, 0, 20); // Start game timer
    }

    private void showInstructions() {
        JDialog instructionsDialog = new JDialog();
        instructionsDialog.setTitle("Game Instructions");
        instructionsDialog.setPreferredSize(new Dimension(400, 300));
        instructionsDialog.setBackground(Color.WHITE);
        JTextArea instructionsArea = new JTextArea();
        instructionsArea.setText("Instructions:\n\n" +
                "Press SPACEBAR or UP ARROW KEY to jump.\n" +
                "\nAvoid obstacles and collect Score Multiplier power-ups to increase your score!");
        instructionsArea.setEditable(false);
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);
        instructionsDialog.add(instructionsArea);
        instructionsDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        instructionsDialog.pack();
        instructionsDialog.setLocationRelativeTo(null); // Center the dialog on the screen
        instructionsDialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Main Menu");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new MainMenu());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
