module com.example.morpionfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.morpionfx to javafx.fxml;
    exports com.example.morpionfx;
}