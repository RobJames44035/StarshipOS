/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8300079
 * @summary Verify that String.copyValueOf properly handles null input with intrinsified helper methods.
 * @run main/othervm -XX:-TieredCompilation -Xcomp
 *                   -XX:CompileCommand=compileonly,compiler.intrinsics.string.TestCopyValueOf::test
 *                   -XX:CompileCommand=dontinline,java.lang.String::rangeCheck
 *                   compiler.intrinsics.string.TestCopyValueOf
 */

package compiler.intrinsics.string;

public class TestCopyValueOf {

    public static boolean test() {
        try {
            String.copyValueOf(null, 42, 43);
        } catch (NullPointerException e) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        // Warmup
        char data[] = {42};
        String.copyValueOf(data, 0, 1);

        if (!test()) {
            throw new RuntimeException("Unexpected result");
        }
    }
}
