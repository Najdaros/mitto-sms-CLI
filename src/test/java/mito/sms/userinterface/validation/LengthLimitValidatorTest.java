package mito.sms.userinterface.validation;

import mitto.sms.userinterface.validation.LengthLimitValidator;
import org.junit.jupiter.api.Test;

class LengthLimitValidatorTest extends AbstractValidatorTest<String> {

    private static final Integer LIMIT = 10;

    LengthLimitValidatorTest() {
        super(new LengthLimitValidator(LIMIT));
    }

    @Test
    @Override
    void validatorTest() {
        String stringOverLimit = new String(new char[LIMIT+1]).replace('\0', 'a');
        String topLimitedString = new String(new char[LIMIT]).replace('\0', 'b');

        validatorTestPositive("");
        validatorTestPositive("abc");
        validatorTestPositive(topLimitedString);

        validatorTestNegative(null);
        validatorTestNegative(stringOverLimit);
    }
}
