/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 *
 * @author alumno
 */
public class UD2Ejer501 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Dime un par de frases para escribir en un fichero");
        Scanner sc = new Scanner(System.in);
        String frases1 = sc.nextLine();
        System.out.println("Dime un par de frases para escribir en un fichero");
        String frases2 = sc.nextLine();

        File archivo = new File("./datos/frases1.txt");
        // Apertura del fichero y creación de PrintWriter
        try (PrintWriter pw = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(archivo, true), StandardCharsets.UTF_8)))) {
            // Escritura en el fichero
            pw.println(frases1);
            pw.println(frases2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
