package org.kelbymannigel.tidedatatracker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;

import static org.junit.jupiter.api.Assertions.*;

class MoonDataFileReaderTest {

    MoonDataFileReader reader;

    // TEST VALUES

    double[] illuminationValues = {74.38, 65.82, 56.66},
             daysSinceNewMoonValues = {19.478, 20.478, 21.478};

    @BeforeEach
    void setup() {
        reader = new MoonDataFileReader("src\\test\\resources\\MoonDataExample.txt");
    }

    @AfterEach
    void reset() {
        reader = null;
    }

    /**
     * Tests if getNextMoonData can read multiple days of moon data correctly.
     */
    @Test
    void getNextMoonData() {
        MoonData moonData;
        for(int day = 0; day < daysSinceNewMoonValues.length; day++) {
            moonData = reader.getNextMoonData();
            assertEquals(illuminationValues[day], moonData.getIllumination());
            assertEquals(daysSinceNewMoonValues[day], moonData.getDaysSinceNewMoon());
        }
    }

    /**
     * Tests if hasNextMoonData returns true when more moon data exists and false if the end of file has been reached.
     */
    @Test
    void hasNextMoonData() {
        MoonData moonData;
        for(int day = 0; day < 366; day++) {
            assertTrue(reader.hasNextMoonData());
            moonData = reader.getNextMoonData();
        }
        assertFalse(reader.hasNextMoonData());
    }
}