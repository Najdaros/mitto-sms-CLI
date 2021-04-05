package mitto.sms.scheduler;

import mitto.sms.userinterface.command.StatsCommand;
import mitto.sms.service.Service;
import mitto.sms.userinterface.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * The SMSScheduler class handling asynchronous task execution for printing top SMS senders in regular intervals (1 minute)
 * @author Jan Sorad
 */
@EnableAsync
@EnableScheduling
@Component
public class SMSScheduler {

    private final StatsCommand statsCommand;

    @Autowired
    public SMSScheduler(Service service, UserInterface userInterface){
        statsCommand = new StatsCommand(service, userInterface);
    }

    /**
     * printing top senders statistics information
     */
    @Scheduled(fixedRate = 60000)
    private void topSMSSenders() {
        statsCommand.printTopSendersStats();
    }

}
