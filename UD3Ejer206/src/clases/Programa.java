package clases;

import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author joel
 */
public class Programa implements Serializable {

    Integer codigo;
    String nombre;
    String carpeta_instalacion;
    Integer ocupacion;

    public Programa() {
    }

    public Programa(Integer codigo, String nombre, String carpeta_instalacion, Integer ocupacion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.carpeta_instalacion = carpeta_instalacion;
        this.ocupacion = ocupacion;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCarpetaInstalacion() {
        return carpeta_instalacion;
    }

    public void setCarpetaInstalacion(String carpeta_instalacion) {
        this.carpeta_instalacion = carpeta_instalacion;
    }

    public Integer getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(Integer ocupacion) {
        this.ocupacion = ocupacion;
    }

    @Override
    public String toString() {
        return "Programa{" + "codigo=" + codigo + ", nombre=" + nombre + ", carpetaInstalacion=" + carpeta_instalacion + ", ocupacion=" + ocupacion + '}';
    }

}
