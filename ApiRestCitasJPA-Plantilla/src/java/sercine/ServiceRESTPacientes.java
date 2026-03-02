package sercine;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.HashMap;
import java.util.List;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PathParam;
import jpacitas.Medicos;
import jpacitas.Pacientes;
import jpacitas.PacientesJpaController;

@Path("pacientes")
public class ServiceRESTPacientes {

    private static final String PERSISTENCE_UNIT = "ApiRestCitasJPAPU";

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Status statusResul;
        List<Pacientes> lista;
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {
            PacientesJpaController dao = new PacientesJpaController(emf);
            lista = dao.findPacientesEntities();
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
    @Path("/{idreg}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("idreg") int id) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Status statusResul;
        Pacientes pac;
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {
            PacientesJpaController dao = new PacientesJpaController(emf);
            pac = dao.findPacientes(id);
            if (pac == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe paciente con IDREG " + id);
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                statusResul = Response.Status.OK;
                response = Response
                        .status(statusResul)
                        .entity(pac)
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
    @Path("/{idreg}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePaciente(@PathParam("idreg") int id) {

        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;

        try (EntityManagerFactory emf =
                Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {

            PacientesJpaController dao = new PacientesJpaController(emf);
            
            Pacientes paciente = dao.findPacientes(id);
            

            if (paciente == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe paciente con ID " + id);
                response = Response.status(statusResul).entity(mensaje).build();
            } else {                
                dao.destroy(id);

                statusResul = Response.Status.OK;
                mensaje.put("mensaje", "Paciente con ID " + id +  "eliminado");
                response = Response.status(statusResul).entity(mensaje).build();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al procesar la petición");
            response = Response.status(statusResul).entity(mensaje).build();
        }

        return response;
    }
    
    

}
