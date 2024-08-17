/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.grupo_06_adivina;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class MenuController implements Initializable {

    @FXML
    private Button btnClasico;
    @FXML
    private Button btnTrivia;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void modoClasico(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/mycompany/grupo_06_adivina/Cargar.fxml"));
        Parent root=fxmlLoader.load();
        
        CargarController controlador= fxmlLoader.getController();
        controlador.setBotonSeleccionado("Clasico");
        
        Stage modalStage= new Stage();
        modalStage.setScene(new Scene(root));
        modalStage.initModality(Modality.WINDOW_MODAL);
        Stage primaryStage = (Stage) btnClasico.getScene().getWindow();
        modalStage.initOwner(primaryStage);
        modalStage.showAndWait();
    }

    @FXML
    private void modoTrivia(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/mycompany/grupo_06_adivina/Cargar.fxml"));
        Parent root=fxmlLoader.load();
        
        CargarController controlador= fxmlLoader.getController();
        controlador.setBotonSeleccionado("Trivia");
        
        Stage modalStage= new Stage();
        modalStage.setScene(new Scene(root));
        modalStage.initModality(Modality.WINDOW_MODAL);
        Stage primaryStage = (Stage) btnTrivia.getScene().getWindow();
        modalStage.initOwner(primaryStage);
        modalStage.showAndWait();
    }

}
