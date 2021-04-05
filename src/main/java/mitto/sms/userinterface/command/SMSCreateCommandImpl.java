package mitto.sms.userinterface.command;

import mitto.sms.userinterface.parsing.SMSParserImpl;
import mitto.sms.userinterface.parsing.exception.ParsingException;
import mitto.sms.hibernate.entity.SMS;
import mitto.sms.service.Service;
import mitto.sms.userinterface.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SMSCreateCommandImpl implements Command{

    private final SMSParserImpl parser;
    private Service service;
    private UserInterface userInterface;

    @Autowired
    public SMSCreateCommandImpl(Service service, UserInterface userInterface){
        this.service = service;
        this.userInterface = userInterface;
        this.parser = new SMSParserImpl();
    }

    @Override
    public boolean processCommand(String input) throws ParsingException {
        Optional<SMS> optionalSms = parser.parse(input);
        Boolean saved = optionalSms.map(sms -> service.saveSMS(sms)).orElse(false);
        if (saved) {
            userInterface.print("Entity "+optionalSms.get().toString() +" is saved");
        }
        return saved;
    }
}
