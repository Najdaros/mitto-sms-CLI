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
public class ProgramArgumentHandler {

    private CommandLineParser parser;
    private Options options;
    private final UserInterface userInterface;
    private final Service service;

    @Autowired
    public ProgramArgumentHandler(@Value("#{userConsole}") UserInterface userInterface, @Value("#{smsService}")Service service) {
        initArgumentParser();
        this.userInterface = userInterface;
        this.service = service;
    }

    private void initArgumentParser() {
        parser = new DefaultParser();
        options = new Options();
        options.addOption("h", "help", false, "print help");
        options.addOption("m", "sms_file", true, "filename containing list of sms messages");
        options.addOption("f", "fee_file", true, "filename containing list of sms fees");
    }

    public void handle(String[] args) {
        try {
            CommandLine cmd = parser.parse(options, args);
            UserCommandsHandler defaultCommandsHandler = new UserCommandsHandler();
            defaultCommandsHandler.addFreeFormatCommand(new SMSCreateCommandImpl(service, userInterface));
            defaultCommandsHandler.addStrictFormatCommand(new QuitCommand(userInterface));

            if (cmd.hasOption("h")) {
                String header = "Program arguments description\n\n";

                HelpFormatter formatter = new HelpFormatter();
                PrintWriter pw = new PrintWriter(userInterface.getPrintStream());
                formatter.printHelp(pw, formatter.getWidth(), "Program", header, options, formatter.getLeftPadding(), formatter.getDescPadding(), "\n\n", true);
                pw.flush();
                return;
            }

            if (cmd.hasOption("f")) {
                String filename = cmd.getOptionValue("f");
                service.setCountryFeeEnabled(true);
                UserCommandsHandler countryFeeCommandsHandler = new UserCommandsHandler();
                countryFeeCommandsHandler.addFreeFormatCommand(new CountryFeeCreateCommandImpl(service, userInterface));
                userInterface.setCommandsHandler(countryFeeCommandsHandler);
                userInterface.run(new File(filename));
                defaultCommandsHandler.addStrictFormatCommand(new StatsCommand(service, userInterface));
            }

            userInterface.setCommandsHandler(defaultCommandsHandler);

            if (cmd.hasOption("m")) {
                String filename = cmd.getOptionValue("m");
                userInterface.run(new File(filename));
            }
            userInterface.run();
        } catch (FileNotFoundException e) {
            userInterface.print("File not found. Reason: " + e.getMessage());
        } catch (ParseException e) {
            userInterface.print("Program argument exception. Reason: " + e.getMessage());
        }
    }

}
