/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import bdcamiones.Camiones;
import bdcamiones.CamionesJpaController;
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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author alumno
 */
@Path("camiones")
public class ServiceRESTcamiones {

    private static final String PERSISTENCE_UNIT = "ApiRestCamionesJPAPU";

    @GET
    @Path("{matricula}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("matricula") String matricula) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        Camiones cam;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            CamionesJpaController dao = new CamionesJpaController(emf);
            cam = dao.findCamiones(matricula);

            if (cam == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe camión con matricula " + matricula);
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                statusResul = Response.Status.OK;
                response = Response
                        .status(statusResul)
                        .entity(cam)
                        .build();
            }
        } catch (Exception ex) {
            ex.printStackTrace(); // <-- CLAVE
            statusResul = Response.Status.INTERNAL_SERVER_ERROR; // 500 sería lo correcto
            mensaje.put("mensaje", "Error: " + ex.getMessage());
            response = Response.status(statusResul).entity(mensaje).build();
        }
        return response;
    }

    //GetAll(ByMatricula) Con NAMEDQUERY
    @GET
    @Path("camiones/{matricula}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCamionNamedQuery(@PathParam("matricula") String matricula) {

        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        String resultado;

        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {

            CamionesJpaController dao = new CamionesJpaController(emf);
            EntityManager em = dao.getEntityManager();

            Query query = em.createNamedQuery("Camiones.findByMatricula");
            query.setParameter("matricula", matricula);

            List<Object[]> lista = query.getResultList();

            if ((lista != null) && (!lista.isEmpty())) {

                JSONArray jsonArray = new JSONArray();

                for (Object[] obj : lista) {
                    JSONObject json = new JSONObject();
                    json.put("matricula", obj[0]);
                    json.put("marca", obj[1]);
                    json.put("modelo", obj[2]);
                    json.put("precio", obj[3]);
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

    // GetAll(ByMatricula) con JPQL
    @GET
    @Path("camionesjpql/{matricula}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCamionJPQL(@PathParam("matricula") String matricula) {

        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        String resultado;

        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {
            CamionesJpaController dao = new CamionesJpaController(emf);
            EntityManager em = dao.getEntityManager();
            Query query = em.createQuery(
                    "SELECT c.matricula, c.marca, c.modelo, c.precio "
                    + "FROM Camiones c WHERE c.matricula = :matricula");
            query.setParameter("matricula", matricula);
            List<Object[]> lista = query.getResultList();

            if ((lista != null) && (!lista.isEmpty())) {
                JSONArray jsonArray = new JSONArray();
                for (Object[] obj : lista) {
                    JSONObject json = new JSONObject();
                    json.put("matricula", obj[0]);
                    json.put("marca", obj[1]);
                    json.put("modelo", obj[2]);
                    json.put("precio", obj[3]);
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

    //GetOne JPQL
    @GET
    @Path("{matricula}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneJPQL(@PathParam("matricula") String matricula) {

        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        Camiones cam;

        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {

            CamionesJpaController dao = new CamionesJpaController(emf);
            EntityManager em = dao.getEntityManager();
            Query query = em.createQuery(
                    "SELECT c FROM Camiones c WHERE c.matricula = :matricula");
            query.setParameter("matricula", matricula);
            cam = (Camiones) query.getSingleResult();

            if (cam == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe camión con matricula " + matricula);
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                statusResul = Response.Status.OK;
                response = Response
                        .status(statusResul)
                        .entity(cam)
                        .build();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            statusResul = Response.Status.INTERNAL_SERVER_ERROR;
            mensaje.put("mensaje", "Error: " + ex.getMessage());
            response = Response.status(statusResul).entity(mensaje).build();
        }

        return response;
    }

    //GetOne NAMEDQUERY
    @GET
    @Path("{matriculaNamed}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneNamed(@PathParam("matriculaNamed") String matricula) {

        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        Camiones cam;

        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {

            CamionesJpaController dao = new CamionesJpaController(emf);
            EntityManager em = dao.getEntityManager();
            Query query = em.createNamedQuery("Camiones.findByMatricula");
            query.setParameter("matricula", matricula);
            cam = (Camiones) query.getSingleResult();
            if (cam == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe camión con matricula " + matricula);
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                statusResul = Response.Status.OK;
                response = Response
                        .status(statusResul)
                        .entity(cam)
                        .build();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            statusResul = Response.Status.INTERNAL_SERVER_ERROR;
            mensaje.put("mensaje", "Error: " + ex.getMessage());
            response = Response.status(statusResul).entity(mensaje).build();
        }

        return response;
    }

}
