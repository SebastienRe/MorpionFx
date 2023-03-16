package com.projet.morpion.controller;

import com.projet.morpion.modelMorpion.Morpion;
import com.projet.morpion.utilities.SceneManager;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class PlayController {
    @FXML
    private Label affichageHaut;
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
    private boolean finAvecGagnant = false;
    private static boolean isVSai = false;
    private static String model = "";
    private Morpion morpion = new Morpion();

    @FXML
    protected void initialize() {
        replay.setOnAction(actionEvent -> { // Bouton replay
            SceneManager.getInstance().changeScene("jeu.fxml");
        });

        affichageHaut.setFont(new Font("Arial", 20));
        if (isVSai)
            affichageHaut.setText("Player 1 VS AI");
        else
            affichageHaut.setText("Player 1 VS Player 2");
        affichageHaut.setVisible(true);


    }
    public static void setAi(boolean isVSai) {
        PlayController.isVSai = isVSai;
    }

    @FXML
    protected void jouePosition(Event event) {
        Button positionJoue = (Button) event.getSource();
        int idCaseJouee = Integer.parseInt(String.valueOf(positionJoue.getId().charAt(6)));
        System.out.println("id de la case jouée " + idCaseJouee);
        createImgPlay(); // Création de l'image du joueur qui joue
        if (isJoeurOne) {
            morpion.afterPlayerOneMove(idCaseJouee);
            isJoeurOne = false; // On change de joueur
        } else {
            morpion.afterPlayerTwoMove(idCaseJouee);
            isJoeurOne = true; // On change de joueur
        }
        positionJoue.setGraphic(img);
        positionJoue.setDisable(true);

        if (morpion.getNombreDeTour() >= 3) {
            if (morpion.isWin()) {
                finAvecGagnant = true;
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

            if (morpion.getNombreDeTour() >= 9 && !finAvecGagnant)
                affichageHaut.setText("Fin de la partie, match nul");
            else if (morpion.getIdWinner() == -1)
                affichageHaut.setText("Félicitation joueur 1");
            else if (morpion.getIdWinner() == 1)
                if (isVSai)
                    affichageHaut.setText("Félicitation AI");
                else
                    affichageHaut.setText("Félicitation joueur 2");
            else
                throw new IllegalStateException("Unexpected value: " + morpion.getIdWinner());
            affichageHaut.setVisible(true);
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
            img1 = new ImageView("file:./resources/images/TicTacToe/winCross2.png");
            img2 = new ImageView("file:./resources/images/TicTacToe/winCross2.png");
            img3 = new ImageView("file:./resources/images/TicTacToe/winCross2.png");
        } else if (result == 1) {
            img1 = new ImageView("file:./resources/images/TicTacToe/win_circle2.png");
            img2 = new ImageView("file:./resources/images/TicTacToe/win_circle2.png");
            img3 = new ImageView("file:./resources/images/TicTacToe/win_circle2.png");

        }
        img1.setFitWidth(30);
        img1.setFitHeight(30);
        img2.setFitWidth(30);
        img2.setFitHeight(30);
        img3.setFitWidth(30);
        img3.setFitHeight(30);
    }

    private void createImgPlay() {
        if(isJoeurOne)
            img = new ImageView("file:./resources/images/TicTacToe/cross2.png");
        else
            img = new ImageView("file:./resources/images/TicTacToe/circle2.png");
        img.setFitWidth(30);
        img.setFitHeight(30);
    }
}


