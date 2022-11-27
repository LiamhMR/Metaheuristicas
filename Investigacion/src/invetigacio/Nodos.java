package invetigacio;


public class Nodos {
    int Id;
    float Costo;
    int Vecinos[] = {};
    //Propiedades

    Nodos(int id,float costo,int vecinos[]){
        this.Id = id;
        this.Costo = costo;
        this.Vecinos = vecinos;
    }

    Nodos(){
    }
}
