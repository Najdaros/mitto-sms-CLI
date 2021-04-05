package mitto.sms.userinterface.parsing;

import mitto.sms.userinterface.validation.NotEmptyInputValidator;

import java.util.StringTokenizer;

public class StringTokenizerFactory {

    private static final String SEPARATOR = " ";
    private final NotEmptyInputValidator notEmptyInputValidator = new NotEmptyInputValidator();

    StringTokenizer getTokenizer(String value) {
        if (notEmptyInputValidator.validate(value))
            value = value.trim();
        else
            return null;

        return new StringTokenizer(value, SEPARATOR);
    }

    public static String getSEPARATOR() {
        return SEPARATOR;
    }
}
