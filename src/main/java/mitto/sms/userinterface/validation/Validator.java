package mitto.sms.userinterface.validation;

public interface Validator<T extends String> {

    boolean validate(T value);

    String formatDesc();
}
