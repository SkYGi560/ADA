/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author alumno
 */
public class Equipo implements Serializable {
    String nombre;
    String pais;
    Integer fundacion;
    ArrayList<Piloto> pilotos;

    public Equipo() {
    }

    public Equipo(String nombre, String pais, Integer fundacion, ArrayList<Piloto> pilotos) {
        this.nombre = nombre;
        this.pais = pais;
        this.fundacion = fundacion;
        this.pilotos = pilotos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Integer getFundacion() {
        return fundacion;
    }

    public void setFundacion(Integer fundacion) {
        this.fundacion = fundacion;
    }

    public ArrayList<Piloto> getPilotos() {
        return pilotos;
    }

    public void setPilotos(ArrayList<Piloto> pilotos) {
        this.pilotos = pilotos;
    }

    @Override
    public String toString() {
        return "Equipo{" + "nombre=" + nombre + ", pais=" + pais + ", fundacion=" + fundacion + ", pilotos=" + pilotos + '}';
    }
}
