/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8324121
 * @summary SIGFPE in PhaseIdealLoop::extract_long_range_checks
 * @run main/othervm -Xcomp -XX:CompileCommand=compileonly,TestLargeScaleInLongRCOverflow::test* -XX:-TieredCompilation TestLargeScaleInLongRCOverflow
 *
 */

import java.util.Objects;

public class TestLargeScaleInLongRCOverflow {

    public static void main(String args[]) {
        Objects.checkIndex(0, 1);
        try {
            test1();
        } catch (java.lang.IndexOutOfBoundsException e) { }
        try {
            test2();
        } catch (java.lang.IndexOutOfBoundsException e) { }
    }

    // SIGFPE in PhaseIdealLoop::extract_long_range_checks
    public static void test1() {
        for (long i = 1; i < 100; i += 2) {
            Objects.checkIndex(Long.MIN_VALUE * i, 10);
        }
    }

    // "assert(static_cast<T1>(result) == thing) failed: must be" in PhaseIdealLoop::transform_long_range_checks
    public static void test2() {
        for (long i = 1; i < 100; i += 2) {
            Objects.checkIndex((Long.MIN_VALUE + 2) * i, 10);
        }
    }
}
