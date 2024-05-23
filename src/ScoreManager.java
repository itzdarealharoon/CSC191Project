public class ScoreManager {
    private int score;

    public ScoreManager() {
        score = 0;
    }

    public void increaseScore() {
        score++;
    }

    public int getScore() {
        return score;
    }
}