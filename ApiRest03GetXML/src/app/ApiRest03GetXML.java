/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author alumno
 */
public class ApiRest03GetXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Objetos para realizar la petición
        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://riconet.es/fp/apirest")
                .path("/libros/4")
                .request()
                .header("Content-Type", "application/json")
                .header("Accept", "application/xml")
                .get();
        
        // Mostrar la respuesta obtenida
        System.out.println("Status:");
        System.out.println("=======");
        System.out.println(response.getStatus());
        System.out.println();
        System.out.println("Data Body:");
        System.out.println("==========");
        System.out.println(response.readEntity(String.class));
        System.out.println();
    }

}
