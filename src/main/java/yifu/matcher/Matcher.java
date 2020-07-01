package yifu.matcher;

import yifu.csvReader.CsvReader;
import yifu.tools.tuple.Tuple;

import java.io.OutputStream;
import java.util.Iterator;

public interface Matcher extends Iterator<Tuple> {
    void match();
    Tuple getTitles();
}
