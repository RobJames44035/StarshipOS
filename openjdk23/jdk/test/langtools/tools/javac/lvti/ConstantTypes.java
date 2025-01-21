/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8293578
 * @summary Ensure constant types are removed correctly for <string>.getClass().
 * @compile ConstantTypes.java
 * @run main ConstantTypes
 */

import java.util.Objects;

public class ConstantTypes {
    public static void main(String... args) throws Throwable {
        new ConstantTypes().testStringCreation1();
        new ConstantTypes().testStringCreation2();
        new ConstantTypes().testStringCreation3();
        new ConstantTypes().testStringCreation4();
        new ConstantTypes().testStringFolding();
    }

    private void testStringCreation1() throws Throwable {
        var testC = "incorrect".getClass();
        var testV = testC.getConstructor(String.class)
                         .newInstance("correct");
        String actual = testV;
        String expected = "correct";
        if (!Objects.equals(actual, expected)) {
            throw new AssertionError("Unexpected result: " + actual);
        }
    }

    private void testStringCreation2() throws Throwable {
        var test = "incorrect".getClass()
                              .getConstructor(String.class)
                              .newInstance("correct");
        String actual = test;
        String expected = "correct";
        if (!Objects.equals(actual, expected)) {
            throw new AssertionError("Unexpected result: " + actual);
        }
    }

    private void testStringCreation3() throws Throwable {
        final var testC = "incorrect";
        var testV = testC.getClass()
                         .getConstructor(String.class)
                         .newInstance("correct");
        String actual = testV;
        String expected = "correct";
        if (!Objects.equals(actual, expected)) {
            throw new AssertionError("Unexpected result: " + actual);
        }
    }

    private void testStringCreation4() throws Throwable {
        var testC = "incorrect";
        var testV = testC.getClass()
                         .getConstructor(String.class)
                         .newInstance("correct");
        String actual = testV;
        String expected = "correct";
        if (!Objects.equals(actual, expected)) {
            throw new AssertionError("Unexpected result: " + actual);
        }
    }

    private void testStringFolding() {
        final var v1 = "1";
        final var v2 = "2";
        String actual = v1 + v2;
        String expected = "12";
        if (actual != expected) { //intentional reference comparison
            throw new AssertionError("Value not interned!");
        }
    }

}