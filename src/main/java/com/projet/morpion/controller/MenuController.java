package com.projet.morpion.controller;

import com.projet.morpion.utilities.SceneManager;
import com.projet.morpion.utilities.FilesManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Pane;

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
    Pane pane;
    String model = "";

    boolean canStart = false;
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
        PlayController.setAi(false);
        SceneManager.getInstance().changeScene("jeu.fxml");
    }

    @FXML
    protected void humanVSai() {
        if (easy.isSelected() || medium.isSelected() || hard.isSelected()) {
            if (canStart){
                PlayController.setAi(true);
                SceneManager.getInstance().changeScene("jeu.fxml");
            }
            else {
                pane.getChildren().clear();
                Label label = new Label("You have to train the model before playing");
                label.setStyle("-fx-text-fill: #ff0000;");
                pane.getChildren().add(label);
                easy.setSelected(false);
                medium.setSelected(false);
                hard.setSelected(false);
            }
        } else {
            pane.getChildren().clear();
            Label label = new Label("Please select a difficulty");
            label.setStyle("-fx-text-fill: #ff0000;");
            pane.getChildren().add(label);
        }
    }

    @FXML
    protected void easyCheck() {
        if (easy.isSelected()) {
            medium.setSelected(false);
            hard.setSelected(false);
            trainORplay("F");
        } else
            pane.getChildren().clear();
    }

    @FXML
    protected void mediumCheck() {
        if (medium.isSelected()) {
            easy.setSelected(false);
            hard.setSelected(false);
            trainORplay("M");
        } else
            pane.getChildren().clear();
    }

    @FXML
    protected void hardCheck() {
        if (hard.isSelected()) {
            easy.setSelected(false);
            medium.setSelected(false);
            trainORplay("D");
        } else
            pane.getChildren().clear();
    }

    private void trainORplay(String difficulty) {
        String modelFile = FilesManager.getModelSrl(difficulty);
        List<String> models = FilesManager.getFilesInDirectory("./resources/models/");
        pane.getChildren().clear();
        if (models.contains(modelFile)) { // if the model exists
            canStart = true;
            Label label = new Label("Ready to play!");
            label.setStyle("-fx-text-fill: #0000ff;");
            pane.getChildren().add(label);
            this.model = modelFile;
        } else { // if the model doesn't exist
            canStart = false;
            Button button = new Button("Train the AI");
            button.setOnAction(event -> {
                SceneManager.getInstance().changeScene("train.fxml");
            });
            pane.getChildren().add(button);
        }
    }
}
