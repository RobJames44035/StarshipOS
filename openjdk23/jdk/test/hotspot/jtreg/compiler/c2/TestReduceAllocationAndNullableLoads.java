/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8330565
 * @summary Check that Reduce Allocation Merges does not crash when legacy
 *          string concatenation optimization is applied.
 * @requires vm.flagless & vm.compiler2.enabled & vm.opt.final.EliminateAllocations
 * @compile -XDstringConcat=inline TestReduceAllocationAndNullableLoads.java
 * @run main/othervm -XX:CompileCommand=compileonly,*TestReduceAllocationAndNullableLoads*::*
 *                   -XX:CompileCommand=dontinline,*TestReduceAllocationAndNullableLoads*::*
 *                   -XX:-TieredCompilation -Xcomp -server
 *                   compiler.c2.TestReduceAllocationAndNullableLoads
 */

package compiler.c2;

public class TestReduceAllocationAndNullableLoads {
    public static void main(String[] args) {
        try {
            // Load / initialize these classes
            IllegalArgumentException e = new IllegalArgumentException("Reason is: ");
            StringBuilder xixi = new StringBuilder("abc");

            // The actual test
            test(new char[] { 'a', 'b', 'c' });
        } catch (IllegalArgumentException e) { }
    }

    public static void test(char[] chars) throws IllegalArgumentException {
        String reason = null;

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            if (c == 'a') {
                reason = "first entry" + i;
                break;
            }
        }

        if (reason != null) {
            throw new IllegalArgumentException("Reason is: " + reason);
        }
    }
}

