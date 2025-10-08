/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ud0ejer05;

import java.util.Scanner;

/**
 *
 * @author alumno
 */
public class UD0Ejer05 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Dime un numero entero: ");
        Scanner sc = new Scanner(System.in);
        int numero = sc.nextInt();
        if(esPrimo(numero)){
            System.out.println("El numero "+numero+" es primo");
        }
        else{
            System.out.println("El numero "+ numero+" NO es primo");
        }
        if(esPalindromo(numero)){
            System.out.println("El numero "+numero+" es palindromo");
        }
        else{
            System.out.println("El numero "+numero+" NO es palindromo");
        }
        if(esPalindromo(numero) && esPrimo(numero)){
            System.out.println("El numero "+numero+" es primo y palindromo");
        }
        
    }
    // FUNCIÓN - esPrimo
    public static boolean esPrimo(int numero) {
        int contador = 2;
        boolean primo=true;
        while ((primo) && (contador!=numero)){
            if ( (numero % contador) == 0) {
                primo = false;
            }
            contador++;
        }
        return primo;
    }
    // FUNCIÓN - esPalindromo
    public static boolean esPalindromo(int numero) {
        String sPalabra=String.valueOf(numero);
        int inc = 0;
        int des = sPalabra.length()-1;
        boolean palindromo = true;
        while ((inc<des) && (palindromo)){
            if (sPalabra.charAt(inc)==sPalabra.charAt(des)){
                inc++;
                des--;
            } else {
                palindromo = false;
            }
        }
        return palindromo;
    }
    
}
