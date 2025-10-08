/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ud0ejer04;

import java.util.Scanner;

/**
 *
 * @author alumno
 */
public class UD0Ejer04 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Dime un numero entero: ");
        Scanner sc = new Scanner(System.in);
        int numeroMes = sc.nextInt();
        String[] lista = {
            "Enero",
            "Febrero",
            "Marzo",
            "Abril",
            "Mayo",
            "Junio",
            "Julio",
            "Agosto",
            "Septiembre",
            "Octubre",
            "Noviembre",
            "Diciembre"
        };
        
        System.out.println("El mes introducido es: "+lista[numeroMes-1]);
    }
    
}
