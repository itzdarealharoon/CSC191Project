public class ScoreManager {
    private int score;

    public ScoreManager() {
        score = 0;
    }

    public void increaseScore(int multiplier) {
        if (multiplier > 1) {
            score += multiplier;
        } else {
            score++;
        }
    }

    public int getScore() {
        return score;
    }
}