/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.io.Serializable;

/**
 *
 * @author alumno
 */
public class Dimensiones implements Serializable{
    Integer largo;
    Integer ancho;
    Integer espesor;

    public Dimensiones() {
    }

    public Dimensiones(Integer largo, Integer ancho, Integer espesor) {
        this.largo = largo;
        this.ancho = ancho;
        this.espesor = espesor;
    }

    public Integer getLargo() {
        return largo;
    }

    public void setLargo(Integer largo) {
        this.largo = largo;
    }

    public Integer getAncho() {
        return ancho;
    }

    public void setAncho(Integer ancho) {
        this.ancho = ancho;
    }

    public Integer getEspesor() {
        return espesor;
    }

    public void setEspesor(Integer espesor) {
        this.espesor = espesor;
    }

    @Override
    public String toString() {
        return largo + " x " + ancho + " x " + espesor;
    }
    
}
