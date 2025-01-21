/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8031321
 * @summary Verify processing of UseCountTrailingZerosInstruction option
 *          on CPU with TZCNT (BMI1 feature) support.
 * @library /test/lib /
 * @requires vm.flagless
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   compiler.arguments.TestUseCountTrailingZerosInstructionOnSupportedCPU
 */

package compiler.arguments;

import jdk.test.lib.cli.CommandLineOptionTest;

public class TestUseCountTrailingZerosInstructionOnSupportedCPU
        extends BMISupportedCPUTest {
    private static final String DISABLE_BMI = "-XX:-UseBMI1Instructions";

    public TestUseCountTrailingZerosInstructionOnSupportedCPU() {
        super("UseCountTrailingZerosInstruction", TZCNT_WARNING, "bmi1");
    }

    @Override
    public void runTestCases() throws Throwable {

        super.runTestCases();

        /*
          Verify that option will be disabled if all BMI1 instructions
          are explicitly disabled. VM will be launched with following options:
          -XX:-UseBMI1Instructions -version
        */
        CommandLineOptionTest.verifyOptionValueForSameVM(optionName, "false",
                "Option 'UseCountTrailingZerosInstruction' should have "
                    + "'false' value if all BMI1 instructions are explicitly"
                    + " disabled (-XX:-UseBMI1Instructions flag used)",
                TestUseCountTrailingZerosInstructionOnSupportedCPU.DISABLE_BMI);

        /*
          Verify that option could be turned on even if other BMI1
          instructions were turned off. VM will be launched with following
          options: -XX:-UseBMI1Instructions
          -XX:+UseCountTrailingZerosInstruction -version
        */
        CommandLineOptionTest.verifyOptionValueForSameVM(optionName, "true",
                "Option 'UseCountTrailingZerosInstruction' should be able to "
                    + "be turned on even if all BMI1 instructions are "
                    + "disabled (-XX:-UseBMI1Instructions flag used)",
                TestUseCountTrailingZerosInstructionOnSupportedCPU.DISABLE_BMI,
                CommandLineOptionTest.prepareBooleanFlag(optionName, true));
    }

    public static void main(String args[]) throws Throwable {
        new TestUseCountTrailingZerosInstructionOnSupportedCPU().test();
    }
}

