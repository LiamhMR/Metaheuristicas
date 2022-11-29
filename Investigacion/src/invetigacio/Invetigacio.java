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
        //boolean execute=true;

        GetSheet NewGS=new GetSheet();//FUNCIONA
        Nodos Poblacion[][]=new Nodos[10][38];

        /**Soluciones Aleatorias y con heurísticas - SE GENERA LA PRIMERA POBLACIÓN DE SOLUCIONES
        * 30% : Con los nodos más baratos con costo máximo total de maximocosto.
        * 40% : Soluciones generadas aleatoriamente.
        * 30% : Soluciones generadas con un máximo de NCount nodos.
        **/
        Random rand = new Random();
        Nodos[] TrySol;
        for (int i=0;i<10;i++){
            int random=rand.nextInt(9);
            double maximocosto=30;
            int NCount=25;
            int maxRandCount=30;
            switch(random)
            {
                case 0,1,2:
                    System.out.println("MIN TRY");
                    //Mientras no se encuentren soluciones se flexibiliza el costo máximo total
                    do{
                        maximocosto++; 
                        TrySol=minCosto(NewGS.ReturnArr(),maximocosto);
                    }while(isValid(TrySol)==false);
                    Poblacion=fillPob(Poblacion,TrySol,i);
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
                    //Mientras no se hallen soluciones se flexibiliza la cantidad de nodos máximo.
                    do{
                        NCount++;
                        TrySol=maxNodos(NewGS.ReturnArr(), NCount);
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
        while(fin < 5000){
            System.out.println("[ALGORITMO GENÉTICO ITR="+fin+"]"); //Número de iteración
            //elitista
            Nodos p1[] = seleccionp1(Poblacion);
            Nodos p2[] = seleccionp2(Poblacion,p1);
            Nodos hijos[][] = cruzamiento(p1,p2,NewGS.ReturnArr(),NewGS.ReturnArr());
            Nodos Newpoblacion[][] = mutacion(probabilidad_mutacion,hijos,Poblacion,NewGS.ReturnArr(),NewGS.ReturnArr());
            fin++;
        }
        printPoblación(Poblacion);
    }
    
    //selecciona al primer padre de forma elitista

    public static Nodos[] seleccionp1(Nodos poblacion[][]) 
    {
        Random rand = new Random();
        double costo=0; 
        double auxmin=81;

        int n = rand.nextInt(9);
        Nodos[] padre=poblacion[n];

        for(int i = 0 ; i < poblacion.length; i++){
            costo=getSolCost(poblacion[i]);
            if(costo < auxmin){
                        auxmin = costo;
                        padre = poblacion[i];
            }
        }
        return padre;
    }

    // selecciona al segundo padre de manera elitista

    public static Nodos[] seleccionp2(Nodos poblacion[][],Nodos p1[]) 
    {
        Random rand = new Random();
        double costo=0; 
        double auxmin=81;
        
        int n = rand.nextInt(9);
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
        return padre;
    }
    // se entrega por parametros los padres 1 y 2 y genera el cruzamiento para formar 2 hijos 
    //ademas el cruzamiento se hace en un punto 

    public static Nodos[][] cruzamiento(Nodos p1[],Nodos p2[],Nodos bruto1[],Nodos bruto2[])
    {
        //cambiar este numero
        int punto_cruzamiento = (int )(Math.random() * 36);
        Nodos hijos[][] = new Nodos[2][36];

        Nodos h1[]=cleanNode(bruto1);
        Nodos h2[]=cleanNode(bruto2);

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

    //hace la mutacion de los hijos si corresponde 
    public static Nodos[][] mutacion(double probabilidad_mutacion,Nodos[][] hijos,Nodos[][] poblacion,Nodos[] Bruto1,Nodos[] Bruto2)
    {
        Random rand = new Random();
        double mutarh1 = (double) Math.random();
        double mutarh2 = (double) Math.random();
        Nodos[] h1=hijos[0];
        Nodos[] h2=hijos[1];
        //System.out.println("Mutar con:");
        //printArr(hijos[0]);printArr(hijos[1]);
        
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
        
        //printPoblación(poblacion);

        if (isValid(h1)){
            //DESCARTE
            killNode(h1, Bruto1);

            double minc=0;
            int delInd=200;
            for(int k=0;k<poblacion.length;k++){
                if (getSolCost(poblacion[k])>minc){
                    minc=getSolCost(poblacion[k]);
                    delInd=k;
                }

                if(equalSol(h1,poblacion[k])==true){
                    k=poblacion.length;
                    delInd=200;
                }
            }
            if(delInd!=200){
                poblacion=fillPob(poblacion,h1,delInd);
            }
        }else{
            System.out.println("Solución hijo 1 no válida");
        }
        if (isValid(h2)){
            killNode(h2, Bruto2);
            double minc=0;
            int delInd=200;
            for(int k=0;k<poblacion.length;k++){
                if (getSolCost(poblacion[k])>minc){
                    minc=getSolCost(poblacion[k]);
                    delInd=k;
                }

                if(equalSol(h2,poblacion[k])==true){
                    k=poblacion.length;
                    delInd=200;
                }
            }
            if(delInd!=200 && equalSol(h2,poblacion[delInd])==false){
                poblacion=fillPob(poblacion,h2,delInd);
            }
        }else{
            System.out.println("Solución hijo 2 no válida");
        }

        return poblacion;
    }

    /**
     * Minimización de costo con heurística del mas liviano, retorna un arreglo con nodos marcados mientras el total no supere un máximo
     * @param Arr
     * @param max
     * @return
     */
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
            }
        }
        return Arr;
    }

    /**Comprueba que un arreglo solución sea válido - True si la solución es válida
     * @param Arr -->Solución
     * @return Retorna True: si la solución es válida, False: en otro caso.
     */
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

    /**Marca como cubierto el nodo mencionado
     * @param Arr
     * @param lookId
     * @return Retorna una solución con el id:lookId marcado como cubierto
     */
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

    /**Genera una solución con un máximo de max nodos
     * @param Arr Arreglo con todos los nodos
     * @param max máxima cantidad de nodos
     * @return
     */
    public static Nodos[] maxNodos(Nodos[] Arr,int max){
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

    /**Devuelve una solución aleatoria válida
     * @param Arr
     * @return
     */
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

    
    /** Imprime todas las soluciones de una población.
     * @param Poblacion
     */
    public static void printPoblación(Nodos[][] Poblacion){
        for (int i=0;i<Poblacion.length;i++){
            System.out.print("P:"+i+ " ::=>");
            printArr(Poblacion[i]);
        } 
    }

    /** Imprime los id de los nodos seleccionados de una solución
     * @param Arr
     */
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

    /** Obtener el costo total de una solución
     * @param Arr
     * @return Retorna el costo total de una solución
     */
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

    /**Contador de nodos seleccionados
     * @param Arr
     * @return Retorna un entero con la cantidad de nodos seleccionados dentro del arreglo de nodos
     */
    public static int countSelect(Nodos[] Arr){
        int count=0;
        for (int i=0;i<Arr.length;i++){
            if(Arr[i].select==true){
                count++;
            }
        }
        return count;
    }

    /**
     * @param Arr 
     * @return Retorna una referencia a un arreglo de nodos sin nodos seleccionados
     */
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

    /**
     * @param G Población.
     * @param Node Nodo a añadir a la población.
     * @param index Indice donde debe añadirse.
     * @return Retorna una referencia a la población.
     */
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
    
    /**
     * @param Arr1 Solucion A
     * @param Arr2 Solucion B
     * @return Retorna true si las dos soluciones/arreglo de nodos son el mismo.
     */
    public static boolean equalSol(Nodos[] Arr1,Nodos[] Arr2){
        for(int i=0;i<Arr1.length;i++){
            if(Arr1[i].select!=Arr2[i].select){
                return false;
            }
        }
        return true;
    }
    

    /**
     * REFINADOR DE SOLUCIONES
     * @param Arr -->Solución a refinar
     * @param Bruto -->Elemento auxiliar
     * Descarta nodos que están siendo cubiertos por otros nodos
     */
    public static void killNode(Nodos[] Arr,Nodos[] Bruto){

        for(int i=0;i<Arr.length;i++){
            Bruto[i].select=Arr[i].select;
        }

        
        for(int i=0;i<Bruto.length;i++){
            if(Bruto[i].select==true){
                Bruto[i].select=false;
                if(isValid(Bruto)){
                    i=Bruto.length;
                    Arr[i-1].select=false;
                }else{
                    Bruto[i].select=true;
                }
            }
        }
    }
}

