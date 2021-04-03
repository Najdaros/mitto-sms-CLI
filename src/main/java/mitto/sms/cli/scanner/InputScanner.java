package mitto.sms.cli.scanner;

import mitto.sms.hibernate.dao.FeeDao;
import mitto.sms.hibernate.dao.SmsDao;
import mitto.sms.hibernate.entity.SMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

@Component("inputScanner")
public class InputScanner {

    private static final String QUIT = "quit";
    private static final String STATS = "stats";

    private SMSStringConverter converter = new SMSStringConverter();

    private final SmsDao smsDao;
    private final FeeDao feeDao;

    @Autowired
    public InputScanner(SmsDao smsDao, FeeDao feeDao) {
        this.smsDao = smsDao;
        this.feeDao = feeDao;
        feeDao.create(421, "Slovakia", 0.20f);
        List<SMS> aLl = smsDao.findALl();
        System.out.println();
    }

    public void run(File file) throws FileNotFoundException {
        run(new Scanner(file, StandardCharsets.UTF_8.name()));
    }

    public void run() {
        run(new Scanner(System.in));
    }

    private void run(Scanner scanner) {
        try {
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                if (isQuitCommand(input))
                    break;

                SMS sms = converter.convert(input);
                if (sms != null)
                    smsDao.create(sms);
            }
        } finally {
            scanner.close();
        }
    }

    private boolean isQuitCommand(String input) {
        return QUIT.equals(input.trim());
    }

    private boolean isStatsCommand(String input) {
        return STATS.equals(input.trim());
    }
}
