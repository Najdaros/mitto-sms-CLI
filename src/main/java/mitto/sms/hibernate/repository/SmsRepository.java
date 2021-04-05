package mitto.sms.hibernate.repository;

import javafx.util.Pair;
import mitto.sms.hibernate.entity.SMS;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Objects implements Repository interface allow us to do common operations – CRUD, and sorting for SMS entities
 */
public interface SmsRepository extends Repository<SMS>{
    /**
     * find top senders by count of messages
     * @param limit limit number of result (ordered from most occurred to less)
     * @return map where key represent sender name and value represent count of messages
     */
    LinkedHashMap<String, Long> findTopSenders(Integer limit);

    /**
     * Similar to findTopSenders, extended output by overall sum of prices for messages
     * @param limit limit number of result (ordered from most occurred to less)
     * @return map where key represent sender name and value is pair of number of message occurrences and overall sum of prices for messages
     */
    LinkedHashMap<String, Pair<Long, BigDecimal>> findTopSendersWithFee(Integer limit);
    /**
     * Similar to findTopSendersWithFee, working with countryName instead
     * @return map where key represent countryName name and value is pair of number of message occurrences and overall sum of prices for messages
     */
    LinkedHashMap<String, Pair<Long, BigDecimal>> getFeeCountryStats();

    /**
     * Finding all messages filtered by success column
     * success column hold information related to validity of sms to joining to country_fee
     * @param success parameter allows us to choose sms we want to get
     *                success true: valid sms connected to country_fee
     *                success false: sms has invalid country code (can't be calculated price for such sms)
     * @return success true: valid SMSs with relation to country_fee,
     *         success false: invalid SMSs, without country_fee
     *         success null: all SMSs
     */
    List<SMS> findAllSuccess(Boolean success);
}
