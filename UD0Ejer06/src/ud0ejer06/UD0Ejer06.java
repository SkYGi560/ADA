/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ud0ejer06;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author alumno
 */
public class UD0Ejer06 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("--------------------------------------------");
        int numero = 0;
        Scanner sc = new Scanner(System.in);
        ArrayList listaNumeros = new ArrayList<Integer>();
        while(numero >= 0){
            System.out.print("Introduce un numero entero (negativo para terminar): ");
            numero = sc.nextInt();
            if(numero >=0){
                listaNumeros.add(numero);
            }
        }
        System.out.println("--------------------------------------------");
        int numeroBuscar = 0;
        while(numeroBuscar >= 0){
            System.out.print("Introduce un numero a buscar (negativo para terminar): ");
            numeroBuscar = sc.nextInt();
            if(listaNumeros.contains(numeroBuscar)){
                System.out.println(numeroBuscar+" aparece");
            }
            else{
                System.out.println(numeroBuscar+" no existe");
            }
        }
        System.out.println("--------------------------------------------");
        System.out.println("Valores introducidos: ");
        for(var valor : listaNumeros){
            System.out.println(valor);
        }
        
    }
    
}
