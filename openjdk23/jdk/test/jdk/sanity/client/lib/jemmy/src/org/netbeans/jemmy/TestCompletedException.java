/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy;

/**
 *
 * Exception is throught as a result of test. either test failed or passed.
 *
 * @author Alexandre Iline (alexandre.iline@oracle.com)
 */
public class TestCompletedException extends JemmyException {

    private static final long serialVersionUID = 42L;

    private int status;

    /**
     * Constructor.
     *
     * @param st Exit status.
     * @param ex Exception provoked test failure.
     */
    public TestCompletedException(int st, Exception ex) {
        super("Test "
                + ((st == 0)
                        ? "passed"
                        : "failed with status " + Integer.toString(st)),
                ex);
        status = st;
    }

    /**
     * Constructor.
     *
     * @param st Exit status.
     * @param description Failure reason
     */
    public TestCompletedException(int st, String description) {
        super("Test "
                + ((st == 0)
                        ? "passed"
                        : "failed with status " + Integer.toString(st)
                        + "\n" + description));
        status = st;
    }

    /**
     * Returns status.
     *
     * @return test status
     */
    public int getStatus() {
        return status;
    }
}
