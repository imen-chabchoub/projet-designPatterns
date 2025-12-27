package main.java.game.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;
import main.java.game.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.java.game.manager.states.PlayingState;
import main.java.game.manager.GameManagerSingleton;

public class MenuController {
    @FXML
    private Button playButton;
    @FXML
    private Button quitButton;

    @FXML
    private void initialize() {
        playButton.setOnAction(e -> {
            GameManagerSingleton.getInstance().setState(new PlayingState());
            try {

                FXMLLoader gameLoader = new FXMLLoader(
                        Main.class.getResource("/main/resources/game/views/GameView.fxml"));
                Scene gameScene = new Scene(gameLoader.load(), 800, 600);
                Main.getPrimaryStage().setScene(gameScene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        quitButton.setOnAction(e -> System.exit(0));
    }
}