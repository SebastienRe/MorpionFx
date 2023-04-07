package com.projet.morpion.controller;

import com.projet.morpion.utilities.SceneManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.robot.Robot;
import javafx.util.Duration;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SettingsController {
    @FXML
    private Button boutonSave;
    private int i = 0;
    private List<String []> listOfNewValues;

@FXML
public void initialize() throws IOException {
    File file = new File("./resources/config.txt");
    // Créer l'objet File Reader
    FileReader fr = new FileReader(file);
    // Créer l'objet BufferedReader
    BufferedReader br = new BufferedReader(fr);
    StringBuffer sb = new StringBuffer();
    String line;
    while ((line = br.readLine()) != null) {
        String [] configLu;
        configLu = line.split(":");
        remplirField(configLu, i);
        i = i + 3;
    }
       fr.close();

}
    @FXML
    private GridPane gridPane;

    @FXML
    protected void saveNewConfig() throws IOException {
        listOfNewValues = getNewConfig();
        FileWriter writer = new FileWriter("./resources/config.txt", false);
        StringBuilder sb = new StringBuilder();
        int cpt = 0;
        String [] tab = new String[3];
        Character niveau = 'F';
        while(cpt < listOfNewValues.size() - 1)
        {
            tab = listOfNewValues.get(cpt);
            sb.append(niveau).append(":").append(tab[0]).append(":")
                    .append(tab[1])
            .append(":").append(tab[2]).append(System.lineSeparator());
            cpt++;
            niveau = 'M';
        }
        niveau = 'D';
        tab = listOfNewValues.get(2);
        sb.append(niveau).append(":").append(tab[0]).append(":")
                .append(tab[1])
                .append(":").append(tab[2]).append(System.lineSeparator());
        writer.write(sb.toString());
        writer.close();

        if ( SceneManager.getInstance().displaySceneAndWait("The data was successfully backed up.", "ok" ) == "ok" ) {
            SceneManager.getInstance().changeScene("menu.fxml");
        }
    }
    public List<String []> getNewConfig() throws IOException {
        int gridLimit = 0;
        Node node = null;
        List<String []> tmpList = new ArrayList<>();

        while (gridLimit<9)
        {

            String [] tab = new String[3];
            node = gridPane.getChildren().get(gridLimit);
            tab[0] = ((TextField) node).getText();
            node = gridPane.getChildren().get(++gridLimit);
            tab[1] = ((TextField) node).getText();
            node = gridPane.getChildren().get(++gridLimit);
            tab[2] = ((TextField) node).getText();

            gridLimit++;
            tmpList.add(tab);
        }


        return tmpList;
    }


    private void remplirField(String[] configLu, int row) {
        int columnIndex = 1;
       int limitRow = row + 3;
      while(row < limitRow)
      {
          Node node = gridPane.getChildren().get(row);
          ((TextField) node).setText(configLu[columnIndex]);
          row++;
          columnIndex++;
      }

    }
    @FXML
    protected void controleField(Event e)
    {

        //disable le bouton save si le texte field est vide
        TextField field = (TextField) e.getSource();
        if(field.getText().isEmpty())
        {
            boutonSave.setDisable(true);
        }
        else
        {
            boutonSave.setDisable(false);
        }
    }
    @FXML
    protected void controleNumeric(Event e)
    {
        TextField field = (TextField) e.getSource();
        if(!field.getText().matches("[0-9.]*"))
        {
            field.setText(field.getText().replaceAll("[^\\d]", ""));
            field.positionCaret(field.getText().length());

        }
    }
    @FXML
    protected void retourMenu()
    {
        SceneManager.getInstance().changeScene("menu.fxml");
    }
}
