package mitto.sms.userinterface.validation;

import java.math.BigDecimal;

public class BigDecimalValidator implements Validator<String> {

    private int scale;

    public BigDecimalValidator(int scale){

        this.scale = scale;
    }

    @Override
    public boolean validate(String value) {
        if (value == null)
            return false;
        try {
            BigDecimal bigDecimalValue = new BigDecimal(value);
            return bigDecimalValue.scale() == scale;

        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    @Override
    public String formatDesc() {
        return "fixed 3 decimals, . (dot) as decimal separator";
    }
}
