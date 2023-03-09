package com.projet.morpion.controller;

import com.projet.morpion.Launcher;
import com.projet.morpion.utilities.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class MenuController {
    @FXML
    protected void learn() {
        SceneManager.getInstance().changeScene("train.fxml");
    }
}
