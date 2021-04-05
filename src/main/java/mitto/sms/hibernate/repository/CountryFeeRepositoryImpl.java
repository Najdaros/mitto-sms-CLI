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

@org.springframework.stereotype.Repository
@Transactional
public class CountryFeeRepositoryImpl implements CountryFeeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public CountryFeeRepositoryImpl() {
    }

    @Override
    public boolean create(CountryFee sms) {
        if (find(sms.getCountryCode()) == null) {
            entityManager.persist(sms);
            return true;
        }
        return false;
    }

    @Override
    public List<CountryFee> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CountryFee> cq = cb.createQuery(CountryFee.class);
        Root<CountryFee> rootEntry = cq.from(CountryFee.class);
        CriteriaQuery<CountryFee> all = cq.select(rootEntry);
        TypedQuery<CountryFee> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public CountryFee find(Integer id) {
        return entityManager.find(CountryFee.class, id);
    }
}
