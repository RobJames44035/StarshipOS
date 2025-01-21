/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package gc.metaspace;

import java.util.List;
import java.lang.management.*;
import jdk.test.lib.Platform;
import static jdk.test.lib.Asserts.*;

/* @test TestMetaspaceMemoryPool
 * @bug 8000754
 * @summary Tests that a MemoryPoolMXBeans is created for metaspace and that a
 *          MemoryManagerMXBean is created.
 * @requires vm.bits == 64 & vm.opt.final.UseCompressedOops == true
 * @library /test/lib
 * @library /
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run main/othervm -XX:-UseCompressedOops gc.metaspace.TestMetaspaceMemoryPool
 * @run main/othervm -XX:-UseCompressedOops -XX:MaxMetaspaceSize=60m gc.metaspace.TestMetaspaceMemoryPool
 * @run main/othervm -XX:+UseCompressedOops -XX:+UseCompressedClassPointers gc.metaspace.TestMetaspaceMemoryPool
 * @run main/othervm -XX:+UseCompressedOops -XX:+UseCompressedClassPointers -XX:CompressedClassSpaceSize=60m gc.metaspace.TestMetaspaceMemoryPool
 */

public class TestMetaspaceMemoryPool {
    public static void main(String[] args) {
        verifyThatMetaspaceMemoryManagerExists();

        boolean isMetaspaceMaxDefined = InputArguments.containsPrefix("-XX:MaxMetaspaceSize");
        verifyMemoryPool(getMemoryPool("Metaspace"), isMetaspaceMaxDefined);

        if (Platform.is64bit()) {
            if (InputArguments.contains("-XX:+UseCompressedOops")) {
                MemoryPoolMXBean cksPool = getMemoryPool("Compressed Class Space");
                verifyMemoryPool(cksPool, true);
            }
        }
    }

    private static void verifyThatMetaspaceMemoryManagerExists() {
        List<MemoryManagerMXBean> managers = ManagementFactory.getMemoryManagerMXBeans();
        for (MemoryManagerMXBean manager : managers) {
            if (manager.getName().equals("Metaspace Manager")) {
                return;
            }
        }

        throw new RuntimeException("Expected to find a metaspace memory manager");
    }

    private static MemoryPoolMXBean getMemoryPool(String name) {
        List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean pool : pools) {
            if (pool.getName().equals(name)) {
                return pool;
            }
        }

        throw new RuntimeException("Expected to find a memory pool with name " + name);
    }

    private static void verifyMemoryPool(MemoryPoolMXBean pool, boolean isMaxDefined) {
        MemoryUsage mu = pool.getUsage();
        long init = mu.getInit();
        long used = mu.getUsed();
        long committed = mu.getCommitted();
        long max = mu.getMax();

        assertGTE(init, 0L);
        assertGTE(used, init);
        assertGTE(committed, used);

        if (isMaxDefined) {
            assertGTE(max, committed);
        } else {
            assertEQ(max, -1L);
        }
    }
}
