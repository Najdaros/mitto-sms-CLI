package mitto.sms.userinterface.command;

import mitto.sms.userinterface.validation.StatsCommandValidator;
import mitto.sms.service.Service;
import mitto.sms.userinterface.UserInterface;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.PrintWriter;
import java.util.List;

/**
 * Command for handling stats -[c|s|h] inputs. Command first try validate input and if validation passed command will be executed
 * stats command arguments:
 * -c printing statistics for countries <country name><space><count of messages><space><overall sum of prices for messages>
 * -s printing statistics for top 5 senders <sender><space><count of messages> or
 *                                          <sender><space><count of messages><space><overall sum of prices for messages>
 * -h printing help
 */
public class StatsCommand implements Command {
    private final StatsCommandValidator statsCommandValidator;
    private Service service;
    private UserInterface userInterface;
    private CommandLineParser parser;
    private Options options;

    public StatsCommand(Service service, UserInterface userInterface) {
        this.service = service;
        this.userInterface = userInterface;
        statsCommandValidator = new StatsCommandValidator();
        parser = new DefaultParser();
        options = new Options();
        options.addOption("c", false, "print country fee stats");
        options.addOption("s", false, "print sender fee stats");
        options.addOption("h", false, "print help");
    }

    @Override
    public boolean processCommand(String input) {
        try {
            if (statsCommandValidator.validate(input)) {
                String[] args = input.replace("stats", "").trim().split("\\s");

                CommandLine cmd = parser.parse(options, args);
                if (cmd.hasOption("h")) {
                    String header = "Stats command description\n\n";

                    HelpFormatter formatter = new HelpFormatter();
                    PrintWriter pw = new PrintWriter(userInterface.getPrintStream());
                    formatter.printHelp(pw, formatter.getWidth(), "stats", header, options, formatter.getLeftPadding(), formatter.getDescPadding(), "\n\n", true);
                    pw.flush();
                    return true;
                }
                if (cmd.hasOption("s")) {

                    printTopSendersStats();
                    return true;
                }
                if (cmd.hasOption("c") || isEmptyCommandArgumentList(cmd.getArgList())) {

                    printCountryStats();
                    return true;
                }
            }
        } catch (ParseException e) {
            userInterface.print("Stats command parsing exception: "+e.getMessage());
            e.printStackTrace(userInterface.getPrintStream());
        }
        return false;
    }

    public void printTopSendersStats() {
        String countryFeeStats = String.join("\n", service.getTopSendersFormatted(5));
        userInterface.print("Top Sender Stats:\n" + countryFeeStats + "\n");
    }

    private void printCountryStats() {
        String countryFeeStats = String.join("\n", service.getCountryFeeFormatted());
        userInterface.print("Country Fee Stats:\n" + countryFeeStats + "\n");
    }

    private static boolean isEmptyCommandArgumentList(List<String> argList) {
        return argList.stream().allMatch(String::isEmpty);
    }
}
