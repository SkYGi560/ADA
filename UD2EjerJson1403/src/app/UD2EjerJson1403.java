/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import org.json.JSONObject;
import utils.JSONUtils;

/**
 *
 * @author alumno
 */
public class UD2EjerJson1403 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JSONObject jObj = JSONUtils.getObjectFromUrl("https://jsonplaceholder.typicode.com/posts/42");
        System.out.println("Recorrer el Array de Objetos");
        System.out.println("----------------------------");
        for(var x:jObj.keySet()){
            System.out.println("Clave: "+x);
            System.out.println("Valor: "+jObj.get(x));
            System.out.println("");
        }
    }
    
}
