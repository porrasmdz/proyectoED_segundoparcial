/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tdas;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ASUS
 */
public class TrieTest {
    public static void main(String[] args) {
        TrieTree tree = new TrieTree();
        tree.insert("Hola");
        tree.insert("Messi");
        tree.insert("Barcelona");
        tree.insert("Lewandoski");
        tree.insert("Beso");
        tree.insert("Bebe");
        tree.insert("Bebesita");
        tree.insert("Bola");
        tree.insert("cola");
        System.out.println(tree.getTotalWords());
        tree.print();
        System.out.println(tree.getSuggestions("B"));
        
        tree.remove("Messi");
        System.out.println(tree.getTotalWords());
        System.out.println(tree.countWordsPerLetter());
        System.out.println(tree.countWordsByStartingLetter().toString());
        System.out.println(tree.countWordsByEndingLetter().toString());
        System.out.println(tree.createLengthHistogram());
        
        System.out.println(tree.getAllWords());
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        System.out.println(tree.searchBySuffix("ola"));
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        System.out.println(tree.getSimilarWords("Bola"));
        
        Iterator<String> it= tree.iterator();
        
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }
    
}
