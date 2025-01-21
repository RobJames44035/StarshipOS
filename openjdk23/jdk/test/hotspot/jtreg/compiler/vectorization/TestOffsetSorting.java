/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/**
 * @test
 * @bug 8334228
 * @summary Test sorting of VPointer by offset, when subtraction of two offsets can overflow.
 * @run main/othervm -XX:CompileCommand=compileonly,compiler.vectorization.TestOffsetSorting::test -Xcomp compiler.vectorization.TestOffsetSorting
 * @run main compiler.vectorization.TestOffsetSorting
 */

package compiler.vectorization;

public class TestOffsetSorting {
    static int RANGE = 10_000;

    public static void main(String[] args) {
        int[] a = new int[RANGE];
        for (int i = 0; i < 10_000; i++) {
            try {
                test(a, 0);
                throw new RuntimeException("test should go out-of-bounds");
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
    }

    static void test(int[] a, int invar) {
        int large = (1 << 28) + (1 << 20);
        for (int i = 0; i < 1_000; i++) {
            a[i + invar - large] = 42;
            a[i + invar + large] = 42;
        }
    }
}
