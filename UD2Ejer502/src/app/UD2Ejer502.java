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
public class UD2Ejer502 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File archivo = new File("./datos/frases2.txt");
        String linea = "";
        Scanner sc = new Scanner(System.in);
        
        try (PrintWriter pw = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(archivo, false), StandardCharsets.UTF_8)))) {
            do {
                System.out.println("Dime una frase (no escribas para terminar): ");
                linea = sc.nextLine();
                pw.println(linea);
            } while (!linea.equals(""));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
