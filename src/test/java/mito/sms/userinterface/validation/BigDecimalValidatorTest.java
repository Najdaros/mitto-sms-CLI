package mito.sms.userinterface.validation;

import mitto.sms.userinterface.validation.BigDecimalValidator;
import org.junit.jupiter.api.Test;

class BigDecimalValidatorTest extends AbstractValidatorTest<String> {

    BigDecimalValidatorTest() {
        super(new BigDecimalValidator(3));
    }

    @Test
    @Override
    void validatorTest() {

        validatorTestPositive("0.005");
        validatorTestPositive("100.005");

        validatorTestNegative("0.000");
        validatorTestNegative(null);
        validatorTestNegative("");
        validatorTestNegative("-0.005");
        validatorTestNegative("0,005");
        validatorTestNegative("0.01");
        validatorTestNegative("1");
        validatorTestNegative("a");
    }
}
