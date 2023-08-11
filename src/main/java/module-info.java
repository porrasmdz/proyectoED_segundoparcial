module com.mycompany.proyectoed_segundoparcial {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.proyectoed_segundoparcial to javafx.fxml;
    exports com.mycompany.proyectoed_segundoparcial;
}
