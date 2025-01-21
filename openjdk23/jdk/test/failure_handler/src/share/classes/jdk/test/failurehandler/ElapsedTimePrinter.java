/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.failurehandler;

import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class ElapsedTimePrinter implements AutoCloseable {
    private final String name;
    private final PrintWriter out;
    private final Stopwatch stopwatch;

    public ElapsedTimePrinter(Stopwatch stopwatch, String name,
                              PrintWriter out) {
        this.stopwatch = stopwatch;
        this.name = name;
        this.out = out;
        stopwatch.start();
    }

    @Override
    public void close()  {
        stopwatch.stop();
        out.printf("%s took %d s%n", name,
                TimeUnit.NANOSECONDS.toSeconds(stopwatch.getElapsedTimeNs()));
    }
}
