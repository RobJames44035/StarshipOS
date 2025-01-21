/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8133885
 * @summary monitorinflation=trace should have logging from each of the statements in the code
 * @requires vm.flagless
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver MonitorInflationTest
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class MonitorInflationTest {
    static void analyzeOutputOn(ProcessBuilder pb) throws Exception {
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldContain("inflate:");
        output.shouldContain("type='MonitorInflationTest$Waiter'");
        output.shouldContain("I've been waiting.");
        output.shouldHaveExitValue(0);
    }

    static void analyzeOutputOff(ProcessBuilder pb) throws Exception {
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldNotContain("[monitorinflation]");
        output.shouldHaveExitValue(0);
    }

    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder("-Xlog:monitorinflation=trace",
                                                                             InnerClass.class.getName());
        analyzeOutputOn(pb);

        pb = ProcessTools.createLimitedTestJavaProcessBuilder("-Xlog:monitorinflation=off",
                                                              InnerClass.class.getName());
        analyzeOutputOff(pb);
    }

    public static class Waiter {
        public static void foo() {
            System.out.println("I've been waiting.");
        }
    }
    public static class InnerClass {
        public static void main(String[] args) throws Exception {
            Waiter w = new Waiter();
            synchronized (w) {
                w.wait(100);
                w.foo();
            }
        }
    }
}
