/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package pkg1;

public class C {

    /**
     * @propertyDescription PropertyDescription
     */
    public void CC() {}

    /**
     *
     */
    public void B() {}

    /**
     * Method A documentation
     * @treatAsPrivate
     */
    public void A() {}

    /**
     * Field i
     * @defaultValue 1.0
     */
    public int i;


    /**
     * Defines the direction/speed at which the {@code Timeline} is expected to
     * be played. This is the second line.
     * @defaultValue 11
     * @since JavaFX 8.0
     */
    private DoubleProperty rate;

    public final void setRate(double value) {}

    public final double getRate() { return 1.0d; }

    public final DoubleProperty rateProperty() { return null; }

    private BooleanProperty paused;

    public final void setPaused(boolean value) {}

    public final double isPaused() { return 3.14d; }

    /**
     * Defines if paused. The second line.
     * @defaultValue false
     * @return foo
     */
    public final BooleanProperty pausedProperty() { return null; }

    class DoubleProperty {}

    class BooleanProperty {}

    public final BooleanProperty setTestMethodProperty() { return null; }

    private class Inner {
        private BooleanProperty testMethodProperty() {}

        /**
         * Defines the direction/speed at which the {@code Timeline} is expected to
         * be played. This is the second line.
         * @defaultValue 11
         */
        private DoubleProperty rate;

        public final void setRate(double value) {}

        public final double getRate() { return 3.14d; }

        public final DoubleProperty rateProperty() { return null; }
    }
}
