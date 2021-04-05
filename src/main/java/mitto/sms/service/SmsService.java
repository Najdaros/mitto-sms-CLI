package mitto.sms.service;

import javafx.util.Pair;
import mitto.sms.hibernate.entity.CountryFee;
import mitto.sms.hibernate.entity.SMS;
import mitto.sms.hibernate.repository.CountryFeeRepository;
import mitto.sms.hibernate.repository.SmsRepository;
import mitto.sms.hibernate.repository.SmsRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("smsService")
public class SmsService implements Service {

    private final CountryFeeRepository countryFeeRepository;
    private final SmsRepository smsRepository;
    private Boolean countryFeeEnabled = Boolean.FALSE;

    @Autowired
    SmsService(SmsRepositoryImpl smsRepository, CountryFeeRepository countryFeeRepository) {
        this.smsRepository = smsRepository;
        this.countryFeeRepository = countryFeeRepository;
    }

    @Override
    public boolean isCountryFeeEnabled() {
        return countryFeeEnabled;
    }

    @Override
    public void setCountryFeeEnabled(boolean enabled) {
        countryFeeEnabled = enabled;
    }


    @Override
    public boolean saveCountryFee(CountryFee countryFee) {
        return countryFeeRepository.create(countryFee);
    }

    @Override
    public boolean saveSMS(SMS sms) {
        Optional<CountryFee> countryFeeReference = countryFeeRepository.findAll().stream()
                .filter(countryFee -> sms.getRecipient().startsWith(countryFee.getCountryCode().toString())).findFirst();
        countryFeeReference.ifPresent(sms::setCountryFee);
        sms.setSuccess(countryFeeReference.isPresent());
        return smsRepository.create(sms);
    }

    public List<String> getTopSendersFormatted(Integer limit) {
        List<String> result;
        if(isCountryFeeEnabled()) {
            LinkedHashMap<String, Pair<Long, BigDecimal>> topSendersWithFee = smsRepository.findTopSendersWithFee(limit);
            result =  topSendersWithFee.entrySet()
                    .stream()
                    .map(entry -> outputValuesToString(
                            entry.getKey(),
                            longToUnsignedInt(entry.getValue().getKey()),
                            entry.getValue().getValue().toString()))
                    .collect(Collectors.toList());
        } else {
            LinkedHashMap<String, Long> topSMSSenders = smsRepository.findTopSenders(limit);
            result = topSMSSenders.entrySet()
                    .stream()
                    .map(entry -> outputValuesToString(
                            entry.getKey(),
                            longToUnsignedInt(entry.getValue())))
                    .collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public List<String> getCountryFeeFormatted() {
        LinkedHashMap<String, Pair<Long, BigDecimal>> feeCountryStats = smsRepository.getFeeCountryStats();
        return feeCountryStats.entrySet()
                .stream()
                .map(entry -> outputValuesToString(
                        entry.getKey(),
                        longToUnsignedInt(entry.getValue().getKey()),
                        entry.getValue().getValue().toString()))
                .collect(Collectors.toList());
    }

    private static String outputValuesToString(String... values){
        return String.join(" ", values);
    }

    private static String longToUnsignedInt(Long value) {
        return Integer.toUnsignedString(value.intValue());
    }


}
