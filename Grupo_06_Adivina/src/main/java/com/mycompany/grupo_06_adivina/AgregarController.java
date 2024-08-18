/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.grupo_06_adivina;

import com.mycompany.grupo_06_adivina.CargarController;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Juego;
/**
 * FXML Controller class
 *
 * @author Anthony
 */
public class AgregarController implements Initializable {


    @FXML
    private TextField tfNombreAnimal;
    @FXML
    private Button btnAgregar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAgregar.setOnMouseClicked(ev -> {
            guardarAnimalNuevo("\n"+tfNombreAnimal.getText().replaceAll("\\s", "")+"(User-added)",Juego.respuestasUsuario,CargarController.rutaR);
            Stage stage = (Stage) btnAgregar.getScene().getWindow();
            stage.close();
        });
    }    
    public static void guardarAnimalNuevo(String animal, ArrayList<String> siNo, String ruta){
        try(BufferedWriter buff= new BufferedWriter(new FileWriter(ruta,true))){
            String linea= animal;
            for(String sn: siNo){
                linea=linea+" "+sn;
            }
            buff.write(linea);
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
