/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
 * @test
 * @key stress randomness
 * @bug 8288204
 * @summary GVN Crash: assert() failed: correct memory chain
 *
 * @run main/othervm -Xbatch -XX:+UnlockDiagnosticVMOptions -XX:+StressIGVN -XX:CompileCommand=compileonly,compiler.c2.TestGVNCrash::test compiler.c2.TestGVNCrash
 */

package compiler.c2;

public class TestGVNCrash {
    public static int iField = 0;
    public static double[] dArrFld = new double[256];
    public static int[] iArrFld = new int[256];
    public int[][] iArrFld1 = new int[256][256];

    public void test() {
        int x = 0;
        for (int i = 0; i < 10; i++) {
            do {
                for (float j = 0; j < 0; j++) {
                    iArrFld[x] = 3;
                    iArrFld1[1][x] -= iField;
                    dArrFld = new double[256];
                    for (int k = 0; k < dArrFld.length; k++) {
                        dArrFld[k] = (k % 2 == 0) ? k + 1 : k - 1;
                    }
                }
            } while (++x < 5);
            for (int j = 0; j < 100_000; j++) {
                String s = "test";
                s = s + s;
                s = s + s;
            }
        }
    }

    public static void main(String[] args) {
        TestGVNCrash t = new TestGVNCrash();
        t.test();
    }
}