package com.projet.morpion.controller;

import com.projet.morpion.modelMorpion.Morpion;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class PlayController {

    @FXML
    private GridPane matriceDuJeu;
    private boolean isJoeurOne = true;

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
            positionJoue.setText("1");
            isJoeurOne = false;
            positionJoue.setDisable(true);
        }
        else
        {
            morpion.afterPlayerTwoMove(Integer.parseInt(idJouer.toString()));
            positionJoue.setText("2");
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
        }


    }

}
