package mito.sms.userinterface.validation;

import mitto.sms.userinterface.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

abstract class AbstractValidatorTest<T extends String> {

    private final Validator<T> validator;

    AbstractValidatorTest(Validator<T> validator) {
        this.validator = validator;
    }

    void validatorTestPositive(T input) {
        assertTrue(validator.validate(input));
    }
    void validatorTestNegative(T input) {
        assertFalse(validator.validate(input));
    }

    @Test
    abstract void validatorTest();

    static char getRandomAlphanumbericCharacter() {
        // can be optimize better, not need to generate all 3 chars.
        List<Character> characters = Arrays.asList(getRandomCharacter('a', 'z'), getRandomCharacter('A', 'Z'), getRandomCharacter('0', '9'));
        return characters.get(new Random().nextInt(characters.size()));
    }

    static char getRandomVarcharCharacter() {
        // can be optimize better, not need to generate all 3 chars.
        List<Character> characters = Arrays.asList(getRandomCharacter('a', 'z'), getRandomCharacter('A', 'Z'));
        return characters.get(new Random().nextInt(characters.size()));
    }

    static char getRandomCharacter(char ch1, char ch2) {
        return (char) (ch1 + Math.random() * (ch2 - ch1 + 1));
    }
}
