package mitto.sms.hibernate.dao;

import mitto.sms.hibernate.entity.SMS;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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
public class SmsDaoImpl implements SmsDao {

    @PersistenceContext
    private EntityManager entityManager;

    public SmsDaoImpl() {
    }


    @Override
    public void create(String sender, String recipient, String text) {
        create(new SMS(sender, recipient, text));
    }

    @Override
    public void create(SMS sms) {
        entityManager.persist(sms);
    }

    @Override
    public List<SMS> findALl() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SMS> cq = cb.createQuery(SMS.class);
        Root<SMS> rootEntry = cq.from(SMS.class);
        CriteriaQuery<SMS> all = cq.select(rootEntry);
        TypedQuery<SMS> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public SMS find(Long id) {
        return entityManager.find(SMS.class, id);
    }

    @Override
    public void update(SMS user) {
//        TODO
    }

    @Override
    public void delete(Long id) {
        SMS sms = find(id);
        if(sms != null)
            entityManager.remove(sms);
    }
}
