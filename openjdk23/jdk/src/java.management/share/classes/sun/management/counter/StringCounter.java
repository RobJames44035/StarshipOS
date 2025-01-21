/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package sun.management.counter;

/**
 * Interface for a performance counter wrapping a <code>String</code> object.
 *
 * @author   Brian Doherty
 */
public interface StringCounter extends Counter {

    /**
     * Get a copy of the value of the StringCounter.
     */
    public String stringValue();
}
