/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.grupo_06_adivina;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modelo.Juego;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class CargarController implements Initializable {

    @FXML
    private TextField txtRutaP;
    @FXML
    private TextField txtRutaR;
    @FXML
    private TextField txtNPreguntas;
    @FXML
    private ComboBox<?> cboxAnimales;
    
    private boolean btnSeleccionado; //True: Clasico, False: Trivia
    private String rutaP;
    private String rutaR;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    //Aqui se hacen las validaciones para empezar el juego
    @FXML
    private void aceptarCargar(ActionEvent event) {
        if(rutaP!=null&&rutaR!=null){ //Todas las rutas de los archivos deben estar cargados
            if(btnSeleccionado==true){ //Clasico
                if(!txtNPreguntas.getText().isEmpty()){
                    Juego.preguntas= Juego.leerPreguntas(rutaP);
                    Juego.respuestas= Juego.leerRespuestas(rutaR);
                    //Accion
                    //El juego clasico
                    
                            
                            
                            
                    //Despues del juego
                    
                    
                }else{
                    mensajecamposVacios();
                }
            }else{ //Trivia
                if(cboxAnimales.getValue()!=null){
                    Juego.preguntas= Juego.leerPreguntas(rutaP);
                    Juego.respuestas= Juego.leerRespuestas(rutaR);
                    //Accion
                    //El juego trivia
                    
                    
                    
                    //Despues del juego
                    
                    
                }else{
                    mensajecamposVacios();
                }
            }
        }else{
            mensajecamposVacios();
        }
    }

    @FXML
    private void cancelarCargar(ActionEvent event) {
        Stage stage= (Stage) txtRutaP.getScene().getWindow();
        stage.close();
    }

    //Inicializa la ventana para cargar los archivos segun el modo de juego
    //Se escribe "Clasico" para cargar como juego clasico. Cualquier otra cosa se considera "Trivia"
    public void setBotonSeleccionado(String btn){
        btnSeleccionado= "Clasico".equals(btn);
        
        txtRutaP.setEditable(false);
        txtRutaR.setEditable(false);
        if(btnSeleccionado==true){ //Clasico
            txtNPreguntas.setDisable(false);
            cboxAnimales.setDisable(true);
            txtNPreguntas.setPromptText("");
            //Solo permitiremos entrada de numeros
            txtNPreguntas.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) { // "\\d*" significa solo dígitos
                    txtNPreguntas.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
            
        }else{ //Trivia
            txtNPreguntas.setDisable(true);
            cboxAnimales.setDisable(false);
            cboxAnimales.setPromptText("Selecciona");
        }
        
        
    }
    
    //Obtiene la ruta del archivo txt
    public String obtenerRuta(){
        String ruta="sinRuta";
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de Texto", "*.txt")); //Filtro para solo buscar .txt
        File selectedFile = fileChooser.showOpenDialog(txtRutaP.getScene().getWindow());
        if (selectedFile != null) ruta = selectedFile.getAbsolutePath(); //Obtener ruta solo si es archivo txt
        return ruta;
    }
    
    public void mensajecamposVacios(){
        Alert vacios=new Alert(Alert.AlertType.WARNING);
        vacios.setTitle("Campos sin llenar o cargar");
        vacios.setHeaderText("Existen campos vacios");
        vacios.setContentText("Debe llenar todos los campos solicitados");
        vacios.showAndWait();
    }
    
    @FXML
    private void cargarArchivoPreguntas(ActionEvent event) {
        String ruta= obtenerRuta();
        if(!ruta.equals("sinRuta")){
            txtRutaP.setText(ruta);
            rutaP=ruta;
        }     
    }

    @FXML
    private void cargarArchivosRespuestas(ActionEvent event) {
       String ruta= obtenerRuta();
        if(!ruta.equals("sinRuta")){
            txtRutaR.setText(ruta);
            rutaR=ruta;
        }
    }
    
}
