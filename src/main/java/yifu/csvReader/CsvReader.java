package yifu.csvReader;

import java.io.FileNotFoundException;
import java.util.Iterator;

/**
 * Interface that defines general CsvReader format
 * extends Iterator interface
 * @param <String> line of csv file
 */
public interface CsvReader<String> extends Iterator<String> {
    void reset() throws FileNotFoundException;
}
