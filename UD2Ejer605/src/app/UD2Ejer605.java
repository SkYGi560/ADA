/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 *
 * @author alumno
 */
public class UD2Ejer605 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File archivo = new File("./datos/anotaciones.txt");
        Scanner sc = new Scanner(System.in);

        // Apertura del fichero y creación de BufferedReader
        try (var br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(archivo), StandardCharsets.UTF_8))) {

            // Lectura del fichero
            String linea;
            System.out.println("Dime el nombre del fichero en el que quieres que vuelque la salida: ");
            String nombreFichero = sc.nextLine();
            File archivoSalida = new File("./datos/" + nombreFichero + ".txt");
            try (PrintWriter pw = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    new FileOutputStream(archivoSalida, false), StandardCharsets.UTF_8)))) {
                while ((linea = br.readLine()) != null) {
                    pw.println(linea);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
