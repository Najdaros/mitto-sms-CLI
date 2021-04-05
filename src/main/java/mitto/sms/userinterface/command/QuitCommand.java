package mitto.sms.userinterface.command;

import mitto.sms.userinterface.UserInterface;

public class QuitCommand implements Command{
    private static final String QUIT = "quit";
    private UserInterface userInterface;

    public QuitCommand(UserInterface userInterface) {

        this.userInterface = userInterface;
    }


    @Override
    public boolean processCommand(String input) {
        if (QUIT.equals(input.trim())){
            userInterface.print("User interface shutting down by quit command...");
            userInterface.stop();
            return true;
        }
        return false;
    }
}
