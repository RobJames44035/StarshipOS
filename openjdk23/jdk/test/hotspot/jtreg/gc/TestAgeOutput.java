/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package gc;

/*
 * @test TestAgeOutputSerial
 * @bug 8164936
 * @requires vm.gc.Serial
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver gc.TestAgeOutput UseSerialGC
 */

/*
 * @test TestAgeOutputG1
 * @bug 8164936
 * @summary Check that collectors using age table based aging print an age table even for the first garbage collection
 * @requires vm.gc.G1
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver gc.TestAgeOutput UseG1GC
 */

import jdk.test.whitebox.WhiteBox;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class TestAgeOutput {

    public static void checkPattern(String pattern, String what) throws Exception {
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(what);

        if (!m.find()) {
            throw new RuntimeException("Could not find pattern " + pattern + " in output");
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
            "-Xlog:gc+age=trace",
            GCTest.class.getName());

        output.shouldHaveExitValue(0);

        System.out.println(output.getStdout());

        String stdout = output.getStdout();

        checkPattern(".*GC\\(0\\) .*Desired survivor size.*", stdout);
        checkPattern(".*GC\\(0\\) .*Age table:.*", stdout);
        checkPattern(".*GC\\(0\\) .*- age   1:.*", stdout);
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
