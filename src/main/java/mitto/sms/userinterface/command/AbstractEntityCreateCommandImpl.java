package mitto.sms.userinterface.command;

import mitto.sms.hibernate.entity.Entity;
import mitto.sms.service.Service;
import mitto.sms.userinterface.UserInterface;
import mitto.sms.userinterface.parsing.Parser;
import mitto.sms.userinterface.parsing.exception.ParsingException;

import java.util.Optional;

/**
 * Command for handling inputs by parser format
 * @see Parser
 */
public class AbstractEntityCreateCommandImpl<T extends Entity> implements Command {
    private final Parser<T> parser;
    private Service service;
    private UserInterface userInterface;

    /**
     * AbstractEntityCreateCommandImpl constructor
     * @param service {@link Service}
     * @param userInterface {@link UserInterface}
     */
    public AbstractEntityCreateCommandImpl(Service service, UserInterface userInterface, Parser<T> parser){
        this.service = service;
        this.userInterface = userInterface;
        this.parser = parser;
    }

    /**
     * Processing is trying parse command and persist parsed entity object
     * @param input for command we are trying process
     * @return true if entity was persisted, false otherwise
     * @throws ParsingException {@link ParsingException}
     */
    @Override
    public boolean processCommand(String input) throws ParsingException {
        Optional<T> optionalEntity = parser.parse(input);
        Boolean saved = optionalEntity.map(entity -> service.saveEntity(entity)).orElse(false);
        if (saved) {
            userInterface.displayMessage(optionalEntity.get().toString()+" saved");
        }
        return saved;
    }
}
