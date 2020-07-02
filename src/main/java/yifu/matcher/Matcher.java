package yifu.matcher;

import yifu.tools.tuple.Tuple;

import java.util.Iterator;

public interface Matcher extends Iterator<Tuple> {

    /**
     * Get titles of matched output file
     * (Call this method when there is no next tuple to get accurate results for all implementations)
     * @return Tuple that contains title columns
     */
    Tuple getTitles();
}
