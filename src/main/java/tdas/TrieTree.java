/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tdas;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author ASUS
 */
public class TrieTree {
    private TrieNode root;
    
    
    public TrieTree(){
        this.root = new TrieNode(' ');
    }
    
    
    public boolean insert(String word){
        if(this.search(word) == true){
            return false;
        }
        TrieNode node = this.root;
        for(char character : word.toCharArray()){
            if(node.getChild(character) == null){
                node.getChildren().add(new TrieNode(character));
            }node = node.getChild(character);
        }node.setIsWord(true);
        return true;
    }
    
    
    public String remove(String word){
         if (!search(word)) {
            return null;
        }
        TrieNode node = root;
        for (char character : word.toCharArray()) {
            TrieNode child = node.getChild(character);
            if (child == null) {
                return null;
            }
            node = child;
        }
        node.setIsWord(false);
        return word;
    }
    
    
    
    public boolean search(String word){
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if(node.getChild(c ) == null){
                return false;
            }node = node.getChild(c);
        }return node.isIsWord();
    }
    
    public void print() {
        List<String> res = new ArrayList<String>(); 
        helper(root, res, "" );
        System.out.println(res);
    }
    //get all words of trie tree
    public List<String> getAllWords(){
        List<String> res = new ArrayList<String>(); 
        helper(root, res, "" );
        return res;
    }
    public void helper(TrieNode node, List<String> res, String prefix) {		
        if (node.isIsWord() == true)  {
                String word = prefix + node.getCharacter();
                res.add(word.substring(1)); 
        }
        for (TrieNode child : node.getChildren()) 				
                helper(child, res, prefix + node.getCharacter());						
    }
    

    public TrieNode getRoot() {
        return root;
    }
    
    //suggestions and search by prefix
    private void collectSuggestions(TrieNode node, String prefix, List<String> suggestions) {
        if (node.isIsWord()) {
            suggestions.add(prefix);
        }
        for (TrieNode child : node.getChildren()) {
            collectSuggestions(child, prefix + child.getCharacter(), suggestions);
        }
    }
    
    public List<String> getSuggestions(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            TrieNode child = node.getChild(c);
            if (child == null) {
                return new ArrayList<>(); // Retorna una lista vacía si no se encuentra el prefijo
            }
            node = child;
        }
        
        List<String> suggestions = new ArrayList<>();
        collectSuggestions(node, prefix, suggestions);
        return suggestions;
    }
    
    
    //STATISTICS METHODS
    //total words
    private int countWords(TrieNode node) {
        int count = 0;
        if (node.isIsWord()) {
            count++;
        }
        for (TrieNode child : node.getChildren()) {
            count += countWords(child);
        }
        return count;
    }
    
    public int getTotalWords() {
        return countWords(root);
    }
    
    //total words for each character
    public Map<Character, Integer> countWordsPerLetter() {
        Map<Character, Integer> letterCountMap = new HashMap<>();
        countWordsPerLetterHelper(root, letterCountMap);
        return letterCountMap;
    }

    private void countWordsPerLetterHelper(TrieNode node, Map<Character, Integer> letterCountMap) {
        if (node.isIsWord()) {
            char character = node.getCharacter();
            letterCountMap.put(character, letterCountMap.getOrDefault(character, 0) + 1);
        }

        for (TrieNode child : node.getChildren()) {
            countWordsPerLetterHelper(child, letterCountMap);
        }
    }
    
    //total words by starting letter
    public Map<Character, Integer> countWordsByStartingLetter() {
        Map<Character, Integer> resultMap = new HashMap<>();

        for (TrieNode child : root.getChildren()) {
            char letter = child.getCharacter();
            int count = countWordsPerLetterHelper(child);
            if (count > 0) {
                resultMap.put(letter, count);
            }
        }

        return resultMap;
    }

    private int countWordsPerLetterHelper(TrieNode node) {
        int count = node.isIsWord() ? 1 : 0;

        for (TrieNode child : node.getChildren()) {
            count += countWordsPerLetterHelper(child);
        }

        return count;
    }
    
    //total words by ending character
    public Map<Character, Integer> countWordsByEndingLetter() {
        Map<Character, Integer> resultMap = new HashMap<>();

        countWordsByEndingLetterHelper(root, resultMap, "");

        return resultMap;
    }

    private void countWordsByEndingLetterHelper(TrieNode node, Map<Character, Integer> resultMap, String suffix) {
        if (node.isIsWord()) {
            char lastChar = suffix.charAt(suffix.length() - 1);
            resultMap.put(lastChar, resultMap.getOrDefault(lastChar, 0) + 1);
        }

        for (TrieNode child : node.getChildren()) {
            countWordsByEndingLetterHelper(child, resultMap, suffix + child.getCharacter());
        }
    }
    
    //length histograma
    public Map<Integer, Integer> createLengthHistogram() {
        Map<Integer, Integer> lengthHistogram = new HashMap<>();
        createLengthHistogramHelper(root, lengthHistogram, 0);

        return lengthHistogram;
    }

    private void createLengthHistogramHelper(TrieNode node, Map<Integer, Integer> lengthHistogram, int length) {
        if (node.isIsWord()) {
            lengthHistogram.put(length, lengthHistogram.getOrDefault(length, 0) + 1);
        }

        for (TrieNode child : node.getChildren()) {
            createLengthHistogramHelper(child, lengthHistogram, length + 1);
        }
    }
    
    //Average word lengths
    public double calculateAverageWordLength() {
        Map<Integer, Integer> lengthHistogram = createLengthHistogram();
        int totalWords = getTotalWords();

        if (totalWords == 0) {
            return 0.0;
        }

        int totalLength = 0;
        for (Map.Entry<Integer, Integer> entry : lengthHistogram.entrySet()) {
            totalLength += entry.getKey() * entry.getValue();
        }

        return (double) totalLength / totalWords;
    }
    
    
    //searchBySuffix
    public List<String> searchBySuffix(String suffix) {
        List <String> allWords = this.getAllWords();
        List <String> suffixWords = new LinkedList<>();
        for (String str : allWords) {
            if (str.endsWith(suffix)) {
                suffixWords.add(str);
            }
        }
        return suffixWords;
    }

   //similarWords
    private int getLevenshteinDistance(String string1, String string2)
    {
        int m = string1.length();
        int n = string2.length();
 
        int[][] T = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            T[i][0] = i;
        }
        for (int j = 1; j <= n; j++) {
            T[0][j] = j;
        }
 
        int cost;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                cost = string1.charAt(i - 1) == string2.charAt(j - 1) ? 0: 1;
                T[i][j] = Integer.min(Integer.min(T[i - 1][j] + 1, T[i][j - 1] + 1),
                        T[i - 1][j - 1] + cost);
            }
        }
 
        return T[m][n];
    }
 
    private double findSimilarity(String string1, String string2) {
        if (string1 == null || string2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        
        double maxLength = Double.max(string1.length(), string2.length());
        if (maxLength > 0) {
            return (maxLength - getLevenshteinDistance(string1, string2)) / maxLength;
        }
        return 1.0;
    }
    
    public List<String> getSimilarWords(String word){
        List<String> similarWords = new LinkedList<>();
        List <String> allWords = this.getAllWords();
        for(String w: allWords){
            if(findSimilarity(w,word)>0.5){
                similarWords.add(w);
            }
        }
        similarWords.remove(word);
        return similarWords;
    }
    






    
}
