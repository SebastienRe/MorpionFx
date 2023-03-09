package com.projet.morpion.utilities;

import com.projet.morpion.Launcher;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.HashMap;

public class SceneManager {
    private HashMap<String, Scene> scenes;
    private static SceneManager instance = null;
    private SceneManager() {
        scenes = new HashMap<>();
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void changeScene(String sceneName) {
        try {
            if (!scenes.containsKey(sceneName)) {
                FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource(sceneName));
                Scene scene = new Scene(fxmlLoader.load(), 320, 240);
                scenes.put(sceneName, scene);
            }
            Launcher.getMainStage().setScene(scenes.get(sceneName));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
