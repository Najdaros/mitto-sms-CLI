package mito.sms.userinterface.parsing;

import mitto.sms.hibernate.entity.CountryFee;
import mitto.sms.userinterface.parsing.CountryFeeParserImpl;
import mitto.sms.userinterface.parsing.Parser;
import mitto.sms.userinterface.parsing.exception.ParsingException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class CountryFeeParserTest {

    private final Parser<CountryFee> countryFeeParser;

    private final String VALID_COUNTRY_CODE = "123";
    private final String INVALID_COUNTRY_CODE = "1234";
    private final String INVALID_COUNTRY_CODE1 = "abc";

    private final String VALID_COUNTRY_NAME = "Slovakia";
    private final String INVALID_COUNTRY_NAME = "USA%";
    private final String INVALID_COUNTRY_NAME1 = "123";

    private final String VALID_PRICE = "11.111";
    private final String INVALID_PRICE = "11,111";
    private final String INVALID_PRICE1 = "11.11";


    CountryFeeParserTest() {
        countryFeeParser = new CountryFeeParserImpl();
    }

    @Test
    void testParseDoesntThrowExceptionPositive() {
        String input = String.format("%s %s %s", VALID_COUNTRY_CODE, VALID_COUNTRY_NAME, VALID_PRICE);
        assertDoesNotThrow(() ->countryFeeParser.parse(input), "Valid input [%s] shouldn't throw ParsingException");
    }

    @Test
    void testParsePositive1() {
        String input = String.format("%s %s %s", VALID_COUNTRY_CODE, VALID_COUNTRY_NAME, VALID_PRICE);
        Optional<CountryFee> parsedObject = Optional.empty();
        try {
            parsedObject = countryFeeParser.parse(input);
        } catch (ParsingException e) {
            fail(String.format("Valid input [%s] shouldn't throw ParsingException", input));
        }
        assertTrue(parsedObject.isPresent(), String.format("Parsed object for input [%s] shouldn't be empty", input));
        assertEquals(VALID_COUNTRY_CODE, parsedObject.get().getCountryCode().toString(), "Country Code is not equal");
        assertEquals(VALID_COUNTRY_NAME, parsedObject.get().getCountryName(), "Country Name is not equal");
        assertEquals(VALID_PRICE, parsedObject.get().getPrice().toString(), "Price is not equal");
    }

    @Test
    void testParseNegative() {
        assertThrows(ParsingException.class, ()->countryFeeParser.parse(String.format("%s %s %s", INVALID_COUNTRY_CODE, VALID_COUNTRY_NAME, VALID_PRICE)));
        assertThrows(ParsingException.class, ()->countryFeeParser.parse(String.format("%s %s %s", VALID_COUNTRY_CODE, INVALID_COUNTRY_NAME, VALID_PRICE)));
        assertThrows(ParsingException.class, ()->countryFeeParser.parse(String.format("%s %s %s", VALID_COUNTRY_CODE, VALID_COUNTRY_NAME, INVALID_PRICE)));
        assertThrows(ParsingException.class, ()->countryFeeParser.parse(String.format("%s %s %s", INVALID_COUNTRY_CODE, INVALID_COUNTRY_NAME, INVALID_PRICE)));

        assertThrows(ParsingException.class, ()->countryFeeParser.parse(String.format("%s %s %s", INVALID_COUNTRY_CODE1, VALID_COUNTRY_NAME, VALID_PRICE)));
        assertThrows(ParsingException.class, ()->countryFeeParser.parse(String.format("%s %s %s", VALID_COUNTRY_CODE, INVALID_COUNTRY_NAME1, VALID_PRICE)));
        assertThrows(ParsingException.class, ()->countryFeeParser.parse(String.format("%s %s %s", VALID_COUNTRY_CODE, VALID_COUNTRY_NAME, INVALID_PRICE1)));
        assertThrows(ParsingException.class, ()->countryFeeParser.parse(String.format("%s %s %s", INVALID_COUNTRY_CODE1, INVALID_COUNTRY_NAME1, INVALID_PRICE1)));

    }

}
