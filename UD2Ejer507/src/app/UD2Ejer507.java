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
public class UD2Ejer507 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Dime el nombre del mes: ");
        String nombre = sc.nextLine();
        System.out.println("Dime la cantidad de dias del mes: ");
        Integer dias = sc.nextInt();
        System.out.println("Dime el numero del primer dia del mes: ");
        Integer dia = sc.nextInt();
        String linea = "";


        File archivo = new File("./datos/calendario" + nombre + ".txt");
        // Apertura del fichero y creación de PrintWriter
        try (var pw = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(archivo, false), StandardCharsets.UTF_8)))) {
            pw.println("------------------");
            pw.println(nombre);
            pw.println("------------------");
            String diasSemana = "lun mar mie jue vie sab dom";
            pw.println(diasSemana);
            for (int j = dia; j > 1; j--) {
                linea += "    ";
            }
            for (int i = 1; i <= dias; i++) {

                if (i < 10) {
                    linea+="  " + i + " ";
                } else {
                    linea+=" " + i + " ";
                }
                if (linea.length() >= diasSemana.length() || i >= dias) {
                    pw.println(linea);
                    linea="";
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
