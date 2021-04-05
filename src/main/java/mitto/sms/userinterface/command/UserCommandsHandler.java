package mitto.sms.userinterface.command;

import mitto.sms.userinterface.parsing.exception.ParsingException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
/**
 * The UserCommandsHandler serves to find acceptable command and process it for given input
 */
public class UserCommandsHandler {
    private List<Command> strictFormatCommands = new ArrayList<>();
    private Command freeFormatCommand;

    /**
     * Appending strictly defined command (e.g. quit or stats -[c|s] string inputs)
     * @param command command to append
     */
    public void addStrictFormatCommand(Command command) {
        strictFormatCommands.add(command);
    }

    /**
     * Setting command for more variable types of inputs (e.g. SMS or CountryFee formatted inputs)
     * @param freeFormatCommand
     */
    public void setFreeFormatCommand(Command freeFormatCommand) {
        this.freeFormatCommand = freeFormatCommand;
    }

    /**
     * Finding and processing first acceptable command for input
     * @param input input to be proccessed by command
     * @throws ParsingException thrown when parsing fails
     */
    public void handle(String input) throws ParsingException {
        for(Command command : strictFormatCommands) {
            if (command.processCommand(input)) {
                return;
            }
        }
        if (freeFormatCommand != null) {
            freeFormatCommand.processCommand(input);
        }
    }
}
