/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author alumno
 */
public class UD2Ejer608 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Dime el nombre del fichero que quieres que lea: ");
        String nombreFichero = sc.nextLine();
        File archivo = new File("./datos/"+nombreFichero+".txt");

        // Apertura del fichero y creación de BufferedReader
        try (var br = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(archivo), StandardCharsets.UTF_8))) {

            // Lectura del fichero
            String linea;
            ArrayList lista = new ArrayList<String>();
            while ((linea = br.readLine()) != null) {
                lista.add(linea);
            }
            String palabraABuscar = "";
            do {                
                System.out.println("Dime una palabra a buscar en el fichero(no introduzcas nada para finalizar): ");
                palabraABuscar = sc.nextLine();
                Boolean esta = false;
                for(var fila : lista){
                    if(!palabraABuscar.equals("") && fila.toString().contains(palabraABuscar)){
                        System.out.println(fila);
                        esta = true;
                    }
                }
                if(!esta && !palabraABuscar.equals("")){
                    System.out.println("No se ha encontrado esta palabra en el fichero");
                }
            } while (!palabraABuscar.equals(""));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
