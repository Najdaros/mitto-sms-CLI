package mitto.sms.userinterface.command;

import mitto.sms.userinterface.parsing.CountryFeeParserImpl;
import mitto.sms.hibernate.entity.CountryFee;
import mitto.sms.service.Service;
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
     * @param service {@link Service}
     * @param userInterface {@link UserInterface}
     */
    @Autowired
    public CountryFeeCreateCommandImpl(Service service, UserInterface userInterface) {
        super(service, userInterface, new CountryFeeParserImpl());
    }
}
