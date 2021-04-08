package mitto.sms.userinterface.validation;

public class SenderValidator implements Validator<String> {


    private final MSISDNValidator msisdnValidator;
    private final AlphanumericValidator alphanumericValidator;
    private final NotEmptyValidator notEmptyValidator;

    public SenderValidator() {
        msisdnValidator = new MSISDNValidator();
        alphanumericValidator = new AlphanumericValidator(256);
        notEmptyValidator = new NotEmptyValidator();
    }


    @Override
    public boolean validate(String value) {
        return notEmptyValidator.validate(value) && (msisdnValidator.validate(value) || alphanumericValidator.validate(value));
    }

    @Override
    public String formatDesc() {
        return "alphanumeric or MSISDN";
    }
}
