package com.projet.morpion.controller;

import com.projet.morpion.Launcher;
import com.projet.morpion.utilities.SceneManager;
import com.projet.morpion.utilities.filesManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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
        SceneManager.getInstance().changeScene("play.fxml");
    }

    @FXML
    protected void humanVSai() {
        SceneManager.getInstance().changeScene("playIA.fxml");
    }

    @FXML
    protected void easyCheck() {
        medium.setSelected(false);
        hard.setSelected(false);
        trainORplay("F");
    }

    @FXML
    protected void mediumCheck() {
        easy.setSelected(false);
        hard.setSelected(false);
        trainORplay("M");
    }

    @FXML
    protected void hardCheck() {
        easy.setSelected(false);
        medium.setSelected(false);
        trainORplay("D");
    }

    private String getModelSrl(String difficulty) {
        try {
            File file = new File("./resources/config.txt");
            // Créer l'objet File Reader
            FileReader fr = null;
            fr = new FileReader(file);
            // Créer l'objet BufferedReader
            BufferedReader br = new BufferedReader(fr);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                String[] configLu;
                configLu = line.split(":");
                if (configLu[0].equals(difficulty)) {
                    return "model_"+configLu[3]+"_"+configLu[1]+"_"+configLu[2]+".srl";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void trainORplay(String difficulty) {
        String modelFile = getModelSrl(difficulty);
        List<String> models = filesManager.getFilesInDirectory("./resources/models/");
        pane.getChildren().clear();
        TrainerController.setDifficulty(difficulty);
        if (models.contains(modelFile)) { // if the model exists
            Label label = new Label("Ready to play!");
            //change color of the text
            label.setStyle("-fx-text-fill: #0000ff;");
            pane.getChildren().add(label);
        } else { // if the model doesn't exist
            Button button = new Button("Train");
            button.setOnAction(event -> {
                SceneManager.getInstance().changeScene("train.fxml");
            });
            pane.getChildren().add(button);
        }
    }
}
