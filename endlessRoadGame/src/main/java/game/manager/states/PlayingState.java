package main.java.game.manager.states;

import java.util.Random;
import main.java.game.manager.GameManager;
import main.java.game.objects.Voiture;
import main.java.game.objects.VoitureJoueur;
import main.java.game.objects.obstacles.Obstacle;
import main.java.game.objects.obstacles.ObstacleFactory;

public class PlayingState implements GameState {
    private Random random = new Random();
    private double obstacleTimer = 0;
    private ObstacleFactory factory = new ObstacleFactory();

    @Override
    public void start(GameManager manager) {
        System.out.println("=== DÉMARRAGE DU JEU ===");
        System.out.println("La voiture commence à rouler !");
        manager.addScore(-manager.getScore());
        manager.setVoiture(new VoitureJoueur("Ma Voiture"));
    }

    @Override
    public void update(GameManager manager, double deltaTime) {

    }

    private void handleCollision(GameManager manager, Voiture voiture, Obstacle obstacle) {

    }

    @Override
    public void end(GameManager manager) {
        System.out.println("Fin de la partie en cours...");
    }
}