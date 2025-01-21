/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package compiler.lib.ir_framework.shared;

/**
 * Exception that is thrown if a JTreg test violates the supported format by the test framework.
 */
public class TestFormatException extends RuntimeException {
    public TestFormatException(String message) {
        super(message);
    }
}
