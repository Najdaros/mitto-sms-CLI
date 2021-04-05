package mitto.sms.userinterface.validation;

public class SenderValidator implements Validator<String> {


    private final MSISDNValidator msisdnValidator;
    private final AlphanumericValidator alphanumericValidator;

    public SenderValidator() {
        msisdnValidator = new MSISDNValidator();
        alphanumericValidator = new AlphanumericValidator(256);
    }


    @Override
    public boolean validate(String value) {
        return msisdnValidator.validate(value) || alphanumericValidator.validate(value);
    }

    @Override
    public String formatDesc() {
        return "alphanumeric or MSISDN";
    }
}
