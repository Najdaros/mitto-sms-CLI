package mitto.sms.userinterface.parsing;

import mitto.sms.userinterface.parsing.exception.ParsingException;
import mitto.sms.userinterface.validation.MSISDNValidator;
import mitto.sms.userinterface.validation.MessageTextValidator;
import mitto.sms.userinterface.validation.SenderValidator;
import mitto.sms.hibernate.entity.SMS;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.StringTokenizer;

@Component("SMSParser")
public class SMSParserImpl implements Parser<SMS> {


    private final SenderValidator senderValidator = new SenderValidator();
    private final MSISDNValidator recipientValidator = new MSISDNValidator();
    private final MessageTextValidator messageTextValidator = new MessageTextValidator();
    private final StringTokenizerFactory tokenizerFactory = new StringTokenizerFactory();

    public SMSParserImpl() {
    }

    public Optional<SMS> parse(String value) throws ParsingException {
        SMS sms = new SMS();

        StringTokenizer tokenizer = tokenizerFactory.getTokenizer(value);

        if (tokenizer == null || tokenizer.countTokens() < 3) {
            throw new ParsingException("Incorrect number of arguments ["+value+"]. Valid format <"+senderValidator.formatDesc()+"><SPACE>" +
                    "<"+recipientValidator.formatDesc()+"><SPACE><"+messageTextValidator.formatDesc()+">");
        }

        String sender = tokenizer.nextToken();
        if (senderValidator.validate(sender))
            sms.setSender(sender);
        else {
            throw new ParsingException("Input ["+value+"]: Sender [" + sender + "] is in incorrect format <" + senderValidator.formatDesc() + ">");
        }
        String recipient = tokenizer.nextToken();
        if (recipientValidator.validate(recipient))
            sms.setRecipient(recipient);
        else {
            throw new ParsingException("Input ["+value+"]: Recipient [" + recipient + "] is in incorrect format <" + recipientValidator.formatDesc() + ">");
        }

        String text = tokenizer.nextToken("").substring(1);
        if (messageTextValidator.validate(text))
            sms.setText(text);
        else {
            throw new ParsingException("Input ["+value+"]: Message text [" + text + "] is in incorrect format <" + messageTextValidator.formatDesc() + ">");
        }
        return Optional.of(sms);
    }
}
