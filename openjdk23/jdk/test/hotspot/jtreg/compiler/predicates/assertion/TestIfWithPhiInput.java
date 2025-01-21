/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8333394
 * @summary Test bailout of range check policy with an If with a Phi as condition.
 * @run main/othervm -XX:CompileCommand=compileonly,*TestIfWithPhiInput*::* -Xcomp -XX:-TieredCompilation
 *                   compiler.predicates.assertion.TestIfWithPhiInput
 */

package compiler.predicates.assertion;

public class TestIfWithPhiInput {
    static int x;
    static int y;

    public static void main(String[] strArr) {
        test();
    }

    static int test() {
        int i = 1;
        do {
            try {
                y = y / y;
            } catch (ArithmeticException a_e) {
            }
            for (int j = i; j < 6; j++) {
                y = i;
            }
        } while (++i < 52);
        return x;
    }
}
