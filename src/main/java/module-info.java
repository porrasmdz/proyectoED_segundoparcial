module com.mycompany.proyectoed_segundoparcial {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens controller to javafx.fxml;
    exports controller;
}
