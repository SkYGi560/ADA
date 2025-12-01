/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package seriestv;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "series")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Series.findAll", query = "SELECT s FROM Series s"),
    @NamedQuery(name = "Series.findByCodigo", query = "SELECT s FROM Series s WHERE s.codigo = :codigo"),
    @NamedQuery(name = "Series.findByTitulo", query = "SELECT s FROM Series s WHERE s.titulo = :titulo"),
    @NamedQuery(name = "Series.findByDuracion", query = "SELECT s FROM Series s WHERE s.duracion = :duracion")})
public class Series implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Size(max = 60)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "duracion")
    private int duracion;
    @JoinTable(name = "participaciones", joinColumns = {
        @JoinColumn(name = "serie", referencedColumnName = "codigo")}, inverseJoinColumns = {
        @JoinColumn(name = "actor", referencedColumnName = "idactor")})
    @ManyToMany
    private Collection<Actores> actoresCollection;
    @JoinColumn(name = "canaltv", referencedColumnName = "codigo")
    @ManyToOne
    private Canalestv canaltv;

    public Series() {
    }

    public Series(Integer codigo) {
        this.codigo = codigo;
    }

    public Series(Integer codigo, int duracion) {
        this.codigo = codigo;
        this.duracion = duracion;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    @XmlTransient
    public Collection<Actores> getActoresCollection() {
        return actoresCollection;
    }

    public void setActoresCollection(Collection<Actores> actoresCollection) {
        this.actoresCollection = actoresCollection;
    }

    public Canalestv getCanaltv() {
        return canaltv;
    }

    public void setCanaltv(Canalestv canaltv) {
        this.canaltv = canaltv;
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
        if (!(object instanceof Series)) {
            return false;
        }
        Series other = (Series) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "seriestv.Series[ codigo=" + codigo + " ]";
    }
    
}
