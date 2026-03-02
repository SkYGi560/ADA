/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpadeportes;

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

/**
 *
 * @author alumnosemi
 */
@Entity
@Table(name = "deportes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deportes.findAll", query = "SELECT d FROM Deportes d"),
    @NamedQuery(name = "Deportes.findById", query = "SELECT d FROM Deportes d WHERE d.id = :id"),
    @NamedQuery(name = "Deportes.findByNombre", query = "SELECT d FROM Deportes d WHERE d.nombre = :nombre"),
    @NamedQuery(name = "Deportes.findByNumeroJugadores", query = "SELECT d FROM Deportes d WHERE d.numeroJugadores = :numeroJugadores"),
    @NamedQuery(name = "Deportes.findByAnyoInicio", query = "SELECT d FROM Deportes d WHERE d.anyoInicio = :anyoInicio")})
public class Deportes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero_jugadores")
    private int numeroJugadores;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anyo_inicio")
    private int anyoInicio;
    @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Categorias categoriaId;

    public Deportes() {
    }

    public Deportes(Integer id) {
        this.id = id;
    }

    public Deportes(Integer id, String nombre, int numeroJugadores, int anyoInicio) {
        this.id = id;
        this.nombre = nombre;
        this.numeroJugadores = numeroJugadores;
        this.anyoInicio = anyoInicio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroJugadores() {
        return numeroJugadores;
    }

    public void setNumeroJugadores(int numeroJugadores) {
        this.numeroJugadores = numeroJugadores;
    }

    public int getAnyoInicio() {
        return anyoInicio;
    }

    public void setAnyoInicio(int anyoInicio) {
        this.anyoInicio = anyoInicio;
    }

    public Categorias getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Categorias categoriaId) {
        this.categoriaId = categoriaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deportes)) {
            return false;
        }
        Deportes other = (Deportes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpadeportes.Deportes[ id=" + id + " ]";
    }
    
}
