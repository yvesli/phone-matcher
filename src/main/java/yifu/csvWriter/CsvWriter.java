package yifu.csvWriter;

import yifu.tools.tuple.Tuple;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter {

    private final BufferedWriter writer;

    /**
     * Load path to where output file is stored
     * @param path path to output
     * @throws IOException when error writing to file
     */
    public CsvWriter(String path) throws IOException {
        File file;
        if (path.endsWith(".csv")) {
            file = new File(path);
        } else {
            file = new File(path + "/Output.csv");
        }
        writer = new BufferedWriter(new FileWriter(file));
    }

    /**
     * Write tuple to file
     * @param tuple tuple to write
     * @throws IOException when error writing to file
     */
    public void writeTuple(Tuple tuple) throws IOException {
        writer.write(tuple.toCsvFormat());
        writer.newLine();
    }

    /**
     * Flush the stream
     * @throws IOException when error flushing
     */
    public void flush() throws IOException {
        writer.flush();
    }

    /**
     * Close the stream
     * @throws IOException when error closing
     */
    public void close() throws IOException {
        writer.close();
    }
}
