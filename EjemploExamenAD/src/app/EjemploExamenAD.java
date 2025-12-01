/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import clases.Equipo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 *
 * @author alumno
 */
public class EjemploExamenAD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Equipo> equipos = LeerFicheroGson("/datos/formula1.json");
        mostrarEquipo(equipos);
    }

    public static ArrayList<Equipo> LeerFicheroGson(String fichero) {
        Gson gson = new Gson();
        String jsonString = "";

        // ************************************************
        // Leer contenido del fichero (De Fichero a String)
        // ************************************************
        File file = new File("./datos/formula1.json");
        try (
                FileInputStream fis = new FileInputStream(file); InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8); BufferedReader reader = new BufferedReader(isr);) {
            String str;
            while ((str = reader.readLine()) != null) {
                jsonString += str.trim();
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        ArrayList<Equipo> equipos = gson.fromJson(jsonString, new TypeToken<ArrayList<Equipo>>() {
        }.getType());
        return equipos;
    }

    public static void mostrarEquipo(ArrayList<Equipo> equipos) {
        System.out.println("+--------------------+--------------+-----------+---------+-----------------+-----------------+");
        System.out.println("| Nombre             | País         | Fundación | Pilotos | Piloto 1        | Piloto 2        |");
        System.out.println("+--------------------+--------------+-----------+---------+-----------------+-----------------+");
        for (var equipo : equipos) {
            System.out.printf("| %-18s | %-12s | %9d | %7d | %-15s | %-15s |\n", 
                    equipo.getNombre(), 
                    equipo.getPais(), 
                    equipo.getFundacion(), 
                    equipo.getPilotos().size(), 
                    equipo.getPilotos().get(0).getNombre(), 
                    equipo.getPilotos().get(1).getNombre());
        }
        System.out.println("+--------------------+--------------+-----------+---------+-----------------+-----------------+");
    }
}
