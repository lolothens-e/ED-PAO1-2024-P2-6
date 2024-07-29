/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EstructuraDatos;
import java.util.ArrayList;
/**
 *
 * @author Anthony
 */
public class ArbolBinario<E> {
    Nodo raiz;
    
    public class Nodo{
        E contenido;
        ArbolBinario<E> izq;
        ArbolBinario<E> der;
        public Nodo(E e){
            contenido = e;
            izq = der = null;
        }
    }

    public ArbolBinario(){ 
        clear();
    }
    
    public ArbolBinario(E contenido){
        raiz = new Nodo(contenido);
    }
    
    public ArbolBinario(Nodo n){
        raiz=n;
    }
    
    public void clear(){ 
        raiz = null; 
    }
    
    public boolean isEmpty(){ 
        return raiz==null; 
    }

    public boolean esHoja(){
        if(isEmpty()) return false;
        return raiz.der==null && raiz.izq==null;
    }

    public int altura(){
        if(isEmpty()) return 0;
        if(esHoja()) return 1;
        int alturaIzq = (raiz.izq!=null) ? raiz.izq.altura() : 0;
        int alturaDer = 0;
        if(raiz.der!=null) alturaDer = raiz.der.altura();

        return 1 + Math.max( alturaDer, alturaIzq);
    }

    public boolean addLeft(ArbolBinario ab){
        if(raiz.izq!=null) return false;
        raiz.izq = ab;
        return true;
    }
    public boolean addRight(ArbolBinario ab){
        if(raiz.der!=null) return false;
        raiz.der = ab;
        return true;
    }

    public ArrayList<E> recorridoPreOrden(){
        if(isEmpty()) return null;
        ArrayList<E> recorrido = new ArrayList<E>();
        recorrido.add(raiz.contenido);

        if(raiz.izq!=null) recorrido.addAll(raiz.izq.recorridoPreOrden());
        if(raiz.der!=null) recorrido.addAll(raiz.der.recorridoPreOrden());
        return recorrido;
    }
    
    public ArrayList<E> recorridoEnOrden(){
        if(isEmpty()) return new ArrayList<E>();
        ArrayList<E> recorrido = new ArrayList<E>();
        if(raiz.izq!=null) recorrido.addAll(raiz.izq.recorridoEnOrden());
        recorrido.add(raiz.contenido);
        if(raiz.der!=null) recorrido.addAll(raiz.der.recorridoEnOrden());
        
        return recorrido;
    }
    
    public ArrayList<E> recorridoPostOrden(){
        if(isEmpty()) return new ArrayList<E>();
        ArrayList<E> recorrido = new ArrayList<E>();
        if(raiz.izq!=null) recorrido.addAll(raiz.izq.recorridoPostOrden());
        if(raiz.der!=null) recorrido.addAll(raiz.der.recorridoPostOrden());
        recorrido.add(raiz.contenido);
        
        return recorrido;
    }
    
    //Retorna los descendientes de un nodo(Los elementos que siguen despues)
    public ArrayList<E> getDescendientes(){
        ArrayList<E> total= new ArrayList<E>();
        ArrayList<E> totalIzq= new ArrayList<E>();
        ArrayList<E> totalDer= new ArrayList<E>();
        
        if(!raiz.izq.isEmpty()) totalIzq= raiz.izq.recorridoPreOrden();
        if(!raiz.der.isEmpty()) totalDer= raiz.der.recorridoPreOrden();
        
        total.addAll(totalIzq);
        total.addAll(totalDer);
        return total;
    }
 
}
