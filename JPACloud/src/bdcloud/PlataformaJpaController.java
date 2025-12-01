/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bdcloud;

import bdcloud.exceptions.IllegalOrphanException;
import bdcloud.exceptions.NonexistentEntityException;
import bdcloud.exceptions.PreexistingEntityException;
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
public class PlataformaJpaController implements Serializable {

    public PlataformaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Plataforma plataforma) throws PreexistingEntityException, Exception {
        if (plataforma.getInfraestructuraCollection() == null) {
            plataforma.setInfraestructuraCollection(new ArrayList<Infraestructura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Infraestructura> attachedInfraestructuraCollection = new ArrayList<Infraestructura>();
            for (Infraestructura infraestructuraCollectionInfraestructuraToAttach : plataforma.getInfraestructuraCollection()) {
                infraestructuraCollectionInfraestructuraToAttach = em.getReference(infraestructuraCollectionInfraestructuraToAttach.getClass(), infraestructuraCollectionInfraestructuraToAttach.getIdService());
                attachedInfraestructuraCollection.add(infraestructuraCollectionInfraestructuraToAttach);
            }
            plataforma.setInfraestructuraCollection(attachedInfraestructuraCollection);
            em.persist(plataforma);
            for (Infraestructura infraestructuraCollectionInfraestructura : plataforma.getInfraestructuraCollection()) {
                Plataforma oldPlataformaOfInfraestructuraCollectionInfraestructura = infraestructuraCollectionInfraestructura.getPlataforma();
                infraestructuraCollectionInfraestructura.setPlataforma(plataforma);
                infraestructuraCollectionInfraestructura = em.merge(infraestructuraCollectionInfraestructura);
                if (oldPlataformaOfInfraestructuraCollectionInfraestructura != null) {
                    oldPlataformaOfInfraestructuraCollectionInfraestructura.getInfraestructuraCollection().remove(infraestructuraCollectionInfraestructura);
                    oldPlataformaOfInfraestructuraCollectionInfraestructura = em.merge(oldPlataformaOfInfraestructuraCollectionInfraestructura);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlataforma(plataforma.getIdPform()) != null) {
                throw new PreexistingEntityException("Plataforma " + plataforma + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Plataforma plataforma) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Plataforma persistentPlataforma = em.find(Plataforma.class, plataforma.getIdPform());
            Collection<Infraestructura> infraestructuraCollectionOld = persistentPlataforma.getInfraestructuraCollection();
            Collection<Infraestructura> infraestructuraCollectionNew = plataforma.getInfraestructuraCollection();
            List<String> illegalOrphanMessages = null;
            for (Infraestructura infraestructuraCollectionOldInfraestructura : infraestructuraCollectionOld) {
                if (!infraestructuraCollectionNew.contains(infraestructuraCollectionOldInfraestructura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Infraestructura " + infraestructuraCollectionOldInfraestructura + " since its plataforma field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Infraestructura> attachedInfraestructuraCollectionNew = new ArrayList<Infraestructura>();
            for (Infraestructura infraestructuraCollectionNewInfraestructuraToAttach : infraestructuraCollectionNew) {
                infraestructuraCollectionNewInfraestructuraToAttach = em.getReference(infraestructuraCollectionNewInfraestructuraToAttach.getClass(), infraestructuraCollectionNewInfraestructuraToAttach.getIdService());
                attachedInfraestructuraCollectionNew.add(infraestructuraCollectionNewInfraestructuraToAttach);
            }
            infraestructuraCollectionNew = attachedInfraestructuraCollectionNew;
            plataforma.setInfraestructuraCollection(infraestructuraCollectionNew);
            plataforma = em.merge(plataforma);
            for (Infraestructura infraestructuraCollectionNewInfraestructura : infraestructuraCollectionNew) {
                if (!infraestructuraCollectionOld.contains(infraestructuraCollectionNewInfraestructura)) {
                    Plataforma oldPlataformaOfInfraestructuraCollectionNewInfraestructura = infraestructuraCollectionNewInfraestructura.getPlataforma();
                    infraestructuraCollectionNewInfraestructura.setPlataforma(plataforma);
                    infraestructuraCollectionNewInfraestructura = em.merge(infraestructuraCollectionNewInfraestructura);
                    if (oldPlataformaOfInfraestructuraCollectionNewInfraestructura != null && !oldPlataformaOfInfraestructuraCollectionNewInfraestructura.equals(plataforma)) {
                        oldPlataformaOfInfraestructuraCollectionNewInfraestructura.getInfraestructuraCollection().remove(infraestructuraCollectionNewInfraestructura);
                        oldPlataformaOfInfraestructuraCollectionNewInfraestructura = em.merge(oldPlataformaOfInfraestructuraCollectionNewInfraestructura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = plataforma.getIdPform();
                if (findPlataforma(id) == null) {
                    throw new NonexistentEntityException("The plataforma with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Plataforma plataforma;
            try {
                plataforma = em.getReference(Plataforma.class, id);
                plataforma.getIdPform();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The plataforma with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Infraestructura> infraestructuraCollectionOrphanCheck = plataforma.getInfraestructuraCollection();
            for (Infraestructura infraestructuraCollectionOrphanCheckInfraestructura : infraestructuraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Plataforma (" + plataforma + ") cannot be destroyed since the Infraestructura " + infraestructuraCollectionOrphanCheckInfraestructura + " in its infraestructuraCollection field has a non-nullable plataforma field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(plataforma);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Plataforma> findPlataformaEntities() {
        return findPlataformaEntities(true, -1, -1);
    }

    public List<Plataforma> findPlataformaEntities(int maxResults, int firstResult) {
        return findPlataformaEntities(false, maxResults, firstResult);
    }

    private List<Plataforma> findPlataformaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Plataforma.class));
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

    public Plataforma findPlataforma(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Plataforma.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlataformaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Plataforma> rt = cq.from(Plataforma.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
