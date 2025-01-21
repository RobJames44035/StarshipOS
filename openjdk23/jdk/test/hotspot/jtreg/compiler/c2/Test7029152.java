/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/**
 * @test
 * @bug 7029152
 * @summary Ideal nodes for String intrinsics miss memory edge optimization
 *
 * @run main/othervm -Xbatch compiler.c2.Test7029152
 */

package compiler.c2;

public class Test7029152 {

    static final String str = "11111xx11111xx1x";
    static int idx = 0;

    static int IndexOfTest(String str) {
        return str.indexOf("11111xx1x");
    }

    public static void main(String args[]) {
        final int ITERS = 2000000;

        for (int i = 0; i < ITERS; i++) {
            idx = IndexOfTest(str);
        }
        System.out.println("IndexOf = " + idx);
    }
}
