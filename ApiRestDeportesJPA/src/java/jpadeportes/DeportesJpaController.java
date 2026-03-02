/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpadeportes;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import jpadeportes.exceptions.NonexistentEntityException;

/**
 *
 * @author alumnosemi
 */
public class DeportesJpaController implements Serializable {

    public DeportesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Deportes deportes) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categorias categoriaId = deportes.getCategoriaId();
            if (categoriaId != null) {
                categoriaId = em.getReference(categoriaId.getClass(), categoriaId.getId());
                deportes.setCategoriaId(categoriaId);
            }
            em.persist(deportes);
            if (categoriaId != null) {
                categoriaId.getDeportesCollection().add(deportes);
                categoriaId = em.merge(categoriaId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Deportes deportes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Deportes persistentDeportes = em.find(Deportes.class, deportes.getId());
            Categorias categoriaIdOld = persistentDeportes.getCategoriaId();
            Categorias categoriaIdNew = deportes.getCategoriaId();
            if (categoriaIdNew != null) {
                categoriaIdNew = em.getReference(categoriaIdNew.getClass(), categoriaIdNew.getId());
                deportes.setCategoriaId(categoriaIdNew);
            }
            deportes = em.merge(deportes);
            if (categoriaIdOld != null && !categoriaIdOld.equals(categoriaIdNew)) {
                categoriaIdOld.getDeportesCollection().remove(deportes);
                categoriaIdOld = em.merge(categoriaIdOld);
            }
            if (categoriaIdNew != null && !categoriaIdNew.equals(categoriaIdOld)) {
                categoriaIdNew.getDeportesCollection().add(deportes);
                categoriaIdNew = em.merge(categoriaIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = deportes.getId();
                if (findDeportes(id) == null) {
                    throw new NonexistentEntityException("The deportes with id " + id + " no longer exists.");
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
            Deportes deportes;
            try {
                deportes = em.getReference(Deportes.class, id);
                deportes.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The deportes with id " + id + " no longer exists.", enfe);
            }
            Categorias categoriaId = deportes.getCategoriaId();
            if (categoriaId != null) {
                categoriaId.getDeportesCollection().remove(deportes);
                categoriaId = em.merge(categoriaId);
            }
            em.remove(deportes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Deportes> findDeportesEntities() {
        return findDeportesEntities(true, -1, -1);
    }

    public List<Deportes> findDeportesEntities(int maxResults, int firstResult) {
        return findDeportesEntities(false, maxResults, firstResult);
    }

    private List<Deportes> findDeportesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Deportes.class));
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

    public Deportes findDeportes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Deportes.class, id);
        } finally {
            em.close();
        }
    }

    public int getDeportesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Deportes> rt = cq.from(Deportes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
