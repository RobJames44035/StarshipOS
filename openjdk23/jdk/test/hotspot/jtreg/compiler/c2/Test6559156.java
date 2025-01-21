/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/**
 * @test
 * @bug 6559156
 * @summary Server compiler generates bad code for "<= Integer.MAX_VALUE" expression
 *
 * @run main compiler.c2.Test6559156
 */

package compiler.c2;

public class Test6559156 {

    static final int N_TESTS = 1000000;

    public static void main(String[] args) throws Exception {

        /*
         * If MAX_VALUE is changed to MAX_VALUE - 1 below, the test passes
         * because (apparently) bad code is only generated when comparing
         * <= MAX_VALUE in the doTest method.
         */
        Test6559156 test = new Test6559156();
        for (int i = 0; i < N_TESTS; i += 1) {
            test.doTest1(10, Integer.MAX_VALUE, i);
            test.doTest2(10, Integer.MAX_VALUE, i);
        }
        System.out.println("No failure");
    }

    void doTest1(int expected, int max, int i) {
        int counted;
        for (counted = 0;
             (counted <= max) && (counted < expected);
             counted += 1) {
        }
        if (counted != expected) {
            throw new RuntimeException("Failed test1 iteration=" + i +
                                       " max=" + max +
                                       " counted=" + counted +
                                       " expected=" + expected);
        }
    }

    void doTest2(int expected, int max, int i) {
        int counted;
        for (counted = 0;
             // change test sequence.
             (counted < expected) && (counted <= max);
             counted += 1) {
        }
        if (counted != expected) {
            throw new RuntimeException("Failed test1 iteration=" + i +
                                       " max=" + max +
                                       " counted=" + counted +
                                       " expected=" + expected);
        }
    }
}

