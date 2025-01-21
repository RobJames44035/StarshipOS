/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package compiler.lib.ir_framework.shared;

/**
 * Exception that is thrown if the JTreg test throws an exception during the execution of individual tests of the
 * test class.
 */
public class TestRunException extends RuntimeException {
    public TestRunException(String message) {
        super(message);
    }

    public TestRunException(String message, Exception e) {
        super(message, e);
    }
}
