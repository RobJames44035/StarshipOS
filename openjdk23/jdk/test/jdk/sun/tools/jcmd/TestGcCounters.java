/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test
 * @bug 8315149
 * @summary Unit test to ensure CPU hsperf counters are published.
 * @requires vm.gc.G1
 *
 * @library /test/lib
 *
 * @run main/othervm -XX:+UsePerfData -XX:+UseStringDeduplication TestGcCounters
 */

import static jdk.test.lib.Asserts.*;

import jdk.test.lib.process.OutputAnalyzer;

public class TestGcCounters {

    private static final String SUN_THREADS = "sun.threads";
    private static final String SUN_THREADS_CPUTIME = "sun.threads.cpu_time";

    public static void main(String[] args) throws Exception {
        testGcCpuCountersExist();
    }


    /**
     * jcmd -J-XX:+UsePerfData pid PerfCounter.print
     */
     private static void testGcCpuCountersExist() throws Exception {
        OutputAnalyzer output = JcmdBase.jcmd(new String[] {"PerfCounter.print"});

        output.shouldHaveExitValue(0);
        output.shouldContain(SUN_THREADS + ".total_gc_cpu_time");
        output.shouldContain(SUN_THREADS_CPUTIME + ".gc_conc_mark");
        output.shouldContain(SUN_THREADS_CPUTIME + ".gc_conc_refine");
        output.shouldContain(SUN_THREADS_CPUTIME + ".gc_service");
        output.shouldContain(SUN_THREADS_CPUTIME + ".gc_parallel_workers");
        output.shouldContain(SUN_THREADS_CPUTIME + ".vm");
        output.shouldContain(SUN_THREADS_CPUTIME + ".conc_dedup");
    }
}

