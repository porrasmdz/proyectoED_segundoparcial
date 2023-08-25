/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import Utils.CharCount;
import Utils.FileUtils;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import tdas.TrieTree;

/**
 * FXML Controller class
 *
 * @author ANDRES PORRAS
 */

public class StatspanelController implements Initializable {

    private TrieTree trie;
    private List<String> words;
    private int totalChars;
    Map<Character, Integer> mc;
    Map<Integer, Integer> lengthFrequency;
    
    @FXML
    private Text totalWordsText;
    @FXML
    private Text totalCharsText;
    @FXML
    private Text treeHeightText;
    @FXML
    private StackedBarChart<String, Integer> stackedBarChart;
    @FXML
    private TableColumn<String, String> charColumn;
    @FXML
    private TableColumn<String, String> countColumn;
    @FXML
    private TableView<CharCount> tableView;
    @FXML
    private Text averageWordSize;
    @FXML
    private NumberAxis totalAxis;
    @FXML
    private CategoryAxis categoryAxis;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        trie = new TrieTree();
        words = FileUtils.loadWords();
        mc = new HashMap<>();
        totalChars = 0;
         for(String w:words){
            trie.insert(w);
            totalChars += w.length();
        }
        initializeTableData();
        
        initializeCardsData();
        
        initializeHistogram();
    }    
    
    private void initializeTableData() {
       
        mc = trie.countWordsByStartingLetter();
        
        List<CharCount> charCount = new ArrayList<>();
        for (Entry<Character,Integer> entry : mc.entrySet()) {
            int count = 1;
            charCount.add(new CharCount(entry.getKey().toString(), entry.getValue()));
        }
        
        ObservableList<CharCount> charCounts = FXCollections.observableList(charCount);
        
        charColumn.setCellValueFactory(new PropertyValueFactory<>("character") );
        countColumn.setCellValueFactory(new PropertyValueFactory<>("count") );
        tableView.setItems(charCounts);
    }
   
    private void initializeHistogram(){
        lengthFrequency = trie.createLengthHistogram();
        
       
        List<String> lengths = new ArrayList<>();
        List<Integer> totals = new ArrayList<>();
        XYChart.Series<String,Integer> series = new XYChart.Series<>();
       
        for (Entry<Integer,Integer> entry : lengthFrequency.entrySet()) {
            String key = Integer.toString(entry.getKey());
            lengths.add(key);
            totals.add(entry.getValue());
            series.getData().add(new XYChart.Data<>(key, entry.getValue()));
            
        }
        
        
        categoryAxis.setCategories(FXCollections.<String>observableList(lengths));
        categoryAxis.setLabel("Lengths");
       
         
        totalAxis.setLabel("Absolute Frequency");
        series.setName("Frequencys");
        
        stackedBarChart.getData().addAll(series);
        
        
        System.out.println(lengthFrequency);
    }
    
    private void initializeCardsData() {
        totalWordsText.setText(Integer.toString(trie.getTotalWords()));
        totalCharsText.setText(Integer.toString(totalChars));
        
        
        treeHeightText.setText(Integer.toString(mc.entrySet().size()));
        
        NumberFormat df = DecimalFormat.getInstance();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        averageWordSize.setText(df.format(trie.calculateAverageWordLength()));
    }
    @FXML
    private void goBack(ActionEvent event) throws IOException {
        App.setRoot("main");
    }

   
    
}
