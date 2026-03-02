package jpaworldnew;

import jakarta.persistence.metamodel.CollectionAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;
import jpaworldnew.City;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-01-16T08:59:02", comments="EclipseLink-4.0.2.v20230616-r3bfa6ac6ddf76d7909adc5ea7ecaa47c02c007ed")
@StaticMetamodel(Country.class)
@SuppressWarnings({"rawtypes", "deprecation"})
public class Country_ { 

    public static volatile SingularAttribute<Country, String> continent;
    public static volatile SingularAttribute<Country, String> code2;
    public static volatile SingularAttribute<Country, City> capital;
    public static volatile SingularAttribute<Country, String> code;
    public static volatile SingularAttribute<Country, String> localname;
    public static volatile SingularAttribute<Country, Float> gnp;
    public static volatile SingularAttribute<Country, String> headofstate;
    public static volatile CollectionAttribute<Country, City> cityCollection;
    public static volatile SingularAttribute<Country, Integer> population;
    public static volatile SingularAttribute<Country, Float> lifeexpectancy;
    public static volatile SingularAttribute<Country, String> governmentform;
    public static volatile SingularAttribute<Country, String> name;
    public static volatile SingularAttribute<Country, Short> indepyear;
    public static volatile SingularAttribute<Country, Float> gnpold;
    public static volatile SingularAttribute<Country, String> region;
    public static volatile SingularAttribute<Country, Float> surfacearea;

}