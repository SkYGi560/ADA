/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bdtareas;

import app.exceptions.NonexistentEntityException;
import app.exceptions.PreexistingEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import bdtareas.Tareas;

/**
 *
 * @author alumno
 */
public class TareasJpaController implements Serializable {

    public TareasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tareas tareas) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tareas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTareas(tareas.getCodigo()) != null) {
                throw new PreexistingEntityException("Tareas " + tareas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tareas tareas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tareas = em.merge(tareas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tareas.getCodigo();
                if (findTareas(id) == null) {
                    throw new NonexistentEntityException("The tareas with id " + id + " no longer exists.");
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
            Tareas tareas;
            try {
                tareas = em.getReference(Tareas.class, id);
                tareas.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tareas with id " + id + " no longer exists.", enfe);
            }
            em.remove(tareas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tareas> findTareasEntities() {
        return findTareasEntities(true, -1, -1);
    }

    public List<Tareas> findTareasEntities(int maxResults, int firstResult) {
        return findTareasEntities(false, maxResults, firstResult);
    }

    private List<Tareas> findTareasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tareas.class));
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

    public Tareas findTareas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tareas.class, id);
        } finally {
            em.close();
        }
    }

    public int getTareasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tareas> rt = cq.from(Tareas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
