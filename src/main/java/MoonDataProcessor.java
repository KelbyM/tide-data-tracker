public interface MoonDataProcessor {

    /**
     * Returns the moon data for the next day.
     * @return A MoonData object containing the moon data.
     */
    public MoonData getNextMoonData();

    /**
     * Checks if there is another day of moon data.
     * @return true: More data exists.
     *         false: No more data exists.
     */
    public boolean hasNextMoonData();
}
