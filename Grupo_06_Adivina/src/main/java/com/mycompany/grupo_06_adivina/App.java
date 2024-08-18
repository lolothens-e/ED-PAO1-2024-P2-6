package com.mycompany.grupo_06_adivina;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    
    
    public final static String rutaFijaP="src/main/resources/archivos/preguntas.txt";
    public final static String rutaFijaR="src/main/resources/archivos/respuestas.txt";
    
    
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Menu"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
    @FXML
    public static void alerta(AlertType tipo,String titulo, String cabecera, String contenido){
        Alert alerta=new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecera);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

}