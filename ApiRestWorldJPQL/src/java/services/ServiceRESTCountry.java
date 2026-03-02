/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import jpaworldnew.Country;
import jpaworldnew.CountryJpaController;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author alumno
 */
@Path("pais")
public class ServiceRESTCountry {

    private static final String PERSISTENCE_UNIT = "ApiRestWorldJPAPU";

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("id") String id) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        Country country;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            CountryJpaController dao = new CountryJpaController(emf);
            country = dao.findCountry(id);

            if (country == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe pais con ID " + id);
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                statusResul = Response.Status.OK;
                response = Response
                        .status(statusResul)
                        .entity(country)
                        .build();
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al procesar la petición");
            response = Response
                    .status(statusResul)
                    .entity(mensaje)
                    .build();
        }
        return response;
    }

    @GET
    @Path("total/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTotalId(@PathParam("id") String id) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        Country country;
        String resultado;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            CountryJpaController dao = new CountryJpaController(emf);
            EntityManager em = dao.getEntityManager();
            country = dao.findCountry(id);

            Query query = 
                    em.createQuery("SELECT p.code, size(p.cityCollection) as numciudades FROM Country p WHERE p.code='" + country.getCode() + "'");
            List<Object[]> lista = query.getResultList();
            if ((lista != null) && (!lista.isEmpty())) {
                JSONArray jsonArray = new JSONArray();
                for (Object[] obj : lista) {
                    JSONObject json = new JSONObject();
                    json.put("countrycode", obj[0]);
                    json.put("numciudades", obj[1]);
                    jsonArray.put(json);
                }
                resultado = jsonArray.toString();
                statusResul = Response.Status.OK;
                response = Response
                        .status(statusResul)
                        .entity(resultado)
                        .build();

            } else {
                statusResul = Response.Status.NO_CONTENT;
                response = Response
                        .status(statusResul)
                        .build();

            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al procesar la petición");
            response = Response
                    .status(statusResul)
                    .entity(mensaje)
                    .build();
        }
        return response;
    }

    @GET
    @Path("total")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTotal() {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        Query query;
        try (
                EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT); EntityManager em = emf.createEntityManager();) {

            query = em.createQuery("SELECT p.code, size(p.cityCollection) as numciudades FROM Country p");
            List listaCiudades = query.getResultList();
            JSONArray jObj = new JSONArray(listaCiudades);
            statusResul = Response.Status.OK;
            response = Response
                    .status(statusResul)
                    .entity(jObj.toString())
                    .build();

        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al procesar la petición");
            response = Response
                    .status(statusResul)
                    .entity(mensaje)
                    .build();
        }
        return response;
    }
}
