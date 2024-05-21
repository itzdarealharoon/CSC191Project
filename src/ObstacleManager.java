// update

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ObstacleManager {
    private List<Obstacle> obstacles;

    public ObstacleManager() {
        obstacles = new ArrayList<>();
    }

    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
    }

    public void removeObstacle(Obstacle obstacle) {
        obstacles.remove(obstacle);
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public void update() {
        // Move each obstacle
        Iterator<Obstacle> iterator = obstacles.iterator();
        while (iterator.hasNext()) {
            Obstacle obstacle = iterator.next();
            obstacle.move();
            // Remove obstacle if it's off the screen
            if (obstacle.getX() + obstacle.getWidth() < 0) {
                iterator.remove();
            }
        }
    }
}
