/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @bug 8147645
 * @summary Array.fill intrinsification code doesn't mark replaced control as dead
 *
 * @run main/othervm -XX:-TieredCompilation
 *      -XX:CompileCommand=dontinline,compiler.loopopts.TestArraysFillDeadControl::dont_inline
 *      compiler.loopopts.TestArraysFillDeadControl
 */

package compiler.loopopts;

import java.util.Arrays;

public class TestArraysFillDeadControl {

    static void dont_inline() {
    }

    static int i = 1;

    public static void main(String[] args) {
        for (int j = 0; j < 200000; j++) {
            int[] a = new int[2];
            int b = i;

            Arrays.fill(a, 1);
            Arrays.fill(a, 1+b);

            dont_inline();
        }
    }
}
