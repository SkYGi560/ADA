/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ud0ejer07;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author alumno
 */
public class UD0Ejer07 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("--------------------------------------------");
        String palabra = "";
        Scanner sc = new Scanner(System.in);
        ArrayList listaNumeros = new ArrayList<String>();
        while(!palabra.equals("fin")){
            System.out.print("Introduce una palabra (fin para terminar): ");
            palabra = sc.nextLine();
            if(!palabra.equals("fin")){
                listaNumeros.add(palabra);
            }
        }
        System.out.println("--------------------------------------------");
        String palabraBuscar = "";
        while(!palabraBuscar.equals("fin")){
            System.out.print("Introduce la palabra a buscar (fin para terminar): ");
            palabraBuscar = sc.nextLine();
            if(listaNumeros.contains(palabraBuscar)){
                System.out.println(palabraBuscar+" aparece");
            }
            else{
                System.out.println(palabraBuscar+" no existe");
            }
        }
        System.out.println("--------------------------------------------");
        System.out.println("Valores introducidos: ");
        for(var valor : listaNumeros){
            System.out.println(valor);
        }
    }
    
}
