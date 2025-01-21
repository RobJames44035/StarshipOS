/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8031321
 * @summary Verify processing of UseCountLeadingZerosInstruction option
 *          on CPU with LZCNT support.
 * @library /test/lib /
 * @requires vm.flagless
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   compiler.arguments.TestUseCountLeadingZerosInstructionOnSupportedCPU
 */

package compiler.arguments;

public class TestUseCountLeadingZerosInstructionOnSupportedCPU
     extends BMISupportedCPUTest {

    public TestUseCountLeadingZerosInstructionOnSupportedCPU() {
        super("UseCountLeadingZerosInstruction", LZCNT_WARNING, "lzcnt");
    }

    public static void main(String args[]) throws Throwable {
        new TestUseCountLeadingZerosInstructionOnSupportedCPU().test();
    }
}

