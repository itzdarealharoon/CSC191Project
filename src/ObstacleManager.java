import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class ObstacleManager {
    private List<Obstacle> obstacles;

    public ObstacleManager() {
        obstacles = new ArrayList<>();
    }

    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public void update() {
        for (Obstacle obstacle : obstacles) {
            obstacle.move();
        }
        obstacles.removeIf(obstacle -> obstacle.getX() < 0);
    }

    public void draw(Graphics g) {
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(g);
        }
    }
}
