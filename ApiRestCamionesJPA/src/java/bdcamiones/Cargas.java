/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bdcamiones;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author alumno
 */
@Entity
@Table(name = "cargas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cargas.findAll", query = "SELECT c FROM Cargas c"),
    @NamedQuery(name = "Cargas.findByIdcarga", query = "SELECT c FROM Cargas c WHERE c.idcarga = :idcarga"),
    @NamedQuery(name = "Cargas.findByFecha", query = "SELECT c FROM Cargas c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "Cargas.findByProducto", query = "SELECT c FROM Cargas c WHERE c.producto = :producto"),
    @NamedQuery(name = "Cargas.findByCajas", query = "SELECT c FROM Cargas c WHERE c.cajas = :cajas"),
    @NamedQuery(name = "Cargas.findByPesototal", query = "SELECT c FROM Cargas c WHERE c.pesototal = :pesototal"),
    @NamedQuery(name = "Cargas.findEspecial", query = "SELECT car.camion.matricula, car.camion.conductor, COUNT(car.camion.matricula), SUM(car.pesototal)FROM Cargas car GROUP BY car.camion.matricula, car.camion.conductor")})

public class Cargas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcarga")
    private Integer idcarga;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    private LocalDate fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "producto")
    private String producto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cajas")
    private int cajas;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "pesototal")
    private BigDecimal pesototal;
    @JoinColumn(name = "camion", referencedColumnName = "matricula")
    @ManyToOne
    private Camiones camion;

    public Cargas() {
    }

    public Cargas(Integer idcarga) {
        this.idcarga = idcarga;
    }

    public Cargas(Integer idcarga, LocalDate fecha, String producto, int cajas, BigDecimal pesototal, Camiones camion) {
        this.idcarga = idcarga;
        this.fecha = fecha;
        this.producto = producto;
        this.cajas = cajas;
        this.pesototal = pesototal;
        this.camion = camion;
    }

    public Integer getIdcarga() {
        return idcarga;
    }

    public void setIdcarga(Integer idcarga) {
        this.idcarga = idcarga;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCajas() {
        return cajas;
    }

    public void setCajas(int cajas) {
        this.cajas = cajas;
    }

    public BigDecimal getPesototal() {
        return pesototal;
    }

    public void setPesototal(BigDecimal pesototal) {
        this.pesototal = pesototal;
    }

    public Camiones getCamion() {
        return camion;
    }

    public void setCamion(Camiones camion) {
        this.camion = camion;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.idcarga);
        hash = 37 * hash + Objects.hashCode(this.fecha);
        hash = 37 * hash + Objects.hashCode(this.producto);
        hash = 37 * hash + this.cajas;
        hash = 37 * hash + Objects.hashCode(this.pesototal);
        hash = 37 * hash + Objects.hashCode(this.camion);
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
        final Cargas other = (Cargas) obj;
        if (this.cajas != other.cajas) {
            return false;
        }
        if (!Objects.equals(this.producto, other.producto)) {
            return false;
        }
        if (!Objects.equals(this.idcarga, other.idcarga)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        if (!Objects.equals(this.pesototal, other.pesototal)) {
            return false;
        }
        return Objects.equals(this.camion, other.camion);
    }

    @Override
    public String toString() {
        return "Cargas{" + "idcarga=" + idcarga + ", fecha=" + fecha + ", producto=" + producto + ", cajas=" + cajas + ", pesototal=" + pesototal + ", camion=" + camion + '}';
    }
    
}
