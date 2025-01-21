/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.share;

/**
 * Thrown when it becomes obvious that the test algorithm
 * works incorrectly (for example - tries to write to debugee's
 * stdin after it is already redirected, or something of the
 * kind).
 */
public class TestBug extends Failure {
        /** Explain particular failure. */
        public TestBug(String message) {
                super(message);
        }

        /** Enwrap another throwable. */
        public TestBug(Throwable throwable) {
                super(throwable);
        }

        public TestBug(String message, Throwable throwable) {
                super(message, throwable);
        }
}
