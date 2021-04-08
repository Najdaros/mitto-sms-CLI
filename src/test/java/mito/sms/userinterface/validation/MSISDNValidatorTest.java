package mito.sms.userinterface.validation;

import mitto.sms.userinterface.validation.MSISDNValidator;
import org.junit.jupiter.api.Test;

class MSISDNValidatorTest extends AbstractValidatorTest<String> {

    private static final Integer MAX_LIMIT = 15;
    private static final Integer MIN_LIMIT = 11;

    MSISDNValidatorTest() {
        super(new MSISDNValidator());
    }

    @Test
    @Override
    void validatorTest() {
        String overLimitNumValue = getRandomCharacter('1', '9') +
                new String(new char[MAX_LIMIT]).replace('\0', getRandomCharacter('0','9'));
        String maxLimitedNumValue = getRandomCharacter('1', '9') +
                new String(new char[MAX_LIMIT -1]).replace('\0', getRandomCharacter('0','9'));
        String minLimitedNumValue = getRandomCharacter('1', '9') +
                new String(new char[MIN_LIMIT -1 ]).replace('\0', getRandomCharacter('0','9'));
        String shortNumValue = getRandomCharacter('1', '9') +
                new String(new char[MIN_LIMIT -2]).replace('\0', getRandomCharacter('0','9'));

        validatorTestPositive("918369110173");
        validatorTestPositive(minLimitedNumValue);
        validatorTestPositive(maxLimitedNumValue);

        validatorTestNegative(null);
        validatorTestNegative("");
        validatorTestNegative("abc");
        validatorTestNegative("000000000000");
        validatorTestNegative(shortNumValue);
        validatorTestNegative(overLimitNumValue);
    }
}
