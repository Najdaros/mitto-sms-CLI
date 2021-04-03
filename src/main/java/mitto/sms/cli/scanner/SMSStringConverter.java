package mitto.sms.cli.scanner;

import mitto.sms.hibernate.entity.SMS;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class SMSStringConverter {

    private static final String MSDINT_PATTERN = "[1-9][0-9]{10,14}";
    private static final String ALPHANUM_PATTERN = "[a-zA-Z0-9]*";
    private static final String SEPARATOR = " ";
    //https://www.metaltoad.com/blog/regex-quoted-string-escapable-quotes
    private static final String QUOTED_TEXT = "((?<![\\\\])[\"])((?:.(?!(?<![\\\\])\\1))*.?)\\1";
    private static final String SINGLE_WORD = "(?=.*\\w)^(\\w|')+";

    private final Pattern p1 = Pattern.compile(MSDINT_PATTERN + "|" + ALPHANUM_PATTERN);
    private final Pattern p2 = Pattern.compile(MSDINT_PATTERN);
    private final Pattern p3 = Pattern.compile(QUOTED_TEXT + "|" + SINGLE_WORD);

    public SMSStringConverter() {
    }

    public SMS convert(String value) {
        SMS sms = new SMS();
        // If the specified value is null or zero-length, return null
        if (value == null) {
            System.out.println("Empty Input");
            return null;
        }

        value = value.trim();

        if (value.length() < 1) {
            System.out.println("Empty Input");
            return null;
        }

        StringTokenizer st = new StringTokenizer(value, SEPARATOR);

        if (st.countTokens() < 3) {
            System.out.println("Input ["+value+"] must be in format <ALPHANUM or MSDINT><SPACE><MSDINT><SPACE><Single word or Quoted text>");
            return null;
        }
        String sender = st.nextToken();
        if (!p1.matcher(sender).matches()) {
            System.out.println("Sender information ["+sender+"] is not in correct format <ALPHANUM or MSDINT>");
            return null;
        } else
            sms.setSender(sender);

        String recipient = st.nextToken();
        if (!p2.matcher(recipient).matches()) {
            System.out.println("Recipient information ["+recipient+"] is not in correct format <MSDINT>");
            return null;
        }
        else
            sms.setRecipient(recipient);


        String text = st.nextToken("").substring(1);
        if (!p3.matcher(text).matches()) {
            System.out.println("Text information ["+text+"] is not in correct format <single word or quoted text>");
            return null;
        }
        else
            sms.setText(text);

        return sms;
    }
}
