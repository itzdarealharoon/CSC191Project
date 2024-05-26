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
 * Manages the player's score, handles score incrementation and retrieval.
 */
public class ScoreManager 
{
    private int score; // The player's current score

    /**
     * Constructor for the ScoreManager class.
     * Initializes the score to 0.
     */
    public ScoreManager()
    {
        score = 0;
    }

    /**
     * Increases the score by the given multiplier.
     * If the multiplier is greater than 1, the score is increased by the multiplier.
     * Otherwise, the score is increased by 1.
     * @param multiplier The multiplier to increase the score by
     */
    public void increaseScore(int multiplier) 
    {
        if (multiplier > 1)
        {
            score += multiplier;
        } 
        else
        {
            score++;
        }
    }

    /**
     * Returns the player's current score.
     * @return The player's current score
     */
    public int getScore() 
    {
        return score;
    }
}