package mitto.sms.service;

import mitto.sms.hibernate.StatsDTO;
import mitto.sms.hibernate.entity.CountryFee;
import mitto.sms.hibernate.entity.SMS;
import mitto.sms.hibernate.repository.CountryFeeRepository;
import mitto.sms.hibernate.repository.SmsRepository;
import mitto.sms.hibernate.repository.SmsRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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

    private boolean isCountryFeeEnabled() {
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

    @Override
    public List<StatsDTO> getTopSendersStats(Integer limit) {
        List<StatsDTO>  result;
        if(isCountryFeeEnabled()) {
            result = smsRepository.findTopSendersWithFee(limit);
        } else {
            result =  smsRepository.findTopSenders(limit);
        }
        return result;
    }

    @Override
    public List<StatsDTO> getCountryFeeStats() {
        return smsRepository.getCountryFeeStats();
    }


}
