package main.java.game.manager.states;

import main.java.game.manager.GameManager;

public class MenuState implements GameState {
    @Override
    public void start(GameManager manager) {
        System.out.println("=== MENU PRINCIPAL ===");

    }

    @Override
    public void update(GameManager manager, double deltaTime) {

    }

    @Override
    public void end(GameManager manager) {
        System.out.println("Quitter le menu...");
    }
}