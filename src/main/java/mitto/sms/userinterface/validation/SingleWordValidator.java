package mitto.sms.userinterface.validation;

import java.util.regex.Pattern;

public class SingleWordValidator extends PatternValidator {

    private static final Pattern PATTERN = Pattern.compile("(?=.*\\w)^(\\w|')+");

    SingleWordValidator() {
        super(PATTERN);
    }

    @Override
    public String formatDesc() {
        return "one unquoted word";
    }
}
