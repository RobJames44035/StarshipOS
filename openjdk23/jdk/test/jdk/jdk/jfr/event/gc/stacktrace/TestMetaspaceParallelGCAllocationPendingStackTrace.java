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
 * @run main/othervm -XX:+UseParallelGC -Xlog:gc* -XX:MaxMetaspaceSize=64M
 *                   -XX:FlightRecorderOptions:stackdepth=256
 *                   jdk.jfr.event.gc.stacktrace.TestMetaspaceParallelGCAllocationPendingStackTrace
 */
public class TestMetaspaceParallelGCAllocationPendingStackTrace {

    public static void main(String[] args) throws Exception {
        AllocationStackTrace.testMetaspaceParallelGCAllocEvent();
    }
}
