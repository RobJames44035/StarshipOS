/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.share;

import java.io.*;

/**
 * Thrown to indicate failure caused by some occasional reason,
 * which does not indicate a problem in the JVM being tested.
 */
public class Failure extends RuntimeException {
        /** Enwrap another throwable. */
        public Failure(Throwable throwable) {
                super(throwable);
        }

        /** Explain particular failure. */
        public Failure(String message) {
                super(message);
        }

        public Failure(String message, Throwable cause) {
                super(message, cause);
        }
}
