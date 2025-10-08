/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.JSONUtils;

/**
 *
 * @author alumno
 */
public class UD2EjerJson1402 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JSONArray jsonArray = JSONUtils.getArrayFromFile("datos/sistoperativos.json");
        System.out.println("Recorrer el Array de Objetos con FOR y contador");
        System.out.println("-----------------------------------------------");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jObj = (JSONObject) jsonArray.get(i);
            System.out.println("Id: " + jObj.getInt("Id"));
            System.out.println("Nombre: " + jObj.getString("Nombre"));
            System.out.println("");
        }
        System.out.println("Recorrer el Array de Objetos con FOR de elementos");
        System.out.println("-------------------------------------------------");
        for (var elemento : jsonArray) {
            JSONObject jObj = (JSONObject) elemento;
            System.out.println(jObj);
            System.out.println("Id: " + jObj.getInt("Id"));
            System.out.println("Nombre: " + jObj.getString("Nombre"));
            System.out.println("");
        }
        System.out.println("Recorrer el Array de Objetos con ITERATOR");
        System.out.println("-----------------------------------------");
        Iterator<Object> iter = jsonArray.iterator();
        while (iter.hasNext()) {
            JSONObject jObj = (JSONObject) iter.next();
            System.out.println(jObj);
            System.out.println("Id: " + jObj.getInt("Id"));
            System.out.println("Nombre: " + jObj.getString("Nombre"));
            System.out.println("");
        }

        System.out.println("Acceso directo a un elemento");
        System.out.println("------------------------------------------");
        System.out.println("Del elemento con índice 2 -> obtener el Nombre");
        JSONObject elemento2 = (JSONObject) jsonArray.get(2);
        System.out.println(elemento2.getString("Nombre"));
    }

}
