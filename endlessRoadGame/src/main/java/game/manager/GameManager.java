package main.java.game.manager;

import main.java.game.manager.states.GameState;
import main.java.game.manager.states.MenuState;
import main.java.game.objects.Voiture;
import main.java.game.objects.VoitureJoueur;

public class GameManager {
    private GameState currentState;
    private int score = 0;
    private boolean running = true;
    private Voiture voiture;

    public GameManager() {
        setState(new MenuState());
    }

    public void setState(GameState newState) {
        if (currentState != null)
            currentState.end(this);
        currentState = newState;
        currentState.start(this);
    }

    public void update(double deltaTime) {
        if (currentState != null)
            currentState.update(this, deltaTime);
    }

    public void addScore(int s) {
        score += s;
    }

    public int getScore() {
        return score;
    }

    public void resetScore() {
        this.score = 0;
    }

    public boolean isRunning() {
        return running;
    }

    public void stopGame() {
        running = false;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture v) {
        voiture = v;
    }
}