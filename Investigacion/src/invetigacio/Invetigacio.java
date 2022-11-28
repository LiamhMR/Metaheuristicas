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
        boolean execute=true;
        //int id = 2;
        //float costo = 1.0f;
        //int vecinos[] = {4,5};
        //int A[] = {};
        GetSheet NewGS=new GetSheet();//FUNCIONA
        Nodos ArrSol[] = NewGS.ReturnArr(); 
        Nodos[] ArrNodos=NewGS.ReturnArr();
        Nodos Poblacion[][]=new Nodos[10][38]; 

        //int p1[] = {};
        //int p2[] = {};

        // TODO code application logic here
        boolean fin = true;
        //Nodos nodo = new Nodos(id,costo,vecinos){};
        //Soluciones Aleatorias y con heurísticas
        Random rand = new Random();
        for (int i=0;i<10;i++){
            int random=rand.nextInt(10);
            double maximocosto=10;
            int NCount=20;
            Nodos[] TrySol=new Nodos[2];
            switch(random)
            {
                case 0,1,2:
                    do{
                        maximocosto++;
                        TrySol=minCosto(ArrNodos,maximocosto);
                    }while(!isValid(TrySol));
                    Poblacion[i]=TrySol;
                break;
                case 4,5,6,7:
                    TrySol=RandomSol(ArrNodos);
                    Poblacion[i]=TrySol;
                break;
                case 8,9:
                    do{
                        NCount++;
                        TrySol=maxVecinos(ArrNodos, NCount);
                    }while(!isValid(TrySol));
                    Poblacion[i]=TrySol;
                break;
            }
                    
        }

        //Algoritmo genético
        while(!fin && execute==true){
            //elitista
            Nodos p1[] = seleccionp1(Poblacion);
            Nodos p2[] = seleccionp2(Poblacion,p1);
            //en un punto
            cruzamiento(p1,p2);
        }
        printPoblación(Poblacion);
    }

    public static Nodos[] seleccionp1(Nodos poblacion[][]) 
    {
        double costo=0; 
        double auxmin=81;
        Nodos[] padre = new Nodos[38];
        for(int i = 0 ; i < poblacion.length; i++){
            for(int j = 0 ; i < poblacion[i].length;i++)
            costo += poblacion[i][j].Costo;
            if(costo < auxmin){
                auxmin = costo;
                padre = poblacion[i];
            }
            costo = 0;
        }
        return padre;
    }



    public static Nodos[] seleccionp2(Nodos poblacion[][],Nodos p1[]) 
    {
        
        double costo=0; 
        double auxmin=81;
        Nodos[] padre = new Nodos[38];
        for(int i = 0 ; i < poblacion.length; i++){
            if(p1 != poblacion[i]){
            for(int j = 0 ; i < poblacion[i].length;i++)
            costo += poblacion[i][j].Costo;
            if(costo < auxmin){
                auxmin = costo;
                padre = poblacion[i];
            }
            costo = 0;
            }
        }
        return padre;
    }


    public static void cruzamiento(Nodos p1[],Nodos p2[])
    {
        //cambiar este numero
        int punto_cruzamiento = 3;
        Nodos h1[] = p1;
        Nodos h2[] = p2;
        for(int i = punto_cruzamiento ; i < p1.length ; i++){
            h1[i] = p2[i];
            h2[i] = p1[i];
        } 
        double probabilidad_mutacion = 0.2;
        mutacion(probabilidad_mutacion,h1,h2);
        
    }


    public static void mutacion(double probabilidad_mutacion,Nodos h1[],Nodos h2[])
    {
        double mutar = (double) Math.random();
        
        for(int i = 0 ; i < h1.length ; i++)
        {
            if(mutar < probabilidad_mutacion)
            {
                //muta esta posicion 
                double cambio = (double) Math.random();
                int posicion = i;
                //esta posicion se multiplica por el dato en esa posicion             
                h1[posicion].Id = (int) Math.round(posicion * cambio);
                System.out.println("nuevo numero = "+h1[posicion]);
            }
            else
            {
                System.out.println("no muta");
            }
        }


        for(int i = 0 ; i < h2.length ; i++)
        {
            if(mutar < probabilidad_mutacion)
            {
                //muta esta posicion 
                double cambio = (double) Math.random();
                int posicion = i;
                //esta posicion se multiplica por el dato en esa posicion
                h2[posicion].Id = (int) Math.round(posicion * cambio);
                System.out.println("nuevo numero = "+h2[posicion]);
            }
            else
            {
                System.out.println("no muta");
            }
        }
    }

    //DEVUELVE UNA SOLUCIÓN
    //Minimización de costo con heurística del mas liviano, retorna un arreglo con nodos marcados mientras el total no supere un máximo
    public static Nodos[] minCosto(Nodos[] Arr,double max){
        int lenghtN=Arr.length;
        double TotalCost=0;
        while(TotalCost<max){
            double CurrentCost=10;
            int SelectNode=200;
            for (int i=0;i<lenghtN;i++){
                if (Arr[i].Costo<CurrentCost && Arr[i].select==false){
                    System.out.println(Arr[SelectNode].select);
                    CurrentCost=Arr[i].Costo;
                    System.out.println(CurrentCost);
                    SelectNode=i;
                    TotalCost=TotalCost+CurrentCost;
                }
            }
            if(SelectNode!=200)
            {
                System.out.println("Id seleccionado:"+Arr[SelectNode].Id);
                Arr[SelectNode].select=true;
                System.out.println(Arr[SelectNode].select);
            }
        }
        System.out.println("Total Costo:"+TotalCost);
        return Arr;
    }

    //Comprueba que un arreglo solución sea válido - True si la solución es válida
    public static boolean isValid(Nodos[] Arr){
        for (int i=0;i<Arr.length;i++){
            if (Arr[i].select=true){
                for(int j=0;j<Arr[i].Vecinos.length;j++){
                    Arr=putCover(Arr, Arr[i].Vecinos[j]);
                }
            }
        }

        for (int i=0;i<Arr.length;i++){
            if (Arr[i].cubierto=false){
                System.out.println("Sol no valido");
                return false;
            }
        }
        return true;
    }

    public static Nodos[] putCover(Nodos[] Arr,int lookId){
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
    public static Nodos[] maxVecinos(Nodos[] Arr,int max){
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
    public static Nodos[] RandomSol(Nodos[] Arr){
        Random rand = new Random();
        while (!isValid(Arr)){
            int random=rand.nextInt(Arr.length);
            Arr[random].select=true;
        }
        return Arr;
    }

    public static void printPoblación(Nodos[][] Poblacion){
        for (int i=0;i<Poblacion.length;i++){
            for(int k=0;k<Poblacion[i].length;k++){
                if(Poblacion[i][k]==null){
                    System.out.println(i+","+k+" es null uwu");

                }
                if (Poblacion[i][k].select=true){
                    System.out.print(Poblacion[i][k].Id + " ");
                }
            }
            System.out.println(" ");
        } 
    }
}

