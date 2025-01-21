/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test id=G1
 * @bug 8241486
 * @summary G1/Z give warning when using LoopStripMiningIter and turn off LoopStripMiningIter (0)
 * @requires vm.flagless
 * @requires vm.flavor == "server" & !vm.graal.enabled
 * @requires vm.gc.G1
 * @library /test/lib
 * @run driver TestNoWarningLoopStripMiningIterSet G1
 */

/*
 * @test id=Shenandoah
 * @bug 8241486
 * @summary G1/Z give warning when using LoopStripMiningIter and turn off LoopStripMiningIter (0)
 * @requires vm.flagless
 * @requires vm.flavor == "server" & !vm.graal.enabled
 * @requires vm.gc.Shenandoah
 * @library /test/lib
 * @run driver TestNoWarningLoopStripMiningIterSet Shenandoah
 */

/*
 * @test id=Z
 * @bug 8241486
 * @summary G1/Z give warning when using LoopStripMiningIter and turn off LoopStripMiningIter (0)
 * @requires vm.flagless
 * @requires vm.flavor == "server" & !vm.graal.enabled
 * @requires vm.gc.Z
 * @library /test/lib
 * @run driver TestNoWarningLoopStripMiningIterSet Z
 */

/*
 * @test id=Epsilon
 * @bug 8241486
 * @summary G1/Z give warning when using LoopStripMiningIter and turn off LoopStripMiningIter (0)
 * @requires vm.flagless
 * @requires vm.flavor == "server" & !vm.graal.enabled
 * @requires vm.gc.Epsilon
 * @library /test/lib
 * @run driver TestNoWarningLoopStripMiningIterSet Epsilon
 */


import jdk.test.lib.Asserts;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import java.util.function.Consumer;
import java.util.Arrays;
import java.util.List;

public class TestNoWarningLoopStripMiningIterSet {
    static final String CLSOnLSMEqualZero = "When counted loop safepoints are enabled, LoopStripMiningIter must be at least 1 (a safepoint every 1 iteration): setting it to 1";
    static final String CLSOffLSMGreaterZero = "Disabling counted safepoints implies no loop strip mining: setting LoopStripMiningIter to 0";

    public static void testWith(Consumer<OutputAnalyzer> check, String msg, boolean cls, int iters, String... args) throws Exception {
        String[] cmds = new String[args.length + 3];
        cmds[0] = "-XX:+UnlockExperimentalVMOptions";
        System.arraycopy(args, 0, cmds, 1, args.length);
        cmds[args.length + 1] = "-XX:+PrintFlagsFinal";
        cmds[args.length + 2] = "-version";
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(cmds);
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldHaveExitValue(0);

        check.accept(output);

        Asserts.assertEQ(output.firstMatch("(.+?) UseCountedLoopSafepoints.+?= (.+?) (.+?)", 2), Boolean.toString(cls), msg + ", but got wrong CLS");
        Asserts.assertEQ(output.firstMatch("(.+?) LoopStripMiningIter.+?= (.+?) (.+?)", 2), String.valueOf(iters), msg + ", but got wrong LSM");
    }

    public static void main(String[] args) throws Exception {
        String gc = "-XX:+Use" + args[0] + "GC";
        testWith(output -> output.shouldNotContain(CLSOffLSMGreaterZero), "should have CLS and LSM enabled", true, 100, "-XX:LoopStripMiningIter=100", gc);
        testWith(output -> output.shouldContain(CLSOffLSMGreaterZero), "should have CLS and LSM disabled", false, 0, "-XX:-UseCountedLoopSafepoints", "-XX:LoopStripMiningIter=100", gc);
        testWith(output -> output.shouldContain(CLSOnLSMEqualZero), "should have CLS and LSM enabled", true, 1, "-XX:LoopStripMiningIter=0", gc);
        testWith(output -> output.shouldNotContain(CLSOnLSMEqualZero), "should have CLS and LSM disabled", false, 0, "-XX:-UseCountedLoopSafepoints", "-XX:LoopStripMiningIter=0", gc);
    }
}
