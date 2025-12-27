package main.java.game.decorators;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.java.game.objects.Voiture;

public class ShieldDecorator extends Decorator {
    private double shieldTimer = 300;
    private double pulseAlpha = 1.0;

    public ShieldDecorator(Voiture voiture) {
        super(voiture);
        System.out.println("ShieldDecorator appliqué.");
    }

    @Override
    public void update() {
        super.update();
        shieldTimer--;
        if (shieldTimer > 0) {
            pulseAlpha = 0.5 + 0.5 * Math.sin(System.currentTimeMillis() * 0.01);
        } else {
            pulseAlpha = 0.0;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        // Effet glow bleu autour de la voiture
        double x = voiture.getPositionX() - 25;
        double y = 500;
        double width = 50, height = 80;

        gc.save();
        gc.setGlobalAlpha(pulseAlpha * 0.3);
        gc.setFill(Color.CYAN.deriveColor(0, 1, 1, 0.8));
        gc.fillOval(x - 10, y - 20, width + 20, height + 40);
        gc.restore();

        super.render(gc);

        if (shieldTimer > 0) {
            gc.setLineWidth(3);
            gc.setStroke(Color.CYAN.deriveColor(0, 1, 1, pulseAlpha));
            gc.strokeRect(x, y, width, height);
        }
    }

    public boolean hasShield() {
        return shieldTimer > 0;
    }
}
