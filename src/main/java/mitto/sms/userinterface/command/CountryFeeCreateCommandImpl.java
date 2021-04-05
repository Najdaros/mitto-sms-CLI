package mitto.sms.userinterface.command;

import mitto.sms.userinterface.parsing.CountryFeeParserImpl;
import mitto.sms.userinterface.parsing.exception.ParsingException;
import mitto.sms.hibernate.entity.CountryFee;
import mitto.sms.service.Service;
import mitto.sms.userinterface.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CountryFeeCreateCommandImpl implements Command{

    private final CountryFeeParserImpl parser;
    private Service service;
    private UserInterface userInterface;

    @Autowired
    public CountryFeeCreateCommandImpl(Service service, UserInterface userInterface){
        this.service = service;
        this.userInterface = userInterface;
        this.parser = new CountryFeeParserImpl();
    }

    @Override
    public boolean processCommand(String input) throws ParsingException {
        Optional<CountryFee> optionalCountryFee = parser.parse(input);
        Boolean saved = optionalCountryFee.map(countryFee -> service.saveCountryFee(countryFee)).orElse(false);
        if (saved) {
            userInterface.print("Entity "+optionalCountryFee.get().toString() +" is saved");
        }
        return saved;
    }
}
