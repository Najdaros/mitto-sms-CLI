package mitto.sms.service;

import mitto.sms.hibernate.entity.CountryFee;
import mitto.sms.hibernate.entity.SMS;

import java.util.List;

public interface Service {

    boolean isCountryFeeEnabled();
    void setCountryFeeEnabled(boolean enabled);
    boolean saveCountryFee(CountryFee countryFee);
    boolean saveSMS(SMS sms);

    List<String> getTopSendersFormatted(Integer limit);
    List<String> getCountryFeeFormatted();
}
