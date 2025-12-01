/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import clases.Departamento;
import clases.Departamentos;
import java.util.ArrayList;
import com.google.gson.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author alumno
 */
public class UD3Ejer402BDXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Departamento> departamentos = Departamentos.creaLista();
        for(var departamento : departamentos){
            System.out.println(departamento.toString());
        }
        File file = new File("datos/departamentos.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try(FileWriter writer = new FileWriter(file)){
            gson.toJson(departamentos, writer);
        }catch(IOException ex){
            ex.printStackTrace();
        }

    }
    
}
