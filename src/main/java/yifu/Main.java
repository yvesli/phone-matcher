package yifu;

import yifu.csvWriter.CsvWriter;
import yifu.matcher.Matcher;
import yifu.matcher.MatcherFactory;

import java.io.IOException;
import java.net.URL;

public class Main {
    public static void main(String[] args) {

        // name and phone file path
        String nameFilePath = args[0];
        String phoneFilePath = args[1];

        // output file path
        String outputFilePath = args[2];


        Matcher matcher = MatcherFactory.createMatcher(nameFilePath, phoneFilePath);

        // write to file
        try {
            CsvWriter writer = new CsvWriter(outputFilePath);
            writer.writeTuple(matcher.getTitles());
            writer.flush();
            while (matcher.hasNext()) {
                writer.writeTuple(matcher.next());
                writer.flush();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Cannot Write to File: " + outputFilePath);
        }

    }
}
