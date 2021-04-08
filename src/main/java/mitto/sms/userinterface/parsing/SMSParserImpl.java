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

    /**
     * Tries to parse value and produce Optinal of SMS object
     * accepted format [sender: alphanumeric, MSISDN][space][recipient: MSISDN format][text: alphanumeric (one word message doesn’t have to be
     *  * in quotes), max 256 characters]
     * @param value string value for parsing
     * @return Optional[SMS] if parsing finish successfully, otherwise ParsingException is thrown
     * @throws ParsingException thrown when validation fails during parsing, exception message will holds detailed information
     */
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
