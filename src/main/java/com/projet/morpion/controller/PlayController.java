package com.projet.morpion.controller;

import com.projet.morpion.Launcher;
import com.projet.morpion.ai.layer.MultiLayerPerceptron;
import com.projet.morpion.models.Morpion;
import com.projet.morpion.utilities.SceneManager;
import javafx.animation.*;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;

public class PlayController {
    @FXML
    AnchorPane anchor;
    @FXML
    private Label affichageHaut;
    @FXML
    private Button help;
    private ImageView img1, img2, img3;
    private ImageView imageWin;
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
    Timeline timeline;
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

        if (isVSai) {

            affichageHaut.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
            //affichageHaut.setText("AI turn");
            affichageHaut.setVisible(true);
            affichageHaut.setText("");
            timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(affichageHaut.scaleXProperty(), 1.0)),
                    new KeyFrame(Duration.seconds(1), new KeyValue(affichageHaut.scaleXProperty(), 1.2)),
                    new KeyFrame(Duration.seconds(2), new KeyValue(affichageHaut.scaleXProperty(), 1.0))
            );
            timeline.setAutoReverse(true);
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
            enableORdisableAllButton(true);
            instanceOfAI = MultiLayerPerceptron.load(model);
            howStartChoicePannel.setVisible(true);
        }
        else {
            affichageHaut.setVisible(true);
            affichageHaut.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
            affichageHaut.setText("Player X turn");
            timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(affichageHaut.scaleXProperty(), 1.0)),
                    new KeyFrame(Duration.seconds(1), new KeyValue(affichageHaut.scaleXProperty(), 1.2)),
                    new KeyFrame(Duration.seconds(2), new KeyValue(affichageHaut.scaleXProperty(), 1.0))
            );
            timeline.setAutoReverse(true);
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }

    }

    @FXML
    protected void help() {
        SceneManager.getInstance().displaySceneAndWait("Le jeu de morpion est un jeu à deux joueurs.\nl'objectif est d'aligner trois symboles identiques, le joueur qui réussit à aligner\ntrois symboles gagne la partie.", "ok");
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
            if(isVSai)
                affichageHaut.setText("AI turn");
            else
                affichageHaut.setText("Player O turn");
            morpion.afterPlayerOneMove(idCaseJouee);
            isPlayerOneTurn = false; // On change de joueur
        } else {
            affichageHaut.setText("Player X turn");
            morpion.afterPlayerTwoMove(idCaseJouee);
            isPlayerOneTurn = true; // On change de joueur
        }

        if (morpion.getNombreDeTour() < 3) // Si le nombre de tour est inférieur à 3, on ne peut pas gagner
            return;

        boolean isWin = morpion.isWin();
        boolean isGrilleRemplis = morpion.isGrilleRempli();

        if (isWin) { // Si il y a un gagnant colore les cases gagnantes
            help.setVisible(false);
            enableORdisableAllButton(false);
            int[] positionGagnante = morpion.getPositionWinner();

            createImgWin();
            Button btn1 = (Button) matriceDuJeu.getChildren().get(positionGagnante[0]);
            btn1.setGraphic(img1);

            Button btn2 = (Button) matriceDuJeu.getChildren().get(positionGagnante[1]);
            btn2.setGraphic(img2);

            Button btn3 = (Button) matriceDuJeu.getChildren().get(positionGagnante[2]);
            btn3.setGraphic(img3);
            // Ajouter les boutons à un conteneur StackPane
            cacherAutreBouton(positionGagnante);
            StackPane stackPane = new StackPane(btn1, btn2, btn3);
            // Définir la position finale de la transition (le point de fusion)
            double xDestination = matriceDuJeu.getWidth() /2 - 50;
            double yDestination = matriceDuJeu.getHeight() /2  - 50;

            // Créer une transition d'animation pour chaque bouton
            TranslateTransition transition1 = createTransition(btn1, xDestination, yDestination);
            transition1.play();
            TranslateTransition transition2 = createTransition(btn2, xDestination, yDestination);
            transition2.play();
            TranslateTransition transition3 = createTransition(btn3, xDestination, yDestination);
            transition3.play();
            matriceDuJeu.add(stackPane, 0, 0);
            //afficher une image à la fin des transitions
            transition3.setOnFinished(e->{
                affichageHaut.setLayoutY(50);
                affichageHaut.setLayoutY(70);
                if(morpion.getIdWinner() == 1) {
                    if (isVSai) {
                        affichageHaut.setText("Winner");
                    } else {
                        affichageHaut.setText("Winner");
                    }
                }else if (morpion.getIdWinner() == -1)
                    affichageHaut.setText("Winner");
            });


        }

        if ( isGrilleRemplis || isWin) { // si partie fini
            System.out.println("fini");
            if (isGrilleRemplis && !isWin) {
                animationMatchNull();
                affichageHaut.setLayoutX(50);
                affichageHaut.setLayoutY(50);
                help.setVisible(false);
                affichageHaut.setText("end game, no winner");
            }else
            {
                affichageHaut.setText("");
            }
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
            imageWin = new ImageView("file:./resources/images/TicTacToe/winCross2.png");
        } else if (result == 1) {
            img1 = new ImageView("file:./resources/images/TicTacToe/win_circle2.png");
            img2 = new ImageView("file:./resources/images/TicTacToe/win_circle2.png");
            img3 = new ImageView("file:./resources/images/TicTacToe/win_circle2.png");
            imageWin = new ImageView("file:./resources/images/TicTacToe/win_circle2.png");

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
    private TranslateTransition createTransition(Button button, double xDestination, double yDestination) {
        // Créer une transition d'animation pour déplacer le bouton vers la position finale
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), button);
        transition.setFromX(button.getLayoutX());
        transition.setFromY(button.getLayoutY());
        transition.setToX(xDestination);
        transition.setToY(yDestination);

        return transition;
    }
    private void cacherAutreBouton(int [] position) {
        // Cacher les autres boutons
        //for matriceDuJeu.getChildren()
        for (Node node : matriceDuJeu.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                int id = Integer.parseInt(String.valueOf(button.getId().charAt(6)));
                if (id != position[0] && id != position[1] && id != position[2]) {
                    //animation pour disparaitre les boutton doucement
                    FadeTransition transition = new FadeTransition(Duration.seconds(1), button);
                    transition.setFromValue(1);
                    transition.setToValue(0);
                    transition.play();
                }
            }
        }
    }
   //animation si match nulle
    public void animationMatchNull()
    {
        double xDestination = matriceDuJeu.getWidth() /2 - 50;
        double yDestination = matriceDuJeu.getHeight() /2  - 50;
        StackPane stackPane = null;
        List<Button> listButton = new ArrayList<>();
        //ajouter tout les boutton au stack pane
        for (Node node : matriceDuJeu.getChildren()) {
                Button button = (Button) node;
                listButton.add(button);
        }
        stackPane = new StackPane(listButton.toArray(new Button[listButton.size()]));
        //animation tout les boutton faire le centre
        for (Node node : stackPane.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                ImageView img = (ImageView) button.getGraphic();
                String type = img.getImage().getUrl().substring(34);
                if(type.equals("cross2.png"))
                {
                    TranslateTransition transition = createTransition(button, xDestination-50, yDestination);
                    transition.play();
                }
                else
                {
                    TranslateTransition transition = createTransition(button, xDestination +60, yDestination);
                    transition.play();
                }
            }
        }
        //ajouter stack pane au matrice du jeu
        matriceDuJeu.getChildren().add(stackPane);
    }

}


