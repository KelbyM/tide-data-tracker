public class MoonData {
    // INSTANCE VARIABLES
    /** Moon phase names. */
    public static final String[] MOON_PHASES = {"New Moon", "Waxing Crescent", "First Quarter",
                                                "Waxing Gibbous", "Full Moon", "Waning Gibbous",
                                                "Last Quarter", "Waning Crescent"};

    /** The illumination percentage. */
    private double illumination;

    // CONSTRUCTORS
    /**
     * Default Constructor
     */
    public MoonData() {
        this.illumination = 0;
    }

    /**
     * Full Constructor
     * @param illumination The illumination percentage.
     */
    public MoonData(double illumination) {
        this.illumination = illumination;
    }


    // ASSESSORS/MUTATORS
    public double getIllumination() {
        return illumination;
    }

    public void setIllumination(double illumination) {
        this.illumination = illumination;
    }
}
