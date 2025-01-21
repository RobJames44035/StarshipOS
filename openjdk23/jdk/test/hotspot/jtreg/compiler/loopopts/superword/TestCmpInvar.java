/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/**
 * @test
 * @bug 8309268
 * @summary Test loop invariant input to Cmp.
 *
 * @run main/othervm -Xcomp -XX:-TieredCompilation
 *      -XX:CompileCommand=compileonly,compiler.loopopts.superword.TestCmpInvar::test*
 *      compiler.loopopts.superword.TestCmpInvar
 */
package compiler.loopopts.superword;

public class TestCmpInvar {
    static int N = 400;
    static long myInvar;

    static void test1(int limit, float fcon) {
        boolean a[] = new boolean[1000];
        for (int i = 0; i < limit; i++) {
            a[i] = fcon > i;
        }
    }

    static void test2(int limit, float fcon) {
        boolean a[] = new boolean[1000];
        for (int i = 0; i < limit; i++) {
            a[i] = i > fcon;
        }
    }

    static int test3() {
        int[] a = new int[N];
        int acc = 0;
        for (int i = 1; i < 63; i++) {
            acc += Math.min(myInvar, a[i]--);
        }
        return acc;
    }

    static int test4() {
        int[] a = new int[N];
        int acc = 0;
        for (int i = 1; i < 63; i++) {
            acc += Math.min(a[i]--, myInvar);
        }
        return acc;
    }

    public static void main(String[] strArr) {
        for (int i = 0; i < 10_100; i++) {
            test1(500, 80.1f);
        }

        for (int i = 0; i < 10_100; i++) {
            test2(500, 80.1f);
        }

        for (int i = 0; i < 10_000; i++) {
            test3();
        }

        for (int i = 0; i < 10_000; i++) {
            test4();
        }
    }
}
