/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ud0ejer03;

import java.util.Scanner;

/**
 *
 * @author alumno
 */
public class UD0Ejer03 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Introduce un numero entero: ");
        Scanner sc = new Scanner(System.in);
        int numeroMes = sc.nextInt();
        String mes = "";
        switch(numeroMes){
            case 1 -> mes = "Enero";
            case 2 -> mes = "Febrero";
            case 3 -> mes = "Marzo";
            case 4 -> mes = "Abril";
            case 5 -> mes = "Mayo";
            case 6 -> mes = "Junio";
            case 7 -> mes = "Julio";
            case 8 -> mes = "Agosto";
            case 9 -> mes = "Septiembre";
            case 10 -> mes = "Octubre";
            case 11 -> mes = "Noviembre";
            case 12 -> mes = "Diciembre";
            default -> System.out.println("Entrada no valida");
        }
        System.out.println("El mes es " + mes);
    }
    
}
