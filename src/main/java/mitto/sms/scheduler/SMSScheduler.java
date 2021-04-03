package mitto.sms.scheduler;

import mitto.sms.hibernate.dao.SmsDao;
import mitto.sms.hibernate.entity.SMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.stream.Collectors;

@EnableAsync
@EnableScheduling
@Component
public class SMSScheduler {

    private final SmsDao smsDao;

    @Autowired
    public SMSScheduler(SmsDao smsDao){

        this.smsDao = smsDao;
    }

    @Scheduled(fixedRate = 20000)
    private void topSMSSenders() {
        HashMap<String, Long> senderFrekvencyMap = smsDao.findALl().stream().collect(
                Collectors.groupingBy(
                        SMS::getSender,
                        HashMap::new, // can be skipped
                        Collectors.counting()));


        String topSMSSenders = senderFrekvencyMap.entrySet().stream()
                // Sort by value.
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                // Top n.
                .limit(5)
                // Keys only.
                .map(entry-> entry.getKey()+ " " +longToUnsignedInt(entry.getValue()))
                // As a list.
                .collect(Collectors.joining("\n"));
        System.out.println("Top 5 Senders:\n"+ topSMSSenders);
//        System.out.println("Added items :\n"+ smsDao.findALl().stream()
//                .map(SMS::toString)
//                .collect(Collectors.joining("\n")));
    }

    private static String longToUnsignedInt(Long value) {
        return Integer.toUnsignedString(value.intValue());
    }


}
