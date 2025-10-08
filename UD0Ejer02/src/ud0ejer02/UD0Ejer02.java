/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ud0ejer02;

import java.util.Scanner;

/**
 *
 * @author alumno
 */
public class UD0Ejer02 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Dime un numero entero: ");
        Scanner sc = new Scanner(System.in);
        int numero = sc.nextInt();
        int factorial = 1;
        for(int i = 1; i <= numero; i++){
            factorial *= i;
        }
        System.out.println("El factorial de "+numero+"es"+factorial);
    }
    
}
