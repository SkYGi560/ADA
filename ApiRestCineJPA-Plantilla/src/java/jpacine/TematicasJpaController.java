package jpacine;

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
import jpacine.exceptions.NonexistentEntityException;

public class TematicasJpaController implements Serializable {

    public TematicasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tematicas tematicas) {
        if (tematicas.getPeliculasCollection() == null) {
            tematicas.setPeliculasCollection(new ArrayList<Peliculas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Peliculas> attachedPeliculasCollection = new ArrayList<Peliculas>();
            for (Peliculas peliculasCollectionPeliculasToAttach : tematicas.getPeliculasCollection()) {
                peliculasCollectionPeliculasToAttach = em.getReference(peliculasCollectionPeliculasToAttach.getClass(), peliculasCollectionPeliculasToAttach.getCodigo());
                attachedPeliculasCollection.add(peliculasCollectionPeliculasToAttach);
            }
            tematicas.setPeliculasCollection(attachedPeliculasCollection);
            em.persist(tematicas);
            for (Peliculas peliculasCollectionPeliculas : tematicas.getPeliculasCollection()) {
                Tematicas oldTematicaOfPeliculasCollectionPeliculas = peliculasCollectionPeliculas.getTematica();
                peliculasCollectionPeliculas.setTematica(tematicas);
                peliculasCollectionPeliculas = em.merge(peliculasCollectionPeliculas);
                if (oldTematicaOfPeliculasCollectionPeliculas != null) {
                    oldTematicaOfPeliculasCollectionPeliculas.getPeliculasCollection().remove(peliculasCollectionPeliculas);
                    oldTematicaOfPeliculasCollectionPeliculas = em.merge(oldTematicaOfPeliculasCollectionPeliculas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tematicas tematicas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tematicas persistentTematicas = em.find(Tematicas.class, tematicas.getCodigo());
            Collection<Peliculas> peliculasCollectionOld = persistentTematicas.getPeliculasCollection();
            Collection<Peliculas> peliculasCollectionNew = tematicas.getPeliculasCollection();
            Collection<Peliculas> attachedPeliculasCollectionNew = new ArrayList<Peliculas>();
            for (Peliculas peliculasCollectionNewPeliculasToAttach : peliculasCollectionNew) {
                peliculasCollectionNewPeliculasToAttach = em.getReference(peliculasCollectionNewPeliculasToAttach.getClass(), peliculasCollectionNewPeliculasToAttach.getCodigo());
                attachedPeliculasCollectionNew.add(peliculasCollectionNewPeliculasToAttach);
            }
            peliculasCollectionNew = attachedPeliculasCollectionNew;
            tematicas.setPeliculasCollection(peliculasCollectionNew);
            tematicas = em.merge(tematicas);
            for (Peliculas peliculasCollectionOldPeliculas : peliculasCollectionOld) {
                if (!peliculasCollectionNew.contains(peliculasCollectionOldPeliculas)) {
                    peliculasCollectionOldPeliculas.setTematica(null);
                    peliculasCollectionOldPeliculas = em.merge(peliculasCollectionOldPeliculas);
                }
            }
            for (Peliculas peliculasCollectionNewPeliculas : peliculasCollectionNew) {
                if (!peliculasCollectionOld.contains(peliculasCollectionNewPeliculas)) {
                    Tematicas oldTematicaOfPeliculasCollectionNewPeliculas = peliculasCollectionNewPeliculas.getTematica();
                    peliculasCollectionNewPeliculas.setTematica(tematicas);
                    peliculasCollectionNewPeliculas = em.merge(peliculasCollectionNewPeliculas);
                    if (oldTematicaOfPeliculasCollectionNewPeliculas != null && !oldTematicaOfPeliculasCollectionNewPeliculas.equals(tematicas)) {
                        oldTematicaOfPeliculasCollectionNewPeliculas.getPeliculasCollection().remove(peliculasCollectionNewPeliculas);
                        oldTematicaOfPeliculasCollectionNewPeliculas = em.merge(oldTematicaOfPeliculasCollectionNewPeliculas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tematicas.getCodigo();
                if (findTematicas(id) == null) {
                    throw new NonexistentEntityException("The tematicas with id " + id + " no longer exists.");
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
            Tematicas tematicas;
            try {
                tematicas = em.getReference(Tematicas.class, id);
                tematicas.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tematicas with id " + id + " no longer exists.", enfe);
            }
            Collection<Peliculas> peliculasCollection = tematicas.getPeliculasCollection();
            for (Peliculas peliculasCollectionPeliculas : peliculasCollection) {
                peliculasCollectionPeliculas.setTematica(null);
                peliculasCollectionPeliculas = em.merge(peliculasCollectionPeliculas);
            }
            em.remove(tematicas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tematicas> findTematicasEntities() {
        return findTematicasEntities(true, -1, -1);
    }

    public List<Tematicas> findTematicasEntities(int maxResults, int firstResult) {
        return findTematicasEntities(false, maxResults, firstResult);
    }

    private List<Tematicas> findTematicasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tematicas.class));
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

    public Tematicas findTematicas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tematicas.class, id);
        } finally {
            em.close();
        }
    }

    public int getTematicasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tematicas> rt = cq.from(Tematicas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
