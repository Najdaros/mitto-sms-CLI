package mitto.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Program Main class
 */
@SpringBootApplication
public class SmsCliApp implements CommandLineRunner{

    @Autowired
    private SmsCli argumentHandler;

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(SmsCliApp.class, args);
        ctx.close();
    }

    @Override
    public void run(String... args) {
        argumentHandler.handle(args);
    }

}
