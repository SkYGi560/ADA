package seriestv;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    public static EntityManagerFactory emf = null;

    public static EntityManagerFactory getEntityManagerFactory() {
        try {
            if (emf == null) {
                emf = Persistence.createEntityManagerFactory("JpaSeries4v1PU");
            }
        } catch (Throwable t) {
            System.out.println("Error al iniciar el Entity Manager factory");
            t.printStackTrace();
            throw new ExceptionInInitializerError();
        }
        return emf;
    }
    
}
