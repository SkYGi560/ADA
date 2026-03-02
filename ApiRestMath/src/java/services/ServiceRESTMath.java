/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("aritmetica")
public class ServiceRESTMath {

    @GET
    @Path("/sumar/{operando1}/{operando2}")
    @Produces(MediaType.TEXT_PLAIN)
    public int sumar(@PathParam("operando1") int op1,
            @PathParam("operando2") int op2) {
        return op1 + op2;
    }

    @GET
    @Path("/dividir/{operando1}/{operando2}")
    @Produces(MediaType.TEXT_PLAIN)
    public double dividir(@PathParam("operando1") double op1,
            @PathParam("operando2") double op2,
            @QueryParam("decimales") int decimales) {
        double resultado = op1 / op2;
        resultado = Math.round(resultado * Math.pow(10, decimales))
                / Math.pow(10, decimales);
        return resultado;
    }
}
