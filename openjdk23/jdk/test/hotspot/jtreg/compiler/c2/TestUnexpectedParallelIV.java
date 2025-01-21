/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
 * @test
 * @bug 8290432
 * @summary Unexpected parallel induction variable pattern was recongized
 *
 * @run main/othervm -XX:-TieredCompilation -Xcomp
 *           -XX:CompileCommand=compileonly,compiler.c2.TestUnexpectedParallelIV::test
 *           -XX:CompileCommand=compileonly,compiler.c2.TestUnexpectedParallelIV::test2
 *           -XX:CompileCommand=quiet
 *           -XX:CompileCommand=dontinline,compiler.c2.TestUnexpectedParallelIV::* compiler.c2.TestUnexpectedParallelIV
 */

package compiler.c2;

public class TestUnexpectedParallelIV {

    static boolean bFld;

    static int dontInline() {
        return 0;
    }

    static int test2(int i1) {
        int i2, i3 = 0, i4, i5 = 0, i6;
        for (i2 = 0; 4 > i2; ++i2) {
            for (i4 = 1; i4 < 5; ++i4) {
                i3 -= --i1;
                i6 = 1;
                while (++i6 < 2) {
                    dontInline();
                    if (bFld) {
                        i1 = 5;
                    }
                }
                if (bFld) {
                    break;
                }
            }
        }
        return i3;
    }

    static long test(int val, boolean b) {
        long ret = 0;
        long dArr[] = new long[100];
        for (int i = 15; 293 > i; ++i) {
            ret = val;
            int j = 1;
            while (++j < 6) {
                int k = (val--);
                for (long l = i; 1 > l; ) {
                    if (k != 0) {
                        ret += dontInline();
                    }
                }
                if (b) {
                    break;
                }
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            test(0, false);
        }

        test2(5);
    }
}
