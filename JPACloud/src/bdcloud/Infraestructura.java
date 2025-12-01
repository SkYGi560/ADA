/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bdcloud;

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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author alumno
 */
@Entity
@Table(name = "infraestructura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Infraestructura.findAll", query = "SELECT i FROM Infraestructura i"),
    @NamedQuery(name = "Infraestructura.findByIdService", query = "SELECT i FROM Infraestructura i WHERE i.idService = :idService"),
    @NamedQuery(name = "Infraestructura.findByDesService", query = "SELECT i FROM Infraestructura i WHERE i.desService = :desService"),
    @NamedQuery(name = "Infraestructura.findByTipo", query = "SELECT i FROM Infraestructura i WHERE i.tipo = :tipo"),
    @NamedQuery(name = "Infraestructura.findByPrecioMes", query = "SELECT i FROM Infraestructura i WHERE i.precioMes = :precioMes"),
    @NamedQuery(name = "Infraestructura.findByCreada", query = "SELECT i FROM Infraestructura i WHERE i.creada = :creada")})
public class Infraestructura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_service")
    private Integer idService;
    @Size(max = 30)
    @Column(name = "des_service")
    private String desService;
    @Size(max = 10)
    @Column(name = "tipo")
    private String tipo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio_mes")
    private Double precioMes;
    @Column(name = "creada")
    @Temporal(TemporalType.DATE)
    private Date creada;
    @JoinColumn(name = "plataforma", referencedColumnName = "id_pform")
    @ManyToOne(optional = false)
    private Plataforma plataforma;

    public Infraestructura() {
    }

    public Infraestructura(Integer idService) {
        this.idService = idService;
    }

    public Integer getIdService() {
        return idService;
    }

    public void setIdService(Integer idService) {
        this.idService = idService;
    }

    public String getDesService() {
        return desService;
    }

    public void setDesService(String desService) {
        this.desService = desService;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getPrecioMes() {
        return precioMes;
    }

    public void setPrecioMes(Double precioMes) {
        this.precioMes = precioMes;
    }

    public Date getCreada() {
        return creada;
    }

    public void setCreada(Date creada) {
        this.creada = creada;
    }

    public Plataforma getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(Plataforma plataforma) {
        this.plataforma = plataforma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idService != null ? idService.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Infraestructura)) {
            return false;
        }
        Infraestructura other = (Infraestructura) object;
        if ((this.idService == null && other.idService != null) || (this.idService != null && !this.idService.equals(other.idService))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bdcloud.Infraestructura[ idService=" + idService + " ]";
    }
    
}
