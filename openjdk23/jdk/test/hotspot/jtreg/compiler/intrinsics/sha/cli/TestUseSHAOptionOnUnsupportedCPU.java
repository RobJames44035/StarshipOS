/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8035968
 * @summary Verify UseSHA option processing on unsupported CPU.
 * @library /test/lib /
 * @requires vm.flagless
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   compiler.intrinsics.sha.cli.TestUseSHAOptionOnUnsupportedCPU
 */

package compiler.intrinsics.sha.cli;

import compiler.intrinsics.sha.cli.testcases.GenericTestCaseForUnsupportedCPU;
import compiler.intrinsics.sha.cli.testcases.UseSHASpecificTestCaseForUnsupportedCPU;

public class TestUseSHAOptionOnUnsupportedCPU {
    public static void main(String args[]) throws Throwable {
        new DigestOptionsBase(
                new GenericTestCaseForUnsupportedCPU(
                        DigestOptionsBase.USE_SHA_OPTION),
                new UseSHASpecificTestCaseForUnsupportedCPU(
                        DigestOptionsBase.USE_SHA_OPTION)).test();
    }
}
