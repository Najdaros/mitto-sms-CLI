package mitto.sms.userinterface.command;

import mitto.sms.userinterface.UserInterface;


/**
 * Command for handling quit inputs. Command first try validate input and if validation passed command will be executed
 */
public class QuitCommand implements Command{
    private static final String QUIT = "quit";
    private UserInterface userInterface;

    /**
     * Quit command constructor
     * @param userInterface user interface
     */
    public QuitCommand(UserInterface userInterface) {
        this.userInterface = userInterface;
    }


    @Override
    public boolean processCommand(String input) {
        if (QUIT.equals(input.trim())){
            userInterface.displayMessage("User interface shutting down by quit command...");
            userInterface.stop();
            return true;
        }
        return false;
    }
}
