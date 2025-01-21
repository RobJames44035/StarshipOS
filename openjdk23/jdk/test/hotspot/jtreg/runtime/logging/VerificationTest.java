/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8150083 8234656
 * @summary test enabling and disabling verification logging and verification log levels
 * @requires vm.flagless
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver VerificationTest
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class VerificationTest {

    static void analyzeOutputOn(ProcessBuilder pb, boolean isLogLevelInfo) throws Exception {
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldContain("[verification]");
        output.shouldContain("Verifying class VerificationTest$InternalClass with new format");
        output.shouldContain("Verifying method VerificationTest$InternalClass.<init>()V");
        output.shouldContain("End class verification for: VerificationTest$InternalClass");

        if (isLogLevelInfo) {
            // logging level 'info' should not output stack map and opcode data.
            output.shouldNotContain("[verification] StackMapTable: frame_count");
            output.shouldNotContain("[verification] offset = 0,  opcode =");

        } else { // log level debug
            output.shouldContain("[debug][verification] StackMapTable: frame_count");
            output.shouldContain("[debug][verification] offset = 0,  opcode =");
        }
        output.shouldHaveExitValue(0);
    }

    static void analyzeOutputOff(ProcessBuilder pb) throws Exception {
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldNotContain("[verification]");
        output.shouldHaveExitValue(0);
    }

    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder("-Xlog:verification=info",
                                                                             InternalClass.class.getName());
        analyzeOutputOn(pb, true);

        pb = ProcessTools.createLimitedTestJavaProcessBuilder("-Xlog:verification=off",
                                                              InternalClass.class.getName());
        analyzeOutputOff(pb);

        // logging level 'debug' should output stackmaps and bytecode data.
        pb = ProcessTools.createLimitedTestJavaProcessBuilder("-Xlog:verification=debug",
                                                              InternalClass.class.getName());
        analyzeOutputOn(pb, false);
    }

    public static class InternalClass {
        public static void main(String[] args) throws Exception {
            System.out.println("VerificationTest");
        }
    }
}
