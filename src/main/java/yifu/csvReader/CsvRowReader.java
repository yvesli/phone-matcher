package yifu.csvReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class is used for simple csv file reading with reasonable row and column numbers
 * Basic implementation that implements iterator interface
 */
public class CsvRowReader implements CsvReader<String> {

    // == private fields ==
    private BufferedReader csvReader;
    private String nextLine;
    private final String path;

    /**
     *
     * @param pathToCsv complete path to csv file to read
     * @throws FileNotFoundException to be handled in upper level class
     */
    public CsvRowReader(String pathToCsv) throws FileNotFoundException {
        csvReader = new BufferedReader(new FileReader(pathToCsv));
        path = pathToCsv;
        loadNextLine();
    }

    /**
     * Check if the file has next line
     * @return true if there is next line, false otherwise
     */
    @Override
    public boolean hasNext() {
        return nextLine != null;
    }

    /**
     * Get the next line of the file
     * @return next line
     */
    @Override
    public String next() {
        String currentLine = nextLine;
        loadNextLine();
        return currentLine;
    }

    /**
     * Resets the CsvReader to initial position
     * @throws FileNotFoundException file on the path provided is not found
     */
    @Override
    public void reset() throws FileNotFoundException {
        csvReader = new BufferedReader(new FileReader(path));
        loadNextLine();
    }

    /**
     * load next line of the file into memory
     * Stored as a private field of the class
     * Can be accessed when public method next() is called
     */
    private void loadNextLine() {
        try {
            nextLine = csvReader.readLine();
        } catch (IOException e) {
            System.err.println("IOException thrown when reading file " + path);
            e.printStackTrace();
        }
    }


}
