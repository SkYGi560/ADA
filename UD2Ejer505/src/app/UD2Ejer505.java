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
public class UD2Ejer505 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File archivo = new File("./datos/rectangulo.txt");
        // Apertura del fichero y creación de PrintWriter
        try (var pw = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(archivo, false), StandardCharsets.UTF_8)))) {
            // Escritura en el fichero
            System.out.println("Dime la altura del rectangulo: ");
            Scanner sc = new Scanner(System.in);
            Integer altura = sc.nextInt();
            System.out.println("Dime la anchura del rectangulo");
            Integer anchura = sc.nextInt();
            for(int i = 0;i<altura;i++){
                for(int j = 0;j<anchura;j++){
                    pw.print("*");
                }
                pw.println();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
