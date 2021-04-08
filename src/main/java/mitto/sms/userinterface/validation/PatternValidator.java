package mitto.sms.userinterface.validation;

import java.util.regex.Pattern;

public abstract class PatternValidator implements Validator<String> {

    private final Pattern pattern;

    PatternValidator(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean validate(String value) {
        if (value == null) {
            return false;
        }
        return pattern.matcher(value).matches();
    }

    @Override
    public abstract String formatDesc();
}
