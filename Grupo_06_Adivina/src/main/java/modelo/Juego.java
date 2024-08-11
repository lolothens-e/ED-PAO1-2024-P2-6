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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
/**
 *
 * @author Steven
 */
public class Juego {
    public static ArbolBinario<String> arbolJuego = new ArbolBinario<>();
    public static ArrayList<String> respuestasUsuario=new ArrayList<>();
    public static int nPreguntasUsuario=0;
    
    public static int nPreguntas=leerPreguntas().size();
    public static HashMap<ArbolBinario,List<String>> caminos = cargarArbol();
    public static HashMap<List<String>,String> animales = new HashMap<>();
    
    static ArrayList<String> preguntas=leerPreguntas();
    static HashMap<String,ArrayList<String>> respuestas=leerRespuestas();
    
    
    public static void cargarAnimales(){
        for(Map.Entry<ArbolBinario,List<String>> m: caminos.entrySet()){
            if(!m.getKey().raiz.contenido.equals("Animal no definido")||!(((String)m.getKey().raiz.contenido).startsWith("¿"))){
                animales.put(m.getValue(),(String)m.getKey().raiz.contenido );
            }
        }
    }
    
    //Carga arbolJuego con las preguntas y respuestas    
    public static HashMap<ArbolBinario,List<String>> cargarArbol(){
    ArrayList<String> lista=preguntas;
    HashMap<String,ArrayList<String>> resp=respuestas;
            
    Queue<ArbolBinario> ramas = new LinkedList<>();
    ArbolBinario arbol=new ArbolBinario<>(lista.get(0));
    HashMap<ArbolBinario,List<String>> local = new HashMap<>();
    
    local.put(arbol,new ArrayList<>());
    
    ramas.offer(arbol);
    
    while(!ramas.isEmpty()){        
        ArbolBinario uso=ramas.poll();
        
        ArbolBinario izquierda,derecha;
                
        try{    
            izquierda=new ArbolBinario(lista.get(lista.indexOf(uso.raiz.contenido)+1));
            derecha=new ArbolBinario(lista.get(lista.indexOf(uso.raiz.contenido)+1));
            
            ramas.offer(izquierda);
            ramas.offer(derecha);
        }catch(IndexOutOfBoundsException e){
            
            izquierda=new ArbolBinario("Animal no definido");
            derecha=new ArbolBinario("Animal no definido");
            
            ArrayList<String> posibles=new ArrayList<>();
            
            for(Map.Entry<String,ArrayList<String>> m:resp.entrySet()){
                if(m.getValue().subList(0, nPreguntas-1).equals(local.get(uso))) posibles.add(m.getKey());//Cambiar a numero de preguntas
            }
            for(String s: posibles){
                List<String> aux=new ArrayList(local.get(uso));
                aux.add("si");
                if(aux.equals(resp.get(s))) izquierda=new ArbolBinario(s);
                else derecha=new ArbolBinario(s);
            } 
        }
       
        local.put(derecha,new ArrayList<>(local.get(uso)));
        local.get(derecha).add("no");
        
        local.put(izquierda,new ArrayList<>(local.get(uso)));
        local.get(izquierda).add("si");

        uso.addLeft(izquierda);
        uso.addRight(derecha);
    }
    
    arbolJuego=arbol;
    
    return local;
    }
    
    
    public static void main(String []args){
        Scanner sc= new Scanner(System.in);
        System.out.print("Ingrese numero de preguntas a jugar:");
        nPreguntasUsuario=sc.nextInt();
        if(nPreguntasUsuario-nPreguntas<0)System.out.println("ADVERTENCIA: Ha seleccionado un numero de preguntas menor a la del archivo. No llegara a un animal especifico");
        if(nPreguntasUsuario-nPreguntas>0)System.out.println("ADVERTENCIA: Ha seleccionado un numero de preguntas mayor a la del archivo. El juego no continuara tras llegar a un animal");
        for(int i = 0; i<nPreguntasUsuario; i++){
            System.out.println(preguntas.indexOf(i)+("Y/N"));
            
            String respuesta=sc.next();
            
            while(!"Y".equals(respuesta)||!"N".equals(respuesta)){
                System.out.println("Opcion invalida. Ingrese nuevamente");
                
            }
            if(respuesta.equals("Y"))respuestasUsuario.add("si");
            if(respuesta.equals("N")) respuestasUsuario.add("no");
            
            if(respuestasUsuario.size()==nPreguntas) i=nPreguntasUsuario;
        }
        ArbolBinario respuesta = regresarArbolRespuesta(respuestasUsuario);
        
        if(respuesta.esHoja()) System.out.println("Tu animal es:"+ (String)respuesta.raiz.contenido);
        else System.out.println("No llegamos a una respuesta concluyente, sin embargo, tus animales pudieron haber sido:"+ regresarAnimalesDescendientes(respuesta));
    }
    
    public static ArrayList<String> regresarAnimalesDescendientes(ArbolBinario ab){
        ArrayList<String> regreso=new ArrayList<>();
        for(var e:ab.getDescendientes()){
            boolean esAnimal= !((String)e).startsWith("¿")||!((String)e).equals("Animal no definido");
            if(esAnimal) regreso.add((String)e);
        }
        return regreso;
    }
    
    public static ArbolBinario regresarArbolRespuesta(ArrayList<String> respuestas){
        String pAnimal=animales.getOrDefault(respuestas,"N/A");

        for(Map.Entry m:caminos.entrySet()) if(m.getValue().equals(pAnimal))return (ArbolBinario) m.getKey();
        for(Map.Entry m:caminos.entrySet()) if(m.getValue().equals(respuestas)) return (ArbolBinario) m.getKey();
        
        return null;
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
