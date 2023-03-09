package com.projet.morpion.controller;

import com.projet.morpion.Launcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.HashMap;

public class ScreenController {
    @FXML
    protected void learn() throws IOException {
        System.out.println("Learn");
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("train.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Launcher.getMainStage().setScene(scene);
    }
}
