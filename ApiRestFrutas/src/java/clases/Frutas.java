
package clases;

import java.io.Serializable;
import java.util.Objects;

public class Frutas implements Serializable {
    String nombre;
    int kg;

    public Frutas() {
    }

    public Frutas(String nombre, int kg) {
        this.nombre = nombre;
        this.kg = kg;
    }
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getKg() {
        return kg;
    }

    public void setKg(int kg) {
        this.kg = kg;
    }

    @Override
    public String toString() {
        return "{ \"nombre\" : \"" + nombre + "\", \"kg\": " + kg + "}";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.nombre);
        hash = 29 * hash + this.kg;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Frutas other = (Frutas) obj;
        if (this.kg != other.kg) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }
    
 
    
    
}
