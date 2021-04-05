package mitto.sms.userinterface.parsing;

import mitto.sms.userinterface.parsing.exception.ParsingException;
import mitto.sms.userinterface.validation.AlphanumericValidator;
import mitto.sms.userinterface.validation.BigDecimalValidator;
import mitto.sms.userinterface.validation.CountryCodeValidator;
import mitto.sms.hibernate.entity.CountryFee;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.StringTokenizer;

@Component("CountryFeeParser")
public class CountryFeeParserImpl implements Parser<CountryFee>{

    private final CountryCodeValidator countryCodeValidator = new CountryCodeValidator();
    private final AlphanumericValidator countryNameValidator = new AlphanumericValidator(256);
    private final BigDecimalValidator priceValidator = new BigDecimalValidator(3);
    private final StringTokenizerFactory tokenizerFactory = new StringTokenizerFactory();

    public Optional<CountryFee> parse(String value) throws ParsingException {
        CountryFee countryFee = new CountryFee();

        StringTokenizer tokenizer = tokenizerFactory.getTokenizer(value);

        if (tokenizer == null || tokenizer.countTokens() < 3) {
            throw new ParsingException("Incorrect number of arguments ["+value+"]. Valid format <"+countryCodeValidator.formatDesc()+"><SPACE>" +
                    "<"+countryNameValidator.formatDesc()+"><SPACE><"+ priceValidator.formatDesc()+">");
        }

        String countryCode = tokenizer.nextToken();
        if (countryCodeValidator.validate(countryCode))
            countryFee.setCountryCode(Integer.valueOf(countryCode));
        else {
            throw new ParsingException("Input ["+value+"]: Country code  [" + countryCode + "] is in incorrect format <" + countryCodeValidator.formatDesc() + ">");
        }

        String countryName = tokenizer.nextToken();
        if (countryNameValidator.validate(countryName))
            countryFee.setCountryName(countryName);
        else {
            throw new ParsingException("Input ["+value+"]: Country name  [" + countryName + "] is in incorrect format <" + countryNameValidator.formatDesc() + ">");
        }

        String price = tokenizer.nextToken();
        if (priceValidator.validate(price))
            countryFee.setPrice(new BigDecimal(price));
        else {
            throw new ParsingException("Input ["+value+"]: Price value  [" + price + "] is in incorrect format <" + priceValidator.formatDesc() + ">");
        }
        return Optional.of(countryFee);
    }
}
