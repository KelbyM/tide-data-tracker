package org.kelbymannigel.tidedatatracker;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;

public class Day {

    // INSTANCE VARIABLES
    private LocalDate date;

    private ArrayList<TideData> tides;

    private MoonData moonData;

    // CONSTRUCTOR
    /**
     * Full Constructor
     * @param date The date of the day.
     * @param tides All tide data for the day.
     * @param moonData Moon data for the day.
     */
    public Day(LocalDate date, ArrayList<TideData> tides, MoonData moonData) {
        this.date = date;
        this.tides = tides;
        this.moonData = moonData;
    }

    // ASSESSORS/MUTATORS

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ArrayList<TideData> getTides() {
        return tides;
    }

    public void setTides(ArrayList<TideData> tides) {
        this.tides = tides;
    }

    public MoonData getMoonData() {
        return moonData;
    }

    public void setMoonData(MoonData moonData) {
        this.moonData = moonData;
    }

    // OTHER METHODS

    public VBox getGUI() {
        try {
            // load DayView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DayView.fxml"));
            VBox vBox = loader.load();
            // access the DayViewController
            DayViewController controller = loader.getController();
            // add the data to the DayView
            controller.setDayInformation(this);
            return vBox;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Day day = (Day) o;
        if (!date.equals(day.date)) return false;
        if (!tides.equals(day.tides)) return false;
        return moonData.equals(day.moonData);
    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + tides.hashCode();
        result = 31 * result + moonData.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Day{" +
                "date=" + date +
                ", tides=" + tides +
                ", moonData=" + moonData +
                '}';
    }
}
