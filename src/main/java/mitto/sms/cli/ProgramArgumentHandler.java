package mitto.sms.cli;

import mitto.sms.cli.scanner.InputScanner;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Component("programArgumentHandler")
public class ProgramArgumentHandler {

    private final InputScanner inputScanner;

    private CommandLineParser parser = new DefaultParser();
    private Options options = new Options();

    @Autowired
    public ProgramArgumentHandler(InputScanner inputScanner) {
        options.addOption("t", false, "display current time");
        options.addOption("m", "sms_file", true, "filename containing list of sms messages");
        options.addOption("f", "fee_file", true, "filename containing list of sms fees");
        this.inputScanner = inputScanner;
    }

    public void handle(String[] args) {
        try {
            // parse the command line arguments
            CommandLine cmd = parser.parse( options, args );
            if(cmd.hasOption("t")) {
                String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
                System.out.println("Current time : "+timeStamp);
            }
            if(cmd.hasOption("m")) {
                String filename = cmd.getOptionValue("m");
                System.out.println("Reading messages file "+filename);
//
                try {
                    inputScanner.run(new File(filename));
                } catch (FileNotFoundException e) {
                    System.out.println("File with name "+filename+" was NOT found. Please check if file exist.");
                }

            }
            if(cmd.hasOption("f")) {
                String filename = cmd.getOptionValue("m");
                System.out.println("Reading fees file "+filename);
//
                try {
                    inputScanner.run(new File(filename));
                } catch (FileNotFoundException e) {
                    System.out.println("File with name "+filename+" was NOT found. Please check if file exist.");
                }

            }
        }
        catch( ParseException exp ) {
            // oops, something went wrong
            System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
        }

    }


}
