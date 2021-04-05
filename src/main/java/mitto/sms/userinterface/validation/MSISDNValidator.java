package mitto.sms.userinterface.validation;


import java.util.regex.Pattern;

public class MSISDNValidator extends PatternValidator {

    private static final Pattern PATTERN = Pattern.compile("[1-9][0-9]{10,14}");

    public MSISDNValidator(){
        super(PATTERN);
    }


    @Override
    public String formatDesc() {
        return "MSISDN";
    }
}
