/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tdas;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class TrieNode {
    private List<TrieNode> children;
    private boolean isWord;
    private char character;
    
    public TrieNode(char character){
        this.character = character;
        this.children = new LinkedList<>();
    }
    public TrieNode getChild(char character){
        if(children != null){
            for (int i = 0; i < children.size(); i++) {
                TrieNode node = children.get(i);
                if(node.getCharacter() == character){
                    return node;
                }
                
            }
        }return null;
    }
    
    public void setCharacter(char character) {
        this.character = character;
    }

    public int getChildrenSize(){
        return children.size();
    }
    
    public char getCharacter() {
        return character;
    }
    
    public List<TrieNode> getChildren() {
        return children;
    }

    public void setChildren(List<TrieNode> children) {
        this.children = children;
    }

    public boolean isIsWord() {
        return isWord;
    }

    public void setIsWord(boolean isWord) {
        this.isWord = isWord;
    }
    
    
    
}
