package mitto.sms.userinterface.validation;

import java.util.regex.Pattern;

public class VarcharValidator extends PatternValidator {

    private static final Pattern PATTERN = Pattern.compile("[a-zA-Z]+");
    private final NotEmptyValidator notEmptyValidator;
    private LengthLimitValidator lengthLimitValidator;

    public VarcharValidator(int lengthLimit) {
        super(PATTERN);
        lengthLimitValidator = new LengthLimitValidator(lengthLimit);
        notEmptyValidator = new NotEmptyValidator();
    }

    @Override
    public boolean validate(String value) {
        return notEmptyValidator.validate(value) && lengthLimitValidator.validate(value) && super.validate(value);
    }

    @Override
    public String formatDesc() {
        return "alphanumeric "+ lengthLimitValidator.formatDesc();
    }
}
