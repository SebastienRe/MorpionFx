package com.projet.morpion.controller;

import com.projet.morpion.utilities.SceneManager;
import com.projet.morpion.utilities.FilesManager;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.List;

public class MenuController {
    @FXML
    CheckBox easy;
    @FXML
    CheckBox medium;
    @FXML
    CheckBox hard;
    @FXML
    VBox vboxAffichage;
    @FXML
    Menu menuCredit;
    @FXML
    Label titre;
    String model = "";

    boolean canStart = false;

    @FXML
    public void initialize() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(titre.scaleXProperty(), 1.0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(titre.scaleXProperty(), 1.2)),
                new KeyFrame(Duration.seconds(2), new KeyValue(titre.scaleXProperty(), 1.0))
        );
        timeline.setAutoReverse(true);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    protected void credit(){
        SceneManager.getInstance().displaySceneAndWait("Graphic interface project realized by :\nSébastien Ré\nLyes Douki", "ok");
    }

    @FXML
    protected void onClickSettings() {
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
                vboxAffichage.getChildren().clear();
                Label label = new Label("You have to train the model before playing");
                label.setStyle("-fx-text-fill: #ff0000;");
                vboxAffichage.getChildren().add(label);
                easy.setSelected(false);
                medium.setSelected(false);
                hard.setSelected(false);
            }
        } else {
            vboxAffichage.getChildren().clear();
            Label label = new Label("Please select a difficulty");
            label.setStyle("-fx-text-fill: #ff0000;");
            vboxAffichage.getChildren().add(label);
        }
    }

    @FXML
    protected void easyCheck() {
        if (easy.isSelected()) {
            medium.setSelected(false);
            hard.setSelected(false);
            trainORplay("F");
        } else
            vboxAffichage.getChildren().clear();
    }

    @FXML
    protected void mediumCheck() {
        if (medium.isSelected()) {
            easy.setSelected(false);
            hard.setSelected(false);
            trainORplay("M");
        } else
            vboxAffichage.getChildren().clear();
    }

    @FXML
    protected void hardCheck() {
        if (hard.isSelected()) {
            easy.setSelected(false);
            medium.setSelected(false);
            trainORplay("D");
        } else
            vboxAffichage.getChildren().clear();
    }

    private void trainORplay(String difficulty) {
        String modelFile = FilesManager.getModelSrl(difficulty);
        List<String> models = FilesManager.getFilesInDirectory("./resources/models/");
        System.out.println(modelFile);
        vboxAffichage.getChildren().clear();
        if (models.contains(modelFile)) { // if the model exists
            canStart = true;
            Label label = new Label("Ready to play !");
            label.setStyle("-fx-text-fill: #0000ff;");
            vboxAffichage.getChildren().add(label);
            PlayController.setModel("./resources/models/"+modelFile);
        } else { // if the model doesn't exist
            TrainerController.setDifficulty(difficulty);
            canStart = false;
            Button button = new Button("Train the AI");
            button.getStyleClass().add("button2");
            button.setOnAction(event -> {
                SceneManager.getInstance().changeScene("train.fxml");
            });
            vboxAffichage.getChildren().add(button);
        }
    }
}
