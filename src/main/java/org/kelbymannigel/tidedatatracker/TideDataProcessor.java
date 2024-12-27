package org.kelbymannigel.tidedatatracker;

import java.util.ArrayList;

public interface TideDataProcessor {

    /**
     * Returns an ArrayList of tide data for the next day.
     * @return An ArrayList of TideData objects containing the tide data for a day.
     */
    public ArrayList<TideData> getNextTideData();

    /**
     * Checks if there is another day of tide data.
     * @return true: More data exists.
     *         false: No more data exists.
     */
    public boolean hasNextTideData();
}
