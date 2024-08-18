/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.grupo_06_adivina;

import EstructuraDatos.ArbolBinario;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import modelo.Juego;
import static modelo.Juego.regresarArbolRespuesta;
import static modelo.Juego.respuestasUsuario;
/**
 * FXML Controller class
 *
 * @author Steven
 */
public class JuegoTriviaController implements Initializable {


    @FXML
    private Label lbPreguntaTrivia;
    @FXML
    private Label lblAnimal;
    @FXML
    private Label lblTitulo;
    @FXML
    private Button btnNoTrivia;
    @FXML
    private Button btnSiTrivia;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblAnimal.setText("El animal a adivinar es: "+Juego.animales.get(Juego.claveAnimalTrivia)+". ¿Crees poder llegar a el?");
        
        btnNoTrivia.setOnMouseClicked(ev -> rspNo());
        btnSiTrivia.setOnMouseClicked(ev -> rspSi());
        
        cargarSiguientePregunta();
        
    }    
    public void rspNo(){
        Juego.respuestasUsuario.add("no");
        revisarRespuesta();
    }
    
    public void rspSi(){
        Juego.respuestasUsuario.add("si");
        revisarRespuesta();
    }
    
    public void cargarSiguientePregunta(){
        if(Juego.respuestasUsuario.equals(Juego.claveAnimalTrivia))anunciarVictoria();
        
        lblTitulo.setText("Modo Trivia");
        lbPreguntaTrivia.setText(Juego.preguntas.get(Juego.respuestasUsuario.size()));
        
    }
    
    public void revisarRespuesta(){
        if(Juego.respuestasUsuario.get(Juego.respuestasUsuario.size()-1).equals(Juego.claveAnimalTrivia.get(Juego.respuestasUsuario.size()-1))) cargarSiguientePregunta();
        else anunciarPerdida();
    }
    
    public void deliberarPerdida(ArrayList<String> posAnimales){
        lblTitulo.setText("Antes de que te vayas...");
        lblAnimal.setText("(Presiona cuaquier boton para volver al menu) Pareceria que estas hablando de otro animal: ");
        lbPreguntaTrivia.setText(posAnimales.get(0));
    }
    
    public void anunciarPerdida(){
        ArrayList<String> posAnimales = Juego.regresarAnimalesDescendientes(Juego.regresarArbolRespuesta((ArrayList<String>)Juego.respuestasUsuario));
        
        App.alerta(Alert.AlertType.WARNING,"Diste lo mejor de ti","Parecer ser que no conoces tan bien a tus animales.","No te preocupes, me tomo mil de años llegar a donde estoy. Solo te faltan 999, buen trabajo. ¿Volver al menu?");
        
        if (!posAnimales.isEmpty())deliberarPerdida(posAnimales);
        
        btnNoTrivia.setOnMouseClicked(ev -> volverMenu());
        btnSiTrivia.setOnMouseClicked(ev -> volverMenu());
        
    }
    
    public void volverMenu(){
        try{
            App.setRoot("Menu");
        }catch(IOException e){
            System.out.println("Error de I/O");
        } 
    }
    
    public void anunciarVictoria(){
        App.alerta(Alert.AlertType.WARNING,"Felicidades","Una persona casi tan preparada como yo.","Aun asi, me tomo mil de años llegar a donde estoy. Solo te faltan 999, buen trabajo.");
        try{
            App.setRoot("Menu");
        }catch(IOException e){
            System.out.println("Error de I/O");
        } 
    }
}
