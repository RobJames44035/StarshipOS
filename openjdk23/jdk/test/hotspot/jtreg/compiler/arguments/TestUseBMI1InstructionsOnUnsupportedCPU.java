/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8031321
 * @summary Verify processing of UseBMI1Instructions option on CPU without
 *          BMI1 feature support.
 * @library /test/lib /
 * @requires vm.flagless
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   compiler.arguments.TestUseBMI1InstructionsOnUnsupportedCPU
 */

package compiler.arguments;

public class TestUseBMI1InstructionsOnUnsupportedCPU
      extends BMIUnsupportedCPUTest {

    public TestUseBMI1InstructionsOnUnsupportedCPU() {
        super("UseBMI1Instructions", BMI1_WARNING, "bmi1");
    }

    public static void main(String args[]) throws Throwable {
        new TestUseBMI1InstructionsOnUnsupportedCPU().test();
    }
}

