/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.io.Serializable;

/**
 *
 * @author alumno
 */
public class Vehiculo implements Serializable {
    Integer id;
    String tipo;
    Float capacidadCarga;
    String matricula;

    public Vehiculo() {
    }

    public Vehiculo(Integer id, String tipo, Float capacidadCarga, String matricula) {
        this.id = id;
        this.tipo = tipo;
        this.capacidadCarga = capacidadCarga;
        this.matricula = matricula;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Float getCapacidadCarga() {
        return capacidadCarga;
    }

    public void setCapacidadCarga(Float capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    @Override
    public String toString() {
        return "Vehiculo{" + "id=" + id + ", tipo=" + tipo + ", capacidadCarga=" + capacidadCarga + ", matricula=" + matricula + '}';
    }

    
}
