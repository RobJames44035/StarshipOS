/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/**
 * @test
 * @bug 8301630
 * @summary C2: 8297933 broke type speculation in some cases
 *
 * @run main/othervm -XX:-BackgroundCompilation -XX:-TieredCompilation -XX:-UseOnStackReplacement -XX:-BackgroundCompilation
 *                   -XX:TypeProfileLevel=222 -XX:CompileOnly=TestSpeculationBrokenWithIntArrays::testHelper
 *                   -XX:CompileOnly=TestSpeculationBrokenWithIntArrays::testHelper2
 *                   -XX:CompileOnly=TestSpeculationBrokenWithIntArrays::test TestSpeculationBrokenWithIntArrays
 *
 */

public class TestSpeculationBrokenWithIntArrays {
    static int[] int_array = new int[10];
    static short[] short_array = new short[10];
    static byte[] byte_array = new byte[10];

    public static void main(String[] args) {
        for (int i = 0; i < 20_000; i++) {
            testHelper(int_array);
            testHelper2(short_array);
        }
        for (int i = 0; i < 20_000; i++) {
            test(int_array);
            test(short_array);
            test(byte_array);
        }
    }

    private static void test(Object o) {
        testHelper(o);
        if (o instanceof short[]) {
            testHelper2(o);
        }
    }

    private static void testHelper(Object o) {
    }

    private static void testHelper2(Object o) {
    }
}
