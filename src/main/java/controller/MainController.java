/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class MainController implements Initializable {

    @FXML
    private Button statsButton;
    @FXML
    private Button exitButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }    

    @FXML
    private void goSearch(ActionEvent event) {
          try {
            App.setRoot("mainmenu");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void goStats(ActionEvent event) {
         try {
            App.setRoot("statspanel");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }
    
}
