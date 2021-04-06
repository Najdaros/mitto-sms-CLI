package mitto.sms.userinterface.command;

import mitto.sms.userinterface.parsing.SMSParserImpl;
import mitto.sms.hibernate.entity.SMS;
import mitto.sms.service.Service;
import mitto.sms.userinterface.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Command for handling inputs in sms format
 * @see SMSParserImpl
 */
@Component
public class SMSCreateCommandImpl extends AbstractEntityCreateCommandImpl<SMS> {
    /**
     * SMSCreateCommandImpl constructor
     * @param service {@link Service}
     * @param userInterface {@link UserInterface}
     */
    @Autowired
    public SMSCreateCommandImpl(Service service, UserInterface userInterface){
        super(service, userInterface, new SMSParserImpl());
    }
}
