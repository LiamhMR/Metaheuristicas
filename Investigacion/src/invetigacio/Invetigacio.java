/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invetigacio;
import java.lang.management.GarbageCollectorMXBean;
//import invetigacio.*;
import java.util.Random;

import org.apache.poi.ss.usermodel.PrintCellComments;

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
        //printArr(ArrNodos);
        Nodos Poblacion[][]=new Nodos[10][38];

        //int p1[] = {};
        //int p2[] = {};

        // TODO code application logic here
        //Nodos nodo = new Nodos(id,costo,vecinos){};
        //Soluciones Aleatorias y con heurísticas
        Random rand = new Random();
        Nodos[] TrySol;
        for (int i=0;i<10;i++){
            int random=rand.nextInt(9);
            //System.out.println("RAND="+random+" itrN="+i);
            double maximocosto=30;
            int NCount=25;
            int maxRandCount=30;
            switch(random)
            {
                case 0,1,2:
                    System.out.println("MIN TRY");
                    do{
                        maximocosto++;
                        TrySol=minCosto(NewGS.ReturnArr(),maximocosto);
                    }while(isValid(TrySol)==false);
                    //printArr(TrySol);
                    Poblacion=fillPob(Poblacion,TrySol,i);
                    //Poblacion[i]=TrySol;
                    System.out.print("S:X ::=>");
                    printArr(Poblacion[i]);
                break;
                case 3,4,5,6:
                    System.out.println("RANDOM TRY");
                    for(int h=0;h<maxRandCount;h++){
                        TrySol=RandomSol(NewGS.ReturnArr());
                        if(countSelect(TrySol)<=maxRandCount || (h++==maxRandCount)){
                            h=maxRandCount;
                            Poblacion=fillPob(Poblacion, TrySol, i);
                            System.out.print("S:X ::=>");
                            printArr(Poblacion[i]);
                        }
                    }
                break;
                case 7,8,9:
                System.out.println("MAX NODOS");
                    do{
                        NCount++;
                        TrySol=maxVecinos(NewGS.ReturnArr(), NCount);
                    }while(!isValid(TrySol));
                    Poblacion=fillPob(Poblacion,TrySol,i);
                    //Poblacion[i]=TrySol;
                    printArr(Poblacion[i]);
                break;
            } 
        }
        //Población inicial
        printPoblación(Poblacion);
 
        //Algoritmo genético
        int fin = 0;
        double probabilidad_mutacion = 0.3;
        while(fin < 1000){
            System.out.println("Ejecutando algoritmo genetico");
            //elitista
            Nodos p1[] = seleccionp1(Poblacion);
            Nodos p2[] = seleccionp2(Poblacion,p1);
            //System.out.println("selP1: ");
            //printArr(p1);
            //System.out.println("selP2: ");
            //printArr(p2);
            //System.out.println("Padres escogidos:");
            //en un punto
            Nodos hijos[][] = cruzamiento(p1,p2,NewGS.ReturnArr(),NewGS.ReturnArr());
            Nodos Newpoblacion[][] = mutacion(probabilidad_mutacion,hijos,Poblacion);
            //Poblacion=Newpoblacion;
            fin++;
        }
        printPoblación(Poblacion);
    }

    public static Nodos[] seleccionp1(Nodos poblacion[][]) 
    {
        Random rand = new Random();
        double costo=0; 
        double auxmin=81;

        int n = rand.nextInt(9);
        //System.out.print("p1 rand:");
        //printArr(poblacion[n]);
        Nodos[] padre=poblacion[n];

        for(int i = 0 ; i < poblacion.length; i++){
            costo=getSolCost(poblacion[i]);
            if(costo < auxmin){
                        auxmin = costo;
                        padre = poblacion[i];
            }
        }
        //System.out.print("p1: ");
        //printArr(padre);
        return padre;
    }



    public static Nodos[] seleccionp2(Nodos poblacion[][],Nodos p1[]) 
    {
        Random rand = new Random();
        double costo=0; 
        double auxmin=81;
        
        int n = rand.nextInt(9);
        //System.out.print("p2 rand:");
        //printArr(poblacion[n]);
        Nodos[] padre=poblacion[n];
        for(int i = 0 ; i < poblacion.length; i++){
            if(p1 != poblacion[i]){
                costo=getSolCost(poblacion[i]);
                if(costo < auxmin){
                    auxmin = costo;
                    padre = poblacion[i];
                }
            }
        }
        //System.out.print("p2: ");
        //printArr(padre);
        return padre;
    }


    public static Nodos[][] cruzamiento(Nodos p1[],Nodos p2[],Nodos bruto1[],Nodos bruto2[])
    {
        //cambiar este numero
        int punto_cruzamiento = (int )(Math.random() * 36);
        Nodos hijos[][] = new Nodos[2][36];

        Nodos h1[]=cleanNode(bruto1);
        Nodos h2[]=cleanNode(bruto2);
        //Nodos h1[] = p1;
        //Nodos h2[] = p2;
        for(int i = 0 ; i < h1.length ; i++)
        {
            h1[i].select=p1[i].select;
            h2[i].select=p2[i].select;
            //System.out.print(i);
        } 

        

        //System.out.print("h1:");
        //printArr(h1);
        //System.out.print("h2:");
        //printArr(h2);

        for(int i = punto_cruzamiento ; i < h1.length ; i++)
        {
            h1[i].select = p2[i].select;
        } 
        for(int i = punto_cruzamiento ; i < h2.length ; i++)
        {
            h2[i].select = p1[i].select;
        } 
        //System.out.println("After Cruzamiento");
        //printArr(h1);printArr(h2);
        //System.out.println("h1 es:"+isValid(h1)+" & h2 es:"+ isValid(h2));

        hijos[0]=h1;
        hijos[1]=h2;
        return hijos;
    }


    public static Nodos[][] mutacion(double probabilidad_mutacion,Nodos[][] hijos,Nodos[][] poblacion)
    {
        Random rand = new Random();
        double mutarh1 = (double) Math.random();
        double mutarh2 = (double) Math.random();
        Nodos[] h1=hijos[0];
        Nodos[] h2=hijos[1];
        System.out.println("Mutar con:");
        printArr(hijos[0]);printArr(hijos[1]);
        
        if(mutarh1 < probabilidad_mutacion)
        {
            //muta esta posicion 
            int cambio = rand.nextInt(36);
            //esta posicion se multiplica por el dato en esa posicion             
            if(h1[cambio].select==true){
                h1[cambio].select=false;
            }else{
                h1[cambio].select=true;
            }
            //System.out.println("nuevo numero = "+h1[posicion]);
            System.out.println("Nueva mutación:");
            printArr(h1);
        }
        else
        {
                System.out.println("no muta");
        }


        if(mutarh2 < probabilidad_mutacion)
        {
            //muta esta posicion 
            int cambio = (int) Math.random()*38;
            //esta posicion se multiplica por el dato en esa posicion             
            if(h2[cambio].select==true){
                h2[cambio].select=false;
            }else{
                h2[cambio].select=true;
            }
            //System.out.println("nuevo numero = "+h1[posicion]);
            System.out.println("Nueva mutación:");
            printArr(h2);
        }
        else
        {
                System.out.println("no muta");
        }
        
        if (isValid(h1)){
            double minc=0;
            int delInd=200;
            for(int k=0;k<poblacion.length;k++){
                if (getSolCost(poblacion[k])>minc){
                    minc=getSolCost(poblacion[k]);
                    delInd=k;
                }
            }
            if(delInd!=200){
                poblacion=fillPob(poblacion,h1,delInd);
            }
        }
        if (isValid(h2)){
            double minc=0;
            int delInd=200;
            for(int k=0;k<poblacion.length;k++){
                if (getSolCost(poblacion[k])>minc){
                    minc=getSolCost(poblacion[k]);
                    delInd=k;
                }
            }
            if(delInd!=200){
                poblacion=fillPob(poblacion,h2,delInd);
            }
        }

        return poblacion;
    }

    //DEVUELVE UNA SOLUCIÓN
    //Minimización de costo con heurística del mas liviano, retorna un arreglo con nodos marcados mientras el total no supere un máximo
    public static Nodos[] minCosto(Nodos[] Arr,double max){
        int lenghtN=Arr.length;
        double TotalCost=0;
        int nodeCount=0;
        //Limpiar el arreglo por si acaso
        for (int i=0;i<lenghtN;i++){
            Arr[i].select=false;
        }
        while(TotalCost<max){
            //System.out.println("Iterando...");
            double CurrentCost=10;
            int SelectNode=200;
            for (int i=0;i<lenghtN;i++){
                if (Arr[i].Costo<CurrentCost && Arr[i].select==false){
                    CurrentCost=Arr[i].Costo;
                    SelectNode=i;
                    TotalCost=TotalCost+CurrentCost;
                }
            }
            if(SelectNode!=200)
            {
                //System.out.println("Id seleccionado:"+Arr[SelectNode].Id);
                Arr[SelectNode].select=true;
                nodeCount++;
                //System.out.println(Arr[SelectNode].select);
            }
            //System.out.println("Cantidad select:"+nodeCount);
        }
        //System.out.println("Total Costo:"+TotalCost);
        return Arr;
    }

    //Comprueba que un arreglo solución sea válido - True si la solución es válida
    public static boolean isValid(Nodos[] Arr){
        for (int i=0;i<Arr.length;i++){
            Arr[i].cubierto=false;
        }
        for (int i=0;i<Arr.length;i++){
            if (Arr[i].select==true){
                for(int j=0;j<Arr[i].Vecinos.length;j++){
                    Arr=putCover(Arr, Arr[i].Vecinos[j]);
                }
            }
        }

        for (int i=0;i<Arr.length;i++){
            if (Arr[i].cubierto==false){
                //System.out.println("Sol no valida");
                return false;
            }
        }
        //System.out.println("Sol valida");
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
        //Limpiar el arreglo por si acaso
        for (int i=0;i<lenghtN;i++){
            Arr[i].select=false;
        }
        Random rand = new Random();
        for(int k=0;k<max;k++){
            int CurrentNCount=0;
            int SelectNode=200;
            for (int i=0;i<lenghtN;i++){
                if (Arr[i].Vecinos.length>CurrentNCount && Arr[i].select!=true){
                    CurrentNCount=Arr[i].Vecinos.length;
                    if(rand.nextInt(100)<70){
                        SelectNode=i;
                    }else{
                        do{
                            SelectNode=rand.nextInt(35);
                        }while(Arr[SelectNode].select==true);
                    }
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
        //Limpiar el arreglo por si acaso
        for (int i=0;i<Arr.length;i++){
            Arr[i].select=false;
        }
        
        Random rand = new Random();
        while (!isValid(Arr)){
            int random=rand.nextInt(Arr.length);
            Arr[random].select=true;
        }
        return Arr;
    }

    public static void printPoblación(Nodos[][] Poblacion){
        for (int i=0;i<Poblacion.length;i++){
            System.out.print("P:"+i+ " ::=>");
            printArr(Poblacion[i]);
            /*
            for(int k=0;k<Poblacion[i].length;k++){
                if(Poblacion[i][k]==null){
                    //System.out.println(i+","+k+" es null uwu");
                }else{
                    if (Poblacion[i][k].select==true){
                        System.out.print(Poblacion[i][k].Id + " ");
                    }
                }
            }
            System.out.println(" ");
            */
        } 
    }

    public static void printArr(Nodos[] Arr){
        double count=0;
        for (int i=0;i<Arr.length;i++){
            if (Arr[i]==null){break;}
            if(Arr[i].select==true){
                System.out.print(Arr[i].Id + " ");
                count=count+Arr[i].Costo;
            }
        }
        System.out.print(" Costo Total Solución="+count);
        System.out.println("");
    }

    public static double getSolCost(Nodos[] Arr){
        double count=0;
        for (int i=0;i<Arr.length;i++){
            if (Arr[i]==null){break;}
            if(Arr[i].select==true){
                //System.out.print(Arr[i].Id + " ");
                count=count+Arr[i].Costo;
            }
        }
        //System.out.print(" Costo Total Solución="+count);
        //System.out.println("");
        return count;
    }

    public static int getSolLenght(Nodos[] Arr){
        int count=0;
        for (int i=0;i<Arr.length;i++){
            if (Arr[i]==null){break;}
            if(Arr[i].select==true){
                //System.out.print(Arr[i].Id + " ");
                count=count++;
            }
        }
        //System.out.print(" Costo Total Solución="+count);
        //System.out.println("");
        return count;
    }

    public static int countSelect(Nodos[] Arr){
        int count=0;
        for (int i=0;i<Arr.length;i++){
            if(Arr[i].select==true){
                count++;
            }
        }
        return count;
    }

    public static Nodos[] cleanNode(Nodos[] Arr){
        for (int i=0;i<Arr.length;i++){
            Arr[i].select=false;
        }
        return Arr;
    }

    public static void clean(){
        Runtime garbage = Runtime.getRuntime();
        garbage.gc();
    }

    public static Nodos[][] fillPob(Nodos[][] G,Nodos[] Node,int index){
        for (int i=0;i<Node.length;i++){
            int Id=Node[i].Id;
            double Costo=Node[i].Costo;
            int Vecinos[]=Node[i].Vecinos;
            boolean select=Node[i].select;
            boolean cubierto=Node[i].cubierto;
            G[index][i]=new Nodos(Id, Costo, Vecinos, select, cubierto);
        }
        return G;
    }

    public static boolean equalSol(Nodos[] Arr1,Nodos[] Arr2){
        for(int i=0)
    }
}

