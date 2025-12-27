package main.java.game.objects.obstacles;

import java.util.Random;

public class ObstacleFactory {
    private static final Random random = new Random();

    public Obstacle createObstacle(String type, double x, double y) {
        switch (type) {
            case "Block":
                return new Block("bloc", x, y);
            case "PowerUp":
                return new PowerUp("powerUp", x, y);
            case "RoadHole":
                return new RoadHole("Hole", x, y);
            default:
                throw new IllegalArgumentException("Type d'obstacle inconnu : " + type);
        }
    }

    public Obstacle createObstacle(String type) {
        double x = random.nextDouble() * 100;
        double y = -50;
        return createObstacle(type, x, y);
    }

    public Obstacle createRandomObstacle() {
        String[] types = { "Block", "PowerUp", "RoadHole" };
        String randomType = types[random.nextInt(types.length)];
        return createObstacle(randomType);
    }
}