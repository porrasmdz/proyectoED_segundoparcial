package Utils;

import java.io.*;
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

    public static TrieTree loadTrie() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("tries.ser"))) {
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
