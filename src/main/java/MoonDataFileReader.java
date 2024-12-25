import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

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
            this.filePath = filePath;
            // connecting the reader to the file
            reader = new BufferedReader(new FileReader(filePath));
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
            double luminance = Double.parseDouble(input[1]);
            double timeSinceNewMoon = Double.parseDouble(input[2]);
            moonData = new MoonData(luminance, timeSinceNewMoon);
        } catch (IOException ioe) {

            System.out.println("Error occurred when reading moon file: " + ioe.getMessage());
        }
        return moonData;
    }

    @Override
    public boolean hasNextMoonData() {
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
