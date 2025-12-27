package main.java.game.objects.obstacles;

import javafx.scene.canvas.GraphicsContext;

public class RoadHole extends Obstacle {
    public RoadHole(String nom, double x, double y) {
        super(nom, x, y);
    }

    public RoadHole() {
        this("Trou dans la Route", 0, 0);
    }

    @Override
    public void render(GraphicsContext gc) {
        System.out.println("Affichage d'un trou dans la route à (" + positionX + ", " + positionY + ")");
    }

    @Override
    public void update() {
        super.update();
        System.out.println("Le trou descend à la position Y=" + positionY);
    }
}
