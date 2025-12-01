package bdtareas;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Scanner;

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

    public static void addTarea(TareasJpaController dao) {
        System.out.println("===========================");
        System.out.println("Añadir una serie");
        System.out.println("===========================");
        String fecha, descripcion, completadaString;
        Boolean completada = false;
        try {
            // Java - Leer datos
            Scanner teclado = new Scanner(System.in);
            System.out.print("Introduzca la fecha(AAAA-MM-DD): ");
            fecha = teclado.nextLine();
            System.out.print("Introduzca la descripcion: ");
            descripcion = teclado.nextLine();
            System.out.print("Introduzca si esta completada (SI/NO): ");
            completadaString = teclado.nextLine();
            // Java - Cargar datos en un objeto Libro
            Tareas tarea = new Tareas();
            tarea.setCodigo(0);
            Date fechaDate = Date.from(LocalDate.parse(fecha).atStartOfDay(ZoneId.systemDefault()).toInstant());
            tarea.setFecha(fechaDate);
            tarea.setDescripcion(descripcion);
            if (completadaString.equals("SI")) {
                completada = true;
            }
            if (completadaString.equals("NO")) {
                completada = false;
            }
            tarea.setCompletada(completada);
            // JPA - Guardar datos del objeto directamente en la BD
            dao.create(tarea);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void verTareasNamedQuery(TareasJpaController dao) {
        System.out.println();
        System.out.println("Mostrando todas las tareas (NamedQuery):");

        List<Tareas> lista = findTareasNamedQuery(dao);
        System.out.println("+----+------------+------------------------------------------+------------+");
        System.out.printf("| %2s | %-10s | %-40s | %-10s |\n", "ID", "Fecha", "Descripcion", "Completada");
        System.out.println("+----+------------+------------------------------------------+------------+");
        for (Tareas tarea : lista) {
            System.out.printf("| %2s | %-10s | %-40s | %-10s |\n", tarea.getCodigo(), new SimpleDateFormat("dd/MM/yyyy").format(tarea.getFecha()), tarea.getDescripcion(), tarea.getCompletada() ? "Si" : "No");
        }
    }

}
