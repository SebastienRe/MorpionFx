package com.projet.morpion.controller;

import com.projet.morpion.utilities.SceneManager;
import com.projet.morpion.utilities.FilesManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.List;

public class ModelsController {
    @FXML
    GridPane grille;
    @FXML
    Button deleteButton;

    @FXML
    public void initialize() {
        deleteButton.setDisable(true);
        List<String> files = FilesManager.getFilesInDirectory("./resources/models/");
        for (String file : files) {
            Label label = new Label("  " + file);
            label.setStyle("-fx-font-size: 20px;");
            CheckBox checkBox = new CheckBox();
            checkBox.setCursor(javafx.scene.Cursor.HAND);
            checkBox.setOnAction(event -> {
                if (checkBox.isSelected()) {
                    deleteButton.setDisable(false);
                } else {
                    boolean disable = true;
                    for (int i = 1; i < grille.getChildren().size(); i = i+2) {
                        if (((CheckBox) grille.getChildren().get(i)).isSelected()) {
                            disable = false;
                        }
                    }
                    deleteButton.setDisable(disable);
                }
            });
            checkBox.setStyle("-fx-font-size: 20px;");

            grille.addRow(grille.getRowCount(), label, checkBox);
        }
    }

    @FXML
    protected void deleteButtonPressed () {
        if (SceneManager.displayPopUpAndWait("Do you really want to delete these files?", "choix") == "oui" ){
            deleteSelectedModels();
            deleteButton.setDisable(true);
        }
    }

    private void deleteSelectedModels() {
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
                FilesManager.deleteFile("./resources/models/" + fileName);
                //on supprime la ligne de la grille
                grille.getChildren().remove(i);
                grille.getChildren().remove(i - 1);
                i = i - 2; // on décrémente i de 2 pour ne pas sauter une ligne
                taille = grille.getChildren().size(); // on met à jour la taille de la grille
            }
        }
    }


    @FXML
    protected void retourMenu() {
        SceneManager.changeScene("menu.fxml");
    }
}
