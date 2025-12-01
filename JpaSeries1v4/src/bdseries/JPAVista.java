package bdseries;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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
    public static List<Series> findSeriesByDuracionInterval(SeriesJpaController dao) {
        EntityManager em = null;
        Integer desde,hasta;
        Scanner sc = new Scanner(System.in);
        try {
            em = dao.getEntityManager();
            System.out.println("Introduzca el intervalo de duracion ->");
            System.out.print("Desde: ");
            desde = Integer.valueOf(sc.nextLine());
            System.out.print("\nHasta: ");
            hasta = Integer.valueOf(sc.nextLine());
            Query query = em.createQuery("SELECT s FROM Series s WHERE s.duracion BETWEEN :min AND :max");
            query.setParameter("min", desde);
            query.setParameter("max", hasta);
            List<Series> lista = query.getResultList();
            return lista;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        
    }
    public static void verSeriesController(SeriesJpaController dao, Integer tipoConsulta) {
        System.out.println();
        List<Series> lista = null;
        switch(tipoConsulta){
            case 1 ->
                lista = findSeriesController(dao);
            case 2 -> 
                lista = findSerieByLikeTitulo(dao);
            case 3 ->
                lista = findSeriesByDuracionInterval(dao);
        }
        System.out.println("+----+----------------------+------------+----------+");
        System.out.printf("| %2s | %-20s | %-10s | %-8s |\n", "ID", "Titulo", "TV", "Duracion");
        System.out.println("+----+----------------------+------------+----------+");
        for (Series serie : lista) {
            System.out.printf("| %-2d | %-20s | %-10s | %-8s |\n", serie.getCodigo(), serie.getTitulo(), serie.getTv(), serie.getDuracion());
        }
    }


}
