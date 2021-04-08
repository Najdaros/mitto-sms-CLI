package mito.sms.userinterface.validation;

import mitto.sms.userinterface.validation.CountryCodeValidator;
import org.junit.jupiter.api.Test;

class CountryCodeValidatorTest extends AbstractValidatorTest<String> {

    CountryCodeValidatorTest() {
        super(new CountryCodeValidator());
    }

    @Test
    @Override
    void validatorTest() {
        validatorTestPositive("001");
        validatorTestPositive("999");
        validatorTestPositive("1");
        validatorTestPositive("87");

        validatorTestNegative(null);
        validatorTestNegative("");
        validatorTestNegative("1000");
        validatorTestNegative("000");
        validatorTestNegative("0");
        validatorTestNegative("a");
        validatorTestNegative("[");
    }
}
