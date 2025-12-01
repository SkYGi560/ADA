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

/**
 *
 * @author alumno
 */
public class SeriesJpaController implements Serializable {

    public SeriesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Series series) {
        if (series.getActoresCollection() == null) {
            series.setActoresCollection(new ArrayList<Actores>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Canalestv canaltv = series.getCanaltv();
            if (canaltv != null) {
                canaltv = em.getReference(canaltv.getClass(), canaltv.getCodigo());
                series.setCanaltv(canaltv);
            }
            Collection<Actores> attachedActoresCollection = new ArrayList<Actores>();
            for (Actores actoresCollectionActoresToAttach : series.getActoresCollection()) {
                actoresCollectionActoresToAttach = em.getReference(actoresCollectionActoresToAttach.getClass(), actoresCollectionActoresToAttach.getIdactor());
                attachedActoresCollection.add(actoresCollectionActoresToAttach);
            }
            series.setActoresCollection(attachedActoresCollection);
            em.persist(series);
            if (canaltv != null) {
                canaltv.getSeriesCollection().add(series);
                canaltv = em.merge(canaltv);
            }
            for (Actores actoresCollectionActores : series.getActoresCollection()) {
                actoresCollectionActores.getSeriesCollection().add(series);
                actoresCollectionActores = em.merge(actoresCollectionActores);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Series series) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Series persistentSeries = em.find(Series.class, series.getCodigo());
            Canalestv canaltvOld = persistentSeries.getCanaltv();
            Canalestv canaltvNew = series.getCanaltv();
            Collection<Actores> actoresCollectionOld = persistentSeries.getActoresCollection();
            Collection<Actores> actoresCollectionNew = series.getActoresCollection();
            if (canaltvNew != null) {
                canaltvNew = em.getReference(canaltvNew.getClass(), canaltvNew.getCodigo());
                series.setCanaltv(canaltvNew);
            }
            Collection<Actores> attachedActoresCollectionNew = new ArrayList<Actores>();
            for (Actores actoresCollectionNewActoresToAttach : actoresCollectionNew) {
                actoresCollectionNewActoresToAttach = em.getReference(actoresCollectionNewActoresToAttach.getClass(), actoresCollectionNewActoresToAttach.getIdactor());
                attachedActoresCollectionNew.add(actoresCollectionNewActoresToAttach);
            }
            actoresCollectionNew = attachedActoresCollectionNew;
            series.setActoresCollection(actoresCollectionNew);
            series = em.merge(series);
            if (canaltvOld != null && !canaltvOld.equals(canaltvNew)) {
                canaltvOld.getSeriesCollection().remove(series);
                canaltvOld = em.merge(canaltvOld);
            }
            if (canaltvNew != null && !canaltvNew.equals(canaltvOld)) {
                canaltvNew.getSeriesCollection().add(series);
                canaltvNew = em.merge(canaltvNew);
            }
            for (Actores actoresCollectionOldActores : actoresCollectionOld) {
                if (!actoresCollectionNew.contains(actoresCollectionOldActores)) {
                    actoresCollectionOldActores.getSeriesCollection().remove(series);
                    actoresCollectionOldActores = em.merge(actoresCollectionOldActores);
                }
            }
            for (Actores actoresCollectionNewActores : actoresCollectionNew) {
                if (!actoresCollectionOld.contains(actoresCollectionNewActores)) {
                    actoresCollectionNewActores.getSeriesCollection().add(series);
                    actoresCollectionNewActores = em.merge(actoresCollectionNewActores);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = series.getCodigo();
                if (findSeries(id) == null) {
                    throw new NonexistentEntityException("The series with id " + id + " no longer exists.");
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
            Series series;
            try {
                series = em.getReference(Series.class, id);
                series.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The series with id " + id + " no longer exists.", enfe);
            }
            Canalestv canaltv = series.getCanaltv();
            if (canaltv != null) {
                canaltv.getSeriesCollection().remove(series);
                canaltv = em.merge(canaltv);
            }
            Collection<Actores> actoresCollection = series.getActoresCollection();
            for (Actores actoresCollectionActores : actoresCollection) {
                actoresCollectionActores.getSeriesCollection().remove(series);
                actoresCollectionActores = em.merge(actoresCollectionActores);
            }
            em.remove(series);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Series> findSeriesEntities() {
        return findSeriesEntities(true, -1, -1);
    }

    public List<Series> findSeriesEntities(int maxResults, int firstResult) {
        return findSeriesEntities(false, maxResults, firstResult);
    }

    private List<Series> findSeriesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Series.class));
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

    public Series findSeries(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Series.class, id);
        } finally {
            em.close();
        }
    }

    public int getSeriesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Series> rt = cq.from(Series.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
