package mitto.sms.userinterface.validation;

import java.util.regex.Pattern;

public class AlphanumericValidator extends PatternValidator {

    private static final Pattern PATTERN = Pattern.compile("[a-zA-Z0-9]*");
    private LengthLimitValidator lenghtLimitValidator;

    public AlphanumericValidator(int lengthLimit) {
        super(PATTERN);
        lenghtLimitValidator = new LengthLimitValidator(lengthLimit);
    }

    @Override
    public boolean validate(String value) {
        return lenghtLimitValidator.validate(value) && super.validate(value);
    }

    @Override
    public String formatDesc() {
        return "alphanumeric "+ lenghtLimitValidator.formatDesc();
    }
}
