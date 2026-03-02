package sercine;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.HashMap;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.PathParam;
import java.util.List;
import jpacitas.Medicos;
import jpacitas.MedicosJpaController;

@Path("medicos")
public class ServiceRESTMedicos {

    private static final String PERSISTENCE_UNIT = "ApiRestCitasJPAPU";
    

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Status statusResul;
        List<Medicos> lista;
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {
            MedicosJpaController dao = new MedicosJpaController(emf);
            lista = dao.findMedicosEntities();
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
    @Path("/{numcolegiado}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("numcolegiado") String numcolegiado) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Status statusResul;
        Medicos med;
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {
            MedicosJpaController dao = new MedicosJpaController(emf);
            med = dao.findMedicos(numcolegiado);
            if (med == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe médico con NUM-COLEGIADO " + numcolegiado);
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                statusResul = Response.Status.OK;
                response = Response
                        .status(statusResul)
                        .entity(med)
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
