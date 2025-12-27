package main.java.game.objects;

public abstract class Voiture extends GameObject {
    protected double positionX;
    protected double speed;

    public Voiture(String nom) {
        super(nom);
    }

    public abstract void conduire();

    public abstract void moveLeft();

    public abstract void moveRight();

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getNom() {
        return nom;
    }
}
