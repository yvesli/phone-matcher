package yifu.tools;

import java.text.ParseException;

public class SizeConverter {

    public static long toByte(String sizeString) throws ParseException {
        StringBuilder numberSb = new StringBuilder();
        StringBuilder magnitudeSb = new StringBuilder();
        long bytes;

        for (int i = 0; i < sizeString.length(); i++) {
            char c = sizeString.charAt(i);
            if (Character.isDigit(c)) {
                numberSb.append(c);
            } else if (Character.isAlphabetic(c)) {
                magnitudeSb.append(Character.toUpperCase(c));
            } else {
                throw new ParseException("cannot parse " + sizeString, i);
            }
        }

        int number = Integer.parseInt(numberSb.toString());
        String magnitude = magnitudeSb.toString();
        switch (magnitude) {
            case "KB":
                return 1024L * number;
            case "MB":
                return 1024 * 1024L * number;
            case "GB":
                return 1024 * 1024 * 1024L * number;
            case "TB":
                return 1024 * 1024 * 1024 * 1024L * number;
            default:
                throw new ParseException("Cannot parse " + magnitude, 0);
        }
    }
}
