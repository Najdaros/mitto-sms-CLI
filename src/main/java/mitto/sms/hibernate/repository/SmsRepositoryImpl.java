package mitto.sms.hibernate.repository;

import mitto.sms.hibernate.StatsDTO;
import mitto.sms.hibernate.entity.SMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@org.springframework.stereotype.Repository
@Transactional
public class SmsRepositoryImpl implements SmsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public SmsRepositoryImpl() {
    }

    @Override
    public boolean create(SMS sms) {
        entityManager.persist(sms);
        return true;
    }

    @Override
    public List<SMS> findAll() {
        return findAllSuccess(null);
    }

    @Override
    public SMS find(Integer id) {
        return entityManager.find(SMS.class, id);
    }

    @Override
    public List<SMS> findAllSuccess(Boolean success) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SMS> cq = cb.createQuery(SMS.class);
        Root<SMS> rootEntry = cq.from(SMS.class);
        CriteriaQuery<SMS> all = cq.select(rootEntry);
        if (success != null) {
            all = all.where(cb.equal(rootEntry.get("success"), success));
        }
        TypedQuery<SMS> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }


    @Override
    public List<StatsDTO> findTopSenders(Integer limit) {
        List<SMS> messages = findAll();
        HashMap<String, Long> senderFrequencyMap = getOccurrenceMap(messages, SMS::getSender);
        return senderFrequencyMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(limit)
                .map(entry -> new StatsDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<StatsDTO> findTopSendersWithFee(Integer limit) {
        List<SMS> messages = findAllSuccess(Boolean.TRUE);
        HashMap<String, Long> senderOccurrenceMap = getOccurrenceMap(messages, SMS::getSender);
        List<String> topSendersOrdered = senderOccurrenceMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(limit).map(Map.Entry::getKey).collect(Collectors.toList());

        HashMap<String, BigDecimal> sendersFeeMap = getFeeStatsMap(messages, SMS::getSender, sms -> topSendersOrdered.contains(sms.getSender()));
        return topSendersOrdered.stream()
                .map(sender -> new StatsDTO(sender, senderOccurrenceMap.get(sender),sendersFeeMap.get(sender)))
                .collect(Collectors.toList());
    }

    @Override
    public List<StatsDTO> getCountryFeeStats() {
        List<SMS> messages = findAllSuccess(Boolean.TRUE);
        HashMap<String, Long> senderOccurrenceMap = getOccurrenceMap(messages, sms -> sms.getCountryFee().getCountryName());
        HashMap<String, BigDecimal> countryFeeMap = getFeeStatsMap(messages, sms -> sms.getCountryFee().getCountryName(), sms -> true);
        return senderOccurrenceMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .map(entry -> new StatsDTO(entry.getKey(), entry.getValue(), countryFeeMap.get(entry.getKey())))
                .collect(Collectors.toList());
    }

    private HashMap<String, BigDecimal> getFeeStatsMap(List<SMS> messages, Function<SMS, String> keyFunction, Predicate<SMS> filter) {
        if (messages == null) {
             return new HashMap<>();
        }
        return messages.stream()
                .filter(filter)
                .collect(Collectors.groupingBy(
                        keyFunction,
                        HashMap::new, // can be skipped
                        Collectors.reducing(BigDecimal.ZERO, sms -> sms.getCountryFee().getPrice(), BigDecimal::add)));
    }

    private HashMap<String, Long> getOccurrenceMap(List<SMS> messages, Function<SMS, String> keyFunction) {
        return messages.stream().collect(
                Collectors.groupingBy(
                        keyFunction,
                        HashMap::new,
                        Collectors.counting()));
    }


}
