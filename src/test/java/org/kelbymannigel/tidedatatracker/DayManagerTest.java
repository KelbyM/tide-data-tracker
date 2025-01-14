package org.kelbymannigel.tidedatatracker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DayManagerTest {

    DayManager manager;

    Day firstDay, lastDay;

    /**
     * Sets up the manager and creates the test values.
     */
    @BeforeEach
    void setUp() {
        DayManager.createInstance("src\\test\\resources\\TideDataExample.txt", "src\\test\\resources\\MoonDataExample.txt");
        manager = DayManager.getInstance();
        manager.parseYear();
        // TideData for 1/1/24
        ArrayList<TideData> tidesForFirstDay = new ArrayList<>();
        tidesForFirstDay.add(new TideData(LocalDateTime.of(2024, 1, 1, 1, 12), 3.97, true));
        tidesForFirstDay.add(new TideData(LocalDateTime.of(2024, 1, 1, 5, 53), 2.99, false));
        tidesForFirstDay.add(new TideData(LocalDateTime.of(2024, 1, 1, 11, 40), 4.72, true));
        tidesForFirstDay.add(new TideData(LocalDateTime.of(2024, 1, 1, 18, 54), 0.70, false));
        // MoonData for 01/01/24
        MoonData firstDayMoonData = new MoonData(74.38, 19.478);
        firstDay = new Day(LocalDate.of(2024, 1, 1), tidesForFirstDay, firstDayMoonData);
        // TideData for 12/31/24
        ArrayList<TideData> tidesForLastDay = new ArrayList<>();
        tidesForLastDay.add(new TideData(LocalDateTime.of(2024, 12, 31, 2, 27), 2.42, false));
        tidesForLastDay.add(new TideData(LocalDateTime.of(2024, 12, 31, 8, 43), 6.91, true));
        tidesForLastDay.add(new TideData(LocalDateTime.of(2024, 12, 31, 15, 59), -1.17, false));
        tidesForLastDay.add(new TideData(LocalDateTime.of(2024, 12, 31, 22, 32), 4.05, true));
        // MoonData for 12/31/24
        MoonData lastDayMoonData = new MoonData(0.49, 0.523);
        lastDay = new Day(LocalDate.of(2024, 12, 31), tidesForLastDay, lastDayMoonData);
    }

    /**
     * Tests if the first and last day are correct.
     */
    @Test
    void getYearData() {
        assertTrue(manager.getYearData().getFirst().equals(firstDay) && manager.getYearData().getLast().equals(lastDay));
    }
}