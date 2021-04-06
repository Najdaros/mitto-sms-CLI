package mitto.sms.userinterface.consoleinterface;

import mitto.sms.hibernate.dao.StatsDTO;
import mitto.sms.userinterface.UserInterface;
import mitto.sms.userinterface.parsing.exception.ParsingException;
import mitto.sms.userinterface.command.UserCommandsHandler;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component("userConsole")
/**
 * System Console user interface
 * provides reading from System.in or File, and writing to System.out stream
 *
 * @see UserInterface
 */
public class UserConsole implements UserInterface{

    private Boolean running = Boolean.TRUE;
    private final PrintStream printStream = System.out;
    private final InputStream inputStream = System.in;
    private UserCommandsHandler defaultCommandsHandler = new UserCommandsHandler();
    private UserCommandsHandler commandsHandler = defaultCommandsHandler;

    /**
     * Default constructor
     */
    public UserConsole() {
    }

    /**
     * Starts system console for reading human inputs
     */
    public void run() {
        displayMessage("Opening user input console...");
        run(new Scanner(inputStream));
    }

    private void run(Scanner scanner) {
        try {
            running = true;
            while (running && scanner.hasNextLine()) {
                String input = scanner.nextLine();
                try {
                    commandsHandler.handle(input);
                } catch (ParsingException e) {
                    displayMessage(e.getMessage());
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
    public void quit() {
        running = Boolean.FALSE;
    }

    /**
     * Handle printing text parameter to console
     * @param message text to be printed
     */
    @Override
    public void displayMessage(String message) {
        printStream.println(message);
    }

    @Override
    public void displaySendersStats(List<StatsDTO> sendersStats) {
        displayMessage("Top Sender Stats:\n" + sendersStats.stream().map(StatsDTO::toString).collect(Collectors.joining("\n")));
    }

    @Override
    public void displayCountryStats(List<StatsDTO> countryStats) {
        displayMessage("Top Sender Stats:\n" + countryStats.stream().map(StatsDTO::toString).collect(Collectors.joining("\n")));
        displayMessage("Country Stats:\n" + countryStats.toString());
    }

    /**
     * Provide System.out object as alternative for printing message
     * @return System.out
     */
    public PrintStream getPrintStream() {
        return printStream;
    }
}
