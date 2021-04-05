package mitto.sms.userinterface.parsing;

import mitto.sms.userinterface.parsing.exception.ParsingException;
import mitto.sms.hibernate.entity.Entity;

import java.util.Optional;

public interface Parser<T extends Entity> {
    Optional<T> parse(String value) throws ParsingException;
}
