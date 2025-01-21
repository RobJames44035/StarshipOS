/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8080156 8060036
 * @summary Test correctness of type propagation to CmpUNodes.
 *
 * @run main compiler.types.TestTypePropagationToCmpU
 */

package compiler.types;

public class TestTypePropagationToCmpU {
    public static void main(String[] args) {
        try {
            // Trigger compilation
            for (int i = 0; i < 100_000; ++i) {
                test();
            }
        } catch (NullPointerException e) {
            // Test should never throw a NullPointerException
            throw new RuntimeException("Test failed");
        }
    }

    static int global = 42;

    public static void test() {
        int a = Integer.MIN_VALUE;
        int b = global;
        char[] buf = { 0 };
        for (int i = 0; i <= b; ++i) {
            a = i - b;
        }
        // C2 adds a range check and an uncommon trap here to ensure that the array index
        // is in bounds. If type information is not propagated correctly to the corresponding
        // CmpUNode, this trap may be always taken. Because C2 also removes the unnecessary
        // allocation of 'buf', a NullPointerException is thrown in this case.
        char c = buf[(a * 11) / 2 - a]; // a is 0 here if global >= 0
        buf[0] = 0;
    }
}
