/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.share;

/**
 * Oddity exception is used to simulate C/C++ style <code>assert()</code>
 * facility. It is thrown when an internal contradiction is revealed, which
 * may do not indicate a bug in the JDI implementation or in the test.
 */
public class Oddity extends Failure {
    /** Explain particular oddity. */
    public Oddity (String message) {
        super(message);
    }
}
