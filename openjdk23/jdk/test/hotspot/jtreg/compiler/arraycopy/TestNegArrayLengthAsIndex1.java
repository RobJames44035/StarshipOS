/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/**
 * @test
 * @bug 8268362
 * @requires vm.compiler2.enabled & vm.debug
 * @summary C2 using negative array length as index, using a.length.
 *          AllocateArrayNode::make_ideal_length create CastIINode to not negative range.
 *          Apply transform in GraphKit::load_array_length will covert array load index type to top.
 *          This cause assert in Parse::array_addressing, it expect index type is int.
 * @run main/othervm -XX:-PrintCompilation compiler.arraycopy.TestNegArrayLengthAsIndex1
 */

package compiler.arraycopy;
public class TestNegArrayLengthAsIndex1 {

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
        return a[a.length - 1];
    }
}
