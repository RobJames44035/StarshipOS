/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test id=passive
 * @requires vm.gc.Shenandoah
 * @library /test/lib
 *
 * @run main/othervm -Xms16m -Xmx512m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCMode=passive
 *      -XX:+ShenandoahDegeneratedGC
 *      -Dtarget=10000
 *      TestDynamicSoftMaxHeapSize
 *
 * @run main/othervm -Xms16m -Xmx512m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCMode=passive
 *      -XX:-ShenandoahDegeneratedGC
 *      -Dtarget=10000
 *      TestDynamicSoftMaxHeapSize
 */

/*
 * @test id=aggressive
 * @requires vm.gc.Shenandoah
 * @library /test/lib
 *
 * @run main/othervm -Xms16m -Xmx512m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=aggressive
 *      -Dtarget=1000
 *      TestDynamicSoftMaxHeapSize
 */

/*
 * @test id=adaptive
 * @requires vm.gc.Shenandoah
 * @library /test/lib
 *
 * @run main/othervm -Xms16m -Xmx512m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=adaptive
 *      -Dtarget=10000
 *      TestDynamicSoftMaxHeapSize
 */

/*
 * @test id=generational
 * @requires vm.gc.Shenandoah
 * @library /test/lib
 *
 * @run main/othervm -Xms16m -Xmx512m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=adaptive -XX:ShenandoahGCMode=generational
 *      -Dtarget=10000
 *      TestDynamicSoftMaxHeapSize
 */

/*
 * @test id=static
 * @requires vm.gc.Shenandoah
 * @library /test/lib
 *
 * @run main/othervm -Xms16m -Xmx512m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=static
 *      -Dtarget=10000
 *      TestDynamicSoftMaxHeapSize
 */

/*
 * @test id=compact
 * @requires vm.gc.Shenandoah
 * @library /test/lib
 *
 * @run main/othervm -Xms16m -Xmx512m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=compact
 *      -Dtarget=1000
 *     TestDynamicSoftMaxHeapSize
 */

import java.util.Random;
import jdk.test.lib.Utils;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.dcmd.PidJcmdExecutor;

public class TestDynamicSoftMaxHeapSize {

    static final long TARGET_MB = Long.getLong("target", 10_000); // 10 Gb allocation
    static final long STRIDE = 10_000_000;

    static volatile Object sink;

    public static void main(String[] args) throws Exception {
        long count = TARGET_MB * 1024 * 1024 / 16;
        Random r = Utils.getRandomInstance();
        PidJcmdExecutor jcmd = new PidJcmdExecutor();

        for (long c = 0; c < count; c += STRIDE) {
            // Sizes specifically include heaps below Xms and above Xmx to test saturation code.
            jcmd.execute("VM.set_flag SoftMaxHeapSize " + r.nextInt(768*1024*1024), true);
            for (long s = 0; s < STRIDE; s++) {
                sink = new Object();
            }
            Thread.sleep(1);
        }
    }

}
