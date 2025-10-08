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
public class UD2Ejer603 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File archivo = new File("./datos/anotaciones.txt");

        // Apertura del fichero y creación de BufferedReader
        try (var br = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(archivo), StandardCharsets.UTF_8))) {

            // Lectura del fichero
            String linea;
            Integer contador = 0;
            Scanner sc = new Scanner(System.in);
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
                if(contador++ >= 10){
                    System.out.println("Escribe intro para contnuar leyendo: ");
                    contador = 0;
                    sc.nextLine();
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
