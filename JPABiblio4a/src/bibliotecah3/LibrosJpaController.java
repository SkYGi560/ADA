/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bibliotecah3;

import bibliotecah3.exceptions.NonexistentEntityException;
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

/**
 *
 * @author alumno
 */
public class LibrosJpaController implements Serializable {

    public LibrosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Libros libros) {
        if (libros.getAutoresCollection() == null) {
            libros.setAutoresCollection(new ArrayList<Autores>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Autores> attachedAutoresCollection = new ArrayList<Autores>();
            for (Autores autoresCollectionAutoresToAttach : libros.getAutoresCollection()) {
                autoresCollectionAutoresToAttach = em.getReference(autoresCollectionAutoresToAttach.getClass(), autoresCollectionAutoresToAttach.getCod());
                attachedAutoresCollection.add(autoresCollectionAutoresToAttach);
            }
            libros.setAutoresCollection(attachedAutoresCollection);
            em.persist(libros);
            for (Autores autoresCollectionAutores : libros.getAutoresCollection()) {
                autoresCollectionAutores.getLibrosCollection().add(libros);
                autoresCollectionAutores = em.merge(autoresCollectionAutores);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Libros libros) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Libros persistentLibros = em.find(Libros.class, libros.getId());
            Collection<Autores> autoresCollectionOld = persistentLibros.getAutoresCollection();
            Collection<Autores> autoresCollectionNew = libros.getAutoresCollection();
            Collection<Autores> attachedAutoresCollectionNew = new ArrayList<Autores>();
            for (Autores autoresCollectionNewAutoresToAttach : autoresCollectionNew) {
                autoresCollectionNewAutoresToAttach = em.getReference(autoresCollectionNewAutoresToAttach.getClass(), autoresCollectionNewAutoresToAttach.getCod());
                attachedAutoresCollectionNew.add(autoresCollectionNewAutoresToAttach);
            }
            autoresCollectionNew = attachedAutoresCollectionNew;
            libros.setAutoresCollection(autoresCollectionNew);
            libros = em.merge(libros);
            for (Autores autoresCollectionOldAutores : autoresCollectionOld) {
                if (!autoresCollectionNew.contains(autoresCollectionOldAutores)) {
                    autoresCollectionOldAutores.getLibrosCollection().remove(libros);
                    autoresCollectionOldAutores = em.merge(autoresCollectionOldAutores);
                }
            }
            for (Autores autoresCollectionNewAutores : autoresCollectionNew) {
                if (!autoresCollectionOld.contains(autoresCollectionNewAutores)) {
                    autoresCollectionNewAutores.getLibrosCollection().add(libros);
                    autoresCollectionNewAutores = em.merge(autoresCollectionNewAutores);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = libros.getId();
                if (findLibros(id) == null) {
                    throw new NonexistentEntityException("The libros with id " + id + " no longer exists.");
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
            Libros libros;
            try {
                libros = em.getReference(Libros.class, id);
                libros.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The libros with id " + id + " no longer exists.", enfe);
            }
            Collection<Autores> autoresCollection = libros.getAutoresCollection();
            for (Autores autoresCollectionAutores : autoresCollection) {
                autoresCollectionAutores.getLibrosCollection().remove(libros);
                autoresCollectionAutores = em.merge(autoresCollectionAutores);
            }
            em.remove(libros);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Libros> findLibrosEntities() {
        return findLibrosEntities(true, -1, -1);
    }

    public List<Libros> findLibrosEntities(int maxResults, int firstResult) {
        return findLibrosEntities(false, maxResults, firstResult);
    }

    private List<Libros> findLibrosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Libros.class));
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

    public Libros findLibros(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Libros.class, id);
        } finally {
            em.close();
        }
    }

    public int getLibrosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Libros> rt = cq.from(Libros.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
