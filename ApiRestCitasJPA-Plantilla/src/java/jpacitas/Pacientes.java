
package jpacitas;

import jakarta.json.bind.annotation.JsonbTransient;
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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "pacientes")
@NamedQueries({
    @NamedQuery(name = "Pacientes.findAll", query = "SELECT p FROM Pacientes p"),
    @NamedQuery(name = "Pacientes.findByIdreg", query = "SELECT p FROM Pacientes p WHERE p.idreg = :idreg"),
    @NamedQuery(name = "Pacientes.findByDni", query = "SELECT p FROM Pacientes p WHERE p.dni = :dni"),
    @NamedQuery(name = "Pacientes.findByNombre", query = "SELECT p FROM Pacientes p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Pacientes.findByCentro", query = "SELECT p FROM Pacientes p WHERE p.centro = :centro"),
    @NamedQuery(name = "Pacientes.findByNumcita", query = "SELECT p FROM Pacientes p WHERE p.numcita = :numcita"),
    @NamedQuery(name = "Pacientes.findByFecha", query = "SELECT p FROM Pacientes p WHERE p.fecha = :fecha"),
    @NamedQuery(name = "Pacientes.findByHora", query = "SELECT p FROM Pacientes p WHERE p.hora = :hora"),
    @NamedQuery(name = "Pacientes.findByMotivo", query = "SELECT p FROM Pacientes p WHERE p.motivo = :motivo")})
public class Pacientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idreg")
    private Integer idreg;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "dni")
    private String dni;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "nombre")
    private String nombre;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "centro")
    private String centro;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "numcita")
    private int numcita;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    private LocalDate fecha;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "hora")
    private String hora;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "motivo")
    private String motivo;
    
    @JoinColumn(name = "medico", referencedColumnName = "numcolegiado")
    @ManyToOne(optional = false)
    @JsonbTransient
    private Medicos medico;

    public Pacientes() {
    }

    public Pacientes(Integer idreg) {
        this.idreg = idreg;
    }

    public Pacientes(Integer idreg, String dni, String nombre, String centro, int numcita, LocalDate fecha, String hora, String motivo) {
        this.idreg = idreg;
        this.dni = dni;
        this.nombre = nombre;
        this.centro = centro;
        this.numcita = numcita;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
    }

    public Integer getIdreg() {
        return idreg;
    }

    public void setIdreg(Integer idreg) {
        this.idreg = idreg;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public int getNumcita() {
        return numcita;
    }

    public void setNumcita(int numcita) {
        this.numcita = numcita;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Medicos getMedico() {
        return medico;
    }

    public void setMedico(Medicos medico) {
        this.medico = medico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idreg != null ? idreg.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pacientes)) {
            return false;
        }
        Pacientes other = (Pacientes) object;
        if ((this.idreg == null && other.idreg != null) || (this.idreg != null && !this.idreg.equals(other.idreg))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpacitas.Pacientes[ idreg=" + idreg + " ]";
    }
    
}
