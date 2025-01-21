/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8160527
 * @summary The VM does not always perform checks added by 8157181 when updating final instance fields
 * @library /test/lib
 * @compile TestPutField.jasm
 * @compile TestPutStatic.jasm
 * @compile TestPutMain.java
 * @run main/othervm TestPutMain
 */

import jdk.test.lib.Asserts;

public class TestPutMain {
    public static void main(String[] args) {
        boolean exception = false;
        try {
            TestPutField.test();
        } catch (java.lang.IllegalAccessError e) {
            exception = true;
        }

        Asserts.assertTrue(exception, "FAILED: Expected IllegalAccessError for illegal update to final instance field was not thrown.");

        exception = false;
        try {
            TestPutStatic.test();
        } catch (java.lang.IllegalAccessError e) {
            exception = true;
        }

        Asserts.assertTrue(exception, "FAILED: Expected IllegalAccessError for illegal update to final static field was not thrown.");

        System.out.println("PASSED: Expected IllegalAccessError was thrown.");
    }
}
