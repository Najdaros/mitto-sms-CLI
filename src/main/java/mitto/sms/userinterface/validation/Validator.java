package mitto.sms.userinterface.validation;

/**
 * Objects The Validator class serve to va
 * @param <T>
 */
public interface Validator<T extends String> {

    boolean validate(T value);

    String formatDesc();
}
