package com.projet.morpion.controller;

import com.projet.morpion.Launcher;
import com.projet.morpion.utilities.SceneManager;
import com.projet.morpion.utilities.filesManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class MenuController {
    @FXML
    protected void onClickSettings() throws IOException {


        SceneManager.getInstance().changeScene("settings.fxml");
    }
    @FXML
    protected void modelButtonPressed() {
        SceneManager.getInstance().changeScene("models.fxml");
    }
}
