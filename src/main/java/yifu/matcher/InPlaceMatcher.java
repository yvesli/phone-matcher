package yifu.matcher;

import yifu.csvReader.CsvReader;
import yifu.csvReader.CsvRowReader;
import yifu.tools.Parsers;
import yifu.tools.tuple.Tuple;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


/**
 * Exhaustive implementation of matcher
 * Low memory usage and high time complexity (space: O(1), time: theta(phone x name))
 */
public class InPlaceMatcher implements Matcher {

    private CsvReader<String> nameReader;
    private CsvReader<String> phoneReader;

    private Tuple nameTitles;
    private Tuple nextEntry;

    private int maxPhoneNumbers = 1;
    private boolean haveMatched = false;

    public InPlaceMatcher(String nameFilePath, String phoneFilePath) {
        try {
            nameReader = new CsvRowReader(nameFilePath);
            phoneReader = new CsvRowReader(phoneFilePath);

            if (nameReader.hasNext()) {
                nameTitles = Parsers.parseLine(nameReader.next());
            }

            if (phoneReader.hasNext()) {
                phoneReader.next();
            }

        } catch (FileNotFoundException e) {
            System.err.println("FILE NOT FOUND");
            e.printStackTrace();
        }
    }

    /**
     * Get titles of the output file
     * Use this class after matching is done
     * @return Tuple titles of output file
     */
    @Override
    public Tuple getTitles() {
        List<String> titles = new ArrayList<>(nameTitles.toList());
        if (maxPhoneNumbers == 1) {
            titles.add("phone");
        } else {
            for (int i = 1; i <= maxPhoneNumbers; i++) {
                titles.add("phone_" + i);
            }
        }
        return new Tuple(titles);
    }

    /**
     * Check if there is more tuples to be iterated
     * @return true if there is, false otherwise
     */
    @Override
    public boolean hasNext() {
        if (!haveMatched) {
            nextEntry = match();
        }
        return nextEntry != null;
    }

    /**
     * Get next matched tuple
     * @return Tuple, null if there isn't any left
     */
    @Override
    public Tuple next() {
        if (!haveMatched) {
            nextEntry = match();
        }
        Tuple currentEntry = nextEntry;
        nextEntry = match();
        return currentEntry;
    }

    private Tuple match() {
        haveMatched = true;
        while (nameReader.hasNext()) {

            // read in next name tuple
            Tuple name = Parsers.parseLine(nameReader.next());
            List<String> numbers = new ArrayList<>();

            // iterate through entire phone file for match
            while (phoneReader.hasNext()) {
                Tuple phone = Parsers.parseLine(phoneReader.next());
                String id = phone.getNthValue(2);
                if (id.equals(name.getNthValue(0))) {
                    numbers.add(phone.getNthValue(1));
                }
            }

            // reset phoneReader pointer
            try {
                phoneReader.reset();

                // skip title line
                if (phoneReader.hasNext()) {
                    phoneReader.next();
                }
            } catch (FileNotFoundException e) {
                System.err.println("FILE NOT FOUND");
                e.printStackTrace();
                return null;
            }

            // only return when there is phone number
            if (numbers.size() > 0) {
                // update max phone numbers
                maxPhoneNumbers = Math.max(numbers.size(), maxPhoneNumbers);

                // generate match results
                List<String> matchResult = name.toList();
                matchResult.addAll(numbers);

                return new Tuple(matchResult);
            }
        }
        return null;
    }
}
