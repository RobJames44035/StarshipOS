/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package gc;

import static jdk.test.lib.Asserts.*;
import gc.testlibrary.PerfCounters;


/* @test TestGenerationPerfCounterSerial
 * @bug 8080345
 * @requires vm.gc.Serial
 * @library /test/lib /
 * @summary Tests that the sun.gc.policy.generations returns 2 for all GCs.
 * @modules java.base/jdk.internal.misc
 *          java.compiler
 *          java.management/sun.management
 *          jdk.internal.jvmstat/sun.jvmstat.monitor
 * @run main/othervm -XX:+UsePerfData -XX:+UseSerialGC gc.TestGenerationPerfCounter
 */

/* @test TestGenerationPerfCounterParallel
 * @bug 8080345
 * @requires vm.gc.Parallel
 * @library /test/lib /
 * @summary Tests that the sun.gc.policy.generations returns 2 for all GCs.
 * @modules java.base/jdk.internal.misc
 *          java.compiler
 *          java.management/sun.management
 *          jdk.internal.jvmstat/sun.jvmstat.monitor
 * @run main/othervm -XX:+UsePerfData -XX:+UseParallelGC gc.TestGenerationPerfCounter
 */

/* @test TestGenerationPerfCounterG1
 * @bug 8080345
 * @requires vm.gc.G1
 * @library /test/lib /
 * @summary Tests that the sun.gc.policy.generations returns 2 for all GCs.
 * @modules java.base/jdk.internal.misc
 *          java.compiler
 *          java.management/sun.management
 *          jdk.internal.jvmstat/sun.jvmstat.monitor
 * @run main/othervm -XX:+UsePerfData -XX:+UseG1GC gc.TestGenerationPerfCounter
 */

public class TestGenerationPerfCounter {
    public static void main(String[] args) throws Exception {
        long numGenerations =
            PerfCounters.findByName("sun.gc.policy.generations").longValue();
        assertEQ(numGenerations, 2L);
    }
}
