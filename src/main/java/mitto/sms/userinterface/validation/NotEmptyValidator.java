package mitto.sms.userinterface.validation;

public class NotEmptyValidator implements Validator<String> {

    public NotEmptyValidator(){
    }

    @Override
    public boolean validate(String value) {
        return value != null && value.length() >= 0;
    }

    @Override
    public String formatDesc() {
        return "not empty";
    }
}
