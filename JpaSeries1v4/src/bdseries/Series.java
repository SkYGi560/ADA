/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bdseries;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author alumno
 */
@Entity
@Table(name = "series")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Series.findAll", query = "SELECT s FROM Series s"),
    @NamedQuery(name = "Series.findByLikeTitulo", query = "SELECT s FROM Series s WHERE s.titulo LIKE :titulo ORDER BY s.titulo"),
    @NamedQuery(name = "Series.findByCodigo", query = "SELECT s FROM Series s WHERE s.codigo = :codigo"),
    @NamedQuery(name = "Series.findByTitulo", query = "SELECT s FROM Series s WHERE s.titulo = :titulo"),
    @NamedQuery(name = "Series.findByTv", query = "SELECT s FROM Series s WHERE s.tv = :tv"),
    @NamedQuery(name = "Series.findByDuracion", query = "SELECT s FROM Series s WHERE s.duracion = :duracion")

})
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
    @Size(max = 35)
    @Column(name = "tv")
    private String tv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "duracion")
    private int duracion;

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

    public String getTv() {
        return tv;
    }

    public void setTv(String tv) {
        this.tv = tv;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
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
        return "bdseries.Series[ codigo=" + codigo + " ]";
    }

}
