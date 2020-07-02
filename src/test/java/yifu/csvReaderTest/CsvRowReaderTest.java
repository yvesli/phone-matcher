package yifu.csvReaderTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import yifu.csvReader.CsvReader;
import yifu.csvReader.CsvRowReader;

import java.io.FileNotFoundException;
import java.util.Objects;

public class CsvRowReaderTest {
    @Test
    void fileReadingTest() {
        try{
            String path = Objects.requireNonNull(getClass().getClassLoader().getResource("Name.csv")).getPath();
            CsvReader<String> csvReader = new CsvRowReader(path);
            Assertions.assertTrue(csvReader.hasNext());
            Assertions.assertEquals("id,name", csvReader.next());
            Assertions.assertTrue(csvReader.hasNext());
            Assertions.assertEquals("001,john", csvReader.next());
            Assertions.assertTrue(csvReader.hasNext());
            Assertions.assertEquals("003,jane", csvReader.next());
            Assertions.assertTrue(csvReader.hasNext());
            Assertions.assertEquals("007,james", csvReader.next());
            Assertions.assertFalse(csvReader.hasNext());
            Assertions.assertNull(csvReader.next());
            Assertions.assertNull(csvReader.next());
        } catch (FileNotFoundException e) {
            System.err.println("Name.csv NOT FOUND");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Name.csv NOT FOUND");
        }
    }
}
