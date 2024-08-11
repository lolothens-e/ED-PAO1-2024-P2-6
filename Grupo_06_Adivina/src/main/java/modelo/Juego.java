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
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;


/**
 *
 * @author Steven
 */
public class Juego {
    public static ArbolBinario<String> arbolJuego= new ArbolBinario<>();
    public static int nPreguntas;
    
    //Carga arbolJuego con las preguntas y respuestas
    public static void cargarArbol(){
        cargarPreguntas(leerPreguntas());
        HashMap<String,ArrayList<String>> respuestas=leerRespuestas();
        
        //for(String s:arbolJuego.recorridoPreOrden()) System.out.println(s);
    }
    
    public static void cargarPreguntas(ArrayList<String> lista) {
    if (lista == null || lista.isEmpty()) {
        return;
    }
    Queue<ArbolBinario> ramas = new LinkedList<>();
    ArbolBinario arbol=new ArbolBinario<>(lista.get(0));
    HashMap<ArbolBinario,List<String>> caminos = new HashMap<>();
    
    caminos.put(arbol,new ArrayList<>());
    
    ramas.offer(arbol);
    
    while(!ramas.isEmpty()){        
        ArbolBinario uso=ramas.poll();
        
        ArbolBinario izquierda;
        ArbolBinario derecha;
                
        try{    
            izquierda=new ArbolBinario(lista.get(lista.indexOf(uso.raiz.contenido)+1));
            derecha=new ArbolBinario(lista.get(lista.indexOf(uso.raiz.contenido)+1));
            
            ramas.offer(izquierda);
            ramas.offer(derecha);
        }catch(IndexOutOfBoundsException e){
            izquierda=new ArbolBinario("Animal");
            derecha=new ArbolBinario("Animal");
        }
       
        caminos.put(derecha,new ArrayList<>(caminos.get(uso)));
        caminos.get(derecha).add("No");
        
        caminos.put(izquierda,new ArrayList<>(caminos.get(uso)));
        caminos.get(izquierda).add("Si");

        uso.addLeft(izquierda);
        uso.addRight(derecha);
    }
    //for(Map.Entry m:caminos.entrySet()) System.out.println(((ArbolBinario)m.getKey()).raiz.contenido+" "+m.getValue());
    arbolJuego=arbol;
    }
    
    
    public static void main(String []args){
        for(String s:leerPreguntas()) System.out.println(s);
        for(Map.Entry m:leerRespuestas().entrySet()) System.out.println(m);
        cargarArbol();
    }
    
    //Retorna una lista con las preguntas
    public static ArrayList<String> leerPreguntas(){
        ArrayList<String> lista= new ArrayList<>();
        
        try(BufferedReader leer= new BufferedReader(new FileReader("src/main/resources"
        + "/archivos/Preguntas2.txt"))){
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
        + "/archivos/Respuestas2.txt"))){
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
