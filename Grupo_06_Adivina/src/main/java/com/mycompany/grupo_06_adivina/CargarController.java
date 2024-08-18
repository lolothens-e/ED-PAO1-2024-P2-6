/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.grupo_06_adivina;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modelo.Juego;
import static modelo.Juego.nPreguntas;
import static modelo.Juego.nPreguntasUsuario;

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
    @FXML
    private Button btnCargar;
    
    private boolean esClasico; //True: Clasico, False: Trivia
    public static String rutaP;
    public static String rutaR;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Juego.limpiarVariables();
        cboxAnimales.setPromptText("Selecciona");
        txtNPreguntas.setDisable(true);
        cboxAnimales.setDisable(true);
    }
    
    @FXML
    private void cargarcmbAnimales(){
        List animales= new ArrayList<>(Juego.animales.values());
        cboxAnimales.getItems().addAll(animales);
        cboxAnimales.setDisable(false);
    }
    
    private void iniciarVarJuego(){
        Juego.leerPreguntas(rutaP);
        Juego.leerRespuestas(rutaR);
        Juego.cargarArbol();
        Juego.cargarAnimales();
    }
    
    @FXML
    private void cargarArchivos(ActionEvent event){
        if(rutaP==null||rutaR==null){
            App.alerta(Alert.AlertType.INFORMATION, "No especifico archivos", "Se procedera a cargar las rutas guardadas","Al no haber colocado rutas o seleccionado archivos se procedera con la carga de los archivos predeterminados");
            rutaP=App.rutaFijaP;
            rutaR=App.rutaFijaR;
            
        }
        iniciarVarJuego();
        if(esClasico) txtNPreguntas.setDisable(false);
        else cargarcmbAnimales();

        btnCargar.setDisable(true);
    }

    //Aqui se hacen las validaciones para empezar el juego
    @FXML
    private void aceptarCargar(ActionEvent event)throws IOException{
        // Si hay rutas sin especificar, si es clasico y no hay # de preguntas, o si es trivia y no hay animal seleccionado
        boolean validacion=(esClasico&&!txtNPreguntas.getText().isEmpty())||(!esClasico&&cboxAnimales.getValue()!=null);
        if(validacion){ //Todas las rutas de los archivos deben estar cargados
            if(esClasico){
                Juego.nPreguntasUsuario=Integer.parseInt(txtNPreguntas.getText());
                abrirJuego("JuegoClasico");
            }
            else{
                Juego.nPreguntas=Juego.nPreguntasUsuario;
                Juego.getClaveByString((String)cboxAnimales.getValue());
                abrirJuego("JuegoTrivia");
            }   
        }else{
            mensajecamposVacios();
        }
    }
    
    @FXML
    private void abrirJuego(String modo)throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/mycompany/grupo_06_adivina/"+modo+".fxml"));
        Parent root=fxmlLoader.load();

        if(nPreguntasUsuario-nPreguntas<0)App.alerta(Alert.AlertType.WARNING,"ADVERTENCIA", "Ha seleccionado un numero de preguntas menor a la del archivo", "No llegara a un animal especifico");
        if(nPreguntasUsuario-nPreguntas>0)App.alerta(Alert.AlertType.WARNING,"ADVERTENCIA", "Ha seleccionado un numero de preguntas mayor a la del archivo","El juego no continuara tras llegar a un animal");
        
        Stage stage = (Stage) txtRutaP.getScene().getWindow();
        stage.close();
        stage.setScene(new Scene(root));
        
        
        App.setRoot(modo);
    }
            
    @FXML
    private void cancelarCargar(ActionEvent event)throws IOException {
        Stage stage= (Stage) txtRutaP.getScene().getWindow();
        stage.close();
        App.setRoot("Menu");
    }

    //Inicializa la ventana para cargar los archivos segun el modo de juego
    //Se escribe "Clasico" para cargar como juego clasico. Cualquier otra cosa se considera "Trivia"
    public void setBotonSeleccionado(String btn){
        esClasico= "Clasico".equals(btn);        
        txtNPreguntas.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtNPreguntas.setText(oldValue);
            }
        });
        txtRutaP.setEditable(false);
        txtRutaR.setEditable(false);
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
        String ruta = obtenerRuta();
        if(!ruta.equals("sinRuta")){
            txtRutaP.setText(ruta);
            rutaP=ruta;
        }     
    }

    @FXML
    private void cargarArchivosRespuestas(ActionEvent event) {
       String ruta = obtenerRuta();
        if(!ruta.equals("sinRuta")){
            txtRutaR.setText(ruta);
            rutaR=ruta;
        }
    }
    
}
