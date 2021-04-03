package mitto.sms.hibernate.dao;

import mitto.sms.hibernate.entity.Fee;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class FeeDaoImpl implements FeeDao {

    @PersistenceContext
    private EntityManager entityManager;

    public FeeDaoImpl() {
    }


    @Override
    public void create(Integer countryCode, String countryName, Float price) {
        create(new Fee(countryCode, countryName, price));
    }

    @Override
    public void create(Fee sms) {
        entityManager.persist(sms);
    }

    @Override
    public List<Fee> findALl() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Fee> cq = cb.createQuery(Fee.class);
        Root<Fee> rootEntry = cq.from(Fee.class);
        CriteriaQuery<Fee> all = cq.select(rootEntry);
        TypedQuery<Fee> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public Fee find(Integer id) {
        return entityManager.find(Fee.class, id);
    }

    @Override
    public void update(Fee user) {
//        TODO
    }

    @Override
    public void delete(Integer id) {
        Fee sms = find(id);
        if(sms != null)
            entityManager.remove(sms);
    }
}
