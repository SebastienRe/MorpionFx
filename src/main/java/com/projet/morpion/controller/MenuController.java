package com.projet.morpion.controller;

import com.projet.morpion.utilities.SceneManager;
import com.projet.morpion.utilities.FilesManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.List;

public class MenuController {
    @FXML
    CheckBox easy;
    @FXML
    CheckBox medium;
    @FXML
    CheckBox hard;
    @FXML
    VBox vbox_affichage;
    String model = "";

    boolean canStart = false;

    @FXML
    public void initialize() {
    }

    @FXML
    protected void onClickSettings() throws IOException {
        SceneManager.getInstance().changeScene("settings.fxml");
    }
    @FXML
    protected void modelButtonPressed() {
        SceneManager.getInstance().changeScene("models.fxml");
    }

    @FXML
    protected void humanVShuman() {
        PlayController.setIsAi(false);
        SceneManager.getInstance().changeScene("jeu.fxml");
    }

    @FXML
    protected void humanVSai() {
        if (easy.isSelected() || medium.isSelected() || hard.isSelected()) {
            if (canStart){
                PlayController.setIsAi(true);
                SceneManager.getInstance().changeScene("jeu.fxml");
            }
            else {
                vbox_affichage.getChildren().clear();
                Label label = new Label("You have to train the model before playing");
                label.setStyle("-fx-text-fill: #ff0000;");
                vbox_affichage.getChildren().add(label);
                easy.setSelected(false);
                medium.setSelected(false);
                hard.setSelected(false);
            }
        } else {
            vbox_affichage.getChildren().clear();
            Label label = new Label("Please select a difficulty");
            label.setStyle("-fx-text-fill: #ff0000;");
            vbox_affichage.getChildren().add(label);
        }
    }

    @FXML
    protected void easyCheck() {
        if (easy.isSelected()) {
            medium.setSelected(false);
            hard.setSelected(false);
            trainORplay("F");
        } else
            vbox_affichage.getChildren().clear();
    }

    @FXML
    protected void mediumCheck() {
        if (medium.isSelected()) {
            easy.setSelected(false);
            hard.setSelected(false);
            trainORplay("M");
        } else
            vbox_affichage.getChildren().clear();
    }

    @FXML
    protected void hardCheck() {
        if (hard.isSelected()) {
            easy.setSelected(false);
            medium.setSelected(false);
            trainORplay("D");
        } else
            vbox_affichage.getChildren().clear();
    }

    private void trainORplay(String difficulty) {
        String modelFile = FilesManager.getModelSrl(difficulty);
        List<String> models = FilesManager.getFilesInDirectory("./resources/models/");
        System.out.println(modelFile);
        vbox_affichage.getChildren().clear();
        if (models.contains(modelFile)) { // if the model exists
            canStart = true;
            Label label = new Label("Ready to play !");
            label.setStyle("-fx-text-fill: #0000ff;");
            vbox_affichage.getChildren().add(label);
            PlayController.setModel("./resources/models/"+modelFile);
        } else { // if the model doesn't exist
            TrainerController.setDifficulty(difficulty);
            canStart = false;
            Button button = new Button("Train the AI");
            //reduire taille du texte
            button.setStyle("-fx-font-size: 20px;");
            //reduire la taille du boutton
            button.setMinSize(70, 75);
            button.setOnAction(event -> {
                SceneManager.getInstance().changeScene("train.fxml");
            });
            vbox_affichage.getChildren().add(button);
        }
    }
}
