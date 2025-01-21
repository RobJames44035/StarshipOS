/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package compiler.lib.ir_framework.shared;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to report a {@link TestFormatException}.
 */
public class TestFormat {
    private static final List<String> FAILURES = new ArrayList<>();

    public static void checkAndReport(boolean test, String failureMessage) {
        if (!test) {
            FAILURES.add(failureMessage);
            throwIfAnyFailures();
        }
    }

    public static void check(boolean test, String failureMessage) {
        if (!test) {
            fail(failureMessage);
        }
    }

    public static void checkNoThrow(boolean test, String failureMessage) {
        if (!test) {
            failNoThrow(failureMessage);
        }
    }

    public static void checkNoReport(boolean test, String failureMessage) {
        if (!test) {
            throw new TestFormatException(failureMessage);
        }
    }

    public static void fail(String failureMessage) {
        FAILURES.add(failureMessage);
        throw new TestFormatException(failureMessage);
    }

    public static void failNoThrow(String failureMessage) {
        FAILURES.add(failureMessage);
    }

    public static void throwIfAnyFailures() {
        if (FAILURES.isEmpty()) {
            // No format violation detected.
            return;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(System.lineSeparator()).append("One or more format violations have been detected:")
               .append(System.lineSeparator()).append(System.lineSeparator());
        builder.append("Violations (").append(FAILURES.size()).append(")").append(System.lineSeparator());
        builder.append("-------------").append("-".repeat(String.valueOf(FAILURES.size()).length()))
               .append(System.lineSeparator());
        for (String failure : FAILURES) {
            builder.append(" - ").append(failure).append(System.lineSeparator());
        }
        builder.append("/============/");
        FAILURES.clear();
        throw new TestFormatException(builder.toString());
    }
}
