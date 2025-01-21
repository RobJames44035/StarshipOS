/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8035968
 * @summary Verify UseSHA256Intrinsics option processing on unsupported CPU.
 * @library /test/lib /
 * @requires vm.flagless
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   compiler.intrinsics.sha.cli.TestUseSHA256IntrinsicsOptionOnUnsupportedCPU
 */

package compiler.intrinsics.sha.cli;

import compiler.intrinsics.sha.cli.testcases.GenericTestCaseForUnsupportedCPU;
import compiler.intrinsics.sha.cli.testcases.UseSHAIntrinsicsSpecificTestCaseForUnsupportedCPU;

public class TestUseSHA256IntrinsicsOptionOnUnsupportedCPU {
    public static void main(String args[]) throws Throwable {
        new DigestOptionsBase(
                new GenericTestCaseForUnsupportedCPU(
                        DigestOptionsBase.USE_SHA256_INTRINSICS_OPTION),
                new UseSHAIntrinsicsSpecificTestCaseForUnsupportedCPU(
                        DigestOptionsBase.USE_SHA256_INTRINSICS_OPTION)).test();
    }
}
