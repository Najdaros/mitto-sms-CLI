package mitto.sms.userinterface.parsing.exception;

/**
 * Custom Parsing exception thrown if parsering fails during parsing
 * @see mitto.sms.userinterface.parsing.Parser
 * @see mitto.sms.userinterface.parsing.SMSParserImpl
 * @see mitto.sms.userinterface.parsing.CountryFeeParserImpl
 */
public class ParsingException extends Exception {
    public ParsingException(String msg) {
        super(msg);
    }
}
