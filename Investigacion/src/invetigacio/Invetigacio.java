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
    GetSheet NewGS=new GetSheet();
    Nodos[] ArrNodos=NewGS.ReturnArr();
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
        Nodos ArrSol[] = {}; 

        // TODO code application logic here
        boolean fin = true;
        //Nodos nodo = new Nodos(id,costo,vecinos){};


        while(!fin){
            //elitista
            seleccion(ArrSol);
            //en un punto
            cruzamiento();
            //aleatoria
            mutacion(probabilidad_mutacion,A);
        }
        
    }

    public static Nodos[] seleccion(Nodos ArrSol[]) 
    {
        double costo = 0; 
        for(int i = 0 ; i < ArrSol.length; i++){
            
            if(ArrSol[i].select){
                costo += ArrSol[i].Costo;
            }
            costo = 0;
        }
        return ArrSol;
    }


    public static void cruzamiento()
    {
        
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

    public Nodos[] minVecinos(Nodos[] Arr){
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

