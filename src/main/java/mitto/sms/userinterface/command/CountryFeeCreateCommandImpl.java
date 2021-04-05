package mitto.sms.userinterface.command;

import mitto.sms.userinterface.parsing.CountryFeeParserImpl;
import mitto.sms.hibernate.entity.CountryFee;
import mitto.sms.service.Service;
import mitto.sms.userinterface.UserInterface;
import mitto.sms.userinterface.parsing.exception.ParsingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Command for handling inputs in countryFee format
 * @see CountryFeeParserImpl
 */
@Component
public class CountryFeeCreateCommandImpl implements Command{
    //TODO SMSCreateCommandImpl and my class should be replaced with generic EntityCreateCommand (all logic are same)
    private final CountryFeeParserImpl parser;
    private Service service;
    private UserInterface userInterface;

    /**
     * CountryFeeCreate constructor
     * @param service {@link Service}
     * @param userInterface {@link UserInterface}
     */
    @Autowired
    public CountryFeeCreateCommandImpl(Service service, UserInterface userInterface){
        this.service = service;
        this.userInterface = userInterface;
        this.parser = new CountryFeeParserImpl();
    }

    /**
     * Processing is trying parse command and persist parsed CountryFee entity object
     * @param input for command we are trying process
     * @return true if entity was persisted, false otherwise
     * @throws ParsingException {@link ParsingException}
     */
    @Override
    public boolean processCommand(String input) throws ParsingException {
        Optional<CountryFee> optionalCountryFee = parser.parse(input);
        Boolean saved = optionalCountryFee.map(countryFee -> service.saveCountryFee(countryFee)).orElse(false);
        if (saved) {
            userInterface.print(optionalCountryFee.get().toString()+" saved");
        }
        return saved;
    }
}
