/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @build jdk.test.whitebox.WhiteBox
 * @requires !(vm.cpu.features ~= ".*aes.*")
 * @requires vm.compiler1.enabled | !vm.graal.enabled
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *       -XX:+WhiteBoxAPI -Xbatch
 *       compiler.cpuflags.TestAESIntrinsicsOnUnsupportedConfig
 */

package compiler.cpuflags;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.cli.predicate.NotPredicate;
import static jdk.test.lib.cli.CommandLineOptionTest.*;

public class TestAESIntrinsicsOnUnsupportedConfig extends AESIntrinsicsBase {

    private static final String INTRINSICS_NOT_AVAILABLE_MSG = "warning: AES "
            + "intrinsics are not available on this CPU";
    private static final String AES_NOT_AVAILABLE_MSG = "warning: AES "
            + "instructions are not available on this CPU";

    protected void runTestCases() throws Throwable {
        testUseAES();
        testUseAESIntrinsics();
    }

    /**
     * Test checks following situation: <br/>
     * UseAESIntrinsics flag is set to true, TestAESMain is executed <br/>
     * Expected result: UseAESIntrinsics flag is set to false <br/>
     * UseAES flag is set to false <br/>
     * Output shouldn't contain intrinsics usage <br/>
     * Output should contain message about intrinsics unavailability
     * @throws Throwable
     */
    private void testUseAESIntrinsics() throws Throwable {
        OutputAnalyzer outputAnalyzer = ProcessTools.executeTestJava(
                AESIntrinsicsBase.prepareArguments(prepareBooleanFlag(
                        AESIntrinsicsBase.USE_AES_INTRINSICS, true)));
        final String errorMessage = "Case testUseAESIntrinsics failed";
        verifyOutput(new String[] {INTRINSICS_NOT_AVAILABLE_MSG},
                new String[] {AESIntrinsicsBase.CIPHER_INTRINSIC,
                        AESIntrinsicsBase.AES_INTRINSIC},
                errorMessage, outputAnalyzer);
        verifyOptionValue(AESIntrinsicsBase.USE_AES_INTRINSICS, "false",
                errorMessage, outputAnalyzer);
        verifyOptionValue(AESIntrinsicsBase.USE_AES, "false", errorMessage,
                outputAnalyzer);
    }

    /**
     * Test checks following situation: <br/>
     * UseAESIntrinsics flag is set to true, TestAESMain is executed <br/>
     * Expected result: UseAES flag is set to false <br/>
     * UseAES flag is set to false <br/>
     * Output shouldn't contain intrinsics usage <br/>
     * Output should contain message about AES unavailability <br/>
     * @throws Throwable
     */
    private void testUseAES() throws Throwable {
        OutputAnalyzer outputAnalyzer = ProcessTools.executeTestJava(
                AESIntrinsicsBase.prepareArguments(prepareBooleanFlag
                        (AESIntrinsicsBase.USE_AES, true)));
        final String errorMessage = "Case testUseAES failed";
        verifyOutput(new String[] {AES_NOT_AVAILABLE_MSG},
                new String[] {AESIntrinsicsBase.CIPHER_INTRINSIC,
                AESIntrinsicsBase.AES_INTRINSIC}, errorMessage, outputAnalyzer);
        verifyOptionValue(AESIntrinsicsBase.USE_AES_INTRINSICS, "false",
                errorMessage, outputAnalyzer);
        verifyOptionValue(AESIntrinsicsBase.USE_AES, "false", errorMessage,
                outputAnalyzer);
    }

    public static void main(String args[]) throws Throwable {
        new TestAESIntrinsicsOnUnsupportedConfig().runTestCases();
    }
}
