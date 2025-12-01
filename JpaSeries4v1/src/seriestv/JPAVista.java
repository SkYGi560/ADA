package seriestv;

import app.exceptions.NonexistentEntityException;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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
        if (dao.findCanalestv(codigo).getCodigo().equals(canalTV.getCodigo())) {
            existe = true;
            System.out.println(Colores.LETRA_ROJO + "Ya existe un canal con el código " + canalTV.getCodigo());
        }

        if (!existe) {
            try {
                dao.create(canalTV);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void addSerie(SeriesJpaController daoSeries, CanalestvJpaController daoCanales) {
        System.out.println("===========================");
        System.out.println("Añadir una serie");
        System.out.println("===========================");
        String titulo, codigo;
        Integer duracion;
        // Java - Leer datos
        Scanner teclado = new Scanner(System.in);
        System.out.print("Introduzca el titulo: ");
        titulo = teclado.nextLine();
        System.out.print("Introduzca la duracion: ");
        duracion = Integer.valueOf(teclado.nextLine());
        System.out.println("Introduzca el código del canal TV: ");
        codigo = teclado.nextLine();
        daoCanales.findCanalestv(codigo);
        // Java - Cargar datos en un objeto Libro
        Series serie = new Series();
        serie.setTitulo(titulo);
        serie.setDuracion(duracion);

        // JPA - Guardar datos del objeto directamente en la BD
        Canalestv canal = daoCanales.findCanalestv(codigo);

        if (canal != null) {
            serie.setCanaltv(canal);
            daoSeries.create(serie);
        } else {
            System.out.println("El Canal " + codigo + " no existe");
        }
    }

    public static void modificarCanal(CanalestvJpaController daoCanales) {
        System.out.println("===========================");
        System.out.println("Modificar un canal de TV");
        System.out.println("===========================");
        String codCanal = null;
        String nombre;
        Canalestv canalModifica = null;
        Scanner teclado = new Scanner(System.in);

        while (canalModifica == null) {
            System.out.print("Introduzca el codigo del canal TV: ");
            codCanal = teclado.nextLine();
            canalModifica = daoCanales.findCanalestv(codCanal);
            if (canalModifica != null) {
                System.out.println("El canal de TV a modificar es '" + canalModifica.getNombre() + "'");
            } else {
                System.out.println("El Canal " + codCanal + " no existe");
            }
        }
        System.out.print("Introduzca el nombre: ");
        nombre = teclado.nextLine();

        canalModifica.setNombre(nombre);
        try {
            daoCanales.edit(canalModifica);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void modificarSerie(SeriesJpaController daoSeries) {
        System.out.println("===========================");
        System.out.println("Modificar una Serie");
        System.out.println("===========================");
        Integer codSerie, duracion;
        String titulo;
        Series SerieModifica = null;
        Scanner teclado = new Scanner(System.in);

        while (SerieModifica == null) {
            System.out.print("Introduzca el codigo de la serie: ");
            codSerie = Integer.valueOf(teclado.nextLine());
            SerieModifica = daoSeries.findSeries(codSerie);
            if (SerieModifica != null) {
                System.out.println("La Serie a modificar es '" + SerieModifica.getTitulo() + "' con " + SerieModifica.getDuracion() + " minutos de duracion");
            } else {
                System.out.println("La Serue " + codSerie + " no existe");
            }
        }
        System.out.print("Introduzca el titulo: ");
        titulo = teclado.nextLine();
        System.out.println("Introduzca la duracion");
        duracion = Integer.valueOf(teclado.nextLine());

        SerieModifica.setTitulo(titulo);
        SerieModifica.setDuracion(duracion);
        try {
            daoSeries.edit(SerieModifica);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void eliminarSerie(SeriesJpaController daoSeries) {
        System.out.println("===========================");
        System.out.println("Eliminar una Serie");
        System.out.println("===========================");
        Integer codSerie = null;
        Series SerieModifica = null;
        Scanner teclado = new Scanner(System.in);
        String sn;

        while (SerieModifica == null) {
            System.out.print("Introduzca el codigo de la serie: ");
            codSerie = Integer.valueOf(teclado.nextLine());
            SerieModifica = daoSeries.findSeries(codSerie);
            if (SerieModifica != null) {
                System.out.println("La Serie a eliminar es '" + SerieModifica.getTitulo() + "' con " + SerieModifica.getDuracion() + " minutos de duracion");
            } else {
                System.out.println("La Serie " + codSerie + " no existe");
            }
        }
        System.out.println("¿Confirma su eliminacion?");
        sn = teclado.nextLine();
        switch (sn.toUpperCase()) {
            case "S" -> {
                try {
                    daoSeries.destroy(codSerie);
                    System.out.println("Serie eliminada");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            case "N" ->
                System.out.println("No se elimino la serie elegida");
            default ->
                System.out.println("La entrada que has introducido no es valida");
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
        System.out.println("+----+----------------------+------------+----------+");
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
