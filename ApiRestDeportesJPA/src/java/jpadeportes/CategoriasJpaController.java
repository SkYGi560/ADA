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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import jpadeportes.exceptions.IllegalOrphanException;
import jpadeportes.exceptions.NonexistentEntityException;

/**
 *
 * @author alumnosemi
 */
public class CategoriasJpaController implements Serializable {

    public CategoriasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Categorias categorias) {
        if (categorias.getDeportesCollection() == null) {
            categorias.setDeportesCollection(new ArrayList<Deportes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Deportes> attachedDeportesCollection = new ArrayList<Deportes>();
            for (Deportes deportesCollectionDeportesToAttach : categorias.getDeportesCollection()) {
                deportesCollectionDeportesToAttach = em.getReference(deportesCollectionDeportesToAttach.getClass(), deportesCollectionDeportesToAttach.getId());
                attachedDeportesCollection.add(deportesCollectionDeportesToAttach);
            }
            categorias.setDeportesCollection(attachedDeportesCollection);
            em.persist(categorias);
            for (Deportes deportesCollectionDeportes : categorias.getDeportesCollection()) {
                Categorias oldCategoriaIdOfDeportesCollectionDeportes = deportesCollectionDeportes.getCategoriaId();
                deportesCollectionDeportes.setCategoriaId(categorias);
                deportesCollectionDeportes = em.merge(deportesCollectionDeportes);
                if (oldCategoriaIdOfDeportesCollectionDeportes != null) {
                    oldCategoriaIdOfDeportesCollectionDeportes.getDeportesCollection().remove(deportesCollectionDeportes);
                    oldCategoriaIdOfDeportesCollectionDeportes = em.merge(oldCategoriaIdOfDeportesCollectionDeportes);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Categorias categorias) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categorias persistentCategorias = em.find(Categorias.class, categorias.getId());
            Collection<Deportes> deportesCollectionOld = persistentCategorias.getDeportesCollection();
            Collection<Deportes> deportesCollectionNew = categorias.getDeportesCollection();
            List<String> illegalOrphanMessages = null;
            for (Deportes deportesCollectionOldDeportes : deportesCollectionOld) {
                if (!deportesCollectionNew.contains(deportesCollectionOldDeportes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Deportes " + deportesCollectionOldDeportes + " since its categoriaId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Deportes> attachedDeportesCollectionNew = new ArrayList<Deportes>();
            for (Deportes deportesCollectionNewDeportesToAttach : deportesCollectionNew) {
                deportesCollectionNewDeportesToAttach = em.getReference(deportesCollectionNewDeportesToAttach.getClass(), deportesCollectionNewDeportesToAttach.getId());
                attachedDeportesCollectionNew.add(deportesCollectionNewDeportesToAttach);
            }
            deportesCollectionNew = attachedDeportesCollectionNew;
            categorias.setDeportesCollection(deportesCollectionNew);
            categorias = em.merge(categorias);
            for (Deportes deportesCollectionNewDeportes : deportesCollectionNew) {
                if (!deportesCollectionOld.contains(deportesCollectionNewDeportes)) {
                    Categorias oldCategoriaIdOfDeportesCollectionNewDeportes = deportesCollectionNewDeportes.getCategoriaId();
                    deportesCollectionNewDeportes.setCategoriaId(categorias);
                    deportesCollectionNewDeportes = em.merge(deportesCollectionNewDeportes);
                    if (oldCategoriaIdOfDeportesCollectionNewDeportes != null && !oldCategoriaIdOfDeportesCollectionNewDeportes.equals(categorias)) {
                        oldCategoriaIdOfDeportesCollectionNewDeportes.getDeportesCollection().remove(deportesCollectionNewDeportes);
                        oldCategoriaIdOfDeportesCollectionNewDeportes = em.merge(oldCategoriaIdOfDeportesCollectionNewDeportes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categorias.getId();
                if (findCategorias(id) == null) {
                    throw new NonexistentEntityException("The categorias with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categorias categorias;
            try {
                categorias = em.getReference(Categorias.class, id);
                categorias.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categorias with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Deportes> deportesCollectionOrphanCheck = categorias.getDeportesCollection();
            for (Deportes deportesCollectionOrphanCheckDeportes : deportesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Categorias (" + categorias + ") cannot be destroyed since the Deportes " + deportesCollectionOrphanCheckDeportes + " in its deportesCollection field has a non-nullable categoriaId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(categorias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Categorias> findCategoriasEntities() {
        return findCategoriasEntities(true, -1, -1);
    }

    public List<Categorias> findCategoriasEntities(int maxResults, int firstResult) {
        return findCategoriasEntities(false, maxResults, firstResult);
    }

    private List<Categorias> findCategoriasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categorias.class));
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

    public Categorias findCategorias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categorias.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categorias> rt = cq.from(Categorias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
