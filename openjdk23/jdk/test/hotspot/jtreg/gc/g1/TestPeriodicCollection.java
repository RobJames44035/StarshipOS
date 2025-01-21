/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package gc.g1;

/**
 * @test TestPeriodicCollection
 * @requires vm.gc.G1
 * @requires vm.compMode != "Xcomp"
 * @summary Verify that heap shrinks when the application is idle.
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 * @modules java.management/sun.management
 * @run main/othervm -XX:MaxNewSize=32M -XX:InitialHeapSize=48M -Xmx128M -XX:MinHeapFreeRatio=5 -XX:MaxHeapFreeRatio=25 -XX:+UseG1GC -XX:G1PeriodicGCInterval=3000 -XX:+G1PeriodicGCInvokesConcurrent -Xlog:gc*,gc+periodic=debug,gc+ergo+heap=debug gc.g1.TestPeriodicCollection
 * @run main/othervm -XX:MaxNewSize=32M -XX:InitialHeapSize=48M -Xmx128M -XX:MinHeapFreeRatio=5 -XX:MaxHeapFreeRatio=25 -XX:+UseG1GC -XX:G1PeriodicGCInterval=3000 -XX:-G1PeriodicGCInvokesConcurrent -Xlog:gc*,gc+periodic=debug,gc+ergo+heap=debug gc.g1.TestPeriodicCollection
 */

import com.sun.management.HotSpotDiagnosticMXBean;

import gc.testlibrary.Helpers;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.text.NumberFormat;
import static jdk.test.lib.Asserts.*;

public class TestPeriodicCollection {

    public static final String MIN_FREE_RATIO_FLAG_NAME = "MinHeapFreeRatio";
    public static final String MAX_FREE_RATIO_FLAG_NAME = "MaxHeapFreeRatio";

    private static final int IDLE_TIME = 7 * 1000;

    private static boolean gcOccurred() {
        for (GarbageCollectorMXBean b : ManagementFactory.getGarbageCollectorMXBeans()) {
            if (b.getCollectionCount() != 0) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        MemoryUsage muInitial = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        printMemoryUsage("initial", muInitial);

        if (gcOccurred()) {
          System.out.println("At least one garbage collection occurred. Exiting as this may have already shrunk the heap.");
          return;
        }

        try {
            Thread.sleep(IDLE_TIME);
        } catch (InterruptedException ie) {
            System.err.println("Skipped. Failed to wait for idle collection");
        }

        MemoryUsage muAfter = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        printMemoryUsage("after", muAfter);

        assertLessThan(muAfter.getCommitted(), muInitial.getCommitted(), String.format(
                "committed free heap size is not less than committed full heap size, heap hasn't been shrunk?%n"
                + "%s = %s%n%s = %s",
                MIN_FREE_RATIO_FLAG_NAME,
                ManagementFactory.getPlatformMXBean(HotSpotDiagnosticMXBean.class)
                    .getVMOption(MIN_FREE_RATIO_FLAG_NAME).getValue(),
                MAX_FREE_RATIO_FLAG_NAME,
                ManagementFactory.getPlatformMXBean(HotSpotDiagnosticMXBean.class)
                    .getVMOption(MAX_FREE_RATIO_FLAG_NAME).getValue()
        ));
    }

    public static final NumberFormat NF = Helpers.numberFormatter();

    public static void printMemoryUsage(String label, MemoryUsage memusage) {
        float freeratio = 1f - (float) memusage.getUsed() / memusage.getCommitted();
        System.out.format("[%-24s] init: %-7s, used: %-7s, comm: %-7s, freeRatio ~= %.1f%%%n",
                label,
                NF.format(memusage.getInit()),
                NF.format(memusage.getUsed()),
                NF.format(memusage.getCommitted()),
                freeratio * 100
        );
    }
}
