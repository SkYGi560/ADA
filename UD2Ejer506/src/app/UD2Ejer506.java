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
public class UD2Ejer506 {

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

        String[] diaSemana = new String[]{"Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo"};
        Integer contador = dia -1;

        File archivo = new File("./datos/agenda"+nombre+".txt");
        // Apertura del fichero y creación de PrintWriter
        try (var pw = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(archivo, false), StandardCharsets.UTF_8)))) {
            pw.println(nombre);
            pw.println("-----------------------------------------------------------------");
            for(int i = 1; i <= dias; i++){
                if(contador >= diaSemana.length) contador = 0;
                
                pw.println(diaSemana[contador++]+" "+i);
                pw.println("-----------------------------------------------------------------");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
