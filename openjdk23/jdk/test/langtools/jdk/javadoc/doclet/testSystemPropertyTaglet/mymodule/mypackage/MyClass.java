/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package mypackage;

/**
 * (class) the {@systemProperty test.property} system property.
 */
public class MyClass {

    /**
     * (class/field) the {@systemProperty test.property} system property.
     */
    public int intField;

    /**
     * (class/static-field) the {@systemProperty test.property} system property.
     */
    public final static int INT_CONSTANT = 42;

    /**
     * (class/static-method) the {@systemProperty test.property} system property.
     *
     * @return an integer, 42
     */
    public static Object value() { return INT_CONSTANT; }

    /**
     * (class/constructor) the {@systemProperty test.property} system property.
     */
    public MyClass() { }

    /**
     * (class/method) the {@systemProperty test.property} system property.
     */
    public void run() { }
}