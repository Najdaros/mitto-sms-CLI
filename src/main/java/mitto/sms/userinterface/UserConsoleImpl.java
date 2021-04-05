package mitto.sms.userinterface;

import mitto.sms.userinterface.parsing.exception.ParsingException;
import mitto.sms.userinterface.command.UserCommandsHandler;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Component("userConsole")
public class UserConsoleImpl implements UserInterface{

    private Boolean running = Boolean.TRUE;
    private final PrintStream printStream = System.out;
    private final InputStream inputStream = System.in;
    private UserCommandsHandler defaultCommandsHandler = new UserCommandsHandler();
    private UserCommandsHandler commandsHandler = defaultCommandsHandler;

    public UserConsoleImpl() {
    }

    public void run() {
        print("Opening user input console...");
        run(new Scanner(inputStream));
    }

    public void run(File file) throws FileNotFoundException {
        print("Processing file... "+file.getName());
        run(new Scanner(file));
    }

    private void run(Scanner scanner) {
        try {
            running = true;
            while (running && scanner.hasNextLine()) {
                String input = scanner.nextLine();
                try {
                    commandsHandler.handle(input);
                } catch (ParsingException e) {
                    print(e.getMessage());
                }
            }

        } catch (Exception e) {
            printStream.println("Unknown exception "+e.getMessage());
            e.printStackTrace(printStream);
        } finally {
            scanner.close();
        }
    }

    public void setCommandsHandler(UserCommandsHandler commandsHandler) {
        this.commandsHandler = commandsHandler;
    }

    @Override
    public void stop() {
        running = Boolean.FALSE;
    }

    @Override
    public void print(String text) {
        printStream.println(text);
    }

    @Override
    public PrintStream getPrintStream() {
        return printStream;
    }
}
