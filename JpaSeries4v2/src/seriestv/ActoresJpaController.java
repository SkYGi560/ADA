/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package seriestv;

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
import seriestv.exceptions.NonexistentEntityException;
import seriestv.exceptions.PreexistingEntityException;

/**
 *
 * @author alumno
 */
public class ActoresJpaController implements Serializable {

    public ActoresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Actores actores) throws PreexistingEntityException, Exception {
        if (actores.getSeriesCollection() == null) {
            actores.setSeriesCollection(new ArrayList<Series>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Series> attachedSeriesCollection = new ArrayList<Series>();
            for (Series seriesCollectionSeriesToAttach : actores.getSeriesCollection()) {
                seriesCollectionSeriesToAttach = em.getReference(seriesCollectionSeriesToAttach.getClass(), seriesCollectionSeriesToAttach.getCodigo());
                attachedSeriesCollection.add(seriesCollectionSeriesToAttach);
            }
            actores.setSeriesCollection(attachedSeriesCollection);
            em.persist(actores);
            for (Series seriesCollectionSeries : actores.getSeriesCollection()) {
                seriesCollectionSeries.getActoresCollection().add(actores);
                seriesCollectionSeries = em.merge(seriesCollectionSeries);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findActores(actores.getIdactor()) != null) {
                throw new PreexistingEntityException("Actores " + actores + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Actores actores) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Actores persistentActores = em.find(Actores.class, actores.getIdactor());
            Collection<Series> seriesCollectionOld = persistentActores.getSeriesCollection();
            Collection<Series> seriesCollectionNew = actores.getSeriesCollection();
            Collection<Series> attachedSeriesCollectionNew = new ArrayList<Series>();
            for (Series seriesCollectionNewSeriesToAttach : seriesCollectionNew) {
                seriesCollectionNewSeriesToAttach = em.getReference(seriesCollectionNewSeriesToAttach.getClass(), seriesCollectionNewSeriesToAttach.getCodigo());
                attachedSeriesCollectionNew.add(seriesCollectionNewSeriesToAttach);
            }
            seriesCollectionNew = attachedSeriesCollectionNew;
            actores.setSeriesCollection(seriesCollectionNew);
            actores = em.merge(actores);
            for (Series seriesCollectionOldSeries : seriesCollectionOld) {
                if (!seriesCollectionNew.contains(seriesCollectionOldSeries)) {
                    seriesCollectionOldSeries.getActoresCollection().remove(actores);
                    seriesCollectionOldSeries = em.merge(seriesCollectionOldSeries);
                }
            }
            for (Series seriesCollectionNewSeries : seriesCollectionNew) {
                if (!seriesCollectionOld.contains(seriesCollectionNewSeries)) {
                    seriesCollectionNewSeries.getActoresCollection().add(actores);
                    seriesCollectionNewSeries = em.merge(seriesCollectionNewSeries);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = actores.getIdactor();
                if (findActores(id) == null) {
                    throw new NonexistentEntityException("The actores with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Actores actores;
            try {
                actores = em.getReference(Actores.class, id);
                actores.getIdactor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actores with id " + id + " no longer exists.", enfe);
            }
            Collection<Series> seriesCollection = actores.getSeriesCollection();
            for (Series seriesCollectionSeries : seriesCollection) {
                seriesCollectionSeries.getActoresCollection().remove(actores);
                seriesCollectionSeries = em.merge(seriesCollectionSeries);
            }
            em.remove(actores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Actores> findActoresEntities() {
        return findActoresEntities(true, -1, -1);
    }

    public List<Actores> findActoresEntities(int maxResults, int firstResult) {
        return findActoresEntities(false, maxResults, firstResult);
    }

    private List<Actores> findActoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Actores.class));
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

    public Actores findActores(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Actores.class, id);
        } finally {
            em.close();
        }
    }

    public int getActoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Actores> rt = cq.from(Actores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
