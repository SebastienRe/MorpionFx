package com.projet.morpion.controller;

import com.projet.morpion.Launcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class PopupController {
    @FXML
    public Label text;
    @FXML
    public HBox hBoxButtons;

    public Stage stage;
    public String answer = null;

    public String getResult() {
        return answer;
    }

    public void mode(String choix) {
        hBoxButtons.getChildren().clear();

        if (choix.equals("choix")) {
            Button oui = new Button("Oui");
            oui.getStyleClass().add("button2");
            Button non = new Button("Non");
            non.getStyleClass().add("button2");
            hBoxButtons.getChildren().addAll(oui, non);

            //si le bouton oui est cliqué answer = oui et stage.close
            oui.setOnAction(event -> {
                answer = "oui";
                stage.close();
            });
            //si le bouton non est cliqué answer = non et stage.close
            non.setOnAction(event -> {
                answer = "non";
                stage.close();
            });

        } else if (choix.equals("save")) {
            Button ok = new Button("OK");
            ok.getStyleClass().add("button2");
            hBoxButtons.getChildren().addAll(ok);

            //si le bouton non est cliqué answer = non et stage.close
            ok.setOnAction(event -> {
                answer = "ok";
                stage.close();
            });
        }

    }
}
