package mitto.sms.userinterface.command;

import mitto.sms.userinterface.parsing.SMSParserImpl;
import mitto.sms.userinterface.parsing.exception.ParsingException;
import mitto.sms.hibernate.entity.SMS;
import mitto.sms.service.Service;
import mitto.sms.userinterface.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Command for handling inputs in sms format
 * @see SMSParserImpl
 */
@Component
public class SMSCreateCommandImpl implements Command {
    //TODO CountryFeeCreateCommandImpl and my class should be replaced with generic EntityCreateCommand (all logic are same)
    private final SMSParserImpl parser;
    private Service service;
    private UserInterface userInterface;

    /**
     * CountryFeeCreate constructor
     * @param service {@link Service}
     * @param userInterface {@link UserInterface}
     */
    @Autowired
    public SMSCreateCommandImpl(Service service, UserInterface userInterface){
        this.service = service;
        this.userInterface = userInterface;
        this.parser = new SMSParserImpl();
    }

    /**
     * Processing is trying parse command and persist parsed SMS entity object
     * @param input for command we are trying process
     * @return true if entity was persisted, false otherwise
     * @throws ParsingException {@link ParsingException}
     */
    @Override
    public boolean processCommand(String input) throws ParsingException {
        Optional<SMS> optionalSms = parser.parse(input);
        Boolean saved = optionalSms.map(sms -> service.saveSMS(sms)).orElse(false);
        if (saved) {
            userInterface.displayMessage(optionalSms.get().toString()+" saved");
        }
        return saved;
    }
}
