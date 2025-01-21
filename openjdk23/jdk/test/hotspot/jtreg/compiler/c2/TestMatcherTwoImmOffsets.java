/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/**
 * @test
 * @bug 8339303
 * @summary Test that the matcher does not create dead nodes when matching
 *          address expressions with two immediate offsets.
 * @requires os.maxMemory > 4G
 *
 * @run main/othervm -Xmx4g -Xbatch -XX:-TieredCompilation
 *      -XX:CompileOnly=compiler.c2.TestMatcherTwoImmOffsets::test
 *      compiler.c2.TestMatcherTwoImmOffsets
 */

package compiler.c2;

public class TestMatcherTwoImmOffsets {
    static final int[] a1 = new int[10];
    int[] a2;
    static TestMatcherTwoImmOffsets o = new TestMatcherTwoImmOffsets();

    public static void test() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 100; j++) {
                int[][] nArray = new int[10][];
                for (int k = 0; k < nArray.length; k++) {}
            }
            for (long j = 1L; j < 3L; j++) {
                a1[(int) j]--;
            }
            o.a2 = a1;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            test();
        }
    }
}
