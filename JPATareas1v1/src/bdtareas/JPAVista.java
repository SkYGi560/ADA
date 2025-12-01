package bdtareas;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.text.SimpleDateFormat;

public class JPAVista {

    // ******************************************
    // verLibros usando NamedQuery
    // ******************************************
    public static List<Tareas> findTareasNamedQuery(TareasJpaController dao) {
        EntityManager em = null;
        List<Tareas> lista = null;
        try {
            em = dao.getEntityManager();
            TypedQuery<Tareas> tQuery
                    = em.createNamedQuery("Tareas.findAll", Tareas.class);
            lista = tQuery.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return lista;
    }

    public static void verTareasNamedQuery(TareasJpaController dao) {
        System.out.println();
        System.out.println("Mostrando todas las tareas (NamedQuery):");

        List<Tareas> lista = findTareasNamedQuery(dao);
        System.out.println("+----+------------+------------------------------------------+------------+");
        System.out.printf("| %-2s | %-10s | %-40s | %-10s |\n", "ID", "Fecha", "Descripcion", "Completada");
        System.out.println("+----+------------+------------------------------------------+------------+");
        for (Tareas tarea : lista) {
            System.out.printf("| %-2s | %-10s | %-21s | %-10s |\n", tarea.getCodigo(),new SimpleDateFormat("dd/MM/yyyy").format(tarea.getFecha()),tarea.getDescripcion(),tarea.getCompletada() ? "Si" : "No");
        }
    }

}
