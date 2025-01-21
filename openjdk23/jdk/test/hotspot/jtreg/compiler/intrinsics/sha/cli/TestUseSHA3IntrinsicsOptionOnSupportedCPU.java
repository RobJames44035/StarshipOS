/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/**
 * @test
 * @bug 8252204
 * @summary Verify UseSHA3Intrinsics option processing on supported CPU.
 * @library /test/lib /
 * @requires vm.flagless
 * @requires os.arch == "aarch64" & os.family == "mac"
 * @comment sha3 is only implemented on AArch64 for now.
 *          UseSHA3Intrinsics is only auto-enabled on Apple silicon, because it
 *          may introduce performance regression on others. See JDK-8297092.
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   compiler.intrinsics.sha.cli.TestUseSHA3IntrinsicsOptionOnSupportedCPU
 */

package compiler.intrinsics.sha.cli;

import compiler.intrinsics.sha.cli.testcases.GenericTestCaseForSupportedCPU;

public class TestUseSHA3IntrinsicsOptionOnSupportedCPU {
    public static void main(String args[]) throws Throwable {
        new DigestOptionsBase(new GenericTestCaseForSupportedCPU(
                DigestOptionsBase.USE_SHA3_INTRINSICS_OPTION)).test();
    }
}
