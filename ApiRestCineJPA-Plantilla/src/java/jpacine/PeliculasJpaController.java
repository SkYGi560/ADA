package jpacine;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import jpacine.exceptions.NonexistentEntityException;

public class PeliculasJpaController implements Serializable {

    public PeliculasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Peliculas peliculas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tematicas tematica = peliculas.getTematica();
            if (tematica != null) {
                tematica = em.getReference(tematica.getClass(), tematica.getCodigo());
                peliculas.setTematica(tematica);
            }
            em.persist(peliculas);
            if (tematica != null) {
                tematica.getPeliculasCollection().add(peliculas);
                tematica = em.merge(tematica);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Peliculas peliculas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Peliculas persistentPeliculas = em.find(Peliculas.class, peliculas.getCodigo());
            Tematicas tematicaOld = persistentPeliculas.getTematica();
            Tematicas tematicaNew = peliculas.getTematica();
            if (tematicaNew != null) {
                tematicaNew = em.getReference(tematicaNew.getClass(), tematicaNew.getCodigo());
                peliculas.setTematica(tematicaNew);
            }
            peliculas = em.merge(peliculas);
            if (tematicaOld != null && !tematicaOld.equals(tematicaNew)) {
                tematicaOld.getPeliculasCollection().remove(peliculas);
                tematicaOld = em.merge(tematicaOld);
            }
            if (tematicaNew != null && !tematicaNew.equals(tematicaOld)) {
                tematicaNew.getPeliculasCollection().add(peliculas);
                tematicaNew = em.merge(tematicaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = peliculas.getCodigo();
                if (findPeliculas(id) == null) {
                    throw new NonexistentEntityException("The peliculas with id " + id + " no longer exists.");
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
            Peliculas peliculas;
            try {
                peliculas = em.getReference(Peliculas.class, id);
                peliculas.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The peliculas with id " + id + " no longer exists.", enfe);
            }
            Tematicas tematica = peliculas.getTematica();
            if (tematica != null) {
                tematica.getPeliculasCollection().remove(peliculas);
                tematica = em.merge(tematica);
            }
            em.remove(peliculas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Peliculas> findPeliculasEntities() {
        return findPeliculasEntities(true, -1, -1);
    }

    public List<Peliculas> findPeliculasEntities(int maxResults, int firstResult) {
        return findPeliculasEntities(false, maxResults, firstResult);
    }

    private List<Peliculas> findPeliculasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Peliculas.class));
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

    public Peliculas findPeliculas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Peliculas.class, id);
        } finally {
            em.close();
        }
    }

    public int getPeliculasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Peliculas> rt = cq.from(Peliculas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
