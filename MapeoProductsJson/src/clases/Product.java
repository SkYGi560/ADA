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
public class Product implements Serializable{
    String art;
    String descrip;
    String categoria;
    ArrayList<String> colores;
    Dimensiones dimensiones;
    Double precio;

    public Product() {
    }

    public Product(String art, String descrip, String categoria, ArrayList<String> colores, Dimensiones dimensiones, Double precio) {
        this.art = art;
        this.descrip = descrip;
        this.categoria = categoria;
        this.colores = colores;
        this.dimensiones = dimensiones;
        this.precio = precio;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public ArrayList<String> getColores() {
        return colores;
    }

    public void setColores(ArrayList<String> colores) {
        this.colores = colores;
    }

    public Dimensiones getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(Dimensiones dimensiones) {
        this.dimensiones = dimensiones;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Product{" + "art=" + art + ", descrip=" + descrip + ", categoria=" + categoria + ", colores=" + colores + ", dimensiones=" + dimensiones + ", precio=" + precio + '}';
    }
    
}
