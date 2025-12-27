package main.java.game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import main.java.game.manager.GameManager;
import main.java.game.manager.GameManagerSingleton;
import java.io.IOException;

public class Main extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        GameManager manager = GameManagerSingleton.getInstance();
        // Initialisation de la scène du menu
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/main/resources/game/views/Menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Endless Road Game");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
