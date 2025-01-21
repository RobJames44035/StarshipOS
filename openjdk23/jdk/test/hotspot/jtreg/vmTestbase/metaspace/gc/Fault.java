/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package metaspace.gc;

/**
 * Exception signaling a test failure.
 */
public class Fault extends RuntimeException {

    public Fault(String message) {
        super(message);
    }

    public Fault(Throwable t) {
        super(t);
    }

}
