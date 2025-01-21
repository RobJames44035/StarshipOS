/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/**
 * @test
 * @bug 6443505
 * @summary Some cases for CmpLTMask missed; also wrong code.
 *
 * @run main/othervm -Xcomp
 *      -XX:CompileCommand=compileonly,compiler.c2.Test6443505::compiled
 *      compiler.c2.Test6443505
 */

package compiler.c2;

public class Test6443505 {

    public static void main(String[] args) throws InterruptedException {
        test(Integer.MIN_VALUE, 0);
        test(0, Integer.MIN_VALUE);
        test(Integer.MIN_VALUE, -1);
        test(-1, Integer.MIN_VALUE);
        test(Integer.MIN_VALUE, 1);
        test(1, Integer.MIN_VALUE);

        test(Integer.MAX_VALUE, 0);
        test(0, Integer.MAX_VALUE);
        test(Integer.MAX_VALUE, -1);
        test(-1, Integer.MAX_VALUE);
        test(Integer.MAX_VALUE, 1);
        test(1, Integer.MAX_VALUE);

        test(Integer.MIN_VALUE, Integer.MAX_VALUE);
        test(Integer.MAX_VALUE, Integer.MIN_VALUE);

        test(1, -1);
        test(1, 0);
        test(1, 1);
        test(-1, -1);
        test(-1, 0);
        test(-1, 1);
        test(0, -1);
        test(0, 0);
        test(0, 1);
    }

    public static void test(int a, int b) throws InterruptedException {
        int C = compiled(4, a, b);
        int I = interpreted(4, a, b);
        if (C != I) {
            System.err.println("#1 C = " + C + ", I = " + I);
            System.err.println("#1 C != I, FAIL");
            System.exit(97);
        }

        C = compiled(a, b, q, 4);
        I = interpreted(a, b, q, 4);
        if (C != I) {
            System.err.println("#2 C = " + C + ", I = " + I);
            System.err.println("#2 C != I, FAIL");
            System.exit(97);
        }

    }

    static int q = 4;

    // If improperly compiled, uses carry/borrow bit, which is wrong.
    // with -XX:+PrintOptoAssembly, look for cadd_cmpLTMask
    static int compiled(int p, int x, int y) {
        return (x < y) ? q + (x - y) : (x - y);
    }

    // interpreted reference
    static int interpreted(int p, int x, int y) {
        return (x < y) ? q + (x - y) : (x - y);
    }

    // Test new code with a range of cases
    // with -XX:+PrintOptoAssembly, look for and_cmpLTMask
    static int compiled(int x, int y, int q, int p) {
        return (x < y) ? p + q : q;
    }

    // interpreted reference
    static int interpreted(int x, int y, int q, int p) {
        return (x < y) ? p + q : q;
    }

}
