package com.projet.morpion.utilities;

import com.projet.morpion.Launcher;
import com.projet.morpion.controller.PopupController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {


    public static void changeScene(String sceneName) {
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

    public static String displayPopUpAndWait(String text, String mode) {
        try {
            // Créer une nouvelle fenêtre
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL); // Bloquer l'accès à la fenêtre principale
            popupStage.setResizable(false);
            popupStage.getIcons().add(new Image("file:./resources/images/TicTacToe/logo.png"));

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
