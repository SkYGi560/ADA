/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.io.Serializable;

/**
 *
 * @author joel
 */
public class Domicilio implements Serializable{
    String calle;
    Integer numero;
    Integer codigo;
    String poblacion;

    public Domicilio() {
    }

    public Domicilio(String calle, Integer numero, Integer codigo, String poblacion) {
        this.calle = calle;
        this.numero = numero;
        this.codigo = codigo;
        this.poblacion = poblacion;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    @Override
    public String toString() {
        return "Domicilio{" + "calle=" + calle + ", numero=" + numero + ", codigo=" + codigo + ", poblacion=" + poblacion + '}';
    }
    
}
