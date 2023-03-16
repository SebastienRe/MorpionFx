package com.projet.morpion.controller;

import com.projet.morpion.ai.layer.MultiLayerPerceptron;
import com.projet.morpion.models.Morpion;
import com.projet.morpion.models.SceneManager;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Thread.sleep;

public class PlayController {
    @FXML
    private Label affichageHaut;
    private ImageView img1, img2, img3;
    @FXML
    private GridPane matriceDuJeu;
    private ArrayList<Node> listButton = new ArrayList<>();
    private boolean isPlayerOneTurn = true;
    @FXML
    private Button replay;
    private static boolean isVSai = false;

    //AI
    private static String model = "";
    private Morpion morpion = new Morpion();
    private MultiLayerPerceptron instanceOfAI = null;
    @FXML
    VBox howStartChoicePannel;
    @FXML
    protected void playerStartTheGame() {
        howStartChoicePannel.setVisible(false);
        enableORdisableAllButton(false);
    }
    @FXML
    protected void AIStartTheGame() {
        howStartChoicePannel.setVisible(false);
        isPlayerOneTurn = false;
        AIplay();
    }

    @FXML
    protected void initialize() {
        replay.setOnAction(actionEvent -> { // Bouton replay
            SceneManager.getInstance().changeScene("jeu.fxml");
        });

        affichageHaut.setFont(new Font("Arial", 20));
        if (isVSai) {
            affichageHaut.setText("Player 1 VS AI");
            enableORdisableAllButton(true);
            instanceOfAI = MultiLayerPerceptron.load(model);
            howStartChoicePannel.setVisible(true);
        }
        else
            affichageHaut.setText("Player 1 VS Player 2");
        affichageHaut.setVisible(true);
    }

    public static void setIsAi(boolean isVSai) {
        PlayController.isVSai = isVSai;
    }

    public static void setModel(String modelFile) {
        model = modelFile;
    }

    private void play(int idCaseJouee){
        System.out.println("id de la case jouée " + idCaseJouee);
        Button positionJoue = (Button) matriceDuJeu.getChildren().get(idCaseJouee);
        disableButton(positionJoue);
        ImageView imgPlay = createImgPlay();
        positionJoue.setGraphic(imgPlay); // création de l'image et ajout dans le bouton
        if (isPlayerOneTurn) {
            morpion.afterPlayerOneMove(idCaseJouee);
            isPlayerOneTurn = false; // On change de joueur
        } else {
            morpion.afterPlayerTwoMove(idCaseJouee);
            isPlayerOneTurn = true; // On change de joueur
        }

        if (morpion.getNombreDeTour() < 3) // Si le nombre de tour est inférieur à 3, on ne peut pas gagner
            return;

        boolean isWin = morpion.isWin();
        boolean isGrilleRemplis = morpion.isGrilleRempli();

        if (isWin) { // Si il y a un gagnant colore les cases gagnantes
            enableORdisableAllButton(false);
            int[] positionGagnante = morpion.getPositionWinner();

            createImgWin();
            Button btn1 = (Button) matriceDuJeu.getChildren().get(positionGagnante[0]);
            btn1.setGraphic(img1);

            Button btn2 = (Button) matriceDuJeu.getChildren().get(positionGagnante[1]);
            btn2.setGraphic(img2);

            Button btn3 = (Button) matriceDuJeu.getChildren().get(positionGagnante[2]);
            btn3.setGraphic(img3);
        }

        if ( isGrilleRemplis || isWin) { // si partie fini
            System.out.println("fini");
            if (isGrilleRemplis && !isWin)
                affichageHaut.setText("end game, no winner");
            else if (morpion.getIdWinner() == -1)
                affichageHaut.setText("Player 1 win");
            else if (morpion.getIdWinner() == 1)
                if (isVSai)
                    affichageHaut.setText("AI win");
                else
                    affichageHaut.setText("Player 2 win");
            else
                throw new IllegalStateException("Unexpected value: " + morpion.getIdWinner());
            affichageHaut.setVisible(true);
            replay.setVisible(true);
            enableORdisableAllButton(true);
        }
    }

    private void disableButton(Button positionJoue) {
        positionJoue.setDisable(true);
        listButton.remove(positionJoue);
    }

    @FXML
    protected void humanPlay(Event event) {
        // joueurs humain
        Button positionJoue = (Button) event.getSource();
        int idCaseJouee = Integer.parseInt(String.valueOf(positionJoue.getId().charAt(6)));
        play(idCaseJouee);
        if (isVSai && !morpion.isWin() && !morpion.isGrilleRempli())
            AIplay();
    }
    private void AIplay() {
        enableORdisableAllButton(true);
        Task task = new Task() {
            @Override
            protected Object call() throws Exception{
                try {//ne fait rien de 1 à 2 secondes pour simuler le temps de réflexion de l'IA
                    sleep((long) (Math.random() * 1000 + 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        task.setOnSucceeded(e -> {
            double[] res = instanceOfAI.forwardPropagation(morpion.getMatriceDuJeu());
            System.out.println("table de jeu: "+ Arrays.toString(morpion.getMatriceDuJeu()) + "");
            System.out.println("Dev predicted: "+ Arrays.toString(res));


            while (true) {

                int i = 0;
                for (int j = 0; j < res.length; j++) {
                    System.out.println("res[" + j + "] = " + res[j]);
                    if (res[j] > res[i])
                        i = j;
                }

                System.out.println("AI play " + i);
                if (morpion.getMatriceDuJeu()[i] == 0){
                    play(i);
                    break;
                }
                res[i] = -1;
            }
            enableORdisableAllButton(false);
        });

    }

    private void enableORdisableAllButton(boolean isDisable) {
        if (isDisable)
            for (Node node : matriceDuJeu.getChildren()) {
                if (!node.isDisable())
                    listButton.add(node);
                node.setDisable(true);
            }
        else {
            for (Node node : listButton)
                node.setDisable(false);
            listButton.clear();
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

    private ImageView createImgPlay() {
        ImageView img;
        if(isPlayerOneTurn)
            img = new ImageView("file:./resources/images/TicTacToe/cross2.png");
        else
            img = new ImageView("file:./resources/images/TicTacToe/circle2.png");
        img.setFitWidth(30);
        img.setFitHeight(30);
        return img;
    }
}


