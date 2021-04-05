package mitto.sms.userinterface.validation;

public class MessageTextValidator implements Validator<String> {

    private final SingleWordValidator singleWordValidator;
    private final QuotedTextValidator quotedTextValidator;
    private final LengthLimitValidator lengthLimitValidator;

    public MessageTextValidator(){
        singleWordValidator = new SingleWordValidator();
        quotedTextValidator = new QuotedTextValidator();
        lengthLimitValidator = new LengthLimitValidator(256);
    }

    @Override
    public boolean validate(String value) {
        return lengthLimitValidator.validate(value) && (singleWordValidator.validate(value) || quotedTextValidator.validate(value));
    }

    @Override
    public String formatDesc() {
        return "alphanumeric (one word message doesn’t have to be in quotes), "+ lengthLimitValidator.formatDesc();
    }
}
