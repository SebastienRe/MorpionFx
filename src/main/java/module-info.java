module com.projet.morpion {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.projet.morpion to javafx.fxml;
    exports com.projet.morpion;
    exports com.projet.morpion.controller;
    opens com.projet.morpion.controller to javafx.fxml;
}