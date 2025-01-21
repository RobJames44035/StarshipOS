/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8323682
 * @summary Test that the appropriate guards are generated for the copyOfRange
 *          intrinsic, even if the result of the array copy is not used.
 *
 * @run main/othervm -XX:-TieredCompilation
 *                   -XX:CompileCommand=compileonly,compiler.arraycopy.TestArrayCopyOfRangeGuards::test
 *                   -Xbatch
 *                   compiler.arraycopy.TestArrayCopyOfRangeGuards
 */

package compiler.arraycopy;

import java.util.Arrays;

public class TestArrayCopyOfRangeGuards {
    static int counter = 0;

    public static void main(String[] args) {
        Object[] array = new Object[10];
        for (int i = 0; i < 50_000; i++) {
            test(array);
        }
        if (counter != 50_000) {
            throw new RuntimeException("Test failed");
        }
    }

    static void test(Object[] array) {
        try {
            Arrays.copyOfRange(array, 15, 20, Object[].class);
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
            counter++;
        }
    }
}
