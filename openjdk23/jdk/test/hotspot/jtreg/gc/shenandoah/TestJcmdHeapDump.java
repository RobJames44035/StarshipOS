/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test id=passive
 * @library /test/lib
 * @modules jdk.attach/com.sun.tools.attach
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm/timeout=480 -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCMode=passive
 *      -XX:+ShenandoahDegeneratedGC
 *      TestJcmdHeapDump
 *
 * @run main/othervm/timeout=480 -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCMode=passive
 *      -XX:-ShenandoahDegeneratedGC
 *      TestJcmdHeapDump
 */

/*
 * @test id=aggressive
 * @library /test/lib
 * @modules jdk.attach/com.sun.tools.attach
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm/timeout=480 -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=aggressive
 *      -XX:+ShenandoahOOMDuringEvacALot
 *      TestJcmdHeapDump
 *
 * @run main/othervm/timeout=480 -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=aggressive
 *      -XX:+ShenandoahAllocFailureALot
 *      TestJcmdHeapDump
 *
 * @run main/othervm/timeout=480 -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=aggressive
 *      TestJcmdHeapDump
 */

/*
 * @test id=adaptive
 * @library /test/lib
 * @modules jdk.attach/com.sun.tools.attach
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm/timeout=480 -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=adaptive
 *      -Dtarget=10000
 *      TestJcmdHeapDump
 */

/*
 * @test id=generational
 * @library /test/lib
 * @modules jdk.attach/com.sun.tools.attach
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm/timeout=480 -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=adaptive -XX:ShenandoahGCMode=generational
 *      -Dtarget=10000
 *      TestJcmdHeapDump
 */

/*
 * @test id=static
 * @library /test/lib
 * @modules jdk.attach/com.sun.tools.attach
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm/timeout=480 -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=static
 *      TestJcmdHeapDump
 */

/*
 * @test id=compact
 * @library /test/lib
 * @modules jdk.attach/com.sun.tools.attach
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm/timeout=480 -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=compact
 *     TestJcmdHeapDump
 */

import jdk.test.lib.JDKToolLauncher;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

import java.io.File;

public class TestJcmdHeapDump {
    public static void main(String[] args) {
        long pid = ProcessHandle.current().pid();
        JDKToolLauncher jcmd = JDKToolLauncher.createUsingTestJDK("jcmd");
        jcmd.addToolArg(String.valueOf(pid));
        jcmd.addToolArg("GC.heap_dump");
        String dumpFileName = "myheapdump" + String.valueOf(pid);
        jcmd.addToolArg(dumpFileName);

        try {
            OutputAnalyzer output = ProcessTools.executeProcess(jcmd.getCommand());
            output.shouldHaveExitValue(0);
        } catch (Exception e) {
            throw new RuntimeException("Test failed: " + e);
        }

        File f = new File(dumpFileName);
        if (f.exists()) {
            f.delete();
        } else {
            throw new RuntimeException("Dump file not created");
        }
    }
}
