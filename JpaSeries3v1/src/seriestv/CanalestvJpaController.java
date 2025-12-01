/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package seriestv;

import app.exceptions.NonexistentEntityException;
import app.exceptions.PreexistingEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import seriestv.Series;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import seriestv.Canalestv;

/**
 *
 * @author alumno
 */
public class CanalestvJpaController implements Serializable {

    public CanalestvJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Canalestv canalestv) throws PreexistingEntityException, Exception {
        if (canalestv.getSeriesCollection() == null) {
            canalestv.setSeriesCollection(new ArrayList<Series>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Series> attachedSeriesCollection = new ArrayList<Series>();
            for (Series seriesCollectionSeriesToAttach : canalestv.getSeriesCollection()) {
                seriesCollectionSeriesToAttach = em.getReference(seriesCollectionSeriesToAttach.getClass(), seriesCollectionSeriesToAttach.getCodigo());
                attachedSeriesCollection.add(seriesCollectionSeriesToAttach);
            }
            canalestv.setSeriesCollection(attachedSeriesCollection);
            em.persist(canalestv);
            for (Series seriesCollectionSeries : canalestv.getSeriesCollection()) {
                Canalestv oldCanaltvOfSeriesCollectionSeries = seriesCollectionSeries.getCanaltv();
                seriesCollectionSeries.setCanaltv(canalestv);
                seriesCollectionSeries = em.merge(seriesCollectionSeries);
                if (oldCanaltvOfSeriesCollectionSeries != null) {
                    oldCanaltvOfSeriesCollectionSeries.getSeriesCollection().remove(seriesCollectionSeries);
                    oldCanaltvOfSeriesCollectionSeries = em.merge(oldCanaltvOfSeriesCollectionSeries);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCanalestv(canalestv.getCodigo()) != null) {
                throw new PreexistingEntityException("Canalestv " + canalestv + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Canalestv canalestv) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Canalestv persistentCanalestv = em.find(Canalestv.class, canalestv.getCodigo());
            Collection<Series> seriesCollectionOld = persistentCanalestv.getSeriesCollection();
            Collection<Series> seriesCollectionNew = canalestv.getSeriesCollection();
            Collection<Series> attachedSeriesCollectionNew = new ArrayList<Series>();
            for (Series seriesCollectionNewSeriesToAttach : seriesCollectionNew) {
                seriesCollectionNewSeriesToAttach = em.getReference(seriesCollectionNewSeriesToAttach.getClass(), seriesCollectionNewSeriesToAttach.getCodigo());
                attachedSeriesCollectionNew.add(seriesCollectionNewSeriesToAttach);
            }
            seriesCollectionNew = attachedSeriesCollectionNew;
            canalestv.setSeriesCollection(seriesCollectionNew);
            canalestv = em.merge(canalestv);
            for (Series seriesCollectionOldSeries : seriesCollectionOld) {
                if (!seriesCollectionNew.contains(seriesCollectionOldSeries)) {
                    seriesCollectionOldSeries.setCanaltv(null);
                    seriesCollectionOldSeries = em.merge(seriesCollectionOldSeries);
                }
            }
            for (Series seriesCollectionNewSeries : seriesCollectionNew) {
                if (!seriesCollectionOld.contains(seriesCollectionNewSeries)) {
                    Canalestv oldCanaltvOfSeriesCollectionNewSeries = seriesCollectionNewSeries.getCanaltv();
                    seriesCollectionNewSeries.setCanaltv(canalestv);
                    seriesCollectionNewSeries = em.merge(seriesCollectionNewSeries);
                    if (oldCanaltvOfSeriesCollectionNewSeries != null && !oldCanaltvOfSeriesCollectionNewSeries.equals(canalestv)) {
                        oldCanaltvOfSeriesCollectionNewSeries.getSeriesCollection().remove(seriesCollectionNewSeries);
                        oldCanaltvOfSeriesCollectionNewSeries = em.merge(oldCanaltvOfSeriesCollectionNewSeries);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = canalestv.getCodigo();
                if (findCanalestv(id) == null) {
                    throw new NonexistentEntityException("The canalestv with id " + id + " no longer exists.");
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
            Canalestv canalestv;
            try {
                canalestv = em.getReference(Canalestv.class, id);
                canalestv.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The canalestv with id " + id + " no longer exists.", enfe);
            }
            Collection<Series> seriesCollection = canalestv.getSeriesCollection();
            for (Series seriesCollectionSeries : seriesCollection) {
                seriesCollectionSeries.setCanaltv(null);
                seriesCollectionSeries = em.merge(seriesCollectionSeries);
            }
            em.remove(canalestv);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Canalestv> findCanalestvEntities() {
        return findCanalestvEntities(true, -1, -1);
    }

    public List<Canalestv> findCanalestvEntities(int maxResults, int firstResult) {
        return findCanalestvEntities(false, maxResults, firstResult);
    }

    private List<Canalestv> findCanalestvEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Canalestv.class));
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

    public Canalestv findCanalestv(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Canalestv.class, id);
        } finally {
            em.close();
        }
    }

    public int getCanalestvCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Canalestv> rt = cq.from(Canalestv.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
