/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ud0ejer01;

import java.util.Scanner;

/**
 *
 * @author alumno
 */
public class UD0Ejer01 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Dime tu nombre: ");
        Scanner sc = new Scanner(System.in);
        String nombre = sc.nextLine();
        
        for(int i = 0; i < 5; i++){
            System.out.println("Hola, "+ nombre);
        }
    }
    
}
