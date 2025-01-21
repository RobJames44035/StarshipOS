/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/**
 * @test
 * @bug 8268362
 * @requires vm.compiler2.enabled & vm.debug
 * @summary C2 using negative array length as index, using array allocation length.
 *          This assertion is triggered by 8267904.
 * @run main/othervm compiler.arraycopy.TestNegArrayLengthAsIndex2
 */

package compiler.arraycopy;
public class TestNegArrayLengthAsIndex2 {

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10000; i++) {
            foo();
        }
    }

    static int foo() {
        int minusOne = -1;
        int[] a = null;
        try {
            a = new int[minusOne];
        } catch (NegativeArraySizeException e) {
           return 0;
        }
        return a[minusOne - 1];
    }
}
