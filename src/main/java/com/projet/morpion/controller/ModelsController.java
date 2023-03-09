package com.projet.morpion.controller;

import com.projet.morpion.Launcher;
import com.projet.morpion.utilities.SceneManager;
import com.projet.morpion.utilities.filesManager;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.List;

public class ModelsController {
    @FXML
    GridPane grille;

    @FXML
    public void initialize() {
        List<String> files = filesManager.getFilesInDirectory("./resources/models/");
        for (String file : files)
            grille.addRow(grille.getRowCount(), new Label("  "+file), new CheckBox());
    }

    @FXML
    protected void deleteButtonPressed () {
        //pour chaque ligne de la grille
        int taille = grille.getChildren().size();
        for (int i = 1; i < taille; i = i+2) {
            //on récupère la checkbox
            CheckBox checkBox = (CheckBox) grille.getChildren().get(i);
            //si elle est cochée
            if (checkBox.isSelected()) {
                //on récupère le nom du fichier
                String fileName = ((Label) grille.getChildren().get(i - 1)).getText().replace("  ", "");
                //on supprime le fichier
                filesManager.deleteFile("./resources/models/" + fileName);
                //on supprime la ligne de la grille
                grille.getChildren().remove(i);
                grille.getChildren().remove(i - 1);
                i = i - 2; // on décrémente i de 2 pour ne pas sauter une ligne
                taille = grille.getChildren().size(); // on met à jour la taille de la grille
            }
        }
        SceneManager.getInstance().changeScene("menu.fxml");
    }

    @FXML
    protected void retourMenu() {
        SceneManager.getInstance().changeScene("menu.fxml");
    }
}
