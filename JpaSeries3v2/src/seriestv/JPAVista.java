package seriestv;

import java.util.List;
import jakarta.persistence.EntityManager;
import java.util.Scanner;
import packUtils.Colores;

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

    public static void addCanalTV(CanalestvJpaController dao) {
        System.out.println("===========================");
        System.out.println("Añadir un canal de TV");
        System.out.println("===========================");
        String codigo, nombre;
        Boolean existe = false;
        // Java - Leer datos
        Scanner teclado = new Scanner(System.in);
        System.out.print("Introduzca el codigo del canal de TV: ");
        codigo = teclado.nextLine();
        System.out.print("Introduzca el titulo: ");
        nombre = teclado.nextLine();
        // Java - Cargar datos en un objeto Libro
        Canalestv canalTV = new Canalestv();
        canalTV.setCodigo(codigo);
        canalTV.setNombre(nombre);
        // JPA - Guardar datos del objeto directamente en la BD
        for (Canalestv canal : dao.findCanalestvEntities()) {
            if (canal.getCodigo().equals(canalTV.getCodigo())) {
                existe = true;
                System.out.println(Colores.LETRA_ROJO+ "Ya exsite un canal con el codigo "+canal.getCodigo());
            }
        }
        if (!existe) {
            try {
                dao.create(canalTV);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
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
            System.out.printf("| %-2d | %-20s | %-10s | %-8s |\n", serie.getCodigo(), serie.getTitulo(), serie.getCanaltv().getNombre(), serie.getDuracion());
        }
        System.out.println("+----------------------+----------------------+");
    }

    public static void verCanalesController(CanalestvJpaController dao, Integer tipoConsulta) {
        System.out.println();
        List<Canalestv> lista = null;
        switch (tipoConsulta) {
            case 1 ->
                lista = findCanalesController(dao);
        }
        System.out.println("+----------------------+--------------------------------+");
        System.out.printf("| %-20s | %-30s |\n", "Codigo", "Nombre");
        System.out.println("+----------------------+--------------------------------+");
        for (Canalestv canal : lista) {
            System.out.printf("| %-20s | %-30s |\n", canal.getCodigo(), canal.getNombre());
        }
        System.out.println("+----------------------+--------------------------------+");
    }

}
