/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

import jdk.test.lib.*;
import jdk.test.lib.process.*;
import jdk.test.whitebox.WhiteBox;

/*
 * @test TestAbortVMOnSafepointTimeout
 * @summary Check if VM can kill thread which doesn't reach safepoint,
 *          test grace period before AbortVMOnSafepointTimeout kicks in
 * @bug 8219584 8227528 8315795
 * @requires vm.flagless
 * @library /testlibrary /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver TestAbortVMOnSafepointTimeout
 */

public class TestAbortVMOnSafepointTimeout {

    public static void testThreadKilledOnSafepointTimeout() throws Exception {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
                "-Xbootclasspath/a:.",
                "-XX:+UnlockDiagnosticVMOptions",
                "-XX:+WhiteBoxAPI",
                "-XX:+SafepointTimeout",
                "-XX:+SafepointALot",
                "-XX:+AbortVMOnSafepointTimeout",
                "-XX:SafepointTimeoutDelay=50",
                "-XX:GuaranteedSafepointInterval=1",
                "-XX:-CreateCoredumpOnCrash",
                "-Xms64m",
                "TestAbortVMOnSafepointTimeout$Test",
                "999" /* 999 is max unsafe sleep */
        );

        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        verifyAbortVmApplied(output);
    }

    public static void testGracePeriodAppliedBeforeVmAbort() throws Exception {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
                "-Xbootclasspath/a:.",
                "-XX:+UnlockDiagnosticVMOptions",
                "-XX:+WhiteBoxAPI",
                "-XX:+SafepointTimeout",
                "-XX:+SafepointALot",
                "-XX:+AbortVMOnSafepointTimeout",
                "-XX:AbortVMOnSafepointTimeoutDelay=10000", // Using 10 seconds instead of a smaller value for windows-debug
                "-XX:SafepointTimeoutDelay=50",
                "-XX:GuaranteedSafepointInterval=1",
                "-XX:-CreateCoredumpOnCrash",
                "-Xms64m",
                "TestAbortVMOnSafepointTimeout$TestWithDelay"
        );

        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldContain(TestWithDelay.PRE_STALL_TEXT);
        verifyAbortVmApplied(output);
    }

    private static void verifyAbortVmApplied(OutputAnalyzer output) {
        output.shouldContain("Timed out while spinning to reach a safepoint.");
        if (Platform.isWindows()) {
            output.shouldContain("Safepoint sync time longer than");
        } else {
            output.shouldContain("SIGILL");
            if (Platform.isLinux()) {
                output.shouldContain("(sent by kill)");
            }
        }
        output.shouldNotHaveExitValue(0);
    }

    public static void main(String[] args) throws Exception {
        // test basic AbortVMOnSafepointTimeout functionality
        testThreadKilledOnSafepointTimeout();

        // verify -XX:AbortVMOnSafepointTimeoutDelay functionality
        testGracePeriodAppliedBeforeVmAbort();
    }

    public static class Test {
        public static void main(String[] args) throws Exception {
            Integer waitTime = Integer.parseInt(args[0]);
            WhiteBox wb = WhiteBox.getWhiteBox();
            // Loop here to cause a safepoint timeout.
            while (true) {
                wb.waitUnsafe(waitTime);
            }
        }
    }

    public static class TestWithDelay {

        public static final String PRE_STALL_TEXT = "THE FOLLOWING STALL SHOULD BE CAPTURED";

        public static void main(String[] args) throws Exception {
            WhiteBox wb = WhiteBox.getWhiteBox();
            // induce a stall that should not be picked up before grace period
            wb.waitUnsafe(999);
            System.out.println(PRE_STALL_TEXT);

            // trigger safepoint timeout
            while (true) {
                wb.waitUnsafe(999);
            }
        }
    }
}
