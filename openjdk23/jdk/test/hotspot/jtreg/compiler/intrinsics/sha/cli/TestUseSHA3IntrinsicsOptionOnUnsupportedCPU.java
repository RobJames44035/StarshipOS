/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/**
 * @test
 * @bug 8252204
 * @summary Verify UseSHA3Intrinsics option processing on unsupported CPU.
 * @library /test/lib /
 * @requires vm.flagless
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   compiler.intrinsics.sha.cli.TestUseSHA3IntrinsicsOptionOnUnsupportedCPU
 */

package compiler.intrinsics.sha.cli;

import compiler.intrinsics.sha.cli.testcases.GenericTestCaseForUnsupportedCPU;
import compiler.intrinsics.sha.cli.testcases.UseSHAIntrinsicsSpecificTestCaseForUnsupportedCPU;

public class TestUseSHA3IntrinsicsOptionOnUnsupportedCPU {
    public static void main(String args[]) throws Throwable {
        new DigestOptionsBase(
                new GenericTestCaseForUnsupportedCPU(
                        DigestOptionsBase.USE_SHA3_INTRINSICS_OPTION),
                new UseSHAIntrinsicsSpecificTestCaseForUnsupportedCPU(
                        DigestOptionsBase.USE_SHA3_INTRINSICS_OPTION)).test();
    }
}
