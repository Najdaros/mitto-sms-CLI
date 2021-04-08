package mito.sms.userinterface.validation;

import mitto.sms.userinterface.validation.VarcharValidator;
import org.junit.jupiter.api.Test;

class VarcharValidatorTest extends AbstractValidatorTest<String> {

    private static final Integer LIMIT = 10;

    VarcharValidatorTest() {
        super(new VarcharValidator(LIMIT));
    }

    @Test
    @Override
    void validatorTest() {
        String stringOverLimit = new String(new char[LIMIT+1]).replace('\0', getRandomVarcharCharacter());
        String topLimitedString = new String(new char[LIMIT]).replace('\0', getRandomVarcharCharacter());

        validatorTestPositive("test");
        validatorTestPositive("teST");
        validatorTestPositive("TEST");
        validatorTestPositive(topLimitedString);

        validatorTestNegative(null);
        validatorTestNegative("");
        validatorTestNegative("test123");
        validatorTestNegative("123test");
        validatorTestNegative("TEst123");
        validatorTestNegative("109teST");
        validatorTestNegative("tes't");
        validatorTestNegative("te[st");
        validatorTestNegative("??");
        validatorTestNegative(stringOverLimit);
    }
}
