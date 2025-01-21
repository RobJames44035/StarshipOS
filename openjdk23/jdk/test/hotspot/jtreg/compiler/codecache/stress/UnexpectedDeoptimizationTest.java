/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test UnexpectedDeoptimizationTest
 * @key stress randomness
 * @summary stressing code cache by forcing unexpected deoptimizations
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
 *                   compiler.codecache.stress.UnexpectedDeoptimizationTest
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   -XX:+IgnoreUnrecognizedVMOptions -XX:-DeoptimizeRandom
 *                   -XX:CompileCommand=dontinline,compiler.codecache.stress.Helper$TestCase::method
 *                   -XX:+SegmentedCodeCache
 *                   compiler.codecache.stress.UnexpectedDeoptimizationTest
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   -XX:+IgnoreUnrecognizedVMOptions -XX:-DeoptimizeRandom
 *                   -XX:CompileCommand=dontinline,compiler.codecache.stress.Helper$TestCase::method
 *                   -XX:-SegmentedCodeCache
 *                   -DhelperVirtualThread=true
 *                   compiler.codecache.stress.UnexpectedDeoptimizationTest
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   -XX:+IgnoreUnrecognizedVMOptions -XX:-DeoptimizeRandom
 *                   -XX:CompileCommand=dontinline,compiler.codecache.stress.Helper$TestCase::method
 *                   -XX:+SegmentedCodeCache
 *                   -DhelperVirtualThread=true
 *                   compiler.codecache.stress.UnexpectedDeoptimizationTest
 */

package compiler.codecache.stress;

import java.util.Random;
import jdk.test.lib.Utils;

public class UnexpectedDeoptimizationTest implements Runnable {
    private final Random rng = Utils.getRandomInstance();

    public static void main(String[] args) {
        new CodeCacheStressRunner(new UnexpectedDeoptimizationTest()).runTest();
    }

    @Override
    public void run() {
        Helper.WHITE_BOX.deoptimizeFrames(rng.nextBoolean());
        // Sleep a short while to allow the stacks to grow - otherwise
        //  we end up running almost all code in the interpreter
        try {
            Thread.sleep(10);
        } catch (Exception e) {
        }

    }

}
