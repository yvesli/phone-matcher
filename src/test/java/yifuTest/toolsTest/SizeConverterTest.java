package yifuTest.toolsTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import yifu.tools.SizeConverter;

import java.text.ParseException;

public class SizeConverterTest {
    @Test
    void testSizeWithCorrectFormat() throws ParseException {
        String mbSizeString = "100MB";
        long mbSize = SizeConverter.toByte(mbSizeString);
        Assertions.assertEquals(100 * 1024 * 1024, mbSize);

        String kbSizeString = "25kb";
        long kbSize = SizeConverter.toByte(kbSizeString);
        Assertions.assertEquals(25 * 1024, kbSize);

        String gbSizeString = "10Gb";
        long gbSize = SizeConverter.toByte(gbSizeString);
        Assertions.assertEquals(10 * 1024 * 1024 * 1024L, gbSize);
    }

    @Test
    void testSizeWithUnsupportedFormat() {
        String wrongSizeString1 = "10ZBD";
        Assertions.assertThrows(ParseException.class, () -> {
            SizeConverter.toByte(wrongSizeString1);
        });

        String wrongSizeString2 = "CA10";
        Assertions.assertThrows(ParseException.class, () -> {
            SizeConverter.toByte(wrongSizeString2);
        });
    }
}
