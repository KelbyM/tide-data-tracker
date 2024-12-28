package org.kelbymannigel.tidedatatracker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TideDataFileReaderTest {

    TideDataFileReader reader;

    // TEST VALUES

    int[] hours1 = {1, 5, 11, 18},
          hours2 = {6, 13, 20};

    int[] minutes1 = {12, 53, 40, 54},
          minutes2 = {18, 42, 11};

    double[] heights1 = {3.97, 2.99, 4.72, 0.70},
             heights2 = {6.44, -0.70, 3.78};

    boolean[] isHighTide1 = {true, false, true, false},
              isHighTide2 = {true, false, true};

    @BeforeEach
    void setup() {
        reader = new TideDataFileReader("src\\test\\resources\\TideDataExample.txt");
    }

    @AfterEach
    void reset() {
        reader = null;
    }

    /**
     * Tests if hasNextTideData returns true when more tide data exists and false if the end of file has been reached.
     */
    @Test
    void getNextTideData() {
        // getting 2024/01/01 tide data
        ArrayList<TideData> day = reader.getNextTideData();
        for(int i = 0; i < 4; i++) {
            assertEquals(hours1[i], day.get(i).getTime().getHour());
            assertEquals(minutes1[i], day.get(i).getTime().getMinute());
            assertEquals(heights1[i], day.get(i).getTideHeight());
            assertEquals(isHighTide1[i], day.get(i).isHighTide());
        }
        // getting 2024/01/08 tide data
        for(int i = 0; i < 7; i++) {
            day = reader.getNextTideData();
        }
        for(int i = 0; i < 3; i++) {
            assertEquals(hours2[i], day.get(i).getTime().getHour());
            assertEquals(minutes2[i], day.get(i).getTime().getMinute());
            assertEquals(heights2[i], day.get(i).getTideHeight());
            assertEquals(isHighTide2[i], day.get(i).isHighTide());
        }
    }

    /**
     * Tests if hasNextTideData returns true when more tide data exists and false if the end of file has been reached.
     */
    @Test
    void hasNextTideData() {
        ArrayList<TideData> temp;
        // loop through the year and check that the hasNextTideInfo returns the correct values
        for(int day = 0; day < 366; day++) {
            assertTrue(reader.hasNextTideData());
            temp = reader.getNextTideData();
        }
        // end of the data file has been reached, check if another day exists
        assertFalse(reader.hasNextTideData());
    }
}