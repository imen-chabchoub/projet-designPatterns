package main.java.game.decorators;

import javafx.scene.canvas.GraphicsContext;
import main.java.game.objects.Voiture;

public abstract class Decorator extends Voiture {
    protected Voiture voiture;

    public Decorator(Voiture voiture) {
        super(voiture.getNom());
        this.voiture = voiture;
        this.positionX = voiture.getPositionX();
        this.speed = voiture.getSpeed();
    }

    @Override
    public void conduire() {
        voiture.conduire();
    }

    @Override
    public void moveLeft() {
        voiture.moveLeft();
    }

    @Override
    public void moveRight() {
        voiture.moveRight();
    }

    @Override
    public void render(GraphicsContext gc) {
        voiture.render(gc);
    }

    @Override
    public void update() {
        voiture.update();
    }

    @Override
    public double getPositionX() {
        return voiture.getPositionX();
    }

    @Override
    public void setPositionX(double positionX) {
        voiture.setPositionX(positionX);
    }

    @Override
    public double getSpeed() {
        return voiture.getSpeed();
    }

    @Override
    public void setSpeed(double speed) {
        voiture.setSpeed(speed);
    }
}
