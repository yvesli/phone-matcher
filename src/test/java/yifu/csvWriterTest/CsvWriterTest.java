package yifu.csvWriterTest;

import org.junit.jupiter.api.Test;
import yifu.csvWriter.CsvWriter;
import yifu.tools.tuple.Tuple;

import java.io.IOException;
import java.util.Objects;

public class CsvWriterTest {
    @Test
    void csvWriterTest() {
        try {
            String path = Objects.requireNonNull(getClass().getClassLoader().getResource("output.csv")).getPath();
            CsvWriter writer = new CsvWriter(path);
            writer.writeTuple(new Tuple(new String[]{"name,id,age,phone_1"}));
            writer.close();
        } catch (IOException e) {
            System.err.println("Cannot find path to output.csv");
        }
    }
}
