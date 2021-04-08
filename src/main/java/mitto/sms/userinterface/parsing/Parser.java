package mitto.sms.userinterface.parsing;

import mitto.sms.userinterface.parsing.exception.ParsingException;
import mitto.sms.hibernate.entity.Entity;

import java.util.Optional;

/**
 * Objects implementing Parser interface providing parsing String value and converting it to Entity type Objects
 * @param <T> Entity type object
 */
public interface Parser<T extends Entity> {

    /**
     * Parses string value and converting it to Optinal<Entity> object
     * @param value string value for parsing
     * @return Optinal<Entity> object if parsing finish successfully, otherwise ParsingException is thrown
     * @throws ParsingException thrown when something fails during parsing, exception message will holds detailed information
     */
    Optional<T> parse(String value) throws ParsingException;
}
