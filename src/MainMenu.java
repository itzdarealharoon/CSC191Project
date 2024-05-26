import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

/**
 * Lead Author(s): Haroon Usman; 5550080871
 * @author 
 * @author 
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
 * The MainMenu class is responsible for displaying the main menu of the game with options to start the game or view instructions.
 */
public class MainMenu extends JPanel 
{
    
    /**
     * Constructor for the MainMenu class.
     * Sets up the main menu with buttons for starting the game and viewing instructions.
     */
    public MainMenu() 
    {
        setPreferredSize(new Dimension(800, 400)); // Set the size of the main menu
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Create and configure the Start Game button
        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                startGame();
            }
        });

        // Create and configure the Instructions button
        JButton instructionsButton = new JButton("Instructions");
        instructionsButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                showInstructions();
            }
        });

        // Add the buttons to a panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));
        buttonPanel.add(startGameButton);
        buttonPanel.add(instructionsButton);

        // Add the button panel to the center of the main menu
        add(buttonPanel, BorderLayout.CENTER);
    }

    /**
     * Starts the game by creating a new GameManager instance and displaying it in a new JFrame.
     */
    private void startGame() 
    {
        JFrame frame = new JFrame("DinoDash");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GameManager gameManager = new GameManager(frame);
        frame.getContentPane().add(gameManager);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Displays the game instructions in a dialog.
     */
    private void showInstructions() 
    {
        JDialog instructionsDialog = new JDialog();
        instructionsDialog.setTitle("Game Instructions");
        instructionsDialog.setPreferredSize(new Dimension(400, 300));
        instructionsDialog.setBackground(Color.WHITE);

        // Create and configure the instructions text area
        JTextArea instructionsArea = new JTextArea();
        instructionsArea.setText("Instructions:\n\n" +
                "Objective:\n" +
                "Avoid obstacles and collect power-ups to increase your score.\n\n" +
                "Controls:\n" +
                "Press SPACEBAR or UP ARROW KEY to jump.\n" +
                "Press DOWN ARROW KEY to cancel a jump and fall faster.\n\n" +
                "Power-Ups:\n" +
                "Score Multiplier (Green Circle): Doubles your score for a limited time.\n" +
                "Bouncy Spring (Spring Image): Propels you upwards in the air!");
        instructionsArea.setEditable(false);
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);

        // Add the instructions text area to the dialog
        instructionsDialog.add(new JScrollPane(instructionsArea));
        instructionsDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        instructionsDialog.pack();
        instructionsDialog.setLocationRelativeTo(null); // Center the dialog on the screen
        instructionsDialog.setVisible(true);
    }

    /**
     * The main method to launch the main menu.
     * @param args Command line arguments
     */
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
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