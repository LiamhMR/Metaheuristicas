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
        // TODO code application logic here
        boolean fin = true;
        
        Random probabilidad_mutacion = new Random();

        while(!fin){
            //elitista
            seleccion();
            //en un punto
            cruzamiento();
            //aleatoria
            mutacion(probabilidad_mutacion);
        }
        
    }

    public static void seleccion() 
    {

    }


    public static void cruzamiento()
    {
    
    }


    public static void mutacion(Random probabilidad_mutacion)
    {
    
    }


}

