package Utils;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import tdas.TrieTree;

public class FileUtils1 {
    public static boolean saveTrie(TrieTree wordTree) {
        List<String> words = new ArrayList<>();
        wordTree.helper(wordTree.getRoot(), words,"");

        try (PrintWriter writer = new PrintWriter(new FileWriter("diccionario.txt"))) {
            for (String word : words) {
                writer.println(word);
            }
            System.out.println("Trie saved successfully.");
            return true;
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Resultado de la operación");
            alert.setContentText("Error al cargar diccionario \n Intente nuevamente");
            alert.showAndWait();
            return false;
        }
    }

    public static TrieTree loadTrie() {
        TrieTree loadedTrie = new TrieTree();

        try (BufferedReader reader = new BufferedReader(new FileReader("diccionario.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                loadedTrie.insert(line);
            }
            System.out.println("Trie loaded successfully.");
        } catch (IOException e) {
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Resultado de la operación");
            alert.setContentText("Error al cargar diccionario \n Intente nuevamente");
            alert.showAndWait();
            return null;
        }

        return loadedTrie;
    }
//
//    private static void collectWords(TrieNode node, String prefix, List<String> words) {
//        if (node.isEndOfWord()) {
//            words.add(prefix);
//        }
//
//        for (char c = 'a'; c <= 'z'; c++) {
//            TrieNode child = node.getChild(c);
//            if (child != null) {
//                collectWords(child, prefix + c, words);
//            }
//        }
//    }
}


