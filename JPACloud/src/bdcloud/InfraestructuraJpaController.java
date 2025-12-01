/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bdcloud;

import bdcloud.exceptions.NonexistentEntityException;
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
public class InfraestructuraJpaController implements Serializable {

    public InfraestructuraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Infraestructura infraestructura) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Plataforma plataforma = infraestructura.getPlataforma();
            if (plataforma != null) {
                plataforma = em.getReference(plataforma.getClass(), plataforma.getIdPform());
                infraestructura.setPlataforma(plataforma);
            }
            em.persist(infraestructura);
            if (plataforma != null) {
                plataforma.getInfraestructuraCollection().add(infraestructura);
                plataforma = em.merge(plataforma);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Infraestructura infraestructura) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Infraestructura persistentInfraestructura = em.find(Infraestructura.class, infraestructura.getIdService());
            Plataforma plataformaOld = persistentInfraestructura.getPlataforma();
            Plataforma plataformaNew = infraestructura.getPlataforma();
            if (plataformaNew != null) {
                plataformaNew = em.getReference(plataformaNew.getClass(), plataformaNew.getIdPform());
                infraestructura.setPlataforma(plataformaNew);
            }
            infraestructura = em.merge(infraestructura);
            if (plataformaOld != null && !plataformaOld.equals(plataformaNew)) {
                plataformaOld.getInfraestructuraCollection().remove(infraestructura);
                plataformaOld = em.merge(plataformaOld);
            }
            if (plataformaNew != null && !plataformaNew.equals(plataformaOld)) {
                plataformaNew.getInfraestructuraCollection().add(infraestructura);
                plataformaNew = em.merge(plataformaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = infraestructura.getIdService();
                if (findInfraestructura(id) == null) {
                    throw new NonexistentEntityException("The infraestructura with id " + id + " no longer exists.");
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
            Infraestructura infraestructura;
            try {
                infraestructura = em.getReference(Infraestructura.class, id);
                infraestructura.getIdService();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The infraestructura with id " + id + " no longer exists.", enfe);
            }
            Plataforma plataforma = infraestructura.getPlataforma();
            if (plataforma != null) {
                plataforma.getInfraestructuraCollection().remove(infraestructura);
                plataforma = em.merge(plataforma);
            }
            em.remove(infraestructura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Infraestructura> findInfraestructuraEntities() {
        return findInfraestructuraEntities(true, -1, -1);
    }

    public List<Infraestructura> findInfraestructuraEntities(int maxResults, int firstResult) {
        return findInfraestructuraEntities(false, maxResults, firstResult);
    }

    private List<Infraestructura> findInfraestructuraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Infraestructura.class));
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

    public Infraestructura findInfraestructura(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Infraestructura.class, id);
        } finally {
            em.close();
        }
    }

    public int getInfraestructuraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Infraestructura> rt = cq.from(Infraestructura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
