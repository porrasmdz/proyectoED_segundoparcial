
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;


import Utils.CharCount;
import Utils.FileUtils;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.converter.NumberStringConverter;
import model.PartidaModel;
import tdas.TrieTree;

/**
 * FXML Controller class
 *
HEAD
 * @author ASUS
 */


public class GameController implements Initializable {
	
	private static final int LIFES = 9;
	private static final int T_SMALL = 1;
	private static final int T_MEdium = 2;
	private static final int T_BIG = 3;
	private static final int GUESS_POINTS = 10;
	private int actualWord = 0;
	private List<String> wordsList;
        
        
	@FXML
	private GridPane view;
	
	@FXML
	private ImageView ahorcadoImg;
	
	@FXML
	private Text puntosTxt, vidasTxt;
	
	@FXML
	private Label letrasTxt, finalTxt;
	
	@FXML
	private TextField intentoTxt;
	
	@FXML
	private Button letraBt, respuestaBt;
	
	//----------------------------------------------------
	
	// Model
	private PartidaModel model = new PartidaModel();
    @FXML
    private Button backBtn;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Bindings
		ahorcadoImg.imageProperty().bind(model.imageProperty());
		puntosTxt.textProperty().bindBidirectional(model.puntosProperty(), new NumberStringConverter());
		vidasTxt.textProperty().bindBidirectional(model.vidasProperty(), new NumberStringConverter());
		letrasTxt.textProperty().bind(model.usadoProperty());
		finalTxt.textProperty().bind(model.adivinarProperty());
		model.intentoProperty().bind(intentoTxt.textProperty());
		
		// Reduce life when images changes
		model.vidasProperty().addListener( (v, ov, nv) -> changeImage());
	
		// Events
		letraBt.setOnAction( evt -> addLetra() );
		respuestaBt.setOnAction( evt -> intentoAdivinar() );
		initGame();
	}

	private void changeImage() {
		
		if( model.getVidas() > 0 ) {
			model.setImage(new Image(getClass().getResource(String.format("/images/%d.png",10-model.getVidas())).toString()));
		}
	}

	private void initGame() {
		model.setVidas(LIFES);
		model.setPuntos(0);
		
		wordsList = FileUtils.loadWords();
		actualWord = (int) (Math.random() * wordsList.size());
		initWord();
	}
        
	private void initWord() {
		final String palabraOriginal =  wordsList.get(actualWord);
		StringBuilder palabra = new StringBuilder(palabraOriginal.replaceAll("[a-z,A-Z,0-9]", "-"));
		int len = palabra.length();
		int n_letras;
		// max letters by word
		if( len < 5 ) {
			n_letras = T_SMALL;
		} else if( len < 8 ) {
			n_letras = T_MEdium;
		} else {
			n_letras = T_BIG;
		}
		while( n_letras > 0 ) {
			
			int r = (int) (Math.random() * palabra.length());
			palabra.setCharAt(r, palabraOriginal.charAt(r));
			n_letras--;
		}
		
		model.setAdivinar(palabra.toString());
	}
	private void addLetra() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Letter");
		dialog.setHeaderText("Insert a Letter");
		Optional<String> str = dialog.showAndWait();
		Character c = ' ';
		if( str.isPresent() && !str.get().isEmpty() && !str.get().isBlank() ) {
			c = str.get().charAt(0);
			c = Character.toLowerCase(c); // Las palabras son en mayúscula, para que se vean más claras
			
			if( !Character.isLetter(c) && !Character.isDigit(c) ) {
				sendLetterWarning();
				return;
			}
		}else {
			sendLetterWarning();
			return;
		}
                System.out.println(c);
		final String palabraOriginal = wordsList.get(actualWord);
		//reapeated characters
		if( model.getUsado().indexOf(c) != -1 ) {
			return;
		}
		if( model.getAdivinar().indexOf(c) != -1 ) {
			int n, j, i, m;
			n = j = m = 0;
			while( (i = model.getAdivinar().indexOf(c,j)) != -1 ) {
				j = i+1;
				n++;
			}
			j = 0;
			while( (i = palabraOriginal.indexOf(c,j)) != -1 ) {
				j = i+1;
				m++;
			}
			if( m == n ) {
				return;
			}
			
		}
		          System.out.println(palabraOriginal);
                          System.out.println(palabraOriginal.indexOf(c));
		if( palabraOriginal.indexOf(c) != -1) {
			//character ok
			model.setPuntos(model.getPuntos()+1);
			StringBuilder palabra = new StringBuilder(model.getAdivinar());
			int i, j;
			j = 0;
			while( (i = palabraOriginal.indexOf(c,j)) != -1 ) {
				palabra.setCharAt(i, c);
				j = i+1;
			}
			                 System.out.println(model.getAdivinar());
			model.setAdivinar(palabra.toString());
			//is word completed
			if( palabra.toString().compareTo(palabraOriginal) == 0 ) {
				acabarJuego(false);
			}
		} else {
			model.setVidas(model.getVidas()-1);
			
			if( model.getVidas() <= 0 ) { // Hemos acabado
				fin();
			} else {
				model.setUsado(model.getUsado()+c);
			}
		}
		
	}
	private void acabarJuego(boolean esAdivinado) {
		
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("END");
		
		if( !esAdivinado ) {
			alert.setHeaderText("Completed Word");
		} else {
			alert.setHeaderText("Guessed Word");
			model.setPuntos(model.getPuntos()+GUESS_POINTS); // 10 puntos extras por adivinar la palabra
		}
		
		alert.showAndWait();
		wordsList.remove(wordsList.get(actualWord));
		if( wordsList.size() == 0 ) {
			fin();
		} else {
			actualWord = (int) (Math.random() * wordsList.size());
			model.setUsado(""); //clean used characters
			initWord(); //keep playing
		}
	}
	
	private void fin() {
		
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("END GAME");
		alert.setHeaderText("");
		alert.setContentText("Your score has been " + model.getPuntos() + ".");
		alert.showAndWait();
	}
	

	private void intentoAdivinar() {
		
		if( model.getIntento().equalsIgnoreCase(wordsList.get(actualWord))) {
			acabarJuego(true);
			
		} else {
			
			model.setVidas(model.getVidas()-1);
			if( model.getVidas() <= 0 ) { 
				fin();
			} 
		}
	}

	private void sendLetterWarning() {
		
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Letter");
		alert.setHeaderText("Invalid Letter");
		alert.setContentText("Invalid letter, please try again");	
		alert.showAndWait();
	}
	
	
	public GridPane getRootView() {
		return view;
	}
    
    private void goBack(ActionEvent event) throws IOException {
        App.setRoot("main");
    }

    @FXML
    private void back(ActionEvent event)throws IOException {
        App.setRoot("main");
    }



}
