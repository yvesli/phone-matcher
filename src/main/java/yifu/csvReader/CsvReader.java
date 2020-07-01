package yifu.csvReader;

import java.io.FileNotFoundException;
import java.util.Iterator;

/**
 * Interface that defines general CsvReader format
 * extends Iterator interface
 * @param <T>
 */
public interface CsvReader<T> extends Iterator<T> {
    void reset() throws FileNotFoundException;
}
