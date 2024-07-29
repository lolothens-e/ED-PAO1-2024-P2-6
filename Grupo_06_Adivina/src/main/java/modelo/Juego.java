/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import EstructuraDatos.ArbolBinario;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


/**
 *
 * @author Steven
 */
public class Juego {
    public static ArbolBinario<String> arbolJuego= new ArbolBinario<>();
    public static int nPreguntas;
    
    //Carga arbolJuego con las preguntas y respuestas
    public static void cargarArbol(){
    }
    
    //Retorna una lista con las preguntas
    public static ArrayList<String> leerPreguntas(){
        ArrayList<String> lista= new ArrayList<>();
        
        try(BufferedReader leer= new BufferedReader(new FileReader("src/main/resources"
        + "/archivos/Preguntas.txt"))){
            String linea= "";
            while((linea=leer.readLine())!=null){
                lista.add(linea);
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        
        return lista;
    }
    
    //Retorna una mapa donde la llave es el animal y el valor es una lista con los Si y No
    public static HashMap<String,ArrayList<String>> leerRespuestas(){
        HashMap<String,ArrayList<String>> mapa= new HashMap<>();
        
        try(BufferedReader leer= new BufferedReader(new FileReader("src/main/resources"
        + "/archivos/Respuestas.txt"))){
            String linea="";
            while((linea=leer.readLine())!=null){
                ArrayList<String> lista= new ArrayList<>();
                String[] partes= linea.split(" ");
                String nombre= partes[0];
                String[] siNo = Arrays.copyOfRange(partes, 1, partes.length);
                ArrayList<String> siNoLista = new ArrayList<>(Arrays.asList(siNo));
                
                lista.addAll(siNoLista);
                mapa.put(nombre,lista);
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        
        return mapa;
    }
    
    
    
}
