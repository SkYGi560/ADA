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
import jpaworldnew.exceptions.PreexistingEntityException;

/**
 *
 * @author alumno
 */
public class CountryJpaController implements Serializable {

    public CountryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Country country) throws PreexistingEntityException, Exception {
        if (country.getCityCollection() == null) {
            country.setCityCollection(new ArrayList<City>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            City capital = country.getCapital();
            if (capital != null) {
                capital = em.getReference(capital.getClass(), capital.getId());
                country.setCapital(capital);
            }
            Collection<City> attachedCityCollection = new ArrayList<City>();
            for (City cityCollectionCityToAttach : country.getCityCollection()) {
                cityCollectionCityToAttach = em.getReference(cityCollectionCityToAttach.getClass(), cityCollectionCityToAttach.getId());
                attachedCityCollection.add(cityCollectionCityToAttach);
            }
            country.setCityCollection(attachedCityCollection);
            em.persist(country);
            if (capital != null) {
                capital.getCountryCollection().add(country);
                capital = em.merge(capital);
            }
            for (City cityCollectionCity : country.getCityCollection()) {
                Country oldCountrycodeOfCityCollectionCity = cityCollectionCity.getCountrycode();
                cityCollectionCity.setCountrycode(country);
                cityCollectionCity = em.merge(cityCollectionCity);
                if (oldCountrycodeOfCityCollectionCity != null) {
                    oldCountrycodeOfCityCollectionCity.getCityCollection().remove(cityCollectionCity);
                    oldCountrycodeOfCityCollectionCity = em.merge(oldCountrycodeOfCityCollectionCity);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCountry(country.getCode()) != null) {
                throw new PreexistingEntityException("Country " + country + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Country country) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Country persistentCountry = em.find(Country.class, country.getCode());
            City capitalOld = persistentCountry.getCapital();
            City capitalNew = country.getCapital();
            Collection<City> cityCollectionOld = persistentCountry.getCityCollection();
            Collection<City> cityCollectionNew = country.getCityCollection();
            List<String> illegalOrphanMessages = null;
            for (City cityCollectionOldCity : cityCollectionOld) {
                if (!cityCollectionNew.contains(cityCollectionOldCity)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain City " + cityCollectionOldCity + " since its countrycode field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (capitalNew != null) {
                capitalNew = em.getReference(capitalNew.getClass(), capitalNew.getId());
                country.setCapital(capitalNew);
            }
            Collection<City> attachedCityCollectionNew = new ArrayList<City>();
            for (City cityCollectionNewCityToAttach : cityCollectionNew) {
                cityCollectionNewCityToAttach = em.getReference(cityCollectionNewCityToAttach.getClass(), cityCollectionNewCityToAttach.getId());
                attachedCityCollectionNew.add(cityCollectionNewCityToAttach);
            }
            cityCollectionNew = attachedCityCollectionNew;
            country.setCityCollection(cityCollectionNew);
            country = em.merge(country);
            if (capitalOld != null && !capitalOld.equals(capitalNew)) {
                capitalOld.getCountryCollection().remove(country);
                capitalOld = em.merge(capitalOld);
            }
            if (capitalNew != null && !capitalNew.equals(capitalOld)) {
                capitalNew.getCountryCollection().add(country);
                capitalNew = em.merge(capitalNew);
            }
            for (City cityCollectionNewCity : cityCollectionNew) {
                if (!cityCollectionOld.contains(cityCollectionNewCity)) {
                    Country oldCountrycodeOfCityCollectionNewCity = cityCollectionNewCity.getCountrycode();
                    cityCollectionNewCity.setCountrycode(country);
                    cityCollectionNewCity = em.merge(cityCollectionNewCity);
                    if (oldCountrycodeOfCityCollectionNewCity != null && !oldCountrycodeOfCityCollectionNewCity.equals(country)) {
                        oldCountrycodeOfCityCollectionNewCity.getCityCollection().remove(cityCollectionNewCity);
                        oldCountrycodeOfCityCollectionNewCity = em.merge(oldCountrycodeOfCityCollectionNewCity);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = country.getCode();
                if (findCountry(id) == null) {
                    throw new NonexistentEntityException("The country with id " + id + " no longer exists.");
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
            Country country;
            try {
                country = em.getReference(Country.class, id);
                country.getCode();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The country with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<City> cityCollectionOrphanCheck = country.getCityCollection();
            for (City cityCollectionOrphanCheckCity : cityCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Country (" + country + ") cannot be destroyed since the City " + cityCollectionOrphanCheckCity + " in its cityCollection field has a non-nullable countrycode field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            City capital = country.getCapital();
            if (capital != null) {
                capital.getCountryCollection().remove(country);
                capital = em.merge(capital);
            }
            em.remove(country);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Country> findCountryEntities() {
        return findCountryEntities(true, -1, -1);
    }

    public List<Country> findCountryEntities(int maxResults, int firstResult) {
        return findCountryEntities(false, maxResults, firstResult);
    }

    private List<Country> findCountryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Country.class));
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

    public Country findCountry(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Country.class, id);
        } finally {
            em.close();
        }
    }

    public int getCountryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Country> rt = cq.from(Country.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
