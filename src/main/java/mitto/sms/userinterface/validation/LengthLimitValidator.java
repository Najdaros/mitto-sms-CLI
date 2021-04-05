package mitto.sms.userinterface.validation;

public class LengthLimitValidator implements Validator<String> {

    private int lengthLimit;

    public LengthLimitValidator(int lengthLimit){

        this.lengthLimit = lengthLimit;
    }

    @Override
    public boolean validate(String value) {
        return value != null && value.length() <= lengthLimit;
    }

    @Override
    public String formatDesc() {
        return "max "+ lengthLimit + " characters";
    }
}
