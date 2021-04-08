package mito.sms.userinterface.validation;

import mitto.sms.userinterface.validation.MessageTextValidator;
import org.junit.jupiter.api.Test;

class MessageTextValidatorTest extends AbstractValidatorTest<String> {

    private static final Integer LIMIT = 256;

    MessageTextValidatorTest() {
        super(new MessageTextValidator());
    }

    @Test
    @Override
    void validatorTest() {
        String stringOverLimit = new String(new char[LIMIT+1]).replace('\0', 'a');
        String topLimitedString = new String(new char[LIMIT]).replace('\0', 'b');

        validatorTestPositive("Test");
        validatorTestPositive("\"Test\"");
        validatorTestPositive("\"Test Test\"");
        validatorTestPositive("\"Test Test Test\"");
        validatorTestPositive("\"T'est Te'st Tes't\"");
        validatorTestPositive("\"whatever characters /*+!@#$%&^(&)*_(~\"");
        validatorTestPositive("\"0.005\"");
        validatorTestPositive("4512");
        validatorTestPositive(topLimitedString);

        validatorTestNegative(null);
        validatorTestNegative("");
        validatorTestNegative("Test Test");
        validatorTestNegative("\'Test Test\'");
        validatorTestNegative("\" asd \"dob\"");
        validatorTestNegative(stringOverLimit);
    }
}
