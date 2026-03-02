package jpacitas;

import jakarta.persistence.metamodel.CollectionAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;
import jpacitas.Pacientes;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-02-07T22:19:46", comments="EclipseLink-4.0.2.v20230616-r3bfa6ac6ddf76d7909adc5ea7ecaa47c02c007ed")
@StaticMetamodel(Medicos.class)
@SuppressWarnings({"rawtypes", "deprecation"})
public class Medicos_ { 

    public static volatile CollectionAttribute<Medicos, Pacientes> pacientesCollection;
    public static volatile SingularAttribute<Medicos, String> nombre;
    public static volatile SingularAttribute<Medicos, String> especialidad;
    public static volatile SingularAttribute<Medicos, String> numcolegiado;

}