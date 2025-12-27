package main.java.game.manager;

public class GameManagerSingleton {
    private static GameManager instance;

    private GameManagerSingleton() {
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }
}
