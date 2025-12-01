/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package seriestv;

import app.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import seriestv.Canalestv;
import seriestv.Series;

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
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Canalestv canaltv = series.getCanaltv();
            if (canaltv != null) {
                canaltv = em.getReference(canaltv.getClass(), canaltv.getCodigo());
                series.setCanaltv(canaltv);
            }
            em.persist(series);
            if (canaltv != null) {
                canaltv.getSeriesCollection().add(series);
                canaltv = em.merge(canaltv);
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
            if (canaltvNew != null) {
                canaltvNew = em.getReference(canaltvNew.getClass(), canaltvNew.getCodigo());
                series.setCanaltv(canaltvNew);
            }
            series = em.merge(series);
            if (canaltvOld != null && !canaltvOld.equals(canaltvNew)) {
                canaltvOld.getSeriesCollection().remove(series);
                canaltvOld = em.merge(canaltvOld);
            }
            if (canaltvNew != null && !canaltvNew.equals(canaltvOld)) {
                canaltvNew.getSeriesCollection().add(series);
                canaltvNew = em.merge(canaltvNew);
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
