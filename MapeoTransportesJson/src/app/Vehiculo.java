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
    String tipo;
    Integer capacidadCarga;
    String matricula;

    public Vehiculo() {
    }

    public Vehiculo(String tipo, Integer capacidadCarga, String matricula) {
        this.tipo = tipo;
        this.capacidadCarga = capacidadCarga;
        this.matricula = matricula;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getCapacidadCarga() {
        return capacidadCarga;
    }

    public void setCapacidadCarga(Integer capacidadCarga) {
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
        return "Vehiculo{" + "tipo=" + tipo + ", capacidadCarga=" + capacidadCarga + ", matricula=" + matricula + '}';
    }
    
    
}
