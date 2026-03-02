package sercine;

import jakarta.persistence.EntityManager;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.HashMap;
import java.util.List;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.ws.rs.PathParam;
import jpacine.Peliculas;
import jpacine.PeliculasJpaController;
import jpacine.Tematicas;
import jpacine.TematicasJpaController;
import org.json.JSONArray;
import org.json.JSONObject;

@Path("tematicas")
public class ServiceRESTTematicas {

    private static final String PERSISTENCE_UNIT = "ApiRestCineJPAPU";

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Status statusResul;
        List<Tematicas> lista;
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {
            TematicasJpaController dao = new TematicasJpaController(emf);
            lista = dao.findTematicasEntities();
            if (lista == null) {
                statusResul = Response.Status.NO_CONTENT;
                response = Response
                        .status(statusResul)
                        .build();
            } else {
                statusResul = Response.Status.OK;
                response = Response
                        .status(statusResul)
                        .entity(lista)
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
    @Path("/{cod}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("cod") int cod) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Status statusResul;
        Tematicas tem;
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {
            TematicasJpaController dao = new TematicasJpaController(emf);
            tem = dao.findTematicas(cod);
            if (tem == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe temática con COD " + cod);
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                statusResul = Response.Status.OK;
                response = Response
                        .status(statusResul)
                        .entity(tem)
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

    @POST
    @Path("")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createTematica(Tematicas tematicaNueva) {

        Response response;
        HashMap<String, String> mensaje = new HashMap<>();
        Response.Status statusResul;

        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            TematicasJpaController dao = new TematicasJpaController(emf);
            if (tematicaNueva == null) {
                statusResul = Response.Status.BAD_REQUEST;
                mensaje.put("mensaje", "Ha habido un problema con la carga de datos");
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                dao.create(tematicaNueva);
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

    @GET
    @Path("count/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTotalJPQL(@PathParam("id") Integer id) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        String resultado;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            TematicasJpaController dao = new TematicasJpaController(emf);
            EntityManager em = dao.getEntityManager();

            Query query
                    = em.createQuery("SELECT tem.codigo, tem.descripcion, COUNT(p) FROM Tematicas tem LEFT JOIN tem.peliculasCollection p WHERE tem.codigo ="+id+" GROUP BY tem.codigo, tem.descripcion");
            
            List<Object[]> lista = query.getResultList();
            if ((lista != null) && (!lista.isEmpty())) {
                JSONObject json = new JSONObject();
                for (Object[] obj : lista) {
                    json.put("codigo", obj[0]);
                    json.put("descripcion", obj[1]);
                    json.put("numpeliculas", obj[2]);
                }
                resultado = json.toString();
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

}
