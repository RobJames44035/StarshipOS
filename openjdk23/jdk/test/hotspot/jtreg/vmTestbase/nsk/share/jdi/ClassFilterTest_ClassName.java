/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
package nsk.share.jdi;

import nsk.share.Consts;
import nsk.share.TestBug;

import java.io.PrintStream;

/*
 * Subclass of ClassExclusionFilterTest, create ClassFilter instead of
 * ClassExclusionFilter, this filter is added by request's method
 * addClassFilter(String classPattern)
 */
public class ClassFilterTest_ClassName extends ClassExclusionFilterTest {
    public static void main(String[] argv) {
        int result = run(argv,System.out);
        if (result != 0) {
            throw new RuntimeException("TEST FAILED with result " + result);
        }
    }

    public static int run(String[] argv, PrintStream out) {
        return new ClassFilterTest_ClassName().runIt(argv, out);
    }

    protected EventFilters.DebugEventFilter[] createTestFilters(int testedFilterIndex) {
        if (testedFilterIndex < 0 || testedFilterIndex >= classPatterns.length) {
            throw new TestBug("Invalid testedFilterIndex: " + testedFilterIndex);
        }

        return new EventFilters.DebugEventFilter[]{new EventFilters.ClassFilter(classPatterns[testedFilterIndex])};
    }
}
