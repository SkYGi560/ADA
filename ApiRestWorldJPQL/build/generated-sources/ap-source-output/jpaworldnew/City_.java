package jpaworldnew;

import jakarta.persistence.metamodel.CollectionAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;
import jpaworldnew.Country;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-01-16T08:59:02", comments="EclipseLink-4.0.2.v20230616-r3bfa6ac6ddf76d7909adc5ea7ecaa47c02c007ed")
@StaticMetamodel(City.class)
@SuppressWarnings({"rawtypes", "deprecation"})
public class City_ { 

    public static volatile SingularAttribute<City, String> district;
    public static volatile SingularAttribute<City, Country> countrycode;
    public static volatile SingularAttribute<City, String> name;
    public static volatile CollectionAttribute<City, Country> countryCollection;
    public static volatile SingularAttribute<City, Integer> id;
    public static volatile SingularAttribute<City, Integer> population;

}