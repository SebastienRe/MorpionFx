package com.projet.morpion;

import com.projet.morpion.utilities.SceneManager;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {
    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        SceneManager.changeScene("menu.fxml");
        stage.setTitle("Tic-Tac-Toe");
        stage.getIcons().add(new Image("file:./resources/images/TicTacToe/logo.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    public static Stage getMainStage() {
        return mainStage;
    }
}