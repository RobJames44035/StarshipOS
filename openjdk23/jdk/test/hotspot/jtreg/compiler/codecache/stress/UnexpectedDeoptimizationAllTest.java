/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test UnexpectedDeoptimizationAllTest
 * @key stress
 * @summary stressing code cache by forcing unexpected deoptimizations of all methods
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @build jdk.test.whitebox.WhiteBox compiler.codecache.stress.Helper compiler.codecache.stress.TestCaseImpl
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   -XX:+IgnoreUnrecognizedVMOptions -XX:-DeoptimizeRandom
 *                   -XX:CompileCommand=dontinline,compiler.codecache.stress.Helper$TestCase::method
 *                   -XX:-SegmentedCodeCache
 *                   compiler.codecache.stress.UnexpectedDeoptimizationAllTest
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   -XX:+IgnoreUnrecognizedVMOptions -XX:-DeoptimizeRandom
 *                   -XX:CompileCommand=dontinline,compiler.codecache.stress.Helper$TestCase::method
 *                   -XX:+SegmentedCodeCache
 *                   compiler.codecache.stress.UnexpectedDeoptimizationAllTest
  * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   -XX:+IgnoreUnrecognizedVMOptions -XX:-DeoptimizeRandom
 *                   -XX:CompileCommand=dontinline,compiler.codecache.stress.Helper$TestCase::method
 *                   -XX:-SegmentedCodeCache
 *                   -DhelperVirtualThread=true
 *                   compiler.codecache.stress.UnexpectedDeoptimizationAllTest
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   -XX:+IgnoreUnrecognizedVMOptions -XX:-DeoptimizeRandom
 *                   -XX:CompileCommand=dontinline,compiler.codecache.stress.Helper$TestCase::method
 *                   -XX:+SegmentedCodeCache
 *                   -DhelperVirtualThread=true
 *                   compiler.codecache.stress.UnexpectedDeoptimizationAllTest
 */

package compiler.codecache.stress;

public class UnexpectedDeoptimizationAllTest implements Runnable {

    public static void main(String[] args) {
        new CodeCacheStressRunner(new UnexpectedDeoptimizationAllTest()).runTest();
    }

    @Override
    public void run() {
        Helper.WHITE_BOX.deoptimizeAll();
        try {
            Thread.sleep(10);
        } catch (Exception e) {
        }
    }

}
