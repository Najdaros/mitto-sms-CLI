package mitto.sms.hibernate.repository;

import javafx.util.Pair;
import mitto.sms.hibernate.entity.SMS;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

public interface SmsRepository extends Repository<SMS>{
    LinkedHashMap<String, Long> findTopSenders(Integer limit);
    LinkedHashMap<String, Pair<Long, BigDecimal>> findTopSendersWithFee(Integer limit);
    LinkedHashMap<String, Pair<Long, BigDecimal>> getFeeCountryStats();
    List<SMS> findAllSuccess(Boolean success);
}
