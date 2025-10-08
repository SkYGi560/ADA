/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.JSONUtils;

/**
 *
 * @author alumno
 */
public class UD2EjerJson1401 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JSONArray arrayFrutas = JSONUtils.getArrayFromFile("datos/contenedores-frutas-50.json");
        System.out.println("Contendores del pais \"FR\"");
        System.out.println("---------------------------");
        arrayFrutas.forEach((elemento) -> {
            JSONObject fruta = (JSONObject) elemento;
            if (fruta.getString("pais").equals("FR")) {
                System.out.print("IdContendor: ");
                System.out.printf("%-4s",fruta.get("idContenedor") + "  ");
                System.out.print("Producto: ");
                System.out.printf("%-15s",fruta.get("producto") + "  ");
                System.out.printf("%-6s","Pais:");
                System.out.printf(fruta.get("pais") + "  ");
                System.out.print("Ciudad: ");
                System.out.printf("%-20s",fruta.get("ciudad"));
                System.out.printf("%n");
            }
        });
    }

}
