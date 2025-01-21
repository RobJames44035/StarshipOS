/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/**
 * @test
 * @bug 8332827
 * @summary [REDO] C2: crash in compiled code because of dependency on removed range check CastIIs
 *
 * @run main/othervm -XX:-TieredCompilation -XX:-UseOnStackReplacement -XX:-BackgroundCompilation TestRangeCheckCastIISplitThruPhi
 * @run main TestRangeCheckCastIISplitThruPhi
 *
 */

import java.util.Arrays;

public class TestRangeCheckCastIISplitThruPhi {
    private static volatile int volatileField;

    public static void main(String[] args) {
        int[] array = new int[100];
        int[] baseline = null;
        for (int i = 0; i < 20_000; i++) {
            Arrays.fill(array, 0);
            test1(array);
            if (baseline == null) {
                baseline = array.clone();
            } else {
                boolean failures = false;
                for (int j = 0; j < array.length; j++) {
                    if (array[j] != baseline[j]) {
                        System.out.println("XXX @" + j + " " + array[j] + " != " + baseline[j]);
                       failures = true;
                    }
                }
                if (failures) {
                    throw new RuntimeException();
                }
            }
            test2(array, true);
            test2(array, false);
        }
    }

    private static void test1(int[] array) {
        int[] array2 = new int[100];
        int j = 4;
        int i = 3;
        int k;
        for (k = 1; k < 2; k *= 2) {

        }
        int stride = k / 2;
        do {
            synchronized (new Object()) {
            }
            array2[j-1] = 42;
            array[j+1] = 42;
            j = i;
            i -= stride;
        } while (i >= 0);
    }

    private static void test2(int[] array, boolean flag) {
        int[] array2 = new int[100];
        int j = 4;
        int i = 3;
        int k;
        for (k = 1; k < 2; k *= 2) {

        }
        int stride = k / 2;
        if (flag) {
            volatileField = 42;
            array[0] = 42;
        } else {
            do {
                synchronized (new Object()) {
                }
                array2[j - 1] = 42;
                array[j + 1] = 42;
                j = i;
                i -= stride;
            } while (i >= 0);
        }
    }
}
