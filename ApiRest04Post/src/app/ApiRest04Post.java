/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;
import java.util.Scanner;
import org.json.JSONObject;

/**
 *
 * @author alumno
 */
public class ApiRest04Post {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // DECLARACIÓN DE VARIABLES
        String body;
        int id;
        String titulo;
        String autor;
        Scanner teclado = new Scanner(System.in);
        Response responsePost;
        
        // PROCESO DE POST
        Client client = ClientBuilder.newClient();
        
        // ***************
        // DATOS DEL LIBRO
        System.out.print("Introduzca título: ");
        titulo = teclado.nextLine();
        System.out.print("Introduzca autor: ");
        autor = teclado.nextLine();
        
        // ***************
        // JSON DEL LIBRO
        String libro = """
                                {
                                    "id": %d,
                                    "titulo":"%s",
                                    "autor":"%s"
                                }
                        """;
        libro = String.format(libro, 0, titulo, autor);
        System.out.println(libro);

        // ***************
        // POST DEL LIBRO
        responsePost = client.target("http://riconet.es/fp/apirest")
                .path("/libros/")
                .request()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .post(Entity.json(libro));
        if (responsePost.getStatus() == 201) {
            body = responsePost.readEntity(String.class);
            // System.out.println("Response: " + body);
            JSONObject json = new JSONObject(body);
            System.out.println(json.getString("mensaje"));
        } else {
            body = responsePost.readEntity(String.class);
            // System.out.println("Response: " + body);
            JSONObject json = new JSONObject(body);
            if (!json.optString("mensaje").isEmpty()) {
                System.out.println("ERROR: " + json.getString("mensaje"));
            }
            if (!json.optString("sqlError").isEmpty()) {
                System.out.println(json.getString("sqlError"));
            }
        }
    }

}
