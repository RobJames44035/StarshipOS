/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package pkg;

/**
 * @deprecated error_test1 passes.
 */
@Deprecated(forRemoval=true)
public class TestError extends Error {

    /**
     * @deprecated error_test2 passes.
     */
    public int field;

    /**
     * @deprecated error_test3 passes.
     */
    public TestError() {}

    /**
     * @deprecated error_test4 passes.
     */
    public void method() {}
}
