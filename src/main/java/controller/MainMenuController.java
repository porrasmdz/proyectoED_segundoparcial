package controller;

import Utils.FileUtils;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import tdas.TrieTree;

public class MainMenuController {
    
    private TrieTree trie;
    private List<String> words;

    @FXML
    private ListView<String> listView1;
    @FXML
    private ListView<String> listView2;
    @FXML
    private ListView<String> listView3;
    @FXML
    private ListView<String> listView4;

    public void initialize() throws IOException {
        TrieTree trie = new TrieTree();
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
}
