package mitto.sms.userinterface.command;

import mitto.sms.userinterface.parsing.exception.ParsingException;

public interface Command {
    boolean processCommand(String input) throws ParsingException;
}
