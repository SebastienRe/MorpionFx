package com.projet.morpion.utilities;

import com.projet.morpion.Launcher;
import com.projet.morpion.controller.PopupController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    public static Popup popup;
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

    public String displaySceneAndWait(String text, String mode) {
        try {
            // Créer une nouvelle fenêtre
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setResizable(false);

            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("popup.fxml"));
            Parent root = loader.load();

            // Créer la scène
            Scene scene = new Scene(root);

            // Appliquer la scène à la fenêtre
            popupStage.setScene(scene);

            // Récupérer le contrôleur du fichier FXML
            PopupController controller = loader.getController();
            controller.text.setText(text);
            controller.stage = popupStage;
            controller.mode(mode);

            //Attendre la fermeture de la fenêtre
            popupStage.showAndWait();

            // Récupérer le résultat du popup
            return controller.getResult();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
