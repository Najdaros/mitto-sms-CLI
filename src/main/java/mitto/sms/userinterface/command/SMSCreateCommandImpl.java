package mitto.sms.userinterface.command;

import mitto.sms.userinterface.parsing.SMSParserImpl;
import mitto.sms.hibernate.entity.SMS;
import mitto.sms.service.SmsService;
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
     * @param smsService {@link SmsService}
     * @param userInterface {@link UserInterface}
     */
    @Autowired
    public SMSCreateCommandImpl(SmsService smsService, UserInterface userInterface){
        super(smsService, userInterface, new SMSParserImpl());
    }
}
