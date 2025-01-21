/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

package javacserver.shared;

/**
 * Result codes.
 */
public enum Result {
    OK(0),        // Compilation completed with no errors.
    ERROR(1),     // Completed but reported errors.
    CMDERR(2);    // Bad command-line arguments

    public final int exitCode;

    Result(int exitCode) {
        this.exitCode = exitCode;
    }
}
