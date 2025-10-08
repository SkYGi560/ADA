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
import java.util.Scanner;

/**
 *
 * @author alumno
 */
public class UD2Ejer606 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Dime el nombre del fichero a buscar");
        Scanner sc = new Scanner(System.in);
        String fichero = sc.nextLine();
        System.out.println("Dime la palabra a buscar en el fichero");
        String palabra = sc.nextLine();
        File archivo = new File("./datos/" + fichero + ".txt");

        // Apertura del fichero y creación de BufferedReader
        try (BufferedReader br
                = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(archivo), StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.contains(palabra)) {
                    
                    System.out.println(linea);
                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
