package mitto.sms.userinterface.validation;

import java.util.regex.Pattern;

public class QuotedTextValidator extends PatternValidator {

    private static final Pattern PATTERN = Pattern.compile("((?<![\\\\])[\"])((?:.(?!(?<![\\\\])\\1))*.?)\\1");

    QuotedTextValidator() {
        super(PATTERN);
    }

    @Override
    public String formatDesc() {
        return "alphanumeric quoted text";
    }
}
