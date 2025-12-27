package main.java.game.objects.obstacles;

import javafx.scene.canvas.GraphicsContext;
import main.java.game.objects.GameObject;

public abstract class Obstacle extends GameObject {
    protected double positionX;
    protected double positionY;
    private double speed = 5.0;

    public Obstacle(String nom, double positionX, double positionY) {
        super(nom);
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void update() {

        this.positionY += speed;
    }

    @Override
    public void render(GraphicsContext gc) {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " en position (" + positionX + ", " + positionY + ")";
    }
}
