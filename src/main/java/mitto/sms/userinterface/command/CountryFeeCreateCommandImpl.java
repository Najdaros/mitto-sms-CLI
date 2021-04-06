package mitto.sms.userinterface.command;

import mitto.sms.userinterface.parsing.CountryFeeParserImpl;
import mitto.sms.hibernate.entity.CountryFee;
import mitto.sms.service.SmsService;
import mitto.sms.userinterface.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Command for handling inputs in countryFee format
 * @see CountryFeeParserImpl
 */
@Component
public class CountryFeeCreateCommandImpl extends AbstractEntityCreateCommandImpl<CountryFee>{
    /**
     * CountryFeeCreateCommandImpl constructor
     * @param smsService {@link SmsService}
     * @param userInterface {@link UserInterface}
     */
    @Autowired
    public CountryFeeCreateCommandImpl(SmsService smsService, UserInterface userInterface) {
        super(smsService, userInterface, new CountryFeeParserImpl());
    }
}
