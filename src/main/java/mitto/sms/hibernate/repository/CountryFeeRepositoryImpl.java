package mitto.sms.hibernate.repository;

import mitto.sms.hibernate.entity.CountryFee;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Repository implementation providing common operations – CRUD, and sorting for CountryFee entities
 */
@org.springframework.stereotype.Repository
@Transactional
public class CountryFeeRepositoryImpl implements CountryFeeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Persists countryFee entity
     * @param countryFee entity to persis
     * @return true if entity is persisted
     */
    @Override
    public boolean create(CountryFee countryFee) {
        if (find(countryFee.getCountryCode()) == null) {
            entityManager.persist(countryFee);
            return true;
        }
        return false;
    }

    /**
     * Finding all entities
     * @return list of entities
     */
    @Override
    public List<CountryFee> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CountryFee> cq = cb.createQuery(CountryFee.class);
        Root<CountryFee> rootEntry = cq.from(CountryFee.class);
        CriteriaQuery<CountryFee> all = cq.select(rootEntry);
        TypedQuery<CountryFee> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    /**
     * Finding entity for given id
     * @param id id of entity we are searching
     * @return found entity or null
     */
    @Override
    public CountryFee find(Integer id) {
        return entityManager.find(CountryFee.class, id);
    }
}
