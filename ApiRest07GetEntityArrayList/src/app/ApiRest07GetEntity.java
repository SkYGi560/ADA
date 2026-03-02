
package app;


import clases.Libros;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;

public class ApiRest07GetEntity {

    public static void main(String[] args) {
        // Objetos para realizar la petición
        Client client = ClientBuilder.newClient();

        // Indicar la URL del API Rest        
        Response response = client
              .target("http://riconet.es/fp/apirest")
              .path("/libros")
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
        //System.out.println(response.readEntity(String.class));
                
        ArrayList<Libros> listaLib;
        listaLib = response.readEntity(new GenericType<ArrayList<Libros>>() {});
        for (Libros lib : listaLib) {
            System.out.println(lib);
        }

        System.out.println();

    }
}