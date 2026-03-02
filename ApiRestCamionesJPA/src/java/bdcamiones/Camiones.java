/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bdcamiones;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

/**
 *
 * @author alumno
 */
@Entity
@Table(name = "camiones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Camiones.findAll", query = "SELECT c FROM Camiones c"),
    @NamedQuery(name = "Camiones.findByMatricula", query = "SELECT c FROM Camiones c WHERE c.matricula = :matricula"),
    @NamedQuery(name = "Camiones.findByMarca", query = "SELECT c FROM Camiones c WHERE c.marca = :marca"),
    @NamedQuery(name = "Camiones.findByConductor", query = "SELECT c FROM Camiones c WHERE c.conductor = :conductor"),
    @NamedQuery(name = "Camiones.findByCargamaxima", query = "SELECT c FROM Camiones c WHERE c.cargamaxima = :cargamaxima")})
public class Camiones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "matricula")
    private String matricula;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "marca")
    private String marca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "conductor")
    private String conductor;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "cargamaxima")
    private BigDecimal cargamaxima;
    @JsonbTransient
    @OneToMany(mappedBy = "camion")
    private Collection<Cargas> cargasCollection;

    public Camiones() {
    }

    public Camiones(String matricula) {
        this.matricula = matricula;
    }

    public Camiones(String matricula, String marca, String conductor, BigDecimal cargamaxima) {
        this.matricula = matricula;
        this.marca = marca;
        this.conductor = conductor;
        this.cargamaxima = cargamaxima;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public BigDecimal getCargamaxima() {
        return cargamaxima;
    }

    public void setCargamaxima(BigDecimal cargamaxima) {
        this.cargamaxima = cargamaxima;
    }

    @XmlTransient
    public Collection<Cargas> getCargasCollection() {
        return cargasCollection;
    }

    public void setCargasCollection(Collection<Cargas> cargasCollection) {
        this.cargasCollection = cargasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (matricula != null ? matricula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Camiones)) {
            return false;
        }
        Camiones other = (Camiones) object;
        if ((this.matricula == null && other.matricula != null) || (this.matricula != null && !this.matricula.equals(other.matricula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bdcamiones.Camiones[ matricula=" + matricula + " ]";
    }
    
}
