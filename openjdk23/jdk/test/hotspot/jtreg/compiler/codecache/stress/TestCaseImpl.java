/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package compiler.codecache.stress;

import java.util.concurrent.Callable;

public class TestCaseImpl implements Helper.TestCase {
    private static final int RETURN_VALUE = 42;
    private static final int RECURSION_DEPTH = 10;
    private volatile int i;

    @Override
    public Callable<Integer> getCallable() {
        return () -> {
            i = 0;
            return method();
        };
    }

    @Override
    public int method() {
        ++i;
        int result = RETURN_VALUE;
        if (i < RECURSION_DEPTH) {
            return result + method();
        }
        return result;
    }

    @Override
    public int expectedValue() {
        return RETURN_VALUE * RECURSION_DEPTH;
    }
}
