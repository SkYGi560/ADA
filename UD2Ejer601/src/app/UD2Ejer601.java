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

/**
 *
 * @author alumno
 */
public class UD2Ejer601 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File archivo = new File("./datos/anotaciones.txt");

        // Apertura del fichero y creación de BufferedReader
        try (var br = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(archivo), StandardCharsets.UTF_8))) {
            System.out.println(br.readLine());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
