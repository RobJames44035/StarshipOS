/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/* @test
 * @summary Test selective barrier enabling works, by aggressively compiling HelloWorld with combinations
 *          of barrier flags
 * @requires vm.gc.Shenandoah
 * @library /test/lib
 * @run driver TestSelectiveBarrierFlags -Xint
 * @run driver TestSelectiveBarrierFlags -Xbatch -XX:CompileThreshold=100 -XX:TieredStopAtLevel=1
 */

/* @test
 * @summary Test selective barrier enabling works, by aggressively compiling HelloWorld with combinations
 *          of barrier flags
 * @requires vm.gc.Shenandoah
 * @library /test/lib
 * @run driver TestSelectiveBarrierFlags -Xbatch -XX:CompileThreshold=100 -XX:-TieredCompilation
 */

import java.util.*;
import java.util.concurrent.*;

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class TestSelectiveBarrierFlags {

    public static void main(String[] args) throws Exception {
        String[][] opts = {
                new String[] { "ShenandoahLoadRefBarrier" },
                new String[] { "ShenandoahSATBBarrier" },
                new String[] { "ShenandoahCASBarrier" },
                new String[] { "ShenandoahCloneBarrier" },
                new String[] { "ShenandoahStackWatermarkBarrier" }
        };

        int size = 1;
        for (String[] l : opts) {
            size *= (l.length + 1);
        }

        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int c = 0; c < size; c++) {
            int t = c;

            List<String> conf = new ArrayList<>();
            conf.addAll(Arrays.asList(args));
            conf.add("-Xmx128m");
            conf.add("-XX:+UnlockDiagnosticVMOptions");
            conf.add("-XX:+UnlockExperimentalVMOptions");
            conf.add("-XX:+UseShenandoahGC");
            conf.add("-XX:ShenandoahGCMode=passive");

            StringBuilder sb = new StringBuilder();
            for (String[] l : opts) {
                // Make a choice which flag to select from the group.
                // Zero means no flag is selected from the group.
                int choice = t % (l.length + 1);
                for (int e = 0; e < l.length; e++) {
                    conf.add("-XX:" + ((choice == (e + 1)) ? "+" : "-") + l[e]);
                }
                t = t / (l.length + 1);
            }

            conf.add("TestSelectiveBarrierFlags$Test");

            pool.submit(() -> {
                try {
                    OutputAnalyzer output = ProcessTools.executeLimitedTestJava(conf.toArray(new String[0]));
                    output.shouldHaveExitValue(0);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            });
        }

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.HOURS);
    }

    public static class Test {
        public static void main(String... args) {
            System.out.println("HelloWorld");
        }
    }

}
