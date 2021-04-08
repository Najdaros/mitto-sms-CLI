package mitto.sms.userinterface;

import mitto.sms.hibernate.dao.StatsDTO;
import mitto.sms.userinterface.command.UserCommandsHandler;

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
     * Allows inject specific user commands handler to provides different operations for various type of inputs
     * @see UserCommandsHandler
     * @param commandsHandler command handler for user inputs
     */
    void setCommandsHandler(UserCommandsHandler commandsHandler);

    /**
     * Will stop currently running input processing (file or user)
     */
    void quit();

    void displayMessage(String message);
    void displaySendersStats(List<StatsDTO> sendersStats);
    void displayCountryStats(List<StatsDTO> countryStats);
}
