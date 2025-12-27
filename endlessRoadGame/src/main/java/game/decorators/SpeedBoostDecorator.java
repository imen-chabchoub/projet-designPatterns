package main.java.game.decorators;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import main.java.game.objects.Voiture;

public class SpeedBoostDecorator extends Decorator {
    private double boostAmount;
    private double boostTimer = 180; // 3s
    private List<Particle> particles = new ArrayList<>();

    public SpeedBoostDecorator(Voiture voiture, double boostAmount) {
        super(voiture);
        this.boostAmount = boostAmount;
        System.out.println("SpeedBoost activé.");
    }

    @Override
    public void update() {
        super.update();
        boostTimer--;

        if (boostTimer > 0) {

            double baseSpeed = 10.0;
            voiture.setSpeed(baseSpeed * 2.0); // Double vitesse pendant boost

            // Particules
            if (Math.random() < 0.7) {
                particles.add(new Particle(
                        voiture.getPositionX() + 25, 550,
                        (Math.random() - 0.5) * 4, Math.random() * 3 + 2));
            }
        } else {
            // Retour à vitesse normale
            voiture.setSpeed(10.0);
            particles.clear();
        }

        particles.removeIf(p -> p.life <= 0);
        particles.forEach(Particle::update);
    }

    @Override
    public void render(GraphicsContext gc) {

        particles.forEach(p -> p.render(gc));

        // Flammes sous voiture (si boost actif)
        if (boostTimer > 0) {
            gc.save();
            gc.setGlobalAlpha(0.8);
            gc.setFill(Color.ORANGE.deriveColor(0, 1, 0.8, 0.9));
            gc.fillOval(voiture.getPositionX() - 10, 560, 30, 25);
            gc.setFill(Color.RED.deriveColor(0, 1, 1, 0.9));
            gc.fillOval(voiture.getPositionX() - 5, 570, 20, 15);
            gc.restore();
        }

        super.render(gc);
    }

    private static class Particle {
        double x, y, vx, vy, life = 1.0;

        Particle(double x, double y, double vx, double vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }

        void update() {
            x += vx;
            y += vy;
            vy += 0.2;
            life -= 0.03;
        }

        void render(GraphicsContext gc) {
            gc.save();
            gc.setGlobalAlpha(life);
            gc.setFill(Color.YELLOW.deriveColor(0, 1, 1, life));
            gc.fillOval(x, y, 6 * life, 6 * life);
            gc.restore();
        }
    }
}
