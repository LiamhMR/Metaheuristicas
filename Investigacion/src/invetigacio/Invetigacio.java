/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invetigacio;
//import invetigacio.*;
import java.util.Random;

/**
 *
 * @author USUARIO
 */



public class Invetigacio
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        //int id = 2;
        //float costo = 1.0f;
        //int vecinos[] = {4,5};
        double probabilidad_mutacion = 0.2;
        int A[] = {};
        GetSheet NewGS=new GetSheet();
        Nodos ArrSol[] = NewGS.ReturnArr(); 
        Nodos[] ArrNodos=NewGS.ReturnArr();
        Nodos Poblacion[][]=new Nodos[10][38]; 
        int p1[] = {};
        int p2[] = {};

        // TODO code application logic here
        boolean fin = true;
        //Nodos nodo = new Nodos(id,costo,vecinos){};
        //Soluciones Aleatorias y con heurísticas
        Random rand = new Random();
        for (int i=0;i<10;i++){
            int random=rand.nextInt(10);
            switch(random)
            {
                case 0,1,2:
                    Poblacion[i]=minCosto(ArrNodos,50);
                break;
                case 4,5,6,7:
                    
                break;
                case 8,9:
                break;
                default;
            }
                    
        }

        //Algoritmo genético
        while(!fin){
            //elitista
            seleccion(Poblacion);
            //en un punto
            cruzamiento(p1,p2);
            //aleatoria
            mutacion(probabilidad_mutacion,A);
        }
        
    }

    public static Nodos[] seleccion(Nodos poblacion[][]) 
    {
        double costo=0; 
        double auxmin=81;
        for(int i = 0 ; i < poblacion.length; i++){
            for(int j = 0 ; i < poblacion[i].length;i++)
            costo += poblacion[i][j].Costo;
            if(){
                costo += ArrSol[i].Costo;
            }
            costo = 0;
        }
        return ArrSol;
    }


    public static void cruzamiento(int p1[],int p2[])
    {
        //cambiar este numero
        int punto_cruzamiento = 3;
        int h1[] = p1;
        int h2[] = p2;
        for(int i = punto_cruzamiento ; i < p1.length ; i++){
            h1[i] = p2[i];
            h2[i] = p1[i];
        }
        
    }


    public static void mutacion(double probabilidad_mutacion,int A[])
    {
        double mutar = (double) Math.random();
        
        for(int i = 0 ; i < A.length ; i++)
        {
            if(mutar < probabilidad_mutacion)
            {
                //muta esta posicion 
                double cambio = (double) Math.random();
                int posicion = i;
                //esta posicion se multiplica por el dato en esa posicion
                A[posicion] = (int) Math.round(posicion * cambio);
                System.out.println("nuevo numero = "+A[posicion]);
            }
            else
            {
                System.out.println("no muta");
            }
        }
    }

    //DEVUELVE UNA SOLUCIÓN
    //Minimización de costo con heurística del mas liviano, retorna un arreglo con nodos marcados mientras el total no supere un máximo
    public Nodos[] minCosto(Nodos[] Arr,float max){
        int lenghtN=Arr.length;
        float TotalCost=0;
        while(TotalCost<max){
            float CurrentCost=10;
            int SelectNode=200;
            for (int i=0;i<lenghtN;i++){
                if (Arr[i].Costo<CurrentCost){
                    CurrentCost=Arr[i].Costo;
                    SelectNode=i;
                    TotalCost=TotalCost+CurrentCost;
                }
            }
            if(SelectNode!=200)
            {
                Arr[SelectNode].select=true;
            }
        }
        return Arr;
    }

    //Comprueba que un arreglo solución sea válido - True si la solución es válida
    public boolean isValid(Nodos[] Arr){
        for (int i=0;i<Arr.length;i++){
            if (Arr[i].select=true){
                for(int j=0;j<Arr[i].Vecinos.length;j++){
                    Arr=putCover(Arr, Arr[i].Vecinos[j]);
                }
            }
        }

        for (int i=0;i<Arr.length;i++){
            if (Arr[i].cubierto=false){
                return false;
            }
        }
        return true;
    }

    public Nodos[] putCover(Nodos[] Arr,int lookId){
        for (int i=0;i<Arr.length;i++){
            if (Arr[i].Id==lookId)
            {
                Arr[i].cubierto=true;
                return Arr;
            }
        }
        return Arr;
    }

    //Genera una solución con los n(max) nodos con más vecinos  
    public Nodos[] maxVecinos(Nodos[] Arr,int max){
        int lenghtN=Arr.length;
        Random rand = new Random();
        for(int k=0;k<max;k++){
            int CurrentNCount=0;
            int SelectNode=200;
            for (int i=0;i<lenghtN;i++){
                if (Arr[i].Vecinos.length>CurrentNCount && Arr[i].select!=true){
                    CurrentNCount=Arr[i].Vecinos.length;
                    SelectNode=i;
                }
            }
            if(SelectNode!=200)
            {
                Arr[SelectNode].select=true;
            }
        }
        return Arr;
    }

    //Devuelve una solución aleatoria válida
    public Nodos[] RandomSol(Nodos[] Arr){
        Random rand = new Random();
        while (!isValid(Arr)){
            int random=rand.nextInt(Arr.length);
            Arr[random].select=true;
        }
        return Arr;
    }
}

