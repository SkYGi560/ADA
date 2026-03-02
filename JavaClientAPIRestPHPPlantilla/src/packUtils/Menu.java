
package packUtils;

import java.util.InputMismatchException;
import java.util.Scanner;
import app.ApiRestMain;
import static packUtils.UtilString.*;

public class Menu {

    public static void Mostrar() {
        
        Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario    
        
        while (!salir) {
            Linea();
            System.out.print(Colores.FONDO_BLANCO);
            System.out.println(Colores.LETRA_AZUL + "1. Obtener todos los registros");
            System.out.println(Colores.LETRA_AZUL + "2. Obtener un registro");
            System.out.println(Colores.LETRA_AZUL + "3. INSERTAR un registro");
            System.out.println(Colores.LETRA_AZUL + "4. ACTUALIZAR un registro");
            System.out.println(Colores.LETRA_AZUL + "5. ELIMINAR un registro");
            System.out.println(Colores.LETRA_AZUL + UtilString.StrRepetir('-',20));
            System.out.println(Colores.LETRA_AZUL + "0. Salir");
            System.out.println(Colores.LETRA_AZUL + UtilString.StrRepetir('-',20));

            try {
                
                System.out.print(Colores.LETRA_NEGRO + "Escribe una de las opciones: ");
                opcion = sn.nextInt();

                Linea();
                switch (opcion) {
                    case 1:
                        ApiRestMain.getTodos();
                        break;  
                    case 2:
                        ApiRestMain.getOne();
                        break;  
                    case 3:
                        ApiRestMain.insert();
                        break;  
                    case 4:
                        ApiRestMain.update();
                        break;  
                    case 5:
                        ApiRestMain.delete();
                        break;  

                        
                    // **************************
                    // SALIR
                    // **************************
                    case 0:
                        salir = true;
                        System.out.println("Terminado");
                        Linea();
                        break;                        
                    default:
                        System.out.println(Colores.LETRA_ROJO + Colores.FONDO_AMARILLO + "Opción no válida");
                        break;
                }
            } catch (InputMismatchException e) {
                Linea();
                System.out.println(Colores.LETRA_ROJO + Colores.FONDO_AMARILLO + "Debe insertar un número");
                sn.next();
            }
        }               
              
    }
    
    
    
    
}
