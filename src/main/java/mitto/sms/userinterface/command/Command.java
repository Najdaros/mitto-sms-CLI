package mitto.sms.userinterface.command;

import mitto.sms.userinterface.parsing.exception.ParsingException;

/**
 * Objects implementing Command Interface are designed to serve user/file input line
 */
public interface Command {
    /**
     * Tries to process command for given input
     * @param input for command we are trying process
     * @return true if command successfully precessed input, false otherwise
     * @throws ParsingException thrown when parsing input fail
     */
    boolean processCommand(String input) throws ParsingException;
}
