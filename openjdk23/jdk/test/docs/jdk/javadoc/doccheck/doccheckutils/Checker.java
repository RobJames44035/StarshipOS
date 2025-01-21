/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package doccheckutils;

import java.io.Closeable;

/**
 * Base class for {@link FileChecker file checkers} and
 */
public interface Checker extends Closeable {

    /**
     * Writes a report at the end of a run, to summarize the results of the
     * checking done by this checker.
     */
    void report();

    boolean isOK();
}
