package main.java.game.manager.states;

import main.java.game.manager.GameManager;

public class GameOverState implements GameState {
    @Override
    public void start(GameManager manager) {
        System.out.println("=== GAME OVER ===");
        System.out.println("Score final: " + manager.getScore());

    }

    @Override
    public void update(GameManager manager, double deltaTime) {

    }

    @Override
    public void end(GameManager manager) {
        System.out.println("Quitter l'écran de fin de jeu...");
    }
}