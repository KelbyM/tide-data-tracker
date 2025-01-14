package org.kelbymannigel.tidedatatracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/**
 * Connects to a moon data text file and creates MoonData objects using the data.
 */
public class MoonDataFileReader implements MoonDataProcessor {

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
     * Connects to the moon data file.
     * @param filePath The file path for the moon data text file.
     */
    public MoonDataFileReader(String filePath) {
        try {
            setFilePath(filePath);
            reader = new BufferedReader(new FileReader(filePath));
            // try to find the data header line in the file
            if(!findData()) System.out.println("Error: Unable to find the data header line.\n");
            //setReader(new BufferedReader(reader));
        } catch(IOException e) {
            System.out.println("Error: Unable to connect to the file. " + e.getMessage());
        }
    }

    // ASSESSORS/MUTATORS

    /**
     * @return The path to the text file.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets the filePath variable.
     * @param filePath The file path to the data file.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

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


    // OTHER FUNCTIONS

    /**
     * Finds the data header line in the moon data file.
     * @return true: Data header line was found.
     *         false: Data header line was not found.
     */
    public boolean findData() {
        try {
            // connecting the reader to the file
            String line;
            // iterating until the moon data header is found
            while((line = reader.readLine()) != null) {
                line = line.trim();
                // separating line at spaces
                String[] parts = line.split("\\s+");
                // checking if the data header line has been found
                if(
                        parts[0].equalsIgnoreCase("Date") &&
                        parts[1].equalsIgnoreCase("Time") &&
                        parts[2].equalsIgnoreCase("Phase") &&
                        parts[3].equalsIgnoreCase("Age")
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
     * Returns the next day's moon data.
     * @return The next day's moon data.
     */
    @Override
    public MoonData getNextMoonData() {
        // if there is no next item to read
        if(!hasNextMoonData()) return null;
        // place to store the file input
        String[] input;
        MoonData moonData = new MoonData();
        try {
            // read 11 lines
            for (int line = 0; line < 11; line++) {
                reader.readLine();
            }
            //Read and parse needed line for the day's average
            input = reader.readLine().split(" {2}");
            //Read the other 12 lines
            for (int line = 0; line < 12; line++) {
                reader.readLine();
            }
            //Parse the info from the input
            double illumination = Double.parseDouble(input[1]);
            double daysSinceNewMoon = Double.parseDouble(input[2]);
            moonData = new MoonData(illumination, daysSinceNewMoon);
        } catch (IOException ioe) {

            System.out.println("Error occurred when reading moon file: " + ioe.getMessage());
        }
        return moonData;
    }

    /**
     * Checks if there is another line of text in the moon data file.
     * @return true: another line of text exists
     *         false: the end of file has been reached
     */
    @Override
    public boolean hasNextMoonData() {
        String line = "";
        try {
            // check if the BufferedReader has been closed already
            if(!reader.ready()) return false;
            // set return location
            reader.mark(4096);
            // read ahead looking for another 24 lines
            for (int linesAhead = 0; linesAhead < 24; linesAhead++) {
                line = reader.readLine();
            }
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
            System.out.println("Error occurred when reading ahead in moon file: " + ioe.getMessage());
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoonDataFileReader that = (MoonDataFileReader) o;
        if (!Objects.equals(filePath, that.filePath)) return false;
        return Objects.equals(reader, that.reader);
    }

    @Override
    public int hashCode() {
        int result = filePath != null ? filePath.hashCode() : 0;
        result = 31 * result + (reader != null ? reader.hashCode() : 0);
        return result;
    }
}
