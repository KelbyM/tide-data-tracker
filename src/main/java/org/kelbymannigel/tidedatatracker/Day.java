package org.kelbymannigel.tidedatatracker;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a day which contains a date, tide data and moon data.
 */
@Entity
@Table(name = "DAYS")
public class Day {

    // INSTANCE VARIABLES

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /** The day's date. */
    @Column(unique = true, nullable = false)
    private LocalDate date;

    /** All tides for this day. */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "day", fetch = FetchType.LAZY)
    private List<TideData> tides;

    /** The moon data for this day. */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "moon_data_id", referencedColumnName = "id")
    private MoonData moonData;

    // CONSTRUCTOR

    /**
     * Default Constructor
     */
    public Day() {
        this.date = LocalDate.now();
        this.tides = new ArrayList<>();
        this.moonData = new MoonData();
    }

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

    public List<TideData> getTides() {
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

    /**
     * Loads and returns a VBox containing the Day's tide and moon data in a GUI.
     * @return The Vbox containing the Day's tide and moon data in a GUI.
     */
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
        if (!new ArrayList<>(tides).equals(new ArrayList<>(day.tides))) return false;
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
