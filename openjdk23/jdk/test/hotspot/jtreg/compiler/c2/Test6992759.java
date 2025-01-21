/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/**
 * @test
 * @bug 6992759
 * @summary Bad code generated for integer <= comparison, fails for Integer.MAX_VALUE
 *
 * @run main/timeout=240 compiler.c2.Test6992759
 */

package compiler.c2;

public class Test6992759 {

    static final int N_TESTS = 1000000000;

    public static void main(String[] args) throws Exception {

        /*
         * If MAX_VALUE is changed to MAX_VALUE - 1 below, the test passes
         * because (apparently) bad code is only generated when comparing
         * <= MAX_VALUE in the doTest method.
         */
        Test6992759 test = new Test6992759();
        for (int i = 0; i < N_TESTS; i += 1) {
            test.doTest(10, Integer.MAX_VALUE, i);
            //test.doTest(10, Integer.MAX_VALUE - 1, i);
        }
        System.out.println("No failure");
    }

    void doTest(int expected, int max, int i) {
        int counted;
        for (counted = 0;
             (counted <= max) && (counted < expected);
             counted += 1) {
        }
        if (counted != expected) {
            throw new RuntimeException("Failed test iteration=" + i +
                                       " max=" + max +
                                       " counted=" + counted +
                                       " expected=" + expected);
        }
    }
}

