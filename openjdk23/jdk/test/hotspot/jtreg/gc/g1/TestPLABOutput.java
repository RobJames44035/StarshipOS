/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package gc.g1;

/*
 * @test TestPLABOutput
 * @bug 8140585
 * @summary Check that G1 does not report empty PLAB statistics in the first evacuation.
 * @requires vm.gc.G1
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver gc.g1.TestPLABOutput
 */

import jdk.test.whitebox.WhiteBox;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

import static jdk.test.lib.Asserts.*;

public class TestPLABOutput {

    public static void runTest() throws Exception {
        final String[] arguments = {
            "-Xbootclasspath/a:.",
            "-XX:+UnlockExperimentalVMOptions",
            "-XX:+UnlockDiagnosticVMOptions",
            "-XX:+WhiteBoxAPI",
            "-XX:+UseG1GC",
            "-Xmx10M",
            "-Xlog:gc+plab=debug",
            GCTest.class.getName()
            };

        OutputAnalyzer output = ProcessTools.executeLimitedTestJava(arguments);

        output.shouldHaveExitValue(0);

        System.out.println(output.getStdout());

        String pattern = ".*GC\\(0\\) .*allocated: (\\d+).*";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(output.getStdout());

        if (!m.find()) {
            throw new RuntimeException("Could not find any PLAB statistics output");
        }
        int allocated = Integer.parseInt(m.group(1));
        assertGT(allocated, 0, "Did not allocate any memory during test");
    }

    public static void main(String[] args) throws Exception {
        runTest();
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
