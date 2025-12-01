package bdseries;

import app.SeriesJpaController;
import java.util.List;
import jakarta.persistence.EntityManager;

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

    public static void verSeriesController(SeriesJpaController dao) {
        System.out.println();
        System.out.println("Mostrando todos los libros (NamedQuery):");

        List<Series> lista = findSeriesController(dao);
        System.out.println("+----+----------------------+------------+----------+");
        System.out.printf("| %2s | %-20s | %-10s | %-8s |\n","ID","Titulo","TV","Duracion");
        System.out.println("+----+----------------------+------------+----------+");
        for (Series libro : lista) {
            System.out.printf("| %-2s | %-20s | %-10s | %-8s |\n",libro.getCodigo(),libro.getTitulo(),libro.getTv(),libro.getDuracion());
        }
    }

}
