/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test DeoptimizeMethodTest
 * @bug 8006683 8007288 8022832
 * @summary testing of WB::deoptimizeMethod()
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   -XX:CompileCommand=compileonly,compiler.whitebox.SimpleTestCaseHelper::*
 *                   compiler.whitebox.DeoptimizeMethodTest
 */

package compiler.whitebox;

public class DeoptimizeMethodTest extends CompilerWhiteBoxTest {

    public static void main(String[] args) throws Exception {
        CompilerWhiteBoxTest.main(DeoptimizeMethodTest::new, args);
    }

    private DeoptimizeMethodTest(TestCase testCase) {
        super(testCase);
        // to prevent inlining of #method
        WHITE_BOX.testSetDontInlineMethod(method, true);
    }

    /**
     * Tests {@code WB::deoptimizeMethod()} by calling it after
     * compilation and checking that method isn't compiled.
     *
     * @throws Exception if one of the checks fails.
     */
    @Override
    protected void test() throws Exception {
        if (skipXcompOSR()) {
            return;
        }
        compile();
        checkCompiled();
        deoptimize();
        checkNotCompiled();
    }
}
