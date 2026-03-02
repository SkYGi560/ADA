/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpaworldnew;

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
import jpaworldnew.exceptions.IllegalOrphanException;
import jpaworldnew.exceptions.NonexistentEntityException;

/**
 *
 * @author alumno
 */
public class CityJpaController implements Serializable {

    public CityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(City city) throws IllegalOrphanException {
        if (city.getCountryCollection() == null) {
            city.setCountryCollection(new ArrayList<Country>());
        }
        List<String> illegalOrphanMessages = null;
        Country countrycodeOrphanCheck = city.getCountrycode();
        if (countrycodeOrphanCheck != null) {
            City oldCapitalOfCountrycode = countrycodeOrphanCheck.getCapital();
            if (oldCapitalOfCountrycode != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Country " + countrycodeOrphanCheck + " already has an item of type City whose countrycode column cannot be null. Please make another selection for the countrycode field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Country countrycode = city.getCountrycode();
            if (countrycode != null) {
                countrycode = em.getReference(countrycode.getClass(), countrycode.getCode());
                city.setCountrycode(countrycode);
            }
            Collection<Country> attachedCountryCollection = new ArrayList<Country>();
            for (Country countryCollectionCountryToAttach : city.getCountryCollection()) {
                countryCollectionCountryToAttach = em.getReference(countryCollectionCountryToAttach.getClass(), countryCollectionCountryToAttach.getCode());
                attachedCountryCollection.add(countryCollectionCountryToAttach);
            }
            city.setCountryCollection(attachedCountryCollection);
            em.persist(city);
            if (countrycode != null) {
                countrycode.setCapital(city);
                countrycode = em.merge(countrycode);
            }
            for (Country countryCollectionCountry : city.getCountryCollection()) {
                City oldCapitalOfCountryCollectionCountry = countryCollectionCountry.getCapital();
                countryCollectionCountry.setCapital(city);
                countryCollectionCountry = em.merge(countryCollectionCountry);
                if (oldCapitalOfCountryCollectionCountry != null) {
                    oldCapitalOfCountryCollectionCountry.getCountryCollection().remove(countryCollectionCountry);
                    oldCapitalOfCountryCollectionCountry = em.merge(oldCapitalOfCountryCollectionCountry);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(City city) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            City persistentCity = em.find(City.class, city.getId());
            Country countrycodeOld = persistentCity.getCountrycode();
            Country countrycodeNew = city.getCountrycode();
            Collection<Country> countryCollectionOld = persistentCity.getCountryCollection();
            Collection<Country> countryCollectionNew = city.getCountryCollection();
            List<String> illegalOrphanMessages = null;
            if (countrycodeNew != null && !countrycodeNew.equals(countrycodeOld)) {
                City oldCapitalOfCountrycode = countrycodeNew.getCapital();
                if (oldCapitalOfCountrycode != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Country " + countrycodeNew + " already has an item of type City whose countrycode column cannot be null. Please make another selection for the countrycode field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (countrycodeNew != null) {
                countrycodeNew = em.getReference(countrycodeNew.getClass(), countrycodeNew.getCode());
                city.setCountrycode(countrycodeNew);
            }
            Collection<Country> attachedCountryCollectionNew = new ArrayList<Country>();
            for (Country countryCollectionNewCountryToAttach : countryCollectionNew) {
                countryCollectionNewCountryToAttach = em.getReference(countryCollectionNewCountryToAttach.getClass(), countryCollectionNewCountryToAttach.getCode());
                attachedCountryCollectionNew.add(countryCollectionNewCountryToAttach);
            }
            countryCollectionNew = attachedCountryCollectionNew;
            city.setCountryCollection(countryCollectionNew);
            city = em.merge(city);
            if (countrycodeOld != null && !countrycodeOld.equals(countrycodeNew)) {
                countrycodeOld.setCapital(null);
                countrycodeOld = em.merge(countrycodeOld);
            }
            if (countrycodeNew != null && !countrycodeNew.equals(countrycodeOld)) {
                countrycodeNew.setCapital(city);
                countrycodeNew = em.merge(countrycodeNew);
            }
            for (Country countryCollectionOldCountry : countryCollectionOld) {
                if (!countryCollectionNew.contains(countryCollectionOldCountry)) {
                    countryCollectionOldCountry.setCapital(null);
                    countryCollectionOldCountry = em.merge(countryCollectionOldCountry);
                }
            }
            for (Country countryCollectionNewCountry : countryCollectionNew) {
                if (!countryCollectionOld.contains(countryCollectionNewCountry)) {
                    City oldCapitalOfCountryCollectionNewCountry = countryCollectionNewCountry.getCapital();
                    countryCollectionNewCountry.setCapital(city);
                    countryCollectionNewCountry = em.merge(countryCollectionNewCountry);
                    if (oldCapitalOfCountryCollectionNewCountry != null && !oldCapitalOfCountryCollectionNewCountry.equals(city)) {
                        oldCapitalOfCountryCollectionNewCountry.getCountryCollection().remove(countryCollectionNewCountry);
                        oldCapitalOfCountryCollectionNewCountry = em.merge(oldCapitalOfCountryCollectionNewCountry);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = city.getId();
                if (findCity(id) == null) {
                    throw new NonexistentEntityException("The city with id " + id + " no longer exists.");
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
            City city;
            try {
                city = em.getReference(City.class, id);
                city.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The city with id " + id + " no longer exists.", enfe);
            }
            Country countrycode = city.getCountrycode();
            if (countrycode != null) {
                countrycode.setCapital(null);
                countrycode = em.merge(countrycode);
            }
            Collection<Country> countryCollection = city.getCountryCollection();
            for (Country countryCollectionCountry : countryCollection) {
                countryCollectionCountry.setCapital(null);
                countryCollectionCountry = em.merge(countryCollectionCountry);
            }
            em.remove(city);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<City> findCityEntities() {
        return findCityEntities(true, -1, -1);
    }

    public List<City> findCityEntities(int maxResults, int firstResult) {
        return findCityEntities(false, maxResults, firstResult);
    }

    private List<City> findCityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(City.class));
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

    public City findCity(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(City.class, id);
        } finally {
            em.close();
        }
    }

    public int getCityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<City> rt = cq.from(City.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
