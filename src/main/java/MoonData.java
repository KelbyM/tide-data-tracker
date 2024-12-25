public class MoonData {

    // INSTANCE VARIABLES

    /** Moon phase names. */
    public static final String[] MOON_PHASES = {"New Moon", "Waxing Crescent", "First Quarter",
                                                "Waxing Gibbous", "Full Moon", "Waning Gibbous",
                                                "Last Quarter", "Waning Crescent"};

    /** The illumination percentage. */
    private double illumination;

    /** Number of days since the new moon. */
    private double daysSinceNewMoon;

    // CONSTRUCTORS

    /**
     * Default Constructor
     */
    public MoonData() {
        this.illumination = 0;
        this.daysSinceNewMoon = 0;
    }

    /**
     * Full Constructor
     * @param illumination The illumination percentage.
     * @param daysSinceNewMoon Number of days since the new moon.
     */
    public MoonData(double illumination, double daysSinceNewMoon) {
        if(!setIllumination(illumination)) {
            setIllumination(0);
        }
        if(!setDaysSinceNewMoon(daysSinceNewMoon)) {
            setDaysSinceNewMoon(0);
        }
    }

    // ASSESSORS/MUTATORS

    public double getIllumination() {
        return illumination;
    }

    /**
     * Sets the illumination value with acceptable values between 0 and 100.
     * @param illumination The illumination percentage.
     * @return true: if set
     *         false: not set
     */
    public boolean setIllumination(double illumination) {
        double minLuminance = 0;
        double maxLuminance = 100;
        // check for values outside of bounds
        if(illumination < minLuminance || illumination > maxLuminance) return false;
        this.illumination = illumination;
        return true;
    }

    public double getDaysSinceNewMoon() {
        return daysSinceNewMoon;
    }

    /**
     * Sets the time since new moon value with acceptable values between 0 and 31.
     * @param daysSinceNewMoon Number of days since the new moon.
     * @return true: if set
     *         false: not set
     */
    public boolean setDaysSinceNewMoon(double daysSinceNewMoon) {
        // check for values outside of bounds
        double minTime = 0;
        double maxTime = 31;
        if(daysSinceNewMoon < minTime || daysSinceNewMoon > maxTime) return false;
        // set the value
        this.daysSinceNewMoon = daysSinceNewMoon;
        return true;
    }

    // OTHER METHODS

    public String processPhaseName() {
        // calculate the length of a phase of the moon in time since previous
        double maxTime = 29.5;
        double phaseLength = maxTime / MOON_PHASES.length;
        // get the correct moon phase name based off of current illumination
        int phaseNumber = (int)(daysSinceNewMoon / phaseLength);
        // check if outside of bounds and set to max phase number
        if(phaseNumber >= MOON_PHASES.length) {
            phaseNumber = MOON_PHASES.length - 1;
        }
        // return the moon phase name
        return MOON_PHASES[phaseNumber];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoonData moonData = (MoonData) o;
        if (Double.compare(moonData.illumination, illumination) != 0) return false;
        return Double.compare(moonData.daysSinceNewMoon, daysSinceNewMoon) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(illumination);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(daysSinceNewMoon);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "MoonData{" +
                "illumination=" + illumination +
                ", daysSinceNewMoon=" + daysSinceNewMoon +
                '}';
    }
}
