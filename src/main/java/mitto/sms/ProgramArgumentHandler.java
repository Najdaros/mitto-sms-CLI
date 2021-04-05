package mitto.sms;

import mitto.sms.service.Service;
import mitto.sms.userinterface.command.CountryFeeCreateCommandImpl;
import mitto.sms.userinterface.command.QuitCommand;
import mitto.sms.userinterface.command.SMSCreateCommandImpl;
import mitto.sms.userinterface.command.StatsCommand;
import mitto.sms.userinterface.command.UserCommandsHandler;
import mitto.sms.userinterface.UserInterface;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

@Component("programArgumentHandler")
/**
 * The ProgramArgumentHandler class provides base operation to initialise UserInterface and main Service
 * for handling user inputs via registered Commands
 * UserInterface and Service Configuration is set by input programArguments
 * supported program line arguments:
 * -h --help                prints help
 * -f --fee_file [filepath] load and process country_fee file
 * -s --sms_file [filepath] load and process sms messages file in same format as user can write
 *
 * Handling program arguments is provided by {@link CommandLineParser}
 *
 */
public class ProgramArgumentHandler {

    private CommandLineParser parser;
    private Options options;
    private final UserInterface userInterface;
    private final Service service;

    @Autowired
    /**
     * ProgramArgumentHandler Constructor
     * autowiring object implementation of UserInterface and Service by value parameters
     */
    public ProgramArgumentHandler(@Value("#{userConsole}") UserInterface userInterface, @Value("#{smsService}")Service service) {
        initArgumentParser();
        this.userInterface = userInterface;
        this.service = service;
    }

    private void initArgumentParser() {
        parser = new DefaultParser();
        options = new Options();
        options.addOption("h", "help", false, "print help");
        options.addOption("s", "sms_file", true, "filename containing list of sms messages");
        options.addOption("f", "fee_file", true, "filename containing list of sms fees");
    }

    /**
     * Method handles provided program line arguments, configures Service and runs UserInterface accordingly
     * @param args program line arguments
     */
    void handle(String[] args) {
        try {
            CommandLine cmd = parser.parse(options, args);
            UserCommandsHandler defaultCommandsHandler = new UserCommandsHandler();
            SMSCreateCommandImpl smsCreateCommand = new SMSCreateCommandImpl(service, userInterface);
            defaultCommandsHandler.setFreeFormatCommand(smsCreateCommand);
            defaultCommandsHandler.addStrictFormatCommand(new QuitCommand(userInterface));

            if (cmd.hasOption("h")) {
                printHelp();
                return;
            }

            if (cmd.hasOption("f")) {
                String filename = cmd.getOptionValue("f");
                service.setCountryFeeEnabled(true);
                UserCommandsHandler countryFeeFileCommandsHandler = new UserCommandsHandler();
                countryFeeFileCommandsHandler.setFreeFormatCommand(new CountryFeeCreateCommandImpl(service, userInterface));
                userInterface.setCommandsHandler(countryFeeFileCommandsHandler);
                userInterface.run(new File(filename));
                defaultCommandsHandler.addStrictFormatCommand(new StatsCommand(service, userInterface));
            }

            if (cmd.hasOption("s")) {
                String filename = cmd.getOptionValue("s");
                UserCommandsHandler smsFileCommandsHandler = new UserCommandsHandler();
                smsFileCommandsHandler.setFreeFormatCommand(smsCreateCommand);
                userInterface.setCommandsHandler(smsFileCommandsHandler);
                userInterface.run(new File(filename));
            }

            userInterface.setCommandsHandler(defaultCommandsHandler);
            userInterface.run();
        } catch (FileNotFoundException e) {
            userInterface.print("File not found. Reason: " + e.getMessage()+"\n Program will end...");
        } catch (ParseException e) {
            userInterface.print("Program argument exception. Reason: " + e.getMessage()+"\n Program will end...");
        }
    }
    private void printHelp() {
        String header = "Program arguments description\n\n";

        HelpFormatter formatter = new HelpFormatter();
        PrintWriter pw = new PrintWriter(userInterface.getPrintStream());
        formatter.printHelp(pw, formatter.getWidth(), "Program", header, options, formatter.getLeftPadding(), formatter.getDescPadding(), "\n\n", true);
        pw.flush();
    }
}
