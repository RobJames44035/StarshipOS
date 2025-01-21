/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package gc;

/*
 * @test TestNumWorkerOutputG1
 * @bug 8165292
 * @summary Check that when PrintGCDetails is enabled, gc,task output is printed only once per collection.
 * @requires vm.gc.G1
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver gc.TestNumWorkerOutput UseG1GC
 */

import jdk.test.whitebox.WhiteBox;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class TestNumWorkerOutput {

    public static void checkPatternOnce(String pattern, String what) throws Exception {
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(what);

        if (!m.find()) {
            throw new RuntimeException("Could not find pattern " + pattern + " in output");
        }
        if (m.find()) {
            throw new RuntimeException("Could find pattern " + pattern + " in output more than once");
        }
    }

    public static void runTest(String gcArg) throws Exception {
        OutputAnalyzer output = ProcessTools.executeLimitedTestJava(
            "-Xbootclasspath/a:.",
            "-XX:+UnlockExperimentalVMOptions",
            "-XX:+UnlockDiagnosticVMOptions",
            "-XX:+WhiteBoxAPI",
            "-XX:+" + gcArg,
            "-Xmx10M",
            "-XX:+PrintGCDetails",
            GCTest.class.getName());

        output.shouldHaveExitValue(0);

        System.out.println(output.getStdout());

        String stdout = output.getStdout();

        checkPatternOnce(".*[info.*].*[gc,task.*].*GC\\(0\\) .*Using \\d+ workers of \\d+ for evacuation.*", stdout);
    }

    public static void main(String[] args) throws Exception {
        runTest(args[0]);
    }

    static class GCTest {
        private static final WhiteBox WB = WhiteBox.getWhiteBox();

        public static Object holder;

        public static void main(String [] args) {
            holder = new byte[100];
            WB.youngGC();
            System.out.println(holder);
        }
    }
}
