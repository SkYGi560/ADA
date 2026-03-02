/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import clases.Frutas;
import dao.DAOFrutas;
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

/**
 *
 * @author alumno
 */
@Path("frutas")
public class ServiceRESTFrutas {
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        Response response;
        response = Response
                .status(Response.Status.OK)
                .entity(DAOFrutas.getAll())
                .build();
        return response;
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getOne(@PathParam("id") String nombre) {
        Response response;
        HashMap<String, String> mensaje = new HashMap<>();
        Frutas fruta = DAOFrutas.get(nombre);
        if (fruta != null) {
            response = Response
                    .status(Response.Status.OK)
                    .entity(fruta)
                    .build();
        } else {
            mensaje.put("mensaje", "No existe fruta con nombre: " + nombre);
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
    public Response post(Frutas fruta) {
        Response response;
        HashMap<String, String> mensaje = new HashMap<>();
        if (DAOFrutas.add(fruta)) {
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
    public Response put(Frutas fruta) {
        Response response;
        HashMap<String, String> mensaje = new HashMap<>();
        if (DAOFrutas.existe(fruta.getNombre())) {
            if (DAOFrutas.put(fruta)) {
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
            mensaje.put("mensaje", "No existe fruta con nombre: " + fruta.getNombre());
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
    public Response delete(@PathParam("id") String nombre) {
        Response response;
        HashMap<String, String> mensaje = new HashMap<>();
        if (DAOFrutas.existe(nombre)) {
            if (DAOFrutas.delete(nombre)) {
                mensaje.put("mensaje", "Fruta eliminada");
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
            mensaje.put("mensaje", "No existe fruta con nombre: " + nombre);
            response = Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(mensaje)
                    .build();
        }
        return response;
    }
}
