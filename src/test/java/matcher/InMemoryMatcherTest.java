package matcher;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import yifu.matcher.Matcher;
import yifu.matcher.MatcherFactory;
import yifu.tools.tuple.Tuple;

import java.io.FileNotFoundException;
import java.util.Objects;

public class InMemoryMatcherTest {

    @Test
    void completeInMemeoryTest() {
        // find path to each files
        String namePath = Objects.requireNonNull(getClass().getClassLoader().getResource("Name.csv")).getPath();
        String phonePath = Objects.requireNonNull(getClass().getClassLoader().getResource("Phone.csv")).getPath();

        // initialize matcher
        Matcher matcher = MatcherFactory.createSimpleMatcher(namePath, phonePath);
        matcher.match();

        // test titles of output file
        Tuple titles = matcher.getTitles();
        Assertions.assertEquals("id", titles.getNthValue(0));
        Assertions.assertEquals("name", titles.getNthValue(1));
        Assertions.assertEquals("phone_1", titles.getNthValue(2));
        Assertions.assertEquals("phone_2", titles.getNthValue(3));

        // test first matcher result
        Assertions.assertTrue(matcher.hasNext());
        Tuple row1 = matcher.next();
        Assertions.assertEquals("003", row1.getNthValue(0));
        Assertions.assertEquals("jane", row1.getNthValue(1));
        Assertions.assertEquals("345-6789", row1.getNthValue(2));
        Assertions.assertEquals("", row1.getNthValue(3));

        // test second matcher result
        Assertions.assertTrue(matcher.hasNext());
        Tuple row2 = matcher.next();
        Assertions.assertEquals("007", row2.getNthValue(0));
        Assertions.assertEquals("james", row2.getNthValue(1));
        Assertions.assertEquals("123-4567", row2.getNthValue(2));
        Assertions.assertEquals("555-5555", row2.getNthValue(3));

        // test no more results
        Assertions.assertFalse(matcher.hasNext());
        Assertions.assertNull(matcher.next());
    }
}
