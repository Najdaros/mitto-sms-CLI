package mitto.sms;

import mitto.sms.service.SmsService;
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
import java.io.StringWriter;

@Component("programArgumentHandler")
/**
 * The SmsCli class provides base operation to initialise UserInterface and main SmsService
 * for handling user inputs via registered Commands
 * UserInterface and SmsService Configuration is set by input programArguments
 * supported program line arguments:
 * -h --help                prints help
 * -f --fee_file [filepath] load and process country_fee file
 * -s --sms_file [filepath] load and process sms messages file in same format as user can write
 *
 * Handling program arguments is provided by {@link CommandLineParser}
 *
 */
public class SmsCli {

    private CommandLineParser parser;
    private Options options;
    private final UserInterface userInterface;
    private final SmsService smsService;

    /**
     * SmsCli Constructor
     * autowiring object implementation of UserInterface and SmsService by value parameters
     */
    @Autowired
    public SmsCli(@Value("#{userConsole}") UserInterface userInterface, @Value("#{smsService}") SmsService smsService) {
        initArgumentParser();
        this.userInterface = userInterface;
        this.smsService = smsService;
    }

    private void initArgumentParser() {
        parser = new DefaultParser();
        options = new Options();
        options.addOption("h", "help", false, "print help");
        options.addOption("s", "sms_file", true, "filename containing list of sms messages");
        options.addOption("f", "fee_file", true, "filename containing list of sms fees");
    }

    /**
     * Method handles provided program line arguments, configures SmsService and runs UserInterface accordingly
     * @param args program line arguments
     */
    void handle(String[] args) {
        try {
            CommandLine cmd = parser.parse(options, args);
            UserCommandsHandler defaultCommandsHandler = new UserCommandsHandler();
            SMSCreateCommandImpl smsCreateCommand = new SMSCreateCommandImpl(smsService, userInterface);
            defaultCommandsHandler.setFreeFormatCommand(smsCreateCommand);
            defaultCommandsHandler.addStrictFormatCommand(new QuitCommand(userInterface));

            if (cmd.hasOption("h")) {
                printHelp();
                return;
            }

            if (cmd.hasOption("f")) {
                String filename = cmd.getOptionValue("f");
                handleCountryFeeFile(filename, smsService, userInterface);
                defaultCommandsHandler.addStrictFormatCommand(new StatsCommand(smsService, userInterface));
            }
            if (cmd.hasOption("s")) {
                String filename = cmd.getOptionValue("s");
                handleSMSFile(filename, userInterface, smsCreateCommand);
            }

            userInterface.setCommandsHandler(defaultCommandsHandler);
            userInterface.run();
        } catch (FileNotFoundException e) {
            userInterface.displayMessage("File not found. Reason: " + e.getMessage()+"\n Program will end...");
        } catch (ParseException e) {
            userInterface.displayMessage("Program argument exception. Reason: " + e.getMessage()+"\n Program will end...");
        }
    }

    private void handleSMSFile(String filename, UserInterface userInterface, SMSCreateCommandImpl smsCreateCommand) throws FileNotFoundException {
        UserCommandsHandler smsFileCommandsHandler = new UserCommandsHandler();
        smsFileCommandsHandler.setFreeFormatCommand(smsCreateCommand);
        userInterface.setCommandsHandler(smsFileCommandsHandler);
        FileEntityProcessor.process(filename, userInterface, smsCreateCommand);
    }

    private void handleCountryFeeFile(String filename, SmsService smsService, UserInterface userInterface) throws FileNotFoundException {
        smsService.setCountryFeeEnabled(true);
        UserCommandsHandler countryFeeFileCommandsHandler = new UserCommandsHandler();
        CountryFeeCreateCommandImpl countryFeeCreateCommand = new CountryFeeCreateCommandImpl(smsService, userInterface);
        countryFeeFileCommandsHandler.setFreeFormatCommand(countryFeeCreateCommand);
        userInterface.setCommandsHandler(countryFeeFileCommandsHandler);
        FileEntityProcessor.process(filename, userInterface, countryFeeCreateCommand);
    }

    private void printHelp() {
        String header = "Program arguments description\n\n";
        HelpFormatter formatter = new HelpFormatter();
        StringWriter stringWriter = new StringWriter();
        PrintWriter pw = new PrintWriter(stringWriter);
        formatter.printHelp(pw, formatter.getWidth(), "Program", header, options, formatter.getLeftPadding(), formatter.getDescPadding(), "\n\n", true);
        pw.flush();
        userInterface.displayMessage(stringWriter.toString());
    }
}
