package mito.sms.userinterface.validation;

import mitto.sms.userinterface.validation.SenderValidator;
import org.junit.jupiter.api.Test;

class SenderValidatorTest extends AbstractValidatorTest<String> {

    private static final Integer LIMIT = 256;

    SenderValidatorTest() {
        super(new SenderValidator());
    }

    @Test
    @Override
    void validatorTest() {
        String stringOverLimit = new String(new char[LIMIT+1]).replace('\0', getRandomAlphanumbericCharacter());
        String topLimitedString = new String(new char[LIMIT]).replace('\0', getRandomAlphanumbericCharacter());

        validatorTestPositive("918369110173");
        validatorTestPositive("TEST");
        validatorTestPositive(topLimitedString);

        validatorTestNegative(null);
        validatorTestNegative("");
        validatorTestNegative(stringOverLimit);
    }
}
