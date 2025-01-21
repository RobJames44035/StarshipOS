/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.awt.color.CMMException;
import java.util.Objects;

/**
 * @test
 * @bug 6211126
 * @summary Checks basic functionality of java.awt.color.CMMException
 */
public final class CMMExceptionMessage {

    public static void main(String[] args) {
        test(null);
        test("");
        test("CMMExceptionMessage");
    }

    private static void test(String expected) {
        CMMException e = new CMMException(expected);
        if (!Objects.equals(e.getMessage(), expected)) {
            System.err.println("Expected message: " + expected);
            System.err.println("Actual message: " + e.getMessage());
            throw new RuntimeException();
        }
    }
}
