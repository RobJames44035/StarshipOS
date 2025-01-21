/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package gc.arguments;

/*
 * @test TestAggressiveHeap
 * @bug 8179084
 * @requires vm.gc.Parallel
 * @summary Test argument processing for -XX:+AggressiveHeap.
 * @library /test/lib
 * @library /
 * @modules java.management
 * @run driver gc.arguments.TestAggressiveHeap
 */

import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import jdk.test.lib.process.OutputAnalyzer;
import jtreg.SkippedException;

public class TestAggressiveHeap {

    public static void main(String args[]) throws Exception {
        if (canUseAggressiveHeapOption()) {
            testFlag();
        }
    }

    // Note: Not a normal boolean flag; -XX:-AggressiveHeap is invalid.
    private static final String option = "-XX:+AggressiveHeap";

    // Option requires at least 256M, else error during option processing.
    private static final long minMemory = 256 * 1024 * 1024;

    // Setting the heap to half of the physical memory is not suitable for
    // a test environment with many tests running concurrently, setting to
    // half of the required size instead.
    private static final String heapSizeOption = "-Xmx128M";

    // bool UseParallelGC = true {product} {command line}
    private static final String parallelGCPattern =
        " *bool +UseParallelGC *= *true +\\{product\\} *\\{command line\\}";

    private static void testFlag() throws Exception {
        OutputAnalyzer output = GCArguments.executeTestJava(
            option, heapSizeOption, "-XX:+PrintFlagsFinal", "-version");

        output.shouldHaveExitValue(0);

        String value = output.firstMatch(parallelGCPattern);
        if (value == null) {
            throw new RuntimeException(
                option + " didn't set UseParallelGC as if from command line");
        }
    }

    private static boolean haveRequiredMemory() throws Exception {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName os = new ObjectName("java.lang", "type", "OperatingSystem");
        Object attr = server.getAttribute(os, "TotalPhysicalMemorySize");
        String value = attr.toString();
        long memory = Long.parseLong(value);
        return memory >= minMemory;
    }

    private static boolean canUseAggressiveHeapOption() throws Exception {
        if (!haveRequiredMemory()) {
            throw new SkippedException("Skipping test of " + option + " : insufficient memory");
        }
        return true;
    }
}
