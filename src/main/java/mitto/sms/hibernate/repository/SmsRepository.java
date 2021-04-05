package mitto.sms.hibernate.repository;

import mitto.sms.hibernate.StatsDTO;
import mitto.sms.hibernate.entity.SMS;

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
    List<StatsDTO> findTopSenders(Integer limit);

    /**
     * Similar to findTopSenders, extended output by overall sum of prices for messages
     * @param limit limit number of result (ordered from most occurred to less)
     * @return map where key represent sender name and value is pair of number of message occurrences and overall sum of prices for messages
     */
    List<StatsDTO> findTopSendersWithFee(Integer limit);
    /**
     * Similar to findTopSendersWithFee, working with countryName instead
     * @return map where key represent countryName name and value is pair of number of message occurrences and overall sum of prices for messages
     */
    List<StatsDTO> getgetCountryFeeStats();

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
