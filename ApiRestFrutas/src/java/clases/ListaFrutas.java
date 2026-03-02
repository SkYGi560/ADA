
package clases;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaFrutas implements Serializable {
    ArrayList<Frutas> fruta = new ArrayList<>();

    public ListaFrutas() {
    }

    public ArrayList<Frutas> getLista() {
        return fruta;
    }

    public void setLista(ArrayList<Frutas> frutas) {
        this.fruta = frutas;
    }

    @Override
    public String toString() {
        return "{ \"frutas\" : " + fruta + "}";
    }
    
    public void add(Frutas fruit) {
        fruta.add(fruit);
    }
    
    public boolean contains(Frutas fruit) {
        return fruta.contains(fruit);
    }
    
    public boolean remove(Frutas fruit) {
        return fruta.remove(fruit);
    }

    public int size() {
        return fruta.size();
    }
    
}
