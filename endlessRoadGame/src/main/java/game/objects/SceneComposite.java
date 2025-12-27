package main.java.game.objects;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;

public class SceneComposite extends GameObject {
    private List<GameObject> children = new ArrayList<>();

    public SceneComposite(String nom) {
        super(nom);
    }

    public void add(GameObject gameObject) {
        children.add(gameObject);
    }

    public void remove(GameObject gameObject) {
        children.remove(gameObject);
    }

    @Override
    public void render(GraphicsContext gc) {
        System.out.println("Rendu du composite: " + nom);
        for (GameObject child : children) {
            child.render(gc);
        }
    }

    @Override
    public void update() {
        System.out.println("Mise à jour du composite: " + nom);
        for (GameObject child : children) {
            child.update();
        }
    }
}