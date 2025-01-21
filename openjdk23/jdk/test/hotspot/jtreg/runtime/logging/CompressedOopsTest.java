/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8149991
 * @requires vm.bits == 64
 * @requires vm.flagless
 * @summary -Xlog:gc+heap+coops=info should have output from the code
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver CompressedOopsTest
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.Platform;
import jdk.test.lib.process.ProcessTools;

public class CompressedOopsTest {
    static void analyzeOutputOn(ProcessBuilder pb) throws Exception {
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldContain("[gc,heap,coops] Heap address");
        output.shouldHaveExitValue(0);
    }

    static void analyzeOutputOff(ProcessBuilder pb) throws Exception {
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldNotContain("[gc,heap,coops]");
        output.shouldHaveExitValue(0);
    }

    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder("-XX:+UseCompressedOops",
                                                                             "-Xlog:gc+heap+coops=debug",
                                                                             InnerClass.class.getName());
        analyzeOutputOn(pb);

        pb = ProcessTools.createLimitedTestJavaProcessBuilder("-XX:+UseCompressedOops",
                                                              "-Xlog:gc+heap+coops",
                                                              InnerClass.class.getName());
        // No coops logging on info level.
        analyzeOutputOff(pb);

        pb = ProcessTools.createLimitedTestJavaProcessBuilder("-XX:+UseCompressedOops",
                                                              "-Xlog:gc+heap+coops=off",
                                                              InnerClass.class.getName());
        analyzeOutputOff(pb);
    }

    public static class InnerClass {
        public static void main(String[] args) throws Exception {
            System.out.println("Compressed Oops (gc+heap+coops) test");
        }
    }
}
