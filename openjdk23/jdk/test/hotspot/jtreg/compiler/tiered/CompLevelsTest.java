/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/**
 * Abstract class for testing of used compilation levels correctness.
 *
 * @author igor.ignatyev@oracle.com
 */

package compiler.tiered;

import compiler.whitebox.CompilerWhiteBoxTest;

public abstract class CompLevelsTest extends CompilerWhiteBoxTest {
    protected CompLevelsTest(TestCase testCase) {
        super(testCase);
        // to prevent inlining of #method
        WHITE_BOX.testSetDontInlineMethod(method, true);
    }

    /**
     * Checks that level is available.
     * @param compLevel level to check
     */
    protected void testAvailableLevel(int compLevel, int bci) {
        if (IS_VERBOSE) {
            System.out.printf("testAvailableLevel(level = %d, bci = %d)%n",
                    compLevel, bci);
        }
        WHITE_BOX.enqueueMethodForCompilation(method, compLevel, bci);
        checkCompiled();
        checkLevel(compLevel, getCompLevel());
        deoptimize();
    }

    /**
     * Checks that level is unavailable.
     * @param compLevel level to check
     */
    protected void testUnavailableLevel(int compLevel, int bci) {
        if (IS_VERBOSE) {
            System.out.printf("testUnavailableLevel(level = %d, bci = %d)%n",
                    compLevel, bci);
        }
        WHITE_BOX.enqueueMethodForCompilation(method, compLevel, bci);
        checkNotCompiled();
    }

    /**
     * Checks validity of compilation level.
     * @param expected expected level
     * @param actual actually level
     */
    protected void checkLevel(int expected, int actual) {
        if (expected != actual) {
            throw new RuntimeException("expected[" + expected + "] != actual["
                    + actual + "]");
        }
    }
}
