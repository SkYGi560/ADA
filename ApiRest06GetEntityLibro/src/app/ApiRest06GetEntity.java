
package app;


import clases.Libros;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;

public class ApiRest06GetEntity {

    public static void main(String[] args) {
        // Objetos para realizar la petición
        Client client = ClientBuilder.newClient();

        // Indicar la URL del API Rest        
        Response response = client
              .target("http://riconet.es/fp/apirest")
              .path("/libros/4")
              .request()
              .header("Content-Type", "application/json")
              .header("Accept",       "application/json")
              .get();  

        // Mostrar la respuesta obtenida
        System.out.println("Status:");
        System.out.println("=======");
        System.out.println(response.getStatus());
        System.out.println();
        
        System.out.println("Data Body:");
        System.out.println("==========");
                
        Libros lib;
        lib = response.readEntity(Libros.class);
        System.out.println(lib);
        System.out.println();

    }
}