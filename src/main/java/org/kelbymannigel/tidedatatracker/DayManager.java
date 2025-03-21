package org.kelbymannigel.tidedatatracker;

import java.io.File;
import java.util.ArrayList;

/**
 * Manages the creation of Day objects. Contains a year of Day objects.
 */
public class DayManager {

    // INSTANCE VARIABLES

    private static DayManager dayManager;

    /** The file path to the tide data file. */
    private String tideDataFilePath;

    /** The file path to the moon data file. */
    private String moonDataFilePath;

    /** A year of tide and moon data. */
    private ArrayList<Day> yearData;

    // CONSTRUCTORS

    /**
     * Default Constructor
     */
    private DayManager() {
        this.tideDataFilePath = "";
        this.moonDataFilePath = "";
        this.yearData = new ArrayList<>();
    }

    /**
     * Sets the tide and moon data file paths.
     * @param tideDataFilePath The file path to the tide data.
     * @param moonDataFilePath The file path to the moon data.
     */
    private DayManager(String tideDataFilePath, String moonDataFilePath) {
        this.tideDataFilePath = tideDataFilePath;
        this.moonDataFilePath = moonDataFilePath;
        this.yearData = new ArrayList<>();
    }

    // SINGLETON PATTERN METHODS

    /**
     * Initializes the dayManager variable using the file paths.
     * @param tideDataFilePath The file path to the tide data file.
     * @param moonDataFilePath The file path to the moon data file.
     */
    public static void createInstance(String tideDataFilePath, String moonDataFilePath) {
        if(dayManager == null) {
            dayManager = new DayManager(tideDataFilePath, moonDataFilePath);
        }
        else {
            System.out.println("Error: createInstance has already been called.");
        }
    }

    /**
     * Initializes the dayManager variable.
     */
    public static void createInstance() {
        if(dayManager == null) {
            dayManager = new DayManager();
        }
        else {
            System.out.println("Error: createInstance has already been called.");
        }
    }

    /**
     * Returns the dayManager instance variable.
     * @return The dayManager variable.
     */
    public static DayManager getInstance() {
        if(dayManager == null) {
            System.out.println("Error: createInstance has not been called yet.");
            return null;
        }
        return dayManager;
    }

    // ASSESSORS/MUTATORS

    public static DayManager getDayManager() {
        return dayManager;
    }

    public static void setDayManager(DayManager dayManager) {
        DayManager.dayManager = dayManager;
    }

    public String getTideDataFilePath() {
        return tideDataFilePath;
    }

    public void setTideDataFilePath(String tideDataFilePath) {
        this.tideDataFilePath = tideDataFilePath;
    }

    public String getMoonDataFilePath() {
        return moonDataFilePath;
    }

    public void setMoonDataFilePath(String moonDataFilePath) {
        this.moonDataFilePath = moonDataFilePath;
    }

    public ArrayList<Day> getYearData() {
        return yearData;
    }

    public void setYearData(ArrayList<Day> yearData) {
        this.yearData = yearData;
    }

    // OTHER METHODS

    /**
     * Checks if both the tide and moon data file paths are valid.
     * @return True: Both files were found.
     *         False: One or both files were not found.
     */
    public boolean checkFilePaths() {
        boolean filePathsFound = true;
        // check the tide data file path
        if(tideDataFilePath == null) {
            System.out.println("Error: Tide data file path not set.");
            filePathsFound = false;
        } else {
            if(!new File(tideDataFilePath).exists()) {
                System.out.println("Error: Tide data file not found.");
                filePathsFound = false;

            }
        }
        // check the moon data file path
        if(moonDataFilePath == null) {
            System.out.println("Error: Moon data file path not set.");
            filePathsFound = false;
        } else {
            if(!new File(moonDataFilePath).exists()) {
                System.out.println("Error: Moon data file not found.");
                filePathsFound = false;
            }
        }
        return filePathsFound;
    }

    /**
     * Creates and returns a Day object with tide and moon data.
     * @param tideDataProcessor Reads tide data.
     * @param moonDataProcessor Reads moon data.
     * @return A Day object containing tide and moon data.
     */
    private Day parseDay(TideDataProcessor tideDataProcessor, MoonDataProcessor moonDataProcessor) {
        // get the tide data for the day
        ArrayList<TideData> tides = tideDataProcessor.getNextTideData();
        // get the moon data for the day
        MoonData moonData = moonDataProcessor.getNextMoonData();
        // creating the day object
        Day day = new Day(tides.getFirst().getTime().toLocalDate(), tides, moonData);
        // setting the day for each tide (many-to-one relationship)
        for(TideData tide: tides) {
            tide.setDay(day);
        }
        return day;
    }

    /**
     * Populates the ArrayList with Day objects containing data from the data files.
     */
    public void parseYear() {
        yearData = new ArrayList<>();
        // create the data processors
        TideDataProcessor tideDataProcessor = new TideDataFileReader(tideDataFilePath);
        MoonDataProcessor moonDataProcessor = new MoonDataFileReader(moonDataFilePath);
        // parse all data from the files
        while(moonDataProcessor.hasNextMoonData()) {
            yearData.add(parseDay(tideDataProcessor, moonDataProcessor));
        }
    }

    /**
     * Retrieves the data from the database;
     */
    public void getYearFromDatabase() {
        yearData = DatabaseManager.getYear();
    }
    /**
     * Passes the year data to the DatabaseManager so that they can be written to the database.
     */
    public void saveYear() {
        DatabaseManager.writeDays(yearData);
    }

    @Override
    public String toString() {
        return "DayManager{" +
                "TideDataFilePath='" + tideDataFilePath + '\'' +
                ", MoonDataFilePath='" + moonDataFilePath + '\'' +
                ", yearData=" + yearData +
                '}';
    }
}
