/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/**
 * @test
 * @summary Test OOME in separate thread is recoverable
 * @requires vm.gc.Shenandoah
 * @library /test/lib
 * @run driver TestThreadFailure
 */

import java.util.*;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class TestThreadFailure {

    static final int SIZE = 1024;
    static final int COUNT = 16;

    static class NastyThread extends Thread {
        @Override
        public void run() {
            List<Object> root = new ArrayList<Object>();
            while (true) {
                root.add(new Object[SIZE]);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length > 0) {
            for (int t = 0; t < COUNT; t++) {
                // If we experience OutOfMemoryError during our attempt to instantiate NastyThread, we'll abort
                // main and will not print "All good".  We'll also report a non-zero termination code.  In the
                // case that the previously instantiated NastyThread accumulated more than SheanndoahNoProgressThreshold
                // unproductive GC cycles before failing, the main thread may not try a Full GC before it experiences
                // OutOfMemoryError exception.
                Thread thread = new NastyThread();
                thread.start();
                thread.join();
                // Having joined thread, we know the memory consumed by thread is now garbage, and will eventually be
                // collected.  Some or all of that memory may have been promoted, so we may need to perform a Full GC
                // in order to reclaim it quickly.
            }
            System.out.println("All good");
            return;
        }

        {
            OutputAnalyzer analyzer = ProcessTools.executeLimitedTestJava(
                    "-Xmx32m",
                    "-XX:+UnlockExperimentalVMOptions",
                    "-XX:+UseShenandoahGC",
                    TestThreadFailure.class.getName(),
                    "test");

            analyzer.shouldHaveExitValue(0);
            analyzer.shouldContain("java.lang.OutOfMemoryError");
            analyzer.shouldContain("All good");
        }

        {
            ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
                    "-Xmx32m",
                    "-XX:+UnlockExperimentalVMOptions",
                    "-XX:+UseShenandoahGC", "-XX:ShenandoahGCMode=generational",
                    TestThreadFailure.class.getName(),
                    "test");

            OutputAnalyzer analyzer = new OutputAnalyzer(pb.start());
            analyzer.shouldHaveExitValue(0);
            analyzer.shouldContain("java.lang.OutOfMemoryError");
            analyzer.shouldContain("All good");
        }
    }
}
