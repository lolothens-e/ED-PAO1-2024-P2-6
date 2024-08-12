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
    
    static Scanner sc = new Scanner(System.in);
    
    static ArrayList<String> preguntas=leerPreguntas();
    static HashMap<String,ArrayList<String>> respuestas=leerRespuestas();
    
    public static ArbolBinario<String> arbolJuego = new ArbolBinario<>();
    public static ArrayList<String> respuestasUsuario=new ArrayList<>();
    public static int nPreguntasUsuario=0;
    
    public static int nPreguntas=leerPreguntas().size();
    public static HashMap<ArbolBinario,List<String>> caminos = cargarArbol();
    public static HashMap<List<String>,String> animales = cargarAnimales();
    
    public static HashMap<List<String>,String> cargarAnimales(){
        HashMap<List<String>,String> retorno=new HashMap<>();
        for(Map.Entry<ArbolBinario,List<String>> m: caminos.entrySet()){
            if(!m.getKey().raiz.contenido.equals("Animal no definido")&&!(((String)m.getKey().raiz.contenido).startsWith("¿"))){
                retorno.put(m.getValue(),(String)m.getKey().raiz.contenido );
            }
        }
        return retorno;
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
        System.out.print("Ingrese numero de preguntas a jugar o escribe 9999 para ir a modo trivia:");
        Integer n=sc.nextInt();
        if(n==9999) modoTrivia();
        else juegoNormal(n);
    }
    
    public static void juegoNormal(Integer n){
        
        if(nPreguntasUsuario-nPreguntas<0)System.out.println("ADVERTENCIA: Ha seleccionado un numero de preguntas menor a la del archivo. No llegara a un animal especifico");
        if(nPreguntasUsuario-nPreguntas>0)System.out.println("ADVERTENCIA: Ha seleccionado un numero de preguntas mayor a la del archivo. El juego no continuara tras llegar a un animal");   
        
        nPreguntasUsuario=n;
        
        preguntar(); 
        
        ArbolBinario respuesta = regresarArbolRespuesta(respuestasUsuario);
        
        System.out.println(respuestasUsuario);
                
        if(respuesta.esHoja()) System.out.println("Tu animal es: "+ (String)respuesta.raiz.contenido);
        else System.out.println("No llegamos a una respuesta concluyente, sin embargo, tus animales pudieron haber sido:"+ regresarAnimalesDescendientes(respuesta));
    }
    
    public static void preguntar(){
        respuestasUsuario.clear();
        for(int i = 0; i<nPreguntasUsuario; i++){
            System.out.println(preguntas.get(i)+(" Responda con Y(Si) / N(No) :"));
            
            String respuesta=sc.next().strip().toLowerCase();
            
            while(!"y".equals(respuesta)&&!"n".equals(respuesta)){
                System.out.println("Opcion invalida. Ingrese nuevamente");
                respuesta=sc.next().strip().toLowerCase();
            }
            if(respuesta.equals("y"))respuestasUsuario.add("si");
            if(respuesta.equals("n")) respuestasUsuario.add("no");
            
            if(respuestasUsuario.size()==nPreguntas) i=nPreguntasUsuario;
        }
    }
    
    public static ArrayList<String> regresarAnimalesDescendientes(ArbolBinario ab){
        ArrayList<String> regreso=new ArrayList<>();
        for(var e:ab.getDescendientes()){
            boolean esAnimal= !((String)e).startsWith("¿")&&!((String)e).equals("Animal no definido");
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
    
    public static String capitalize(String s){
        s=s.toLowerCase().strip();
        s=s.substring(0,1).toUpperCase()+s.substring(1);
        return s;
    }
    
    public static void modoTrivia(){
        ArrayList<String> claveAnimal=new ArrayList<>();
        nPreguntasUsuario=nPreguntas;
        
        System.out.print("Bienvenido a trivia, escribe el nombre de un animal(existente) para comenzar: ");
        String animalAdivina=sc.next();
        
        animalAdivina=capitalize(animalAdivina);
        System.out.println(animalAdivina);
        
        while(!animales.values().contains(animalAdivina)) {
            System.out.println(animalAdivina);
            System.out.println("Animal no encontrado. Los animales disponibles son : "+ animales.values()+" \nIngrese de nuevo: ");
            animalAdivina=capitalize(sc.next());  
        }
        
        for(Map.Entry m:animales.entrySet())if(animalAdivina.equals(m.getValue())) claveAnimal=(ArrayList<String>)m.getKey();
        
        preguntar();
        
        System.out.println(respuestasUsuario);
        
        ArbolBinario respuesta=regresarArbolRespuesta(respuestasUsuario);
        
        if(respuesta.esHoja()&&respuesta.raiz.contenido.equals(animalAdivina)) System.out.println("Adivinaste correctamente el animal!");
        else if(animales.getOrDefault(respuestasUsuario,"null").equals("null")){
            respuestasUsuario.remove(respuestasUsuario.size()-1);
            respuestasUsuario.remove(respuestasUsuario.size()-1);
            System.out.println("No adivinaste el animal que buscabas. Aqui tienes algunos a los que quizas quisiste apuntar: "+ regresarAnimalesDescendientes(regresarArbolRespuesta(respuestasUsuario)));
        }
        else System.out.println("Aunque no adivinaste el animal "+animalAdivina+". Conseguiste atinarle a: "+ animales.get(respuestasUsuario));   
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
