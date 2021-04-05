package mitto.sms.userinterface.validation;

public class NotEmptyInputValidator implements Validator<String> {

    @Override
    public boolean validate(String value) {
        return value != null && value.trim().length() > 0;
    }

    @Override
    public String formatDesc() {
        return "not empty input";
    }
}
