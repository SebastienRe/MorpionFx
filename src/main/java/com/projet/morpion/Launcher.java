package com.projet.morpion;

import com.projet.morpion.utilities.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {
    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        SceneManager.getInstance().changeScene("menu.fxml");
        stage.setTitle("JavaFX-Morpion");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    public static Stage getMainStage() {
        return mainStage;
    }
}