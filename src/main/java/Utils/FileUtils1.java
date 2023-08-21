package Utils;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import tdas.TrieTree;

public class FileUtils1 {
    public static boolean saveTrie(TrieTree wordTree, Window window) {
        List<String> words = new ArrayList<>();
        wordTree.helper(wordTree.getRoot(), words,"");
        
        DirectoryChooser dirChooser = new DirectoryChooser();
        File selectedFolder = dirChooser.showDialog(window);
        if(selectedFolder == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Resultado de la operación");
            alert.setContentText("Error al guardar diccionario \n Intente nuevamente");
            alert.showAndWait();
            return false;
        }
        String fullPath = selectedFolder.getAbsolutePath()+
                System.getProperty("file.separator")+"diccionario.txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fullPath))) {
            for (String word : words) {
                writer.println(word);
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText("Resultado de la operación");
            alert.setContentText("El diccionario se guardó correctamente\nFelicitaciones");
            alert.showAndWait();
            return true;
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Resultado de la operación");
            alert.setContentText("Error al cargar diccionario \nIntente nuevamente");
            alert.showAndWait();
            return false;
        }
    }
    
       
    public static void overwriteWordsFile(File srcfile,File targetFile) throws IOException {
        
        Path from = srcfile.toPath();
        Path to = targetFile.toPath();
        Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        
        
    }

    public static TrieTree loadTrie(Window window) {
        Alert warn = new Alert(Alert.AlertType.WARNING);
        warn.setTitle("Atención");
        warn.setHeaderText("Alerta en Carga de Diccionario");
        warn.setContentText("La carga de un nuevo diccionario sobreescribirá el anterior\nProceda con precaución");
        warn.showAndWait();
        
        TrieTree loadedTrie = new TrieTree();
        
        FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog(window);
        if(file == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Resultado de la operación");
            alert.setContentText("Error al cargar diccionario\nIntente nuevamente");
            alert.showAndWait();
            return null;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                loadedTrie.insert(line);
            }
            
            overwriteWordsFile(file,new File("src/main/resources/files/words.txt"));
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText("Estado de Carga:");
            alert.setContentText("El archivo se ha cargado satisfactoriamente\nFelicidades");
            alert.showAndWait();
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


