package com.projet.morpion.controller;

import com.projet.morpion.Launcher;
import com.projet.morpion.utilities.SceneManager;
import com.projet.morpion.utilities.filesManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.util.List;

public class MenuController {
    @FXML
    protected void learn() {
        SceneManager.getInstance().changeScene("train.fxml");
    }
    @FXML
    protected void modelButtonPressed() {
        SceneManager.getInstance().changeScene("models.fxml");
    }
}
