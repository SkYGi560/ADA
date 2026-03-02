
package app;


import clases.Libros;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Scanner;
import org.json.JSONObject;

public class ApiRest07PostEntity {

    public static void main(String[] args) {
        // DECLARACIÓN DE VARIABLES
        String body;

        String titulo;
        String autor;

        Scanner teclado = new Scanner(System.in);

        Client client = ClientBuilder.newClient();
        Response responsePost;

        // ***************
        // DATOS DEL LIBRO
        System.out.print("Introduzca título: ");
        titulo = teclado.nextLine();

        System.out.print("Introduzca autor: ");
        autor = teclado.nextLine();
        
        // ***************
        // OBJETO DEL LIBRO
        Libros libro = new Libros(0, titulo, autor);
        System.out.println(libro);

       
        // ***************
        // POST DEL LIBRO
        responsePost = client.target("http://riconet.es/fp/apirest")
                                  .path("/libros/")
                                  .request()
                                  .header("Content-Type", "application/json")
                                  .header("Accept", "application/json")
                                  .post(Entity.entity(libro, MediaType.APPLICATION_JSON));     

        // ***************
        // RESULTADO
        if (responsePost.getStatus() == 201){
            body = responsePost.readEntity(String.class);
            // System.out.println("Response: " + body);

            JSONObject json = new JSONObject(body);
            System.out.println(json.getString("mensaje"));
        } else {
            body = responsePost.readEntity(String.class);
            // System.out.println("Response: " + body);

            JSONObject json = new JSONObject(body);
            if (!json.optString("mensaje").isEmpty()) {
              System.out.println("ERROR: "+json.getString("mensaje"));
            }
            if (!json.optString("sqlError").isEmpty()) {
              System.out.println(json.getString("sqlError"));
            }
        }              
        

    }
}
