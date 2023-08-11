package com.mycompany.proyectoed_segundoparcial;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DictionaryViewController {

    @FXML
    private Button returnButton;

    @FXML
    private void switchToMainMenu(ActionEvent event) throws IOException {
         App.setRoot("mainmenu");
    }
}