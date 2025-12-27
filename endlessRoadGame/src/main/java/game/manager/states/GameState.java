package main.java.game.manager.states;

import main.java.game.manager.GameManager;

public interface GameState {
    void start(GameManager manager);

    void update(GameManager manager, double deltaTime);

    void end(GameManager manager);
}