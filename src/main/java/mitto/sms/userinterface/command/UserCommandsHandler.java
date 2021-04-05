package mitto.sms.userinterface.command;

import mitto.sms.userinterface.parsing.exception.ParsingException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserCommandsHandler {
    private List<Command> strictFormatCommands = new ArrayList<>();
    private List<Command> freeFormatCommands = new ArrayList<>();

    public void addStrictFormatCommand(Command command) {
        strictFormatCommands.add(command);
    }

    public void addFreeFormatCommand(Command command) {
        freeFormatCommands.add(command);
    }

    public UserCommandsHandler() {
    }

    public void handle(String input) throws ParsingException {
        for(Command command : strictFormatCommands) {
            if (command.processCommand(input)) {
                return;
            }
        }
        for(Command command : freeFormatCommands) {
            if (command.processCommand(input)) {
                return;
            }
        }
    }

}
