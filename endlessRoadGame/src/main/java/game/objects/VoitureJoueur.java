package main.java.game.objects;

import javafx.scene.canvas.GraphicsContext;

public class VoitureJoueur extends Voiture {
    private double moveSpeed = 8.0;
    private double verticalVelocity = 0;
    private double gravity = 0.5;
    private double jumpPower = 15;
    private boolean isJumping = false;
    private double groundY = 0;

    public VoitureJoueur(String nom) {
        super(nom);
        this.positionX = 375;
        this.speed = 10;
        this.groundY = 480;
    }

    @Override
    public void conduire() {

        System.out.println(this.nom + " roule à la position X=" + this.positionX);
    }

    @Override
    public void moveLeft() {
        if (this.positionX > 0) {
            this.positionX -= moveSpeed;
        }
    }

    @Override
    public void moveRight() {
        if (this.positionX < 750) {
            this.positionX += moveSpeed;
        }
    }

    public void jump() {
        if (!isJumping) {
            isJumping = true;
            verticalVelocity = -jumpPower;
        }
    }

    public void updatePhysics(double positionY) {
        // Mettre à jour la position Y en fonction de la gravité
        if (isJumping) {
            verticalVelocity += gravity;
            positionY += verticalVelocity;

            // Vérifier si la voiture a touché le sol
            if (positionY >= groundY) {
                positionY = groundY;
                isJumping = false;
                verticalVelocity = 0;
            }
        }
    }

    public boolean isJumping() {
        return isJumping;
    }

    public double getVerticalVelocity() {
        return verticalVelocity;
    }

    public void setGroundY(double groundY) {
        this.groundY = groundY;
    }

    @Override
    public void render(GraphicsContext gc) {
        System.out.println("Affichage de la voiture '" + this.nom + "' à la position X=" + this.positionX);
    }

    @Override
    public void update() {
        conduire();
    }
}
