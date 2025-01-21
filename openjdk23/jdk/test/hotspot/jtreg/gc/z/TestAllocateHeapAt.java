/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package gc.z;

/*
 * @test TestAllocateHeapAt
 * @requires vm.gc.Z & os.family == "linux"
 * @requires !vm.opt.final.UseLargePages
 * @requires !vm.opt.final.UseTransparentHugePages
 * @summary Test ZGC with -XX:AllocateHeapAt
 * @library /test/lib
 * @run main/othervm gc.z.TestAllocateHeapAt . true
 * @run main/othervm gc.z.TestAllocateHeapAt non-existing-directory false
 */

import jdk.test.lib.os.linux.HugePageConfiguration;
import jdk.test.lib.os.linux.HugePageConfiguration.ShmemTHPMode;
import jdk.test.lib.process.ProcessTools;
import jtreg.SkippedException;

public class TestAllocateHeapAt {
    public static void main(String[] args) throws Exception {
        final String directory = args[0];
        final boolean exists = Boolean.parseBoolean(args[1]);
        final String heapBackingFile = "Heap Backing File: " + directory;
        final String failedToCreateFile = "Failed to create file " + directory;

        final HugePageConfiguration hugePageConfiguration = HugePageConfiguration.readFromOS();
        final ShmemTHPMode mode = hugePageConfiguration.getShmemThpMode();

        if (mode != ShmemTHPMode.never && mode != ShmemTHPMode.advise) {
            throw new SkippedException("The UseTransparentHugePages option may not be respected with Shmem THP Mode: " + mode.name());
        }

        ProcessTools.executeTestJava(
            "-XX:+UseZGC",
            "-Xlog:gc*",
            "-Xms32M",
            "-Xmx32M",
            "-XX:AllocateHeapAt=" + directory,
            "-version")
                .shouldContain(exists ? heapBackingFile : failedToCreateFile)
                .shouldNotContain(exists ? failedToCreateFile : heapBackingFile)
                .shouldHaveExitValue(exists ? 0 : 1);
    }
}
