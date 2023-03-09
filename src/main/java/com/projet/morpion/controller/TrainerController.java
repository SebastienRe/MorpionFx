package com.projet.morpion.controller;

import com.projet.morpion.ai.config.Config;
import com.projet.morpion.ai.config.ConfigFileLoader;
import com.projet.morpion.ai.coup.Coup;
import com.projet.morpion.ai.layer.MultiLayerPerceptron;
import com.projet.morpion.ai.transfert.SigmoidalTransferFunction;
import com.projet.morpion.utilities.SceneManager;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

import java.util.HashMap;

import static com.projet.morpion.ai.Test.*;

public class TrainerController {

    @FXML
    private TextField field;
    @FXML
    private ProgressBar progress;

    @FXML
    protected void onClick() {

        // LOAD DATA ...
        //
        HashMap<Integer, Coup> coups = loadGames("./resources/dataset/Tic_tac_initial_results.csv");
        saveGames(coups, "./resources/train_dev_test/", 0.7);
        //
        // LOAD CONFIG ...
        //
        ConfigFileLoader cfl = new ConfigFileLoader();
        cfl.loadConfigFile("./resources/config.txt");
        Config config = cfl.get("F");
        System.out.println("Test.main() : "+config);
        //
        //
        //TRAIN THE MODEL ...
        //
        double epochs = 10000 ;
        HashMap<Integer, Coup> mapTrain = loadCoupsFromFile("./resources/train_dev_test/train.txt");
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                System.out.println();
                System.out.println("START TRAINING ...");
                System.out.println();
                int[] layers = new int[ (config.numberOfhiddenLayers+2)];
                layers[0] = 9 ;
                for (int i = 0; i < config.numberOfhiddenLayers; i++) {
                    layers[i+1] = config.hiddenLayerSize ;
                }
                layers[layers.length-1] = 9 ;
                //
                double error = 0.0 ;
                MultiLayerPerceptron net = new MultiLayerPerceptron(layers, config.learningRate, new SigmoidalTransferFunction());
                System.out.println("---");
                System.out.println("Load data ...");
                System.out.println("---");
                //TRAINING ...
                for(int i = 0; i < epochs; i++){

                    Coup c = null ;
                    while ( c == null )
                        c = mapTrain.get((int)(Math.round(Math.random() * mapTrain.size())));

                    error += net.backPropagate(c.in, c.out);

                    if ( i % 100 == 0 ) {
                        updateMessage("Error at step " + i + " is " + (error / (double) i));
                        updateProgress(i, epochs);
                    }
                }
                    updateMessage("Learning completed!");
                    updateProgress(100, 100);

                    // save to model
                String  nameModel = "model_"+ config.numberOfhiddenLayers
                        +"_"+ config.hiddenLayerSize + "_"
                        + config.learningRate + ".srl";
                net.save("./resources/models/" + nameModel);
                return null;
            }
        };
        task.messageProperty().addListener((observableValue, s, t1) -> {
            field.setText(t1);
        });
        progress.progressProperty().bind(task.progressProperty());
        Thread thread = new Thread(task);
        thread.start();
        


    }

}