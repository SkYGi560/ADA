package menu;

import java.util.InputMismatchException;
import java.util.Scanner;
import jakarta.persistence.EntityManagerFactory;
import packUtils.Colores;
import packUtils.UtilString;
import static packUtils.UtilString.*;
import seriestv.CanalestvJpaController;
import seriestv.JPAUtil;
import seriestv.JPAVista;
import seriestv.SeriesJpaController;

public class Menu {

    public static void mostrar() {

        try (EntityManagerFactory emf = JPAUtil.getEntityManagerFactory()) {
            SeriesJpaController SeriesDao = new SeriesJpaController(emf);
            CanalestvJpaController CanalesDao = new CanalestvJpaController(emf);

            Scanner sn = new Scanner(System.in);
            boolean salir = false;
            int opcion; //Guardaremos la opcion del usuario

            while (!salir) {
                linea();
                System.out.print(Colores.FONDO_BLANCO);
                System.out.println(Colores.LETRA_AZUL + "1. Mostrar series");
                System.out.println(Colores.LETRA_AZUL + "2. Mostrar canales de TV");
                System.out.println(Colores.LETRA_AZUL + "3. Añadir canal de TV");
                System.out.println(Colores.LETRA_AZUL + "4. Añadir Serie");
                System.out.println(Colores.LETRA_AZUL + UtilString.strRepetir('-', 20));
                System.out.println(Colores.LETRA_AZUL + "0. Salir");
                System.out.println(Colores.LETRA_AZUL + UtilString.strRepetir('-', 20));

                try {

                    System.out.print(Colores.LETRA_NEGRO + "Escribe una de las opciones: ");
                    opcion = sn.nextInt();

                    linea();
                    switch (opcion) {
                        case 1 ->
                            JPAVista.verSeriesController(SeriesDao,1);
                        case 2 -> 
                            JPAVista.verCanalesController(CanalesDao,1);
                        case 3 ->
                            JPAVista.addCanalTV(CanalesDao);
                        case 4 ->
                            JPAVista.addSerie(SeriesDao,CanalesDao);
                        /*case 5 ->
                            JPAVista.mostrarSeriesPorCanal(dao);*/
                        case 0 -> {
                            salir = true;
                            System.out.println("Terminado");
                            linea();
                        }
                        default ->
                            System.out.println(Colores.LETRA_ROJO + Colores.FONDO_AMARILLO + "Opción no válida");
                    }
                    // **************************
                    // SALIR
                    // **************************
                } catch (InputMismatchException e) {
                    linea();
                    System.out.println(Colores.LETRA_ROJO + Colores.FONDO_AMARILLO + "Debe insertar un número");
                    sn.next();
                }
            }
        }

    }

}
