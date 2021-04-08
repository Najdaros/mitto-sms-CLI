package mitto.sms.userinterface.validation;

import java.util.regex.Pattern;

public class StatsCommandValidator extends PatternValidator {

    private static final Pattern PATTERN = Pattern.compile("(\\s)*stats(\\s+-[a-zA-Z]|)\\s*");

    public StatsCommandValidator() {
        super(PATTERN);
    }

    @Override
    public String formatDesc() {
        return "stats -[a-zA-Z]";
    }
}
