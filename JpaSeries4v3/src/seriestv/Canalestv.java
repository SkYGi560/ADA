/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package seriestv;

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
import java.util.Collection;

/**
 *
 * @author alumno
 */
@Entity
@Table(name = "canalestv")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Canalestv.findAll", query = "SELECT c FROM Canalestv c"),
    @NamedQuery(name = "Canalestv.findByCodigo", query = "SELECT c FROM Canalestv c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "Canalestv.findByNombre", query = "SELECT c FROM Canalestv c WHERE c.nombre = :nombre")})
public class Canalestv implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 35)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "canaltv")
    private Collection<Series> seriesCollection;

    public Canalestv() {
    }

    public Canalestv(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Canalestv)) {
            return false;
        }
        Canalestv other = (Canalestv) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "seriestv.Canalestv[ codigo=" + codigo + " ]";
    }
    
}
