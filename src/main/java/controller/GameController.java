
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;


import Utils.CharCount;
import Utils.FileUtils;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import tdas.TrieTree;

/**
 * FXML Controller class
 *
HEAD
 * @author ASUS
 */


public class GameController implements Initializable {
    private TrieTree trie;
    private List<String> words;

    @FXML
    private GridPane gridP;
    @FXML
    private GridPane word;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        trie = new TrieTree();
        words = FileUtils.loadWords();
        for(String w:words){
            trie.insert(w);
        }
        List<Character> listLetters = trie.getListCharacters();        
        Set<Character> letters = trie.getLettersForGame(pickRandomCharacter(listLetters));
        int columnIndex = 0;
        int rowIndex = 0;

        for (char letter : letters) {
            Button button = new Button(String.valueOf(letter));
            button.setOnAction(event -> handleLetterButtonClick(letter)); // Agregar acción al botón si es necesario

            // Cambiar el texto del botón a mayúsculas
            button.setText(button.getText().toUpperCase());

            gridP.add(button, columnIndex, rowIndex);

            columnIndex++;
            if (columnIndex >= 3) {
                columnIndex = 0;
                rowIndex++;
            }
            if (rowIndex >=3){
                break;
            }
        }
    }   
    
    private void handleLetterButtonClick(char letter) {
//        selectedLetters.add(letter);
//        updateLetterGrid();
//        String word = "";
//        word.concat();
    }
    private void updateLetterGrid() {
        // Limpiar el GridPane antes de actualizarlo
        word.getChildren().clear();

        int rowIndex = 0;
//        for (char letter : selectedLetters) {
//            Label letterLabel = new Label(String.valueOf(letter));
//            word.add(letterLabel, 0, rowIndex);
//            rowIndex++;
//        }
    }
    
    public static char pickRandomCharacter(List<Character> characterList) {
        if (characterList == null || characterList.isEmpty()) {
            throw new IllegalArgumentException("La lista de caracteres está vacía o es nula.");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(characterList.size());
        return characterList.get(randomIndex);
    }
    
    @FXML
    private void goBack(ActionEvent event) throws IOException {
        App.setRoot("main");
    }



}
