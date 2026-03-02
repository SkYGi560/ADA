/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpacitas;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import jpacitas.exceptions.IllegalOrphanException;
import jpacitas.exceptions.NonexistentEntityException;
import jpacitas.exceptions.PreexistingEntityException;

/**
 *
 * @author VRICO
 */
public class MedicosJpaController implements Serializable {

    public MedicosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Medicos medicos) throws PreexistingEntityException, Exception {
        if (medicos.getPacientesCollection() == null) {
            medicos.setPacientesCollection(new ArrayList<Pacientes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Pacientes> attachedPacientesCollection = new ArrayList<Pacientes>();
            for (Pacientes pacientesCollectionPacientesToAttach : medicos.getPacientesCollection()) {
                pacientesCollectionPacientesToAttach = em.getReference(pacientesCollectionPacientesToAttach.getClass(), pacientesCollectionPacientesToAttach.getIdreg());
                attachedPacientesCollection.add(pacientesCollectionPacientesToAttach);
            }
            medicos.setPacientesCollection(attachedPacientesCollection);
            em.persist(medicos);
            for (Pacientes pacientesCollectionPacientes : medicos.getPacientesCollection()) {
                Medicos oldMedicoOfPacientesCollectionPacientes = pacientesCollectionPacientes.getMedico();
                pacientesCollectionPacientes.setMedico(medicos);
                pacientesCollectionPacientes = em.merge(pacientesCollectionPacientes);
                if (oldMedicoOfPacientesCollectionPacientes != null) {
                    oldMedicoOfPacientesCollectionPacientes.getPacientesCollection().remove(pacientesCollectionPacientes);
                    oldMedicoOfPacientesCollectionPacientes = em.merge(oldMedicoOfPacientesCollectionPacientes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMedicos(medicos.getNumcolegiado()) != null) {
                throw new PreexistingEntityException("Medicos " + medicos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Medicos medicos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Medicos persistentMedicos = em.find(Medicos.class, medicos.getNumcolegiado());
            Collection<Pacientes> pacientesCollectionOld = persistentMedicos.getPacientesCollection();
            Collection<Pacientes> pacientesCollectionNew = medicos.getPacientesCollection();
            List<String> illegalOrphanMessages = null;
            for (Pacientes pacientesCollectionOldPacientes : pacientesCollectionOld) {
                if (!pacientesCollectionNew.contains(pacientesCollectionOldPacientes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pacientes " + pacientesCollectionOldPacientes + " since its medico field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Pacientes> attachedPacientesCollectionNew = new ArrayList<Pacientes>();
            for (Pacientes pacientesCollectionNewPacientesToAttach : pacientesCollectionNew) {
                pacientesCollectionNewPacientesToAttach = em.getReference(pacientesCollectionNewPacientesToAttach.getClass(), pacientesCollectionNewPacientesToAttach.getIdreg());
                attachedPacientesCollectionNew.add(pacientesCollectionNewPacientesToAttach);
            }
            pacientesCollectionNew = attachedPacientesCollectionNew;
            medicos.setPacientesCollection(pacientesCollectionNew);
            medicos = em.merge(medicos);
            for (Pacientes pacientesCollectionNewPacientes : pacientesCollectionNew) {
                if (!pacientesCollectionOld.contains(pacientesCollectionNewPacientes)) {
                    Medicos oldMedicoOfPacientesCollectionNewPacientes = pacientesCollectionNewPacientes.getMedico();
                    pacientesCollectionNewPacientes.setMedico(medicos);
                    pacientesCollectionNewPacientes = em.merge(pacientesCollectionNewPacientes);
                    if (oldMedicoOfPacientesCollectionNewPacientes != null && !oldMedicoOfPacientesCollectionNewPacientes.equals(medicos)) {
                        oldMedicoOfPacientesCollectionNewPacientes.getPacientesCollection().remove(pacientesCollectionNewPacientes);
                        oldMedicoOfPacientesCollectionNewPacientes = em.merge(oldMedicoOfPacientesCollectionNewPacientes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = medicos.getNumcolegiado();
                if (findMedicos(id) == null) {
                    throw new NonexistentEntityException("The medicos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Medicos medicos;
            try {
                medicos = em.getReference(Medicos.class, id);
                medicos.getNumcolegiado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The medicos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Pacientes> pacientesCollectionOrphanCheck = medicos.getPacientesCollection();
            for (Pacientes pacientesCollectionOrphanCheckPacientes : pacientesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Medicos (" + medicos + ") cannot be destroyed since the Pacientes " + pacientesCollectionOrphanCheckPacientes + " in its pacientesCollection field has a non-nullable medico field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(medicos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Medicos> findMedicosEntities() {
        return findMedicosEntities(true, -1, -1);
    }

    public List<Medicos> findMedicosEntities(int maxResults, int firstResult) {
        return findMedicosEntities(false, maxResults, firstResult);
    }

    private List<Medicos> findMedicosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Medicos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Medicos findMedicos(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Medicos.class, id);
        } finally {
            em.close();
        }
    }

    public int getMedicosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Medicos> rt = cq.from(Medicos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
