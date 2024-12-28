package org.kelbymannigel.tidedatatracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TideDataFileReader implements TideDataProcessor {

    // INSTANCE VARIABLES

    /**
     * The path to the text file.
     */
    String filePath;

    /**
     * The reader that connects to the text file.
     */
    private BufferedReader reader;

    // CONSTRUCTORS

    /**
     * Connects to the tide data file.
     * @param filePath The file path for the tide data text file.
     */
    public TideDataFileReader(String filePath) {
        try {
            setFilePath(filePath);
            // connecting the reader to the file
            reader = new BufferedReader(new FileReader(filePath));
            findData();
        } catch(IOException e) {
            System.out.println("Error: Unable to connect to the file. " + e.getMessage());
        }
    }

    // ASSESSORS/MUTATORS

    /**
     * @return The BufferedReader that connects to the text file.
     */
    public BufferedReader getReader() {
        return reader;
    }

    /**
     * Sets the BufferedReader variable.
     * @param reader The BufferedReader.
     * @return true: setting the reader was successful
     *         false: reader is null
     */
    public boolean setReader(BufferedReader reader) {
        // check for null input
        if(reader == null) return false;
        this.reader = reader;
        return true;
    }

    /**
     * @return The path to the text file.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets the filePath variable.
     * @param filePath The file path to the tide data file.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // OTHER METHODS

    /**
     * Finds the data header line in the tide data file.
     * @return true: Data header line was found.
     *         false: Data header line was not found.
     */
    public boolean findData() {
        try {
            // connecting the reader to the file
            String line;
            // iterating until the tide data header is found
            while((line = reader.readLine()) != null) {
                // separating line at spaces
                String[] parts = line.split("\\s+");
                if(parts.length != 6) continue;
                // checking if the data header line has been found
                if(
                        parts[0].equals("Date") &&
                        parts[1].equals("Day") &&
                        parts[2].equals("Time") &&
                        parts[3].equals("Pred(Ft)") &&
                        parts[4].equals("Pred(cm)") &&
                        parts[5].equals("High/Low")
                ) {
                    // data header line has been found
                    return true;
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        // data header line has not been found
        return false;
    }

    /**
     * Returns the next day's tide data.
     * @return The next day's tide data in an ArrayList.
     */
    @Override
    public ArrayList<TideData> getNextTideData() {
        ArrayList<TideData> tides = new ArrayList<>();
        int dayNum = 0;
        // iterate while there is another line
        while(hasNextTideData()) {
            String line = "";
            try {
                // read the next line in the file and then check if it is null
                line = reader.readLine();
                if(line == null) return null;
                // if the line is empty, then iterate until data is found
                while(line != null && line.isEmpty()) {
                    line = reader.readLine();
                }
                assert line != null;
                // splitting the line by spaces
                String[] parts = line.split("\\s+");
                String date = parts[0],
                       time = parts[2],
                       height = parts[3],
                       highLow = parts[5];
                // creating a LocalDateTime object
                String[] dateParts = date.split("/");
                String[] timeParts = time.split(":");
                // exit the method if a different day has been reached
                if(dayNum != 0 && dayNum != Integer.parseInt(dateParts[2])) {
                    // go back a line
                    reader.reset();
                    break;
                }
                dayNum = Integer.parseInt(dateParts[2]);
                LocalDateTime dateTime = LocalDateTime.of(
                        Integer.parseInt(dateParts[0]),
                        Integer.parseInt(dateParts[1]),
                        Integer.parseInt(dateParts[2]),
                        Integer.parseInt(timeParts[0]),
                        Integer.parseInt(timeParts[1]));
                // adding the tide to the ArrayList
                tides.add(new TideData(dateTime, Double.parseDouble(height), highLow.equals("H")));
                // mark the position in the BufferedReader
                reader.mark(1000);
            } catch(IOException ioe) {
                System.out.println("Error occurred when reading the tide data file: " + ioe.getMessage());
            }
        }
        return tides;
    }

    /**
     * Checks if there is another line of text in the tide data file.
     * @return true: another line of text exists
     *         false: the end of file has been reached
     */
    @Override
    public boolean hasNextTideData() {
        String line = "";
        try {
            // check if the BufferedReader has been closed already
            if(!reader.ready()) return false;
            // set return location
            reader.mark(4096);
            // read the next line
            line = reader.readLine();
            // check if location was valid
            if(line != null) {
                // reset read location
                reader.reset();
                return true;
            }
            // close the reader
            reader.close();
            return false;
        } catch (IOException ioe) {
            System.out.println("Error occurred when reading ahead in tide data file: " + ioe.getMessage());
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TideDataFileReader that = (TideDataFileReader) o;
        if (!filePath.equals(that.filePath)) return false;
        return reader.equals(that.reader);
    }

    @Override
    public int hashCode() {
        int result = filePath.hashCode();
        result = 31 * result + reader.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TideDataFileReader{" +
                "filePath='" + filePath + '\'' +
                ", reader=" + reader +
                '}';
    }
}
