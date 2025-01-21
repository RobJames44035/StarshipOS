/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8035968
 * @summary Verify UseSHA512Intrinsics option processing on supported CPU.
 * @requires os.arch!="x86" & os.arch!="i386"
 * @library /test/lib /
 * @requires vm.flagless
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   compiler.intrinsics.sha.cli.TestUseSHA512IntrinsicsOptionOnSupportedCPU
 */

package compiler.intrinsics.sha.cli;

import compiler.intrinsics.sha.cli.testcases.GenericTestCaseForSupportedCPU;

public class TestUseSHA512IntrinsicsOptionOnSupportedCPU {
    public static void main(String args[]) throws Throwable {
        new DigestOptionsBase(new GenericTestCaseForSupportedCPU(
                DigestOptionsBase.USE_SHA512_INTRINSICS_OPTION)).test();
    }
}
