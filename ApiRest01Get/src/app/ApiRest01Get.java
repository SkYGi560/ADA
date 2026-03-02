/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author alumno
 */
public class ApiRest01Get {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Objetos para realizar la petición
        Client client = ClientBuilder.newClient();
        WebTarget webTargetBase;
        WebTarget webTargetSolicitud;
        Invocation.Builder invbuilder;
        Response response;

        // Indicar la URL del API Rest
        webTargetBase = client.target("http://riconet.es/fp/apirest");
        webTargetSolicitud = webTargetBase.path("/libros/4");

        // Aportar los formatos y datos a la llamada
        invbuilder = webTargetSolicitud.request();
        invbuilder.header("Content-Type", "application/json");
        invbuilder.header("Accept", "application/json");

        // Ejecutar el método
        response = invbuilder.get();

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
