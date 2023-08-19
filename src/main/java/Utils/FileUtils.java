package Utils;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.control.Alert;
import tdas.TrieTree;

public class FileUtils {
    
    public static boolean saveTrie(TrieTree wordTree) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tries.ser"))) {
            oos.writeObject(wordTree);
            System.out.println("Trie saved successfully.");
            return true;
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Resultado de la operación");
            alert.setContentText("Error al guardar diccionario  \n Intente nuevamente");
            alert.showAndWait();
            return false;
        }
    }
    
   
    
    public static List<String> loadWords() {
        LinkedList<String> words = new LinkedList<>();
        try ( BufferedReader br = new BufferedReader(new FileReader("src/main/resources/files/words.txt"))) {
            String currentLine;
             while((currentLine = br.readLine()) != null){
                words.add(currentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }
    
    public static boolean saveWords(TrieTree wordTree) throws IOException {
        List<String> words = loadWords();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tries.ser"))) {
            oos.writeObject(wordTree);
            System.out.println("Trie saved successfully.");
            return true;
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Resultado de la operación");
            alert.setContentText("Error al guardar diccionario  \n Intente nuevamente");
            alert.showAndWait();
            return false;
        }
    }
    

    public static TrieTree loadTrie() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("files/tries.ser"))) {
            TrieTree loadedTrie = (TrieTree) ois.readObject();
            System.out.println("Trie loaded successfully.");
            return loadedTrie;
        } catch (IOException | ClassNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Resultado de la operación");
            alert.setContentText("Error al cargar diccionario \n Intente nuevamente");
            alert.showAndWait();
            
            return null;
        }
    }
}
