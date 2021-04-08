package mitto.sms.userinterface.command;

import mitto.sms.hibernate.entity.Entity;
import mitto.sms.service.SmsService;
import mitto.sms.userinterface.UserInterface;
import mitto.sms.userinterface.parsing.Parser;
import mitto.sms.userinterface.parsing.exception.ParsingException;

import java.util.Optional;

/**
 * Command for handling inputs by parsering format
 * @see Parser
 */
public class AbstractEntityCreateCommandImpl<T extends Entity> implements EntityCreateCommand {
    private final Parser<T> parser;
    private SmsService smsService;
    private UserInterface userInterface;

    /**
     * AbstractEntityCreateCommandImpl constructor
     * @param smsService {@link SmsService}
     * @param userInterface {@link UserInterface}
     */
    public AbstractEntityCreateCommandImpl(SmsService smsService, UserInterface userInterface, Parser<T> parser){
        this.smsService = smsService;
        this.userInterface = userInterface;
        this.parser = parser;
    }

    /**
     * Processing is trying parse command and persist parsed entity object
     * @param input for command we are trying process
     * @return true if entity was persisted, false otherwise
     * @throws ParsingException {@link ParsingException} {@link Parser} propagate parsering exception
     */
    @Override
    public boolean processCommand(String input) throws ParsingException {
        Optional<T> optionalEntity = parser.parse(input);
        Boolean saved = optionalEntity.map(entity -> smsService.saveEntity(entity)).orElse(false);
        if (saved) {
            userInterface.displayMessage(optionalEntity.get().toString()+" saved");
        }
        return saved;
    }
}
