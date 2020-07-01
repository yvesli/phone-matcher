package yifu.matcher;

import yifu.csvReader.CsvReader;
import yifu.csvReader.CsvRowReader;
import yifu.tools.Parsers;
import yifu.tools.tuple.Tuple;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


/**
 * Use this class only when both input files are relatively small, can be contained in memory
 */
public class InMemoryMatcher implements Matcher {

    // == private fields ==
    private CsvReader<String> nameFileReader;
    private CsvReader<String> phoneFileReader;

    // key: name_id, value: list of phone numbers
    private final HashMap<String, List<String>> idToPhone = new HashMap<>();

    // key: phone_column_titles, value: corresponding column index number
    private final HashMap<String, Integer> phoneIndexMap = new HashMap<>();

    private Tuple nameColTitles;

    // index of the id column of name
    private int nameIdColNum;

    // max size of phone numbers (the max number of phones that one name has)
    private int maxPhoneNumSize = 1;

    // if match being ran
    private boolean haveMatched = false;

    private Tuple nextEntry;

    /**
     * Constructor requires two file path for matching
     * @param nameFilePath path to file Name.csv
     * @param phoneFilePath path to file phone.csv
     */
    public InMemoryMatcher(String nameFilePath, String phoneFilePath) {
        try {
            // load both files to their csvReader
            nameFileReader = new CsvRowReader(nameFilePath);
            phoneFileReader = new CsvRowReader(phoneFilePath);

            // load titles of the phone and store their column index in HashMap
            loadTitles();
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
            e.printStackTrace();
        }
    }

    /**
     * Get titles columns of output file
     * @return titles Tuple
     */
    @Override
    public Tuple getTitles() {
        assertMatch();
        List<String> titles = new ArrayList<>();
        for (String title: nameColTitles) {
            titles.add(title);
        }
        if (maxPhoneNumSize > 1) {
            for (int i = 1; i <= maxPhoneNumSize; i++) {
                titles.add("phone_".concat(String.valueOf(i)));
            }
        }
        return new Tuple(titles);
    }

    /**
     * Check if there is next tuple
     * @return true if there is more tuple, false otherwise
     */
    @Override
    public boolean hasNext() {
        assertMatch();
        return nextEntry != null;
    }

    /**
     * Get the next tuple
     * @return the next tuple available, null otherwise
     */
    @Override
    public Tuple next() {
        assertMatch();
        Tuple currentTuple = nextEntry;
        nextEntry = getNextEntry();
        return currentTuple;
    }

    /**
     * Initiate and run matching
     */
    @Override
    public void match() {
        while(phoneFileReader.hasNext()) {
            Tuple row = Parsers.parseLine(phoneFileReader.next());
            String id = row.getNthValue(phoneIndexMap.get("name_id"));
            String phone = row.getNthValue(phoneIndexMap.get("phone"));
            if (idToPhone.containsKey(id)) {
                List<String> phoneList = idToPhone.get(id);
                maxPhoneNumSize = Math.max(maxPhoneNumSize, phoneList.size() + 1);
                phoneList.add(phone);
            } else {
                List<String> phones = new ArrayList<>();
                phones.add(phone);
                idToPhone.put(id, phones);
            }
        }
        nextEntry = getNextEntry();
        haveMatched = true;
    }

    private void assertMatch() {
        assert haveMatched : "Call match first to run";
    }

    private void loadTitles() {
        // load titles of the name and store their column index in HashMap
        if (nameFileReader.hasNext()) {
            String titleRow = nameFileReader.next();
            getNameIdColNum(titleRow);
            nameColTitles = Parsers.parseLine(titleRow);
        }

        // load titles of phone
        if (phoneFileReader.hasNext()) {
            Tuple colTitles = Parsers.parseLine(phoneFileReader.next());
            for (int i = 0; i < colTitles.size(); i++) {
                phoneIndexMap.put(colTitles.getNthValue(i), i);
            }
        }
    }

    /**
     *  Organize column number into hashMap
     */
    private void getNameIdColNum(String titleRow) {
        Tuple nameTitles = Parsers.parseLine(titleRow);
        for (int i = 0; i < nameTitles.size(); i++) {
            if (nameTitles.getNthValue(i).equals("id")) {
                nameIdColNum = i;
                break;
            }
        }
    }

    private Tuple getNextEntry() {
        while (nameFileReader.hasNext()) {
            Tuple nameRow = Parsers.parseLine(nameFileReader.next());
            String id = nameRow.getNthValue(nameIdColNum);
            if (idToPhone.containsKey(id)) {
                List<String> phoneList = idToPhone.get(id);
                List<String> trailingEmptySpace = Collections.nCopies(maxPhoneNumSize - phoneList.size(), "");
                phoneList.addAll(trailingEmptySpace);
                List<String> nxtTuple = new ArrayList<>();
                for (String s: nameRow) {
                    nxtTuple.add(s);
                }
                nxtTuple.addAll(phoneList);
                return new Tuple(nxtTuple);
            }
        }
        return null;
    }
}
