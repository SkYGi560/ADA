/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bdcloud;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
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
@Table(name = "plataforma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Plataforma.findAll", query = "SELECT p FROM Plataforma p"),
    @NamedQuery(name = "Plataforma.findByIdPform", query = "SELECT p FROM Plataforma p WHERE p.idPform = :idPform"),
    @NamedQuery(name = "Plataforma.findByNomPform", query = "SELECT p FROM Plataforma p WHERE p.nomPform = :nomPform"),
    @NamedQuery(name = "Plataforma.findByWebPform", query = "SELECT p FROM Plataforma p WHERE p.webPform = :webPform")})
public class Plataforma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "id_pform")
    private String idPform;
    @Size(max = 30)
    @Column(name = "nom_pform")
    private String nomPform;
    @Size(max = 50)
    @Column(name = "web_pform")
    private String webPform;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "plataforma")
    private Collection<Infraestructura> infraestructuraCollection;

    public Plataforma() {
    }

    public Plataforma(String idPform) {
        this.idPform = idPform;
    }

    public String getIdPform() {
        return idPform;
    }

    public void setIdPform(String idPform) {
        this.idPform = idPform;
    }

    public String getNomPform() {
        return nomPform;
    }

    public void setNomPform(String nomPform) {
        this.nomPform = nomPform;
    }

    public String getWebPform() {
        return webPform;
    }

    public void setWebPform(String webPform) {
        this.webPform = webPform;
    }

    @XmlTransient
    public Collection<Infraestructura> getInfraestructuraCollection() {
        return infraestructuraCollection;
    }

    public void setInfraestructuraCollection(Collection<Infraestructura> infraestructuraCollection) {
        this.infraestructuraCollection = infraestructuraCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPform != null ? idPform.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Plataforma)) {
            return false;
        }
        Plataforma other = (Plataforma) object;
        if ((this.idPform == null && other.idPform != null) || (this.idPform != null && !this.idPform.equals(other.idPform))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bdcloud.Plataforma[ idPform=" + idPform + " ]";
    }
    
}
