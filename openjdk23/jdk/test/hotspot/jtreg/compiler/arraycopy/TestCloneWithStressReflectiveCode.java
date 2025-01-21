/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8284951
 * @summary Test clone intrinsic with StressReflectiveCode.
 * @requires vm.debug
 * @run main/othervm -XX:-InlineUnsafeOps -XX:-ReduceInitialCardMarks -XX:+StressReflectiveCode -Xbatch -XX:-TieredCompilation
 *                   -XX:CompileCommand=dontinline,compiler.arraycopy.TestCloneWithStressReflectiveCode::test
 *                   compiler.arraycopy.TestCloneWithStressReflectiveCode
 * @run main/othervm -XX:-InlineUnsafeOps -XX:-ReduceInitialCardMarks -XX:+StressReflectiveCode -Xcomp -XX:-TieredCompilation
 *                   compiler.arraycopy.TestCloneWithStressReflectiveCode
 */

package compiler.arraycopy;

public class TestCloneWithStressReflectiveCode implements Cloneable {

    public Object test() throws CloneNotSupportedException {
        return clone();
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        TestCloneWithStressReflectiveCode t = new TestCloneWithStressReflectiveCode();
        for (int i = 0; i < 50_000; ++i) {
            t.test();
        }
    }
}
