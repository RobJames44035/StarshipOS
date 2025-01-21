/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/**
 * @test
 * @bug 8254998
 * @summary C2: assert(!n->as_Loop()->is_transformed_long_loop()) failure with -XX:StressLongCountedLoop=1
 * @requires vm.compiler2.enabled & vm.gc.Parallel
 *
 * @run main/othervm -Xcomp -XX:-TieredCompilation -XX:MaxRecursiveInlineLevel=26 -XX:MaxInlineLevel=26 -XX:LoopOptsCount=41 -XX:+UseParallelGC TestTooManyLoopOpts
 *
 */

public class TestTooManyLoopOpts {
    private static volatile int field;

    public static void main(String[] args) {
        test(0);
    }

    private static void test(int stop) {
        for (long l = 0; l < 10; l++) {
            test_helper(stop, 26);
            field = 0x42;
        }
    }
    private static void test_helper(int stop, int rec) {
        if (rec <= 0) {
            return;
        }
        for (int i = 0; i < stop; i++) {
            test_helper(stop, rec - 1);
        }
    }
}
