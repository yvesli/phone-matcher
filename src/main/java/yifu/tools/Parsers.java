package yifu.tools;

import yifu.tools.tuple.Tuple;

public class Parsers {

    public static Tuple parseLine(String line) {
        return new Tuple(line.split(","));
    }

}
