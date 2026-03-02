/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import clases.Libros;
import dao.DAOLibros;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;

@Path("libros")
public class ServiceRESTLibros {

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        Response response;
        response = Response
                .status(Response.Status.OK)
                .entity(DAOLibros.librosGetAll())
                .build();
        return response;
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getOne(@PathParam("id") int id) {
        Response response;
        HashMap<String, String> mensaje = new HashMap<>();
        Libros libro = DAOLibros.librosGet(id);
        if (libro != null) {
            response = Response
                    .status(Response.Status.OK)
                    .entity(libro)
                    .build();
        } else {
            mensaje.put("mensaje", "No existe libro con ID " + id);
            response = Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(mensaje)
                    .build();
        }
        return response;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response post(Libros libro) {
        Response response;
        HashMap<String, String> mensaje = new HashMap<>();
        if (DAOLibros.librosPost(libro)) {
            mensaje.put("mensaje", "Registro insertado");
            response = Response
                    .status(Response.Status.CREATED)
                    .entity(mensaje)
                    .build();
        } else {
            mensaje.put("mensaje", "Error al insertar");
            response = Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(mensaje)
                    .build();
        }
        return response;
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response put(Libros libro) {
        Response response;
        HashMap<String, String> mensaje = new HashMap<>();
        if (DAOLibros.librosExiste(libro.getId())) {
            if (DAOLibros.librosPut(libro)) {
                mensaje.put("mensaje", "Registro actualizado");
                response = Response
                        .status(Response.Status.OK)
                        .entity(mensaje)
                        .build();
            } else {
                mensaje.put("mensaje", "Error al actualizar");
                response = Response
                        .status(Response.Status.CONFLICT)
                        .entity(mensaje)
                        .build();
            }
        } else {
            mensaje.put("mensaje", "No existe libro con ID " + libro.getId());
            response = Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(mensaje)
                    .build();
        }
        return response;
    }
@DELETE
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") int id) {
        Response response;
        HashMap<String, String> mensaje = new HashMap<>();
        if (DAOLibros.librosExiste(id)) {
            if (DAOLibros.librosDelete(id)) {
                mensaje.put("mensaje", "Libro eliminado");
                response = Response
                        .status(Response.Status.OK)
                        .entity(mensaje)
                        .build();
            } else {
                mensaje.put("mensaje", "Error al eliminar");
                response = Response
                        .status(Response.Status.CONFLICT)
                        .entity(mensaje)
                        .build();
            }
        } else {
            mensaje.put("mensaje", "No existe libro con ID " + id);
            response = Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(mensaje)
                    .build();
        }
        return response;
    }
}
