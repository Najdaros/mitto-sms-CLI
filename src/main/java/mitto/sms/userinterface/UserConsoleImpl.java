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
/**
 * System Console user interface
 * provides reading from System.in or File, and writing to System.out stream
 *
 * @see UserInterface
 */
public class UserConsoleImpl implements UserInterface{

    private Boolean running = Boolean.TRUE;
    private final PrintStream printStream = System.out;
    private final InputStream inputStream = System.in;
    private UserCommandsHandler defaultCommandsHandler = new UserCommandsHandler();
    private UserCommandsHandler commandsHandler = defaultCommandsHandler;

    /**
     * Default constructor
     */
    public UserConsoleImpl() {
    }

    /**
     * Starts system console for reading human inputs
     */
    public void run() {
        print("Opening user input console...");
        run(new Scanner(inputStream));
    }

    /**
     * Starts file input processing
     * @param file input file contains formatted lines
     * @throws FileNotFoundException exception thrown when no such file exists
     */
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

    /**
     * Allows inject specific user commands handler to provides different operations for various type of inputs
     * @param commandsHandler command handler for user inputs
     */
    public void setCommandsHandler(UserCommandsHandler commandsHandler) {
        this.commandsHandler = commandsHandler;
    }

    /**
     * Will stop currently running input processing (file or user)
     */
    @Override
    public void stop() {
        running = Boolean.FALSE;
    }

    /**
     * Handle printing text parameter
     * @param text text to be printed
     */
    @Override
    public void print(String text) {
        printStream.println(text);
    }

    /**
     * Provide System.out object as alternative for printing message
     * @return System.out
     */
    @Override
    public PrintStream getPrintStream() {
        return printStream;
    }
}
