package seriestv;

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
    public static List<Canalestv> findCanalesController(CanalestvJpaController dao) {
        EntityManager em = null;
        List<Canalestv> lista = null;
        try {
            lista = dao.findCanalestvEntities();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return lista;
    }

    public static void verSeriesController(SeriesJpaController dao, Integer tipoConsulta) {
        System.out.println();
        List<Series> lista = null;
        switch (tipoConsulta) {
            case 1 ->
                lista = findSeriesController(dao);
            /*case 2 ->
                lista = findSerieByLikeTitulo(dao);
            case 3 ->
                lista = findSeriesByDuracionInterval(dao);*/
        }
        System.out.println("+----+----------------------+------------+----------+");
        System.out.printf("| %2s | %-20s | %-10s | %-8s |\n", "ID", "Titulo", "TV", "Duracion");
        System.out.println("+----+----------------------+------------+----------+");
        for (Series serie : lista) {
            System.out.printf("| %-2d | %-20s | %-10s | %-8s |\n", serie.getCodigo(),serie.getTitulo(),serie.getCanaltv().getNombre(),serie.getDuracion());
        }
        System.out.println("+----------------------+----------------------+");
    }
    public static void verCanalesController(CanalestvJpaController dao, Integer tipoConsulta){
        System.out.println();
        List<Canalestv> lista = null;
        switch(tipoConsulta){
            case 1 -> 
                lista = findCanalesController(dao);
        }
        System.out.println("+----------------------+----------------------+");
        System.out.printf("| %-20s | %-20s |\n", "Codigo", "Nombre");
        System.out.println("+----------------------+----------------------+");
        for (Canalestv canal : lista) {
            System.out.printf("| %-20s | %-20s |\n", canal.getCodigo(),canal.getNombre());
        }
        System.out.println("+----------------------+----------------------+");
    }

}
