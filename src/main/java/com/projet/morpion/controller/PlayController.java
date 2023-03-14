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

    private ImageView img1, img2, img3;
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
                int [] positionGagnante = morpion.getPositionWinner();

                createImg();
                Button btn1 = (Button) matriceDuJeu.getChildren().get(positionGagnante[0]);
                btn1.setGraphic(img1);

                Button btn2 = (Button) matriceDuJeu.getChildren().get(positionGagnante[1]);
                btn2.setGraphic(img2);

                Button btn3 = (Button) matriceDuJeu.getChildren().get(positionGagnante[2]);
                btn3.setGraphic(img3);

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
    private void createImg()
    {
        int result = morpion.getIdWinner();
        if(result == -1)
        {
            img1 = new ImageView("C:\\projets\\javaFX\\MorpionFx\\resources\\images\\TicTacToe\\winCross2.png");
            img1.setFitWidth(30);
            img1.setFitHeight(30);
            img2 = new ImageView("C:\\projets\\javaFX\\MorpionFx\\resources\\images\\TicTacToe\\winCross2.png");
            img2.setFitWidth(30);
            img2.setFitHeight(30);
            img3 = new ImageView("C:\\projets\\javaFX\\MorpionFx\\resources\\images\\TicTacToe\\winCross2.png");
            img3.setFitWidth(30);
            img3.setFitHeight(30);
        } else if (result == 1) {
            img1 = new ImageView("C:\\projets\\javaFX\\MorpionFx\\resources\\images\\TicTacToe\\win_circle2.png");
            img1.setFitWidth(30);
            img1.setFitHeight(30);
            img2 = new ImageView("C:\\projets\\javaFX\\MorpionFx\\resources\\images\\TicTacToe\\win_circle2.png");
            img2.setFitWidth(30);
            img2.setFitHeight(30);
            img3 = new ImageView("C:\\projets\\javaFX\\MorpionFx\\resources\\images\\TicTacToe\\win_circle2.png");
            img3.setFitWidth(30);
            img3.setFitHeight(30);

        }
    }

}
