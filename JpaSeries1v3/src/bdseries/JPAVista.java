package bdseries;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.Scanner;

public class JPAVista {

    // ******************************************
    // verLibros usando NamedQuery
    // ******************************************
    public static List<Series> findSeriesController(SeriesJpaController dao) {
        EntityManager em = null;
        List<Series> lista = null;
        try {
            lista = dao.findSeriesEntities();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return lista;
    }

    public static void addSerie(SeriesJpaController dao) {
        System.out.println("===========================");
        System.out.println("Añadir una serie");
        System.out.println("===========================");
        String titulo, cadena;
        Integer duracion;
        // Java - Leer datos
        Scanner teclado = new Scanner(System.in);
        System.out.print("Introduzca el título: ");
        titulo = teclado.nextLine();
        System.out.print("Introduzca la cadena de TV: ");
        cadena = teclado.nextLine();
        System.out.print("Introduzca la duracion: ");
        duracion = Integer.valueOf(teclado.nextLine());
        // Java - Cargar datos en un objeto Libro
        Series serie = new Series();
        serie.setCodigo(0);
        serie.setTitulo(titulo);
        serie.setTv(cadena);
        serie.setDuracion(duracion);
        // JPA - Guardar datos del objeto directamente en la BD
        dao.create(serie);
    }

    public static List<Series> findSerieByLikeTitulo(SeriesJpaController dao) {
        EntityManager em = null;
        Scanner sc = new Scanner(System.in);
        String titulo;
        try {
            em = dao.getEntityManager();
            System.out.println("Dime el titulo: ");
            titulo = "%" + sc.nextLine() + "%";
            TypedQuery<Series> tQuery
                    = em.createNamedQuery("Series.findByLikeTitulo", Series.class);
            tQuery.setParameter("titulo", titulo);
            return tQuery.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public static void verSeriesController(SeriesJpaController dao) {
        System.out.println();
        System.out.println("Mostrando todas las series:");

        List<Series> lista = findSeriesController(dao);
        System.out.println("+----+----------------------+------------+----------+");
        System.out.printf("| %2s | %-20s | %-10s | %-8s |\n", "ID", "Titulo", "TV", "Duracion");
        System.out.println("+----+----------------------+------------+----------+");
        for (Series serie : lista) {
            System.out.printf("| %-2d | %-20s | %-10s | %-8s |\n", serie.getCodigo(), serie.getTitulo(), serie.getTv(), serie.getDuracion());
        }
    }
    public static void verSeriesLikeTituloController(SeriesJpaController dao) {
        System.out.println();
        System.out.println("Mostrando las series que contienen lo introducido:");

        List<Series> lista = findSerieByLikeTitulo(dao);
        System.out.println("+----+----------------------+------------+----------+");
        System.out.printf("| %2s | %-20s | %-10s | %-8s |\n", "ID", "Titulo", "TV", "Duracion");
        System.out.println("+----+----------------------+------------+----------+");
        for (Series serie : lista) {
            System.out.printf("| %-2d | %-20s | %-10s | %-8s |\n", serie.getCodigo(), serie.getTitulo(), serie.getTv(), serie.getDuracion());
        }
    }

}
