package mitto.sms.userinterface.parsing;

import mitto.sms.userinterface.validation.NotEmptyInputValidator;

import java.util.StringTokenizer;


/**
 * The StringTokenizerFactory class serves to creating StringTokenizer by given input value
 */
public class StringTokenizerFactory {

    private static final String SEPARATOR = " ";
    private final NotEmptyInputValidator notEmptyInputValidator = new NotEmptyInputValidator();

    /**
     * Factory method creating StringTokenizer-s given sanitized input
     * @param value string value for tokenize
     * @return StringTokenizer if value not null or empty, otherwise null
     */
    StringTokenizer getTokenizer(String value) {
        if (notEmptyInputValidator.validate(value))
            value = value.trim();
        else
            return null;

        return new StringTokenizer(value, SEPARATOR);
    }
}
