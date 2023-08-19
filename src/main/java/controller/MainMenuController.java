package controller;

import Utils.FileUtils;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tdas.TrieTree;

public class MainMenuController {
    
    private TrieTree trie;
    private List<String> words;

    @FXML
    private ListView<String> listView1;
    @FXML
    private Button btnSearch;
    @FXML
    private TextField txtSearch;
    @FXML
    private TextField txtInsert;
    @FXML
    private Button btnInsert;
    @FXML
    private TextField txtRemove;
    @FXML
    private TextField txtSearchByPrefix;
    @FXML
    private Button btnSearchPreffix;
    @FXML
    private TextField txtSearchBySuffix;
    @FXML
    private Button btnSearchSuffix;
    @FXML
    private TextField txtReverSearch;
    @FXML
    private Button btnReverseSearch;
    @FXML
    private TextField txtSimilarWords;
    @FXML
    private ListView<String> listViewResults;

    public void initialize() throws IOException {
        trie = new TrieTree();
        words = FileUtils.loadWords();
        for(String w:words){
            trie.insert(w);
        }
        listView1.getItems().setAll(words);
        
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
            listView1.getSelectionModel().select(word);
            listView1.scrollTo(words.indexOf(word.toLowerCase()));
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("DICTIONARY");
            alert.setTitle("ERROR");
            alert.setContentText("The word doesn't exists");
            alert.showAndWait();
        }
        
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
    private void searchBySuffix(ActionEvent event) {
    }

    @FXML
    private void reverseSearch(ActionEvent event) {
    }

    @FXML
    private void similarWords(ActionEvent event) {
    }
}
