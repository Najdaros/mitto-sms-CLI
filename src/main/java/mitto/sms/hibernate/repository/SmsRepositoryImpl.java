package mitto.sms.hibernate.repository;

import javafx.util.Pair;
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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Filter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public LinkedHashMap<String, Long> findTopSenders(Integer limit) {
        List<SMS> messages = findAll();
        HashMap<String, Long> senderFrequencyMap = getFrequencyMap(messages, SMS::getSender);
        return senderFrequencyMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(limit)
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new));
    }

    @Override
    public LinkedHashMap<String, Pair<Long, BigDecimal>> findTopSendersWithFee(Integer limit) {
        List<SMS> messages = findAllSuccess(Boolean.TRUE);
        HashMap<String, Long> senderFrequencyMap = getFrequencyMap(messages, SMS::getSender);
        List<String> topSendersOrdered = senderFrequencyMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(limit).map(Map.Entry::getKey).collect(Collectors.toList());

        HashMap<String, BigDecimal> sendersFeeMap = getFeeStatsMap(messages, SMS::getSender, sms -> topSendersOrdered.contains(sms.getSender()));
        return topSendersOrdered.stream().collect(Collectors.toMap(Function.identity(),
                sender->new Pair<>(senderFrequencyMap.get(sender), sendersFeeMap.get(sender)),
                        (v1, v2) -> v1,
                        LinkedHashMap::new));
    }

    @Override
    public LinkedHashMap<String, Pair<Long, BigDecimal>> getFeeCountryStats() {
        List<SMS> messages = findAllSuccess(Boolean.TRUE);
        HashMap<String, Long> senderFrequencyMap = getFrequencyMap(messages, sms -> sms.getCountryFee().getCountryName());
        HashMap<String, BigDecimal> countryFeeMap = getFeeStatsMap(messages, sms -> sms.getCountryFee().getCountryName(), sms -> true);
        return senderFrequencyMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey,
                entry->new Pair<>(entry.getValue(), countryFeeMap.get(entry.getKey())),
                (v1, v2) -> v1,
                LinkedHashMap::new));
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

    private HashMap<String, Long> getFrequencyMap(List<SMS> messages, Function<SMS, String> keyFunction) {
        return messages.stream().collect(
                Collectors.groupingBy(
                        keyFunction,
                        HashMap::new,
                        Collectors.counting()));
    }


}
