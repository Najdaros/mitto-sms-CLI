package mitto.sms.userinterface.validation;

public class CountryCodeValidator implements Validator<String> {

    private final int digits;

    public CountryCodeValidator() {
        digits = 3;
    }

    @Override
    public boolean validate(String value) {
        if (value == null)
            return false;
        try {
            Integer intValue = Integer.valueOf(value);
            return intValue >= 1 && intValue < (int) Math.pow(10, digits);

        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    @Override
    public String formatDesc() {
        return "max " + digits + " digits";
    }
}
