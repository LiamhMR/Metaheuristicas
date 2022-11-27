package invetigacio;


public class Nodos {
    int Id;
    float Costo;
    int Vecinos[] = {};
    //Select significa que el nodo está seleccionado como parte de la solución
    boolean select;
    //Cubierto significa que está siendo cubierto por un vecino seleccionado
    boolean cubierto;
    //Propiedades

    Nodos(int id,float costo,int vecinos[]){
        this.Id = id;
        this.Costo = costo;
        this.Vecinos = vecinos;
    }

    Nodos(){
        select=false;
        cubierto=false;
    }
}
