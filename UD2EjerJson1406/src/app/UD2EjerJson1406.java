/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import clases.Domicilio;
import clases.Persona;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 *
 * @author joel
 */
public class UD2EjerJson1406 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File archivo = new File("datos/censo.csv");

        try (BufferedReader br
                = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(archivo), StandardCharsets.UTF_8))) {
            String linea;
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            var personas = new ArrayList<Persona>();
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if(partes[0].matches("\\d+")){
                    var domicilio = new Domicilio(partes[2],Integer.getInteger(partes[3]),Integer.getInteger(partes[4]),partes[5]);
                    var persona = new Persona(Integer.getInteger(partes[0]),partes[1],domicilio);
                    personas.add(persona);
                }
            }
            for(var persona : personas){
                System.out.println(persona);
            }
            
            String personasListaString = gson.toJson(personas);
            System.out.println(personasListaString);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
