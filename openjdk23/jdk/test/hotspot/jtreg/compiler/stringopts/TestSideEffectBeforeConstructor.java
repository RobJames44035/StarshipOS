/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8290705
 * @summary Test correctness of the string concatenation optimization with
 *          a store between StringBuffer allocation and constructor invocation.
 * @compile SideEffectBeforeConstructor.jasm
 * @run main/othervm -Xbatch compiler.stringopts.TestSideEffectBeforeConstructor
 */

package compiler.stringopts;

public class TestSideEffectBeforeConstructor {

    public static void main(String[] args) {
        for (int i = 0; i < 100_000; ++i) {
            try {
                SideEffectBeforeConstructor.test(null);
            } catch (NullPointerException npe) {
                // Expected
            }
        }
        if (SideEffectBeforeConstructor.result != 100_000) {
            throw new RuntimeException("Unexpected result: " + SideEffectBeforeConstructor.result);
        }
    }
}
