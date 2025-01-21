/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+IgnoreUnrecognizedVMOptions -XX:+WhiteBoxAPI -XX:+LogCompilation
 *                   -XX:CompileCommand=compileonly,compiler.intrinsics.mathexact.sanity.MathIntrinsic*::execMathMethod
 *                   -XX:LogFile=hs_neg.log -XX:-UseMathExactIntrinsics
 *                   compiler.intrinsics.mathexact.sanity.DecrementExactIntTest
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+IgnoreUnrecognizedVMOptions -XX:+WhiteBoxAPI -XX:+LogCompilation
 *                   -XX:CompileCommand=compileonly,compiler.intrinsics.mathexact.sanity.MathIntrinsic*::execMathMethod
 *                   -XX:LogFile=hs.log -XX:+UseMathExactIntrinsics
 *                   compiler.intrinsics.mathexact.sanity.DecrementExactIntTest
 * @run driver compiler.testlibrary.intrinsics.Verifier hs_neg.log hs.log
 */

package compiler.intrinsics.mathexact.sanity;

public class DecrementExactIntTest {

    public static void main(String[] args) throws Exception {
        new IntrinsicBase.IntTest(MathIntrinsic.IntIntrinsic.Decrement).test();
    }
}
