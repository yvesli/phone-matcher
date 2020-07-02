package yifu.matcher;

import yifu.ResourceLoader;
import yifu.tools.SizeConverter;

import java.io.File;
import java.text.ParseException;

public class MatcherFactory {

    /**
     * Create matcher according to files
     * @param namePath path to name.csv
     * @param phonePath path to phone.csv
     * @return matcher object
     */
    public static Matcher createMatcher(String namePath, String phonePath) {
        File name = new File(namePath);
        File phone = new File(phonePath);

        try {
            String thresholdSizeString = ResourceLoader.getInstance().getConfig("file.size.threshold");
            long thresholdSize = SizeConverter.toByte(thresholdSizeString);
            if (name.length() < thresholdSize && phone.length() < thresholdSize) {
                return createSimpleMatcher(namePath, phonePath);
            }
            return createInPlaceMatcher(namePath, phonePath);
        } catch (ParseException e) {
            System.out.println("Cannot parse ");
            e.printStackTrace();
            return createInPlaceMatcher(namePath, phonePath);
        }
    }

    public static Matcher createSimpleMatcher(String namePath, String phonePath) {
        return new SimpleMatcher(namePath, phonePath);
    }

    public static Matcher createInPlaceMatcher(String namePath, String phonePath) {
        return new InPlaceMatcher(namePath, phonePath);
    }
}
