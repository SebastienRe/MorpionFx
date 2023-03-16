package com.projet.morpion.models;

import com.projet.morpion.Launcher;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class SceneManager {
    private static SceneManager instance = null;
    private SceneManager() {
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void changeScene(String sceneName) {
        try {
            Launcher.getMainStage().setResizable(false);
            FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource(sceneName));
            Scene scene = new Scene(fxmlLoader.load(), 452, 400);
            Launcher.getMainStage().setScene(scene);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
