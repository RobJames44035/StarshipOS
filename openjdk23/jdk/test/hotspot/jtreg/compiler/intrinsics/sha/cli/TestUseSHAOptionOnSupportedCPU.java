/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8035968
 * @summary Verify UseSHA option processing on supported CPU.
 * @library /test/lib /
 * @requires vm.flagless
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   compiler.intrinsics.sha.cli.TestUseSHAOptionOnSupportedCPU
 */

package compiler.intrinsics.sha.cli;

import compiler.intrinsics.sha.cli.testcases.GenericTestCaseForSupportedCPU;
import compiler.intrinsics.sha.cli.testcases.UseSHASpecificTestCaseForSupportedCPU;

public class TestUseSHAOptionOnSupportedCPU {
    public static void main(String args[]) throws Throwable {
        new DigestOptionsBase(
                new GenericTestCaseForSupportedCPU(
                        DigestOptionsBase.USE_SHA_OPTION),
                new UseSHASpecificTestCaseForSupportedCPU(
                        DigestOptionsBase.USE_SHA_OPTION)).test();
    }
}
