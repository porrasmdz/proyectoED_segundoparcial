/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tdas;

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
        System.out.println(tree.remove("Hola"));
        System.out.println(tree.search("Messi"));
        tree.print();
        System.out.println(tree.getSuggestions("B"));
        
        tree.remove("Messi");
    }
}