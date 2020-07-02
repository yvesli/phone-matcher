package yifu.tools.tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Tuple implements Iterable<String> {

    private final String[] strings;

    public Tuple(String[] strings) {
        this.strings = strings;
    }

    public Tuple(List<String> stringList) {
        strings =  stringList.toArray(new String[0]);
    }

    public String getNthValue(int n) {
        return strings[n];
    }

    public int size() {
        return strings.length;
    }

    @Override
    public Iterator<String> iterator() {
        return Arrays.stream(strings).iterator();
    }

    public List<String> toList() {
        return new ArrayList<>(Arrays.asList(strings));
    }

    public String toString() {
        return Arrays.toString(strings);
    }

    public String toCsvFormat() {
        return String.join(",", strings);
    }
}
