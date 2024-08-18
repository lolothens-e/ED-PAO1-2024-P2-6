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
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
            guardarAnimalNuevo(tfNombreAnimal.getText()+"(User added)",Juego.respuestasUsuario,CargarController.rutaR);
            try{
                App.setRoot("Menu");
            }catch(IOException e){
                System.out.println("Error de I/O");
            }
        });
    }    
    public static void guardarAnimalNuevo(String animal, ArrayList<String> siNo, String ruta){
        try(BufferedWriter buff= new BufferedWriter(new FileWriter(ruta,true))){
            String linea= animal;
            for(String sn: siNo){
                linea=linea+" "+sn;
            }
            buff.write(linea);
            buff.newLine();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
