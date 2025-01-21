/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
* @test
* @bug 8287517
* @summary Test bug fix for JDK-8287517 related to fuzzer test failure in x86_64
* @requires vm.compiler2.enabled
* @run main/othervm -Xcomp -XX:CompileOnly=compiler.vectorization.TestSmallVectorPopIndex::test -XX:MaxVectorSize=8 compiler.vectorization.TestSmallVectorPopIndex
*/

package compiler.vectorization;

public class TestSmallVectorPopIndex {
    private static final int count = 1000;

    private static float[] f;

    public static void main(String args[]) {
        TestSmallVectorPopIndex t = new TestSmallVectorPopIndex();
        t.test();
    }

    public TestSmallVectorPopIndex() {
        f = new float[count];
    }

    public void test() {
        for (int i = 0; i < count; i++) {
            f[i] = i * i + 100;
        }
        checkResult();
    }

    public void checkResult() {
        for (int i = 0; i < count; i++) {
            float expected = i * i  + 100;
            if (f[i] != expected) {
                throw new RuntimeException("Invalid result: f[" + i + "] = " + f[i] + " != " + expected);
            }
        }
    }
}
