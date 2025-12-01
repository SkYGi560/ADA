/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

/**
 *
 * @author alumno
 */
public class Conductor {
    String transportista;
    String tipoLicencia;
    Integer experiencia;
    Vehiculo vehiculo;

    public Conductor() {
    }

    public Conductor(String transportista, String tipoLicencia, Integer experiencia, Vehiculo vehiculo) {
        this.transportista = transportista;
        this.tipoLicencia = tipoLicencia;
        this.experiencia = experiencia;
        this.vehiculo = vehiculo;
    }

    public String getTransportista() {
        return transportista;
    }

    public void setTransportista(String transportista) {
        this.transportista = transportista;
    }

    public String getTipoLicencia() {
        return tipoLicencia;
    }

    public void setTipoLicencia(String tipoLicencia) {
        this.tipoLicencia = tipoLicencia;
    }

    public Integer getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(Integer experiencia) {
        this.experiencia = experiencia;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    @Override
    public String toString() {
        return "Conductor{" + "transportista=" + transportista + ", tipoLicencia=" + tipoLicencia + ", experiencia=" + experiencia + ", vehiculo=" + vehiculo + '}';
    }
    
}
