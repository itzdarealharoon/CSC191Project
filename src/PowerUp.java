public class PowerUp {
    private boolean hasSlowMotion;
    private boolean hasScoreBooster;

    public PowerUp() {
        hasSlowMotion = false;
        hasScoreBooster = false;
    }

    public boolean hasSlowMotion() {
        return hasSlowMotion;
    }

    public boolean hasScoreBooster() {
        return hasScoreBooster;
    }

    public void activateSlowMotion() {
        hasSlowMotion = true;
    }

    public void activateScoreBooster() {
        hasScoreBooster = true;
    }
}
