package menu;

import bdseries.SeriesJpaController;
import bdseries.JPAUtil;
import bdseries.JPAVista;
import java.util.InputMismatchException;
import java.util.Scanner;
import jakarta.persistence.EntityManagerFactory;
import packUtils.Colores;
import packUtils.UtilString;
import static packUtils.UtilString.*;

public class Menu {

    public static void mostrar() {

        try (EntityManagerFactory emf = JPAUtil.getEntityManagerFactory()) {
            SeriesJpaController dao = new SeriesJpaController(emf);

            Scanner sn = new Scanner(System.in);
            boolean salir = false;
            int opcion; //Guardaremos la opcion del usuario

            while (!salir) {
                linea();
                System.out.print(Colores.FONDO_BLANCO);
                System.out.println(Colores.LETRA_AZUL + "1. Mostrar series");
                System.out.println(Colores.LETRA_AZUL + UtilString.strRepetir('-', 20));
                System.out.println(Colores.LETRA_AZUL + "2. Añadir serie");
                System.out.println(Colores.LETRA_AZUL + UtilString.strRepetir('-', 20));
                System.out.println(Colores.LETRA_AZUL + "3. Buscar serie por titulo");
                System.out.println(Colores.LETRA_AZUL + UtilString.strRepetir('-', 20));
                System.out.println(Colores.LETRA_AZUL + "4. Buscar serie por titulo");
                System.out.println(Colores.LETRA_AZUL + UtilString.strRepetir('-', 20));
                System.out.println(Colores.LETRA_AZUL + "0. Salir");
                System.out.println(Colores.LETRA_AZUL + UtilString.strRepetir('-', 20));

                try {

                    System.out.print(Colores.LETRA_NEGRO + "Escribe una de las opciones: ");
                    opcion = sn.nextInt();

                    linea();
                    switch (opcion) {
                        case 1 ->
                            JPAVista.verSeriesController(dao,1);
                        case 2 -> 
                            JPAVista.addSerie(dao);
                        case 3 ->
                            JPAVista.verSeriesController(dao,2);
                        case 4 ->
                            JPAVista.verSeriesController(dao,3);
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
