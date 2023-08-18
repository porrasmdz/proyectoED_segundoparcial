/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tdas;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
    
    
    public TrieNode remove(String word){
        if(this.search(word) == false){
            return null;
        }
        TrieNode node = this.root;
        for(char character : word.toCharArray()){
            if(node.getChild(character) == null){
                return null;
            }
            node = node.getChild(character);
        }node.setIsWord(false);
        return node;
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
    
    
}