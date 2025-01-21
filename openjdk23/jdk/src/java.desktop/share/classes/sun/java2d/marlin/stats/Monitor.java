/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package sun.java2d.marlin.stats;

/**
 * Generic monitor ie gathers time statistics as nanos.
 */
public final class Monitor extends StatLong {

    private static final long INVALID = -1L;

    private long start = INVALID;

    public Monitor(final String name) {
        super(name);
    }

    public void start() {
        start = System.nanoTime();
    }

    public void stop() {
        final long elapsed = System.nanoTime() - start;
        if (start != INVALID && elapsed > 0L) {
            add(elapsed);
        }
        start = INVALID;
    }
}

