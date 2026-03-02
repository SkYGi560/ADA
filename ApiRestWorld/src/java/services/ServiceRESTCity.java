/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import clases.City;
import dao.DAOWorld;
import jakarta.ws.rs.GET;
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
@Path("ciudad")
public class ServiceRESTCity {
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getOne(@PathParam("id") int id) {
        Response response;
        HashMap<String, String> mensaje = new HashMap<>();
        City city = DAOWorld.cityGet(id);
        if (city != null) {
            response = Response
                    .status(Response.Status.OK)
                    .entity(city)
                    .build();
        } else {
            mensaje.put("mensaje", "No existe ciudad con id: " + id);
            response = Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(mensaje)
                    .build();
        }
        return response;
    }
}
