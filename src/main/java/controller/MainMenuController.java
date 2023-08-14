package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainMenuController {

    @FXML
    private Button wordSearchButton;
    @FXML
    private Button prefixSearchButton;
    @FXML
    private Button dictCreateButton;

   

    @FXML
    private void switchToWordSearch() throws IOException {
        App.setRoot("wordsearch");
    }

    @FXML
    private void switchToPrefixSearch(ActionEvent event)  throws IOException {
        App.setRoot("prefixsearch");
    }

    @FXML
    private void switchToDictView(ActionEvent event) throws IOException {
        App.setRoot("dictionaryview");
    }
}
