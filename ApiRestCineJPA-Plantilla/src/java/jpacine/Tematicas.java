
package jpacine;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;


@Entity
@Table(name = "tematicas")
@NamedQueries({
    @NamedQuery(name = "Tematicas.findAll", query = "SELECT t FROM Tematicas t"),
    @NamedQuery(name = "Tematicas.findByCodigo", query = "SELECT t FROM Tematicas t WHERE t.codigo = :codigo"),
    @NamedQuery(name = "Tematicas.findByDescripcion", query = "SELECT t FROM Tematicas t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "Tematicas.findBySeguidores", query = "SELECT t FROM Tematicas t WHERE t.seguidores = :seguidores")})
public class Tematicas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    
    @Size(max = 50)
    @Column(name = "descripcion")
    private String descripcion;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "seguidores")
    private int seguidores;
    
    @OneToMany(mappedBy = "tematica")
    // @JsonbTransient
    private Collection<Peliculas> peliculasCollection;
    
    
    

    public Tematicas() {
    }

    public Tematicas(Integer codigo) {
        this.codigo = codigo;
    }

    public Tematicas(Integer codigo, int seguidores) {
        this.codigo = codigo;
        this.seguidores = seguidores;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(int seguidores) {
        this.seguidores = seguidores;
    }

    public Collection<Peliculas> getPeliculasCollection() {
        return peliculasCollection;
    }

    public void setPeliculasCollection(Collection<Peliculas> peliculasCollection) {
        this.peliculasCollection = peliculasCollection;
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
        if (!(object instanceof Tematicas)) {
            return false;
        }
        Tematicas other = (Tematicas) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpacine.Tematicas[ codigo=" + codigo + " ]";
    }
    
}
