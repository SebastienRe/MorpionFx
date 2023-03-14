package com.projet.morpion.controller;

import com.projet.morpion.Launcher;
import com.projet.morpion.utilities.SceneManager;
import com.projet.morpion.utilities.filesManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class MenuController {
    @FXML
    CheckBox easy;
    @FXML
    CheckBox medium;
    @FXML
    CheckBox hard;
    @FXML
    protected void onClickSettings() throws IOException {


        SceneManager.getInstance().changeScene("settings.fxml");
    }
    @FXML
    protected void modelButtonPressed() {
        SceneManager.getInstance().changeScene("models.fxml");
    }
    @FXML
    protected void onClickTrain()
    {
        SceneManager.getInstance().changeScene("train.fxml");
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
    }

    @FXML
    protected void mediumCheck() {
        easy.setSelected(false);
        hard.setSelected(false);
    }

    @FXML
    protected void hardCheck() {
        easy.setSelected(false);
        medium.setSelected(false);
    }

}
