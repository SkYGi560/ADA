/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import clases.Country;
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
@Path("pais")
public class ServiceRESTCountry {
    @GET
    @Path("{code}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getOne(@PathParam("code") String code) {
        Response response;
        HashMap<String, String> mensaje = new HashMap<>();
        Country country = DAOWorld.countryGet(code);
        if (country != null) {
            response = Response
                    .status(Response.Status.OK)
                    .entity(country)
                    .build();
        } else {
            mensaje.put("mensaje", "No existe pais con code: " + code);
            response = Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(mensaje)
                    .build();
        }
        return response;
    }
}
