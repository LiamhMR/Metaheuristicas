package invetigacio;


public class Nodos {
    int Id;
    double Costo;
    int Vecinos[] = {};
    //Select significa que el nodo está seleccionado como parte de la solución
    boolean select;
    //Cubierto significa que está siendo cubierto por un vecino seleccionado
    boolean cubierto;
    //Propiedades

    Nodos(int id,double costo,int vecinos[]){
        this.Id = id;
        this.Costo = costo;
        this.Vecinos = vecinos;
        this.select=false;
        this.cubierto=false;
    }

    Nodos(int id,double costo,int vecinos[],Boolean sel,boolean cover){
        this.Id = id;
        this.Costo = costo;
        this.Vecinos = vecinos;
        this.select=sel;
        this.cubierto=cover;
    }

    Nodos(){
        Id=0;
        Costo=0;
        select=false;
        cubierto=false;
    }
}
