/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package seriestv;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author alumno
 */
@Entity
@Table(name = "actores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Actores.findAll", query = "SELECT a FROM Actores a"),
    @NamedQuery(name = "Actores.findByIdactor", query = "SELECT a FROM Actores a WHERE a.idactor = :idactor"),
    @NamedQuery(name = "Actores.findByNombre", query = "SELECT a FROM Actores a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Actores.findByApellidos", query = "SELECT a FROM Actores a WHERE a.apellidos = :apellidos")})
public class Actores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "idactor")
    private String idactor;
    @Size(max = 30)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 30)
    @Column(name = "apellidos")
    private String apellidos;
    @ManyToMany(mappedBy = "actoresCollection")
    private Collection<Series> seriesCollection;

    public Actores() {
    }

    public Actores(String idactor) {
        this.idactor = idactor;
    }

    public String getIdactor() {
        return idactor;
    }

    public void setIdactor(String idactor) {
        this.idactor = idactor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @XmlTransient
    public Collection<Series> getSeriesCollection() {
        return seriesCollection;
    }

    public void setSeriesCollection(Collection<Series> seriesCollection) {
        this.seriesCollection = seriesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idactor != null ? idactor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Actores)) {
            return false;
        }
        Actores other = (Actores) object;
        if ((this.idactor == null && other.idactor != null) || (this.idactor != null && !this.idactor.equals(other.idactor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "seriestv.Actores[ idactor=" + idactor + " ]";
    }
    
}
