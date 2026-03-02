/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bdcamiones;

import bdcamiones.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

/**
 *
 * @author alumno
 */
public class CargasJpaController implements Serializable {

    public CargasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cargas cargas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Camiones camion = cargas.getCamion();
            if (camion != null) {
                camion = em.getReference(camion.getClass(), camion.getMatricula());
                cargas.setCamion(camion);
            }
            em.persist(cargas);
            if (camion != null) {
                camion.getCargasCollection().add(cargas);
                camion = em.merge(camion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cargas cargas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargas persistentCargas = em.find(Cargas.class, cargas.getIdcarga());
            Camiones camionOld = persistentCargas.getCamion();
            Camiones camionNew = cargas.getCamion();
            if (camionNew != null) {
                camionNew = em.getReference(camionNew.getClass(), camionNew.getMatricula());
                cargas.setCamion(camionNew);
            }
            cargas = em.merge(cargas);
            if (camionOld != null && !camionOld.equals(camionNew)) {
                camionOld.getCargasCollection().remove(cargas);
                camionOld = em.merge(camionOld);
            }
            if (camionNew != null && !camionNew.equals(camionOld)) {
                camionNew.getCargasCollection().add(cargas);
                camionNew = em.merge(camionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cargas.getIdcarga();
                if (findCargas(id) == null) {
                    throw new NonexistentEntityException("The cargas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargas cargas;
            try {
                cargas = em.getReference(Cargas.class, id);
                cargas.getIdcarga();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cargas with id " + id + " no longer exists.", enfe);
            }
            Camiones camion = cargas.getCamion();
            if (camion != null) {
                camion.getCargasCollection().remove(cargas);
                camion = em.merge(camion);
            }
            em.remove(cargas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cargas> findCargasEntities() {
        return findCargasEntities(true, -1, -1);
    }

    public List<Cargas> findCargasEntities(int maxResults, int firstResult) {
        return findCargasEntities(false, maxResults, firstResult);
    }

    private List<Cargas> findCargasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cargas.class));
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

    public Cargas findCargas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cargas.class, id);
        } finally {
            em.close();
        }
    }

    public int getCargasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cargas> rt = cq.from(Cargas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
