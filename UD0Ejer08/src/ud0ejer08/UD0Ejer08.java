/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ud0ejer08;

import java.util.Scanner;

/**
 *
 * @author alumno
 */
public class UD0Ejer08 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ListaDeDatos lista = new ListaDeDatos();
        String palabra = "";
        Scanner sc = new Scanner(System.in);
        
        System.out.println("----------------------------------------------");
        while(!palabra.equals("fin")){
            System.out.print("Introduce una palabra (fin para terminar): ");
            palabra = sc.nextLine();
            if(!palabra.equals("fin")){
                lista.Incluir(palabra);
            }
        }
        palabra = "";
        System.out.println("----------------------------------------------");
        while(!palabra.equals("fin")){
            System.out.print("Introduce una palabra (fin para terminar): ");
            palabra = sc.nextLine();
            if(lista.Contiene(palabra)){
                System.out.println(palabra+" Existe");
            }
            else{
                System.out.println(palabra+" NO existe");
            }
        }
        System.out.println("----------------------------------------------");
        System.out.println("Valores instroducidos:");
        lista.mostrarDatosOrdenados();
    }
    
}
