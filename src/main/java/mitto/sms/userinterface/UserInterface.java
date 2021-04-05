package mitto.sms.userinterface;

import mitto.sms.hibernate.StatsDTO;
import mitto.sms.userinterface.command.UserCommandsHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Common User Interface
 * Interface serves to handle user/file inputs and outputs
 * Interface allow inject input commands handler, for variable input processing
 * Object implementations of userInterface can be stopped
 */
public interface UserInterface {

    /**
     * Start input processing from user
     */
    void run();

    /**
     * Start input processing from file
     * @param file input file contains formatted lines
     * @throws FileNotFoundException exception is thrown when File is not found
     */
    void run(File file) throws FileNotFoundException;

    /**
     * Allows inject specific user commands handler to provides different operations for various type of inputs
     * @see UserCommandsHandler
     * @param commandsHandler command handler for user inputs
     */
    void setCommandsHandler(UserCommandsHandler commandsHandler);

    /**
     * Will stop currently running input processing (file or user)
     */
    void stop();

    void displayMessage(String message);
    void displaySendersStats(List<StatsDTO> sendersStats);
    void displayCountryStats(List<StatsDTO> countryStats);
}
