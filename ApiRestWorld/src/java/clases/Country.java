/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author alumno
 */
public class Country {

    String code;
    String name;
    String continent;
    Double surfacearea;
    City capital;
    ArrayList<City> ciudades;

    public Country() {
    }

    public Country(String code, String name, String continent, Double surfacearea, City capital, ArrayList<City> ciudades) {
        this.code = code;
        this.name = name;
        this.continent = continent;
        this.surfacearea = surfacearea;
        this.capital = capital;
        this.ciudades = ciudades;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public Double getSurfacearea() {
        return surfacearea;
    }

    public void setSurfacearea(Double surfacearea) {
        this.surfacearea = surfacearea;
    }

    public City getCapital() {
        return capital;
    }

    public void setCapital(City capital) {
        this.capital = capital;
    }

    public ArrayList<City> getCiudades() {
        return ciudades;
    }

    public void setCiudades(ArrayList<City> ciudades) {
        this.ciudades = ciudades;
    }

    @Override
    public String toString() {
        return "Country{" + "code=" + code + ", name=" + name + ", continent=" + continent + ", surfacearea=" + surfacearea + ", capital=" + capital + ", ciudades=" + ciudades + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.code);
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.continent);
        hash = 59 * hash + Objects.hashCode(this.surfacearea);
        hash = 59 * hash + Objects.hashCode(this.capital);
        hash = 59 * hash + Objects.hashCode(this.ciudades);
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
        final Country other = (Country) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.continent, other.continent)) {
            return false;
        }
        if (!Objects.equals(this.surfacearea, other.surfacearea)) {
            return false;
        }
        if (!Objects.equals(this.capital, other.capital)) {
            return false;
        }
        return Objects.equals(this.ciudades, other.ciudades);
    }
    
}
