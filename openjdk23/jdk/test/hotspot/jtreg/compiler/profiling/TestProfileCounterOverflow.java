/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/**
 * @test
 * @bug 8224162
 * @summary Profile counter for a call site may overflow.
 * @requires vm.compMode != "Xcomp"
 * @run main/othervm -Xbatch -XX:-UseOnStackReplacement -XX:+IgnoreUnrecognizedVMOptions -XX:MaxTrivialSize=0 -XX:C1MaxTrivialSize=0 compiler.profiling.TestProfileCounterOverflow
 */

package compiler.profiling;

public class TestProfileCounterOverflow {
    public static void test(long iterations) {
        for (long j = 0; j < iterations; j++) {
            call();
        }
    }

    public static void call() {}

    public static void main(String[] args) {
        // trigger profiling on tier3
        for (int i = 0; i < 500; i++) {
            test(1);
        }

        test(Integer.MAX_VALUE + 10000L); // overflow call counter

        // trigger c2 compilation
        for (int i = 0; i < 10_000; i++) {
            test(1);
        }
        System.out.println("TEST PASSED");
    }
}
