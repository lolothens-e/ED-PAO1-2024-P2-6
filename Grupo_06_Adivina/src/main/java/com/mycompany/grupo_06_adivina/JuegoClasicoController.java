/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.grupo_06_adivina;

import EstructuraDatos.ArbolBinario;
import java.io.IOException;
import static modelo.Juego.*;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Juego;

/**
 * FXML Controller class
 *
 * @author Steven
 */

public class JuegoClasicoController implements Initializable {
    @FXML
    private Label lbPregunta;
    @FXML
    private Label lblnPregunta;
    @FXML
    private Label lblEditable;
    @FXML
    private Label lblTitulo;
    @FXML
    private Button btnNo;
    @FXML
    private Button btnSi;
    @FXML
    private SplitPane pnPrincipal;
    @FXML
    private VBox vbPosibilidades;
    @FXML
    private Label lbVerbo;
    @FXML
    private Label lbFrase;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnNo.setOnMouseClicked(ev -> rspNo());
        btnSi.setOnMouseClicked(ev -> rspSi());
        
        pnPrincipal.setDividerPositions(0.8);
        pnPrincipal.getDividers().get(0).positionProperty().addListener((obs, oldVal, newVal) -> {
        pnPrincipal.setDividerPositions(0.8); // Revert to the old value
        });
        
        cargarSiguientePregunta();
    }    
    public void cargarSiguientePregunta(){
        if(Juego.nPreguntasUsuario>Juego.respuestasUsuario.size()){
            try{
            lblnPregunta.setText("Pregunta #"+(Juego.respuestasUsuario.size()+1));
            lblTitulo.setText("Modo Clasico");
            lbPregunta.setText(Juego.preguntas.get(Juego.respuestasUsuario.size()));
            actualizarPosibilidades();
            }catch(IndexOutOfBoundsException e){
            revisarPosibilidadesFinales();
            }
        }
        else{
            revisarPosibilidadesFinales();  
        }  
    }
    
    public void revisarPosibilidadesFinales(){
        
        ArbolBinario respuesta = regresarArbolRespuesta(respuestasUsuario);
        
        if(respuesta.esHoja()&&!((String)respuesta.raiz.contenido).equals("Animal no definido")) anunciarAnimalRespuesta((String)respuesta.raiz.contenido);
        
        if(Juego.nPreguntasUsuario<Juego.nPreguntas&&!respuesta.esHoja()&& Juego.regresarAnimalesDescendientes(respuesta).size()>1) anunciarAnimalesPosibilidad(Juego.regresarAnimalesDescendientes(respuesta));
        
        if(Juego.nPreguntasUsuario<Juego.nPreguntas&&!respuesta.esHoja()&& Juego.regresarAnimalesDescendientes(respuesta).size()==1) anunciarAnimalPosibilidad(Juego.regresarAnimalesDescendientes(respuesta).get(0));
        
        if(Juego.nPreguntasUsuario<Juego.nPreguntas&&!respuesta.esHoja()&& Juego.regresarAnimalesDescendientes(respuesta).isEmpty()) anunciarDerrota();
        
        if(Juego.nPreguntasUsuario==Juego.nPreguntas&& respuesta.esHoja()&& animales.getOrDefault(Juego.respuestasUsuario,"N/A").equals("N/A")) deliberacionAgregarAnimal();

    }
    
    public void rspNo(){
        Juego.respuestasUsuario.add("no");
        fraseAleatoria();
        cargarSiguientePregunta();
    }
    
    public void rspSi(){
        Juego.respuestasUsuario.add("si");
        fraseAleatoria();
        cargarSiguientePregunta();
    }  
    
    public void actualizarPosibilidades()throws IndexOutOfBoundsException{
        ArrayList<String> posibilidades=Juego.regresarAnimalesDescendientes(Juego.regresarArbolRespuesta(Juego.respuestasUsuario));
        
        vbPosibilidades.getChildren().clear();
        if(posibilidades.isEmpty()){
            Label mensaje= new Label("Eh... Esto es vergonzoso");
            mensaje.setAlignment(Pos.CENTER);
            
            vbPosibilidades.setMargin(mensaje, new Insets(3,3,3,3));
            vbPosibilidades.getChildren().add(mensaje);
            lblEditable.setText("Tengo problemas tratando de adivinar tu animal...   ");
        }else{
            for(String s:posibilidades){
            Label posibilidad= new Label(s);
            posibilidad.setStyle("-fx-text-fill: white;");
            posibilidad.setAlignment(Pos.CENTER);
            posibilidad.setAlignment(Pos.TOP_CENTER);
            
            vbPosibilidades.setMargin(posibilidad, new Insets(3,3,3,3));
            vbPosibilidades.getChildren().add(posibilidad);
            
            lblEditable.setText("Puede tu animal quizas ser... "+ posibilidades.get(0)+"   ");
            }
        }
    }
    
    public void resetButtons(){
        btnSi.setOnMouseClicked(evSi -> rspSi());
        btnNo.setOnMouseClicked(evNo -> rspNo());
    }
    public void anunciarAnimalRespuesta(String s){
        lblnPregunta.setText("Tras deliberar creo yo que el animal en el que piensas es: ");
        lbPregunta.setText(s);
        lblTitulo.setText("¿Estoy en lo correcto?");
        
        btnSi.setOnMouseClicked(ev -> {
            try{
                App.alerta(Alert.AlertType.INFORMATION, "Gracias","Lo sabia, yo gano, siempre gano", "Gracias por intentar.");
                App.setRoot("Menu");
            }catch(IOException e){
                System.out.println("Error de I/O");
            }
        });
        btnNo.setOnMouseClicked(ev -> {
            try{
                App.alerta(Alert.AlertType.ERROR, "Hmmm...","No lo creo, yo siempre gano. Debes estar mintiendo", "Digamos que fue un empate ok?");
                App.setRoot("Menu");
            }catch(IOException e){
                System.out.println("Error de I/O");
            }
        });
    }
    public void anunciarAnimalPosibilidad(String s){
        lblnPregunta.setText("Soy tan bueno que no necesito el resto de preguntas. Creo que tu animal es: ");
        lbPregunta.setText(s);
        lblTitulo.setText("Espera");
        
        btnSi.setOnMouseClicked(ev -> {
            try{
                App.alerta(Alert.AlertType.INFORMATION, "Gracias","Lo sabia, yo gano, siempre gano", "Gracias por intentar.");
                App.alerta(Alert.AlertType.INFORMATION, "Gracias","Lo sabia, yo gano, siempre gano", "Gracias por intentar.");
                App.setRoot("Menu");
            }catch(IOException e){
                System.out.println("Error de I/O");
            }
        });
        btnNo.setOnMouseClicked(ev -> {
            try{
                App.alerta(Alert.AlertType.INFORMATION, "Hmmm...","Bueno, un error lo puede tener cualquiera", "Hasta los dioses mas grandes han caido, no puedes esperar la perfeccion o si?");
                App.alerta(Alert.AlertType.INFORMATION, "Gracias","Lo sabia, yo gano, siempre gano", "Gracias por intentar.");
                App.setRoot("Menu");
            }catch(IOException e){
                System.out.println("Error de I/O");
            }
        });
    }
    public void anunciarAnimalesPosibilidad(ArrayList<String> list){
        lblnPregunta.setText("Creo que tu animal se encuentra a tu derecha. ¿Es asi?");
        lbPregunta.setText("========>");
        lblTitulo.setText("Espera...");
        btnSi.setOnMouseClicked(ev -> {
            try{
                App.alerta(Alert.AlertType.INFORMATION, "Gracias","Lo sabia, yo gano, siempre gano", "Gracias por intentar.");
                App.setRoot("Menu");
            }catch(IOException e){
                System.out.println("Error de I/O");
            }
        });
        btnNo.setOnMouseClicked(ev -> {
            try{
                App.alerta(Alert.AlertType.INFORMATION, "Hmmm...","Bueno, un error lo puede tener cualquiera", "Hasta los dioses mas grandes han caido, no puedes esperar la perfeccion o si?");
                App.setRoot("Menu");
            }catch(IOException e){
                System.out.println("Error de I/O");
            }
        });
    }
    public void anunciarDerrota(){
        lblnPregunta.setText("No logro adivinar tu animal");
        lbPregunta.setText("Revancha?");
        lblTitulo.setText("Buen trabajo...");
        btnSi.setOnMouseClicked(ev -> {
            try{
                App.alerta(Alert.AlertType.INFORMATION, "Gracias","La proxima lo hare mucho mejor", "Buen trabajo.");
                App.setRoot("Menu");
            }catch(IOException e){
                System.out.println("Error de I/O");
            }
        });
        btnNo.setText("Si");
        btnNo.setOnMouseClicked(ev -> {
            try{
                App.alerta(Alert.AlertType.INFORMATION, "Gracias","La proxima lo hare mucho mejor", "Buen trabajo.");
                App.setRoot("Menu");
            }catch(IOException e){
                System.out.println("Error de I/O");
            }
        });
    }
    public void deliberacionAgregarAnimal(){
        lblnPregunta.setText("No puedo dar con tu animal. Quieres agregarlo?");
        lbPregunta.setText("Viendo el futuro creo que...");
        lblTitulo.setText("Buen trabajo");
        btnNo.setOnMouseClicked(ev -> {
            try{
                App.alerta(Alert.AlertType.ERROR, "Hmmm...","Bueno, un error lo puede tener cualquiera", "Entiendo tu miedo a que me haga aun mas poderoso. Acepto mi derrota. Hasta otra ocasion.");
                App.setRoot("Menu");
            }catch(IOException e){
                System.out.println("Error de I/O");
            }
        });
        btnSi.setOnMouseClicked(ev -> {
            try{
                App.alerta(Alert.AlertType.INFORMATION, "Gracias","La proxima lo hare mucho mejor", "Buen trabajo.");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/mycompany/grupo_06_adivina/Agregar.fxml"));
                Parent root=fxmlLoader.load();

                Stage auxStage= new Stage();
                auxStage.setScene(new Scene(root));
                auxStage.initModality(Modality.WINDOW_MODAL);
                Stage primaryStage = (Stage) btnSi.getScene().getWindow();
                auxStage.initOwner(primaryStage);
                auxStage.showAndWait();
                App.setRoot("Menu");
            }catch(IOException e){
                System.out.println("Error de I/O");
            }
        });
    }
    
    //mostrar frases aleatorias :)
    public void fraseAleatoria(){
        List<String> listaVerbo = List.of("Integrando...", "Adivinando...", "Derivando...", "Craneando...", "Pensando...", "Hackeando...",
     "Investigando...","Actualizando...","Desencriptando...","Compilando...","Refactorizando...","Depurando...");
        List<String> listaFrase = List.of("Esto no es un Akinator de animales", "La base de datos de virus ha sido actualizada", 
     "Basado en mis conocimientos puedes estar pensando en:", "Espero que tu animal siga aquí:", "El mejor juego de tu vida",
     "Alguien se toma el tiempo de leer esto?","Espero que estes respondiendo bien las preguntas ._.");
        Random random = new Random();
        int indV= random.nextInt(listaVerbo.size());
        int indF= random.nextInt(listaFrase.size());
        lbVerbo.setText(listaVerbo.get(indV));
        lbFrase.setText(listaFrase.get(indF));
    }
    
    
    /*
    public void pausa(Integer s){
        PauseTransition pause = new PauseTransition(Duration.seconds(s)); // 3-second pause

        // Start the pause
        pause.play();
    }
    */
    
    
}
