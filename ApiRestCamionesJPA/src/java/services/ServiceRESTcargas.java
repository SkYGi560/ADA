/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import bdcamiones.Cargas;
import bdcamiones.CargasJpaController;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
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
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author alumno
 */
@Path("cargas")
public class ServiceRESTcargas {

    private static final String PERSISTENCE_UNIT = "ApiRestCamionesJPAPU";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        List<Cargas> cargas;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            CargasJpaController dao = new CargasJpaController(emf);
            cargas = dao.findCargasEntities();

            if (cargas == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "Ha habido un problema con la carga de datos");
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                statusResul = Response.Status.OK;
                response = Response
                        .status(statusResul)
                        .entity(cargas)
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

    @DELETE
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") Integer id) {
        Response response;
        HashMap<String, String> mensaje = new HashMap<>();
        Response.Status statusResul;
        Cargas carga;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            CargasJpaController dao = new CargasJpaController(emf);
            carga = dao.findCargas(id);

            if (carga == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "Ha habido un problema con la carga de datos");
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                dao.destroy(id);
                statusResul = Response.Status.OK;
                mensaje.put("mensaje", "carga eliminada");
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
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
    @Path("camiones")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTotalId() {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        List<Cargas> cargas;
        String resultado;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            CargasJpaController dao = new CargasJpaController(emf);
            EntityManager em = dao.getEntityManager();
            cargas = dao.findCargasEntities();

            List<Object[]> lista = em.createNamedQuery("Cargas.findEspecial").getResultList();
            if ((lista != null) && (!lista.isEmpty())) {
                JSONArray jsonArray = new JSONArray();
                for (Object[] obj : lista) {
                    JSONObject json = new JSONObject();
                    json.put("matricula", obj[0]);
                    json.put("conductor", obj[1]);
                    json.put("numcargas", obj[2]);
                    json.put("totalpeso", obj[3]);
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
    @Path("camionesjpql")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTotalJPQL() {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        List<Cargas> cargas;
        String resultado;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            CargasJpaController dao = new CargasJpaController(emf);
            EntityManager em = dao.getEntityManager();
            cargas = dao.findCargasEntities();

            Query query
                    = em.createQuery("SELECT car.camion.matricula, car.camion.conductor, COUNT(car.camion.matricula), SUM(car.pesototal)FROM Cargas car GROUP BY car.camion.matricula, car.camion.conductor");
            List<Object[]> lista = query.getResultList();
            if ((lista != null) && (!lista.isEmpty())) {
                JSONArray jsonArray = new JSONArray();
                for (Object[] obj : lista) {
                    JSONObject json = new JSONObject();
                    json.put("matricula", obj[0]);
                    json.put("conductor", obj[1]);
                    json.put("numcargas", obj[2]);
                    json.put("totalpeso", obj[3]);
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

    // ===============================================
// PUT / POST / DELETE siguiendo tu estructura
// Operaciones (create/edit/destroy) hechas desde el DAO
// Búsqueda con JPQL (para comprobar existencia)
// ===============================================
    /*
  DELETE con JPQL + DAO
  --------------------
  - Comprobamos existencia con JPQL
  - Eliminamos con dao.destroy(id)
     */
    @DELETE
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteJPQL(@PathParam("id") Integer id) {

        Response response;
        HashMap<String, String> mensaje = new HashMap<>();
        Response.Status statusResul;
        Cargas carga;

        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {

            CargasJpaController dao = new CargasJpaController(emf);
            EntityManager em = dao.getEntityManager();
            Query query = em.createQuery("SELECT c FROM Cargas c WHERE c.id = :id");
            query.setParameter("id", id);
            carga = (Cargas) query.getSingleResult();

            if (carga == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "Ha habido un problema con la carga de datos");
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                dao.destroy(id);
                statusResul = Response.Status.OK;
                mensaje.put("mensaje", "carga eliminada");
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
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


    /*
  POST con DAO (create)
  --------------------
  - Insertamos con dao.create(cargaNueva)
     */
    @POST
    @Path("")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createJPQL(Cargas cargaNueva) {

        Response response;
        HashMap<String, String> mensaje = new HashMap<>();
        Response.Status statusResul;

        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            CargasJpaController dao = new CargasJpaController(emf);
            if (cargaNueva == null) {
                statusResul = Response.Status.BAD_REQUEST;
                mensaje.put("mensaje", "Ha habido un problema con la carga de datos");
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                dao.create(cargaNueva);
                statusResul = Response.Status.CREATED;
                mensaje.put("mensaje", "carga creada");
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
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


    /*
  PUT con JPQL + DAO (edit)
  ------------------------
  - Comprobamos existencia con JPQL
  - Actualizamos con dao.edit(cargaNueva)
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response updateJPQL(@PathParam("id") Integer id, Cargas cargaNueva) {
        Response response;
        HashMap<String, String> mensaje = new HashMap<>();
        Response.Status statusResul;
        Cargas carga;

        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            CargasJpaController dao = new CargasJpaController(emf);
            EntityManager em = dao.getEntityManager();
            Query query = em.createQuery("SELECT c FROM Cargas c WHERE c.id = :id");
            query.setParameter("id", id);
            carga = (Cargas) query.getSingleResult();

            if (carga == null || cargaNueva == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "Ha habido un problema con la carga de datos");
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                cargaNueva.setIdcarga(id);
                dao.edit(cargaNueva);
                statusResul = Response.Status.OK;
                mensaje.put("mensaje", "carga actualizada");
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
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

}
