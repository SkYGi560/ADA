/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bdcamiones;

import bdcamiones.exceptions.NonexistentEntityException;
import bdcamiones.exceptions.PreexistingEntityException;
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
public class CamionesJpaController implements Serializable {

    public CamionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Camiones camiones) throws PreexistingEntityException, Exception {
        if (camiones.getCargasCollection() == null) {
            camiones.setCargasCollection(new ArrayList<Cargas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Cargas> attachedCargasCollection = new ArrayList<Cargas>();
            for (Cargas cargasCollectionCargasToAttach : camiones.getCargasCollection()) {
                cargasCollectionCargasToAttach = em.getReference(cargasCollectionCargasToAttach.getClass(), cargasCollectionCargasToAttach.getIdcarga());
                attachedCargasCollection.add(cargasCollectionCargasToAttach);
            }
            camiones.setCargasCollection(attachedCargasCollection);
            em.persist(camiones);
            for (Cargas cargasCollectionCargas : camiones.getCargasCollection()) {
                Camiones oldCamionOfCargasCollectionCargas = cargasCollectionCargas.getCamion();
                cargasCollectionCargas.setCamion(camiones);
                cargasCollectionCargas = em.merge(cargasCollectionCargas);
                if (oldCamionOfCargasCollectionCargas != null) {
                    oldCamionOfCargasCollectionCargas.getCargasCollection().remove(cargasCollectionCargas);
                    oldCamionOfCargasCollectionCargas = em.merge(oldCamionOfCargasCollectionCargas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCamiones(camiones.getMatricula()) != null) {
                throw new PreexistingEntityException("Camiones " + camiones + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Camiones camiones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Camiones persistentCamiones = em.find(Camiones.class, camiones.getMatricula());
            Collection<Cargas> cargasCollectionOld = persistentCamiones.getCargasCollection();
            Collection<Cargas> cargasCollectionNew = camiones.getCargasCollection();
            Collection<Cargas> attachedCargasCollectionNew = new ArrayList<Cargas>();
            for (Cargas cargasCollectionNewCargasToAttach : cargasCollectionNew) {
                cargasCollectionNewCargasToAttach = em.getReference(cargasCollectionNewCargasToAttach.getClass(), cargasCollectionNewCargasToAttach.getIdcarga());
                attachedCargasCollectionNew.add(cargasCollectionNewCargasToAttach);
            }
            cargasCollectionNew = attachedCargasCollectionNew;
            camiones.setCargasCollection(cargasCollectionNew);
            camiones = em.merge(camiones);
            for (Cargas cargasCollectionOldCargas : cargasCollectionOld) {
                if (!cargasCollectionNew.contains(cargasCollectionOldCargas)) {
                    cargasCollectionOldCargas.setCamion(null);
                    cargasCollectionOldCargas = em.merge(cargasCollectionOldCargas);
                }
            }
            for (Cargas cargasCollectionNewCargas : cargasCollectionNew) {
                if (!cargasCollectionOld.contains(cargasCollectionNewCargas)) {
                    Camiones oldCamionOfCargasCollectionNewCargas = cargasCollectionNewCargas.getCamion();
                    cargasCollectionNewCargas.setCamion(camiones);
                    cargasCollectionNewCargas = em.merge(cargasCollectionNewCargas);
                    if (oldCamionOfCargasCollectionNewCargas != null && !oldCamionOfCargasCollectionNewCargas.equals(camiones)) {
                        oldCamionOfCargasCollectionNewCargas.getCargasCollection().remove(cargasCollectionNewCargas);
                        oldCamionOfCargasCollectionNewCargas = em.merge(oldCamionOfCargasCollectionNewCargas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = camiones.getMatricula();
                if (findCamiones(id) == null) {
                    throw new NonexistentEntityException("The camiones with id " + id + " no longer exists.");
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
            Camiones camiones;
            try {
                camiones = em.getReference(Camiones.class, id);
                camiones.getMatricula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The camiones with id " + id + " no longer exists.", enfe);
            }
            Collection<Cargas> cargasCollection = camiones.getCargasCollection();
            for (Cargas cargasCollectionCargas : cargasCollection) {
                cargasCollectionCargas.setCamion(null);
                cargasCollectionCargas = em.merge(cargasCollectionCargas);
            }
            em.remove(camiones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Camiones> findCamionesEntities() {
        return findCamionesEntities(true, -1, -1);
    }

    public List<Camiones> findCamionesEntities(int maxResults, int firstResult) {
        return findCamionesEntities(false, maxResults, firstResult);
    }

    private List<Camiones> findCamionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Camiones.class));
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

    public Camiones findCamiones(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Camiones.class, id);
        } finally {
            em.close();
        }
    }

    public int getCamionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Camiones> rt = cq.from(Camiones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
