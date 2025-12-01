/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bibliotecah3;

import bibliotecah3.exceptions.NonexistentEntityException;
import bibliotecah3.exceptions.PreexistingEntityException;
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
public class AutoresJpaController implements Serializable {

    public AutoresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Autores autores) throws PreexistingEntityException, Exception {
        if (autores.getLibrosCollection() == null) {
            autores.setLibrosCollection(new ArrayList<Libros>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Libros> attachedLibrosCollection = new ArrayList<Libros>();
            for (Libros librosCollectionLibrosToAttach : autores.getLibrosCollection()) {
                librosCollectionLibrosToAttach = em.getReference(librosCollectionLibrosToAttach.getClass(), librosCollectionLibrosToAttach.getId());
                attachedLibrosCollection.add(librosCollectionLibrosToAttach);
            }
            autores.setLibrosCollection(attachedLibrosCollection);
            em.persist(autores);
            for (Libros librosCollectionLibros : autores.getLibrosCollection()) {
                librosCollectionLibros.getAutoresCollection().add(autores);
                librosCollectionLibros = em.merge(librosCollectionLibros);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAutores(autores.getCod()) != null) {
                throw new PreexistingEntityException("Autores " + autores + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Autores autores) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Autores persistentAutores = em.find(Autores.class, autores.getCod());
            Collection<Libros> librosCollectionOld = persistentAutores.getLibrosCollection();
            Collection<Libros> librosCollectionNew = autores.getLibrosCollection();
            Collection<Libros> attachedLibrosCollectionNew = new ArrayList<Libros>();
            for (Libros librosCollectionNewLibrosToAttach : librosCollectionNew) {
                librosCollectionNewLibrosToAttach = em.getReference(librosCollectionNewLibrosToAttach.getClass(), librosCollectionNewLibrosToAttach.getId());
                attachedLibrosCollectionNew.add(librosCollectionNewLibrosToAttach);
            }
            librosCollectionNew = attachedLibrosCollectionNew;
            autores.setLibrosCollection(librosCollectionNew);
            autores = em.merge(autores);
            for (Libros librosCollectionOldLibros : librosCollectionOld) {
                if (!librosCollectionNew.contains(librosCollectionOldLibros)) {
                    librosCollectionOldLibros.getAutoresCollection().remove(autores);
                    librosCollectionOldLibros = em.merge(librosCollectionOldLibros);
                }
            }
            for (Libros librosCollectionNewLibros : librosCollectionNew) {
                if (!librosCollectionOld.contains(librosCollectionNewLibros)) {
                    librosCollectionNewLibros.getAutoresCollection().add(autores);
                    librosCollectionNewLibros = em.merge(librosCollectionNewLibros);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = autores.getCod();
                if (findAutores(id) == null) {
                    throw new NonexistentEntityException("The autores with id " + id + " no longer exists.");
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
            Autores autores;
            try {
                autores = em.getReference(Autores.class, id);
                autores.getCod();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The autores with id " + id + " no longer exists.", enfe);
            }
            Collection<Libros> librosCollection = autores.getLibrosCollection();
            for (Libros librosCollectionLibros : librosCollection) {
                librosCollectionLibros.getAutoresCollection().remove(autores);
                librosCollectionLibros = em.merge(librosCollectionLibros);
            }
            em.remove(autores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Autores> findAutoresEntities() {
        return findAutoresEntities(true, -1, -1);
    }

    public List<Autores> findAutoresEntities(int maxResults, int firstResult) {
        return findAutoresEntities(false, maxResults, firstResult);
    }

    private List<Autores> findAutoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Autores.class));
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

    public Autores findAutores(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Autores.class, id);
        } finally {
            em.close();
        }
    }

    public int getAutoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Autores> rt = cq.from(Autores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
