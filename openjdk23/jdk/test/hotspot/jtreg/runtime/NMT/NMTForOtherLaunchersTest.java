/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * With JDK-8256844 "Make NMT late-initializable", NMT should work out of the box with jdk launchers other than
 * java.exe.
 *
 * Test that assumption (we test with javac and jar and leave it at that, other tools should be fine as well)
 */

/**
 * @test id=javac
 * @bug 8256844
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver NMTForOtherLaunchersTest javac
 */

/**
 * @test id=jar
 * @bug 8256844
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver NMTForOtherLaunchersTest jar
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.JDKToolFinder;

public class NMTForOtherLaunchersTest {
    public static void main(String args[]) throws Exception {
        String tool = args[0];
        ProcessBuilder pb = new ProcessBuilder();
        pb.command(new String[]{
                JDKToolFinder.getJDKTool(tool),
                "-J-XX:NativeMemoryTracking=summary",
                "-J-XX:+UnlockDiagnosticVMOptions",
                "-J-XX:+PrintNMTStatistics",
                "--help"});
        System.out.println(pb.command());
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldHaveExitValue(0);
        // We should not see the "wrong launcher?" message, which would indicate
        // an older JDK, and we should see the NMT stat output when the VM shuts down.
        output.shouldNotContain("wrong launcher");
        output.shouldContain("Native Memory Tracking:");
        output.shouldMatch("Total: reserved=\\d+, committed=\\d+.*");
    }
}
