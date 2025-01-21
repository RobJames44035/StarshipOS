/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package jdk.jfr.event.gc.stacktrace;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 *
 * @requires vm.gc == "null" | vm.gc == "Parallel"
 * @library /test/lib /test/jdk
 * @run main/othervm -XX:MaxNewSize=10M -Xmx64M -XX:+UseParallelGC -Xlog:gc*
 *                   -XX:FlightRecorderOptions:stackdepth=256
 *                   jdk.jfr.event.gc.stacktrace.TestParallelMarkSweepAllocationPendingStackTrace
 */
public class TestParallelMarkSweepAllocationPendingStackTrace {

    public static void main(String[] args) throws Exception {
        AllocationStackTrace.testParallelMarkSweepAllocEvent();
    }
}
