/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test SetForceInlineMethodTest
 * @bug 8006683 8007288 8022832
 * @summary testing of WB::testSetForceInlineMethod()
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   -XX:CompileCommand=compileonly,compiler.whitebox.SimpleTestCaseHelper::*
 *                   compiler.whitebox.SetForceInlineMethodTest
 */

package compiler.whitebox;

public class SetForceInlineMethodTest extends CompilerWhiteBoxTest {

    public static void main(String[] args) throws Exception {
        CompilerWhiteBoxTest.main(SetForceInlineMethodTest::new, args);
    }

    private SetForceInlineMethodTest(TestCase testCase) {
        super(testCase);
    }

    /**
     * Tests {@code WB::testSetForceInlineMethod()} by sequential calling it and
     * checking of return value.
     *
     * @throws Exception if one of the checks fails.
     */
    @Override
    protected void test() throws Exception {
        if (WHITE_BOX.testSetForceInlineMethod(method, true)) {
            throw new RuntimeException("on start " + method
                    + " must be not force inlineable");
        }
        if (!WHITE_BOX.testSetForceInlineMethod(method, true)) {
            throw new RuntimeException("after first change to true " + method
                    + " must be force inlineable");
        }
        if (!WHITE_BOX.testSetForceInlineMethod(method, false)) {
            throw new RuntimeException("after second change to true " + method
                    + " must be still force inlineable");
        }
        if (WHITE_BOX.testSetForceInlineMethod(method, false)) {
            throw new RuntimeException("after first change to false" + method
                    + " must be not force inlineable");
        }
        if (WHITE_BOX.testSetForceInlineMethod(method, false)) {
            throw new RuntimeException("after second change to false " + method
                    + " must be not force inlineable");
        }
    }
}
