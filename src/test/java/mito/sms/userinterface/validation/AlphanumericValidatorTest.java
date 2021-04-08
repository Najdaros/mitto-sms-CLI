package mito.sms.userinterface.validation;

import mitto.sms.userinterface.validation.AlphanumericValidator;
import org.junit.jupiter.api.Test;

class AlphanumericValidatorTest extends AbstractValidatorTest<String> {

    private static final Integer LIMIT = 10;

    AlphanumericValidatorTest() {
        super(new AlphanumericValidator(LIMIT));
    }

    @Test
    @Override
    void validatorTest() {
        String stringOverLimit = new String(new char[LIMIT+1]).replace('\0', getRandomAlphanumbericCharacter());
        String topLimitedString = new String(new char[LIMIT]).replace('\0', getRandomAlphanumbericCharacter());

        validatorTestPositive("test");
        validatorTestPositive("test123");
        validatorTestPositive("123test");
        validatorTestPositive("teST");
        validatorTestPositive("TEst123");
        validatorTestPositive("109teST");
        validatorTestPositive(topLimitedString);

        validatorTestNegative(null);
        validatorTestNegative("");
        validatorTestNegative("tes't");
        validatorTestNegative("te[st");
        validatorTestNegative("??");
        validatorTestNegative(stringOverLimit);
    }
}
