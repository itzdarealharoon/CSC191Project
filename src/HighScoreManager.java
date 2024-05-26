import java.io.*;

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
 * Manages the high score, handles saving and loading high scores from a file.
 * HAS-A relationship with file I/O operations.
 */
public class HighScoreManager 
{
    private static final String HIGH_SCORE_FILE = "highscore.txt";
    private int highScore;

    /**
     * Constructs a new HighScoreManager and loads the high score from file.
     */
    public HighScoreManager()
    {
        highScore = loadHighScore();
    }

    /**
     * Loads the high score from a file.
     * 
     * @return The high score loaded from the file, or 0 if an error occurs.
     */
    private int loadHighScore() 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(HIGH_SCORE_FILE))) 
        {
            return Integer.parseInt(reader.readLine());
        } 
        catch (IOException | NumberFormatException e) 
        {
            return 0;
        }
    }

    /**
     * Saves the high score to a file.
     * 
     * @param score The high score to save.
     */
    private void saveHighScore(int score) 
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HIGH_SCORE_FILE))) 
        {
            writer.write(String.valueOf(score));
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    /**
     * Gets the current high score.
     * 
     * @return The current high score.
     */
    public int getHighScore() 
    
    {
        return highScore;
    }

    /**
     * Checks if the given score is higher than the current high score, and updates the high score if it is.
     * 
     * @param score The score to check.
     */
    public void checkAndSetHighScore(int score) 
    {
        if (score > highScore)
        {
            highScore = score;
            saveHighScore(score);
        }
    }
}