/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/**
 * @test
 * @bug 8342330
 * @summary C2: "node pinned on loop exit test?" assert failure
 * @requires vm.flavor == "server"
  *
 * @run main/othervm -XX:-BackgroundCompilation -XX:-UseOnStackReplacement -XX:-TieredCompilation
 *                   -XX:-UseLoopPredicate -XX:LoopMaxUnroll=0 TestSunkRangeFromPreLoopRCE
 *
 */


import java.util.Arrays;

public class TestSunkRangeFromPreLoopRCE {
    private static int[] array = new int[1000];
    private static A objectField = new A(42);

    public static void main(String[] args) {
        boolean[] allTrue = new boolean[1000];
        Arrays.fill(allTrue, true);
        boolean[] allFalse = new boolean[1000];
        for (int i = 0; i < 20_000; i++) {
            test1(array.length/4, allTrue, 1, 0);
            test1(array.length/4, allFalse, 1, 0);
        }
    }

    private static int test1(int stop, boolean[] flags, int otherScale, int x) {
        int scale;
        for (scale = 0; scale < 4; scale++) {
            for (int i = 0; i < 10; i++) {

            }
        }
        if (array == null) {
        }
        int v = 0;
        for (int i = 0; i < stop; i++) {
            v += array[i];
            v += array[scale * i];
            if (i * scale + (objectField.intField + 1) == x) {
            }
            v += (scale - 4) * (x-objectField.intField);
            if (flags[i]) {
                return (x-objectField.intField);
            }
        }
        return v;
    }

    private static class A {
        A(int field) {
            intField = field;
        }
        public int intField;
    }
}
