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
public class Alumno implements Serializable{
    Integer id;
    String nombre;
    String localidad;
    ArrayList<Float> notas;

    public Alumno() {
    }

    public Alumno(Integer id, String nombre, String localidad, ArrayList<Float> notas) {
        this.id = id;
        this.nombre = nombre;
        this.localidad = localidad;
        this.notas = notas;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLocalidad() {
        return localidad;
    }

    public ArrayList<Float> getNotas() {
        return notas;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public void setNotas(ArrayList<Float> notas) {
        this.notas = notas;
    }

    @Override
    public String toString() {
        return "Alumno{" + "id=" + id + ", nombre=" + nombre + ", localidad=" + localidad + ", notas=" + notas + '}';
    }
    
}
