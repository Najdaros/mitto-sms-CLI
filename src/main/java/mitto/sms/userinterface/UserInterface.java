package mitto.sms.userinterface;

import mitto.sms.userinterface.command.UserCommandsHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public interface UserInterface {

    void run();
    void run(File file) throws FileNotFoundException;
    void setCommandsHandler(UserCommandsHandler commandsHandler);
    void stop();
    void print(String text);
    PrintStream getPrintStream();

}
