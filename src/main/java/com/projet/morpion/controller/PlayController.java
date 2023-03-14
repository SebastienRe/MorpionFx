package com.projet.morpion.controller;

import com.projet.morpion.modelMorpion.Morpion;
import com.projet.morpion.utilities.SceneManager;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class PlayController {

    @FXML
    private GridPane matriceDuJeu;
    private boolean isJoeurOne = true;
    @FXML
    private Button replay;
    @FXML
    private Button quit;
    private boolean isAbleToSearchWinner = false;
    private Morpion morpion = new Morpion();

    @FXML
    protected void jouePosition(Event event)
    {
        Button positionJoue = (Button) event.getSource();
        Character idJouer = positionJoue.getId().charAt(6);
        System.out.println("id de la case jouée " + idJouer);
        if(isJoeurOne) {
            morpion.afterPlayerOneMove(Integer.parseInt(idJouer.toString()));
            ImageView img = new ImageView("C:\\projets\\javaFX\\MorpionFx\\resources\\images\\TicTacToe\\cross2.png");
            img.setFitWidth(30);
            img.setFitHeight(30);
            positionJoue.setGraphic(img);
            //positionJoue.setText("1");
            isJoeurOne = false;
            positionJoue.setDisable(true);
        }
        else
        {
            morpion.afterPlayerTwoMove(Integer.parseInt(idJouer.toString()));
            ImageView img = new ImageView("C:\\projets\\javaFX\\MorpionFx\\resources\\images\\TicTacToe\\circle2.png");
            img.setFitWidth(30);
            img.setFitHeight(30);
            positionJoue.setGraphic(img);
            //positionJoue.setText("2");
            isJoeurOne = true;
            positionJoue.setDisable(true);
        }
        if(morpion.getNombreDeTour() >= 3) {
            if (morpion.isWin()) {
                System.out.println("###########");
                for (Node node : matriceDuJeu.getChildren()) {
                    ((Button) node).setDisable(true);
                }
            }
        }
        if(morpion.isEndGame())
        {
            System.out.println("fini");
            replay.setOnAction(actionEvent -> {
                SceneManager.getInstance().changeScene("matriceDuJeu.fxml");
            });
            replay.setVisible(true);
            quit.setOnAction(actionEvent -> {
                SceneManager.getInstance().changeScene("menu.fxml");
            });
            quit.setVisible(true);
        }


    }

}
