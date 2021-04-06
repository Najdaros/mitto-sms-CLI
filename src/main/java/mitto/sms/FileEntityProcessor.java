package mitto.sms;

import mitto.sms.userinterface.UserInterface;
import mitto.sms.userinterface.command.EntityCreateCommand;
import mitto.sms.userinterface.parsing.exception.ParsingException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Util for processing files contains entity formatted data
 */
public class FileEntityProcessor {

    /**
     * Processing file containing entity formatted data, every line will be processed by entity create command
     * @param filename name of file
     * @param userInterface user interface where errors will be displayed
     * @param command command for processing lines
     * @throws FileNotFoundException thrown when file can't be found
     */
    public static void process(String filename, UserInterface userInterface, EntityCreateCommand command) throws FileNotFoundException {
        File fleExample = new File(filename);
        Scanner opnScanner = new Scanner(fleExample);

        while( opnScanner.hasNext() ) {
            String line = opnScanner.nextLine();
            try {
                command.processCommand(line);
            } catch (ParsingException e) {
                userInterface.displayMessage(e.getMessage());
            }
        }
        opnScanner.close();
    }

}
