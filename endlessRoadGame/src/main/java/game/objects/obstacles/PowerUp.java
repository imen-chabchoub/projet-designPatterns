package main.java.game.objects.obstacles;

import javafx.scene.canvas.GraphicsContext;

public class PowerUp extends Obstacle {
    public PowerUp(String nom, double x, double y) {
        super(nom, x, y);
    }

    public PowerUp() {
        this("Power-Up", 0, 0);
    }

    @Override
    public void render(GraphicsContext gc) {
        System.out.println("Affichage d'un PowerUp à (" + positionX + ", " + positionY + ")");
    }

    @Override
    public void update() {
        super.update();
        System.out.println("Le PowerUp descend à la position Y=" + positionY);
    }
}
