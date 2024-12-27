package org.kelbymannigel.tidedatatracker;

import java.time.LocalDateTime;

public class TideData {

    // INSTANCE VARIABLES

    /** Time of the high or low tide. */
    private LocalDateTime time;

    /** Height of the high or low tide in feet. */
    private double tideHeight;

    /**
     * true: high tide
     * false: low tide
     * */
    private boolean isHighTide;

    // CONSTRUCTORS

    /**
     * Default Constructor
     */
    public TideData() {
        time = LocalDateTime.now();
        tideHeight = 0;
        isHighTide = true;
    }

    /**
     * Full Constructor
     * @param time Time of the high or low tide.
     * @param tideHeight Height of the high or low tide in feet.
     * @param isHighTide true: high tide, false: low tide
     */
    public TideData(LocalDateTime time, double tideHeight, boolean isHighTide) {
        this.time = time;
        this.tideHeight = tideHeight;
        this.isHighTide = isHighTide;
    }

    // ASSESSORS/MUTATORS

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public double getTideHeight() {
        return tideHeight;
    }

    public void setTideHeight(double tideHeight) {
        this.tideHeight = tideHeight;
    }

    public boolean isHighTide() {
        return isHighTide;
    }

    public void setHighTide(boolean highTide) {
        isHighTide = highTide;
    }

    // OTHER METHODS

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TideData tideData = (TideData) o;
        if (Double.compare(tideData.tideHeight, tideHeight) != 0) return false;
        if (isHighTide != tideData.isHighTide) return false;
        return time.equals(tideData.time);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = time.hashCode();
        temp = Double.doubleToLongBits(tideHeight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isHighTide ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TideData{" +
                "time=" + time +
                ", tideHeight=" + tideHeight +
                ", isHighTide=" + isHighTide +
                '}';
    }
}
