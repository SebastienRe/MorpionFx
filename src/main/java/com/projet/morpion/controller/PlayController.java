package com.projet.morpion.controller;

import com.projet.morpion.modelMorpion.Morpion;
import com.projet.morpion.utilities.SceneManager;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PlayController {

    @FXML
    private Label resultat;
    private ImageView img;
    private ImageView img1, img2, img3;
    @FXML
    private GridPane matriceDuJeu;
    private boolean isJoeurOne = true;
    @FXML
    private Button replay;
    @FXML
    private Button quit;
    private boolean isAbleToSearchWinner = false;
    private boolean finAvecGangant = false;
    private Morpion morpion = new Morpion();

    @FXML
    protected void jouePosition(Event event) {
        Button positionJoue = (Button) event.getSource();
        Character idJouer = positionJoue.getId().charAt(6);
        System.out.println("id de la case jouée " + idJouer);
        createImgPlay();
        if (isJoeurOne) {
            morpion.afterPlayerOneMove(Integer.parseInt(idJouer.toString()));
            positionJoue.setGraphic(img);
            isJoeurOne = false;
            positionJoue.setDisable(true);
        } else {
            morpion.afterPlayerTwoMove(Integer.parseInt(idJouer.toString()));
            positionJoue.setGraphic(img);
            isJoeurOne = true;
            positionJoue.setDisable(true);
        }
        if (morpion.getNombreDeTour() >= 3) {
            if (morpion.isWin()) {
                finAvecGangant = true;
                System.out.println("###########");
                for (Node node : matriceDuJeu.getChildren()) {
                    ((Button) node).setDisable(true);
                }
                int[] positionGagnante = morpion.getPositionWinner();

                createImgWin();
                Button btn1 = (Button) matriceDuJeu.getChildren().get(positionGagnante[0]);
                btn1.setGraphic(img1);

                Button btn2 = (Button) matriceDuJeu.getChildren().get(positionGagnante[1]);
                btn2.setGraphic(img2);

                Button btn3 = (Button) matriceDuJeu.getChildren().get(positionGagnante[2]);
                btn3.setGraphic(img3);

            }
        }
        if (morpion.isEndGame()) {
            System.out.println("fini");
            if (morpion.getNombreDeTour() >= 9 && !finAvecGangant) {
                resultat.setFont(new Font("Arial", 20));
                resultat.setText("Fin de la partie, match nul");
                resultat.setVisible(true);
            }
            if (morpion.getIdWinner() == -1) {
                resultat.setFont(new Font("Arial", 20));
                resultat.setText("Félicitation joueur 1");
                resultat.setVisible(true);
            }
            if (morpion.getIdWinner() == 1) {
                resultat.setFont(new Font("Arial", 20));
                resultat.setText("Félicitation joueur 2");
                resultat.setVisible(true);
            }
            replay.setOnAction(actionEvent -> {
                SceneManager.getInstance().changeScene("matriceDuJeu.fxml");
            });
            replay.setVisible(true);
        }


    }

    @FXML
    protected void onClickBack()
    {
        SceneManager.getInstance().changeScene("menu.fxml");
    }
    private void createImgWin() {
        int result = morpion.getIdWinner();
        if (result == -1) {
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

    private void createImgPlay() {
        if(isJoeurOne)
        {
            img = new ImageView("C:\\projets\\javaFX\\MorpionFx\\resources\\images\\TicTacToe\\cross2.png");
            img.setFitWidth(30);
            img.setFitHeight(30);
        }
        else
        {
            img = new ImageView("C:\\projets\\javaFX\\MorpionFx\\resources\\images\\TicTacToe\\circle2.png");
            img.setFitWidth(30);
            img.setFitHeight(30);
        }
    }
}


