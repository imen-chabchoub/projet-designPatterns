package main.java.game.objects;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {
    protected String nom;

    public GameObject(String nom) {
        this.nom = nom;
    }

    public abstract void render(GraphicsContext gc );

    public abstract void update();
}