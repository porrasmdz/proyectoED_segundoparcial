package com.mycompany.edproy2;

import Utils.FileUtils;
import static Utils.FileUtils1.loadTrie;
import static Utils.FileUtils1.saveTrie;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import tdas.TrieTree;

public class MainMenuController {
    
    private TrieTree trie;
    private List<String> words;

    @FXML
    private ListView<String> listView1;
    @FXML
    private TextField txtSearch;
    @FXML
    private TextField txtInsert;
    @FXML
    private TextField txtRemove;
    @FXML
    private TextField txtSearchByPrefix;
    @FXML
    private TextField txtReverSearch;
    @FXML
    private TextField txtSimilarWords;
    @FXML
    private ListView<String> listViewResults;
    @FXML
    private Label lblResults;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnSearchPreffix;
    @FXML
    private Button btnReverseSearch;
    @FXML
    private Button btnInsert;
    @FXML
    private Label wrdNormalResult;
    @FXML
    private Label wrdPrefixResult;
    @FXML
    private Label wrdInverseResult;
    @FXML
    private Label wrdApproxResult;

    public void initialize() throws IOException {
        trie = new TrieTree();
        words = FileUtils.loadWords();
        for(String w:words){
            trie.insert(w);
        }
        listView1.getItems().setAll(words);
        txtSearchByPrefix.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                suggestions(newValue.toLowerCase());
            }
        });
        resetResults();
        
    }   
    private void refreshlistview1(){
        words = FileUtils.loadWords();
        for(String w:words){
            trie.insert(w);
        }
        listView1.getItems().setAll(words);
        
    }

    private void switchToWordSearch() throws IOException {
        App.setRoot("wordsearch");
    }

    private void switchToPrefixSearch(ActionEvent event)  throws IOException {
        App.setRoot("prefixsearch");
    }

    private void switchToDictView(ActionEvent event) throws IOException {
        App.setRoot("dictionaryview");
    }

    @FXML
    private void search(ActionEvent event) {
        String word = txtSearch.getText();
        if(words.contains(word.toLowerCase())){
            txtSearch.clear();
            resetResults();
            listView1.getSelectionModel().select(word);
            listView1.scrollTo(words.indexOf(word.toLowerCase()));
            wrdNormalResult.setText(word.toLowerCase());
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("DICTIONARY");
            alert.setTitle("ERROR");
            alert.setContentText("The word doesn't exists");
            alert.showAndWait();
        }
        
    }

    private void resetResults(){
        wrdNormalResult.setText("");
        wrdPrefixResult.setText("");
        wrdInverseResult.setText("");
        wrdApproxResult.setText("");
    }    
    
    @FXML
    private void insert(ActionEvent event) {
        String word = txtInsert.getText();
        if(words.contains(word.toLowerCase())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("DICTIONARY");
            alert.setTitle("ERROR");
            alert.setContentText("The word already exists");
            alert.showAndWait();
        }else{
            if(!word.isEmpty()) {
                trie.insert(word.toLowerCase());
                try{
                    FileWriter fw = new FileWriter("src/main/resources/files/words.txt",true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.newLine();
                    bw.write(word);
                    bw.close();
                    fw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }   
            }
        }refreshlistview1();
        txtInsert.clear();
        listView1.getSelectionModel().select(word);
        listView1.scrollTo(words.size()-1);
    }

    @FXML
    private void remove(ActionEvent event) {
        String word = txtRemove.getText();
        if(!words.contains(word.toLowerCase())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("DICTIONARY");
            alert.setTitle("ERROR");
            alert.setContentText("The word doesn't exists");
            alert.showAndWait();
        }else{
            if(!word.isEmpty()) {
                trie.remove(word.toLowerCase());
                words.remove(word.toLowerCase());
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/files/words.txt"))) {
                    for (int i = 0; i < words.size(); i++) {
                        writer.write(words.get(i));
                        if (i < words.size() - 1) {
                            writer.newLine(); // Agregar nueva línea solo si no es la última línea
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("DICTIONARY");
                alert.setTitle("INFORMATION");
                alert.setContentText("'"+word.toLowerCase()+"'"+" has been removed from the dictionary");
                alert.showAndWait();
            }
        }refreshlistview1();
        txtRemove.clear();
        listView1.getSelectionModel().select(word);
        listView1.scrollTo(words.size()-1);
        
    }

    @FXML
    private void searchByPreffix(ActionEvent event) {
        
    }


    @FXML
    private void reverseSearch(ActionEvent event) {
        lblResults.setText("Reverse Search");
        String word = txtReverSearch.getText().toLowerCase();
        List<String> wordListReverse = trie.searchBySuffix(word);
        if(wordListReverse.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("DICTIONARY");
                alert.setTitle("INFORMATION");
                alert.setContentText("No results for "+"'"+word+"'");
                alert.showAndWait();
        }else{
            resetResults();
            listViewResults.getItems().setAll(wordListReverse);
            wrdInverseResult.setText(listViewResults.getItems().get(0));
        }
        txtReverSearch.clear();
        
    }

    @FXML
    private void similarWords(ActionEvent event) {
        lblResults.setText("Similar Words");
        String word = txtSimilarWords.getText().toLowerCase();
        List<String> similarWords = trie.getSimilarWords(word);
        if(similarWords.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("DICTIONARY");
                alert.setTitle("INFORMATION");
                alert.setContentText("No similar words results for "+"'"+word+"'");
                alert.showAndWait();
        }else{
            resetResults();
            listViewResults.getItems().setAll(similarWords);
            wrdApproxResult.setText(listViewResults.getItems().get(0));
        }
        txtSimilarWords.clear();
    }

    @FXML
    private void suggestions(KeyEvent event) {
        lblResults.setText("Suggestions");
        String word = txtSearchByPrefix.getText().toLowerCase();
        System.out.println(word);
        resetResults();
        refreshTableViewResults(word);
        
    }
    
    private void suggestions(String word) {
        System.out.println(word);
        List<String> suggestions = trie.getSuggestions(word);
        System.out.println(suggestions);
        
        listViewResults.getItems().setAll(suggestions);
        
    }
    
    private void refreshTableViewResults(String word){
        List<String> suggestions = trie.getSuggestions(word);
        listViewResults.getItems().setAll(suggestions);
        if(!suggestions.isEmpty())
        {
            wrdPrefixResult.setText(suggestions.get(0));
        }
    }

    @FXML
    private void goBack(ActionEvent event) {
        try {
            App.setRoot("main");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void downloadDict(ActionEvent event) {
        saveTrie(trie,App.getRootWindow());
    }

    @FXML
    private void deleteDict(ActionEvent event) {

    }

    @FXML
    private void uploadDict(ActionEvent event) throws IOException {
        loadTrie(App.getRootWindow());
        App.setRoot("mainmenu");
    }
}
