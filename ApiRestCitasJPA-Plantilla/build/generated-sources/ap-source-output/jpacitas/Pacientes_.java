package jpacitas;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import jpacitas.Medicos;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-02-07T22:19:46", comments="EclipseLink-4.0.2.v20230616-r3bfa6ac6ddf76d7909adc5ea7ecaa47c02c007ed")
@StaticMetamodel(Pacientes.class)
@SuppressWarnings({"rawtypes", "deprecation"})
public class Pacientes_ { 

    public static volatile SingularAttribute<Pacientes, LocalDate> fecha;
    public static volatile SingularAttribute<Pacientes, String> motivo;
    public static volatile SingularAttribute<Pacientes, String> hora;
    public static volatile SingularAttribute<Pacientes, String> centro;
    public static volatile SingularAttribute<Pacientes, Medicos> medico;
    public static volatile SingularAttribute<Pacientes, Integer> numcita;
    public static volatile SingularAttribute<Pacientes, String> nombre;
    public static volatile SingularAttribute<Pacientes, String> dni;
    public static volatile SingularAttribute<Pacientes, Integer> idreg;

}