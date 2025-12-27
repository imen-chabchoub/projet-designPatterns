package main.java.game.objects.obstacles;

import javafx.scene.canvas.GraphicsContext;

public class Block extends Obstacle {
    public Block(String nom, double x, double y) {
        super(nom, x, y);
    }

    public Block() {
        this("Bloc", 0, 0);
    }

    @Override
    public void render(GraphicsContext gc) {
        System.out.println("Affichage d'un bloc à (" + positionX + ", " + positionY + ")");
    }

    @Override
    public void update() {
        super.update();
        System.out.println("Le bloc descend à la position Y=" + positionY);
    }
}