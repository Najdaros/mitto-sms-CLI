package mito.sms.userinterface.parsering;

import mitto.sms.hibernate.entity.SMS;
import mitto.sms.userinterface.parsing.Parser;
import mitto.sms.userinterface.parsing.SMSParserImpl;
import mitto.sms.userinterface.parsing.exception.ParsingException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class SMSParserTest {

    private final Parser<SMS> smsParser;

    private final String VALID_MSIND = "123456789012";
    private final String VALID_MSIND1 = "111111111111";
    private final String INVALID_MSIND = "12as6789012";
    private final String INVALID_MSIND1 = "1234";

    private final String VALID_ALPHANUMERIC = "TAXI";
    private final String INVALID_ALPHANUMERIC = "pe2s%";
    private final String INVALID_ALPHANUMERIC1 = "John's";

    private final String VALID_TEXT = "\"Hurry up John\"";
    private final String VALID_TEXT1 = "Hello";
    private final String INVALID_TEXT = "\"Hello Jonh";
    private final String INVALID_TEXT1 = "\'Hello John\'";

    SMSParserTest() {
        smsParser = new SMSParserImpl();
    }

    @Test
    void testParseDoesntThrowExceptionPositive() {
        String input = String.format("%s %s %s", VALID_MSIND, VALID_MSIND1, VALID_TEXT);
        assertDoesNotThrow(() ->smsParser.parse(input), "Valid input [%s] shouldn't throw ParsingException");
        String input1 = String.format("%s %s %s", VALID_ALPHANUMERIC, VALID_MSIND, VALID_TEXT1);
        assertDoesNotThrow(() ->smsParser.parse(input1), "Valid input [%s] shouldn't throw ParsingException");
    }

    @Test
    void testParseExceptionPositive1() {
        String input = String.format("%s %s %s", VALID_ALPHANUMERIC, VALID_MSIND1, VALID_TEXT);
        Optional<SMS> parsedObject = Optional.empty();
        try {
            parsedObject = smsParser.parse(input);
        } catch (ParsingException e) {
            fail(String.format("Valid input [%s] shouldn't throw ParsingException", input));
        }
        assertTrue(parsedObject.isPresent(), String.format("Parsed object for input [%s] shouldn't be empty", input));
        assertEquals(VALID_ALPHANUMERIC, parsedObject.get().getSender(), "Sender is not equal");
        assertEquals(VALID_MSIND1, parsedObject.get().getRecipient(), "Receiver is not equal");
        assertEquals(VALID_TEXT, parsedObject.get().getText(), "Text is not equal");
    }

    @Test
    void testParseNegative() {
        assertThrows(ParsingException.class, ()->smsParser.parse(String.format("%s %s %s", INVALID_ALPHANUMERIC, VALID_MSIND, VALID_TEXT)));
        assertThrows(ParsingException.class, ()->smsParser.parse(String.format("%s %s %s", VALID_ALPHANUMERIC, INVALID_MSIND, VALID_TEXT)));
        assertThrows(ParsingException.class, ()->smsParser.parse(String.format("%s %s %s", VALID_ALPHANUMERIC, VALID_MSIND, INVALID_TEXT)));
        assertThrows(ParsingException.class, ()->smsParser.parse(String.format("%s %s %s", INVALID_ALPHANUMERIC, INVALID_MSIND, INVALID_TEXT)));

        assertThrows(ParsingException.class, ()->smsParser.parse(String.format("%s %s %s", INVALID_ALPHANUMERIC1, VALID_MSIND, VALID_TEXT)));
        assertThrows(ParsingException.class, ()->smsParser.parse(String.format("%s %s %s", VALID_ALPHANUMERIC, INVALID_MSIND1, VALID_TEXT)));
        assertThrows(ParsingException.class, ()->smsParser.parse(String.format("%s %s %s", VALID_ALPHANUMERIC, VALID_MSIND, INVALID_TEXT1)));
        assertThrows(ParsingException.class, ()->smsParser.parse(String.format("%s %s %s", INVALID_ALPHANUMERIC1, INVALID_MSIND1, INVALID_TEXT1)));

    }

}
