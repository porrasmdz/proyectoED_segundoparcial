/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.proyectoed_segundoparcial;

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
 * @author ANDRES PORRAS
 */
public class PrefixSearchViewController implements Initializable {


    @FXML
    private Button searchButton;
    @FXML
    private Button returnButton1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void searchWordsWIthPrefix(ActionEvent event) {
    }

    @FXML
    private void switchToMainMenu(ActionEvent event)  throws IOException {
        App.setRoot("mainmenu");
    }

}
