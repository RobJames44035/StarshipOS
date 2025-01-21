/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */
package nsk.share;

public class TestFailure extends RuntimeException {
    public TestFailure() {
        super();
    }

    public TestFailure(String message) {
        super(message);
    }

    public TestFailure(String message, Throwable e) {
        super(message, e);
    }

    public TestFailure(Throwable e) {
        super(e);
    }
}
